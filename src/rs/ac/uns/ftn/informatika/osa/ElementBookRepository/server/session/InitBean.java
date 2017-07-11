	package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session;


import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;


import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.*;

import java.util.ArrayList;

	@Stateless
@Remote(Init.class)
public class InitBean implements Init {

	@EJB
	UserDaoLocal userDao;
	
	@EJB
	CategoryDaoLocal categoryDao;

	@EJB
	LanguageDaoLocal languageDao;
	
	@EJB
	EbookDaoLocal ebookDao;
	

	public void init() {
		User user = new User("Admin", "Admin", "admin", "admin", UserRole.ADMIN, null);

		userDao.persist(user);

		Category category = new Category();
		category.setName("Education");
		categoryDao.persist(category);

		Category category2 = new Category();
		category2.setName("Roman");
		categoryDao.persist(category2);


		Language language = new Language();
		language.setName("English");
		languageDao.persist(language);

		Language language2 = new Language();
		language2.setName("Indian");
		languageDao.persist(language2);

		Ebook ebook = new Ebook();
		ebook.setCategory(category);
		ebook.setKeywords("nature, indians, asd");
		ebook.setLanguage(language);
		ebook.setTitle("Ebook 1");
		ebook.setAuthor("Author q");
		ebookDao.persist(ebook);


	}
}
