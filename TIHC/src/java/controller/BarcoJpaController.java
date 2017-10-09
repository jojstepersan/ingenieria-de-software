/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entities.Barco;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Estado;
import entities.Tripulante;
import java.util.ArrayList;
import java.util.List;
import entities.Carga;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class BarcoJpaController implements Serializable {

    public BarcoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Barco barco) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (barco.getTripulanteList() == null) {
            barco.setTripulanteList(new ArrayList<Tripulante>());
        }
        if (barco.getCargaList() == null) {
            barco.setCargaList(new ArrayList<Carga>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Estado codEstado = barco.getCodEstado();
            if (codEstado != null) {
                codEstado = em.getReference(codEstado.getClass(), codEstado.getCodEstado());
                barco.setCodEstado(codEstado);
            }
            List<Tripulante> attachedTripulanteList = new ArrayList<Tripulante>();
            for (Tripulante tripulanteListTripulanteToAttach : barco.getTripulanteList()) {
                tripulanteListTripulanteToAttach = em.getReference(tripulanteListTripulanteToAttach.getClass(), tripulanteListTripulanteToAttach.getCodEmpleado());
                attachedTripulanteList.add(tripulanteListTripulanteToAttach);
            }
            barco.setTripulanteList(attachedTripulanteList);
            List<Carga> attachedCargaList = new ArrayList<Carga>();
            for (Carga cargaListCargaToAttach : barco.getCargaList()) {
                cargaListCargaToAttach = em.getReference(cargaListCargaToAttach.getClass(), cargaListCargaToAttach.getCodCarga());
                attachedCargaList.add(cargaListCargaToAttach);
            }
            barco.setCargaList(attachedCargaList);
            em.persist(barco);
            if (codEstado != null) {
                codEstado.getBarcoList().add(barco);
                codEstado = em.merge(codEstado);
            }
            for (Tripulante tripulanteListTripulante : barco.getTripulanteList()) {
                Barco oldCodBarcoOfTripulanteListTripulante = tripulanteListTripulante.getCodBarco();
                tripulanteListTripulante.setCodBarco(barco);
                tripulanteListTripulante = em.merge(tripulanteListTripulante);
                if (oldCodBarcoOfTripulanteListTripulante != null) {
                    oldCodBarcoOfTripulanteListTripulante.getTripulanteList().remove(tripulanteListTripulante);
                    oldCodBarcoOfTripulanteListTripulante = em.merge(oldCodBarcoOfTripulanteListTripulante);
                }
            }
            for (Carga cargaListCarga : barco.getCargaList()) {
                Barco oldCodBarcoOfCargaListCarga = cargaListCarga.getCodBarco();
                cargaListCarga.setCodBarco(barco);
                cargaListCarga = em.merge(cargaListCarga);
                if (oldCodBarcoOfCargaListCarga != null) {
                    oldCodBarcoOfCargaListCarga.getCargaList().remove(cargaListCarga);
                    oldCodBarcoOfCargaListCarga = em.merge(oldCodBarcoOfCargaListCarga);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBarco(barco.getCodBarco()) != null) {
                throw new PreexistingEntityException("Barco " + barco + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Barco barco) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Barco persistentBarco = em.find(Barco.class, barco.getCodBarco());
            Estado codEstadoOld = persistentBarco.getCodEstado();
            Estado codEstadoNew = barco.getCodEstado();
            List<Tripulante> tripulanteListOld = persistentBarco.getTripulanteList();
            List<Tripulante> tripulanteListNew = barco.getTripulanteList();
            List<Carga> cargaListOld = persistentBarco.getCargaList();
            List<Carga> cargaListNew = barco.getCargaList();
            if (codEstadoNew != null) {
                codEstadoNew = em.getReference(codEstadoNew.getClass(), codEstadoNew.getCodEstado());
                barco.setCodEstado(codEstadoNew);
            }
            List<Tripulante> attachedTripulanteListNew = new ArrayList<Tripulante>();
            for (Tripulante tripulanteListNewTripulanteToAttach : tripulanteListNew) {
                tripulanteListNewTripulanteToAttach = em.getReference(tripulanteListNewTripulanteToAttach.getClass(), tripulanteListNewTripulanteToAttach.getCodEmpleado());
                attachedTripulanteListNew.add(tripulanteListNewTripulanteToAttach);
            }
            tripulanteListNew = attachedTripulanteListNew;
            barco.setTripulanteList(tripulanteListNew);
            List<Carga> attachedCargaListNew = new ArrayList<Carga>();
            for (Carga cargaListNewCargaToAttach : cargaListNew) {
                cargaListNewCargaToAttach = em.getReference(cargaListNewCargaToAttach.getClass(), cargaListNewCargaToAttach.getCodCarga());
                attachedCargaListNew.add(cargaListNewCargaToAttach);
            }
            cargaListNew = attachedCargaListNew;
            barco.setCargaList(cargaListNew);
            barco = em.merge(barco);
            if (codEstadoOld != null && !codEstadoOld.equals(codEstadoNew)) {
                codEstadoOld.getBarcoList().remove(barco);
                codEstadoOld = em.merge(codEstadoOld);
            }
            if (codEstadoNew != null && !codEstadoNew.equals(codEstadoOld)) {
                codEstadoNew.getBarcoList().add(barco);
                codEstadoNew = em.merge(codEstadoNew);
            }
            for (Tripulante tripulanteListOldTripulante : tripulanteListOld) {
                if (!tripulanteListNew.contains(tripulanteListOldTripulante)) {
                    tripulanteListOldTripulante.setCodBarco(null);
                    tripulanteListOldTripulante = em.merge(tripulanteListOldTripulante);
                }
            }
            for (Tripulante tripulanteListNewTripulante : tripulanteListNew) {
                if (!tripulanteListOld.contains(tripulanteListNewTripulante)) {
                    Barco oldCodBarcoOfTripulanteListNewTripulante = tripulanteListNewTripulante.getCodBarco();
                    tripulanteListNewTripulante.setCodBarco(barco);
                    tripulanteListNewTripulante = em.merge(tripulanteListNewTripulante);
                    if (oldCodBarcoOfTripulanteListNewTripulante != null && !oldCodBarcoOfTripulanteListNewTripulante.equals(barco)) {
                        oldCodBarcoOfTripulanteListNewTripulante.getTripulanteList().remove(tripulanteListNewTripulante);
                        oldCodBarcoOfTripulanteListNewTripulante = em.merge(oldCodBarcoOfTripulanteListNewTripulante);
                    }
                }
            }
            for (Carga cargaListOldCarga : cargaListOld) {
                if (!cargaListNew.contains(cargaListOldCarga)) {
                    cargaListOldCarga.setCodBarco(null);
                    cargaListOldCarga = em.merge(cargaListOldCarga);
                }
            }
            for (Carga cargaListNewCarga : cargaListNew) {
                if (!cargaListOld.contains(cargaListNewCarga)) {
                    Barco oldCodBarcoOfCargaListNewCarga = cargaListNewCarga.getCodBarco();
                    cargaListNewCarga.setCodBarco(barco);
                    cargaListNewCarga = em.merge(cargaListNewCarga);
                    if (oldCodBarcoOfCargaListNewCarga != null && !oldCodBarcoOfCargaListNewCarga.equals(barco)) {
                        oldCodBarcoOfCargaListNewCarga.getCargaList().remove(cargaListNewCarga);
                        oldCodBarcoOfCargaListNewCarga = em.merge(oldCodBarcoOfCargaListNewCarga);
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
                Integer id = barco.getCodBarco();
                if (findBarco(id) == null) {
                    throw new NonexistentEntityException("The barco with id " + id + " no longer exists.");
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
            Barco barco;
            try {
                barco = em.getReference(Barco.class, id);
                barco.getCodBarco();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barco with id " + id + " no longer exists.", enfe);
            }
            Estado codEstado = barco.getCodEstado();
            if (codEstado != null) {
                codEstado.getBarcoList().remove(barco);
                codEstado = em.merge(codEstado);
            }
            List<Tripulante> tripulanteList = barco.getTripulanteList();
            for (Tripulante tripulanteListTripulante : tripulanteList) {
                tripulanteListTripulante.setCodBarco(null);
                tripulanteListTripulante = em.merge(tripulanteListTripulante);
            }
            List<Carga> cargaList = barco.getCargaList();
            for (Carga cargaListCarga : cargaList) {
                cargaListCarga.setCodBarco(null);
                cargaListCarga = em.merge(cargaListCarga);
            }
            em.remove(barco);
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

    public List<Barco> findBarcoEntities() {
        return findBarcoEntities(true, -1, -1);
    }

    public List<Barco> findBarcoEntities(int maxResults, int firstResult) {
        return findBarcoEntities(false, maxResults, firstResult);
    }

    private List<Barco> findBarcoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Barco.class));
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

    public Barco findBarco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Barco.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarcoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barco> rt = cq.from(Barco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
