package com.excitingobject.common.api;

import com.excitingobject.common.EoCommon;
import com.excitingobject.common.api.response.EoResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class EoService<T extends EoEntity, ID> extends EoCommon {

    @Autowired
    protected EoRepository<T, ID> repository;

    public abstract T newEntityByData(Map<String, Object> data) throws Exception;
    public abstract T setDataToEntity(T entity, Map<String, Object> data) throws Exception;

    protected T createOne(Map<String, Object> data) throws Exception {
        T entity = newEntityByData(data);
        return repository.save(entity);
    }

    protected T readOne(ID id) throws Exception {
        if (!repository.existsById(id)) {
            _throwException(EoResponseStatus.NO_DATA_FOR_ID, id.toString());
        }
        return repository.getReferenceById(id);
    }

    protected T updateOne(ID id, Map<String, Object> data) throws Exception {
        T entity = this.setDataToEntity(readOne(id), data);
        repository.save(entity);
        return entity;
    }

    protected void deleteOne(ID id) throws Exception {
        T entity = readOne(id);
        repository.delete(entity);
    }

}
