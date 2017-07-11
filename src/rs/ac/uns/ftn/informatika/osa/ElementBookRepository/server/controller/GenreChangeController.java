package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.controller;

import org.apache.log4j.Logger;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Ebook;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.CategoryDaoLocal;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.EbookDaoLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Created by alligator on 9.2.17..
 */
public class GenreChangeController extends HttpServlet {
    private static Logger log = Logger.getLogger(GenreChangeController.class);

    @EJB
    private EbookDaoLocal ebookDao;

    @EJB
    private CategoryDaoLocal categoryDao;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String genreIdStr = request.getParameter("genre");
        int genreId = Integer.parseInt(genreIdStr);
        List<Ebook> ebooks = ebookDao.findAllFilterGenre(genreId);
        request.setAttribute("ebooks", ebooks);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
