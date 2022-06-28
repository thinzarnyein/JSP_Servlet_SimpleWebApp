package simpleapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import simpleapp.utils.DBUtils;
import simpleapp.utils.MyUtils;

@WebServlet( urlPatterns = {"/deleteProduct"} )
public class DeleteProductServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("in deleteProduct servlet");
		
		Connection conn = MyUtils.getStoredConnection(request);
		String code = (String) request.getParameter("code");
		String errorString = null;
		
		try {
			DBUtils.deleteProduct(conn, code);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		//If has an error, redirect to the error page.
		if(errorString != null) {
			//Store the information in the request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			
			RequestDispatcher ds = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/deleteProductErrorView.jsp");
			ds.forward(request, response);
		}
		
		//If everything nice
		//Redirect to the product listing page
		else {
			response.sendRedirect(request.getContextPath() + "/productList");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
