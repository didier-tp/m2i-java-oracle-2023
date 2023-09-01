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
        <label>ht:</label><input type="text" name="ht" /> <br/>
        <label>tauxTva (en %):</label><input type="text" name="tauxTvaPct" /> <br/>
        <input type="submit" value="calculer tva" />
     </form>
</body>
</html>