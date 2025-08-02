package com.project.api.rest;

import com.project.extractor.model.PostgreTableMeta;
import com.project.service.PostgreMetadataExtractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metadata")
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
