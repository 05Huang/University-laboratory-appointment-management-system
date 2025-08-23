package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysBorrowLaboratory;
import com.mafei.laboratory.system.entity.vo.BorrowLaboratoryVo;
import com.mafei.laboratory.system.entity.vo.BorrowLaboratoryVo;
import com.mafei.laboratory.system.service.dto.UpdateDto;

import java.util.List;
import java.util.Set;

/**
 * 设备借用表(SysBorrowLaboratory)表服务接口
 *
 * @author wts
 * @since 2021-03-10 08:48:27
 */
public interface SysBorrowLaboratoryService {

    /**
     * 管理员查询全部
     *
     * @return
     */
    List<BorrowLaboratoryVo> findAll();

    /**
     * 管理员查询审核中的
     *
     * @return
     */
    List<BorrowLaboratoryVo> findAllReview();
    /**
     * 查询用户单据
     *
     * @param userId
     * @return
     */
    List<BorrowLaboratoryVo> findAllByUserId(Long userId);

    /**
     * 根据 状态 查询
     *
     * @param ids
     * @return
     */
    List<SysBorrowLaboratory> findAllByStatus(Set<Long> ids);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysBorrowLaboratory queryById(Long id);

    /**
     * 新增数据
     *
     * @param borrowVo 实例对象
     * @return 实例对象
     */
    void insert(BorrowLaboratoryVo borrowVo);


    /**
     * 归还
     *
     * @param status
     * @param id
     */
    void updateBorrow(String status, Long id);

    /**
     * 批量归还
     *
     * @param status
     * @param id
     */
    void updateBorrow(String status, Set<Long> id);

    /**
     * 审核
     * @param updateDto
     */
    void updateCheck(UpdateDto updateDto);

    /**
     * 批量审核
     *
     * @param status
     * @param id
     */
    void updateCheck(String status, Set<Long> id);

    /**
     * 获取全部记录
     *
     * @return
     */
    Long countAll();

    Long countAllByStatus(String status);

    /**
     * 统计用户的所有借用记录数量
     *
     * @param userId 用户ID
     * @return 借用记录数量
     */
    long countAllByUserId(Long userId);

    /**
     * 统计用户特定状态的借用记录数量
     *
     * @param userId 用户ID
     * @param status 状态
     * @return 特定状态的借用记录数量
     */
    long countAllByUserIdAndStatus(Long userId, String status);
}
