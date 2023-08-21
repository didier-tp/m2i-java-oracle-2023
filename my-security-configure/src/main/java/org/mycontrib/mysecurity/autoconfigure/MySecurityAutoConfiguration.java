package org.mycontrib.mysecurity.autoconfigure;

import org.mycontrib.mysecurity.config.WebSecurityRecentConfig;
import org.mycontrib.mysecurity.config.WithoutSecurityRecentConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
/*
NB: cette classe est référencée dans le fichier
META-INF/spring.factories (de src/main/resources)
org.springframework.boot.autoconfigure.EnableAutoConfiguration=org.mycontrib.mysecurity.autoconfigure.MySecurityAutoConfiguration
(ou bien = AutoConfig1,...,AutoConfigN
selon les spécifications suivantes:
https://docs.spring.io/spring-boot/docs/2.1.18.RELEASE/reference/html/boot-features-developing-auto-configuration.html
*/
@Configuration
@ComponentScan({ "org.mycontrib.mysecurity.config" , "org.mycontrib.mysecurity.util" , "org.mycontrib.mysecurity.rest"})
public class MySecurityAutoConfiguration {

}
