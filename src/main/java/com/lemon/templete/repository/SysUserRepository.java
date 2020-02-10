package com.lemon.templete.repository;

import com.lemon.templete.domain.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * 描述: 用户Repository
 * @author 汤中流
 * @date 2019/07/12
 */
public interface SysUserRepository extends BaseRepository<SysUser> {
    /**
     * 描述: 通过名字相等查询，参数为userName
     * 相当于: select u from sys_user u where u.userName = ?1
     * @param userName
     * @return
     */
    SysUser findByUserName(String userName);

    /**
     * 描述: 通过名字like查询，参数为userName
     * 相当于: select u from sys_user u where u.userName like ?1
     * @param userName
     * @return
     */
    List<SysUser> findByUserNameLike(String userName);

    /**
     * 描述: 通过主键id集合查询，参数为id集合
     * 相当于: select u from sys_user u where u.id in (?, ?, ?)
     * @param ids
     * @return
     */
    List<SysUser> findByIdIn(Collection<Integer> ids);

    /**
     * 描述: 通过关联表[角色表]的角色名作为查询key，查询
     * 相当于: select u from sys_user u, sys_user_role_rel rel, sys_role r where u.user_id = rel.user_id and rel.role_id = r.role_id and r.role_name = ?
     * @param roleName
     * @return
     */
    List<SysUser> findBySysRolesRoleName(String roleName);

    /**
     * JPQL查询
     * findByUserNameAndStatusWithJPQL("t", 1, new PageRequest(1, 10, Sort.Direction.DESC, "createTime"))
     * @param userName
     * @param status
     * @param pageable
     * @return
     */
    @Query(value = "select u from SysUser u where u.userName like %:userName% and status = :status")
    Page<SysUser> findByUserNameAndStatusWithJPQL(@Param("userName") String userName, @Param("status") Integer status, Pageable pageable);

    /**
     * 原生SQL查询
     * findByUserNameAndStatusWithSQL("t", 1, new PageRequest(1, 10, Sort.Direction.DESC, "create_time"))
     * @param userName
     * @param status
     * @param pageable
     * @return
     */
    @Query(value = "select * from sys_user where user_name like %?1% and status = ?2",
            countQuery = "select count(*) from sys_user where user_name like %?1% and status = ?2", nativeQuery = true)
    Page<SysUser> findByUserNameAndStatusWithSQL(String userName, Integer status, Pageable pageable);
}
