package com.excitingobject.common.base.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EoException extends Exception {
    private EoResponseStatus status = EoResponseStatus.UNDEFINED;

    public EoException(Exception e) {
        super(e.getMessage());
        try {
            // TODO Exception 예외 처리
        } catch (Exception ex) {
        }
    }

    public EoException(EoResponseStatus status) {
        super();
        this.status = status;
    }

    public EoException(EoResponseStatus status, String message) {
        super(message);
        this.status = status;
    }
}
