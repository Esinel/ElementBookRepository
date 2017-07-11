package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "category_id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "category_name", nullable = false, length=30)
	private String name;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "category")
	private Set<Ebook> ebooks = new HashSet<Ebook>();
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "category")
	private Set<User> users = new HashSet<User>();
	
	public void add(Ebook ebook) {
		if (ebook.getCategory() != null)
			ebook.getCategory().getMovies().remove(ebook);
		ebook.setCategory(this);
		ebooks.add(ebook);
	}

	public void remove(Ebook ebook) {
		ebook.setCategory(null);
		ebooks.remove(ebook);
	}
	
	public void add(User u) {
		if (u.getCategory() != null)
			u.getCategory().getUsers().remove(u);
		u.setCategory(this);
		users.add(u);
	}

	public void remove(User u) {
		u.setCategory(null);
		ebooks.remove(u);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Ebook> getMovies() {
		return ebooks;
	}

	public void setMovies(Set<Ebook> ebooks) {
		this.ebooks = ebooks;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Category(){
		super();
	}

	public Category(String name, Set<Ebook> ebooks, Set<User> users) {
		super();
		this.name = name;
		this.ebooks = ebooks;
		this.users = users;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	
	

}
