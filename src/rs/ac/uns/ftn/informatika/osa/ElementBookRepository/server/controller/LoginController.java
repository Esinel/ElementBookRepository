package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.User;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.UserDaoLocal;


public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    @EJB
    private UserDaoLocal userDao;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		try{
			User user = (User) session.getAttribute("user");
			// direct access - *blockedAccess: transfer user to login
			if (user == null){
				response.sendRedirect("login.jsp");
			}else{
				response.sendRedirect("index.jsp");
			}
		}catch(Exception e){
			out.write(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		String jsonUser;
		User user = (User) session.getAttribute("user");
		if (user != null) {
			response.sendRedirect("index.jsp");
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");

	    
		try{
			User userDB = userDao.findUserByUsenameAndPassword(username, password);
			if (userDB != null){
				session.setAttribute("user", userDB);
				session.setAttribute("userRole", userDB.getRole().name());
				if (userDB.getCategory() != null){
					session.setAttribute("moderGenre", userDB.getCategory().getName());
				}
				request.setAttribute("loginState", "true");
				response.sendRedirect("index.jsp");
			}else{
                session.setAttribute("loginState", "false");
                response.sendRedirect("login.jsp");
			}
		} catch(Exception e) {
	            response.setStatus(400);
	            response.getWriter().write(e.getMessage());
		}
	}

}
