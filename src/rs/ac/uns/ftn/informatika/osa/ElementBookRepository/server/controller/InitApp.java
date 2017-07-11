package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.controller;

import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Category;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.CategoryDaoLocal;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

/**
 * Created by alligator on 26.1.17..
 */
public class InitApp implements ServletContextListener {

    @EJB
    CategoryDaoLocal categoryDao;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        List<Category> categories =  categoryDao.findAll();
        servletContextEvent.getServletContext().setAttribute("genres", categories);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
