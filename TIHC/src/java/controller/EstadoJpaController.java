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
import entities.Barco;
import entities.Estado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class EstadoJpaController implements Serializable {

    public EstadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (estado.getBarcoList() == null) {
            estado.setBarcoList(new ArrayList<Barco>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Barco> attachedBarcoList = new ArrayList<Barco>();
            for (Barco barcoListBarcoToAttach : estado.getBarcoList()) {
                barcoListBarcoToAttach = em.getReference(barcoListBarcoToAttach.getClass(), barcoListBarcoToAttach.getCodBarco());
                attachedBarcoList.add(barcoListBarcoToAttach);
            }
            estado.setBarcoList(attachedBarcoList);
            em.persist(estado);
            for (Barco barcoListBarco : estado.getBarcoList()) {
                Estado oldCodEstadoOfBarcoListBarco = barcoListBarco.getCodEstado();
                barcoListBarco.setCodEstado(estado);
                barcoListBarco = em.merge(barcoListBarco);
                if (oldCodEstadoOfBarcoListBarco != null) {
                    oldCodEstadoOfBarcoListBarco.getBarcoList().remove(barcoListBarco);
                    oldCodEstadoOfBarcoListBarco = em.merge(oldCodEstadoOfBarcoListBarco);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEstado(estado.getCodEstado()) != null) {
                throw new PreexistingEntityException("Estado " + estado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estado estado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Estado persistentEstado = em.find(Estado.class, estado.getCodEstado());
            List<Barco> barcoListOld = persistentEstado.getBarcoList();
            List<Barco> barcoListNew = estado.getBarcoList();
            List<Barco> attachedBarcoListNew = new ArrayList<Barco>();
            for (Barco barcoListNewBarcoToAttach : barcoListNew) {
                barcoListNewBarcoToAttach = em.getReference(barcoListNewBarcoToAttach.getClass(), barcoListNewBarcoToAttach.getCodBarco());
                attachedBarcoListNew.add(barcoListNewBarcoToAttach);
            }
            barcoListNew = attachedBarcoListNew;
            estado.setBarcoList(barcoListNew);
            estado = em.merge(estado);
            for (Barco barcoListOldBarco : barcoListOld) {
                if (!barcoListNew.contains(barcoListOldBarco)) {
                    barcoListOldBarco.setCodEstado(null);
                    barcoListOldBarco = em.merge(barcoListOldBarco);
                }
            }
            for (Barco barcoListNewBarco : barcoListNew) {
                if (!barcoListOld.contains(barcoListNewBarco)) {
                    Estado oldCodEstadoOfBarcoListNewBarco = barcoListNewBarco.getCodEstado();
                    barcoListNewBarco.setCodEstado(estado);
                    barcoListNewBarco = em.merge(barcoListNewBarco);
                    if (oldCodEstadoOfBarcoListNewBarco != null && !oldCodEstadoOfBarcoListNewBarco.equals(estado)) {
                        oldCodEstadoOfBarcoListNewBarco.getBarcoList().remove(barcoListNewBarco);
                        oldCodEstadoOfBarcoListNewBarco = em.merge(oldCodEstadoOfBarcoListNewBarco);
                    }
                }
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
                Integer id = estado.getCodEstado();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getCodEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            List<Barco> barcoList = estado.getBarcoList();
            for (Barco barcoListBarco : barcoList) {
                barcoListBarco.setCodEstado(null);
                barcoListBarco = em.merge(barcoListBarco);
            }
            em.remove(estado);
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

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
