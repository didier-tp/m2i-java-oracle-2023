package org.mycontrib.mysecurity.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;


public class MyAppGlobalUserDetailsConfigHelper  {
   
    private static DataSource realmDataSource;
    
    private static void initRealmDataSource(DataSourceProperties dsProps) {
    	DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(dsProps.getDriverClassName());
    	driverManagerDataSource.setUrl(dsProps.getUrl());
    	driverManagerDataSource.setUsername(dsProps.getUsername());
    	driverManagerDataSource.setPassword(dsProps.getPassword());
    	realmDataSource = driverManagerDataSource;
    }
    
    private boolean isRealmSchemaInitialized() {
    	int nbExistingTablesOfRealmSchema = 0;
    	try {
			Connection cn = realmDataSource.getConnection();
			DatabaseMetaData meta = cn.getMetaData(); 
			String tabOfTableType[] = {"TABLE"};
			ResultSet rs = meta.getTables(null,null,"%",tabOfTableType);
			while(rs.next()){ 
				String existingTableName = rs.getString(3);
				if(existingTableName.equalsIgnoreCase("users")
				    || existingTableName.equalsIgnoreCase("authorities")) {
					nbExistingTablesOfRealmSchema++;
				}
			}
			rs.close(); 
			cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return (nbExistingTablesOfRealmSchema>=2);
    }
	

    public UserDetailsManagerConfigurer initJdbcGlobalUserDetails(final AuthenticationManagerBuilder auth,
    		   MySecuritySimpleConfigurer mySecuritySimpleConfigurer,DataSourceProperties dsProps) throws Exception {
		initRealmDataSource(dsProps);
		JdbcUserDetailsManagerConfigurer jdbcUserDetailsManagerConfigurer = 
				auth.jdbcAuthentication()
		        .dataSource(realmDataSource);
		if(isRealmSchemaInitialized()) {
			/*
			 jdbcUserDetailsManagerConfigurer
			.usersByUsernameQuery("select username,password, enabled from users where username=?")
	    	.authoritiesByUsernameQuery("select username, authority from authorities where username=?");
	    	//by default
	    	*/
	    	// or .authoritiesByUsernameQuery("select username, role from user_roles where username=?") if custom schema
		}else {
			//creating default schema and default tables "users" , "authorities"
			jdbcUserDetailsManagerConfigurer.withDefaultSchema();
			//inserting default users:
			mySecuritySimpleConfigurer.configureDefaultUsers(jdbcUserDetailsManagerConfigurer);
		}
		return jdbcUserDetailsManagerConfigurer;
    }
    
    public UserDetailsManagerConfigurer initInMemoryGlobalUserDetails(final AuthenticationManagerBuilder auth,
    		                 MySecuritySimpleConfigurer mySecuritySimpleConfigurer)throws Exception {
    	UserDetailsManagerConfigurer udmc  = auth.inMemoryAuthentication();
    	mySecuritySimpleConfigurer.configureDefaultUsers(udmc);
    	return udmc;
    }
}
