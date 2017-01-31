# Java Beckhoff SPS library
This library is a simplified and improved version of Beckhoff Java library from [Using the AdsToJava.dll](https://infosys.beckhoff.com/content/1033/tcsample_java/html/tcjavatoads_sample01.html?id=5603283073250120314). This library can be used instead of the Beckhoffs `CallAdsFuncs.java`, is compatible with all Beckhoffs examples and adds some user-friendly features.

# Functions

:one: `String getAdsVersion();`

Returns the version string of the ADS-router

---
     
:two: `boolean isConnected();`

Returns `true`, if is connected to the ADS-router, otherwise `false`

---
  
:three: `String getAddress();`

Return the address of the ADS-router as a string

---
 
:four: `long openPort(boolean localNetAddr, String txtNetString, int Prt);`

Connect to the ADS-router.
   
**Parameters**:
   - *localNetAddr*	If = true, will be used localhost address to connection
   - *txtNetString*	Connection address (will be used if `localNetAddr = false`)
   - *prt*		Port number
    
**Return**
     Port number, assigned from ADS-Router to the connection
     
---

:five: `void closePort();`

Close current connection

---
      
:six: `void plcStart();`

Start the PLC on ADS-router

---
	 
:seven: `void plcStop();`

Stop the PLC on ADS-router

---
      
:eight: `void readState(AdsState adsStateBuff, AdsState devStateBuff)`

Read the ADS- and device status from ADS-router
    
**Parameters**
    - *adsStateBuff*		The buffer to store ADS status
    - *devStateBuff*		The buffer to store device status
    
---
       
:nine: `void readDeviceInfo(AdsDevName devName, AdsVersion adsVersion);`

Read name and version of an ADS device
      
**Parameters**
    - *devName*			The buffer to store name of the ADS device
    - *adsVersion*		The buffer to store version of the ADS device
    
---
	 
:one::zero: `void writeControl(int adsState, int devState, JNIByteBuffer databuff);`

Change the ADS- and device status
    
**Parameters**
    - *adsState*		The ADS state to set
    - *devState*		The device state to set
    - *databuff*		The data to send to device
    
---
       
:one::one: `void setAdsTimeout(long adsTimeout)`

Change timeout for all ADS functions (5000 ms by default)
    
**Parameters**
    - *adsTimeout*		Timeout to set

---

:one::two: `void readByIGrpOffs(JNIByteBuffer databuff, long lj_idxGrp, long lj_idxOffs);`

Read data synchrony from ADS-router by given IndexGroup and IndexOffset
    
**Parameters**
    - *databuff*		The buffer to store readed data
    - *lj_idxGrp*		IndexGroup
    - *lj_idxOffs*		IndexOffset

---

:one::three: `void writeByIGrpOffs(JNIByteBuffer databuff, long lj_idxGrp, long lj_idxOffs);`

Write data synchrony to ADS-router by given IndexGroup and IndexOffset

**Parameters**
    - *databuff*		A data to write
    - *lj_idxGrp*		IndexGroup
    - *lj_idxOffs*		IndexOffset

---

:one::four: `void getHandleBySymbol(JNIByteBuffer hdlbuff, JNIByteBuffer symbuff);`

Read handle by symbol name (symbol like "MAIN.iCounter" name in symbuff) from PLC

**Parameters**
    - *hdlbuff*			The buffer to store the handle
    - *symbuff*			The symbol name

---

:one::five: `void readByHandle(JNIByteBuffer databuff, long symHandle)`;

Read a variable from PLC by handle

**Parameters**
    - *databuff*		The buffer to store readed value
    - *symHandle*		The handle of the symbol

---

:one::six: `writeByHandle(JNIByteBuffer databuff, long symHandle);`

Store a variable into PLC by symbol handle

**Parameters**
    - *databuff*		The buffer with a variable
    - *symHandle*		The handle of the symbol

---

:one::seven: `void readBySymbol(JNIByteBuffer databuff, JNIByteBuffer symbuff);`

Read a variable by symbol name (symbol like "MAIN.iCounter" name in symbuff)

**Parameters**
    - *databuff*		The buffer to store variable
    - *symbuff*			The name of the symbol

---

:one::eight: `void writeBySymbol(JNIByteBuffer databuff, JNIByteBuffer symbuff);`

Write variable by symbol name

**Parameters**
    - *databuff*		The data to write
    - *symbuff*			Symbol name

---

:one::nine: `void writeFloatValue(String symbolName, float floatValue);`

Set the real value by symbol name

**Parameters**
    - *symbolName*		Symbol name
    - *floatValue*		Float value to write

---

:two::zero: `void writeBooleanValue(String symbolName, boolean boolValue);`

Set the boolean value by symbol name

**Parameters**
    - *symbolName*		Symbol name
    - *boolValue*		Boolean value to write

---

:two::one: `float readFloatValue(String symbolName);`

Read real value from PLC by symbol name

**Parameters**
    - *symbolName*		Symbol name
    
**Return**
    The readed from PLC value

---

:two::two: `boolean readBooleanValue(String symbolName);`

Read boolean value from PLC by symbol name

**Parameters**
    - *symbolName*		Symbol name
    
**Return**
    -  * @return boolean value from PLC
	 * @throws AdsException 
	 */

---

:two::three: `String getPlcStateString(int adsState);`

Return the ADS-Status as string

**Return**
The ADS status as a string


## Exceptions
       
The AdsException class enthaelt fehlernumber und errror text, wenn es moeglich ist
