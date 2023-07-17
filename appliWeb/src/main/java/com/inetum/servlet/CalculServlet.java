package com.inetum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/CalculServlet")
public class CalculServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	// <a href="CalculServlet">CalculServlet</a> <br/> dans index.jsp
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out  = response.getWriter();
		String sX = request.getParameter("x");
		Double x = sX!=null?Double.parseDouble(sX):0;
		Double y = Math.sqrt(x);
		out.println("<html>");
		out.println("<body>");
		out.println("<form method='GET' action='' >");  
		   out.println("x=<input type='text' name='x' value='" + x + "' />");
		   out.println("<input type='submit' value='calculer racine carree' />");
		out.println("</form>"); 
		out.println("<hr/>");
		out.println("y="+y);
		out.println("</body>");
		out.println("</html>");
	}
	
	public CalculServlet() { super();       
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
