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

                //new TagsJpaController().create();

                result = "ConfirmationPage";
            } catch (Exception ex) {
                try {
                    utx.rollback();
                } catch (Exception re) {
                    throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
                }
                throw ex;
            } finally {
            }

            return result;
        }
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
