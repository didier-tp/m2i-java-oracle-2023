<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
</head>
<body>
   <h1>Login</h1>
   
   <form name='f' action="login" method='POST'>
   
   		<!-- csrf protection activated by default -->
       <input type="hidden"  name="${_csrf.parameterName}"  value="${_csrf.token}"/> 
      <table>
         <tr>
            <td>User:</td>
            <td><input type='text' name='username' value=''></td>
         </tr>
         <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
         </tr>
      </table>
  </form>
   <%
    if(null!=request.getParameter("error"))
    {%>
        <div style="color:red;">login failure. try again</div>
    <%}
   %>
</body>
</html>