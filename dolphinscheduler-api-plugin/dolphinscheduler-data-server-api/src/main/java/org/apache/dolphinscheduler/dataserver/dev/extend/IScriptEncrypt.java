package org.apache.dolphinscheduler.dataserver.dev.extend;

/**
 * 代码加解密抽象接口
 */
public interface IScriptEncrypt {
    String encrypt(String source) throws Exception;
    String decrypt(String encrypt) throws Exception;
}
