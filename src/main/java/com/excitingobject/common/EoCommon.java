package com.excitingobject.common;

import com.excitingobject.common.api.message.MessageComponent;
import com.excitingobject.common.api.response.EoException;
import com.excitingobject.common.api.response.EoResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public abstract class EoCommon implements EoConstants {

    @Autowired
    protected MessageComponent _msg;

    protected boolean _isNull(Object data) {
        return data == null;
    }

    protected boolean _isEmpty(Object data) {
        if (!_isNull(data)) {
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

    protected void _checkRequired(Map<String, Object> params, String... keys) throws Exception {
        // TODO throw EoException
        if (_isEmpty(params)) {
            throw new NullPointerException();
        }
        for (String key : keys) {
            if (!params.containsKey(key) || _isEmpty(params.get(key))) {
                throw new NullPointerException(key);
            }
        }
    }

    protected void _throwException(EoResponseStatus status, String... args) throws EoException {
        throw new EoException(status, _msg.get(status, args));
    }
}

