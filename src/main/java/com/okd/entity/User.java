package com.okd.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="userid")
    private int userid;  
	
	@Column(name="name")
    private String name;
	
	@Column(name="password")
    private String password;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private Set<Phrase> phrases;
	
	public int getUserid() {
		return userid;
	}
	
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Phrase> getPhrases() {
		return phrases;
	}

	public void setPhrases(Set<Phrase> phrases) {
		this.phrases = phrases;
	}
	
}
