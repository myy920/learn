package com.myy.web.controller;

import com.myy.service.DocumentService;
import com.myy.service.dto.DocCriteria;
import com.myy.service.dto.DocumentDTO;
import com.myy.util.Result;
import com.myy.util.page.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/document")
public class DocumentController {

    @Resource
    private DocumentService wordService;

    @PostMapping
    public Result<Void> create(@RequestBody DocumentDTO dto) {
        new Thread(() -> wordService.createDocument(dto)).start();
        return Result.ok();
    }

    @PutMapping
    public Result<Void> update(@RequestBody DocumentDTO dto) {
        wordService.updateDocument(dto);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        wordService.deleteDocument(id);
    }

    @GetMapping
    public Result<PageResult<DocumentDTO>> queryDocs(Pageable pageable) {
        DocCriteria criteria = new DocCriteria();
        criteria.setIsLastVersion(Boolean.TRUE);
        return Result.ok(wordService.queryDocs(criteria, pageable));
    }

}
