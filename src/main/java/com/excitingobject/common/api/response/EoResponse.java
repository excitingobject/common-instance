package com.excitingobject.common.api.response;

import java.util.Map;

@FunctionalInterface
public interface EoResponse {
    Map getDto(Map defaultDto);
}
