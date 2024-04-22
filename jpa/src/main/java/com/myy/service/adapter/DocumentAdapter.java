package com.myy.service.adapter;

import com.myy.dao.entity.DocumentEntity;
import com.myy.service.dto.DocumentDTO;
import org.springframework.beans.BeanUtils;

public class DocumentAdapter {

    public static DocumentEntity toEntity(DocumentDTO dto) {
        DocumentEntity entity = new DocumentEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static DocumentDTO toDTO(DocumentEntity entity) {
        DocumentDTO dto = new DocumentDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
