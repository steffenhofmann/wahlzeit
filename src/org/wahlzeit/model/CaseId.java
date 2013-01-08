package org.wahlzeit.model;

public class CaseId {
	/**
	 * 
	 */
	private final int value;
	
	/**
	 * 
	 */
	private final String stringvalue;
	
	/**
	 * What a hack :-)
	 */
	public static final int ID_START = getFromString("x1abz") + 1 ;
	
	/**
	 * 
	 */
	public static final CaseId NULL_ID = new CaseId(0);
	
	/**
	 * 
	 */
	public CaseId(int id){
		if(id < 0) throw new IllegalArgumentException();
		
		this.value = id;
		this.stringvalue = getFromInt(id);
	}
	
	/**
	 * 
	 */
	public CaseId(String id){
		if(id == null) throw new IllegalArgumentException();
		
		this.value = getFromString(id);
		this.stringvalue = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaseId other = (CaseId) obj;
		if (stringvalue == null) {
			if (other.stringvalue != null)
				return false;
		} else if (!stringvalue.equals(other.stringvalue))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	/**
	 * 
	 */
	public boolean isEqual(CaseId other) {
		return other.asInt() == value;
	}
	
	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((stringvalue == null) ? 0 : stringvalue.hashCode());
		result = prime * result + value;
		return result;
	}
	
	/**
	 * 
	 */
	public boolean isNullId() {
		return this == NULL_ID;
	}
	
	/**
	 * 
	 */
	public int asInt() {
		return value;
	}
	
	/**
	 * 
	 */
	public String asString() {
		return stringvalue;
	}
	
	/**
	 * 
	 */
	public static String getFromInt(int id) {
		StringBuffer result = new StringBuffer(10);
		
		id += ID_START;
		for ( ; id > 0;	id = id / 36 ) {
			char letterOrDigit;
			int modulus = id % 36;
			if (modulus < 10) {
				letterOrDigit = (char) ((int) '0' + modulus);
			} else {
				letterOrDigit = (char) ((int) 'a' - 10 + modulus);
			}
			result.insert(0, letterOrDigit);

		}
		
		return "x" + result.toString();
	}
	
	/**
	 * 
	 */
	public static int getFromString(String value) {
		int result = 0;		
		for (int i = 1; i < value.length(); i ++ ) {
			int temp = 0;
			char letterOrDigit = value.charAt(i);
			if (letterOrDigit < 'a') {
				temp = (int) letterOrDigit - '0';
			} else {
				temp = 10 + (int) letterOrDigit - 'a';
			}
			result = result * 36 + temp; 
		}
			
		result -= ID_START;
		if (result < 0) {
			result = 0;
		}

		return result;
	}
}
