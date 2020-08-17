package com.teamtrace.axiseducation.search.rolegroup;

import com.teamtrace.axiseducation.search.DynamicSearchTemplate;
import com.teamtrace.axiseducation.search.mapper.RoleGroupMapper;

public class RoleGroupSearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "" +
            " SELECT A.role_group_id, A.name, A.status, A.created_date, B.full_name " +
            " FROM role_group A LEFT JOIN ADMIN_USER B ON A.created_by_id = B.admin_user_id";
    static String COUNT_SQL = "SELECT COUNT(A.role_group_id) AS ct FROM role_group A ";

    public RoleGroupSearch() {
        super(SELECT_SQL, COUNT_SQL, new RoleGroupMapper());
    }
}
