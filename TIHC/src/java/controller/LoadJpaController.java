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
import entities.Contract;
import entities.Country;
import entities.Load;
import entities.TypeLoad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class LoadJpaController implements Serializable {

    public LoadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Load load) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ship codBarco = load.getCodBarco();
            if (codBarco != null) {
                codBarco = em.getReference(codBarco.getClass(), codBarco.getCodBarco());
                load.setCodBarco(codBarco);
            }
            Contract codContrato = load.getCodContrato();
            if (codContrato != null) {
                codContrato = em.getReference(codContrato.getClass(), codContrato.getCodContrato());
                load.setCodContrato(codContrato);
            }
            Country destino = load.getDestino();
            if (destino != null) {
                destino = em.getReference(destino.getClass(), destino.getCodPais());
                load.setDestino(destino);
            }
            Country origen = load.getOrigen();
            if (origen != null) {
                origen = em.getReference(origen.getClass(), origen.getCodPais());
                load.setOrigen(origen);
            }
            TypeLoad codTipoCarga = load.getCodTipoCarga();
            if (codTipoCarga != null) {
                codTipoCarga = em.getReference(codTipoCarga.getClass(), codTipoCarga.getCodTipoCarga());
                load.setCodTipoCarga(codTipoCarga);
            }
            em.persist(load);
            if (codBarco != null) {
                codBarco.getLoadList().add(load);
                codBarco = em.merge(codBarco);
            }
            if (codContrato != null) {
                codContrato.getLoadList().add(load);
                codContrato = em.merge(codContrato);
            }
            if (destino != null) {
                destino.getLoadList().add(load);
                destino = em.merge(destino);
            }
            if (origen != null) {
                origen.getLoadList().add(load);
                origen = em.merge(origen);
            }
            if (codTipoCarga != null) {
                codTipoCarga.getLoadList().add(load);
                codTipoCarga = em.merge(codTipoCarga);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLoad(load.getCodCarga()) != null) {
                throw new PreexistingEntityException("Load " + load + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Load load) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Load persistentLoad = em.find(Load.class, load.getCodCarga());
            Ship codBarcoOld = persistentLoad.getCodBarco();
            Ship codBarcoNew = load.getCodBarco();
            Contract codContratoOld = persistentLoad.getCodContrato();
            Contract codContratoNew = load.getCodContrato();
            Country destinoOld = persistentLoad.getDestino();
            Country destinoNew = load.getDestino();
            Country origenOld = persistentLoad.getOrigen();
            Country origenNew = load.getOrigen();
            TypeLoad codTipoCargaOld = persistentLoad.getCodTipoCarga();
            TypeLoad codTipoCargaNew = load.getCodTipoCarga();
            if (codBarcoNew != null) {
                codBarcoNew = em.getReference(codBarcoNew.getClass(), codBarcoNew.getCodBarco());
                load.setCodBarco(codBarcoNew);
            }
            if (codContratoNew != null) {
                codContratoNew = em.getReference(codContratoNew.getClass(), codContratoNew.getCodContrato());
                load.setCodContrato(codContratoNew);
            }
            if (destinoNew != null) {
                destinoNew = em.getReference(destinoNew.getClass(), destinoNew.getCodPais());
                load.setDestino(destinoNew);
            }
            if (origenNew != null) {
                origenNew = em.getReference(origenNew.getClass(), origenNew.getCodPais());
                load.setOrigen(origenNew);
            }
            if (codTipoCargaNew != null) {
                codTipoCargaNew = em.getReference(codTipoCargaNew.getClass(), codTipoCargaNew.getCodTipoCarga());
                load.setCodTipoCarga(codTipoCargaNew);
            }
            load = em.merge(load);
            if (codBarcoOld != null && !codBarcoOld.equals(codBarcoNew)) {
                codBarcoOld.getLoadList().remove(load);
                codBarcoOld = em.merge(codBarcoOld);
            }
            if (codBarcoNew != null && !codBarcoNew.equals(codBarcoOld)) {
                codBarcoNew.getLoadList().add(load);
                codBarcoNew = em.merge(codBarcoNew);
            }
            if (codContratoOld != null && !codContratoOld.equals(codContratoNew)) {
                codContratoOld.getLoadList().remove(load);
                codContratoOld = em.merge(codContratoOld);
            }
            if (codContratoNew != null && !codContratoNew.equals(codContratoOld)) {
                codContratoNew.getLoadList().add(load);
                codContratoNew = em.merge(codContratoNew);
            }
            if (destinoOld != null && !destinoOld.equals(destinoNew)) {
                destinoOld.getLoadList().remove(load);
                destinoOld = em.merge(destinoOld);
            }
            if (destinoNew != null && !destinoNew.equals(destinoOld)) {
                destinoNew.getLoadList().add(load);
                destinoNew = em.merge(destinoNew);
            }
            if (origenOld != null && !origenOld.equals(origenNew)) {
                origenOld.getLoadList().remove(load);
                origenOld = em.merge(origenOld);
            }
            if (origenNew != null && !origenNew.equals(origenOld)) {
                origenNew.getLoadList().add(load);
                origenNew = em.merge(origenNew);
            }
            if (codTipoCargaOld != null && !codTipoCargaOld.equals(codTipoCargaNew)) {
                codTipoCargaOld.getLoadList().remove(load);
                codTipoCargaOld = em.merge(codTipoCargaOld);
            }
            if (codTipoCargaNew != null && !codTipoCargaNew.equals(codTipoCargaOld)) {
                codTipoCargaNew.getLoadList().add(load);
                codTipoCargaNew = em.merge(codTipoCargaNew);
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
                Integer id = load.getCodCarga();
                if (findLoad(id) == null) {
                    throw new NonexistentEntityException("The load with id " + id + " no longer exists.");
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
            Load load;
            try {
                load = em.getReference(Load.class, id);
                load.getCodCarga();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The load with id " + id + " no longer exists.", enfe);
            }
            Ship codBarco = load.getCodBarco();
            if (codBarco != null) {
                codBarco.getLoadList().remove(load);
                codBarco = em.merge(codBarco);
            }
            Contract codContrato = load.getCodContrato();
            if (codContrato != null) {
                codContrato.getLoadList().remove(load);
                codContrato = em.merge(codContrato);
            }
            Country destino = load.getDestino();
            if (destino != null) {
                destino.getLoadList().remove(load);
                destino = em.merge(destino);
            }
            Country origen = load.getOrigen();
            if (origen != null) {
                origen.getLoadList().remove(load);
                origen = em.merge(origen);
            }
            TypeLoad codTipoCarga = load.getCodTipoCarga();
            if (codTipoCarga != null) {
                codTipoCarga.getLoadList().remove(load);
                codTipoCarga = em.merge(codTipoCarga);
            }
            em.remove(load);
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

    public List<Load> findLoadEntities() {
        return findLoadEntities(true, -1, -1);
    }

    public List<Load> findLoadEntities(int maxResults, int firstResult) {
        return findLoadEntities(false, maxResults, firstResult);
    }

    private List<Load> findLoadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Load.class));
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

    public Load findLoad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Load.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Load> rt = cq.from(Load.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
