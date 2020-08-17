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

public abstract class StaticSearchTemplate extends StaticDataOnlySearchTemplate {
    protected final String countSQL;

    public StaticSearchTemplate(String selectSQL, String countSQL, Mapper mapper) {
        super(selectSQL, mapper);
        this.countSQL = countSQL;
    }

    protected final void execute(SearchCriteria criteria, SearchResponse response) {
        try (Connection con = DataSourceManager.getDataSource().getConnection();
             PreparedStatement psCount = getCountStatement(con, criteria);
             ResultSet rsCount = psCount.executeQuery();
             PreparedStatement psData = getDataStatement(con, criteria);
             ResultSet rsData = psData.executeQuery()) {
            if (rsCount.next()) {
                response.setRecordCount(rsCount.getInt("ct"));
            }
            if (response.getRecordCount() > 0) {
                response.setData(mapper.parseResults(con, rsData, criteria.getLimit()));
            }

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract PreparedStatement getCountStatement(Connection connection, SearchCriteria criteria)
            throws SQLException;

}
