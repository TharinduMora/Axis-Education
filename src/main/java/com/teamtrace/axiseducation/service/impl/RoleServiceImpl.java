package com.teamtrace.axiseducation.service.impl;

import com.teamtrace.axiseducation.api.request.Request;
import com.teamtrace.axiseducation.api.response.ApiSearchResponse;
import com.teamtrace.axiseducation.model.Role;
import com.teamtrace.axiseducation.service.RoleService;
import com.teamtrace.axiseducation.util.ModelToApiConverter;
import com.teamtrace.axiseducation.util.constant.Statuses;
import com.teamtrace.axiseducation.util.persistence.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    public ApiSearchResponse getApprovedRolesByMerchantId(Request request) {
        ApiSearchResponse response = new ApiSearchResponse();

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        List<Integer> types = new ArrayList<>(4);
        types.add(0);

        Query query = em.createNamedQuery("Role.unassignedRolesByRoleType", Role.class);


        List<Role> roles = query.getResultList();

        if (roles != null && !roles.isEmpty()) {
            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setData(ModelToApiConverter.convertRoleList(roles));
            logger.info("Get approved roles successful.");
        } else {
            response.setStatus(Statuses.RESPONSE_STATUS_NO_DATA);
        }

        return response;
    }
}
