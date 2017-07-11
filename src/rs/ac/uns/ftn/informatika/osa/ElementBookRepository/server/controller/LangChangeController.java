package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class LangChangeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(LangChangeController.class);
	
    public LangChangeController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			
			String language = null;
			language = request.getParameter("lang");
			System.out.println("SISOOO");
			if(language.equals("1")){
				request.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", "sr");
			}
			if(language.equals("2")){
				request.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", "en");
			} 
			response.sendRedirect(response.encodeRedirectURL("./index.jsp"));
			
			
			
		}catch(Exception e){
			response.getWriter().write(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
