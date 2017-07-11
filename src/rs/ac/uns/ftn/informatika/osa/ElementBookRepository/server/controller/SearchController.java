package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.controller;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.QueryBuilder;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.ResultRetriever;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.model.SearchType;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.lucene.model.RequiredHighlight;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alligator on 10.2.17..
 */
public class SearchController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subscriberGenre = request.getParameter("genre");



        String title = request.getParameter("title");
        String titlest = request.getParameter("titlest");
        SearchType.Type titleSearchType = SearchType.getType(titlest);
        String titlesc = request.getParameter("titlesc");
        BooleanClause.Occur titleOccur = getOccur(titlesc);

        String author = request.getParameter("author");
        String authorst = request.getParameter("authorst");
        SearchType.Type authorSearchType = SearchType.getType(authorst);
        String authorsc = request.getParameter("authorsc");
        BooleanClause.Occur authorOccur = getOccur(authorsc);

        String kw = request.getParameter("kw");
        String kwst = request.getParameter("kwst");
        SearchType.Type kwSearchType = SearchType.getType(kwst);
        String kwsc = request.getParameter("kwsc");
        BooleanClause.Occur kwOccur = getOccur(kwsc);

        String text = request.getParameter("text");
        String textst = request.getParameter("textst");
        SearchType.Type textSearchType = SearchType.getType(textst);
        String textsc = request.getParameter("textsc");
        BooleanClause.Occur textOccur = getOccur(textsc);

        // add LANGUAGE :)



        try {
            BooleanQuery bquery = new BooleanQuery();

            List<RequiredHighlight> rhs = new ArrayList<RequiredHighlight>();

            if(!(title == null || title.equals(""))){
                Query query = QueryBuilder.buildQuery(titleSearchType, "title", title);
                bquery.add(query, titleOccur);
                //RequiredHighlight rh = new RequiredHighlight("title", title);
                //rhs.add(rh);
            }

            if(!(author == null || author.equals(""))){
                Query query = QueryBuilder.buildQuery(authorSearchType, "author", author);
                bquery.add(query, authorOccur);
                //RequiredHighlight rh = new RequiredHighlight("title", title);
                //rhs.add(rh);
            }

            if(!(kw == null || kw.equals(""))){
                Query query = QueryBuilder.buildQuery(kwSearchType, "keywords", kw);
                bquery.add(query, kwOccur);
                //RequiredHighlight rh = new RequiredHighlight("keyword", kw);
                //rhs.add(rh);
            }

            if(!(text == null || text.equals(""))){
                Query query = QueryBuilder.buildQuery(textSearchType, "text", text);
                bquery.add(query, textOccur);
                RequiredHighlight rh = new RequiredHighlight("text", text);
                rhs.add(rh);
            }



            request.getSession().setAttribute("data", ResultRetriever.getResults(bquery, rhs, subscriberGenre));
            response.sendRedirect("result.jsp");
        } catch (IllegalArgumentException e) {
            response.sendRedirect("index.jsp?error");
        } catch (ParseException e) {
            response.sendRedirect("index.jsp?error");
        }
    }

    private BooleanClause.Occur getOccur(String value) {
        if (value.equals("+")) {
            return BooleanClause.Occur.MUST;
        }else{
            // its OR
            return BooleanClause.Occur.SHOULD;
        }
    }
}
