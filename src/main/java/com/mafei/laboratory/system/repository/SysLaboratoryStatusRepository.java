package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysLaboratoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author wts
 */
@Repository
public interface SysLaboratoryStatusRepository extends JpaRepository<SysLaboratoryStatus, Long>, JpaSpecificationExecutor<SysLaboratoryStatus> {

}