package com.project.api.rest;

import com.project.service.PostgreMarkdownService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PostgreMarkdownController 用於處理 PostgreSQL Markdown 相關的 API 請求。
 * 提供一個 API 來獲取資料庫表格的 Markdown 文件。
 */
@RestController
@RequestMapping("/api/postgresql/document")
@RequiredArgsConstructor
@Profile("postgresql")
public class PostgreMarkdownController {

    private final PostgreMarkdownService postgreMarkdownService;

    @RequestMapping("/markdown")
    public void getMarkdown(HttpServletResponse response) {
        postgreMarkdownService.generateMarkdown(response);
    }
}
