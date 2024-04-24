package com.myy.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "test_department")
public class DepartmentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "l1_dept", nullable = false)
    private String l1Dept;

    @Column(name = "l2_dept")
    private String l2Dept;

    @Column(name = "l3_dept")
    private String l3Dept;

    @Column(name = "l4_dept")
    private String l4Dept;

    private String description;

    private Integer sort;

}
