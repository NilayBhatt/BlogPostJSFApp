/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Beans.Post;
import Beans.Tags;
import Jpa.exceptions.NonexistentEntityException;
import Jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author Nilay Bhatt
 */
@ManagedBean
public class TagsJpaController implements Serializable {

    public TagsJpaController() {
    }
    
    @Resource
    private final UserTransaction utx = null;
    
    @PersistenceUnit(name="HW3DBPU")
    private final EntityManagerFactory emf = null;

    @ManagedProperty(value = "#{tag}")
    private Tags tag;

    public void create() throws RollbackFailureException, Exception {
        try {
            utx.begin();
            EntityManager em = emf.createEntityManager();
            em.persist(tag);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    /**
     * @return the tags
     */
    public Tags getTag() {
        return tag;
    }

    /**
     * @param tags the tags to set
     */
    public void setTag(Tags tags) {
        this.tag = tags;
    }
 }

//    public void edit(Tags tags) throws NonexistentEntityException, RollbackFailureException, Exception {
//        EntityManager em = null;
//        try {
//            utx.begin();
//            em = getEntityManager();
//            tags = em.merge(tags);
//            utx.commit();
//        } catch (Exception ex) {
//            try {
//                utx.rollback();
//            } catch (Exception re) {
//                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
//            }
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Long id = tags.getId();
//                if (findTags(id) == null) {
//                    throw new NonexistentEntityException("The tags with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
//        EntityManager em = null;
//        try {
//            utx.begin();
//            em = getEntityManager();
//            Tags tags;
//            try {
//                tags = em.getReference(Tags.class, id);
//                tags.getId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The tags with id " + id + " no longer exists.", enfe);
//            }
//            em.remove(tags);
//            utx.commit();
//        } catch (Exception ex) {
//            try {
//                utx.rollback();
//            } catch (Exception re) {
//                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public List<Tags> findTagsEntities() {
//        return findTagsEntities(true, -1, -1);
//    }
//
//    public List<Tags> findTagsEntities(int maxResults, int firstResult) {
//        return findTagsEntities(false, maxResults, firstResult);
//    }
//
//    private List<Tags> findTagsEntities(boolean all, int maxResults, int firstResult) {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            cq.select(cq.from(Tags.class));
//            Query q = em.createQuery(cq);
//            if (!all) {
//                q.setMaxResults(maxResults);
//                q.setFirstResult(firstResult);
//            }
//            return q.getResultList();
//        } finally {
//            em.close();
//        }
//    }
//
//    public Tags findTags(Long id) {
//        EntityManager em = getEntityManager();
//        try {
//            return em.find(Tags.class, id);
//        } finally {
//            em.close();
//        }
//    }
//
//    public int getTagsCount() {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            Root<Tags> rt = cq.from(Tags.class);
//            cq.select(em.getCriteriaBuilder().count(rt));
//            Query q = em.createQuery(cq);
//            return ((Long) q.getSingleResult()).intValue();
//        } finally {
//            em.close();
//        }
//    }
//
//    
//}
