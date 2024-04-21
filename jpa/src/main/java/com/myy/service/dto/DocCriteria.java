package com.myy.service.dto;

import lombok.Data;

@Data
public class DocCriteria {

    private String title;

    private String contentLike;

    private String version;

    private Boolean isLastVersion;

}
