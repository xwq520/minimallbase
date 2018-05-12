package com.minimall.boilerplate.system.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.Instant;

import static java.util.Objects.nonNull;

/**
 * Title: DateTime类型转换器.
 * <p>Description: 数据库DateTime类型和Java Long类型转换器</p>
 *
 * @author huangtao
 */
@Converter
public class DateTimeConverter implements AttributeConverter<Long, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(Long entityValue) {
    return nonNull(entityValue) ?
        Timestamp.from(Instant.ofEpochMilli(entityValue)) :
        null;
	}

	@Override
	public Long convertToEntityAttribute(Timestamp databaseValue) {
    return nonNull(databaseValue) ?
        databaseValue.toInstant().toEpochMilli() :
        null;
	}
}