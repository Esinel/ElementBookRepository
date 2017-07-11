package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Category;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.User;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.UserRole;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.CategoryDaoLocal;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.UserDaoLocal;



public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	private CategoryDaoLocal categoryDao;
	
	@EJB
	private UserDaoLocal userDao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String purpose = request.getParameter("purpose");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		try{
			if ("getById".equals(purpose)){
				int id = Integer.parseInt(request.getParameter("userId"));
				User user = userDao.findById(id);
				String jsonUser = mapper.writeValueAsString(user);
				out.print(jsonUser); 
				out.flush();
				out.close(); 
			} else if ("getModerators".equals(purpose)){
				String jsonUsers = mapper.writeValueAsString(userDao.findAllModerators());
				out.print(jsonUsers);
				out.flush();
				out.close();
			} else{
				//get all
				String jsonUsers = mapper.writeValueAsString(userDao.findAll());
				out.print(jsonUsers);
				out.flush();
				out.close();
			}
		}catch(Exception e) {
			response.getWriter().write(e.getMessage());
		}
	} 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		String purpose = request.getParameter("purpose");
		if ("create".equals(purpose)){
			String userJson = request.getParameter("userJson");
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObj = mapper.readTree(userJson); 
			
			String username = jsonObj.get("username").asText();
			String password = jsonObj.get("password").asText();
			String firstname = jsonObj.get("firstname").asText();
			String lastname = jsonObj.get("lastname").asText();
			int roleId = jsonObj.get("role").asInt();
			
			User user = new User(firstname, lastname, username, password);
			
			if (roleId == 1){
				user.setRole(UserRole.ADMIN);
			}else if(roleId == 2){
				user.setRole(UserRole.SUBSCRIBER);
				int genreId = jsonObj.get("genre").asInt();
				Category genre = categoryDao.findById(genreId);
				user.setCategory(genre);
			}
			
			userDao.persist(user);
			
		}else if ("update".equals(purpose)){
			doPut(request, response);
		}else if ("delete".equals(purpose)){
			doDelete(request, response);
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userJson = request.getParameter("userJson");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonObj = mapper.readTree(userJson); 
		
		int id = jsonObj.get("id").asInt();
		String username = jsonObj.get("username").asText();
		String password = jsonObj.get("password").asText();
		String firstname = jsonObj.get("firstname").asText();
		String lastname = jsonObj.get("lastname").asText();
		int roleId = jsonObj.get("role").asInt();
		
		User user = new User(firstname, lastname, username, password);
		
		if (roleId == 1){
			user.setRole(UserRole.ADMIN);
		}else if(roleId == 2){
			user.setRole(UserRole.SUBSCRIBER);
			int genreId = jsonObj.get("genre").asInt();
			Category genre = categoryDao.findById(genreId);
			user.setCategory(genre);
		}

		user.setId(id);
		userDao.merge(user);
	}
		
	 
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("userId");
		int id = Integer.parseInt(idStr);
		userDao.remove(id);
	}
		
		
}

