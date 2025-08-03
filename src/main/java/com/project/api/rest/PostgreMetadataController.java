package com.project.api.rest;

import com.project.extractor.model.PostgreTableMeta;
import com.project.service.PostgreMetadataExtractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * PostgreMetadataController 用於處理 PostgreSQL Metadata 相關的 API 請求。
 * 提供一個 API 來獲取資料庫表格的 Metadata。
 */
@RestController
@RequestMapping("/api/postgresql/metadata")
@RequiredArgsConstructor
@Profile("postgresql")
public class PostgreMetadataController {

    private final PostgreMetadataExtractionService metadataService;

    @RequestMapping("/tables")
    public ResponseEntity<List<PostgreTableMeta>> getTables() {
        List<PostgreTableMeta> tables = metadataService.extractTables();
        return ResponseEntity.ok(tables);
    }
}
