package simpleapp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import simpleapp.beans.UserAccount;
import simpleapp.utils.MyUtils;

@WebServlet(urlPatterns={"/userInfo"})
public class UserInfoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		System.out.println("in userinfo servlet");
		HttpSession session = request.getSession();
		
		//Check User has logged on
		UserAccount loginedUser = MyUtils.getLoginedUser(session);
		
		System.out.println("contextPath : " + request.getContextPath());
		System.out.println("servlet Path : " + request.getServletPath());
		System.out.println("servlet context : " + request.getServletContext());
		
		//Not logged in
		if(loginedUser == null) {
			//Redirect to login page.
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		
		//Store info to the request attribute before forwarding. 
		request.setAttribute("user", loginedUser);   //eg, get attribute user.userName in userInfoview.jsp
		
		//If the user has logged in, then forward to the page
		// /WEB-INF/views/userInfoView.jsp
		RequestDispatcher ds =
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/userInfoView.jsp");
		ds.forward(request, response);	
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
