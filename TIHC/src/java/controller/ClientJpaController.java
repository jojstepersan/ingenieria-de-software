/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entities.Client;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Contract;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class ClientJpaController implements Serializable {

    public ClientJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Client client) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (client.getContractList() == null) {
            client.setContractList(new ArrayList<Contract>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Contract> attachedContractList = new ArrayList<Contract>();
            for (Contract contractListContractToAttach : client.getContractList()) {
                contractListContractToAttach = em.getReference(contractListContractToAttach.getClass(), contractListContractToAttach.getCodContrato());
                attachedContractList.add(contractListContractToAttach);
            }
            client.setContractList(attachedContractList);
            em.persist(client);
            for (Contract contractListContract : client.getContractList()) {
                Client oldCodClienteOfContractListContract = contractListContract.getCodCliente();
                contractListContract.setCodCliente(client);
                contractListContract = em.merge(contractListContract);
                if (oldCodClienteOfContractListContract != null) {
                    oldCodClienteOfContractListContract.getContractList().remove(contractListContract);
                    oldCodClienteOfContractListContract = em.merge(oldCodClienteOfContractListContract);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findClient(client.getCodCliente()) != null) {
                throw new PreexistingEntityException("Client " + client + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Client client) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Client persistentClient = em.find(Client.class, client.getCodCliente());
            List<Contract> contractListOld = persistentClient.getContractList();
            List<Contract> contractListNew = client.getContractList();
            List<Contract> attachedContractListNew = new ArrayList<Contract>();
            for (Contract contractListNewContractToAttach : contractListNew) {
                contractListNewContractToAttach = em.getReference(contractListNewContractToAttach.getClass(), contractListNewContractToAttach.getCodContrato());
                attachedContractListNew.add(contractListNewContractToAttach);
            }
            contractListNew = attachedContractListNew;
            client.setContractList(contractListNew);
            client = em.merge(client);
            for (Contract contractListOldContract : contractListOld) {
                if (!contractListNew.contains(contractListOldContract)) {
                    contractListOldContract.setCodCliente(null);
                    contractListOldContract = em.merge(contractListOldContract);
                }
            }
            for (Contract contractListNewContract : contractListNew) {
                if (!contractListOld.contains(contractListNewContract)) {
                    Client oldCodClienteOfContractListNewContract = contractListNewContract.getCodCliente();
                    contractListNewContract.setCodCliente(client);
                    contractListNewContract = em.merge(contractListNewContract);
                    if (oldCodClienteOfContractListNewContract != null && !oldCodClienteOfContractListNewContract.equals(client)) {
                        oldCodClienteOfContractListNewContract.getContractList().remove(contractListNewContract);
                        oldCodClienteOfContractListNewContract = em.merge(oldCodClienteOfContractListNewContract);
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
                Integer id = client.getCodCliente();
                if (findClient(id) == null) {
                    throw new NonexistentEntityException("The client with id " + id + " no longer exists.");
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
            Client client;
            try {
                client = em.getReference(Client.class, id);
                client.getCodCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The client with id " + id + " no longer exists.", enfe);
            }
            List<Contract> contractList = client.getContractList();
            for (Contract contractListContract : contractList) {
                contractListContract.setCodCliente(null);
                contractListContract = em.merge(contractListContract);
            }
            em.remove(client);
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

    public List<Client> findClientEntities() {
        return findClientEntities(true, -1, -1);
    }

    public List<Client> findClientEntities(int maxResults, int firstResult) {
        return findClientEntities(false, maxResults, firstResult);
    }

    private List<Client> findClientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Client.class));
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

    public Client findClient(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Client.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Client> rt = cq.from(Client.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
