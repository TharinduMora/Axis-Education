package com.teamtrace.axiseducation.service.impl;

import com.teamtrace.axiseducation.api.request.SystemParameterCreateRequest;
import com.teamtrace.axiseducation.api.request.SystemParameterUpdateRequest;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;
import com.teamtrace.axiseducation.model.SystemStatement;
import com.teamtrace.axiseducation.service.SystemStatementService;
import com.teamtrace.axiseducation.util.ExceptionHandler;
import com.teamtrace.axiseducation.util.constant.Statuses;
import com.teamtrace.axiseducation.util.persistence.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SystemStatementServiceImpl implements SystemStatementService {
    private static final Logger logger = LoggerFactory.getLogger(SystemStatementServiceImpl.class);

    public CreationResponse createSystemStatement(SystemParameterCreateRequest request) {
        CreationResponse response = new CreationResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        SystemStatement systemStatement = new SystemStatement();
        systemStatement.setKey(request.getKey());
        systemStatement.setValue(request.getValue());

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(systemStatement);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(systemStatement.getStatementId());
            response.setData(systemStatement);
            logger.info("Create system statement successful. id : {}", systemStatement.getStatementId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create system statement not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public UpdateResponse updateSystemStatement(SystemParameterUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        SystemStatement old = em.find(SystemStatement.class, request.getParameterId());
        if (old == null) {
            logger.info("Invalid system statement. id : {}", request.getParameterId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setKey(request.getKey());
        old.setValue(request.getValue());

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.merge(old);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update system statement successful. id : {}", old.getStatementId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update system statement not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }
}
