import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import cn.vente.dao.UserDAO;
import cn.vente.model.User;

public class UserController extends ActionSupport implements SessionAware {

	private Map<String, Object> session; 
	private String current_pass;
	private String new_pass;
	private String re_pass;
	
	ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationCentext.xml");
	
	@Override 
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getCurrent_pass() {
		return current_pass;
	}

	public void setCurrent_pass(String current_pass) {
		this.current_pass = current_pass;
	}

	public String getNew_pass() {
		return new_pass;
	}

	public void setNew_pass(String new_pass) {
		this.new_pass = new_pass;
	}

	public String getRe_pass() {
		return re_pass;
	}

	public void setRe_pass(String re_pass) {
		this.re_pass = re_pass;
	}

	
	// change password
	public String change_password() {
		UserDAO dao = (UserDAO) context.getBean("UserDAO");
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        
		User user = (User) session.getAttribute("loggedInUser");
		if(user.getPass().equals(current_pass) && new_pass.equals(re_pass)) {
			user.setPass(new_pass);
			dao.update(user);
			
		return SUCCESS ;
		}

		return ERROR;
	}
	
	//logout 
    public String logout() {
    	HttpSession session = ServletActionContext.getRequest().getSession(false);
        
       
        if (session != null) {
            session.invalidate();
        }
        
        return SUCCESS;
    }

}
