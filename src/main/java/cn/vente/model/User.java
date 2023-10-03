package cn.vente.model;

import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import cn.vente.dao.UserDAO;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
public class User extends ActionSupport  {

	
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
	private int id;

	@Column(unique = true)
	private String login;

	@Column
	private String pass;

	@Column
	private boolean role;

	private String re_pass;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE) // ida msaht shi user khass ytmesho commandes dialo, sinon error f report
	private List<Commandes> commandes;

	// Getter and Setter methods

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

	public String getRe_pass() {
		return re_pass;
	}

	public void setRe_pass(String re_pass) {
		this.re_pass = re_pass;
	}

	
	
	
	// execute method signin
	public String execute() {
		UserDAO dao = new UserDAO();
		List<User> users = dao.getAll();

		for (User user : users) {
			if (this.login.equals(user.getLogin()) && this.pass.equals(user.getPass())) {
				 HttpServletRequest request = ServletActionContext.getRequest();
		            HttpSession session = request.getSession();
		            session.setAttribute("loggedInUser", user);

				return SUCCESS;
			}
		}

		return ERROR;
	}

	//signup
	public String check_user() {
		UserDAO dao = new UserDAO();
		User new_user = new User();
		if (login != null && pass != null && re_pass != null && pass.equals(re_pass)) {
			new_user.setLogin(login);
			new_user.setPass(pass);
			new_user.setRole(false);
			dao.save(new_user);

			return SUCCESS;
		}
		return ERROR;
	}
	
	
}