package com.teamtrace.axiseducation.search;

import com.teamtrace.axiseducation.api.request.SearchCriteria;
import com.teamtrace.axiseducation.api.response.SearchResponse;
import com.teamtrace.axiseducation.search.mapper.Mapper;

public abstract class SearchTemplate {
    protected final String selectSQL;
    protected Mapper mapper;

    public SearchTemplate(String selectSQL, Mapper mapper) {
        this.selectSQL = selectSQL;
        this.mapper = mapper;
    }

    public final SearchResponse findByCriteria(SearchCriteria criteria) {
        SearchResponse response = generateSearchResponse(criteria);
        execute(criteria, response);
        postProcess(criteria, response);

        return response;
    }

    protected final SearchResponse generateSearchResponse(SearchCriteria criteria) {
        SearchResponse response = new SearchResponse();
        response.setOffset(criteria.getOffset());
        response.setLimit(criteria.getLimit());

        return response;
    }

    protected abstract void execute(SearchCriteria criteria, SearchResponse response);

    protected SearchResponse postProcess(SearchCriteria criteria, SearchResponse response) {
        return response;
    }

    protected final String escapeControlCharacters(Object text) {
        if (text != null) {
            return String.valueOf(text).replaceAll("'", "\'");
        }
        return null;
    }
}
