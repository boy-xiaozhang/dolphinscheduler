package org.apache.dolphinscheduler.dataserver.dev.utils;

import org.apache.dolphinscheduler.dataserver.dev.RocketAPIApplication;


public class PackageUtils {
    public static String getVersion() {
        Package pkg = RocketAPIApplication.class.getPackage();
        return (pkg != null ? pkg.getImplementationVersion() : null);
    }
}
