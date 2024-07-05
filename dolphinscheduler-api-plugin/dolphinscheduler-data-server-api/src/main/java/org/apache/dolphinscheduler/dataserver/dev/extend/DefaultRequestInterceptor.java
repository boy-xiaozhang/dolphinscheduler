package org.apache.dolphinscheduler.dataserver.dev.extend;

import org.apache.dolphinscheduler.dataserver.dev.entity.ApiInfo;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiParams;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 默认结果包装类
 */
@Component
public class DefaultRequestInterceptor implements IRequestInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, ApiInfo apiInfo, ApiParams apiParams) {
        return true;
    }
}
