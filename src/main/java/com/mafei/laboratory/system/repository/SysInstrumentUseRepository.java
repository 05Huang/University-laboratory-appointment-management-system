package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysInstrumentUse;
import com.mafei.laboratory.system.entity.SysInstrumentUse;
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
public interface SysInstrumentUseRepository extends JpaRepository<SysInstrumentUse, Long>, JpaSpecificationExecutor<SysInstrumentUse> {
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
    Optional<SysInstrumentUse> findById(Long id);

    /**
     * 根据设备Id查询
     *
     * @param instrumentId
     * @return
     */
    SysInstrumentUse findByInstrumentId(Long instrumentId);

    /**
     * 查询多个
     *
     * @param ids
     * @return
     */
    @Query(value = "SELECT * FROM sys_instrument_use WHERE id in ?1", nativeQuery = true)
    List<SysInstrumentUse> queryByIds(Set<Long> ids);

    /**
     * 批量删除
     *
     * @param ids
     */
    @Modifying
    @Query(value = "delete from sys_instrument_use where id in ?1", nativeQuery = true)
    void deleteByIds(Set<Long> ids);

    /**
     * 获取名字
     *
     * @param id
     * @return
     */
    @Query(value = "select a.instrument_name from sys_instrument a,sys_instrument_use b where b.id = ?1" +
            " and b.instrument_id = a.id", nativeQuery = true)
    String getName(Long id);
}