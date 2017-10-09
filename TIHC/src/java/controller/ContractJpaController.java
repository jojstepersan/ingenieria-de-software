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
import entities.Client;
import entities.Contract;
import entities.Load;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class ContractJpaController implements Serializable {

    public ContractJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contract contract) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (contract.getLoadList() == null) {
            contract.setLoadList(new ArrayList<Load>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Client codCliente = contract.getCodCliente();
            if (codCliente != null) {
                codCliente = em.getReference(codCliente.getClass(), codCliente.getCodCliente());
                contract.setCodCliente(codCliente);
            }
            List<Load> attachedLoadList = new ArrayList<Load>();
            for (Load loadListLoadToAttach : contract.getLoadList()) {
                loadListLoadToAttach = em.getReference(loadListLoadToAttach.getClass(), loadListLoadToAttach.getCodCarga());
                attachedLoadList.add(loadListLoadToAttach);
            }
            contract.setLoadList(attachedLoadList);
            em.persist(contract);
            if (codCliente != null) {
                codCliente.getContractList().add(contract);
                codCliente = em.merge(codCliente);
            }
            for (Load loadListLoad : contract.getLoadList()) {
                Contract oldCodContratoOfLoadListLoad = loadListLoad.getCodContrato();
                loadListLoad.setCodContrato(contract);
                loadListLoad = em.merge(loadListLoad);
                if (oldCodContratoOfLoadListLoad != null) {
                    oldCodContratoOfLoadListLoad.getLoadList().remove(loadListLoad);
                    oldCodContratoOfLoadListLoad = em.merge(oldCodContratoOfLoadListLoad);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findContract(contract.getCodContrato()) != null) {
                throw new PreexistingEntityException("Contract " + contract + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contract contract) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contract persistentContract = em.find(Contract.class, contract.getCodContrato());
            Client codClienteOld = persistentContract.getCodCliente();
            Client codClienteNew = contract.getCodCliente();
            List<Load> loadListOld = persistentContract.getLoadList();
            List<Load> loadListNew = contract.getLoadList();
            if (codClienteNew != null) {
                codClienteNew = em.getReference(codClienteNew.getClass(), codClienteNew.getCodCliente());
                contract.setCodCliente(codClienteNew);
            }
            List<Load> attachedLoadListNew = new ArrayList<Load>();
            for (Load loadListNewLoadToAttach : loadListNew) {
                loadListNewLoadToAttach = em.getReference(loadListNewLoadToAttach.getClass(), loadListNewLoadToAttach.getCodCarga());
                attachedLoadListNew.add(loadListNewLoadToAttach);
            }
            loadListNew = attachedLoadListNew;
            contract.setLoadList(loadListNew);
            contract = em.merge(contract);
            if (codClienteOld != null && !codClienteOld.equals(codClienteNew)) {
                codClienteOld.getContractList().remove(contract);
                codClienteOld = em.merge(codClienteOld);
            }
            if (codClienteNew != null && !codClienteNew.equals(codClienteOld)) {
                codClienteNew.getContractList().add(contract);
                codClienteNew = em.merge(codClienteNew);
            }
            for (Load loadListOldLoad : loadListOld) {
                if (!loadListNew.contains(loadListOldLoad)) {
                    loadListOldLoad.setCodContrato(null);
                    loadListOldLoad = em.merge(loadListOldLoad);
                }
            }
            for (Load loadListNewLoad : loadListNew) {
                if (!loadListOld.contains(loadListNewLoad)) {
                    Contract oldCodContratoOfLoadListNewLoad = loadListNewLoad.getCodContrato();
                    loadListNewLoad.setCodContrato(contract);
                    loadListNewLoad = em.merge(loadListNewLoad);
                    if (oldCodContratoOfLoadListNewLoad != null && !oldCodContratoOfLoadListNewLoad.equals(contract)) {
                        oldCodContratoOfLoadListNewLoad.getLoadList().remove(loadListNewLoad);
                        oldCodContratoOfLoadListNewLoad = em.merge(oldCodContratoOfLoadListNewLoad);
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
                Integer id = contract.getCodContrato();
                if (findContract(id) == null) {
                    throw new NonexistentEntityException("The contract with id " + id + " no longer exists.");
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
            Contract contract;
            try {
                contract = em.getReference(Contract.class, id);
                contract.getCodContrato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contract with id " + id + " no longer exists.", enfe);
            }
            Client codCliente = contract.getCodCliente();
            if (codCliente != null) {
                codCliente.getContractList().remove(contract);
                codCliente = em.merge(codCliente);
            }
            List<Load> loadList = contract.getLoadList();
            for (Load loadListLoad : loadList) {
                loadListLoad.setCodContrato(null);
                loadListLoad = em.merge(loadListLoad);
            }
            em.remove(contract);
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

    public List<Contract> findContractEntities() {
        return findContractEntities(true, -1, -1);
    }

    public List<Contract> findContractEntities(int maxResults, int firstResult) {
        return findContractEntities(false, maxResults, firstResult);
    }

    private List<Contract> findContractEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contract.class));
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

    public Contract findContract(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contract.class, id);
        } finally {
            em.close();
        }
    }

    public int getContractCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contract> rt = cq.from(Contract.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
