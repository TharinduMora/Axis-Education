package com.teamtrace.axiseducation.service.impl;

import com.teamtrace.axiseducation.api.request.SystemParameterCreateRequest;
import com.teamtrace.axiseducation.api.request.SystemParameterUpdateRequest;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;
import com.teamtrace.axiseducation.model.SystemParameter;
import com.teamtrace.axiseducation.service.SystemParameterService;
import com.teamtrace.axiseducation.util.ExceptionHandler;
import com.teamtrace.axiseducation.util.constant.Statuses;
import com.teamtrace.axiseducation.util.persistence.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SystemParameterServiceImpl implements SystemParameterService {
    private static final Logger logger = LoggerFactory.getLogger(SystemParameterServiceImpl.class);

    public CreationResponse createSystemParameter(SystemParameterCreateRequest request) {
        CreationResponse response = new CreationResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        SystemParameter systemParameter = new SystemParameter();
        systemParameter.setDataType(request.getDataType());
        systemParameter.setKey(request.getKey());
        systemParameter.setValue(request.getValue());

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.persist(systemParameter);

            txn.commit();

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(systemParameter.getParameterId());
            response.setData(systemParameter);
            logger.info("Create system parameter successful. id : {}", systemParameter.getParameterId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create system parameter not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }

    public UpdateResponse updateSystemParameter(SystemParameterUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        SystemParameter old = em.find(SystemParameter.class, request.getParameterId());

        if (old == null) {
            logger.info("Invalid system parameter. id : {}", request.getParameterId());
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
            logger.info("Update system parameter successful. id : {}", old.getParameterId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update system parameter not successful. error : {}", e.getMessage());

            ExceptionHandler.handleException(txn, em, e);
        }

        return response;
    }
}
