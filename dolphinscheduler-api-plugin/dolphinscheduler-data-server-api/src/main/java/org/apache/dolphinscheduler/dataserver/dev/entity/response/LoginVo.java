package org.apache.dolphinscheduler.dataserver.dev.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录入参实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    private String username;
    private String password;
}
