package com.rest.api.exception;

/**
 * 
 * @author vinayaksm
 *
 */
public class ValidationException extends RuntimeException {
	private String fieldName;
	private String fieldValue;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException() {
		super();
	}

	public ValidationException(final String fieldName, final String fieldType, final String arg0, final Throwable arg1,
			final boolean arg2, final boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		this.fieldName = fieldName;
		this.fieldValue = fieldType;

	}

	public ValidationException(final String fieldName, final String fieldType, final String arg0, final Throwable arg1) {
		super(arg0, arg1);
		this.fieldName = fieldName;
		this.fieldValue = fieldType;
		// TODO Auto-generated constructor stub
	}

	public ValidationException(final String fieldName, final String fieldType, final String arg0) {
		super(arg0);
		this.fieldName = fieldName;
		this.fieldValue = fieldType;
	}

	public ValidationException(final String fieldName, final String fieldType,final Throwable arg0) {
		super(arg0);
		this.fieldName = fieldName;
		this.fieldValue = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(final String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(final String fieldType) {
		this.fieldValue = fieldType;
	}

}
