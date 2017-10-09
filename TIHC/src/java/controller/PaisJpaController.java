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
import entities.Puerto;
import java.util.ArrayList;
import java.util.List;
import entities.Carga;
import entities.Pais;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class PaisJpaController implements Serializable {

    public PaisJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (pais.getPuertoList() == null) {
            pais.setPuertoList(new ArrayList<Puerto>());
        }
        if (pais.getCargaList() == null) {
            pais.setCargaList(new ArrayList<Carga>());
        }
        if (pais.getCargaList1() == null) {
            pais.setCargaList1(new ArrayList<Carga>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Puerto> attachedPuertoList = new ArrayList<Puerto>();
            for (Puerto puertoListPuertoToAttach : pais.getPuertoList()) {
                puertoListPuertoToAttach = em.getReference(puertoListPuertoToAttach.getClass(), puertoListPuertoToAttach.getCodPuerto());
                attachedPuertoList.add(puertoListPuertoToAttach);
            }
            pais.setPuertoList(attachedPuertoList);
            List<Carga> attachedCargaList = new ArrayList<Carga>();
            for (Carga cargaListCargaToAttach : pais.getCargaList()) {
                cargaListCargaToAttach = em.getReference(cargaListCargaToAttach.getClass(), cargaListCargaToAttach.getCodCarga());
                attachedCargaList.add(cargaListCargaToAttach);
            }
            pais.setCargaList(attachedCargaList);
            List<Carga> attachedCargaList1 = new ArrayList<Carga>();
            for (Carga cargaList1CargaToAttach : pais.getCargaList1()) {
                cargaList1CargaToAttach = em.getReference(cargaList1CargaToAttach.getClass(), cargaList1CargaToAttach.getCodCarga());
                attachedCargaList1.add(cargaList1CargaToAttach);
            }
            pais.setCargaList1(attachedCargaList1);
            em.persist(pais);
            for (Puerto puertoListPuerto : pais.getPuertoList()) {
                Pais oldCodPaisOfPuertoListPuerto = puertoListPuerto.getCodPais();
                puertoListPuerto.setCodPais(pais);
                puertoListPuerto = em.merge(puertoListPuerto);
                if (oldCodPaisOfPuertoListPuerto != null) {
                    oldCodPaisOfPuertoListPuerto.getPuertoList().remove(puertoListPuerto);
                    oldCodPaisOfPuertoListPuerto = em.merge(oldCodPaisOfPuertoListPuerto);
                }
            }
            for (Carga cargaListCarga : pais.getCargaList()) {
                Pais oldDestinoOfCargaListCarga = cargaListCarga.getDestino();
                cargaListCarga.setDestino(pais);
                cargaListCarga = em.merge(cargaListCarga);
                if (oldDestinoOfCargaListCarga != null) {
                    oldDestinoOfCargaListCarga.getCargaList().remove(cargaListCarga);
                    oldDestinoOfCargaListCarga = em.merge(oldDestinoOfCargaListCarga);
                }
            }
            for (Carga cargaList1Carga : pais.getCargaList1()) {
                Pais oldOrigenOfCargaList1Carga = cargaList1Carga.getOrigen();
                cargaList1Carga.setOrigen(pais);
                cargaList1Carga = em.merge(cargaList1Carga);
                if (oldOrigenOfCargaList1Carga != null) {
                    oldOrigenOfCargaList1Carga.getCargaList1().remove(cargaList1Carga);
                    oldOrigenOfCargaList1Carga = em.merge(oldOrigenOfCargaList1Carga);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPais(pais.getCodPais()) != null) {
                throw new PreexistingEntityException("Pais " + pais + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pais persistentPais = em.find(Pais.class, pais.getCodPais());
            List<Puerto> puertoListOld = persistentPais.getPuertoList();
            List<Puerto> puertoListNew = pais.getPuertoList();
            List<Carga> cargaListOld = persistentPais.getCargaList();
            List<Carga> cargaListNew = pais.getCargaList();
            List<Carga> cargaList1Old = persistentPais.getCargaList1();
            List<Carga> cargaList1New = pais.getCargaList1();
            List<Puerto> attachedPuertoListNew = new ArrayList<Puerto>();
            for (Puerto puertoListNewPuertoToAttach : puertoListNew) {
                puertoListNewPuertoToAttach = em.getReference(puertoListNewPuertoToAttach.getClass(), puertoListNewPuertoToAttach.getCodPuerto());
                attachedPuertoListNew.add(puertoListNewPuertoToAttach);
            }
            puertoListNew = attachedPuertoListNew;
            pais.setPuertoList(puertoListNew);
            List<Carga> attachedCargaListNew = new ArrayList<Carga>();
            for (Carga cargaListNewCargaToAttach : cargaListNew) {
                cargaListNewCargaToAttach = em.getReference(cargaListNewCargaToAttach.getClass(), cargaListNewCargaToAttach.getCodCarga());
                attachedCargaListNew.add(cargaListNewCargaToAttach);
            }
            cargaListNew = attachedCargaListNew;
            pais.setCargaList(cargaListNew);
            List<Carga> attachedCargaList1New = new ArrayList<Carga>();
            for (Carga cargaList1NewCargaToAttach : cargaList1New) {
                cargaList1NewCargaToAttach = em.getReference(cargaList1NewCargaToAttach.getClass(), cargaList1NewCargaToAttach.getCodCarga());
                attachedCargaList1New.add(cargaList1NewCargaToAttach);
            }
            cargaList1New = attachedCargaList1New;
            pais.setCargaList1(cargaList1New);
            pais = em.merge(pais);
            for (Puerto puertoListOldPuerto : puertoListOld) {
                if (!puertoListNew.contains(puertoListOldPuerto)) {
                    puertoListOldPuerto.setCodPais(null);
                    puertoListOldPuerto = em.merge(puertoListOldPuerto);
                }
            }
            for (Puerto puertoListNewPuerto : puertoListNew) {
                if (!puertoListOld.contains(puertoListNewPuerto)) {
                    Pais oldCodPaisOfPuertoListNewPuerto = puertoListNewPuerto.getCodPais();
                    puertoListNewPuerto.setCodPais(pais);
                    puertoListNewPuerto = em.merge(puertoListNewPuerto);
                    if (oldCodPaisOfPuertoListNewPuerto != null && !oldCodPaisOfPuertoListNewPuerto.equals(pais)) {
                        oldCodPaisOfPuertoListNewPuerto.getPuertoList().remove(puertoListNewPuerto);
                        oldCodPaisOfPuertoListNewPuerto = em.merge(oldCodPaisOfPuertoListNewPuerto);
                    }
                }
            }
            for (Carga cargaListOldCarga : cargaListOld) {
                if (!cargaListNew.contains(cargaListOldCarga)) {
                    cargaListOldCarga.setDestino(null);
                    cargaListOldCarga = em.merge(cargaListOldCarga);
                }
            }
            for (Carga cargaListNewCarga : cargaListNew) {
                if (!cargaListOld.contains(cargaListNewCarga)) {
                    Pais oldDestinoOfCargaListNewCarga = cargaListNewCarga.getDestino();
                    cargaListNewCarga.setDestino(pais);
                    cargaListNewCarga = em.merge(cargaListNewCarga);
                    if (oldDestinoOfCargaListNewCarga != null && !oldDestinoOfCargaListNewCarga.equals(pais)) {
                        oldDestinoOfCargaListNewCarga.getCargaList().remove(cargaListNewCarga);
                        oldDestinoOfCargaListNewCarga = em.merge(oldDestinoOfCargaListNewCarga);
                    }
                }
            }
            for (Carga cargaList1OldCarga : cargaList1Old) {
                if (!cargaList1New.contains(cargaList1OldCarga)) {
                    cargaList1OldCarga.setOrigen(null);
                    cargaList1OldCarga = em.merge(cargaList1OldCarga);
                }
            }
            for (Carga cargaList1NewCarga : cargaList1New) {
                if (!cargaList1Old.contains(cargaList1NewCarga)) {
                    Pais oldOrigenOfCargaList1NewCarga = cargaList1NewCarga.getOrigen();
                    cargaList1NewCarga.setOrigen(pais);
                    cargaList1NewCarga = em.merge(cargaList1NewCarga);
                    if (oldOrigenOfCargaList1NewCarga != null && !oldOrigenOfCargaList1NewCarga.equals(pais)) {
                        oldOrigenOfCargaList1NewCarga.getCargaList1().remove(cargaList1NewCarga);
                        oldOrigenOfCargaList1NewCarga = em.merge(oldOrigenOfCargaList1NewCarga);
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
                Integer id = pais.getCodPais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getCodPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<Puerto> puertoList = pais.getPuertoList();
            for (Puerto puertoListPuerto : puertoList) {
                puertoListPuerto.setCodPais(null);
                puertoListPuerto = em.merge(puertoListPuerto);
            }
            List<Carga> cargaList = pais.getCargaList();
            for (Carga cargaListCarga : cargaList) {
                cargaListCarga.setDestino(null);
                cargaListCarga = em.merge(cargaListCarga);
            }
            List<Carga> cargaList1 = pais.getCargaList1();
            for (Carga cargaList1Carga : cargaList1) {
                cargaList1Carga.setOrigen(null);
                cargaList1Carga = em.merge(cargaList1Carga);
            }
            em.remove(pais);
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

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
