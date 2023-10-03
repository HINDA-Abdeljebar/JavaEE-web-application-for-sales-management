package cn.vente.dao;


import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import cn.vente.model.Articles;
import cn.vente.model.Commandes;

public class CommandeDAO {

    public Commandes getById(int id) {
        Session session = SessionFact.getSessionfactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Commandes commande = session.get(Commandes.class, id);

        transaction.commit();
        session.close();
        return commande;
    }

    public void save(Commandes commande) {

        Session session = SessionFact.getSessionfactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(commande);

        transaction.commit();
        session.close();

    }
    @SuppressWarnings("unchecked") 
    public List<Commandes> getAll(){ 
        Transaction transaction = null; 
        List<Commandes> Commandes = null; 
        try ( Session session = SessionFact.getSessionfactory().openSession()){ 
          
         transaction = session.beginTransaction(); 
         Commandes = session.createQuery("from Commandes order by id_cmd DESC").list(); 
         transaction.commit();  
         
          
          
        }catch(Exception e) { 
         System.out.println(e.getMessage()); 
          if (transaction != null) { 
             transaction.rollback();} 

        } 
         
        return Commandes ; 
    } 


    public void delete(Commandes commande) {
        Session session = SessionFact.getSessionfactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(commande);

        transaction.commit();
        session.close();

    }

    public void update(Commandes commande) {
        Session session = SessionFact.getSessionfactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(commande);
        transaction.commit();
        session.close();

    }

    public List<Commandes> getUserCommandes(int userId) {
        Transaction transaction = null;
        List<Commandes> commandes = null;
        try (Session session = SessionFact.getSessionfactory().openSession()) {

            transaction = session.beginTransaction();
            String query = "FROM Commande WHERE user_id = :userId";
            Query<Commandes> hibernateQuery = session.createQuery(query, Commandes.class);
            hibernateQuery.setParameter("userId", userId);
            commandes = hibernateQuery.list();

            transaction.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }

        }

        return commandes;
    }

 
}
