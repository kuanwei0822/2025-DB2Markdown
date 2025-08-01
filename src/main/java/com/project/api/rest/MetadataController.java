package com.project.api.rest;

import com.project.extractor.model.TableMeta;
import com.project.service.MetadataExtractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metadata")
@RequiredArgsConstructor
public class MetadataController {

    private final MetadataExtractionService metadataService;

    @RequestMapping("tables")
    public ResponseEntity<List<TableMeta>> getTables() {
        List<TableMeta> tables = metadataService.extractTables();
        return ResponseEntity.ok(tables);
    }
}
