package org.apache.dolphinscheduler.dataserver.dev.script;

import org.apache.dolphinscheduler.dataserver.dev.entity.ApiInfo;
import org.apache.dolphinscheduler.dataserver.dev.entity.ApiParams;

import javax.script.Bindings;

/**
 * 脚本执行器接口，实现此接口可自定义脚本执行引擎
 */
public interface IScriptParse {
    public Object runScript(String script, ApiInfo apiInfo, ApiParams apiParams) throws Throwable;

    public Object engineEval(String script, Bindings bindings) throws Throwable;
}
