package com.excitingobject.common.base.api.response;

import org.springframework.http.HttpStatus;

public enum EoResponseStatus {
        SUCCESS("200.000000", HttpStatus.OK),
        CREATED("201.000000", HttpStatus.CREATED),

        BAD_REQUEST("400.000000", HttpStatus.BAD_REQUEST),

        UNAUTHORIZED("401.000000", HttpStatus.UNAUTHORIZED),
        FAILED_SIGN_IN("401.000001", HttpStatus.UNAUTHORIZED),

        FORBIDDEN("403.000000", HttpStatus.FORBIDDEN),

        NOT_FOUND("404.000000", HttpStatus.NOT_FOUND),

        CONFLICT("409.000000", HttpStatus.CONFLICT),
        ALREADY_EXISTS("409.000001", HttpStatus.CONFLICT),

        UNDEFINED("500.000000", HttpStatus.INTERNAL_SERVER_ERROR);

        private String code;
        private HttpStatus httpStatus;

        EoResponseStatus(String code, HttpStatus httpStatus) {
            this.code = code;
            this.httpStatus = httpStatus;
        }

        public String getCode() {
            return code;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }