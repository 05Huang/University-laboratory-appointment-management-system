package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysLaboratory;
import com.mafei.laboratory.system.service.dto.LaboratoryDto;
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
public interface SysLaboratoryRepository extends JpaRepository<SysLaboratory, Long>, JpaSpecificationExecutor<SysLaboratory> {
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
    Optional<SysLaboratory> findById(Long id);


    /**
     * 查询全部
     *
     * @return
     */
    @Query(value = "select new com.mafei.laboratory.system.service.dto.LaboratoryDto(" +
            "a.id,a.laboratoryName,b.deptName,a.capacity,a.target,a.useType,a.status,a.image,a.admin)  " +
            "from SysLaboratory as a,SysDept as b  where a.depotId = b.deptId")
    List<LaboratoryDto> queryAll();

    /**
     * 根据状态查询实验室
     *
     * @return
     */
    @Query(value = "select * from sys_laboratory where status in ('0', '8')", nativeQuery = true)
    List<SysLaboratory> findByStatus();


    /**
     * 查询多个
     *
     * @param ids
     * @return
     */
    @Query(value = "SELECT * FROM sys_laboratory WHERE id in ?1", nativeQuery = true)
    List<SysLaboratory> queryByIds(Set<Long> ids);

    /**
     * 批量删除
     *
     * @param ids
     */
    @Modifying
    @Query(value = "delete from sys_laboratory where id in ?1", nativeQuery = true)
    void deleteByIds(Set<Long> ids);

    /**
     * 获取名字
     *
     * @param id
     * @return
     */
    @Query(value = "select laboratory_name from sys_laboratory where id = ?1", nativeQuery = true)
    String getName(Long id);

    /**
     * 修改状态
     *
     * @param id
     * @param status
     */
    @Modifying
    @Query(value = "update sys_laboratory set status = ?2 where id = ?1", nativeQuery = true)
    void updateStatus(Long id, String status);

}