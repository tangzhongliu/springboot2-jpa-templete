-- 数据库
CREATE DATABASE IF NOT EXISTS test_db
DEFAULT CHARACTER SET utf8mb4;

-- 用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL  COMMENT '更新时间',
  `create_user` VARCHAR(18) NOT NULL  COMMENT '创建用户名',
  `update_user` VARCHAR(18) NOT NULL  COMMENT '更新用户名',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '数据状态(0:无效/1:有效[默认])',
  `version` INT(11) NOT NULL DEFAULT 0 COMMENT '版本锁（乐观锁）',
  `user_name` VARCHAR(18) NOT NULL  COMMENT '用户名',
  `user_password` VARCHAR(64) NOT NULL  COMMENT '用户密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL  COMMENT '更新时间',
  `create_user` VARCHAR(18) NOT NULL  COMMENT '创建用户名',
  `update_user` VARCHAR(18) NOT NULL  COMMENT '更新用户名',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '数据状态(0:无效/1:有效[默认])',
  `version` INT(11) NOT NULL DEFAULT 0 COMMENT '版本锁（乐观锁）',
  `role_name` VARCHAR(32) NOT NULL  COMMENT '角色名称',
  `role_remark` VARCHAR(64) NOT NULL  COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 菜单表
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL  COMMENT '更新时间',
  `create_user` VARCHAR(18) NOT NULL  COMMENT '创建用户名',
  `update_user` VARCHAR(18) NOT NULL  COMMENT '更新用户名',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '数据状态(0:无效/1:有效[默认])',
  `version` INT(11) NOT NULL DEFAULT 0 COMMENT '版本锁（乐观锁）',
  `menu_url` VARCHAR(32) NOT NULL  COMMENT '菜单url',
  `menu_path` VARCHAR(32) NOT NULL  COMMENT '菜单path',
  `menu_component` VARCHAR(32) NOT NULL  COMMENT '菜单组件',
  `menu_name` VARCHAR(32) NOT NULL  COMMENT '菜单名称',
  `menu_title` VARCHAR(32) NOT NULL  COMMENT '菜单标题',
  `menu_icon` VARCHAR(32) NOT NULL  COMMENT '菜单图标',
  `menu_parent_id` INT(11)  DEFAULT NULL COMMENT '菜单父ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 用户与角色关联表
DROP TABLE IF EXISTS `sys_user_role_rel`;
CREATE TABLE IF NOT EXISTS `sys_user_role_rel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL  COMMENT '更新时间',
  `create_user` VARCHAR(18) NOT NULL  COMMENT '创建用户名',
  `update_user` VARCHAR(18) NOT NULL  COMMENT '更新用户名',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '数据状态(0:无效/1:有效[默认])',
  `version` INT(11) NOT NULL DEFAULT 0 COMMENT '版本锁（乐观锁）',
  `user_id` INT(11) NOT NULL  COMMENT '用户ID',
  `role_id` INT(11) NOT NULL  COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色关联表';

-- 角色与菜单关联表
DROP TABLE IF EXISTS `sys_role_menu_rel`;
CREATE TABLE IF NOT EXISTS `sys_role_menu_rel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL  COMMENT '更新时间',
  `create_user` VARCHAR(18) NOT NULL  COMMENT '创建用户名',
  `update_user` VARCHAR(18) NOT NULL  COMMENT '更新用户名',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '数据状态(0:无效/1:有效[默认])',
  `version` INT(11) NOT NULL DEFAULT 0 COMMENT '版本锁（乐观锁）',
  `role_id` INT(11) NOT NULL  COMMENT '角色ID',
  `menu_id` INT(11) NOT NULL  COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单关联表';