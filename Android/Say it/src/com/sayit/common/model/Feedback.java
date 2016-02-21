package com.sayit.common.model;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class Feedback implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attribute;
	private int attributeIndex;

	public Feedback(String attribute,int attributeIndex) {
		this.attribute = attribute;
		this.attributeIndex = attributeIndex;
	}

	public String getAttribute() {
		return attribute;
	}
	
	public int getAttributeIndex() {
		return attributeIndex;
	}

	public static Feedback jsonToPontoReferencia(JSONObject pontoReferencia) {
		try {
			//Feedback result = new Feedback();
			String attribute =  pontoReferencia.get("v").toString();
			int index = Integer.parseInt( pontoReferencia.get("f").toString());
			
			return new Feedback(attribute, index);
		} catch (JSONException ex) {
			Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
}