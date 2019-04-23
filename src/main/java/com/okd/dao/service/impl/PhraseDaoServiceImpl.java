package com.okd.dao.service.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okd.dao.service.PhraseDaoService;
import com.okd.dao.service.UserDaoService;
import com.okd.entity.Phrase;
import com.okd.entity.User;

@Service
public class PhraseDaoServiceImpl implements PhraseDaoService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	UserDaoService userDaoService;

	@Override
	@Transactional
	public void create(User user, String phrase) {
		User loaded = userDaoService.getUser(user.getName());
		Phrase p = new Phrase();
		p.setContent(phrase);
		p.setUser(loaded);
		p.setCreated(new Date(Calendar.getInstance().getTime().getTime()));
		entityManager.persist(p);
		
	}

	@Override
	public Set<String> getAllByPeriod(Date today, int days) {
		Date previous = subtractDays(today, days);
		String hql = "Select content from Phrase p where p.created between :from and :to";
		return convert(entityManager.createQuery(hql).setParameter("from", previous)
        .setParameter("to", today).getResultList());
	}
	
	

    private Set<String> convert(List l) {
    	Set<String> strings = new HashSet<>();
    	for(Object o : l) {
    		strings.add(o.toString());
    	}
    	return strings;
	}

	private Date subtractDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return new Date(c.getTimeInMillis());
    }


}
