package org.apache.dolphinscheduler.dataserver.dev.extend;

import org.apache.dolphinscheduler.dataserver.dev.entity.ApiInfo;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiParams;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRequestInterceptor {
    public abstract boolean preHandle(HttpServletRequest request, HttpServletResponse response, ApiInfo apiInfo, ApiParams apiParams);
}
