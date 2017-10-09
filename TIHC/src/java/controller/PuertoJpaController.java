/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Pais;
import entities.Puerto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class PuertoJpaController implements Serializable {

    public PuertoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Puerto puerto) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pais codPais = puerto.getCodPais();
            if (codPais != null) {
                codPais = em.getReference(codPais.getClass(), codPais.getCodPais());
                puerto.setCodPais(codPais);
            }
            em.persist(puerto);
            if (codPais != null) {
                codPais.getPuertoList().add(puerto);
                codPais = em.merge(codPais);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPuerto(puerto.getCodPuerto()) != null) {
                throw new PreexistingEntityException("Puerto " + puerto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Puerto puerto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Puerto persistentPuerto = em.find(Puerto.class, puerto.getCodPuerto());
            Pais codPaisOld = persistentPuerto.getCodPais();
            Pais codPaisNew = puerto.getCodPais();
            if (codPaisNew != null) {
                codPaisNew = em.getReference(codPaisNew.getClass(), codPaisNew.getCodPais());
                puerto.setCodPais(codPaisNew);
            }
            puerto = em.merge(puerto);
            if (codPaisOld != null && !codPaisOld.equals(codPaisNew)) {
                codPaisOld.getPuertoList().remove(puerto);
                codPaisOld = em.merge(codPaisOld);
            }
            if (codPaisNew != null && !codPaisNew.equals(codPaisOld)) {
                codPaisNew.getPuertoList().add(puerto);
                codPaisNew = em.merge(codPaisNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = puerto.getCodPuerto();
                if (findPuerto(id) == null) {
                    throw new NonexistentEntityException("The puerto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Puerto puerto;
            try {
                puerto = em.getReference(Puerto.class, id);
                puerto.getCodPuerto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puerto with id " + id + " no longer exists.", enfe);
            }
            Pais codPais = puerto.getCodPais();
            if (codPais != null) {
                codPais.getPuertoList().remove(puerto);
                codPais = em.merge(codPais);
            }
            em.remove(puerto);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Puerto> findPuertoEntities() {
        return findPuertoEntities(true, -1, -1);
    }

    public List<Puerto> findPuertoEntities(int maxResults, int firstResult) {
        return findPuertoEntities(false, maxResults, firstResult);
    }

    private List<Puerto> findPuertoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Puerto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Puerto findPuerto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Puerto.class, id);
        } finally {
            em.close();
        }
    }

    public int getPuertoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Puerto> rt = cq.from(Puerto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
