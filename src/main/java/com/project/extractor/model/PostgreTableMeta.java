package com.project.extractor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

/**
 * TableMeta 類別用於描述資料庫表格的 Metadata。
 * - 包含表格名稱、描述、欄位、主鍵、Foreign Key 和約束條件等資訊。
 * - Profile 指定在 PostgreSQL 環境下使用。
 *
 * 註：
 * 這個類別是給機器（程式）處理的，不適合直接輸出閱讀，若要直接輸出閱讀，請轉換 DTO 類別輸出。
 */
@Data
@Profile("postgresql")
public class PostgreTableMeta implements ITableMeta {

    // Table 名稱
    private String tableName;

    // Table 的描述或備註
    private String remarks;

    // 欄位
    private List<ColumnMeta> columns;

    // 主鍵
    private List<String> primaryKeys;

    // Foreign Key
    private List<ForeignKeyMeta> foreignKeys;

    // 約束條件
    private List<ConstraintMeta> constraints;

    // ==== Inner Classes ====

    // 欄位的 MetaData
    @Data
    public static class ColumnMeta {
        // 欄位名稱
        private String columnName;
        // 欄位的資料類型
        private String dataType;
        // 欄位大小
        private Integer columnSize;
        // 是否允許為 NULL : 不允許 NULL、允許 NULL、允許 NULL 不明
        private Nullable nullable;
        // 預設值
        private String defaultValue;
        // 備註
        private String remarks;
        // 是否為主鍵，預設為否
        private boolean isPrimaryKey = false;

        /**
         * 欄位是否允許為 NULL 的枚舉類型。
         * - COLUMN_NO_NULLS: 不允許 NULL
         * - COLUMN_NULLABLE: 允許 NULL
         * - COLUMN_NULLABLE_UNKNOWN: 允許 NULL 不明
         */
        @AllArgsConstructor
        public enum Nullable {
            COLUMN_NO_NULLS(0,"NO"), // 不允許 NULL
            COLUMN_NULLABLE(1, "YES"), // 允許 NULL
            COLUMN_NULLABLE_UNKNOWN(2, "UNKNOWN"); // 允許 NULL 不明

            @Getter
            private final int value;

            @Getter
            private final String displayName;

            // 根據 JDBC 回傳的 code 取得對應的 Nullable Enum
            public static Nullable from(int code) {
                return Arrays.stream(values())
                        .filter(n -> n.getValue() == code)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unknown NULLABLE code: " + code));
            }
        }
    }

    // Foreign Key 的 MetaData
    @Data
    public static class ForeignKeyMeta {
        // Foreign Key 名稱
        private String fkName;
        // 參照的欄位資訊
        private String columnName;
        // 參照的表格名稱
        private String referencedTable;
        // 參照的欄位名稱
        private String referencedColumn;
        // ON UPDATE 和 ON DELETE 的規則
        private String updateRule;
        // ON DELETE 的規則
        private String deleteRule;
    }

    // 約束條件的 MetaData
    @Data
    public static class ConstraintMeta {
        // 約束條件名稱
        private String constraintName;
        // 約束條件類型 PRIMARY KEY, FOREIGN KEY, CHECK, UNIQUE...
        private String constraintType;
        // 涉及的欄位名稱（可能是多欄）
        private List<String> columnNames;
        // 若為 FOREIGN KEY，這裡為參照的表格名稱
        private String checkClause;
    }
}
