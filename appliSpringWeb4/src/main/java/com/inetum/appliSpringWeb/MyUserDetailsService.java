package com.inetum.appliSpringWeb;

import java.util.ArrayList;
import java.util.List;

import org.mycontrib.mysecurity.realm.config.MySecurityExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.service.ServiceCompteImpl;
import com.inetum.appliSpringWeb.service.ServiceCustomer;

@Profile("withSecurity")
//@Service(MySecurityExtension.MY_EXCLUSIVE_USERDETAILSSERVICE_NAME)
@Service(MySecurityExtension.MY_ADDITIONAL_USERDETAILSSERVICE_NAME)
public class MyUserDetailsService implements UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
	
	
	@Autowired
    private PasswordEncoder passwordEncoder;
   
	
	@Autowired
	private ServiceCustomer serviceCustomer;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails=null;
		logger.debug("MyUserDetailsService.loadUserByUsername() called with username="+username);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String password=null;
		if(username.equals("james_Bond")) {
			password=passwordEncoder.encode("007");//simulation password ici
			authorities.add(new SimpleGrantedAuthority("ROLE_AGENTSECRET"));
			userDetails = new User(username, password, authorities);
		}
		else {
			//NB le username considéré comme potentiellement
			//égal à firstname_lastname
			try {
				String firstname = username.split("_")[0];
				String lastname = username.split("_")[1];
					
				List<Customer> customers = serviceCustomer.rechercherCustomerSelonPrenomEtNom(firstname,lastname);
				if(!customers.isEmpty()) {
					Customer firstCustomer = customers.get(0);
					authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));//ou "ROLE_USER" ou "ROLE_ADMIN"
						//password=customer.getPassword(); déjà stocké en base en mode crypté
					password=passwordEncoder.encode(firstCustomer.getPassword());
					userDetails = new User(username, password, authorities);
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		
		
		if(userDetails==null) {
			//NB: il est important de remonter UsernameNotFoundException (mais pas null , ni une autre exception)
			//si l'on souhaite qu'en cas d'échec avec cet AuthenticationManager
			//un éventuel AuthenticationManager parent soit utilisé en plan B
			throw new UsernameNotFoundException(username + " not found by MyUserDetailsService");
		}
			
		return userDetails;
	} 

}
