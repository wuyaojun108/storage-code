package org.wyj.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.wyj.blog.dao.pojo.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("SELECT max(id) FROM ms_sys_user")
    Long getMaxId();
}
