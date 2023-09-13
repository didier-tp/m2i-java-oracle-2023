package com.inetum.appliSpringWeb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inetum.appliSpringWeb.entity.News;

public interface DaoNews extends JpaRepository<News,Long>{

}
