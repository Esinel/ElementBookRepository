package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session;


import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Category;
import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Ebook;

import java.util.List;

public interface EbookDaoLocal extends GenericDaoLocal<Ebook, Integer> {

	  public List<Ebook> findAllFilterGenre(int categoryId);
}
