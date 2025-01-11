package com.excitingobject.common.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EoRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
