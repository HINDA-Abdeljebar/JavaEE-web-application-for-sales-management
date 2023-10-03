package cn.vente.dao; 
 
import java.util.ArrayList;
import java.util.List; 
 
import org.hibernate.Session; 
import org.hibernate.Transaction; 
import org.hibernate.query.Query;
import cn.vente.model.*;  
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
 
public class ArticlesDAO { 
 
 public Articles getById(int id) { 
  Session session = SessionFact.getSessionfactory().getCurrentSession(); 
  Transaction transaction = session.beginTransaction(); 
  Articles produit = session.get(Articles.class, id); 
 
  transaction.commit(); 
  session.close(); 
  return produit; 
 } 
 
// Search 
 public List<Articles> getByName(String name) {
	 Session session = SessionFact.getSessionfactory().getCurrentSession(); 
	 Transaction transaction = session.beginTransaction(); 
	// System.out.println("FROM Articles WHERE nom LIKE CONCAT('%', "+name+", '%')");
	   // Query<Articles> query = session.createQuery("FROM Articles WHERE nom = :name ", Articles.class);
	 Query<Articles> query = session.createQuery("FROM Articles WHERE nom LIKE CONCAT('%', :name, '%')", Articles.class);
	    query.setParameter("name", name);
	    List<Articles> articles = query.list();
	    System.out.println("Articles found: " + articles);

	    transaction.commit();
	    return articles;
	}
 
 public void save(Articles article) { 
 
  Session session = SessionFact.getSessionfactory().getCurrentSession(); 
  Transaction transaction = session.beginTransaction(); 
  session.save(article); 
 
  transaction.commit(); 
  session.close(); 
 
 } 
 

 
 public void delete(Articles article) { 
  Session session = SessionFact.getSessionfactory().getCurrentSession(); 
  Transaction transaction = session.beginTransaction(); 
  session.delete(article); 
 
  transaction.commit(); 
  session.close(); 
 
 } 
 
 public void update(Articles article) { 
  Session session = SessionFact.getSessionfactory().getCurrentSession(); 
  Transaction transaction = session.beginTransaction(); 
  session.update(article); 
  transaction.commit(); 
  session.close(); 
 
 } 
  
 @SuppressWarnings("unchecked") 
 public List<Articles> getAll(){ 
     Transaction transaction = null; 
     List<Articles> articles = null; 
     try ( Session session = SessionFact.getSessionfactory().openSession()){ 
       
      transaction = session.beginTransaction(); 
      articles = session.createQuery("from Articles").list(); 
      transaction.commit();  
      
       
       
     }catch(Exception e) { 
      System.out.println(e.getMessage()); 
       if (transaction != null) { 
          transaction.rollback();} 

     } 
      
     return articles ; 
 } 

 
}