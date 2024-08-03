package com.myy.web.controller;

import com.myy.dao.entity.DocumentEntity;
import com.myy.dao.repository.DocumentRepository;
import com.myy.service.DocumentService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EdmHandler {

    @Resource
    private DocumentService documentService;

    @Resource
    private DocumentRepository documentRepository;

    @Transactional
    public void execute() {
        documentService.edmDelete("001");
        documentService.edmDelete("002");
        documentService.edmDelete("003");
        DocumentEntity entity = documentRepository.findById(1L).orElseThrow(() -> new RuntimeException("not find"));
        entity.setTitle(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        documentRepository.save(entity);
        int a = 1 / 0;
    }

}
