package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysBorrowLaboratory;
import com.mafei.laboratory.system.entity.vo.BorrowLaboratoryVo;
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
public interface SysBorrowLaboratoryRepository extends JpaRepository<SysBorrowLaboratory, Long>, JpaSpecificationExecutor<SysBorrowLaboratory> {
    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    @Override
    Optional<SysBorrowLaboratory> findById(Long id);

    /**
     * 根据status查询
     *
     * @param status
     * @return
     */
    SysBorrowLaboratory findByStatus(String status);

    /**
     * 根据用户查询
     *
     * @param userId
     * @return
     */
    @Query(value = "select distinct * from sys_borrow_laboratory where user_id =?1 and borrow_status in ('3','4','5','7')" +
            " order by borrow_status desc,create_time desc", nativeQuery = true)
    List<SysBorrowLaboratory> findByUserId(Long userId);

    /**
     * 查询全部
     *
     * @return
     */
    @Query(value = "select new com.mafei.laboratory.system.entity.vo.BorrowLaboratoryVo(" +
            "a.id,a.userId,a.laboratoryId,b.userName,a.status,a.borrowStatus,a.comment,a.createTime,c.laboratoryName) " +
            " from SysBorrowLaboratory as a,SysUser as b,SysLaboratory as c " +
            " where a.userId = b.userId and a.laboratoryId = c.id and a.status in ('7','1','4','5','8') " +
            " order by a.status desc ,a.createTime desc")
    List<BorrowLaboratoryVo> myFindAll();

    /**
     * 查询审核中的全部
     *
     * @return
     */
    @Query(value = "select new com.mafei.laboratory.system.entity.vo.BorrowLaboratoryVo(" +
            "a.id,a.userId,a.laboratoryId,b.userName,a.status,a.borrowStatus,a.comment,a.createTime,c.laboratoryName) " +
            " from SysBorrowLaboratory as a,SysUser as b,SysLaboratory as c " +
            " where a.userId = b.userId and a.laboratoryId = c.id and a.status in ('7','1','4','5') " +
            " order by a.status desc ,a.createTime desc")
    List<BorrowLaboratoryVo> myFindAllReview();


    /**
     * 查询多个
     *
     * @param ids
     * @return
     */
    @Query(value = "SELECT * FROM sys_borrow_laboratory WHERE id not in ?1", nativeQuery = true)
    List<SysBorrowLaboratory> queryByIds(Set<Long> ids);


    /**
     * 修改状态
     *
     * @param id
     * @param status
     */
    @Modifying
    @Query(value = "update sys_borrow_laboratory set status = ?2 where id = ?1", nativeQuery = true)
    void updateStatus(Long id, String status);

    /**
     * 修改状态
     *
     * @param id
     * @param status
     */
    @Modifying
    @Query(value = "update sys_borrow_laboratory set borrow_status = ?2 where id = ?1", nativeQuery = true)
    void updateBorrowStatus(Long id, String status);

    /**
     * 查找id
     *
     * @return
     */
    @Query(value = "select instrument_id from sys_borrow_laboratory", nativeQuery = true)
    List<Long> findInstrumentId();

    /**
     * 根据状态查询
     * @param status
     * @return
     */
    Long countByBorrowStatus(String status);

    /**
     * 统计用户的所有借用记录数量
     *
     * @param userId 用户ID
     * @return 借用记录数量
     */
    long countByUserId(Long userId);

    /**
     * 统计用户特定状态的借用记录数量
     *
     * @param userId 用户ID
     * @param status 状态
     * @return 特定状态的借用记录数量
     */
    long countByUserIdAndBorrowStatus(Long userId, String status);
}