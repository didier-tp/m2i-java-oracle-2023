Authentication = interface avec .getPrincipal() , .getCredentials() , .getDetails()
.getAuthorities() , .isAuthenticated() , ...
Principales classes d'implémentation: UsernamePasswordAuthenticationToken (avec username et password)
     OpenIDAuthenticationToken , ...


AuthenticationManager = interface avec .authenticate(Authentication)
La classe "ProviderManager" implémente l'interface AuthenticationManager
en itèrant sur une liste de AuthenticationProvider enregistrés de façon 
à trouver le premier AuthenticationProvider capable de gérer l'authentification

A priori , Le "ProviderManager" principal (lié à l'implémentation de AuthenticationManager)
est potentiellement relié à un ProviderManager parent

Le lien s'effectue via AuthenticationManagerBuilder.parentAuthenticationManager()

AuthenticationProvider = interface avec .authenticate(Authentication) et 
.supports(.class implementant l'interface Authentication)

Principales classes implémentant l'interface  AuthenticationProvider :
* LdapAuthenticationProvider
* OpenIDAuthenticationProvider
* DaoAuthenticationProvider 
   (avec une implémentation UserDetailsService en interne : ex Jdbc ou InMemory , ...)
* Autres XxxAuthenticationProvider (RememberMe, Jaas , Remote , CAS, ...)

UserDetailsService est une interface comportant essentiellement
   UserDetails loadUserByUsername(String username)
   
Comme implémentation de l'interface UserDetailsService on trouve entre autres:
   JdbcUserDetailsManager  , LdapUserDetailsManager  , InMemoryDaoImpl , ...