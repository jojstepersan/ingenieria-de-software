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
import entities.crewman;
import java.util.ArrayList;
import java.util.List;
import entities.EmployeeUser;
import entities.TypeEmployee;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class TypeEmployeeJpaController implements Serializable {

    public TypeEmployeeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TypeEmployee typeEmployee) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (typeEmployee.getCrewmanList() == null) {
            typeEmployee.setCrewmanList(new ArrayList<crewman>());
        }
        if (typeEmployee.getEmployeeUserList() == null) {
            typeEmployee.setEmployeeUserList(new ArrayList<EmployeeUser>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<crewman> attachedCrewmanList = new ArrayList<crewman>();
            for (crewman crewmanListcrewmanToAttach : typeEmployee.getCrewmanList()) {
                crewmanListcrewmanToAttach = em.getReference(crewmanListcrewmanToAttach.getClass(), crewmanListcrewmanToAttach.getCodEmpleado());
                attachedCrewmanList.add(crewmanListcrewmanToAttach);
            }
            typeEmployee.setCrewmanList(attachedCrewmanList);
            List<EmployeeUser> attachedEmployeeUserList = new ArrayList<EmployeeUser>();
            for (EmployeeUser employeeUserListEmployeeUserToAttach : typeEmployee.getEmployeeUserList()) {
                employeeUserListEmployeeUserToAttach = em.getReference(employeeUserListEmployeeUserToAttach.getClass(), employeeUserListEmployeeUserToAttach.getCodUsuario());
                attachedEmployeeUserList.add(employeeUserListEmployeeUserToAttach);
            }
            typeEmployee.setEmployeeUserList(attachedEmployeeUserList);
            em.persist(typeEmployee);
            for (crewman crewmanListcrewman : typeEmployee.getCrewmanList()) {
                TypeEmployee oldTipoEmpleadoOfCrewmanListcrewman = crewmanListcrewman.getTipoEmpleado();
                crewmanListcrewman.setTipoEmpleado(typeEmployee);
                crewmanListcrewman = em.merge(crewmanListcrewman);
                if (oldTipoEmpleadoOfCrewmanListcrewman != null) {
                    oldTipoEmpleadoOfCrewmanListcrewman.getCrewmanList().remove(crewmanListcrewman);
                    oldTipoEmpleadoOfCrewmanListcrewman = em.merge(oldTipoEmpleadoOfCrewmanListcrewman);
                }
            }
            for (EmployeeUser employeeUserListEmployeeUser : typeEmployee.getEmployeeUserList()) {
                TypeEmployee oldCodTipoEmpleadoOfEmployeeUserListEmployeeUser = employeeUserListEmployeeUser.getCodTipoEmpleado();
                employeeUserListEmployeeUser.setCodTipoEmpleado(typeEmployee);
                employeeUserListEmployeeUser = em.merge(employeeUserListEmployeeUser);
                if (oldCodTipoEmpleadoOfEmployeeUserListEmployeeUser != null) {
                    oldCodTipoEmpleadoOfEmployeeUserListEmployeeUser.getEmployeeUserList().remove(employeeUserListEmployeeUser);
                    oldCodTipoEmpleadoOfEmployeeUserListEmployeeUser = em.merge(oldCodTipoEmpleadoOfEmployeeUserListEmployeeUser);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTypeEmployee(typeEmployee.getCodTipoEmpleado()) != null) {
                throw new PreexistingEntityException("TypeEmployee " + typeEmployee + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TypeEmployee typeEmployee) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TypeEmployee persistentTypeEmployee = em.find(TypeEmployee.class, typeEmployee.getCodTipoEmpleado());
            List<crewman> crewmanListOld = persistentTypeEmployee.getCrewmanList();
            List<crewman> crewmanListNew = typeEmployee.getCrewmanList();
            List<EmployeeUser> employeeUserListOld = persistentTypeEmployee.getEmployeeUserList();
            List<EmployeeUser> employeeUserListNew = typeEmployee.getEmployeeUserList();
            List<crewman> attachedCrewmanListNew = new ArrayList<crewman>();
            for (crewman crewmanListNewcrewmanToAttach : crewmanListNew) {
                crewmanListNewcrewmanToAttach = em.getReference(crewmanListNewcrewmanToAttach.getClass(), crewmanListNewcrewmanToAttach.getCodEmpleado());
                attachedCrewmanListNew.add(crewmanListNewcrewmanToAttach);
            }
            crewmanListNew = attachedCrewmanListNew;
            typeEmployee.setCrewmanList(crewmanListNew);
            List<EmployeeUser> attachedEmployeeUserListNew = new ArrayList<EmployeeUser>();
            for (EmployeeUser employeeUserListNewEmployeeUserToAttach : employeeUserListNew) {
                employeeUserListNewEmployeeUserToAttach = em.getReference(employeeUserListNewEmployeeUserToAttach.getClass(), employeeUserListNewEmployeeUserToAttach.getCodUsuario());
                attachedEmployeeUserListNew.add(employeeUserListNewEmployeeUserToAttach);
            }
            employeeUserListNew = attachedEmployeeUserListNew;
            typeEmployee.setEmployeeUserList(employeeUserListNew);
            typeEmployee = em.merge(typeEmployee);
            for (crewman crewmanListOldcrewman : crewmanListOld) {
                if (!crewmanListNew.contains(crewmanListOldcrewman)) {
                    crewmanListOldcrewman.setTipoEmpleado(null);
                    crewmanListOldcrewman = em.merge(crewmanListOldcrewman);
                }
            }
            for (crewman crewmanListNewcrewman : crewmanListNew) {
                if (!crewmanListOld.contains(crewmanListNewcrewman)) {
                    TypeEmployee oldTipoEmpleadoOfCrewmanListNewcrewman = crewmanListNewcrewman.getTipoEmpleado();
                    crewmanListNewcrewman.setTipoEmpleado(typeEmployee);
                    crewmanListNewcrewman = em.merge(crewmanListNewcrewman);
                    if (oldTipoEmpleadoOfCrewmanListNewcrewman != null && !oldTipoEmpleadoOfCrewmanListNewcrewman.equals(typeEmployee)) {
                        oldTipoEmpleadoOfCrewmanListNewcrewman.getCrewmanList().remove(crewmanListNewcrewman);
                        oldTipoEmpleadoOfCrewmanListNewcrewman = em.merge(oldTipoEmpleadoOfCrewmanListNewcrewman);
                    }
                }
            }
            for (EmployeeUser employeeUserListOldEmployeeUser : employeeUserListOld) {
                if (!employeeUserListNew.contains(employeeUserListOldEmployeeUser)) {
                    employeeUserListOldEmployeeUser.setCodTipoEmpleado(null);
                    employeeUserListOldEmployeeUser = em.merge(employeeUserListOldEmployeeUser);
                }
            }
            for (EmployeeUser employeeUserListNewEmployeeUser : employeeUserListNew) {
                if (!employeeUserListOld.contains(employeeUserListNewEmployeeUser)) {
                    TypeEmployee oldCodTipoEmpleadoOfEmployeeUserListNewEmployeeUser = employeeUserListNewEmployeeUser.getCodTipoEmpleado();
                    employeeUserListNewEmployeeUser.setCodTipoEmpleado(typeEmployee);
                    employeeUserListNewEmployeeUser = em.merge(employeeUserListNewEmployeeUser);
                    if (oldCodTipoEmpleadoOfEmployeeUserListNewEmployeeUser != null && !oldCodTipoEmpleadoOfEmployeeUserListNewEmployeeUser.equals(typeEmployee)) {
                        oldCodTipoEmpleadoOfEmployeeUserListNewEmployeeUser.getEmployeeUserList().remove(employeeUserListNewEmployeeUser);
                        oldCodTipoEmpleadoOfEmployeeUserListNewEmployeeUser = em.merge(oldCodTipoEmpleadoOfEmployeeUserListNewEmployeeUser);
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
                Integer id = typeEmployee.getCodTipoEmpleado();
                if (findTypeEmployee(id) == null) {
                    throw new NonexistentEntityException("The typeEmployee with id " + id + " no longer exists.");
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
            TypeEmployee typeEmployee;
            try {
                typeEmployee = em.getReference(TypeEmployee.class, id);
                typeEmployee.getCodTipoEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The typeEmployee with id " + id + " no longer exists.", enfe);
            }
            List<crewman> crewmanList = typeEmployee.getCrewmanList();
            for (crewman crewmanListcrewman : crewmanList) {
                crewmanListcrewman.setTipoEmpleado(null);
                crewmanListcrewman = em.merge(crewmanListcrewman);
            }
            List<EmployeeUser> employeeUserList = typeEmployee.getEmployeeUserList();
            for (EmployeeUser employeeUserListEmployeeUser : employeeUserList) {
                employeeUserListEmployeeUser.setCodTipoEmpleado(null);
                employeeUserListEmployeeUser = em.merge(employeeUserListEmployeeUser);
            }
            em.remove(typeEmployee);
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

    public List<TypeEmployee> findTypeEmployeeEntities() {
        return findTypeEmployeeEntities(true, -1, -1);
    }

    public List<TypeEmployee> findTypeEmployeeEntities(int maxResults, int firstResult) {
        return findTypeEmployeeEntities(false, maxResults, firstResult);
    }

    private List<TypeEmployee> findTypeEmployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TypeEmployee.class));
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

    public TypeEmployee findTypeEmployee(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TypeEmployee.class, id);
        } finally {
            em.close();
        }
    }

    public int getTypeEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TypeEmployee> rt = cq.from(TypeEmployee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
