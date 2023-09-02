/*
 Approximativement:
//restApiMainRealm=Jdbc or SpecificUserDetailsService or none(if oAuth2Server) or Ldap or ...
//none by default (with oAuth2Server))

//webSiteMainRealm=Jdbc or SpecificUserDetailsService or Ldap or ...
//SpecificUserDetailsService par default 

//restApiGlobalRealm=restApiMainRealm+restSecondaryRealm
//webSiteGlobalRealm=webSiteMainRealm+webSiteSecondaryRealm
 */

//PRECISEMENT

//avec prefix "mysecurity.realm"
//withGlobalDefaultSecondaryInMemoryRealm=true_or_false (default=false)
//withRestDefaultSecondaryInMemoryRealm=true_or_false (default=false)
//withWebSiteDefaultSecondaryInMemoryRealm=true_or_false (default=false)


//SpecificUserDetailsService n'a de sens que dans le projet principal
//en tant qu'extension !!!! (MySecurityExtension)
//Cette configuration sera considérée comme prioritaire si elle elle détectée

//Autre realm (jdbc,ldap) à configurer selon ce qui existe
//ex:
//mysecurity.realm.site.jdbc-realm....
//mysecurity.realm.rest.jdbc-realm....
//mysecurity.realm.global.jdbc-realm....
//...
	