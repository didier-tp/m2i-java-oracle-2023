package com.inetum.appliSpringWeb.service;

import org.mycontrib.util.generic.service.GenericService;

import com.inetum.appliSpringWeb.entity.News;

//Business service / service métier
//avec remontées d'exceptions (héritant de RuntimeException)
public interface ServiceNews extends GenericService<News,Long> {
	
}
