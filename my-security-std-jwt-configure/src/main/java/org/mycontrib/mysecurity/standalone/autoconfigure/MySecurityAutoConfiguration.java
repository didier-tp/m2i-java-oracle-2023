package org.mycontrib.mysecurity.standalone.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/*
NB: cette classe est référencée dans le fichier
META-INF/spring.factories (de src/main/resources)
org.springframework.boot.autoconfigure.EnableAutoConfiguration=org.mycontrib.mysecurity.autoconfigure.MySecurityAutoConfiguration
(ou bien = AutoConfig1,...,AutoConfigN
selon les spécifications suivantes:
https://docs.spring.io/spring-boot/docs/2.1.18.RELEASE/reference/html/boot-features-developing-auto-configuration.html
*/
@Configuration
@ComponentScan({ "org.mycontrib.mysecurity.standalone.config" , 
	             "org.mycontrib.mysecurity.standalone.util" , 
	             "org.mycontrib.mysecurity.jwt.util" ,
	             "org.mycontrib.mysecurity.standalone.rest",
	             "org.mycontrib.mysecurity.common"})
public class MySecurityAutoConfiguration {

}
