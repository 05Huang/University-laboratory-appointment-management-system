/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : laboratory

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 26/03/2021 14:15:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (1);

-- ----------------------------
-- Table structure for sys_borrow_instrument
-- ----------------------------
DROP TABLE IF EXISTS `sys_borrow_instrument`;
CREATE TABLE `sys_borrow_instrument`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '仪器id',
  `user_id` bigint(20) NOT NULL,
  `instrument_id` bigint(20) NOT NULL,
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0已审核，1未审核',
  `borrow_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0未归还，1已归还',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '借用时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '归还时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备借用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_borrow_instrument
-- ----------------------------
INSERT INTO `sys_borrow_instrument` VALUES (109, 103, 111, '8', '8', '阿萨飒飒', NULL, '2021-03-03 08:00:00', NULL, '2021-03-12 01:51:40');
INSERT INTO `sys_borrow_instrument` VALUES (110, 104, 110, '4', '4', '太贵了', NULL, '2021-03-06 08:00:00', NULL, '2021-03-12 05:11:08');
INSERT INTO `sys_borrow_instrument` VALUES (111, 103, 112, '4', '4', '理由不成立', NULL, '2021-03-10 08:00:00', NULL, '2021-03-12 15:03:08');

-- ----------------------------
-- Table structure for sys_borrow_laboratory
-- ----------------------------
DROP TABLE IF EXISTS `sys_borrow_laboratory`;
CREATE TABLE `sys_borrow_laboratory`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '仪器id',
  `user_id` bigint(20) NOT NULL,
  `laboratory_id` bigint(20) NOT NULL,
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0未归还，1已归还',
  `borrow_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0未归还，1已归还',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '借用时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '归还时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1009 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备借用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_borrow_laboratory
-- ----------------------------
INSERT INTO `sys_borrow_laboratory` VALUES (1009, 103, 1001, '1', '5', '预约测试', NULL, '2021-03-04 08:00:00', NULL, '2021-03-12 04:53:57');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '贵州理工学院', 0, '校长', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '大数据学院', 1, '校长', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '理学院', 2, '校长', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '材料学院', 1, '校长', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '土木学院', 2, '校长', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '环境学院', 3, '校长', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '航天航空学院', 4, '校长', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '城规学院', 5, '校长', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '设计学院', 1, '校长', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL);
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '其他', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL);

-- ----------------------------
-- Table structure for sys_instrument
-- ----------------------------
DROP TABLE IF EXISTS `sys_instrument`;
CREATE TABLE `sys_instrument`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '仪器id',
  `depot_id` bigint(20) NULL DEFAULT NULL,
  `instrument_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '名字',
  `model` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '型号',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '类型（0常规设备 1购入设备 2捐赠设备）',
  `status` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '（0代表存在 1代表启用）',
  `price` decimal(10, 3) NOT NULL COMMENT '仪器价格',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '购入时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 113 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仪器信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_instrument
-- ----------------------------
INSERT INTO `sys_instrument` VALUES (1, 100, '玻璃瓶', '1l', '0', '0', 12.000, 'admin', '2021-03-10 13:03:24', 'admin', '2021-03-10 13:03:32');
INSERT INTO `sys_instrument` VALUES (109, 100, '烧杯', '100ml', '0', '2', 24.000, 'admin', '2021-03-12 02:55:06', 'admin', '2021-03-12 02:55:16');
INSERT INTO `sys_instrument` VALUES (110, 101, '显微镜', 'XT-1001', '0', '0', 1000.000, 'admin', NULL, NULL, NULL);
INSERT INTO `sys_instrument` VALUES (111, 101, '台式电脑', 'rog', '0', '0', 5000.000, 'admin', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_instrument_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_instrument_position`;
CREATE TABLE `sys_instrument_position`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '仪器id',
  `instrument_id` bigint(20) NOT NULL,
  `position` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '地点名',
  `detail_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地点（箱号，柜号等）',
  `repair_position` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仪器位置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_instrument_position
-- ----------------------------
INSERT INTO `sys_instrument_position` VALUES (1, 1, '209一号柜', '1-2-1', NULL, '测试信息', 'admin', '2021-03-10 13:04:59', 'admin', '2021-03-10 13:05:05');

-- ----------------------------
-- Table structure for sys_instrument_repair
-- ----------------------------
DROP TABLE IF EXISTS `sys_instrument_repair`;
CREATE TABLE `sys_instrument_repair`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '仪器id',
  `instrument_id` bigint(20) NOT NULL,
  `status` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '（0代表存在 1代表启用）',
  `price` decimal(6, 3) NULL DEFAULT NULL,
  `repair_position` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '学校维修部',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '维修时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '预计修复时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仪器用途表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_instrument_repair
-- ----------------------------
INSERT INTO `sys_instrument_repair` VALUES (112, 109, NULL, 12.000, '贵阳', '维修测试', 'admin', '2021-03-02 08:00:00', NULL, '2021-03-03 08:00:00');

-- ----------------------------
-- Table structure for sys_instrument_use
-- ----------------------------
DROP TABLE IF EXISTS `sys_instrument_use`;
CREATE TABLE `sys_instrument_use`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '仪器id',
  `instrument_id` bigint(20) NOT NULL,
  `use_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用途描述',
  `use_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用途名',
  `model` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '用途类型（0化学 1物理 2计算机，3金工，4电路，5土木，7航天航空，8食品，9药物，10材料）',
  `status` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '（0代表存在 1代表启用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '购入时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仪器用途表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_instrument_use
-- ----------------------------
INSERT INTO `sys_instrument_use` VALUES (1, 109, '用于化学测试', '烧杯测量', '1l', '0', '0', 'admin', '2021-03-10 13:05:43', 'admin', '2021-03-10 13:05:48');
INSERT INTO `sys_instrument_use` VALUES (109, 1, '玻璃瓶的耐压测试', '材料测试', NULL, '0', NULL, 'admin', '2021-03-11 17:31:54', NULL, NULL);

-- ----------------------------
-- Table structure for sys_laboratory
-- ----------------------------
DROP TABLE IF EXISTS `sys_laboratory`;
CREATE TABLE `sys_laboratory`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '实验室ID',
  `laboratory_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `depot_id` bigint(20) NULL DEFAULT 0 COMMENT '所属部门',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `capacity` int(4) NULL DEFAULT 0 COMMENT '教室最大容纳人数',
  `target` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '所属实验类型',
  `use_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用途类型（0理论教室 1综合教室 2实验教室）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `admin` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '负责人',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1001 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实验室信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_laboratory
-- ----------------------------
INSERT INTO `sys_laboratory` VALUES (1, '物理实验室203', 106, 1, 33, '重力实验', '3', '0', NULL, NULL, '#', 'admin', '2021-03-10 09:06:31', 'admin', '2021-03-10 09:06:39', '1栋203');
INSERT INTO `sys_laboratory` VALUES (1001, '重力实验室201', 102, 2, 42, '物理实验', '3', '0', '/images/upload/a.jpg', NULL, 'admin', 'admin', '2021-03-12 04:42:00', 'admin', '2021-03-12 04:42:06', NULL);

-- ----------------------------
-- Table structure for sys_laboratory_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_laboratory_log`;
CREATE TABLE `sys_laboratory_log`  (
  `id` bigint(20) NOT NULL COMMENT '记录ID',
  `laboratory_id` bigint(20) NOT NULL COMMENT '实验室ID',
  `laboratory_status_id` bigint(20) NOT NULL COMMENT '状态ID',
  `user_id` bigint(20) NOT NULL COMMENT '预约人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_laboratory_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_laboratory_status
-- ----------------------------
DROP TABLE IF EXISTS `sys_laboratory_status`;
CREATE TABLE `sys_laboratory_status`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '实验室状态ID',
  `laboratory_id` bigint(20) NOT NULL COMMENT '实验室ID',
  `status` int(4) NOT NULL DEFAULT 0 COMMENT '0空闲，1未归还',
  `isDamage` int(4) NULL DEFAULT 10 COMMENT '10 未损坏，11损坏',
  `useCount` int(4) NULL DEFAULT 0 COMMENT '使用次数',
  `fix_time` datetime(0) NULL DEFAULT NULL COMMENT '预计恢复时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '原因',
  `is_damage` int(11) NULL DEFAULT NULL,
  `use_count` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实验室状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_laboratory_status
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '请求地址',
  `target` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `is_refresh` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否刷新（0刷新 1不刷新）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2000 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '业务管理', 0, 1, '#', '', 'M', '0', '1', '', 'mdi mdi-laptop-mac', 'admin', '2021-02-09 19:24:43', '', NULL, '简单的业务处理');
INSERT INTO `sys_menu` VALUES (2, '设备管理', 0, 2, '#', '', 'M', '0', '1', '', 'mdi mdi-beaker', 'admin', '2021-02-09 19:24:43', '', NULL, '实验设备管理');
INSERT INTO `sys_menu` VALUES (3, '实验室管理', 0, 3, '#', '', 'M', '0', '1', '', 'mdi mdi-home-assistant', 'admin', '2021-02-09 19:24:43', '', NULL, '实验室管理');
INSERT INTO `sys_menu` VALUES (4, '系统管理', 0, 4, '#', '', 'M', '0', '1', '', 'mdi mdi-settings', 'admin', '2021-02-09 19:24:43', '', NULL, '系统工具目录');
INSERT INTO `sys_menu` VALUES (5, '借用设备', 0, 1, '#', '', 'M', '0', '1', '', 'mdi mdi-beaker', 'admin', '2021-02-09 19:24:43', '', NULL, '借用设备');
INSERT INTO `sys_menu` VALUES (6, '预约实验室', 0, 1, '#', '', 'M', '0', '1', '', 'mdi mdi-home-assistant', 'admin', '2021-02-09 19:24:43', '', NULL, '预约实验室');
INSERT INTO `sys_menu` VALUES (100, '实验设备借用记录', 1, 3, '/process/borrowLog', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (101, '实验设备借用审核', 1, 1, '/process/checkApp', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (102, '实验室预约审核', 1, 2, '/process/checkLaboratory', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (103, '实验室预约记录', 1, 4, '/process/returnLog', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (104, '设备信息管理', 2, 1, '/apparatus/info', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (105, '设备位置管理', 2, 2, '/apparatus/position', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (106, '设备用途管理', 2, 3, '/apparatus/use', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (107, '设备维修管理', 2, 4, '/apparatus/repair', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (110, '实验室信息', 3, 2, '/laboratory/info', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (111, '信息导入', 3, 3, '/laboratory/import', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (112, '用户管理', 4, 1, '/system/user', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (113, '角色管理', 4, 2, '/system/role', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (114, '菜单管理', 4, 3, '/system/menu', '', 'C', '0', '1', 'system:user:view', '', 'admin', '2021-02-09 19:24:43', '', NULL, '设备借用记录');
INSERT INTO `sys_menu` VALUES (115, '预约实验室', 6, 1, '/borrow/laboratory', '', 'C', '0', '1', '', '', 'admin', '2021-02-09 19:24:43', '', NULL, '预约实验室');
INSERT INTO `sys_menu` VALUES (116, '设备借用', 5, 1, '/borrow/apparatus', '', 'C', '0', '1', '', '', 'admin', '2021-02-09 19:24:43', '', NULL, '预约实验室');
INSERT INTO `sys_menu` VALUES (117, '人脸录入', 4, 4, '/system/face_register', '', 'C', '0', '1', 'system:user:view', 'mdi mdi-face-recognition', 'admin', '2021-02-09 19:24:43', '', NULL, '教师人脸信息录入');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '老师', 'teacher', 2, '2', '0', '0', 'admin', '2021-02-09 19:24:43', '', NULL, '普通角色');
INSERT INTO `sys_role` VALUES (3, '学生', 'student', 3, '1', '0', '0', 'admin', '2021-02-19 23:00:55', '', NULL, '学生');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 100);
INSERT INTO `sys_role_menu` VALUES (1, 101);
INSERT INTO `sys_role_menu` VALUES (1, 102);
INSERT INTO `sys_role_menu` VALUES (1, 103);
INSERT INTO `sys_role_menu` VALUES (1, 104);
INSERT INTO `sys_role_menu` VALUES (1, 105);
INSERT INTO `sys_role_menu` VALUES (1, 106);
INSERT INTO `sys_role_menu` VALUES (1, 107);
INSERT INTO `sys_role_menu` VALUES (1, 108);
INSERT INTO `sys_role_menu` VALUES (1, 109);
INSERT INTO `sys_role_menu` VALUES (1, 110);
INSERT INTO `sys_role_menu` VALUES (1, 111);
INSERT INTO `sys_role_menu` VALUES (1, 112);
INSERT INTO `sys_role_menu` VALUES (1, 113);
INSERT INTO `sys_role_menu` VALUES (1, 114);
INSERT INTO `sys_role_menu` VALUES (1, 115);
INSERT INTO `sys_role_menu` VALUES (1, 116);
INSERT INTO `sys_role_menu` VALUES (1, 117);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 115);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 117);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (3, 5);
INSERT INTO `sys_role_menu` VALUES (3, 116);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户 01注册用户）',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' COMMENT '头像路径',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '盐加密',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `pwd_update_date` datetime(0) NULL DEFAULT NULL COMMENT '密码最后更新时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 100, '2017250082', '张主任', '00', 'ry@163.com', '15888888888', '1', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '123456', '111111', '0', '0', '127.0.0.1', '2021-02-09 20:08:23', '2021-02-09 19:24:43', 'admin', '2021-02-09 19:24:43', '', '2021-02-09 20:08:22', '管理员');
INSERT INTO `sys_user` VALUES (103, 103, '2017250083', '刘老师', '00', '1643607621@qq.com', '15085250704', '0', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '123456', '222222', '0', '0', '127.0.0.1', '2021-03-09 17:57:18', '2021-03-09 17:57:22', 'admin', '2021-03-09 17:57:28', NULL, '2021-03-09 17:57:33', 'admin');
INSERT INTO `sys_user` VALUES (104, 103, '2017250084', '李同学', '00', 'ry@163.com', '15888888888', '1', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '123456', '111111', '0', '0', '127.0.0.1', '2021-02-09 20:08:23', '2021-02-09 19:24:43', 'admin', '2021-02-09 19:24:43', '', '2021-02-09 20:08:22', '管理员');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (103, 2);
INSERT INTO `sys_user_role` VALUES (104, 3);

SET FOREIGN_KEY_CHECKS = 1;
