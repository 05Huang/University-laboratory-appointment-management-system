package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysBorrowInstrument;
import com.mafei.laboratory.system.entity.vo.BorrowInstrumentVo;
import com.mafei.laboratory.system.service.dto.UpdateDto;

import java.util.List;
import java.util.Set;

/**
 * 设备借用表(SysBorrowInstrument)表服务接口
 *
 * @author makejava
 * @since 2021-03-10 08:48:25
 */
public interface SysBorrowInstrumentService {
    /**
     * 管理员查询全部
     *
     * @return
     */
    List<BorrowInstrumentVo> findAll();

    /**
     * 用户查询单据
     *
     * @param userId
     * @return
     */
    List<BorrowInstrumentVo> findAllByUserId(Long userId);

    /**
     * 根据 状态 查询
     *
     * @param ids
     * @return
     */
    List<SysBorrowInstrument> findAllByStatus(Set<Long> ids);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysBorrowInstrument queryById(Long id);

    /**
     * 新增数据
     *
     * @param borrowVo 实例对象
     * @return 实例对象
     */
    void insert(BorrowInstrumentVo borrowVo);

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
     *
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

    /**
     * 获取全部记录
     *
     * @return
     */
    Long countAllByStatus(String status);
}
