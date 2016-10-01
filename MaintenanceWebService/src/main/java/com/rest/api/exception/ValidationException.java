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

	public ValidationException(final String fieldName, final String fieldValue, final String arg0, final Throwable arg1,
			final boolean arg2, final boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;

	}

	public ValidationException(final String fieldName, final String fieldValue, final String arg0, final Throwable arg1) {
		super(arg0, arg1);
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		// TODO Auto-generated constructor stub
	}

	public ValidationException(final String fieldName, final String fieldValue, final String arg0) {
		super(arg0);
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public ValidationException(final String fieldName, final String fieldValue,final Throwable arg0) {
		super(arg0);
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
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

	public void setFieldValue(final String fieldValue) {
		this.fieldValue = fieldValue;
	}

}
