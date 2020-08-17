package com.teamtrace.axiseducation.service.impl;

import com.teamtrace.axiseducation.api.response.AdminUserLoginResponse;
import com.teamtrace.axiseducation.api.response.ApiSearchResponse;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;
import com.teamtrace.axiseducation.model.AdminUser;
import com.teamtrace.axiseducation.model.Role;
import com.teamtrace.axiseducation.model.RoleGroup;
import com.teamtrace.axiseducation.service.AdminUserService;
import com.teamtrace.axiseducation.util.ExceptionHandler;
import com.teamtrace.axiseducation.util.ModelToApiConverter;
import com.teamtrace.axiseducation.util.SessionUtil;
import com.teamtrace.axiseducation.util.UUIDUtil;
import com.teamtrace.axiseducation.util.constant.Statuses;
import com.teamtrace.axiseducation.util.constant.SystemParameters;
import com.teamtrace.axiseducation.util.password.PasswordHandler;
import com.teamtrace.axiseducation.util.password.PasswordHandlerFactory;
import com.teamtrace.axiseducation.util.persistence.PersistenceManager;
import com.teamtrace.axiseducation.api.request.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AdminUserServiceImpl implements AdminUserService {
    private static final Logger logger = LoggerFactory.getLogger(AdminUserServiceImpl.class);

    public CreationResponse createAdminUser(AdminUserCreateRequest request) throws GeneralSecurityException {
        CreationResponse response = new CreationResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        Query query = em.createNamedQuery("AdminUser.findByLoginName", AdminUser.class);
        query.setParameter("loginName", request.getLoginName());

        List<AdminUser> users = query.getResultList();

        if (!users.isEmpty()) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            logger.info("Create Admin user, login name already exists. {}", request.getLoginName());
            response.setMessage(request.getResourceBundle().getString("login_name_not_available"));

            return response;
        }

        String pwd = UUIDUtil.generateRandomPassword(SystemParameters.AUTO_PASSWORD_LENGTH);

        String newPassword = PasswordHandlerFactory.getPasswordHandler(PasswordHandler.MD5).
                hashPassword(request.getLoginName(), pwd);

        AdminUser adminUser = new AdminUser();
        adminUser.setEmail(request.getEmail());
        adminUser.setLoginName(request.getLoginName());
        adminUser.setPassword(newPassword);
        adminUser.setPwd(pwd);
        adminUser.setFullName(request.getFullName());
        adminUser.setMobile(request.getMobile());
        adminUser.setNotificationEnable(true);
        adminUser.setPasswordChangeRequired(true);
        adminUser.setProfileImageUrl(request.getProfileImageUrl());
        adminUser.setStatus(Statuses.PENDING);
        adminUser.setCreatedDate(new Date());

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            adminUser.setRoleGroups(new ArrayList<>(request.getRoles().size()));
            for (int roleId : request.getRoles()) {
                RoleGroup roleGroup = em.find(RoleGroup.class, roleId);
                if (roleGroup == null) {
                    logger.info("Admin user creation, invalid user role. id : {}", roleId);
                    response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                    response.setMessage(request.getResourceBundle().getString("invalid_user_role"));

                    return response;
                }
                adminUser.getRoleGroups().add(roleGroup);

            }
        }

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(adminUser);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(adminUser.getAdminUserId());
            response.setData(ModelToApiConverter.convert(adminUser));
            logger.info("Create admin user, create system user. id : {}", adminUser.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create admin user, create system user not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);

            return response;
        }

        //todo send notification

        return response;
    }

    public UpdateResponse updateAdminUserBasicDetails(AdminUserBasicDetailsUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUser old = em.find(AdminUser.class, request.getAdminUserId());

        if (old == null) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setFullName(request.getFullName());
        old.setMobile(request.getMobile());
        if (request.getRoles() != null) {
            old.setRoleGroups(new ArrayList<>(request.getRoles().size()));
            for (int roleId : request.getRoles()) {
                RoleGroup roleGroup = em.find(RoleGroup.class, roleId);
                if (roleGroup == null) {
                    logger.info("Admin user basic details update, invalid user role. id : {}", roleId);
                    response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                    response.setMessage(request.getResourceBundle().getString("invalid_user_role"));

                    return response;
                }
                old.getRoleGroups().add(roleGroup);
            }
        }

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(old);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update admin user basic details successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update admin user basic details not successful. error : {}", e.getMessage());
            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public UpdateResponse updateAdminUser(AdminUserUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUser old = em.find(AdminUser.class, request.getAdminUserId());

        if (old == null) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setFullName(request.getFullName());
        old.setMobile(request.getMobile());
        if (request.getRoles() != null) {
            old.setRoleGroups(new ArrayList<>(request.getRoles().size()));
            for (int roleId : request.getRoles()) {
                RoleGroup roleGroup = em.find(RoleGroup.class, roleId);
                if (roleGroup == null) {
                    logger.info("Admin user update, invalid user role. id : {}", roleId);
                    response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                    response.setMessage(request.getResourceBundle().getString("invalid_user_role"));

                    return response;
                }
                old.getRoleGroups().add(roleGroup);
            }
        }

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(old);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update admin user. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update admin user not successful. error : {}", e.getMessage());
            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public UpdateResponse updateAdminUserProfileImage(AdminUserProfileImageUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUser old = em.find(AdminUser.class, request.getAdminUserId());

        if (old == null) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setProfileImageUrl(request.getProfileImageUrl());

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(old);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update admin user profile image successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update admin user profile image not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public ApiSearchResponse getAdminUserByUserId(GetByPrimaryIdRequest request) {
        ApiSearchResponse response = new ApiSearchResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUser old = em.find(AdminUser.class, request.getPrimaryId());

        if (old == null) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        response.setData(ModelToApiConverter.convert(old));
        logger.info("Get Admin user details by user id. id : {}", old.getAdminUserId());


        return response;
    }

    public UpdateResponse changeAdminUserPassword(AdminUserChangePasswordRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUser old = em.find(AdminUser.class, request.getAdminUserId());

        if (old == null) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        if (!request.getOldPassword().equalsIgnoreCase(old.getPassword())) {
            logger.info("Invalid password. id : {}", request.getAdminUser().getAdminUserId());
            response.setMessage("Invalid password.");
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setPassword(request.getNewPassword());

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(old);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Change admin user password successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Change admin user password not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public UpdateResponse approveAdminUser(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUser old = em.find(AdminUser.class, request.getPrimaryId());

        if (old == null) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't approve admin user. id : {}", request.getPrimaryId());
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
            logger.info("Approve admin user successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Approve admin user not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public UpdateResponse suspendAdminUser(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUser old = em.find(AdminUser.class, request.getPrimaryId());

        if (old == null) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't suspend admin user. id : {}", request.getPrimaryId());
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
            logger.info("Suspend admin user successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Suspend admin user not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public UpdateResponse deleteAdminUser(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUser old = em.find(AdminUser.class, request.getPrimaryId());

        if (old == null) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
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
            logger.info("Delete admin user successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Delete admin user not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public AdminUserLoginResponse loginAdminUser(AdminUserLoginRequest request) {
        AdminUserLoginResponse response = new AdminUserLoginResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        Query query = em.createNamedQuery("AdminUser.findByLoginName", AdminUser.class);
        query.setParameter("loginName", request.getLoginName());

        List<AdminUser> users = query.getResultList();

        if (users.isEmpty()) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            logger.info("Login admin user, admin user not found. username : {}", request.getLoginName());
            response.setMessage(request.getResourceBundle().getString("login_name_not_available"));

            return response;
        }
        AdminUser adminUser = users.get(0);

        try {
            boolean validPasswordNew = PasswordHandlerFactory.getPasswordHandler(PasswordHandler.MD5)
                    .validatePassword(request.getLoginName(), request.getPassword(), adminUser.getPassword());
            boolean validPassword = request.getPassword().equalsIgnoreCase(adminUser.getPassword());
            if (!validPassword) {
                logger.info("Login admin user, invalid password for user. username : {}", request.getLoginName());

                response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                response.setMessage(request.getResourceBundle().getString("invalid_login_name_or_password"));

                return response;
            }

        } catch (GeneralSecurityException e) {
            logger.info("Login admin user, error occurred. error : {}", e.getMessage());

            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));

            return response;
        }

        List<Integer> roles = new ArrayList<>(100);
        for (RoleGroup roleGroup : adminUser.getRoleGroups()) {
            /*for (Role r : roleGroup.getRoles()) {
                r.getName();
            }*/
            roles.addAll(roleGroup.getRoles()
                    .stream().map(Role::getRoleId).collect(Collectors.toList())
            );
        }
        String sessionId = UUIDUtil.generateToken();

        response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        response.setAdminUserId(adminUser.getAdminUserId());
        response.setFullName(adminUser.getFullName());
        response.setProfileImageUrl(adminUser.getProfileImageUrl());
        response.setSessionId(sessionId);
        response.setRoles(roles);
        response.setTypeId(adminUser.getTypeId());
        response.setPasswordChangeRequired(adminUser.isPasswordChangeRequired());

        SessionUtil.addAdminUserSession(sessionId, adminUser);

        return response;
    }

    public UpdateResponse logoutAdminUser(AdminUserLogoutRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        if (SessionUtil.removeAdminUserSession(request.getSessionId())) {
            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);

            return response;
        } else {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage("Invalid admin user session.");

            return response;
        }

    }

    public UpdateResponse resetAdminUserPassword(AdminUserResetPasswordRequest request) throws GeneralSecurityException {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        Query query = em.createNamedQuery("AdminUser.findByLoginName", AdminUser.class);
        query.setParameter("loginName", request.getUsername());

        List<AdminUser> users = query.getResultList();

        if (users.isEmpty()) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            logger.info("Reset admin user password, admin user not found. username : {}", request.getUsername());
            response.setMessage(request.getResourceBundle().getString("login_name_not_available"));

            return response;
        }
        AdminUser adminUser = users.get(0);

        String pwd = UUIDUtil.generateRandomPassword(SystemParameters.AUTO_PASSWORD_LENGTH);

        String newPassword = PasswordHandlerFactory.getPasswordHandler(PasswordHandler.MD5).
                hashPassword(request.getUsername(), pwd);

        adminUser.setPassword(newPassword);
        adminUser.setPwd(newPassword);

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(adminUser);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Reset admin user password successful. id : {}", adminUser.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Reset admin user password not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public UpdateResponse resetAdminUserPasswordFormAdmin(AdminUserResetPasswordFromAdminRequest request) throws GeneralSecurityException {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUser adminUser = em.find(AdminUser.class, request.getAdminUserId());

        if (adminUser == null) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        String pwd = UUIDUtil.generateRandomPassword(SystemParameters.AUTO_PASSWORD_LENGTH);

        String newPassword = PasswordHandlerFactory.getPasswordHandler(PasswordHandler.MD5).
                hashPassword(adminUser.getLoginName(), pwd);

        adminUser.setPassword(newPassword);
        adminUser.setPwd(newPassword);

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(adminUser);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Reset admin user password from admin successful. id : {}", adminUser.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Reset admin user password from admin not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

}
