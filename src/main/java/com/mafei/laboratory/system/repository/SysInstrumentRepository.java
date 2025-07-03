package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysInstrument;
import com.mafei.laboratory.system.entity.SysInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author wts
 */
@Repository
public interface SysInstrumentRepository extends JpaRepository<SysInstrument, Long>, JpaSpecificationExecutor<SysInstrument> {
    /**
     * 删除
     *
     * @param id
     */
    @Override
    @Modifying
    void deleteById(Long id);

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    @Override
    Optional<SysInstrument> findById(Long id);

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    List<SysInstrument> findAll();

    /**
     * 查询多个
     *
     * @return
     */
    @Query(value = "SELECT * FROM sys_instrument WHERE status = '0'", nativeQuery = true)
    List<SysInstrument> queryByStatus();

    /**
     * 查询多个
     *
     * @param ids
     * @return
     */
    @Query(value = "SELECT * FROM sys_instrument WHERE id in ?1", nativeQuery = true)
    List<SysInstrument> queryByIds(Set<Long> ids);

    /**
     * 批量删除
     *
     * @param ids
     */
    @Modifying
    @Query(value = "delete from sys_instrument where id in ?1", nativeQuery = true)
    void deleteByIds(Set<Long> ids);


    /**
     * 修改状态
     *
     * @param id
     * @param status
     */
    @Modifying
    @Query(value = "update sys_instrument set status = ?2 where id = ?1", nativeQuery = true)
    void updateStatus(Long id, String status);

    /**
     * 获取名字
     *
     * @param id
     * @return
     */
    @Query(value = "select instrument_name from sys_instrument where id = ?1", nativeQuery = true)
    String getName(Long id);
}