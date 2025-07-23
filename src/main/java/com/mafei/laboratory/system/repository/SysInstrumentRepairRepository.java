package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysInstrumentRepair;
import com.mafei.laboratory.system.entity.SysInstrumentRepair;
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
public interface SysInstrumentRepairRepository extends JpaRepository<SysInstrumentRepair, Long>, JpaSpecificationExecutor<SysInstrumentRepair> {
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
    Optional<SysInstrumentRepair> findById(Long id);

    /**
     * 根据设备Id查询
     *
     * @param instrumentId
     * @return
     */
    SysInstrumentRepair findByInstrumentId(Long instrumentId);

    /**
     * 查询多个
     *
     * @param ids
     * @return
     */
    @Query(value = "SELECT * FROM sys_instrument_repair WHERE id in ?1", nativeQuery = true)
    List<SysInstrumentRepair> queryByIds(Set<Long> ids);

    /**
     * 批量删除
     *
     * @param ids
     */
    @Modifying
    @Query(value = "delete from sys_instrument_repair where id in ?1", nativeQuery = true)
    void deleteByIds(Set<Long> ids);

    /**
     * 获取名字
     *
     * @param id
     * @return
     */
    @Query(value = "select a.instrument_name from sys_instrument a,sys_instrument_repair b where b.id = ?1" +
            " and b.instrument_id = a.id", nativeQuery = true)
    String getName(Long id);

    /**
     * 计数
     *
     * @param id
     * @return
     */
    Integer countByInstrumentId(Long id);

    /**
     * 查找id
     * @return
     */
    @Query(value = "select instrument_id from sys_instrument_repair",nativeQuery = true)
    List<Long> findInstrumentId();
}