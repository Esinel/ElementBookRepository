package rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity;


import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@NamedQuery(name = "User.findByUsernameAndPassword", query = "SELECT k FROM User k WHERE k.username like :username AND k.password LIKE :password")
public class User implements Serializable {

	private static final long serialVersionUID = 3770759786667844735L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "user_firstName", nullable = false, length=30)
	private String firstName;

	@Column(name = "user_lastName", nullable = false, length=30)
	private String lastName;

	@Column(name = "user_username", unique = true, nullable = false, length=10)
	private String username;

	@Column(name = "user_password", nullable = false, length=10)
	private String password;
	
	@Column(name = "user_role", nullable = false, length=10)
	private UserRole role;
	
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable=true)
	private Category category;


	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() { 
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserRole getRole() {
		return role;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}


	public User() {
		super();
	}


	public User(String firstName, String lastName, String username, String password, UserRole role, Category category) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
		if(category!=null){
			this.category = category;
		}else this.category=null;
	}

	public User(String firstName, String lastName, String username, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.category = null;
	}

	//complete copy
	public User(User user){
		super();
		this.id = user.id;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.username = user.username;
		this.password = user.password;
		this.category = user.category;
		this.role = user.role;
	}
	@Override
	public String toString() {
		return firstName + ' ' + lastName;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
