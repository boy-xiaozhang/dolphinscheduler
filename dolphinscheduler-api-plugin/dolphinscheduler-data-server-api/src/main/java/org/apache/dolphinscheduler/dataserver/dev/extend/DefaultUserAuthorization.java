package org.apache.dolphinscheduler.dataserver.dev.extend;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 默认用户授权实现类
 */

@Component
public class DefaultUserAuthorization implements IUserAuthorization {

    @Override
    public String validate(String username, String password) {
        return StringUtils.isEmpty(username)?"admin":username;
    }
}
