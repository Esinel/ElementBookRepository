package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.session.Init;

public class InitClient {

	public static void main(String args[]) {
		
		try {
			Context ctx = new InitialContext();
			Init init = (Init) ctx.lookup("java:InitBeanRemote");
			init.init();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
