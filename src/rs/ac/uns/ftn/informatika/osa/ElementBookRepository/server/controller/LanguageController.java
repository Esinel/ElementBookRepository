package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;

import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Language;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.LanguageDaoLocal;



public class LanguageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	private LanguageDaoLocal languageDao;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String purpose = request.getParameter("purpose");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		try{
			if ("getGenreById".equals(purpose)){
				int id = Integer.parseInt(request.getParameter("langId"));
				Language lang = languageDao.findById(id);
				String jsonLang = mapper.writeValueAsString(lang);
				out.print(jsonLang); 
				out.flush();
				out.close(); 
			} else{
				//get all
				String jsonLang = mapper.writeValueAsString(languageDao.findAll());
				out.print(jsonLang);
				out.flush();
				out.close();
			}
		}catch(Exception e) {
			response.getWriter().write(e.getMessage());
		}
	} 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		/*String purpose = request.getParameter("purpose");
		if ("add".equals(purpose)){
			String categoryJson = request.getParameter("categoryJson");
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObj = mapper.readTree(categoryJson); 
			String name = jsonObj.get("name").asText();
			String description = jsonObj.get("description").asText();
			
			int subcategoryId = jsonObj.get("subcategory").asInt();
			Category subcategory = CategoryDAO.getById(subcategoryId);
			
			Category category = new Category(name, description, subcategory);
			CategoryDAO.insert(category);
			category.setCode(CategoryDAO.getLastId());
			CategoryDAO.categorieList.add(category);
			//response will be automaticaly 200 ok
		}else if ("update".equals(purpose)){
			doPut(request, response);
		}else if ("delete".equals(purpose)){
			doDelete(request, response);
		}*/
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String categoryJson = request.getParameter("categoryJson");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonObj = mapper.readTree(categoryJson);
		
		int id = jsonObj.get("catCode").asInt();
		
		String name = jsonObj.get("name").asText();
		String description = jsonObj.get("description").asText();
		int subcategoryId = jsonObj.get("subcategory").asInt();
		Category subcategory = CategoryDAO.getById(subcategoryId);
		Category category = new Category(id, name, description, subcategory);
		
		CategoryDAO.update(category);
		CategoryDAO.categorieList.clear();
		CategoryDAO.loadAll();*/
		
	}
		
	 
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String idStr = request.getParameter("catId");
		int id = Integer.parseInt(idStr);
		
		CategoryDAO.deleteLogical(id);
		CategoryDAO.categorieList.clear();
		CategoryDAO.loadAll();*/
	}
		
		
}

