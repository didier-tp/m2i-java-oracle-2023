package com.inetum.appliSpring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.inetum.appliSpring.tp" , "com.inetum.appliSpring.tp2" })
//@ComponentScan demande à scruter tous les packages en dessous de .tp
//pour trouver des classes à prendre en charge (avec @Component, @...)
public class MySpringConfig {
    //...
}
