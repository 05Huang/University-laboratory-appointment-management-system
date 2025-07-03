package com.mafei.laboratory.system.service.dto;

import lombok.Data;

import java.util.Set;

/**
 * @author wutangsheng
 * @create 2021-02-23 16:33
 * @info
 */
@Data
public class UpdateStatusDto {
    private String status;

    private Set<Long> ids;

}
