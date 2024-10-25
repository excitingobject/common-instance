package com.excitingobject.common.base.api;

import com.excitingobject.common.base.EoCommon;
import com.excitingobject.common.base.api.response.EoResponseDto;
import com.excitingobject.common.base.api.response.EoResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class EoRestController extends EoCommon {

    @ExceptionHandler(Exception.class)
    public Object initException(Exception e) {
        return response(e);
    }

    private Object response(Exception e) {
        return e;
    }

    private ResponseEntity<EoResponseDto> response(EoResponseDto res, HttpStatus httpStatus) {
        return new ResponseEntity(res, httpStatus);
    }
}
