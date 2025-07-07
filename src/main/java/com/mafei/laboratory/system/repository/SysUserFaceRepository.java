package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysUserFace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysUserFaceRepository extends JpaRepository<SysUserFace, Long> {
    
    /**
     * 根据用户ID查找人脸信息
     */
    Optional<SysUserFace> findByUserId(Long userId);
    
    /**
     * 根据用户ID删除人脸信息
     */
    void deleteByUserId(Long userId);

    @Query("SELECT f FROM SysUserFace f WHERE f.status = 1")
    List<SysUserFace> findAllActiveFaces();
} 