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
    public class PostJpaController implements Serializable {

        public PostJpaController() {
        }
        @Resource
        private UserTransaction utx;

        @PersistenceUnit(name = "HW3DBPU")
        private EntityManagerFactory emf;

        @ManagedProperty(value = "#{post}")
        private Post post;

        public String create() throws RollbackFailureException, Exception {
            String result = "Error";
            try {
                //TagsJpaController tJC = new TagsJpaController();
                //tJC.create();
                utx.begin();
                EntityManager em = emf.createEntityManager();
                em.persist(getPost());
                utx.commit();

                new TagsJpaController().create();

                result = "ConfirmationPage";
            } catch (Exception ex) {
                try {
                    utx.rollback();
                } catch (Exception re) {
                    throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
                }
                throw ex;
            } finally {
    //            if (em != null) {
    //                em.close();
                //}
            }

            return result;
        }

    //    public void edit(Post post) throws NonexistentEntityException, RollbackFailureException, Exception {
    //        EntityManager em = null;
    //        try {
    //            utx.begin();
    //            em = getEntityManager();
    //            post = em.merge(post);
    //            utx.commit();
    //        } catch (Exception ex) {
    //            try {
    //                utx.rollback();
    //            } catch (Exception re) {
    //                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
    //            }
    //            String msg = ex.getLocalizedMessage();
    //            if (msg == null || msg.length() == 0) {
    //                Integer id = post.getId();
    //                if (findPost(id) == null) {
    //                    throw new NonexistentEntityException("The post with id " + id + " no longer exists.");
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
    //    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
    //        EntityManager em = null;
    //        try {
    //            utx.begin();
    //            em = getEntityManager();
    //            Post post;
    //            try {
    //                post = em.getReference(Post.class, id);
    //                post.getId();
    //            } catch (EntityNotFoundException enfe) {
    //                throw new NonexistentEntityException("The post with id " + id + " no longer exists.", enfe);
    //            }
    //            em.remove(post);
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
    //    public List<Post> findPostEntities() {
    //        return findPostEntities(true, -1, -1);
    //    }
    //
    //    public List<Post> findPostEntities(int maxResults, int firstResult) {
    //        return findPostEntities(false, maxResults, firstResult);
    //    }
    //
    //    private List<Post> findPostEntities(boolean all, int maxResults, int firstResult) {
    //        EntityManager em = getEntityManager();
    //        try {
    //            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    //            cq.select(cq.from(Post.class));
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
    //    public Post findPost(Integer id) {
    //        EntityManager em = getEntityManager();
    //        try {
    //            return em.find(Post.class, id);
    //        } finally {
    //            em.close();
    //        }
    //    }
    //
    //    public int getPostCount() {
    //        EntityManager em = getEntityManager();
    //        try {
    //            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    //            Root<Post> rt = cq.from(Post.class);
    //            cq.select(em.getCriteriaBuilder().count(rt));
    //            Query q = em.createQuery(cq);
    //            return ((Long) q.getSingleResult()).intValue();
    //        } finally {
    //            em.close();
    //        }
    //    }
        /**
         * @return the post
         */
        public Post getPost() {
            return post;
        }

        /**
         * @param post the post to set
         */
        public void setPost(Post post) {
            this.post = post;
        }
    }
