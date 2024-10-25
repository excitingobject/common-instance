package com.excitingobject.common.base.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EoRepository<Entity, Key> extends JpaRepository<Entity, Key>, JpaSpecificationExecutor<Entity> {

}
