package com.excitingobject.common;

import java.util.Collection;
import java.util.Map;

public abstract class EoCommon implements EoConstants {
    public static boolean isNull(Object data) {
        return data == null;
    }

    public static boolean isEmpty(Object data) {
        if (!isNull(data)) {
            try {
                if (data instanceof String) {
                    return data.toString().trim().equals("");
                } else if (data instanceof Collection) {
                    return ((Collection) data).isEmpty();
                } else if (data instanceof Map) {
                    return ((Map) data).isEmpty();
                } else if (data instanceof Object[]) {
                    return (((Object[]) data).length < 1);
                }
                return false;
            } catch (Exception e) {
            }
        }
        return true;
    }

    public static void checkRequired(Map<String, Object> params, String... keys) throws Exception {
        // TODO throw EoException
        if (isEmpty(params)) {
            throw new NullPointerException();
        }
        for (String key : keys) {
            if (!params.containsKey(key) || isEmpty(params.get(key))) {
                throw new NullPointerException(key);
            }
        }
    }
}

