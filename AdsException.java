/**
 * 
 */
package de.whz.research.tempcontrol.sps;

/**
 * Exception class for ADS-functions
 * 
 * @author Alexandr Mitiaev
 */
public class AdsException extends Exception {
	
	/**
	 * Integer error code
	 */
	private Integer errorCode;
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -7061270224430606676L;
	
	/**
	 * Create new {@link AdsException} object
	 */
	public AdsException() {
		this.errorCode = 0;
	}

	/**
	 * Create new {@link AdsException} object with error code = 0
	 * 
	 * @param message Error message
	 */
	public AdsException(String message) {
		super(message);
		
		this.errorCode = 0;
	}
	
	/**
	 * Create new {@link AdsException} object
	 * 
	 * @param errorCode Error code
	 * @param message Error message
	 */
	public AdsException(Integer errorCode, String message) {
		super(message);
		
		this.errorCode = errorCode;
	}
	
	/**
	 * Returns code of the error
	 * 
	 * @return error code
	 */
	public Integer getErrorCode() {
		return this.errorCode;
	}
	
	/**
	 * Set new error code
	 * 
	 * @param errorCode new value for {@link #errorCode}
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
