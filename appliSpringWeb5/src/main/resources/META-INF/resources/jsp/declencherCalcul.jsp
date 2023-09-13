<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>declencherCalcul</title>
</head>
<body>
     <form action="tva" method="POST" >
     
       <!-- csrf protection activated by default -->
       <input type="hidden"  name="${_csrf.parameterName}"  value="${_csrf.token}"/> 
        
        <label>ht:</label><input type="text" name="ht" value="200"/> <br/>
        <label>tauxTva (en %):</label><input type="text" name="tauxTvaPct" value="20" /> <br/>
        <input type="submit" value="Calculer TVA" />
     </form>
</body>
</html>