package controllers;

import models.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
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
    
    @Resource
    private boolean filter = false;

    public String savePost() {
        String returnValue = "error";
        Author author = (Author)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        try {
            if(!HasBadWord()){
            userTransaction.begin();
            EntityManager em = entityManagerFactory.createEntityManager();
            post.setAuthor(author);
            em.persist(post);
            userTransaction.commit();
            em.close();
            setFilter(false);
            returnValue = "confirmation";
            } else {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                FacesMessage facesMessage = new FacesMessage("There is a flagged word in your post. This may call for an audit. Please review or click again to proceed anyway.");
                facesContext.addMessage(null, facesMessage);
                setFilter(true);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    
    public String savePostOverride() {
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
        String selectSQL = "select p from Post p where CONCAT(p.body, p.title) like :body";
        try {
            Query selectQuery = entityManager.createQuery(selectSQL);
            selectQuery.setParameter("body", "%"+post.getBody() + "%");
            posts = selectQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }
    
    public List getAllPosts() {
        List<Post> posts = new ArrayList();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String selectSQL = "select p from Post p order by p.id desc";
        try {
            Query selectQuery = entityManager.createQuery(selectSQL);
            posts = selectQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }
    
    public boolean HasBadWord() {
        if(post.getBody() == null) {
            return false;
        } else {
            boolean filter = post.validateBody();
            return filter;
        }
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

    /**
     * @return the filter
     */
    public boolean isFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(boolean filter) {
        this.filter = filter;
    }
    
    public void deletePost(Post post) {
    
        try {
            
            userTransaction.begin();
            EntityManager em = entityManagerFactory.createEntityManager();
            String selectSQL = "select a from Post a WHERE a.id = :id";
            Query selectQuery = em.createQuery(selectSQL);
            
            selectQuery.setParameter("id", post.getId());
            Post temp;
            temp = (Post)selectQuery.getSingleResult();
            
            em.remove(temp);
            try {
                userTransaction.commit();
            } catch (Exception e) {}
            em.close();
            
        } catch (NotSupportedException ex) {Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
}       catch (SystemException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        } 
     
    }
}