package com.myy.service;

import com.myy.service.dto.DocCriteria;
import com.myy.service.dto.DocumentDTO;
import com.myy.util.page.PageResult;
import org.springframework.data.domain.Pageable;

public interface DocumentService {

    void createDocument(DocumentDTO dto);

    void updateDocument(DocumentDTO dto);

    void deleteDocument(Long id);

    void updateLastVersion(String title);

    PageResult<DocumentDTO> queryDocs(DocCriteria criteria, Pageable pageable);

    void edmDelete(String docId);

}
