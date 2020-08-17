package com.teamtrace.axiseducation.search.rolegroup;

import com.teamtrace.axiseducation.api.request.SearchCriteria;
import com.teamtrace.axiseducation.search.StaticDataOnlySearchTemplate;
import com.teamtrace.axiseducation.search.mapper.ListMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoleGroupListSearch extends StaticDataOnlySearchTemplate {
    static String SELECT_SQL = "SELECT A.role_group_id AS id, A.name AS name " +
            " FROM role_group A where A.status = 2";

    public RoleGroupListSearch() {
        super(SELECT_SQL, new ListMapper());
    }

    protected PreparedStatement getDataStatement(Connection connection, SearchCriteria criteria)
            throws SQLException {
        PreparedStatement statement = connection.prepareStatement(selectSQL);

        return statement;
    }
}
