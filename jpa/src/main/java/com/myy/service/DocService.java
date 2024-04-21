package com.myy.service;

import com.myy.service.dto.DocCriteria;
import com.myy.service.dto.DocumentDTO;
import com.myy.util.page.PageResult;
import org.springframework.data.domain.Pageable;

public interface DocService {

    void createDoc(DocumentDTO dto);

    void updateDoc(DocumentDTO dto);

    void deleteDoc(Long id);

    PageResult<DocumentDTO> queryDocs(DocCriteria criteria, Pageable pageable);

}
