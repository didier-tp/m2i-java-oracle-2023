package com.inetum.appliSpringWeb.dto;

import java.util.Date;

import org.mycontrib.util.generic.dto.WithId;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO = Data Transfert Object
 * NewsL0 sera une représentation partielle simplifiée et adaptée
 * de entity.News
 * 
 * CompteL0 ici en version "Basic"
 */

//@Getter @Setter @NoArgsConstructor @ToString
@Data @NoArgsConstructor
public class NewsL0 implements WithId<Long> {

    private Long id;
	private String text;
	private String date;
	
	public NewsL0(Long id, String text, String date) {
		super();
		this.id = id;
		this.text = text;
		this.date = date;
	}
}
