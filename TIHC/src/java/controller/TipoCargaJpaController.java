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
import entities.Carga;
import entities.TipoCarga;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class TipoCargaJpaController implements Serializable {

    public TipoCargaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCarga tipoCarga) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tipoCarga.getCargaList() == null) {
            tipoCarga.setCargaList(new ArrayList<Carga>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Carga> attachedCargaList = new ArrayList<Carga>();
            for (Carga cargaListCargaToAttach : tipoCarga.getCargaList()) {
                cargaListCargaToAttach = em.getReference(cargaListCargaToAttach.getClass(), cargaListCargaToAttach.getCodCarga());
                attachedCargaList.add(cargaListCargaToAttach);
            }
            tipoCarga.setCargaList(attachedCargaList);
            em.persist(tipoCarga);
            for (Carga cargaListCarga : tipoCarga.getCargaList()) {
                TipoCarga oldCodTipoCargaOfCargaListCarga = cargaListCarga.getCodTipoCarga();
                cargaListCarga.setCodTipoCarga(tipoCarga);
                cargaListCarga = em.merge(cargaListCarga);
                if (oldCodTipoCargaOfCargaListCarga != null) {
                    oldCodTipoCargaOfCargaListCarga.getCargaList().remove(cargaListCarga);
                    oldCodTipoCargaOfCargaListCarga = em.merge(oldCodTipoCargaOfCargaListCarga);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoCarga(tipoCarga.getCodTipoCarga()) != null) {
                throw new PreexistingEntityException("TipoCarga " + tipoCarga + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoCarga tipoCarga) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoCarga persistentTipoCarga = em.find(TipoCarga.class, tipoCarga.getCodTipoCarga());
            List<Carga> cargaListOld = persistentTipoCarga.getCargaList();
            List<Carga> cargaListNew = tipoCarga.getCargaList();
            List<Carga> attachedCargaListNew = new ArrayList<Carga>();
            for (Carga cargaListNewCargaToAttach : cargaListNew) {
                cargaListNewCargaToAttach = em.getReference(cargaListNewCargaToAttach.getClass(), cargaListNewCargaToAttach.getCodCarga());
                attachedCargaListNew.add(cargaListNewCargaToAttach);
            }
            cargaListNew = attachedCargaListNew;
            tipoCarga.setCargaList(cargaListNew);
            tipoCarga = em.merge(tipoCarga);
            for (Carga cargaListOldCarga : cargaListOld) {
                if (!cargaListNew.contains(cargaListOldCarga)) {
                    cargaListOldCarga.setCodTipoCarga(null);
                    cargaListOldCarga = em.merge(cargaListOldCarga);
                }
            }
            for (Carga cargaListNewCarga : cargaListNew) {
                if (!cargaListOld.contains(cargaListNewCarga)) {
                    TipoCarga oldCodTipoCargaOfCargaListNewCarga = cargaListNewCarga.getCodTipoCarga();
                    cargaListNewCarga.setCodTipoCarga(tipoCarga);
                    cargaListNewCarga = em.merge(cargaListNewCarga);
                    if (oldCodTipoCargaOfCargaListNewCarga != null && !oldCodTipoCargaOfCargaListNewCarga.equals(tipoCarga)) {
                        oldCodTipoCargaOfCargaListNewCarga.getCargaList().remove(cargaListNewCarga);
                        oldCodTipoCargaOfCargaListNewCarga = em.merge(oldCodTipoCargaOfCargaListNewCarga);
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
                Integer id = tipoCarga.getCodTipoCarga();
                if (findTipoCarga(id) == null) {
                    throw new NonexistentEntityException("The tipoCarga with id " + id + " no longer exists.");
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
            TipoCarga tipoCarga;
            try {
                tipoCarga = em.getReference(TipoCarga.class, id);
                tipoCarga.getCodTipoCarga();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCarga with id " + id + " no longer exists.", enfe);
            }
            List<Carga> cargaList = tipoCarga.getCargaList();
            for (Carga cargaListCarga : cargaList) {
                cargaListCarga.setCodTipoCarga(null);
                cargaListCarga = em.merge(cargaListCarga);
            }
            em.remove(tipoCarga);
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

    public List<TipoCarga> findTipoCargaEntities() {
        return findTipoCargaEntities(true, -1, -1);
    }

    public List<TipoCarga> findTipoCargaEntities(int maxResults, int firstResult) {
        return findTipoCargaEntities(false, maxResults, firstResult);
    }

    private List<TipoCarga> findTipoCargaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCarga.class));
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

    public TipoCarga findTipoCarga(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCarga.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCargaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCarga> rt = cq.from(TipoCarga.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
