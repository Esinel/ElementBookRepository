package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.User;


@Stateless
@Local(UserDaoLocal.class)
public class UserDaoBean extends GenericDaoBean<User, Integer> implements UserDaoLocal {



	public User findUserByUsenameAndPassword(String username, String password) {
		
		try{
			Query q = em.createNamedQuery("User.findByUsernameAndPassword");
			q.setParameter("username", username);
			q.setParameter("password", password);
			User result = (User) q.getSingleResult();
			return result;
		}catch(NoResultException e) {
	        return null;
	    }
		
	}
	
	public void remove(User u) {
		u = em.merge(u);
		u.getCategory().getUsers().remove(u);
		em.remove(u);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAllModerators(){
		Query q = em.createQuery("SELECT u FROM User u WHERE u.role = 'SUBSCRIBER'");
		List<User> result = q.getResultList();
		return result;
	};

}
