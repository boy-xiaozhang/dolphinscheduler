package org.apache.dolphinscheduler.dataserver.dev.function;

import org.apache.dolphinscheduler.dataserver.dev.extend.ApiInfoContent;
import org.apache.dolphinscheduler.dataserver.dev.extend.IApiPager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 分页封装函数
 * @Deprecated  已将方法迁移到 @Link DbFunciton.db.pager(Long total,List list)
 */
@Component
@Deprecated
public class PagerFunction implements IFunction{

    @Autowired
    private IApiPager pager;

    @Autowired
    private ApiInfoContent apiInfoContent;

    @Override
    public String getVarName() {
        return "Pager";
    }

    public Object build(Long total, List<Map<String,Object>> list){
        return pager.buildPager(total,list,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams());
    }
}
