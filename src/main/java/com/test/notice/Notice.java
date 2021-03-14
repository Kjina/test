package com.test.notice;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity@Setter@Getter@ToString
public class Notice {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "title")
	private String title;
	@Column(name = "register")
	private String register;
	@Column(name = "content")
	private String content;
	@Column(name = "regDt")
	private Date regDt;
	@Column(name = "lastRegDt")
	private Date lastRegDt;
}