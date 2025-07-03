package com.mafei.laboratory.commons.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wutangsheng
 * @create 2021-03-11 16:55
 * @info
 */
public enum TypeEnum {
    /**
     * 用途类型（0化学 1物理 2计算机，3金工，4电子电路，5土木，6交通，7航天航空，8食品工程，9药物，10材料）
     */
    CHEMISTRY(0, "化学"),
    PHYSICS(1, "物理"),
    COMPUTER(2, "计算机"),
    METALWORK(3, "金工"),
    ELECTRONIC_CIRCUIT(4, "电子电路"),
    CIVIL_ENGINEERING(5, "土木"),
    TRAFFIC(6, "交通"),
    AEROSPACE(7, "航天航空"),
    FOOD_ENGINEERING(8, "食品工程"),
    DRUG(9, "药物"),
    MATERIAL(10, "材料");


    private Integer type;
    private String value;

    TypeEnum() {
    }

    TypeEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
