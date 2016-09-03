package com.rest.request;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="response")
public class ValidationResponse extends BaseResponse {
	private Map<String, String> fieldMsgMap = new HashMap<String, String>();

	public Map<String, String> getFieldMsgMap() {
		return fieldMsgMap;
	}

	public void setFieldMsgMap(Map<String, String> fieldMsgMap) {
		this.fieldMsgMap = fieldMsgMap;
	}
	public void addFieldMsg(String field,String msg){
		fieldMsgMap.put(field, msg);
	}
	
	
}
