package com.inetum.appliSpringWeb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class News {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	private String text;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	public News(Long id, String text,Date date) {
		super();
		this.id = id;
		this.text = text;
		this.date = date;
	}

	public News(Long id, String text) {
		this(id,text,new Date());
	}
	

}
