package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by alligator on 17.1.17..
 */
public class File implements Serializable{
    private static final long serialVersionUID = 1L;

    @Column(name = "file_id", unique = true, nullable = false)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public File() {
    }

    public File(String fileName, String mimeType) {

        this.fileName = fileName;
        this.mimeType = mimeType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Column(name = "filename", unique = true,length = 200, nullable = false)
    private String fileName;

    @Column(name = "mime_type",length = 100, nullable = true)
    private String mimeType;
}
