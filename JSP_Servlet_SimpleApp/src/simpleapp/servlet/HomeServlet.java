package simpleapp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/home"} )
public class HomeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	   protected void doGet(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, IOException {
		
		System.out.println("in HomeServlet");
		// Forward to /WEB-INF/views/homeView.jsp
	    // (Users can not access directly into JSP pages placed in WEB-INF)
		RequestDispatcher ds = this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeView.jsp");
		ds.forward(request, response);
	}
	
	@Override
	   protected void doPost(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, IOException {
		doGet(request, response);
	}

}
