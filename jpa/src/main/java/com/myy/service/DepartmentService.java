package com.myy.service;

import com.myy.service.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {

    void createDepartment(DepartmentDTO dto);

    void updateDepartment(DepartmentDTO dto);

    void deleteDepartment(Long id);

    List<DepartmentDTO> queryAllDepartment();

}
