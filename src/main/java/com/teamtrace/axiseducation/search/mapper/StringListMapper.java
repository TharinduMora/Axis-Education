package com.teamtrace.axiseducation.search.mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StringListMapper implements Mapper {
    @Override
    public List parseResults(Connection con, ResultSet rs, int limit) throws SQLException {
        List<String> list = new ArrayList<>(10);
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        return list;
    }

    @Override
    public HashMap<String, String> getColumnMap() {
        return null;
    }
}
