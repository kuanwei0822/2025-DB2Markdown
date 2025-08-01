package com.project.extractor;

import com.project.extractor.model.TableMeta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * MetadataExtractor 介面定義了從資料庫中提取表格元數據的方法。
 * - 可以實作不同的資料庫 Metadata 取得，例如 PostgreSQL、MySQL 等。
 */
public interface MetadataExtractor {
    List<TableMeta> extractMetadata(Connection connection) throws SQLException;
}
