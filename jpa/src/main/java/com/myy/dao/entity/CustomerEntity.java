package com.myy.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "t_customer")
@Entity
@DynamicInsert(value = false)
@DynamicUpdate(value = true)
public class CustomerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, length = 32)
    private Long id;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Column(name = "age")
    private Integer age;

    /*@Basic
    private String basicTest;*/

    @Transient
    private String transientTest;
}

