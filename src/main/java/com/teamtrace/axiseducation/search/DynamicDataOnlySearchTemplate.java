package com.teamtrace.axiseducation.search;

import com.teamtrace.axiseducation.api.request.SearchCriteria;
import com.teamtrace.axiseducation.api.response.SearchResponse;
import com.teamtrace.axiseducation.search.mapper.Mapper;
import com.teamtrace.axiseducation.util.constant.Statuses;
import com.teamtrace.axiseducation.util.persistence.DataSourceManager;
import org.bson.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DynamicDataOnlySearchTemplate extends SearchTemplate {
    public DynamicDataOnlySearchTemplate(String selectSQL, Mapper mapper) {
        super(selectSQL, mapper);
    }

    protected void execute(SearchCriteria criteria, SearchResponse response) {
        StringBuilder where = generateWhere(criteria, new StringBuilder(255));
        filter(criteria, where);
        String select = selectSQL + where + generateOrderBy(criteria) + generateLimit(criteria);

        try (Connection con = DataSourceManager.getDataSource().getConnection();
             PreparedStatement psData = con.prepareStatement(select);
             ResultSet rsData = psData.executeQuery()) {
            response.setData(mapper.parseResults(con, rsData, criteria.getLimit()));

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected final StringBuilder generateWhere(SearchCriteria criteria, StringBuilder where) {
        where.append(" WHERE 1 = 1 ");

        if (criteria.getStatuses() != null && !criteria.getStatuses().isEmpty()) {
            String statuses = criteria.getStatuses().toString();
            where.append(" AND A.status IN (").append(statuses.substring(1, statuses.length() - 1))
                    .append(")");
        }
        Document additionalData = new Document();

        if (criteria.getSearchKeys() != null && !criteria.getSearchKeys().isEmpty()) {
            for (int i = 0; i < criteria.getSearchKeys().size(); i++) {
                String key = criteria.getSearchKeys().get(i);
                if (key.startsWith("additionalData.") && criteria.getOperators().get(i).equals("in")) {
                    additionalData.append(key.substring(15), criteria.getValues().get(i));
                } else {
                    if (key.startsWith("additionalData.")) {
                        key = "A.ADDITIONAL_DATA->'$." + key.substring(15) + "'";
                    } else if (key.startsWith("additionalColumns.")) {
                        key = "A.ADDITIONAL_COLUMNS->'$." + key.substring(18) + "'";
                    } else {
                        key = mapper.getColumnMap().get(key);
                    }
                    if (key != null) {
                        Object value = criteria.getValues().get(i);
                        appendCondition(key, criteria.getOperators().get(i), value, where);
                    }
                }
            }

            if (!additionalData.isEmpty()) {
                where.append(" AND JSON_CONTAINS(ADDITIONAL_DATA, '").append(additionalData.toJson())
                        .append("') ");
            }
        }
        return where;
    }

    protected final String generateOrderBy(SearchCriteria criteria) {
        String orderBy = " ASC ";
        if (criteria.getOrderByKey() != null) {
            if (criteria.getOrderByKey().startsWith("additionalData.")) {
                criteria.setOrderByKey("A.ADDITIONAL_DATA->'$." + criteria.getOrderByKey().substring(15) + "'");
            } else if (criteria.getOrderByKey().startsWith("additionalColumns.")) {
                criteria.setOrderByKey("A.ADDITIONAL_COLUMNS->'$." + criteria.getOrderByKey().substring(18) + "'");
            } else {
                criteria.setOrderByKey(mapper.getColumnMap().get(criteria.getOrderByKey()));
            }
            if (criteria.getOrderByKey() != null) {
                if ("DESC".equalsIgnoreCase(criteria.getOrderByValue())) {
                    orderBy = " DESC ";
                }
                return " ORDER BY " + criteria.getOrderByKey() + orderBy;
            }
        }
        return "";
    }

    protected final String generateLimit(SearchCriteria criteria) {
        if (criteria.getLimit() > 0) {
            return " LIMIT " + criteria.getOffset() + "," + criteria.getLimit();
        } else {
            return "";
        }
    }

    protected final void appendCondition(String key, String operator, Object value,
                                         StringBuilder builder) {
        switch (operator) {
            case "=":
            case "eq": {
                if (value instanceof Integer || value instanceof Double)
                    builder.append(" AND ").append(key).append(" = ").append(value);
                else if ("true".equalsIgnoreCase(value.toString()) || "false"
                        .equalsIgnoreCase(value.toString()))
                    builder.append(" AND ").append(key).append(" IS ").append(value);
                else
                    builder.append(" AND ").append(key).append(" = '").append(escapeControlCharacters(value)).append("'");
                break;
            }
            case "!=":
            case "<>": {
                if (value instanceof Integer || value instanceof Double)
                    builder.append(" AND ").append(key).append(" <> ").append(value);
                else
                    builder.append(" AND ").append(key).append(" <> '").append(escapeControlCharacters(value)).append("'");
                break;
            }
            case "like": {
                builder.append(" AND ").append(key).append(" LIKE '%").append(escapeControlCharacters(value)).append("%'");
                break;
            }
            case "%like": {
                builder.append(" AND ").append(key).append(" LIKE '%").append(escapeControlCharacters(value)).append("'");
                break;
            }
            case "like%": {
                builder.append(" AND ").append(key).append(" LIKE '").append(escapeControlCharacters(value)).append("%'");
                break;
            }
            case ">":
            case "gt": {
                if (value instanceof Integer || value instanceof Double)
                    builder.append(" AND ").append(key).append(" > ").append(value).append(" ");
                else
                    builder.append(" AND ").append(key).append(" > '").append(escapeControlCharacters(value)).append("'");
                break;
            }
            case "<":
            case "lt": {
                if (value instanceof Integer || value instanceof Double)
                    builder.append(" AND ").append(key).append(" < ").append(value).append(" ");
                else
                    builder.append(" AND ").append(key).append(" < '").append(escapeControlCharacters(value)).append("'");
                break;
            }
            case ">=":
            case "gte": {
                if (value instanceof Integer || value instanceof Double)
                    builder.append(" AND ").append(key).append(" >= ").append(value).append(" ");
                else
                    builder.append(" AND ").append(key).append(" >= '").append(escapeControlCharacters(value)).append("'");
                break;
            }
            case "<=":
            case "lte": {
                if (value instanceof Integer || value instanceof Double)
                    builder.append(" AND ").append(key).append(" <= ").append(value).append(" ");
                else
                    builder.append(" AND ").append(key).append(" <= '").append(escapeControlCharacters(value)).append("'");
                break;
            }
            case "in": {
                builder.append(" AND ").append(key).append(" IN (").append(escapeControlCharacters(value)).append(")");
                break;
            }
        }
    }

    protected void filter(SearchCriteria criteria, StringBuilder where) {
        //any hard coded filters
    }
}
