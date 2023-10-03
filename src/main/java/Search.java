import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import cn.vente.model.*;
import cn.vente.dao.*;

public class Search extends ActionSupport implements SessionAware{
	private String query; //name=query (search)
	private String id; //id to remove and admin User 
		private Map<String, Object> session; 
		
		public String getQuery() {
			return query;
		}

		public void setQuery(String query) {
			this.query = query;
		}
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		//Search article
		public String execute() {			
	        
			ArticlesDAO dao = new ArticlesDAO();
			List<Articles>articles = dao.getByName(this.query);     
			session.put("articles_Search", articles);
			
			return "success";
		}
		
		@Override
		public void setSession(Map<String, Object> session) {
			this.session = session;
		}
		
		//  remove users 
		public String RemoveUser() {
			UserDAO dao = new UserDAO();
			CommandeDAO dao_cmd = new CommandeDAO();
			List<User> users = dao.getAll(); 
			List<Commandes> commandes = dao_cmd.getAll();

			for (User user : users) {
				if (user.getId()==Integer.parseInt(id) && user.isRole()==false) {
				
					
					dao.delete(user);
					session.put("Userr", user.getLogin()+" is deleted.");
					break;
				}
				
				// can't remove admin
				if (user.getId()==Integer.parseInt(id) && user.isRole()!=false) {
					session.put("Userr", user.getLogin()+" can't be deleted because he's admin.");
				break;
			}
			}

			return "success";
		}
		
		// make admin
		public String AdminUser() {
			UserDAO dao = new UserDAO();
			List<User> users = dao.getAll(); 

			for (User user : users) {
				if (user.getId()==Integer.parseInt(id)) {
					user.setRole(true);
					dao.update(user);
					session.put("UserAdmin", user.getLogin()+" is now admin");
					break;
				}
			}
			
			return "success";
		}
}
