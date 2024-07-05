package org.apache.dolphinscheduler.dataserver.dev.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceProperty {
    private String name;
    private String factoryClassName;
    private Map<String,Object> config;
}
