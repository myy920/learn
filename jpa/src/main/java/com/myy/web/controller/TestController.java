package com.myy.web.controller;

import com.myy.dao.entity.DocumentEntity;
import com.myy.service.dto.DocCriteria;
import com.myy.service.impl.DocumentViewService;
import com.myy.util.Result;
import com.myy.util.page.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @GetMapping("/v1/test")
    public void test() {
        System.out.println(1);
        System.out.println(2);
    }

    @Resource
    private DocumentViewService documentViewService;

    @PostMapping("/v1/document/page")
    public Result<PageResult<DocumentEntity>> pageDocument(Pageable pageable, @RequestBody DocCriteria docCriteria) {
        return Result.ok(documentViewService.pageDocument(docCriteria, pageable));
    }

    @PostMapping("/v1/document/page2")
    public Result<Page<DocumentEntity>> pageDocument2(Pageable pageable, @RequestBody DocCriteria docCriteria) {
        return Result.ok(documentViewService.pageDocument2(docCriteria, pageable));
    }

    @PostMapping("/v1/document/page3")
    public Result<Page<DocumentEntity>> pageDocument3(Pageable pageable, @RequestBody DocCriteria docCriteria) {
        return Result.ok(documentViewService.pageDocument3(docCriteria, pageable));
    }

    @PostMapping("/v1/document/page4")
    public Result<Page<DocumentEntity>> pageDocument4(Pageable pageable, @RequestBody DocCriteria docCriteria) {
        return Result.ok(documentViewService.pageDocument4(docCriteria, pageable));
    }
}
