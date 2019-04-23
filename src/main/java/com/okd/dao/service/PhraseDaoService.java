package com.okd.dao.service;

import java.sql.Date;
import java.util.Set;

import com.okd.entity.User;

public interface PhraseDaoService {
	public void create(User user, String phrase);
	public Set<String> getAllByPeriod(Date today, int days);
}
