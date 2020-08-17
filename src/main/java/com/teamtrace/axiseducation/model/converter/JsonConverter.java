package com.teamtrace.axiseducation.model.converter;

import org.bson.Document;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class JsonConverter implements AttributeConverter<Document, String> {

    @Override
    public String convertToDatabaseColumn(Document document) {
        if (document != null) {
            return document.toJson();
        }
        return null;
    }

    @Override
    public Document convertToEntityAttribute(String dbData) {
        if (dbData != null) return Document.parse(dbData);
        return null;
    }

}
