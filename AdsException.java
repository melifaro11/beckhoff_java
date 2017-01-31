/**
 * Copyright 2016-2017 Alexandr Mitiaev
 * 
 * This file is part of Beckhoff ADS library.
 * 
 * Beckhoff ADS library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beckhoff ADS library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Beckhoff ADS library. If not, see <http://www.gnu.org/licenses/>.
 */
package de.whz.research.tempcontrol.sps;

/**
 * Exception class for ADS functions
 */
public class AdsException extends Exception {
	
	/**
	 * Integer error code value
	 */
	private Integer errorCode;
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -7061270224430606676L;
	
	/**
	 * Create new {@link AdsException} object with error code = 0
	 */
	public AdsException() {
		this.errorCode = 0;
	}

	/**
	 * Create new {@link AdsException} object with error code = 0
	 * 
	 * @param message Message with a description of the errror
	 */
	public AdsException(String message) {
		super(message);
		this.errorCode = 0;
	}
	
	/**
	 * Create new {@link AdsException} object with a given error code
	 * 
	 * @param errorCode Error code
	 * @param message Message with a description of the error
	 */
	public AdsException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	/**
	 * Returns code of the error
	 * 
	 * @return Error code
	 */
	public Integer getErrorCode() {
		return this.errorCode;
	}
	
	/**
	 * Set new error code value
	 * 
	 * @param errorCode New value for error code
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
