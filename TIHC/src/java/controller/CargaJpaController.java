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
import entities.Carga;
import entities.Contrato;
import entities.Pais;
import entities.TipoCarga;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class CargaJpaController implements Serializable {

    public CargaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carga carga) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Barco codBarco = carga.getCodBarco();
            if (codBarco != null) {
                codBarco = em.getReference(codBarco.getClass(), codBarco.getCodBarco());
                carga.setCodBarco(codBarco);
            }
            Contrato codContrato = carga.getCodContrato();
            if (codContrato != null) {
                codContrato = em.getReference(codContrato.getClass(), codContrato.getCodContrato());
                carga.setCodContrato(codContrato);
            }
            Pais destino = carga.getDestino();
            if (destino != null) {
                destino = em.getReference(destino.getClass(), destino.getCodPais());
                carga.setDestino(destino);
            }
            Pais origen = carga.getOrigen();
            if (origen != null) {
                origen = em.getReference(origen.getClass(), origen.getCodPais());
                carga.setOrigen(origen);
            }
            TipoCarga codTipoCarga = carga.getCodTipoCarga();
            if (codTipoCarga != null) {
                codTipoCarga = em.getReference(codTipoCarga.getClass(), codTipoCarga.getCodTipoCarga());
                carga.setCodTipoCarga(codTipoCarga);
            }
            em.persist(carga);
            if (codBarco != null) {
                codBarco.getCargaList().add(carga);
                codBarco = em.merge(codBarco);
            }
            if (codContrato != null) {
                codContrato.getCargaList().add(carga);
                codContrato = em.merge(codContrato);
            }
            if (destino != null) {
                destino.getCargaList().add(carga);
                destino = em.merge(destino);
            }
            if (origen != null) {
                origen.getCargaList().add(carga);
                origen = em.merge(origen);
            }
            if (codTipoCarga != null) {
                codTipoCarga.getCargaList().add(carga);
                codTipoCarga = em.merge(codTipoCarga);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCarga(carga.getCodCarga()) != null) {
                throw new PreexistingEntityException("Carga " + carga + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carga carga) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Carga persistentCarga = em.find(Carga.class, carga.getCodCarga());
            Barco codBarcoOld = persistentCarga.getCodBarco();
            Barco codBarcoNew = carga.getCodBarco();
            Contrato codContratoOld = persistentCarga.getCodContrato();
            Contrato codContratoNew = carga.getCodContrato();
            Pais destinoOld = persistentCarga.getDestino();
            Pais destinoNew = carga.getDestino();
            Pais origenOld = persistentCarga.getOrigen();
            Pais origenNew = carga.getOrigen();
            TipoCarga codTipoCargaOld = persistentCarga.getCodTipoCarga();
            TipoCarga codTipoCargaNew = carga.getCodTipoCarga();
            if (codBarcoNew != null) {
                codBarcoNew = em.getReference(codBarcoNew.getClass(), codBarcoNew.getCodBarco());
                carga.setCodBarco(codBarcoNew);
            }
            if (codContratoNew != null) {
                codContratoNew = em.getReference(codContratoNew.getClass(), codContratoNew.getCodContrato());
                carga.setCodContrato(codContratoNew);
            }
            if (destinoNew != null) {
                destinoNew = em.getReference(destinoNew.getClass(), destinoNew.getCodPais());
                carga.setDestino(destinoNew);
            }
            if (origenNew != null) {
                origenNew = em.getReference(origenNew.getClass(), origenNew.getCodPais());
                carga.setOrigen(origenNew);
            }
            if (codTipoCargaNew != null) {
                codTipoCargaNew = em.getReference(codTipoCargaNew.getClass(), codTipoCargaNew.getCodTipoCarga());
                carga.setCodTipoCarga(codTipoCargaNew);
            }
            carga = em.merge(carga);
            if (codBarcoOld != null && !codBarcoOld.equals(codBarcoNew)) {
                codBarcoOld.getCargaList().remove(carga);
                codBarcoOld = em.merge(codBarcoOld);
            }
            if (codBarcoNew != null && !codBarcoNew.equals(codBarcoOld)) {
                codBarcoNew.getCargaList().add(carga);
                codBarcoNew = em.merge(codBarcoNew);
            }
            if (codContratoOld != null && !codContratoOld.equals(codContratoNew)) {
                codContratoOld.getCargaList().remove(carga);
                codContratoOld = em.merge(codContratoOld);
            }
            if (codContratoNew != null && !codContratoNew.equals(codContratoOld)) {
                codContratoNew.getCargaList().add(carga);
                codContratoNew = em.merge(codContratoNew);
            }
            if (destinoOld != null && !destinoOld.equals(destinoNew)) {
                destinoOld.getCargaList().remove(carga);
                destinoOld = em.merge(destinoOld);
            }
            if (destinoNew != null && !destinoNew.equals(destinoOld)) {
                destinoNew.getCargaList().add(carga);
                destinoNew = em.merge(destinoNew);
            }
            if (origenOld != null && !origenOld.equals(origenNew)) {
                origenOld.getCargaList().remove(carga);
                origenOld = em.merge(origenOld);
            }
            if (origenNew != null && !origenNew.equals(origenOld)) {
                origenNew.getCargaList().add(carga);
                origenNew = em.merge(origenNew);
            }
            if (codTipoCargaOld != null && !codTipoCargaOld.equals(codTipoCargaNew)) {
                codTipoCargaOld.getCargaList().remove(carga);
                codTipoCargaOld = em.merge(codTipoCargaOld);
            }
            if (codTipoCargaNew != null && !codTipoCargaNew.equals(codTipoCargaOld)) {
                codTipoCargaNew.getCargaList().add(carga);
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
                Integer id = carga.getCodCarga();
                if (findCarga(id) == null) {
                    throw new NonexistentEntityException("The carga with id " + id + " no longer exists.");
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
            Carga carga;
            try {
                carga = em.getReference(Carga.class, id);
                carga.getCodCarga();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carga with id " + id + " no longer exists.", enfe);
            }
            Barco codBarco = carga.getCodBarco();
            if (codBarco != null) {
                codBarco.getCargaList().remove(carga);
                codBarco = em.merge(codBarco);
            }
            Contrato codContrato = carga.getCodContrato();
            if (codContrato != null) {
                codContrato.getCargaList().remove(carga);
                codContrato = em.merge(codContrato);
            }
            Pais destino = carga.getDestino();
            if (destino != null) {
                destino.getCargaList().remove(carga);
                destino = em.merge(destino);
            }
            Pais origen = carga.getOrigen();
            if (origen != null) {
                origen.getCargaList().remove(carga);
                origen = em.merge(origen);
            }
            TipoCarga codTipoCarga = carga.getCodTipoCarga();
            if (codTipoCarga != null) {
                codTipoCarga.getCargaList().remove(carga);
                codTipoCarga = em.merge(codTipoCarga);
            }
            em.remove(carga);
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

    public List<Carga> findCargaEntities() {
        return findCargaEntities(true, -1, -1);
    }

    public List<Carga> findCargaEntities(int maxResults, int firstResult) {
        return findCargaEntities(false, maxResults, firstResult);
    }

    private List<Carga> findCargaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carga.class));
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

    public Carga findCarga(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carga.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carga> rt = cq.from(Carga.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
