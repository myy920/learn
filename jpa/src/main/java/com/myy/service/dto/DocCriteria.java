package com.myy.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class DocCriteria {

    private List<String> titles;

    private String title;

    private String contentLike;

    private String version;

    private Boolean isLastVersion;

}
