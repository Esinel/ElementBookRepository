package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;


public abstract class GenericDaoBean<T, ID extends Serializable> implements GenericDaoLocal<T, ID> {
	private static Logger log = Logger.getLogger(GenericDaoBean.class);
	
	private Class<T> entityType;

	@PersistenceContext(unitName = "ElementBookRepository")
	protected EntityManager em;

	/*@Resource
	UserTransaction userTransaction;*/
	
	@SuppressWarnings("unchecked")
	public GenericDaoBean() {
		entityType = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getEntityType() {
		return entityType;
	}

	public T findById(ID id) {
		T entity;
		entity = em.find(entityType, id);
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Query q = em.createQuery("SELECT x FROM " + entityType.getSimpleName()
				+ " x");
		List<T> result = q.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<T> findBy(String query) {
		Query q = em.createQuery(query);
		List<T> result = q.getResultList();
		return result;
	}

	public T persist(T entity) {
		log.info("em.persist: " + entity);
		em.persist(entity);
		em.flush();
		return entity;
	}
	
	public void save(T entity) {
		em.getTransaction().begin();
		em.persist(entity);
		em.flush();
		em.getTransaction().commit();
	}

	public T merge(T entity) {
		log.info("em.merge: " + entity);
		entity = em.merge(entity);
		return entity;
	}

	public void remove(T entity) {
		log.info("em.remove: " + entity);
		entity = em.merge(entity);
		em.remove(entity);
	}
	
	public void remove(ID id) {
		em.remove(findById(id));
	 }

	public void flush() {
		em.flush();
	}

	public void clear() {
		em.clear();
	}

}
