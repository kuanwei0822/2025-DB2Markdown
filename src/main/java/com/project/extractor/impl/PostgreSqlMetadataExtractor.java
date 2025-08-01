package com.project.extractor.impl;

import com.project.extractor.MetadataExtractor;
import com.project.extractor.model.TableMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用於從 PostgreSQL 資料庫中取得 Metadata。
 * - 實作 MetadataExtractor 介面。
 * - 使用 @Profile 註解來指定這個 Component 只在 Profile=postgresql 環境下啟用。
 */
@Slf4j
@Component
@Profile("postgresql")
public class PostgreSqlMetadataExtractor implements MetadataExtractor {

    @Override
    public List<TableMeta> extractMetadata(Connection connection) throws SQLException {

        // TableMeta 承載物件
        List<TableMeta> tableMetas = new ArrayList<>();
        // 取得資料庫的 Metadata
        DatabaseMetaData metaData = connection.getMetaData();
        // 取得當前連線的 Catalog(DBName) 和 Schema
        String catalog = connection.getCatalog();
        String schema = connection.getSchema();

        // 查詢 資料庫(catalog) 的 schema 的所有表格
        try (ResultSet rs = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String tableRemarks = rs.getString("REMARKS");
                log.debug("Found table: {}", tableName);

                TableMeta tableMeta = new TableMeta();
                tableMeta.setTableName(tableName);
                tableMeta.setRemarks(tableRemarks);
                // TODO 取得欄位資訊並設定到 tableMeta.columns

                tableMetas.add(tableMeta);
            }
        }

        return tableMetas;
    }
}