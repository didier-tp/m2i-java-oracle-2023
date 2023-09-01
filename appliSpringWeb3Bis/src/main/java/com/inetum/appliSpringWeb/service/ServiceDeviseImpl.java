package com.inetum.appliSpringWeb.service;

import org.mycontrib.util.generic.service.AbstractGenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inetum.appliSpringWeb.dao.DaoDevise;
import com.inetum.appliSpringWeb.entity.Devise;

@Service
@Transactional
public class ServiceDeviseImpl 
    extends AbstractGenericService<Devise, String>
    implements ServiceDevise {
	
	@Override
	public CrudRepository<Devise, String> getMainDao() {
		return this.daoDevise;
	}
	

	
	Logger logger = LoggerFactory.getLogger(ServiceDeviseImpl.class);
	
	
	@Autowired
	private DaoDevise daoDevise; //dao principal


	@Override
	public void initEntityRelationShipsFromDtoBeforeSave(Devise entity, Object dto) {
	}
	
}
