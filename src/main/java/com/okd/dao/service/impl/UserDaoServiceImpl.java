package com.okd.dao.service.impl;

import static com.okd.Constant.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.okd.dao.service.UserDaoService;
import com.okd.entity.User;


@Repository
@Transactional
public class UserDaoServiceImpl implements UserDaoService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private HttpServletRequest request;
	

	@Override
	public User getUser(String username) {
		String hql = " from User u where u.name = :name";
		User found = (User) entityManager.createQuery(hql).setParameter("name", username).getSingleResult();
		HttpSession session = request.getSession(true);
		session.setAttribute(USER_SESSION_KEY, found);
		return found;
	}

}
