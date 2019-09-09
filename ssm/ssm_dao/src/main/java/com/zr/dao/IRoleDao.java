package com.zr.dao;

import com.zr.domain.Permission;
import com.zr.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IRoleDao {

    /**
     * 根据用户id查询出所有对应的角色
     */
    @Select("select * from role where id in (select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            @Result(property = "permissions",column = "id",
                    javaType = java.util.List.class,
                    many = @Many(select = "com.zr.dao.IPermissionDao.findPermissionByRoleId"))
    })
    List<Role> findRoleByUserId(String userId) throws Exception;

    /**
     * 查询所有角色
      * @return
     * @throws Exception
     */
    @Select("select * from role")
    List<Role> findAll() throws Exception;

    /**
     * 添加用户角色
     * @param role
     */
    @Insert("insert into role(roleName, roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role) throws Exception;

    /**
     * 根据角色id查询角色
     * @param roleId
     * @return
     * @throws Exception
     */
    @Select("select * from role where id=#{roleId}")
    Role findById(String roleId) throws Exception;

    /**
     * 根据角色id查询角色中没有的权限
     * @param roleId
     * @return
     */
    @Select("select * from permission where id not in (select permissionId from role_permission where roleId = #{roleId})")
    List<Permission> findOtherPermission(String roleId) throws Exception;

    /**
     * 给角色添加权限
     * @param roleId
     * @param permissionIds
     * @throws Exception
     */
    @Insert("insert into role_permission(roleId, permissionId) values(#{roleId}, #{permissionIds})")
    void addPermissionToRole(@Param("roleId") String roleId, @Param("permissionIds") String[] permissionIds) throws Exception;
}
