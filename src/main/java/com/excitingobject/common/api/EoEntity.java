package com.excitingobject.common.api;

import com.excitingobject.common.EoConstants;
import com.excitingobject.common.api.response.EoResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class EoEntity implements EoConstants {

    private EoResponse responseDto = null;

    public void setResponseDto(EoResponse responseDto) { this.responseDto = responseDto; }

    protected abstract void initDefaultDto(Map dto);

    public Object getResponseDto() {
        if (responseDto != null) {
            return responseDto.getDto(getDefaultDto());
        }
        return getDefaultDto();
    }

    public Map getDefaultDto() {
        Map dto = new HashMap();
        this.initDefaultDto(dto);
        return dto;
    }
}
