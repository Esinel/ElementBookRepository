package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session;

import java.io.Serializable;
import java.util.List;

public interface GenericDaoLocal<T, ID extends Serializable> {

	public Class<T> getEntityType();

	public T findById(ID id);

	public List<T> findAll();

	public List<T> findBy(String query);

	public T persist(T entity);

	public T merge(T entity);
	
	public void save(T entity);

	public void remove(T entity);
	
	public void remove(ID id);

	public void flush();

	public void clear();

}
