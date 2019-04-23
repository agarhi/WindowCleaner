package com.okd.listener;

import javax.servlet.annotation.WebListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;


/**
 * This is required to inject HttpRequest so you can use @Autowired HttpRequest request and get session from it
 * Equivalent in XML:
 * <listener>
 	<listener-class>
    	org.springframework.web.context.request.RequestContextListener
 	</listener-class>
   </listener>
 * @author asif.garhi
 *
 */
@Configuration
@WebListener
public class MyRequestContextListener extends RequestContextListener{

}
