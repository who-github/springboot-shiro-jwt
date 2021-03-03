package com.woniuxy.mapper;

import com.woniuxy.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author clk
 * @since 2021-03-02
 */
public interface RoleMapper extends BaseMapper<Role> {

    // 根据用户的名称查询角色信息
    @Select("SELECT r.* " +
            "FROM t_user u " +
            "JOIN t_user_role ur " +
            "ON u.id = ur.uid " +
            "JOIN t_role r " +
            "ON r.id = ur.rid " +
            "WHERE u.username = #{username}")
    List<Role> findRoleByUsername(String username);

}
