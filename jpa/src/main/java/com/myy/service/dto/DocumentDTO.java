package com.myy.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentDTO {

    private Long id;
    private String title;
    private String content;
    private String version;
    private Boolean isLastVersion;
    private String creator;
    private LocalDateTime createTime;
    private String updater;
    private LocalDateTime updateTime;

}
