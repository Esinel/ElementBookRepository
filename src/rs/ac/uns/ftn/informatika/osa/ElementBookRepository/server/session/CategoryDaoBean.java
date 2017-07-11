package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session;

import javax.ejb.Local;
import javax.ejb.Stateless;

import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Category;

@Stateless
@Local(CategoryDaoLocal.class)
public class CategoryDaoBean extends GenericDaoBean<Category, Integer> implements
		CategoryDaoLocal {

}
