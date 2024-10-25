package com.excitingobject.common.base.api;

import com.excitingobject.common.base.EoCommon;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class EoService<R> extends EoCommon {

    @Autowired
    protected R repository;
}
