# Java Beckhoff library
This library is a simplified and improved version of Beckhoff Java library from [Using the AdsToJava.dll](https://infosys.beckhoff.com/content/1033/tcsample_java/html/tcjavatoads_sample01.html?id=5603283073250120314)

# Functions

 - String getAdsVersion();
     Returns the version of the ADS-router
     
 - boolean isConnected();
     Return true, if connected to ADS-Server, otherwise false
     
 - String getAddress();
     Return connection address as a string
 
 - long openPort(boolean localNetAddr, String txtNetString, int Prt) throws AdsException;
   Connect to TWINCat Message Router.
   
   Parameters:
    - localNetAddr Use local address, if true
    - txtNetString Router address (if localNetAddr = false)
    - Prt Port number
    
   Return:
     Port number, assigned from ADS-Router to the programm

   Throws: 
     AdsException if connection failed
	 

- void closePort() throws AdsException
    Close connection to TWINCat Message Router
    
    Throws:
      AdsException If failed close connection
      
- void readState(AdsState adsStateBuff, AdsState devStateBuff) throws AdsException
    Read the ADS- and Device-Status from ADS-Server
    
    Parameters:
       - adsStateBuff Buffer to store ADS-Status
       - devStateBuff Buffer to store Device-Status
       
    Throws:
       - AdsException If failed
       
 - void readDeviceInfo(AdsDevName devName, AdsVersion adsVersion) throws AdsException
      Read name and version for ADS-Device from ADS-Server
      
      Parameters:
         - devName Buffer to store name of the ADS-Device
	 - adsVersion Buffer to store version of the ADSDevice
	 
- void writeControl(int adsState, int devState, JNIByteBuffer databuff) throws AdsException;
    Change the ADS- and Device-Status from the ADS-Server
    
    Parameters:
       - adsState New ADS-Status
       - devState New Device-Status
       - databuff Data to send to device
       
- void setAdsTimeout(long adsTimeout) throws AdsException
    Change timeout for ADS-Functions (5000ms by default)
    
    Parameters:
       - adsTimeout New timeout value
       
    Throws:
       - AdsException If failed

- void readByIGrpOffs(JNIByteBuffer databuff, long lj_idxGrp, long lj_idxOffs) throws AdsException
    Read data synchrony from ADS-Server by given IndexGroup and IndexOffset
    
    Parameters:
       - databuff Buffer to store readed data
       - lj_idxGrp IndexGroup
       - lj_idxOffs IndexOffset
       
    Throws:
       - AdsException If failed
       
- void writeByIGrpOffs(JNIByteBuffer databuff, long lj_idxGrp, long lj_idxOffs) throws AdsException;
	/**
	 * Write data synchrony to ADS-Device by given IndexGroup and IndexOffset
	 * 
	 * @param databuff Data to write
	 * @param lj_idxGrp IndexGroup
	 * @param lj_idxOffs IndexOffset
	 * 
	 * @throws AdsException 
	 */
	 
-  void getHandleBySymbol(JNIByteBuffer hdlbuff, JNIByteBuffer symbuff) throws AdsException
/**
	 * Read handle by symbol name (symbol like "MAIN.iCounter" name in symbuff) from ADS-Device
	 * 
	 * @param hdlbuff Buffer to store the handle
	 * @param symbuff Symbol name
	 * 
	 * @throws AdsException If failed 
	 */
	 
- void readByHandle(JNIByteBuffer databuff, long symHandle) throws AdsException
  Read variable by handle
  
  /**
	 * 
	 * 
	 * @param databuff Buffer to store readed value
	 * @param symHandle Symbol handle
	 *
	 * @throws AdsException If failed
	 */
	 
- writeByHandle(JNIByteBuffer databuff, long symHandle) throws AdsException
   Write variable by handle
   
   /**
	 * 
	 * 
	 * @param databuff Buffer to write
	 * @param symHandle Symbol handle
	 * 
	 * @throws AdsException If failed
	 */
	 
- void readBySymbol(JNIByteBuffer databuff, JNIByteBuffer symbuff) throws AdsException
    Read variable by symbol name (symbol like "MAIN.iCounter" name in symbuff)
    
    
	/**
	 * 
	 * 
	 * @param databuff Buffer to store variable
	 * @param symbuff Symbol name
	 * 
	 * @throws AdsException If failed
	 */
	 
-  void writeBySymbol(JNIByteBuffer databuff, JNIByteBuffer symbuff) throws AdsException;
    Write variable by symbol name
    
    
	/**
	 * 
	 * 
	 * @param databuff Data to write
	 * @param symbuff Symbol name
	 * 
	 * @return
	 * @throws AdsException If failed
	 */
	 
- void plcStart() throws AdsException;
/**
	 * Start the PLC
	 * 
	 * @throws AdsException 
	 */
	 
- void plcStop() throws AdsException;
/**
	 * Stop the PLC
	 * 
	 * @throws AdsException 
	 */
	 
- void writeFloatValue(String symbolName, float floatValue) throws AdsException;
/**
	 * Set the real value by symbol name
	 * 
	 * @param symbolName Symbol name
	 * @param floatValue Float value to write
	 * @throws AdsException 
	 */
	 
- void writeBooleanValue(String symbolName, boolean boolValue) throws AdsException
/**
	 * Set the boolean value by symbol name
	 * 
	 * @param symbolName Symbol name
	 * @param boolValue Boolean value to write
	 * @throws AdsException 
	 */
	 
- float readFloatValue(String symbolName) throws AdsException
/**
	 * Read real value from PLC by symbol name
	 * 
	 * @param symbolName Symbol name
	 * 
	 * @return real value from PLC
	 * @throws AdsException
	 */
	 
- boolean readBooleanValue(String symbolName) throws AdsException
Read boolean value from PLC by symbol name


	/**
	 * 
	 * 
	 * @param symbolName Symbol name
	 * 
	 * @return boolean value from PLC
	 * @throws AdsException 
	 */
	 
- getPlcStateString(int adsState)
/**
	 * Return the ADS-Status as string
	 * 
	 * @return the ADS-Status
	 */

## Exceptions
       
The AdsException class enthaelt fehlernumber und errror text, wenn es moeglich ist
