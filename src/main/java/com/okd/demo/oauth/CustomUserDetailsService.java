package com.okd.demo.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.okd.dao.service.UserDaoService;
import com.okd.entity.User;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private UserDaoService userDaoService;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	User user = userDaoService.getUser(username);
    	logger.debug("user name with logger "+logger.getName() + " - "+user.getName());
    	
        return new UserDetails() {
			        	
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public String getUsername() {
				return user.getName();
			}
			
			@Override
			public String getPassword() {
				return user.getPassword();
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				List<GrantedAuthority> lga = new ArrayList<>();
				lga.add(new GrantedAuthority() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public String getAuthority() {
						// TODO Auto-generated method stub
						return "HE_CAN_DO_ANYTHING";
					}
				});
				return lga;
			}
		};
    }
}
