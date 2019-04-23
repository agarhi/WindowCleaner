package com.okd.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="phrase")
public class Phrase implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="phraseid")
    private int phraseid;  
	
	@Column(name="content")
    private String content;
	
	
	@Column(name="created")
    private Date created;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "userid", nullable = false)
    private User user;

	public int getPhraseid() {
		return phraseid;
	}

	public void setPhraseid(int phraseid) {
		this.phraseid = phraseid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} 
	
	
	
	
	
}

