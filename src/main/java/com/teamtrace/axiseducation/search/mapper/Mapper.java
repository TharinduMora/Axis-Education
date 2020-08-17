package com.teamtrace.axiseducation.search.mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface Mapper {

    List parseResults(Connection con, ResultSet rs, int limit) throws SQLException;

    HashMap<String, String> getColumnMap();

}
