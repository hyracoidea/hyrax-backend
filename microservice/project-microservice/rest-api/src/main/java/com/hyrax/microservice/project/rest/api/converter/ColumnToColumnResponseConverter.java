package com.hyrax.microservice.project.rest.api.converter;

import com.hyrax.microservice.project.rest.api.domain.response.ColumnResponse;
import com.hyrax.microservice.project.service.domain.Column;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ColumnToColumnResponseConverter implements Converter<Column, ColumnResponse> {

    @Override
    public ColumnResponse convert(final Column column) {
        return ColumnResponse.builder()
                .columnId(column.getColumnId())
                .columnName(column.getColumnName())
                .columnIndex(column.getColumnIndex())
                .build();
    }
}
