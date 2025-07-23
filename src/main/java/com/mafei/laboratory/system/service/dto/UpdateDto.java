package com.mafei.laboratory.system.service.dto;

import lombok.Data;

import javax.persistence.Id;

/**
 * @author wutangsheng
 * @create 2021-03-12 3:06
 * @info
 */
@Data
public class UpdateDto {
    private String status;
    private Long id;
    private String comment;
}
