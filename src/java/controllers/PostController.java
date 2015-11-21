package controllers;

import models.Post;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import models.Author;


@ManagedBean
public class PostController {

    @PersistenceUnit(unitName = "Project3PU")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction userTransaction;
    
    @ManagedProperty(value = "#{post}")
    private Post post;

    public String savePost() {
        String returnValue = "error";
        try {
            userTransaction.begin();
            EntityManager em = entityManagerFactory.createEntityManager();
            em.persist(post);
            userTransaction.commit();
            em.close();
            returnValue = "confirmation";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public List getMatchingPosts() {
        List<Post> posts = new ArrayList();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String selectSQL = "select p from Post p where p.author like :author";
        try {
            Query selectQuery = entityManager.createQuery(selectSQL);
            selectQuery.setParameter("author", post.getAuthor() + "%");
            posts = selectQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }
    
    public List getAllPosts() {
        List<Post> posts = new ArrayList();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String selectSQL = "select p from Post p";
        try {
            Query selectQuery = entityManager.createQuery(selectSQL);
            posts = selectQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
}