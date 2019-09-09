package com.zr.dao;

import com.zr.domain.Member;
import org.apache.ibatis.annotations.Select;

/**
 * 查询会员信息
 */
public interface IMemberDao {

    @Select("select * from member where id=#{id}")
    Member findById(String id) throws Exception;

}
