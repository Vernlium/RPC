package com.alibaba.middleware.race.rpc.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Title:
 * Create Time: 2015/7/31 ${Time}
 *
 * @version 1.0
 * @author: anan
 */
public class RpcContext
{
    public static Map<String, Object> props = new HashMap();

    public static void addProp(String key, Object value)
    {
        props.put(key, value);
    }

    public static Object getProp(String key) {
        return props.get(key);
    }

    public static Map<String, Object> getProps() {
        return Collections.unmodifiableMap(props);
    }
}
