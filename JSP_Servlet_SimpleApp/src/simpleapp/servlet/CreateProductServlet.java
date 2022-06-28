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

import simpleapp.beans.Product;
import simpleapp.utils.DBUtils;
import simpleapp.utils.MyUtils;

@WebServlet( urlPatterns = {"/createProduct"} )
public class CreateProductServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	// Show product creation page.
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("in create product servlet");
		
		RequestDispatcher ds = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
		ds.forward(request, response);
	}
	
	// When the user enters the product information, and click Submit.
	// This method will be called.
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoredConnection(request);
		
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String priceStr = request.getParameter("price");
		float price = 0;
		try{
			price = Float.parseFloat(priceStr);
		} catch(Exception e){
			
		}
		
		Product product = new Product(code, name, price);
		
		String errorString = null;
		
		//Product ID is the string literal [a-zA-Z_0-9]
		//with at least 1 character
		String regex = "[a-zA-Z]+[0-9]+";
		
		if (code == null || code == "" || name == null || name == "" || price == 0) {
			errorString = "invlid product!";
		}
		
		else if(code == null || !code.matches(regex)) {
			errorString = "Product Code invalid!";
		}
		
		if(errorString == null) {
			try {
				DBUtils.insertProduct(conn, product);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}
		
		//If error, forward to Edit Page
		if(errorString != null) {
			
			// Store information in request attribute, before forward.
			request.setAttribute("errorString", errorString);
			
			RequestDispatcher ds = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
			ds.forward(request, response);
		}
		
		//If everything nice.
		//Redirect to the product listing page
		else {
			response.sendRedirect(request.getContextPath() + "/productList");
		}
	}

}
