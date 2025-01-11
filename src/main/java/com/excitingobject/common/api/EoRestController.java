package com.excitingobject.common.api;

import com.excitingobject.common.EoCommon;
import com.excitingobject.common.api.response.EoException;
import com.excitingobject.common.api.response.EoResponseDto;
import com.excitingobject.common.api.response.EoResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@Slf4j
public abstract class EoRestController extends EoCommon {
    @ExceptionHandler(Exception.class)
    private Object responseException(Exception e) {
        log.error(e.getMessage(), e);
        if (e instanceof AccessDeniedException) {
            return response(EoResponseStatus.FORBIDDEN);
        } else if (e instanceof EmptyResultDataAccessException) {
            return response(EoResponseStatus.NOT_FOUND);
        }
        return response(EoResponseStatus.UNDEFINED);
    }

    @ExceptionHandler(EoException.class)
    private Object responseException(EoException e) {
        EoResponseStatus status = e.getStatus();
        return response(null, status.getCode(), e.getMessage(), status.getHttpStatus());
    }

    private ResponseEntity<EoResponseDto> response(EoResponseStatus status) {
        return response(null, status);
    }

    private ResponseEntity<EoResponseDto> response(Object data, EoResponseStatus status) {
        return response(data, status.getCode(), _msg.get(status), status.getHttpStatus());
    }

    private ResponseEntity<EoResponseDto> response(Object data, String code, String msg, HttpStatus httpStatus) {
        EoResponseDto res = new EoResponseDto(data, code, msg);
        return new ResponseEntity(res, httpStatus);
    }

    protected ResponseEntity<EoResponseDto> created(Object data) {
        return response(data, EoResponseStatus.CREATED);
    }

    protected ResponseEntity<EoResponseDto> success() {
        return success(null);
    }

    protected ResponseEntity<EoResponseDto> success(Object data) {
        return response(data, EoResponseStatus.SUCCESS);
    }

}
