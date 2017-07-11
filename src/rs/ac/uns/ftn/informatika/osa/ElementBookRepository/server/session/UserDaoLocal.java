package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session;

import java.util.List;


import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.User;


public interface UserDaoLocal extends GenericDaoLocal<User, Integer> {

	public User findUserByUsenameAndPassword(String username, String password);
	
	public List<User> findAllModerators();

}
