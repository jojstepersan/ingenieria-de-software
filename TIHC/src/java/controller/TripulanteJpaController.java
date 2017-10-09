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
import entities.TipoEmpleado;
import entities.Tripulante;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class TripulanteJpaController implements Serializable {

    public TripulanteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tripulante tripulante) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Barco codBarco = tripulante.getCodBarco();
            if (codBarco != null) {
                codBarco = em.getReference(codBarco.getClass(), codBarco.getCodBarco());
                tripulante.setCodBarco(codBarco);
            }
            TipoEmpleado tipoEmpleado = tripulante.getTipoEmpleado();
            if (tipoEmpleado != null) {
                tipoEmpleado = em.getReference(tipoEmpleado.getClass(), tipoEmpleado.getCodTipoEmpleado());
                tripulante.setTipoEmpleado(tipoEmpleado);
            }
            em.persist(tripulante);
            if (codBarco != null) {
                codBarco.getTripulanteList().add(tripulante);
                codBarco = em.merge(codBarco);
            }
            if (tipoEmpleado != null) {
                tipoEmpleado.getTripulanteList().add(tripulante);
                tipoEmpleado = em.merge(tipoEmpleado);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTripulante(tripulante.getCodEmpleado()) != null) {
                throw new PreexistingEntityException("Tripulante " + tripulante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tripulante tripulante) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tripulante persistentTripulante = em.find(Tripulante.class, tripulante.getCodEmpleado());
            Barco codBarcoOld = persistentTripulante.getCodBarco();
            Barco codBarcoNew = tripulante.getCodBarco();
            TipoEmpleado tipoEmpleadoOld = persistentTripulante.getTipoEmpleado();
            TipoEmpleado tipoEmpleadoNew = tripulante.getTipoEmpleado();
            if (codBarcoNew != null) {
                codBarcoNew = em.getReference(codBarcoNew.getClass(), codBarcoNew.getCodBarco());
                tripulante.setCodBarco(codBarcoNew);
            }
            if (tipoEmpleadoNew != null) {
                tipoEmpleadoNew = em.getReference(tipoEmpleadoNew.getClass(), tipoEmpleadoNew.getCodTipoEmpleado());
                tripulante.setTipoEmpleado(tipoEmpleadoNew);
            }
            tripulante = em.merge(tripulante);
            if (codBarcoOld != null && !codBarcoOld.equals(codBarcoNew)) {
                codBarcoOld.getTripulanteList().remove(tripulante);
                codBarcoOld = em.merge(codBarcoOld);
            }
            if (codBarcoNew != null && !codBarcoNew.equals(codBarcoOld)) {
                codBarcoNew.getTripulanteList().add(tripulante);
                codBarcoNew = em.merge(codBarcoNew);
            }
            if (tipoEmpleadoOld != null && !tipoEmpleadoOld.equals(tipoEmpleadoNew)) {
                tipoEmpleadoOld.getTripulanteList().remove(tripulante);
                tipoEmpleadoOld = em.merge(tipoEmpleadoOld);
            }
            if (tipoEmpleadoNew != null && !tipoEmpleadoNew.equals(tipoEmpleadoOld)) {
                tipoEmpleadoNew.getTripulanteList().add(tripulante);
                tipoEmpleadoNew = em.merge(tipoEmpleadoNew);
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
                Integer id = tripulante.getCodEmpleado();
                if (findTripulante(id) == null) {
                    throw new NonexistentEntityException("The tripulante with id " + id + " no longer exists.");
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
            Tripulante tripulante;
            try {
                tripulante = em.getReference(Tripulante.class, id);
                tripulante.getCodEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tripulante with id " + id + " no longer exists.", enfe);
            }
            Barco codBarco = tripulante.getCodBarco();
            if (codBarco != null) {
                codBarco.getTripulanteList().remove(tripulante);
                codBarco = em.merge(codBarco);
            }
            TipoEmpleado tipoEmpleado = tripulante.getTipoEmpleado();
            if (tipoEmpleado != null) {
                tipoEmpleado.getTripulanteList().remove(tripulante);
                tipoEmpleado = em.merge(tipoEmpleado);
            }
            em.remove(tripulante);
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

    public List<Tripulante> findTripulanteEntities() {
        return findTripulanteEntities(true, -1, -1);
    }

    public List<Tripulante> findTripulanteEntities(int maxResults, int firstResult) {
        return findTripulanteEntities(false, maxResults, firstResult);
    }

    private List<Tripulante> findTripulanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tripulante.class));
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

    public Tripulante findTripulante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tripulante.class, id);
        } finally {
            em.close();
        }
    }

    public int getTripulanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tripulante> rt = cq.from(Tripulante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
