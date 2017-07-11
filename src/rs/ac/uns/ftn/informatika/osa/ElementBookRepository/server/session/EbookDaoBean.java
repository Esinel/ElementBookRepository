package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session;


import javax.ejb.Local;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Category;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Ebook;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.User;

import java.util.List;


@Stateless
@Local(EbookDaoLocal.class)
public class EbookDaoBean extends GenericDaoBean<Ebook, Integer> implements EbookDaoLocal {
	
	public void remove(Ebook m) {
		m = em.merge(m);

		m.getLanguage().getMovies().remove(m);
		em.remove(m);
	}


	public List<Ebook> findAllFilterGenre(int categoryId){
		try{
			Query q = em.createNamedQuery("Ebook.findAllFilterByGenre");
			q.setParameter("categoryId", categoryId);
			List<Ebook> result = (List<Ebook>) q.getResultList();
			return result;
		}catch(NoResultException e) {
			return null;
		}
	}


}
