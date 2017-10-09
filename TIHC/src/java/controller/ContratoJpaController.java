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
import entities.Cliente;
import entities.Carga;
import entities.Contrato;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class ContratoJpaController implements Serializable {

    public ContratoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contrato contrato) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (contrato.getCargaList() == null) {
            contrato.setCargaList(new ArrayList<Carga>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente codCliente = contrato.getCodCliente();
            if (codCliente != null) {
                codCliente = em.getReference(codCliente.getClass(), codCliente.getCodCliente());
                contrato.setCodCliente(codCliente);
            }
            List<Carga> attachedCargaList = new ArrayList<Carga>();
            for (Carga cargaListCargaToAttach : contrato.getCargaList()) {
                cargaListCargaToAttach = em.getReference(cargaListCargaToAttach.getClass(), cargaListCargaToAttach.getCodCarga());
                attachedCargaList.add(cargaListCargaToAttach);
            }
            contrato.setCargaList(attachedCargaList);
            em.persist(contrato);
            if (codCliente != null) {
                codCliente.getContratoList().add(contrato);
                codCliente = em.merge(codCliente);
            }
            for (Carga cargaListCarga : contrato.getCargaList()) {
                Contrato oldCodContratoOfCargaListCarga = cargaListCarga.getCodContrato();
                cargaListCarga.setCodContrato(contrato);
                cargaListCarga = em.merge(cargaListCarga);
                if (oldCodContratoOfCargaListCarga != null) {
                    oldCodContratoOfCargaListCarga.getCargaList().remove(cargaListCarga);
                    oldCodContratoOfCargaListCarga = em.merge(oldCodContratoOfCargaListCarga);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findContrato(contrato.getCodContrato()) != null) {
                throw new PreexistingEntityException("Contrato " + contrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contrato contrato) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contrato persistentContrato = em.find(Contrato.class, contrato.getCodContrato());
            Cliente codClienteOld = persistentContrato.getCodCliente();
            Cliente codClienteNew = contrato.getCodCliente();
            List<Carga> cargaListOld = persistentContrato.getCargaList();
            List<Carga> cargaListNew = contrato.getCargaList();
            if (codClienteNew != null) {
                codClienteNew = em.getReference(codClienteNew.getClass(), codClienteNew.getCodCliente());
                contrato.setCodCliente(codClienteNew);
            }
            List<Carga> attachedCargaListNew = new ArrayList<Carga>();
            for (Carga cargaListNewCargaToAttach : cargaListNew) {
                cargaListNewCargaToAttach = em.getReference(cargaListNewCargaToAttach.getClass(), cargaListNewCargaToAttach.getCodCarga());
                attachedCargaListNew.add(cargaListNewCargaToAttach);
            }
            cargaListNew = attachedCargaListNew;
            contrato.setCargaList(cargaListNew);
            contrato = em.merge(contrato);
            if (codClienteOld != null && !codClienteOld.equals(codClienteNew)) {
                codClienteOld.getContratoList().remove(contrato);
                codClienteOld = em.merge(codClienteOld);
            }
            if (codClienteNew != null && !codClienteNew.equals(codClienteOld)) {
                codClienteNew.getContratoList().add(contrato);
                codClienteNew = em.merge(codClienteNew);
            }
            for (Carga cargaListOldCarga : cargaListOld) {
                if (!cargaListNew.contains(cargaListOldCarga)) {
                    cargaListOldCarga.setCodContrato(null);
                    cargaListOldCarga = em.merge(cargaListOldCarga);
                }
            }
            for (Carga cargaListNewCarga : cargaListNew) {
                if (!cargaListOld.contains(cargaListNewCarga)) {
                    Contrato oldCodContratoOfCargaListNewCarga = cargaListNewCarga.getCodContrato();
                    cargaListNewCarga.setCodContrato(contrato);
                    cargaListNewCarga = em.merge(cargaListNewCarga);
                    if (oldCodContratoOfCargaListNewCarga != null && !oldCodContratoOfCargaListNewCarga.equals(contrato)) {
                        oldCodContratoOfCargaListNewCarga.getCargaList().remove(cargaListNewCarga);
                        oldCodContratoOfCargaListNewCarga = em.merge(oldCodContratoOfCargaListNewCarga);
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
                Integer id = contrato.getCodContrato();
                if (findContrato(id) == null) {
                    throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.");
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
            Contrato contrato;
            try {
                contrato = em.getReference(Contrato.class, id);
                contrato.getCodContrato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.", enfe);
            }
            Cliente codCliente = contrato.getCodCliente();
            if (codCliente != null) {
                codCliente.getContratoList().remove(contrato);
                codCliente = em.merge(codCliente);
            }
            List<Carga> cargaList = contrato.getCargaList();
            for (Carga cargaListCarga : cargaList) {
                cargaListCarga.setCodContrato(null);
                cargaListCarga = em.merge(cargaListCarga);
            }
            em.remove(contrato);
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

    public List<Contrato> findContratoEntities() {
        return findContratoEntities(true, -1, -1);
    }

    public List<Contrato> findContratoEntities(int maxResults, int firstResult) {
        return findContratoEntities(false, maxResults, firstResult);
    }

    private List<Contrato> findContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contrato.class));
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

    public Contrato findContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contrato> rt = cq.from(Contrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
