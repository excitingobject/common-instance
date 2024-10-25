package com.excitingobject.common.base.api.response;

import java.util.Map;

@FunctionalInterface
public interface EoResponse {
    Map getDto(Map defaultDto);
}
