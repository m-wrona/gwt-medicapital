package com.medicapital.server.database.hibernate.criteria;

import static com.medicapital.server.log.Tracer.tracer;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.type.BooleanType;
import org.hibernate.type.EnumType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import com.medicapital.common.util.Util;
import com.medicapital.server.log.Tracer;

/**
 * Selector includes not nullable and not empty values.
 * 
 * @author mwronski
 * 
 */
final class NotNullPropertySelector implements PropertySelector {

	private static final long serialVersionUID = -1675552764314438248L;

	@Override
	final public boolean include(final Object fieldValue, final String fieldName, final Type fieldType) {
		try {
			Tracer.tracer(this).debug("including property - name: " + fieldName + ", value: " + fieldValue + ", type:" + fieldType);
			if (fieldValue == null) {
				return false;
			} else if (fieldType.getClass().equals(StringType.class) || fieldType.getClass().equals(EnumType.class)) {
				return !Util.isEmpty(fieldValue.toString());
			} else if (fieldType.getClass().equals(BooleanType.class)) {
				return false;
			} else if (fieldType.getClass().equals(FloatType.class)) {
				float value = new Float(fieldValue.toString());
				return value > 0;
			} else if (fieldType.getClass().equals(IntegerType.class)) {
				int value = new Integer(fieldValue.toString());
				return value > 0;
			}
			return true;
		} catch (final Exception e) {
			tracer(this).error("Error while including " + fieldName + " - type: " + fieldType + ", object: " + fieldValue, e);
			return false;
		}
	}
}
