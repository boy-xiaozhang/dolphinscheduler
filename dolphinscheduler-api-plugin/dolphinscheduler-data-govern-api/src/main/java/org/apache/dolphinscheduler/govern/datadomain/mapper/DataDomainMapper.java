package org.apache.dolphinscheduler.govern.datadomain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.dolphinscheduler.govern.datadomain.pojo.entity.DataDomain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataDomainMapper extends BaseMapper<DataDomain> {
}
