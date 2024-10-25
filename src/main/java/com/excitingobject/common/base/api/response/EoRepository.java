package com.excitingobject.common.base.api.response;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EoRepository<E, K> extends JpaRepository<E, K>, JpaSpecificationExecutor<E> {
}
