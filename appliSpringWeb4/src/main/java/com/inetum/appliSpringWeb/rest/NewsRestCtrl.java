package com.inetum.appliSpringWeb.rest;

import java.util.List;

import org.mycontrib.util.generic.rest.AbstractGenericRestCtrl;
import org.mycontrib.util.generic.service.GenericServiceWithDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.dto.NewsL0;
import com.inetum.appliSpringWeb.service.ServiceNews;

@RestController
@RequestMapping(value="/rest/api-news/news" , headers="Accept=application/json")
public class NewsRestCtrl extends AbstractGenericRestCtrl<Long,NewsL0>{
	
	@Autowired
	private ServiceNews serviceNews;
	
	@Override
	public GenericServiceWithDto<Long> getGenericServiceWithDto() {
		return serviceNews;
	}
	
	@Override
	public Class<NewsL0> getMainDtoClass() {
		return NewsL0.class;
	}
	
	//exemple de fin d'URL: ./api-devise/devise
	@GetMapping("" )
	public List<NewsL0> getDevises(){
		return serviceNews.searchAllDto(NewsL0.class);
	}
	
	@PostMapping("" ) 
	public NewsL0 postNews(@RequestBody NewsL0 nouvelleNews) {
		return serviceNews.saveNewFromDto(nouvelleNews);
	}
	
	@PutMapping("" ) 
	public NewsL0 putNews(@RequestBody NewsL0 newsToUpdate) {
		serviceNews.updateExistingFromDto(newsToUpdate);
		return  newsToUpdate;
	}
}
