package controllers;

import models.Author;
import java.util.ArrayList;
import java.util.HashSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import java.security.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


@ManagedBean
public class AuthorController {

    @PersistenceUnit(unitName = "Project3PU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    @ManagedProperty(value = "#{author}")
    private Author author;

    public String addAuthor() {
        String returnValue = "error";
        try {
            userTransaction.begin();
            EntityManager em = entityManagerFactory.createEntityManager();
            author.setPosts(null);
            em.persist(author);
            userTransaction.commit();
            em.close();
            returnValue = "confirmation";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    
    public String login() {
        String rValue = "error";
        Author ok;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String selectSQL = "select p from Author p where p.email like :email";
        try {
            Query selectQuery = entityManager.createQuery(selectSQL);
            selectQuery.setParameter("email", author.getEmail() + "%");
            ok = (Author)selectQuery.getSingleResult();
            
            if (ok.getPassword().equals(author.getPassword())) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("user", ok);
                rValue = "success";
            } else {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                FacesMessage facesMessage = new FacesMessage("Wrong username/password.");
                facesContext.addMessage(null, facesMessage);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rValue;
    }
    
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("user");
        return new String("success");
    }
   

    public List getAuthors() {
        List<Author> authors = new ArrayList();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String selectSQL = "select a from Author a";
        try {
            Query selectQuery = entityManager.createQuery(selectSQL);
            authors = selectQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authors;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}