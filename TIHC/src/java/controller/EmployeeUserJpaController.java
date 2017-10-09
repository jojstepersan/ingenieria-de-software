/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entities.EmployeeUser;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.TypeEmployee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class EmployeeUserJpaController implements Serializable {

    public EmployeeUserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmployeeUser employeeUser) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TypeEmployee codTipoEmpleado = employeeUser.getCodTipoEmpleado();
            if (codTipoEmpleado != null) {
                codTipoEmpleado = em.getReference(codTipoEmpleado.getClass(), codTipoEmpleado.getCodTipoEmpleado());
                employeeUser.setCodTipoEmpleado(codTipoEmpleado);
            }
            em.persist(employeeUser);
            if (codTipoEmpleado != null) {
                codTipoEmpleado.getEmployeeUserList().add(employeeUser);
                codTipoEmpleado = em.merge(codTipoEmpleado);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEmployeeUser(employeeUser.getCodUsuario()) != null) {
                throw new PreexistingEntityException("EmployeeUser " + employeeUser + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmployeeUser employeeUser) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EmployeeUser persistentEmployeeUser = em.find(EmployeeUser.class, employeeUser.getCodUsuario());
            TypeEmployee codTipoEmpleadoOld = persistentEmployeeUser.getCodTipoEmpleado();
            TypeEmployee codTipoEmpleadoNew = employeeUser.getCodTipoEmpleado();
            if (codTipoEmpleadoNew != null) {
                codTipoEmpleadoNew = em.getReference(codTipoEmpleadoNew.getClass(), codTipoEmpleadoNew.getCodTipoEmpleado());
                employeeUser.setCodTipoEmpleado(codTipoEmpleadoNew);
            }
            employeeUser = em.merge(employeeUser);
            if (codTipoEmpleadoOld != null && !codTipoEmpleadoOld.equals(codTipoEmpleadoNew)) {
                codTipoEmpleadoOld.getEmployeeUserList().remove(employeeUser);
                codTipoEmpleadoOld = em.merge(codTipoEmpleadoOld);
            }
            if (codTipoEmpleadoNew != null && !codTipoEmpleadoNew.equals(codTipoEmpleadoOld)) {
                codTipoEmpleadoNew.getEmployeeUserList().add(employeeUser);
                codTipoEmpleadoNew = em.merge(codTipoEmpleadoNew);
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
                Integer id = employeeUser.getCodUsuario();
                if (findEmployeeUser(id) == null) {
                    throw new NonexistentEntityException("The employeeUser with id " + id + " no longer exists.");
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
            EmployeeUser employeeUser;
            try {
                employeeUser = em.getReference(EmployeeUser.class, id);
                employeeUser.getCodUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employeeUser with id " + id + " no longer exists.", enfe);
            }
            TypeEmployee codTipoEmpleado = employeeUser.getCodTipoEmpleado();
            if (codTipoEmpleado != null) {
                codTipoEmpleado.getEmployeeUserList().remove(employeeUser);
                codTipoEmpleado = em.merge(codTipoEmpleado);
            }
            em.remove(employeeUser);
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

    public List<EmployeeUser> findEmployeeUserEntities() {
        return findEmployeeUserEntities(true, -1, -1);
    }

    public List<EmployeeUser> findEmployeeUserEntities(int maxResults, int firstResult) {
        return findEmployeeUserEntities(false, maxResults, firstResult);
    }

    private List<EmployeeUser> findEmployeeUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmployeeUser.class));
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

    public EmployeeUser findEmployeeUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmployeeUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeeUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmployeeUser> rt = cq.from(EmployeeUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
