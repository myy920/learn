package com.myy.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "test_document")
public class DocumentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false)
    private String title;

    private String content;

    @Column(length = 64, nullable = false)
    private String version;

    @Column(nullable = false)
    private Boolean isLastVersion;

}
