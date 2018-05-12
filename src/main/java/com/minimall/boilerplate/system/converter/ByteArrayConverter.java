package com.minimall.boilerplate.system.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Objects.nonNull;

/**
 * Title: byte[]类型转换器.
 * <p>Description: 数据库byte[]类型和Java Long类型转换器</p>
 *
 * @author huangtao
 */
@Converter
public class ByteArrayConverter implements AttributeConverter<Long, byte[]> {

	@Override
	public byte[] convertToDatabaseColumn(final Long entityValue) {
    if(nonNull(entityValue)) {
      byte[] b = new byte[8];
      b[0] = (byte) ((entityValue >> 56) & 0xff);
      b[1] = (byte) ((entityValue >> 48) & 0xff);
      b[2] = (byte) ((entityValue >> 40) & 0xff);
      b[3] = (byte) ((entityValue >> 32) & 0xff);
      b[4] = (byte) ((entityValue >> 24) & 0xff);
      b[5] = (byte) ((entityValue >> 16) & 0xff);
      b[6] = (byte) ((entityValue >> 8) & 0xff);
      b[7] = (byte) (entityValue & 0xff);
      return b;
    }
    return null;
	}

	@Override
	public Long convertToEntityAttribute(final byte[] databaseValue) {
    if(nonNull(databaseValue)) {
      return (((long) (databaseValue[0] & 0xff) << 56)
          | ((long) (databaseValue[1] & 0xff) << 48)
          | ((long) (databaseValue[2] & 0xff) << 40)
          | ((long) (databaseValue[3] & 0xff) << 32)
          | ((long) (databaseValue[4] & 0xff) << 24)
          | ((long) (databaseValue[5] & 0xff) << 16)
          | ((long) (databaseValue[6] & 0xff) << 8)
          | ((long) (databaseValue[7] & 0xff)));
    }
    return null;
	}
}