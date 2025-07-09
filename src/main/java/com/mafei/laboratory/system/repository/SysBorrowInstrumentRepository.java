package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysBorrowInstrument;
import com.mafei.laboratory.system.entity.vo.BorrowInstrumentVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author wts
 */
@Repository
public interface SysBorrowInstrumentRepository extends JpaRepository<SysBorrowInstrument, Long>, JpaSpecificationExecutor<SysBorrowInstrument> {

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    @Override
    Optional<SysBorrowInstrument> findById(Long id);

    /**
     * 根据status查询
     *
     * @param status
     * @return
     */
    SysBorrowInstrument findByStatus(String status);

    /**
     * 根据用户查询
     *
     * @param userId
     * @return
     */
    @Query(value = "select distinct * from sys_borrow_instrument where user_id =?1 and borrow_status in ('3','4','5','7','6','8')" +
            " order by borrow_status desc,create_time desc", nativeQuery = true)
    List<SysBorrowInstrument> findByUserId(Long userId);

    /**
     * 查询全部
     *
     * @return
     */
    @Query(value = "select new com.mafei.laboratory.system.entity.vo.BorrowInstrumentVo( " +
            "a.id,a.userId,a.instrumentId,b.userName,c.instrumentName,a.status,a.borrowStatus,a.comment,a.createTime) " +
            " from SysBorrowInstrument as a,SysUser as b,SysInstrument as c " +
            " where a.userId = b.userId and a.instrumentId = c.id and a.status in ('7','1','4','5','8','6') " +
            " order by a.status,a.createTime desc")
    List<BorrowInstrumentVo> myFindAll();

    /**
     * 获取审核列表
     *
     * @return
     */
    @Query(value = "select new com.mafei.laboratory.system.entity.vo.BorrowInstrumentVo( " +
            "a.id,a.userId,a.instrumentId,b.userName,c.instrumentName,a.status,a.borrowStatus,a.comment,a.createTime) " +
            " from SysBorrowInstrument as a,SysUser as b,SysInstrument as c " +
            " where a.userId = b.userId and a.instrumentId = c.id and a.status in ('7','1','4','5','6') " +
            " order by a.status,a.createTime desc")
    List<BorrowInstrumentVo> myFindAllReview();


    /**
     * 查询多个
     *
     * @param ids
     * @return
     */
    @Query(value = "SELECT * FROM sys_borrow_instrument WHERE id in ?1", nativeQuery = true)
    List<SysBorrowInstrument> queryByIds(Set<Long> ids);


    /**
     * 修改状态
     *
     * @param id
     * @param status
     */
    @Modifying
    @Query(value = "update sys_borrow_instrument set status = ?2 where id = ?1", nativeQuery = true)
    void updateStatus(Long id, String status);

    /**
     * 修改状态
     *
     * @param id
     * @param status
     */
    @Modifying
    @Query(value = "update sys_borrow_instrument set borrow_status = ?2 where id = ?1", nativeQuery = true)
    void updateBorrowStatus(Long id, String status);

    /**
     * 查找id
     *
     * @return
     */
    @Query(value = "select instrument_id from sys_borrow_instrument", nativeQuery = true)
    List<Long> findInstrumentId();


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

    /**
     * 统计特定状态的借用记录数量
     *
     * @param status 状态
     * @return 记录数量
     */
    Long countByBorrowStatus(String status);

    /**
     * 统计指定时间范围内的借用记录数量
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 借用记录数量
     */
    @Query(value = "SELECT COUNT(*) FROM sys_borrow_instrument WHERE create_time BETWEEN ?1 AND ?2", nativeQuery = true)
    long countByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 检查用户是否有有效的仪器预约
     */
    boolean existsByUserIdAndStatusAndBorrowStatus(Long userId, String status, String borrowStatus);

    /**
     * 检查用户是否有指定状态的仪器预约
     */
    boolean existsByUserIdAndBorrowStatus(Long userId, String borrowStatus);
}