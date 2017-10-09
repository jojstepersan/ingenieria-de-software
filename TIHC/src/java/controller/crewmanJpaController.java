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
import entities.Ship;
import entities.TypeEmployee;
import entities.crewman;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class crewmanJpaController implements Serializable {

    public crewmanJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(crewman crewman) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ship codBarco = crewman.getCodBarco();
            if (codBarco != null) {
                codBarco = em.getReference(codBarco.getClass(), codBarco.getCodBarco());
                crewman.setCodBarco(codBarco);
            }
            TypeEmployee tipoEmpleado = crewman.getTipoEmpleado();
            if (tipoEmpleado != null) {
                tipoEmpleado = em.getReference(tipoEmpleado.getClass(), tipoEmpleado.getCodTipoEmpleado());
                crewman.setTipoEmpleado(tipoEmpleado);
            }
            em.persist(crewman);
            if (codBarco != null) {
                codBarco.getCrewmanList().add(crewman);
                codBarco = em.merge(codBarco);
            }
            if (tipoEmpleado != null) {
                tipoEmpleado.getCrewmanList().add(crewman);
                tipoEmpleado = em.merge(tipoEmpleado);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findcrewman(crewman.getCodEmpleado()) != null) {
                throw new PreexistingEntityException("crewman " + crewman + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(crewman crewman) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            crewman persistentcrewman = em.find(crewman.class, crewman.getCodEmpleado());
            Ship codBarcoOld = persistentcrewman.getCodBarco();
            Ship codBarcoNew = crewman.getCodBarco();
            TypeEmployee tipoEmpleadoOld = persistentcrewman.getTipoEmpleado();
            TypeEmployee tipoEmpleadoNew = crewman.getTipoEmpleado();
            if (codBarcoNew != null) {
                codBarcoNew = em.getReference(codBarcoNew.getClass(), codBarcoNew.getCodBarco());
                crewman.setCodBarco(codBarcoNew);
            }
            if (tipoEmpleadoNew != null) {
                tipoEmpleadoNew = em.getReference(tipoEmpleadoNew.getClass(), tipoEmpleadoNew.getCodTipoEmpleado());
                crewman.setTipoEmpleado(tipoEmpleadoNew);
            }
            crewman = em.merge(crewman);
            if (codBarcoOld != null && !codBarcoOld.equals(codBarcoNew)) {
                codBarcoOld.getCrewmanList().remove(crewman);
                codBarcoOld = em.merge(codBarcoOld);
            }
            if (codBarcoNew != null && !codBarcoNew.equals(codBarcoOld)) {
                codBarcoNew.getCrewmanList().add(crewman);
                codBarcoNew = em.merge(codBarcoNew);
            }
            if (tipoEmpleadoOld != null && !tipoEmpleadoOld.equals(tipoEmpleadoNew)) {
                tipoEmpleadoOld.getCrewmanList().remove(crewman);
                tipoEmpleadoOld = em.merge(tipoEmpleadoOld);
            }
            if (tipoEmpleadoNew != null && !tipoEmpleadoNew.equals(tipoEmpleadoOld)) {
                tipoEmpleadoNew.getCrewmanList().add(crewman);
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
                Integer id = crewman.getCodEmpleado();
                if (findcrewman(id) == null) {
                    throw new NonexistentEntityException("The crewman with id " + id + " no longer exists.");
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
            crewman crewman;
            try {
                crewman = em.getReference(crewman.class, id);
                crewman.getCodEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The crewman with id " + id + " no longer exists.", enfe);
            }
            Ship codBarco = crewman.getCodBarco();
            if (codBarco != null) {
                codBarco.getCrewmanList().remove(crewman);
                codBarco = em.merge(codBarco);
            }
            TypeEmployee tipoEmpleado = crewman.getTipoEmpleado();
            if (tipoEmpleado != null) {
                tipoEmpleado.getCrewmanList().remove(crewman);
                tipoEmpleado = em.merge(tipoEmpleado);
            }
            em.remove(crewman);
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

    public List<crewman> findcrewmanEntities() {
        return findcrewmanEntities(true, -1, -1);
    }

    public List<crewman> findcrewmanEntities(int maxResults, int firstResult) {
        return findcrewmanEntities(false, maxResults, firstResult);
    }

    private List<crewman> findcrewmanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(crewman.class));
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

    public crewman findcrewman(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(crewman.class, id);
        } finally {
            em.close();
        }
    }

    public int getcrewmanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<crewman> rt = cq.from(crewman.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
