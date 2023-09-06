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
		
		logger.debug("MyUserDetailsService.loadUserByUsername() called with username="+username);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String password=null;
		if(username.equals("james_Bond")) {
			password=passwordEncoder.encode("007");//simulation password ici
			authorities.add(new SimpleGrantedAuthority("ROLE_AGENTSECRET"));
		}
		else {
			try {
				//NB le username considéré comme potentiellement
				//égal à firstname_lastname
				String firstname = username.split("_")[0];
				String lastname = username.split("_")[1];
				
				List<Customer> customers = serviceCustomer.rechercherCustomerSelonPrenomEtNom(firstname,lastname);
				Customer firstCurstomer = customers.get(0);
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));//ou "ROLE_USER"
				//password=customer.getPassword(); déjà stocké en base en mode crypté
				password=passwordEncoder.encode(firstCurstomer.getPassword());
			} catch (NumberFormatException e) {
				//e.printStackTrace();
				System.err.println(e.getMessage());
				throw new UsernameNotFoundException(username + " not found by MyUserDetailsService");
			}
		}
		return new User(username, password, authorities);
	} 

}
