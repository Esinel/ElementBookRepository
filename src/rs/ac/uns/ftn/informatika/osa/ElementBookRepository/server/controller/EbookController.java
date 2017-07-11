package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.net.httpserver.HttpContext;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.lucene.document.Document;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.Indexer;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Category;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Ebook;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Language;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.User;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.CategoryDaoLocal;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.LanguageDaoLocal;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.EbookDaoLocal;



public class EbookController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(EbookController.class);
    
	@EJB
	private EbookDaoLocal ebookDao;
	
	@EJB
	private CategoryDaoLocal categoryDao;
	
	@EJB
	private LanguageDaoLocal languageDao;
	

	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			User moderator = (User) session.getAttribute("user");

			ObjectMapper mapper = new ObjectMapper();
			String purpose = request.getParameter("purpose");
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			try{
				if ("getById".equals(purpose)){
					int id = Integer.parseInt(request.getParameter("movieId"));
					Ebook ebook = ebookDao.findById(id);
					String jsonMovie = mapper.writeValueAsString(ebook);
					out.print(jsonMovie); 
					out.flush();
					out.close(); 
				}else if ("filterByGenre".equals(purpose)){
					int genreID= Integer.parseInt(request.getParameter("genreID"));
					String jsonFiltMovies = mapper.writeValueAsString(ebookDao.findAllFilterGenre(genreID));
					out.print(jsonFiltMovies);
					out.flush();
					out.close();
				}/*else if ("filterAsc".equals(purpose)) {
					String jsonFiltMovies = mapper.writeValueAsString(ebookDao.findAllFilterAsc());
					out.print(jsonFiltMovies);
					out.flush();
					out.close();
				}else if ("filterDesc".equals(purpose)) {
					String jsonFiltMovies = mapper.writeValueAsString(ebookDao.findAllFilterDesc());
					out.print(jsonFiltMovies);
					out.flush();
					out.close();
				}else if ("filterAscModer".equals(purpose)) {
					int genreId = moderator.getCategory().getId(); 
					String jsonFiltMovies = mapper.writeValueAsString(ebookDao.findAllFilterAscModer(genreId));
					out.print(jsonFiltMovies);
					out.flush();
					out.close();
				}else if ("filterDescModer".equals(purpose)) {
					int genreId = moderator.getCategory().getId();
					String jsonFiltMovies = mapper.writeValueAsString(ebookDao.findAllFilterDescModer(genreId));
					out.print(jsonFiltMovies);
					out.flush();
					out.close();
				}else */else if ("getAllForModerator".equals(purpose)){
					int genreId = moderator.getCategory().getId();
					String jsonFiltMovies = mapper.writeValueAsString(ebookDao.findAllFilterGenre(genreId));
					out.print(jsonFiltMovies);
					out.flush();
					out.close();
				}/*else if ("filterByDays".equals(purpose)){
					int numOfDays = Integer.parseInt(request.getParameter("days"));
					System.out.println(ebookDao.findAllFilterDays(numOfDays));
					ArrayList<Ebook> ebooks = ebookDao.findAllFilterDays(numOfDays);
					String jsonFiltMovies = mapper.writeValueAsString(ebooks);
					out.print(jsonFiltMovies);
					out.flush();
					out.close();
				}else if ("filterByDaysModer".equals(purpose)){
					int numOfDays = Integer.parseInt(request.getParameter("days"));
					int genreId = moderator.getCategory().getId(); 
					String jsonFiltMovies = mapper.writeValueAsString(ebookDao.findAllFilterDaysModer(numOfDays, genreId));
					out.print(jsonFiltMovies);
					out.flush();
					out.close();
				}*/else{
					//get all
					String jsonMovies = mapper.writeValueAsString(ebookDao.findAll());
					out.print(jsonMovies);
					out.flush();
					out.close();
				}
			}catch(Exception e) {
				response.getWriter().write(e.getMessage());
			}
	} 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Ebook ebook = new Ebook();

		String purpose = request.getParameter("purpose");
		int ebookId = -1;

		ResourceBundle rb = ResourceBundle.getBundle("app");
		String storagePath = rb.getString("storage");


		if ("delete".equals(purpose)){
			doDelete(request, response);
		}else{
			//then its create or edit
			// Check that we have a file upload request
			if(ServletFileUpload.isMultipartContent(request)){

				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(1024);
				ServletFileUpload upload = new ServletFileUpload(factory);
				try {
					List<FileItem> items = upload.parseRequest(request);
					FileItem fileItem = null;
					File uploadedFile = null;
					String fileName = "";
					for(FileItem item : items){
						// PROCESIRAJ FORM input type="file"
						if(!item.isFormField()){
							fileName = System.currentTimeMillis() + ".pdf";
							uploadedFile = new File(storagePath, fileName);
							fileItem = item;
							//break;
						}else{
							// PROCESIRAJ REGULARNE INPUT PARAMETRE
							String name = item.getFieldName();
							String value = item.getString();

							if ("id".equals(name)){
								if (!("").equals(value)){
									ebookId = Integer.parseInt(value);
								}
							}else if ("purpose".equals(name)){
								purpose = value;
							}else if ("title".equals(name)){
								ebook.setTitle(value);
							}else if("author".equals(name)){
								ebook.setAuthor(value);
							}else if("publicationYear".equals(name)){
								ebook.setPublicationYear(Integer.parseInt(value));
							}else if("keywords".equals(name)){
								ebook.setKeywords(value);
							}else if("genre".equals(name)){
								Category category = categoryDao.findById(Integer.parseInt(value));
								ebook.setCategory(category);
							}else if("language".equals(name)){
								Language language = languageDao.findById(Integer.parseInt(value));
								ebook.setLanguage(language);
							}
						}
					}




					while (uploadedFile.exists()) {
						fileName = System.currentTimeMillis() + ".pdf";
						uploadedFile = new File(storagePath, fileName);
					}
					//kreira prazan fajl
					uploadedFile.createNewFile();
					//filluje ga podacima
					fileItem.write(uploadedFile);

					if (!"".equals(fileItem.getName())){
						//ako se fajl nije menjao vracamo na stari
						ebook.setFilename(fileName);
						uploadedFile = new File(storagePath, ebook.getFilename());
					}


					if ("update".equals(purpose)){
						ebook.setId(ebookId);
						ebookDao.merge(ebook);
					}else{
						ebookDao.persist(ebook);
					}


					// maps form metadata input values to the actual document and then indexes it
					Indexer.getInstance().index(uploadedFile, ebook);
					response.sendRedirect("index.jsp?success");
				} catch (Exception e) {
					response.sendRedirect("index.jsp?error");
				}
			}
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
		
	 
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("movieId");
		int id = Integer.parseInt(idStr);
		ebookDao.remove(id);
	}

}

