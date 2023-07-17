<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<f:view>
   <h:messages /> <!--  affiche eventuels messages d'erreurs -->
   <hr/>
   <h:form>
        <label>x:</label> <h:inputText value="#{calcul.x}" /> <br/>
        <h:commandButton  value="calcul racine carree" action="#{calcul.calculerRacineCarree}"/>
   </h:form>
   <hr/>
   resultat:  <h:outputText value="#{calcul.res}" />

</f:view>
</body>
</html>