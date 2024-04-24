package com.myy.service.adapter;

import com.myy.dao.entity.DepartmentEntity;
import com.myy.service.dto.DepartmentDTO;
import org.springframework.beans.BeanUtils;

public class DepartmentAdapter {

    public static DepartmentEntity toEntity(DepartmentDTO dto) {
        DepartmentEntity entity = new DepartmentEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

}
