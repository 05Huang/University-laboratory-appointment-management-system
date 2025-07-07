package com.mafei.laboratory.system.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author: HuangXuan
 * @CreateTime: 2025-07-07
 * @Description:
 * @email hxtxyydsa@gmail.com; 3441578327@qq.com
 * @Version: 1.0
 */


@Entity
@Table(name = "sys_user_face")
@Data
@DynamicInsert
@DynamicUpdate
public class SysUserFace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "face_image_path", nullable = false)
    private String faceImagePath;

    @Column(name = "status")
    private Integer status = 1;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
