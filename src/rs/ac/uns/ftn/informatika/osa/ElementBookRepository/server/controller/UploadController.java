package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Ebook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by alligator on 4.2.17..
 */
@SuppressWarnings({"serial", "unchecked"})
public class UploadController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // configure controller ( if purpose = 'fillFormEbook' )
        //String purpose = request.getParameter("purpose");

        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");


        // na ovaj nacin pristupamo 'app.properties' -> storage
        ResourceBundle rb = ResourceBundle.getBundle("app");
        String storagePath = rb.getString("storage");


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
                for(FileItem item : items){ //trebalo bi da ima samo 1
                    // PROCESIRAJ FORM input type="file"
                    if(!item.isFormField()){
                        fileName = System.currentTimeMillis() + ".pdf";
                        uploadedFile = new File(storagePath, fileName);
                        fileItem = item;
                        break;
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



                PDDocument pdfDoc = PDDocument.load(uploadedFile);
                PDDocumentInformation pdfInfo = pdfDoc.getDocumentInformation();

                String title = pdfInfo.getTitle();
                String author = pdfInfo.getAuthor();
                String keywords = pdfInfo.getKeywords();
                Calendar publicationYear = pdfInfo.getCreationDate();

                uploadedFile.delete();

                Ebook tempEbook = new Ebook();
                tempEbook.setTitle(title);
                tempEbook.setAuthor(author);
                tempEbook.setKeywords(keywords);
                tempEbook.setPublicationYear(publicationYear.get(Calendar.YEAR));

                String jsonEbook = mapper.writeValueAsString(tempEbook);
                out.print(jsonEbook);
                out.flush();
                out.close();
                //Indexer.getInstance().index(uploadedFile);
                //response.sendRedirect("upload.jsp?success");
            } catch (Exception e) {
                response.sendRedirect("upload.jsp?error");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
