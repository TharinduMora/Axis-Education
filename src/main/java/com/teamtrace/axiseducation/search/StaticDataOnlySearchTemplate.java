package com.teamtrace.axiseducation.search;

import com.teamtrace.axiseducation.api.request.SearchCriteria;
import com.teamtrace.axiseducation.api.response.SearchResponse;
import com.teamtrace.axiseducation.search.mapper.Mapper;
import com.teamtrace.axiseducation.util.constant.Statuses;
import com.teamtrace.axiseducation.util.persistence.DataSourceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class StaticDataOnlySearchTemplate extends SearchTemplate {
    public StaticDataOnlySearchTemplate(String selectSQL, Mapper mapper) {
        super(selectSQL, mapper);
    }

    protected void execute(SearchCriteria criteria, SearchResponse response) {
        try (Connection con = DataSourceManager.getDataSource().getConnection();
             PreparedStatement psData = getDataStatement(con, criteria);
             ResultSet rsData = psData.executeQuery()) {
            response.setData(mapper.parseResults(con, rsData, criteria.getLimit()));
            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract PreparedStatement getDataStatement(Connection connection, SearchCriteria criteria)
            throws SQLException;

}
