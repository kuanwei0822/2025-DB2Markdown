package com.project.service;

import com.project.document.markdown.MarkdownPostgreStandardGenerator;
import com.project.extractor.model.PostgreTableMeta;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("postgresql")
public class PostgreMarkdownService {

    // Metadata 查詢 Service
    private final PostgreMetadataExtractionService databaseMetaDataSelover;

    // Markdown 標準產生器
    private final MarkdownPostgreStandardGenerator markdownPostgreStandardGenerator;

    /**
     * 生成 PostgreSQL 資料庫的 Markdown 文件的主方法。
     * 先取得所有 Table 的 Metadata 資訊。
     * 再使用 MarkdownPostgreStandardGenerator 生成 Markdown 文件。
     */
    public void generateMarkdown(HttpServletResponse response) {

        // 1. 取得所有 Table 的 Metadata 資訊
        List<PostgreTableMeta> postgreTableMetas = databaseMetaDataSelover.extractTables();

        // 2. 生成 Markdown 文件
        markdownPostgreStandardGenerator.generateMarkdown(postgreTableMetas, response);


    }

}
