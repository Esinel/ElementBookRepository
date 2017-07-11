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
import com.fasterxml.jackson.databind.node.ObjectNode;

import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.User;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.UserDaoLocal;


public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
    private UserDaoLocal userDao;
	
    public AccountController() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String purpose = request.getParameter("purpose");
		PrintWriter out = response.getWriter();
		
		
		try{
			if ("checkSessionUserRole".equals(purpose)){
				
				User user = (User) session.getAttribute("user");
				if (user != null) {
					out.print(user.getRole());
					out.flush();
	                out.close();
				}else{
					out.print("none");
	                out.flush();
	                out.close();
				}
				
			}else{
				// direct access - *blockedAccess: transfer user to login
				if (session.getAttribute("user") == null){
					response.sendRedirect("login.jsp");
				}else{
					response.sendRedirect("my-acc.jsp");
				}
			}
		}catch(Exception e) {
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("index.jsp");
		}
		
		
		String purpose = request.getParameter("purpose");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		 
		try{
			
			if ("changePass".equals(purpose)){
				String oldPass = request.getParameter("oldPass");
				String newPass = request.getParameter("newPass");
				if (!oldPass.equals(user.getPassword())){
					ObjectNode resp = mapper.createObjectNode();
					resp.put("message", "wrongPassword");
					out.print(resp);
	                out.flush();
	                out.close();
				}else{
					user.setPassword(newPass);
					userDao.merge(user);
					session.removeAttribute("user");
					session.setAttribute("user", user);
					out.print(mapper.writeValueAsString(user));
	                out.flush();
	                out.close();
				}
			}else if ("changeName".equals(purpose)){
				String newFN = request.getParameter("newFirstname");
				String newLn = request.getParameter("newLastname");
				user.setFirstName(newFN);
				user.setLastName(newLn);
				userDao.merge(user);
				session.removeAttribute("user");
				session.setAttribute("user", user);
				out.print(mapper.writeValueAsString(user));
                out.flush();
                out.close();
			}
		}catch(Exception e){
			response.getWriter().write(e.getMessage());
		}
	}

}
