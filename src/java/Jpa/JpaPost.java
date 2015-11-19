/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;
import Beans.Post;
import Jpa.exceptions.NonexistentEntityException;
import Jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author Nilay Bhatt
 */
public class JpaPost {
    
}
