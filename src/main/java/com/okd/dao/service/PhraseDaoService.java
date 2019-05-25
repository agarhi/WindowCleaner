package com.okd.dao.service;

import java.sql.Date;
import java.util.Set;

import com.okd.entity.Phrase;
import com.okd.entity.User;

public interface PhraseDaoService {
	public void create(User user, String phrase);
	
	/**
	 * Bulk Phrase creation, mainly for asynchronous redis write-back
	 * @param phrases
	 */
	public void create(Set<Phrase> phrases);
	public Set<String> getAllByPeriod(Date today, int days);
}
