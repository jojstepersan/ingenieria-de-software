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
import entities.Country;
import entities.Port;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class PortJpaController implements Serializable {

    public PortJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Port port) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Country codPais = port.getCodPais();
            if (codPais != null) {
                codPais = em.getReference(codPais.getClass(), codPais.getCodPais());
                port.setCodPais(codPais);
            }
            em.persist(port);
            if (codPais != null) {
                codPais.getPortList().add(port);
                codPais = em.merge(codPais);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPort(port.getCodPuerto()) != null) {
                throw new PreexistingEntityException("Port " + port + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Port port) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Port persistentPort = em.find(Port.class, port.getCodPuerto());
            Country codPaisOld = persistentPort.getCodPais();
            Country codPaisNew = port.getCodPais();
            if (codPaisNew != null) {
                codPaisNew = em.getReference(codPaisNew.getClass(), codPaisNew.getCodPais());
                port.setCodPais(codPaisNew);
            }
            port = em.merge(port);
            if (codPaisOld != null && !codPaisOld.equals(codPaisNew)) {
                codPaisOld.getPortList().remove(port);
                codPaisOld = em.merge(codPaisOld);
            }
            if (codPaisNew != null && !codPaisNew.equals(codPaisOld)) {
                codPaisNew.getPortList().add(port);
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
                Integer id = port.getCodPuerto();
                if (findPort(id) == null) {
                    throw new NonexistentEntityException("The port with id " + id + " no longer exists.");
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
            Port port;
            try {
                port = em.getReference(Port.class, id);
                port.getCodPuerto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The port with id " + id + " no longer exists.", enfe);
            }
            Country codPais = port.getCodPais();
            if (codPais != null) {
                codPais.getPortList().remove(port);
                codPais = em.merge(codPais);
            }
            em.remove(port);
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

    public List<Port> findPortEntities() {
        return findPortEntities(true, -1, -1);
    }

    public List<Port> findPortEntities(int maxResults, int firstResult) {
        return findPortEntities(false, maxResults, firstResult);
    }

    private List<Port> findPortEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Port.class));
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

    public Port findPort(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Port.class, id);
        } finally {
            em.close();
        }
    }

    public int getPortCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Port> rt = cq.from(Port.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
