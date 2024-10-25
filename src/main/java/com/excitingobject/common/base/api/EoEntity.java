package com.excitingobject.common.base.api;

import com.excitingobject.common.base.EoConstants;
import com.excitingobject.common.base.api.response.EoResponse;

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
