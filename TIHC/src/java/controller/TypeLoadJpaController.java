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
import entities.Load;
import entities.TypeLoad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class TypeLoadJpaController implements Serializable {

    public TypeLoadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TypeLoad typeLoad) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (typeLoad.getLoadList() == null) {
            typeLoad.setLoadList(new ArrayList<Load>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Load> attachedLoadList = new ArrayList<Load>();
            for (Load loadListLoadToAttach : typeLoad.getLoadList()) {
                loadListLoadToAttach = em.getReference(loadListLoadToAttach.getClass(), loadListLoadToAttach.getCodCarga());
                attachedLoadList.add(loadListLoadToAttach);
            }
            typeLoad.setLoadList(attachedLoadList);
            em.persist(typeLoad);
            for (Load loadListLoad : typeLoad.getLoadList()) {
                TypeLoad oldCodTipoCargaOfLoadListLoad = loadListLoad.getCodTipoCarga();
                loadListLoad.setCodTipoCarga(typeLoad);
                loadListLoad = em.merge(loadListLoad);
                if (oldCodTipoCargaOfLoadListLoad != null) {
                    oldCodTipoCargaOfLoadListLoad.getLoadList().remove(loadListLoad);
                    oldCodTipoCargaOfLoadListLoad = em.merge(oldCodTipoCargaOfLoadListLoad);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTypeLoad(typeLoad.getCodTipoCarga()) != null) {
                throw new PreexistingEntityException("TypeLoad " + typeLoad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TypeLoad typeLoad) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TypeLoad persistentTypeLoad = em.find(TypeLoad.class, typeLoad.getCodTipoCarga());
            List<Load> loadListOld = persistentTypeLoad.getLoadList();
            List<Load> loadListNew = typeLoad.getLoadList();
            List<Load> attachedLoadListNew = new ArrayList<Load>();
            for (Load loadListNewLoadToAttach : loadListNew) {
                loadListNewLoadToAttach = em.getReference(loadListNewLoadToAttach.getClass(), loadListNewLoadToAttach.getCodCarga());
                attachedLoadListNew.add(loadListNewLoadToAttach);
            }
            loadListNew = attachedLoadListNew;
            typeLoad.setLoadList(loadListNew);
            typeLoad = em.merge(typeLoad);
            for (Load loadListOldLoad : loadListOld) {
                if (!loadListNew.contains(loadListOldLoad)) {
                    loadListOldLoad.setCodTipoCarga(null);
                    loadListOldLoad = em.merge(loadListOldLoad);
                }
            }
            for (Load loadListNewLoad : loadListNew) {
                if (!loadListOld.contains(loadListNewLoad)) {
                    TypeLoad oldCodTipoCargaOfLoadListNewLoad = loadListNewLoad.getCodTipoCarga();
                    loadListNewLoad.setCodTipoCarga(typeLoad);
                    loadListNewLoad = em.merge(loadListNewLoad);
                    if (oldCodTipoCargaOfLoadListNewLoad != null && !oldCodTipoCargaOfLoadListNewLoad.equals(typeLoad)) {
                        oldCodTipoCargaOfLoadListNewLoad.getLoadList().remove(loadListNewLoad);
                        oldCodTipoCargaOfLoadListNewLoad = em.merge(oldCodTipoCargaOfLoadListNewLoad);
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
                Integer id = typeLoad.getCodTipoCarga();
                if (findTypeLoad(id) == null) {
                    throw new NonexistentEntityException("The typeLoad with id " + id + " no longer exists.");
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
            TypeLoad typeLoad;
            try {
                typeLoad = em.getReference(TypeLoad.class, id);
                typeLoad.getCodTipoCarga();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The typeLoad with id " + id + " no longer exists.", enfe);
            }
            List<Load> loadList = typeLoad.getLoadList();
            for (Load loadListLoad : loadList) {
                loadListLoad.setCodTipoCarga(null);
                loadListLoad = em.merge(loadListLoad);
            }
            em.remove(typeLoad);
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

    public List<TypeLoad> findTypeLoadEntities() {
        return findTypeLoadEntities(true, -1, -1);
    }

    public List<TypeLoad> findTypeLoadEntities(int maxResults, int firstResult) {
        return findTypeLoadEntities(false, maxResults, firstResult);
    }

    private List<TypeLoad> findTypeLoadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TypeLoad.class));
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

    public TypeLoad findTypeLoad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TypeLoad.class, id);
        } finally {
            em.close();
        }
    }

    public int getTypeLoadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TypeLoad> rt = cq.from(TypeLoad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
