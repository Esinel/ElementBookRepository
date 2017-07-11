package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.io.StringReader;

import javax.persistence.*;

@Entity
@Table(name = "ebook")
@NamedQuery(name = "Ebook.findAllFilterByGenre", query = "SELECT e FROM Ebook e WHERE e.category.id like :categoryId")
public class Ebook implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ebook_id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "ebook_title", nullable = false, length=80)
	private String title;

	@Column(name = "author")
	private String author;

	@Column(name = "publication_year")
	private int publicationYear;

	@Column(name = "ebook_keywords")
	private String keywords;

	@Column(name = "fileName")
	private String filename;




    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Ebook(String title, String keywords, int publicationYear, String author, Category category, Language language, String filename) {
        this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
		this.category = category;
		this.language = language;
        this.keywords = keywords;
        this.filename = filename;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "language_id", referencedColumnName = "language_id", nullable = false)
	private Language language;



	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Ebook() {
		super();
	}
	

	
	

}
