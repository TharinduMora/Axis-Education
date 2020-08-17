package com.teamtrace.axiseducation.service.impl;

import com.teamtrace.axiseducation.api.RoleGroupRoleApi;
import com.teamtrace.axiseducation.api.request.RoleGroupCreateRequest;
import com.teamtrace.axiseducation.api.request.RoleGroupRolesFindRequest;
import com.teamtrace.axiseducation.api.request.RoleGroupUpdateRequest;
import com.teamtrace.axiseducation.api.request.StatusUpdateRequest;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;
import com.teamtrace.axiseducation.model.Role;
import com.teamtrace.axiseducation.model.RoleGroup;
import com.teamtrace.axiseducation.service.RoleGroupService;
import com.teamtrace.axiseducation.util.ExceptionHandler;
import com.teamtrace.axiseducation.util.ModelToApiConverter;
import com.teamtrace.axiseducation.util.constant.Statuses;
import com.teamtrace.axiseducation.util.persistence.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoleGroupServiceImpl implements RoleGroupService {
    private static final Logger logger = LoggerFactory.getLogger(RoleGroupServiceImpl.class);

    public CreationResponse createRoleGroup(RoleGroupCreateRequest request) {
        CreationResponse response = new CreationResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        RoleGroup roleGroup = new RoleGroup();
        roleGroup.setStatus(Statuses.PENDING);
        roleGroup.setName(request.getName());
        roleGroup.setCreatedDate(new Date());
        roleGroup.setCreatedById(request.getAdminUser().getAdminUserId());

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (Integer id : request.getRoles()) {
                Role role = em.find(Role.class, id);

                if (role == null) {
                    logger.info("Role group creation, invalid role. id : {}", id);
                    response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                    return response;
                }

                roleGroup.getRoles().add(role);
            }
        }

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(roleGroup);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(roleGroup.getRoleGroupId());
            response.setData(ModelToApiConverter.convert(roleGroup));
            logger.info("Create role group successful. id : {}", roleGroup.getRoleGroupId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create role group not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;

    }

    public UpdateResponse updateRoleGroup(RoleGroupUpdateRequest request) {
        CreationResponse response = new CreationResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        RoleGroup old = em.find(RoleGroup.class, request.getRoleGroupId());

        if (old == null) {
            logger.info("Invalid role group. id : {}", request.getRoleGroupId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setName(request.getName());

        old.setRoles(new ArrayList<>());
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (Integer id : request.getRoles()) {
                Role role = em.find(Role.class, id);

                if (role == null) {
                    logger.info("Role group update, invalid role. id : {}", id);
                    response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                    return response;
                }

                old.getRoles().add(role);
            }
        }

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(old);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update role group successful. id : {}", old.getRoleGroupId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update role group not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;

    }

    public UpdateResponse approveRoleGroup(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        RoleGroup old = em.find(RoleGroup.class, request.getPrimaryId());

        if (old == null) {
            logger.info("Invalid role group. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't approve role group. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.APPROVED);

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(old);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Approve role group successful. id : {}", old.getRoleGroupId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Approve role group not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public UpdateResponse suspendRoleGroup(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        RoleGroup old = em.find(RoleGroup.class, request.getPrimaryId());

        if (old == null) {
            logger.info("Invalid role group. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't suspend role group. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.SUSPENDED);

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(old);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Suspend role group successful. id : {}", old.getRoleGroupId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Suspend role group not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public UpdateResponse deleteRoleGroup(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        RoleGroup old = em.find(RoleGroup.class, request.getPrimaryId());

        if (old == null) {
            logger.info("Invalid role group. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.DELETED);

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(old);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Delete role group successful. id : {}", old.getRoleGroupId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Delete role group not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public CreationResponse getRoleGroupRolesByRoleGroupId(RoleGroupRolesFindRequest request) {
        CreationResponse response = new CreationResponse();

        try {
            RoleGroupRoleApi api = new RoleGroupRoleApi();

            EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
            RoleGroup roleGroup = em.find(RoleGroup.class, request.getRoleGroupId());

            if (roleGroup == null) {
                logger.info("Invalid role group. id : {}", request.getRoleGroupId());
                response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                return response;
            }

            List<Integer> roles = new ArrayList<>(1);
            for (Role e : roleGroup.getRoles()) {
                roles.add(e.getRoleId());
            }

            api.setAssignedRoles(roleGroup.getRoles());
            if (roles.isEmpty()) {//if empty list
                roles.add(-1);
            }

            List<Integer> types = new ArrayList<>(4);
            types.add(0);

            Query query = em.createNamedQuery("Role.unassignedRolesByRoleType", Role.class);
            query.setParameter("typeIds", types);
            query.setParameter("roleIds", roles);

            api.setUnassignedRoles(query.getResultList());

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setData(api);

        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
        }

        return response;
    }
}
