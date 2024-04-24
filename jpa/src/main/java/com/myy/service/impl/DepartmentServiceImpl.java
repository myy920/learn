package com.myy.service.impl;

import com.myy.dao.entity.DepartmentEntity;
import com.myy.dao.repository.DepartmentRepository;
import com.myy.service.DepartmentService;
import com.myy.service.adapter.DepartmentAdapter;
import com.myy.service.dto.DepartmentDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentRepository departmentRepository;

    @Override
    public void createDepartment(DepartmentDTO dto) {
        // 自动创建缺失的父级部门
        // 重名校验
        DepartmentEntity entity = DepartmentAdapter.toEntity(dto);
        checkDuplicateName(entity);
        departmentRepository.save(entity);
        //departmentRepository.findByName(entity.getL1Dept());

    }

    private void checkDuplicateName(DepartmentEntity entity) {
        DepartmentEntity probe = new DepartmentEntity();
        probe.setL1Dept(entity.getL1Dept());
        probe.setL2Dept(entity.getL2Dept());
        probe.setL3Dept(entity.getL3Dept());
        probe.setL4Dept(entity.getL4Dept());
        departmentRepository.findAll(Example.of(probe));

    }

    private int toLevel(DepartmentEntity entity) {
        if (StringUtils.isEmpty(entity.getL1Dept())) {
            return 0;
        }
        if (StringUtils.isEmpty(entity.getL2Dept())) {
            return 1;
        }
        if (StringUtils.isEmpty(entity.getL3Dept())) {
            return 2;
        }
        if (StringUtils.isEmpty(entity.getL4Dept())) {
            return 3;
        }
        return 4;
    }

    @Override
    public void updateDepartment(DepartmentDTO dto) {

    }

    @Override
    public void deleteDepartment(Long id) {

    }

    @Override
    public List<DepartmentDTO> queryAllDepartment() {
        ExampleMatcher em = ExampleMatcher.matchingAll();
        em = em.withMatcher("l1Dept", ExampleMatcher.GenericPropertyMatcher::storeDefaultMatching);
        em = em.withMatcher("l2Dept", ExampleMatcher.GenericPropertyMatcher::storeDefaultMatching);
        em = em.withMatcher("l3Dept", ExampleMatcher.GenericPropertyMatcher::storeDefaultMatching);
        em = em.withMatcher("l4Dept", ExampleMatcher.GenericPropertyMatcher::storeDefaultMatching);
        em = em.withIncludeNullValues();
        DepartmentEntity probe = new DepartmentEntity();
        probe.setL1Dept("aaa");
        departmentRepository.findAll(Example.of(probe, em));
        return null;
    }
}
