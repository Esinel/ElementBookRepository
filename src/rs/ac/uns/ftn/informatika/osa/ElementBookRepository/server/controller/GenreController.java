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
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.CategoryDaoLocal;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.UserDaoLocal;



public class GenreController extends HttpServlet {
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
				int id = Integer.parseInt(request.getParameter("genreId"));
				Category genre = categoryDao.findById(id);
				String jsonGenre = mapper.writeValueAsString(genre);
				out.print(jsonGenre); 
				out.flush();
				out.close(); 
			} else{
				//get all
				String jsonGenres = mapper.writeValueAsString(categoryDao.findAll());
				out.print(jsonGenres);
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
			String genreJson = request.getParameter("genreJson");
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObj = mapper.readTree(genreJson); 
			
			String name = jsonObj.get("name").asText();
			Category category = new Category();
			category.setName(name);

			categoryDao.persist(category);
			

		}else if ("update".equals(purpose)){
			doPut(request, response);
		}else if ("delete".equals(purpose)){
			doDelete(request, response);
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String genreJson = request.getParameter("genreJson");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonObj = mapper.readTree(genreJson); 
		
		int id = jsonObj.get("id").asInt();
		String name = jsonObj.get("name").asText();
		Category category = new Category();
		category.setName(name);
		category.setId(id);

		categoryDao.merge(category);
	}
		
	 
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("genreId");
		int id = Integer.parseInt(idStr);
		categoryDao.remove(id);
	}
		
		
}

