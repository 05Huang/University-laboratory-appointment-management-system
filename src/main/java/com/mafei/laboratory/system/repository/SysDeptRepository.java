package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author wts
 */
@Repository
public interface SysDeptRepository extends JpaRepository<SysDept, Long>, JpaSpecificationExecutor<SysDept> {
    /**
     * 查询全部
     *
     * @return
     */
    @Override
    List<SysDept> findAll();
}