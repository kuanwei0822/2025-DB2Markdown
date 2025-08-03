package com.project.extractor;

import com.project.extractor.model.ITableMeta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * MetadataExtractor 介面定義了從資料庫中提取表格 Metadata 的方法。
 * - 可以實作不同的資料庫 Metadata 取得，例如 PostgreSQL、MySQL 等。
 */
public interface MetadataExtractor<T extends ITableMeta> {

    // 提取資料庫中所有表格的 Metadata (細節各家資料庫可能不同，因此只要求最終物件為 List<TableMeta>)
    List<T> extractMetadata(Connection connection) throws SQLException;

}
