package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.controller;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ResourceBundle;

/**
 * Created by alligator on 9.2.17..
 */
public class DownloadController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResourceBundle rb = ResourceBundle.getBundle("app");
        String filepath = "";
        String storagePath = rb.getString("storage");
        String filename = request.getParameter("file");
        if (filename.split("/").length == 1){
            filepath = storagePath + "/" + filename;
        }else{
            filepath = filename;
        }



        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            if(filepath != null) {
                InputStream stream;

                stream = new BufferedInputStream(
                        new FileInputStream(new File(filepath)));
                ServletOutputStream out;
                out = response.getOutputStream();
                byte[] bbuf = new byte[100];
                int length = 0;
                while ((stream != null) && ((length = stream.read(bbuf)) != -1))
                {
                    out.write(bbuf,0,length);
                }
                out.flush();
                out.close();
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

}
