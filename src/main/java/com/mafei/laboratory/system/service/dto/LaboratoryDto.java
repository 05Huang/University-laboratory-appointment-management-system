package com.mafei.laboratory.system.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wutangsheng
 * @create 2021-03-10 10:54
 * @info
 */
@Getter
@Setter
public class LaboratoryDto {
    private Long id;
    private String laboratoryName;
    private String deptName;
    private Integer capacity;
    private String target;
    private String useType;
    private String status;
    private String image;
    private String admin;

    public LaboratoryDto() {
    }

    public LaboratoryDto(Long id, String laboratoryName, String deptName, Integer capacity, String target, String useType, String status, String image, String admin) {
        this.id = id;
        this.laboratoryName = laboratoryName;
        this.deptName = deptName;
        this.capacity = capacity;
        this.target = target;
        this.useType = useType;
        this.status = status;
        this.image = image;
        this.admin = admin;
    }
}
