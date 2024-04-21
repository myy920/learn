package com.myy.web.controller;

import com.myy.service.DocService;
import com.myy.service.dto.DocCriteria;
import com.myy.service.dto.DocumentDTO;
import com.myy.util.page.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/document")
public class DocumentController {

    @Resource
    private DocService wordService;

    @PostMapping
    public Boolean create(@RequestBody DocumentDTO dto) {
        wordService.createDoc(dto);
        return true;
    }

    @PutMapping
    public Boolean update(@RequestBody DocumentDTO dto) {
        wordService.updateDoc(dto);
        return true;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        wordService.deleteDoc(id);
    }

    @GetMapping
    public PageResult<DocumentDTO> queryDocs(Pageable pageable) {
        DocCriteria criteria = new DocCriteria();
        criteria.setIsLastVersion(Boolean.TRUE);
        return wordService.queryDocs(criteria, pageable);
    }

}
