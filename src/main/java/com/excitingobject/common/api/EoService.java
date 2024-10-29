package com.excitingobject.common.api;

import com.excitingobject.common.EoCommon;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class EoService<R> extends EoCommon {

    @Autowired
    protected R repository;
}
