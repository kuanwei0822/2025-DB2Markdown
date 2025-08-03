package com.project.document.markdown;

import com.project.extractor.model.PostgreTableMeta;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
@Profile("postgresql")
public class MarkdownPostgreStandardGenerator {

    public String generateMarkdown(List<PostgreTableMeta> tables, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();

        for (PostgreTableMeta table : tables) {
            String tableName = table.getTableName();

            // 標題：Table 名稱
            sb.append("# Table: ").append(tableName).append("\n\n");

            // 備註（如果有）
            if (table.getRemarks() != null && !table.getRemarks().isBlank()) {
                sb.append("## Description\n").append(table.getRemarks()).append("\n\n");
            }

            // 欄位資訊
            sb.append("## Columns\n");
            sb.append("| Name | Type | Size | Default | Nullable | Primary Key | Remarks |\n");
            sb.append("|------|------|------|---------|----------|-------------|---------|\n");

            for (PostgreTableMeta.ColumnMeta column : table.getColumns()) {
                sb.append("| ")
                        .append(column.getColumnName()).append(" | ")
                        .append(column.getDataType()).append(" | ")
                        .append(column.getColumnSize() == null ? "" : column.getColumnSize()).append(" | ")
                        .append(column.getDefaultValue() == null ? "" : column.getDefaultValue()).append(" | ")
                        .append(column.getNullable().getDisplayName()).append(" | ")
                        .append(column.isPrimaryKey() ? "YES" : "").append(" | ")
                        .append(column.getRemarks() == null ? "" : column.getRemarks())
                        .append(" |\n");
            }

            // 約束資訊
            sb.append("\n## Constraints\n");

            List<PostgreTableMeta.ConstraintMeta> uniques = table.getConstraints().stream()
                    .filter(c -> c.getConstraintType() == PostgreTableMeta.ConstraintMeta.ConstraintType.UNIQUE)
                    .collect(Collectors.toList());

            List<PostgreTableMeta.ConstraintMeta> checks = table.getConstraints().stream()
                    .filter(c -> c.getConstraintType() == PostgreTableMeta.ConstraintMeta.ConstraintType.CHECK)
                    .collect(Collectors.toList());


            // TODO 需要從源頭 MetadataExtractor 修正 uc.getColumnNames() 為 null 問題。
            if (!uniques.isEmpty()) {
                sb.append("\n### UNIQUE\n");
                for (PostgreTableMeta.ConstraintMeta uc : uniques) {
                    sb.append("- ").append(uc.getConstraintName()).append(": (")
                            .append(String.join(", ", uc.getColumnNames() == null ? List.of() : uc.getColumnNames()))
                            .append(")\n");
                }
            }

            if (!checks.isEmpty()) {
                sb.append("\n### CHECK\n");
                for (PostgreTableMeta.ConstraintMeta ck : checks) {
                    sb.append("- ").append(ck.getConstraintName()).append(": ")
                            .append(ck.getCheckClause()).append("\n");
                }
            }

            // 外鍵資訊
            List<PostgreTableMeta.ForeignKeyMeta> fks = table.getForeignKeys();
            if (!fks.isEmpty()) {
                sb.append("\n### FOREIGN KEY\n");
                for (PostgreTableMeta.ForeignKeyMeta fk : fks) {
                    sb.append("- ").append(fk.getFkName())
                            .append(": ").append(fk.getColumnName())
                            .append(" → ").append(fk.getReferencedTable())
                            .append(".").append(fk.getReferencedColumn())
                            .append(" (ON DELETE ").append(fk.getDeleteRule().getDisplayName())
                            .append(", ON UPDATE ").append(fk.getUpdateRule().getDisplayName()).append(")\n");
                }
            }

            sb.append("\n---\n\n");
        }

        // === 寫入 HTTP Response 為 .txt 檔案 ===
        try {
            response.setContentType("text/plain; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"database_metadata.txt\"");
            response.getWriter().write(sb.toString());
        } catch (IOException e) {
            log.error("輸出 metadata 文件時發生錯誤", e);
        }

        return sb.toString(); // 保留回傳字串以供測試或其他用途
    }

}
