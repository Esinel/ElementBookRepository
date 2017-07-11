package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session;


import javax.ejb.Local;
import javax.ejb.Stateless;

import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Language;


@Stateless
@Local(LanguageDaoLocal.class)
public class LanguageDaoBean extends GenericDaoBean<Language, Integer> implements LanguageDaoLocal {

	

}
