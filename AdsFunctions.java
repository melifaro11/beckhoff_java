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

import de.beckhoff.jni.Convert;
import de.beckhoff.jni.JNIByteBuffer;
import de.beckhoff.jni.tcads.*;

/**
 * Adapter class for access to an ADS router via native Beckhoffs dll AdsToJava.dll
 */
public class AdsFunctions {
	
	/**
	 * The size of a float value in PLC
	 */
	private static final int BUFFERSIZE_FLOAT = 4;
	
	/**
	 * The size of a boolean value in PLC
	 */
	private static final int BUFFERSIZE_BOOLEAN = 1;
	
	/**
	 * Port number, assigned from the ADS router
	 */
	private long port = 0;

	/**
	 * The {@link AmsAddr} of the ADS router
	 */
	private AmsAddr addr = new AmsAddr();

	/**
	 * Default constructor
	 */
	public AdsFunctionsHardware() {
	}

	/**
	 * Returns the version of the ADS dll-library
	 * 
	 * @return The version of the ADS dll library
	 */
	public String getAdsVersion() {

		AdsVersion lAdsVersion = AdsCallDllFunction.adsGetDllVersion();

		return new Integer(lAdsVersion.getVersion()) + "." + new Integer(lAdsVersion.getRevision()) + "."
				+ lAdsVersion.getBuild();
	}
	
	/**
	 * Return true, if connected to ADS-Server
	 * 
	 * @return true, if connected to ADS-Server
	 */
	public boolean isConnected() {
		return port != 0;
	}
	
	/**
	 * Return connection address as String
	 * 
	 * @return Connection address
	 */
	public String getAddress() {
		return addr.getNetIdString() + ":" + addr.getPort();
	}

	/**
	 * Connect to TWINCat Message Router
	 * 
	 * @param localNetAddr Use local address, if true
	 * @param txtNetString Router address (if localNetAddr = false)
	 * @param Prt Port number (???)
	 * 
	 * @return Port number, assigned from ADS-Router to the programm
	 * 
	 * @throws AdsException if connection failed
	 */
	public long openPort(boolean localNetAddr, String txtNetString, int Prt) throws AdsException {

		if (port == 0) {

			port = AdsCallDllFunction.adsPortOpen();

			if (localNetAddr == true) {
				addr.setNetIdStringEx(txtNetString);
			} else {
				long nErr = AdsCallDllFunction.getLocalAddress(addr);

				if (nErr != 0) {
					AdsCallDllFunction.adsPortClose();
					throw new AdsException("openPort() -> getLocalAddress() failed with code: " + nErr);
				}
			}
			
			addr.mPort = Prt;
		}

		return port;
	}

	/**
	 * Close connection to TWINCat Message Router
	 * 
	 * @throws AdsException If failed close connection
	 */
	public synchronized void closePort() throws AdsException {
		if (port != 0) {
		
			long nErr = AdsCallDllFunction.adsPortClose();

			port = 0;

			if (nErr != 0)
				throw new AdsException((int)nErr, "closePort() failed with code: " + nErr);
		}
	}

	/**
	 * Read the ADS- and Device-Status from ADS-Server
	 * 
	 * @param adsStateBuff Buffer to store ADS-Status
	 * @param devStateBuff Buffer to store Device-Status
	 * 
	 * @return
	 * 
	 * @throws AdsException If failed
	 */
	public synchronized void readState(AdsState adsStateBuff, AdsState devStateBuff) throws AdsException {

		if (port == 0)
			throw new AdsException(1864, "readState() failed: ADS-Port not opened");
		
		long nErr = AdsCallDllFunction.adsSyncReadStateReq(addr, adsStateBuff, devStateBuff);

		if (nErr != 0)
			throw new AdsException((int)nErr, "readState() failed with code: " + nErr);
	}

	/**
	 * Read name and version for ADS-Device from ADS-Server
	 * 
	 * @param devName Buffer to store name of the ADS-Device
	 * @param adsVersion Buffer to store version of the ADSDevice
	 *
	 * @throws AdsException 
	 */
	public synchronized void readDeviceInfo(AdsDevName devName, AdsVersion adsVersion) throws AdsException {

		if (port == 0)
			throw new AdsException(1864, "ADS-Port not opened");

		long nErr = AdsCallDllFunction.adsSyncReadDeviceInfoReq(addr, devName, adsVersion);
		
		if (nErr != 0)
			throw new AdsException((int)nErr, "readDeviceInfo() failed with code: " + nErr);
	}

	/**
	 * Change the ADS- and Device-Status from the ADS-Server
	 * 
	 * @param adsState New ADS-Status
	 * @param devState New Device-Status
	 * @param databuff Data to send to device
	 *
	 * @throws AdsException If failed
	 */
	public synchronized void writeControl(int adsState, int devState, JNIByteBuffer databuff) throws AdsException {

		if (port == 0)
			throw new AdsException(1864, "ADS-Port not opened");
		
		long nErr = AdsCallDllFunction.adsSyncWriteControlReq(addr, adsState, devState, databuff.getUsedBytesCount(),
					databuff);

		if (nErr != 0)
			throw new AdsException((int)nErr, "writeControl() failed with code: " + nErr);
	}

	/**
	 * Change timeout for ADS-Functions (5000ms by default)
	 * 
	 * @param adsTimeout New timeout value
	 * @throws AdsException If failed
	 */
	public synchronized void setAdsTimeout(long adsTimeout) throws AdsException {

		if (port == 0)
			throw new AdsException(1864, "ADS-Port not opened");
		
		long nErr = AdsCallDllFunction.adsSyncSetTimeout(adsTimeout);
		
		if (nErr != 0)
			throw new AdsException((int)nErr, "setAdsTimeout() failed with code: " + nErr);
	}

	/**
	 * Read data synchrony from ADS-Server by given IndexGroup and IndexOffset
	 * 
	 * @param databuff Buffer to store readed data
	 * @param lj_idxGrp IndexGroup
	 * @param lj_idxOffs IndexOffset
	 * 
	 * @throws AdsException If failed
	 */
	public synchronized void readByIGrpOffs(JNIByteBuffer databuff, long lj_idxGrp, long lj_idxOffs) throws AdsException {

		if (port == 0)
			throw new AdsException(1864, "ADS-Port not opened");
		
		long nErr = AdsCallDllFunction.adsSyncReadReq(addr, lj_idxGrp, lj_idxOffs, databuff.getUsedBytesCount(),
					databuff);
		
		if (nErr != 0)
			throw new AdsException((int)nErr, "readByIGrpOffs() failed with code: " + nErr);
	}

	/**
	 * Write data synchrony to ADS-Device by given IndexGroup and IndexOffset
	 * 
	 * @param databuff Data to write
	 * @param lj_idxGrp IndexGroup
	 * @param lj_idxOffs IndexOffset
	 * 
	 * @throws AdsException 
	 */
	public synchronized void writeByIGrpOffs(JNIByteBuffer databuff, long lj_idxGrp, long lj_idxOffs) throws AdsException {

		if (port == 0)
			throw new AdsException(1864, "ADS-Port not opened");

		long nErr = AdsCallDllFunction.adsSyncWriteReq(addr, lj_idxGrp, lj_idxOffs, databuff.getUsedBytesCount(),
					databuff);
		
		if (nErr != 0)
			throw new AdsException((int)nErr, "readByIGrpOffs() failed with code: " + nErr);
	}

	/**
	 * Read handle by symbol name (symbol like "MAIN.iCounter" name in symbuff) from ADS-Device
	 * 
	 * @param hdlbuff Buffer to store the handle
	 * @param symbuff Symbol name
	 * 
	 * @throws AdsException If failed 
	 */
	public synchronized void getHandleBySymbol(JNIByteBuffer hdlbuff, JNIByteBuffer symbuff) throws AdsException {

		if (port == 0)
			throw new AdsException(1864, "ADS-Port not opened");

		long nErr = AdsCallDllFunction.adsSyncReadWriteReq(addr, 0xF003, 0x0, hdlbuff.getUsedBytesCount(), hdlbuff,
						symbuff.getUsedBytesCount(), symbuff);
		
		if (nErr != 0)
			throw new AdsException((int)nErr, "getHandleBySymbol() failed with code: " + nErr);

	}

	/**
	 * Read variable by handle
	 * 
	 * @param databuff Buffer to store readed value
	 * @param symHandle Symbol handle
	 *
	 * @throws AdsException If failed
	 */
	public synchronized void readByHandle(JNIByteBuffer databuff, long symHandle) throws AdsException {

		if (port == 0)
			throw new AdsException(1864, "ADS-Port not opened");

			if (symHandle != 0) {
				long nErr = AdsCallDllFunction.adsSyncReadReq(addr, 0xF005, symHandle, databuff.getUsedBytesCount(),
						databuff);
				
				if (nErr != 0)
					throw new AdsException((int)nErr, "readByHandle() failed with code: " + nErr);
			}
			else
				throw new AdsException(1809, "Invalid symbol handle");
	}

	/**
	 * Write variable by handle
	 * 
	 * @param databuff Buffer to write
	 * @param symHandle Symbol handle
	 * 
	 * @throws AdsException If failed
	 */
	public synchronized void writeByHandle(JNIByteBuffer databuff, long symHandle) throws AdsException {

		if (port == 0)
			throw new AdsException(1864, "ADS-Port not opened");

			if (symHandle != 0) {
				long nErr = AdsCallDllFunction.adsSyncWriteReq(addr, 0xF005, symHandle, databuff.getUsedBytesCount(),
						databuff);
				if (nErr != 0)
					throw new AdsException((int)nErr, "readByHandle() failed with code: " + nErr);
			}
			else
				throw new AdsException(1809, "Invalid symbol handle");
	}

	/**
	 * Read variable by symbol name (symbol like "MAIN.iCounter" name in symbuff)
	 * 
	 * @param databuff Buffer to store variable
	 * @param symbuff Symbol name
	 * 
	 * @throws AdsException If failed
	 */
	public synchronized void readBySymbol(JNIByteBuffer databuff, JNIByteBuffer symbuff) throws AdsException {
		JNIByteBuffer hdlbuff = new JNIByteBuffer(4);

		getHandleBySymbol(hdlbuff, symbuff);

		long symHandle = Convert.ByteArrToInt(hdlbuff.getByteArray());

		readByHandle(databuff, symHandle);
	}

	/**
	 * Write variable by symbol name
	 * 
	 * @param databuff Data to write
	 * @param symbuff Symbol name
	 * 
	 * @return
	 * @throws AdsException If failed
	 */
	public synchronized void writeBySymbol(JNIByteBuffer databuff, JNIByteBuffer symbuff) throws AdsException {
		JNIByteBuffer hdlbuff = new JNIByteBuffer(4);
		
		getHandleBySymbol(hdlbuff, symbuff);

		long symHandle = Convert.ByteArrToInt(hdlbuff.getByteArray());

		writeByHandle(databuff, symHandle);
	}
	
	/**
	 * Start the PLC
	 * 
	 * @throws AdsException 
	 */
	public synchronized void plcStart() throws AdsException {
		writeControl(AdsState.ADSSTATE_RUN, 0, new JNIByteBuffer(0));
	}
	
	/**
	 * Stop the PLC
	 * 
	 * @throws AdsException 
	 */
	public synchronized void plcStop() throws AdsException {
		writeControl(AdsState.ADSSTATE_STOP, 0, new JNIByteBuffer(0));
	}

	/**
	 * Set the real value by symbol name
	 * 
	 * @param symbolName Symbol name
	 * @param floatValue Float value to write
	 * @throws AdsException 
	 */
	public synchronized void writeFloatValue(String symbolName, float floatValue) throws AdsException {

		JNIByteBuffer databuff = new JNIByteBuffer(Convert.FloatToByteArr(floatValue));

		writeBySymbol(databuff, new JNIByteBuffer(symbolName.getBytes()));
	}
	
	/**
	 * Set the boolean value by symbol name
	 * 
	 * @param symbolName Symbol name
	 * @param boolValue Boolean value to write
	 * @throws AdsException 
	 */
	public synchronized void writeBooleanValue(String symbolName, boolean boolValue) throws AdsException {

		JNIByteBuffer symbuff = new JNIByteBuffer(symbolName.getBytes());
		
		JNIByteBuffer databuff = new JNIByteBuffer(Convert.BoolToByteArr(boolValue));

		writeBySymbol(databuff, symbuff);
	}
	
	/**
	 * Read real value from PLC by symbol name
	 * 
	 * @param symbolName Symbol name
	 * 
	 * @return real value from PLC
	 * @throws AdsException
	 */
	public synchronized float readFloatValue(String symbolName) throws AdsException {
		
		JNIByteBuffer databuff = new JNIByteBuffer(BUFFERSIZE_FLOAT);

		readBySymbol(databuff, new JNIByteBuffer(symbolName.getBytes()));

		return Convert.ByteArrToFloat(databuff.getByteArray());
	}
	
	/**
	 * Read boolean value from PLC by symbol name
	 * 
	 * @param symbolName Symbol name
	 * 
	 * @return boolean value from PLC
	 * @throws AdsException 
	 */
	public synchronized boolean readBooleanValue(String symbolName) throws AdsException {

		JNIByteBuffer databuff = new JNIByteBuffer(BUFFERSIZE_BOOLEAN);

		readBySymbol(databuff, new JNIByteBuffer(symbolName.getBytes()));

		return Convert.ByteArrToBool(databuff.getByteArray());
	}
	
	/**
	 * Return the ADS-Status as string
	 * 
	 * @return the ADS-Status
	 */
	public String getPlcStateString(int adsState) {
		
		if (adsState == -1)
			return "Unavailable";

		switch (adsState) {
		case AdsState.ADSSTATE_ERROR:
			return "error";
		case AdsState.ADSSTATE_IDLE:
			return "idle";
		case AdsState.ADSSTATE_INIT:
			return "init";
		case AdsState.ADSSTATE_INVALID:
			return "invalid";
		case AdsState.ADSSTATE_LOADCFG:
			return "loadcfg";
		case AdsState.ADSSTATE_POWERFAILURE:
			return "power failure";
		case AdsState.ADSSTATE_POWERGOOD:
			return "powergood";
		case AdsState.ADSSTATE_RESET:
			return "reset";
		case AdsState.ADSSTATE_RUN:
			return "run";
		case AdsState.ADSSTATE_SAVECFG:
			return "save configuration";
		case AdsState.ADSSTATE_START:
			return "start";
		case AdsState.ADSSTATE_STOP:
			return "stop";
		default:
			return "unknown state (" + adsState + ")";
		}
	}
}
