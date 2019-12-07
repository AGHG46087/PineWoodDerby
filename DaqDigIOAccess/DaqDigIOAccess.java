package DaqDigIOAccess;

import com.hgutil.ParseData;

/**
 * author:      hgrein<BR> * date:        Jun 7, 2004<BR> * Package:     DaqDigIOAccess<BR> * File Name:   DaqDigIOAccess.java<BR> * Type Name:   DaqDigIOAccess<BR> * Description: A Interface Class that provides capabilites to  * communicate with a "C DLL" to access the Daq2000 series card. * NOTE: When all usage of this class is completed the consumer must call the * destroy() method.<BR> * NOTE:  Any Methods that ndicate that it is a <PRE>CALLBACK</PRE> do not use these * methods, they are intended as callbacks from the DLL<BR> * NOTE: If if you are using this code in a SIMULATION manner and not actually connected * to the box where the Daq2000 series card resides.  Then it is imperative that the  * JVM argument SIM_DAQ_ACCESS is set to true.  For example:<BR> * -DSIM_DAQ_ACCESS=true
 */


public class DaqDigIOAccess
{
  /** Field <code>SIM_MODE</code> : boolean */
  private static boolean SIM_MODE = ParseData.parseBool(System.getProperties().getProperty("SIM_DAQ_ACCESS"), false);
  static
  {
    if(!SIM_MODE)
    {  
      System.loadLibrary("DaqDigIOAccessImpl");
    }
  }
  /** Field <code>LANES</code> : int */
  public static final int LANES = 12;
  /** Field <code>daqIOAccess</code> : DaqDigIOAccess */
  private static  DaqDigIOAccess daqIOAccess = null;

  /**
   * Field <code>deviceName</code>: String
   * 
   * @uml.property name="deviceName"
   */
  private String deviceName = null;

  /**
   * Field <code>errorMsg</code> : String
   * 
   * @uml.property name="errorMsg"
   */
  private String errorMsg = null;

  /**
   * Field <code>timeout</code> : int
   * 
   * @uml.property name="timeout"
   */
  private int timeout = 10;

  /**
   * Field <code>gateArmed</code> : boolean
   * 
   * @uml.property name="gateArmed"
   */
  private boolean gateArmed = false;

  /**
   * Field <code>laneTimes</code> : double[]
   * 
   * @uml.property name="laneTimes"
   */
  private double[] laneTimes = new double[LANES];

  /**
   * Field <code>taggedLanes</code> : boolean[]
   * 
   * @uml.property name="taggedLanes"
   */
  private boolean[] taggedLanes = new boolean[LANES];




  /** Field <code>selectedLanesForRace</code> : boolean[] */
  private static boolean[] selectedLanesForRace = new boolean[LANES];
  /** Field <code>selectedLanesForRaceCount</code> : int */
  private static int selectedLanesForRaceCount = LANES;
  
  /**
   * Method getInstance.  Returns an instance of the DaqDigIOAccess.  Note this class is singleton
   * especially due to the Nature of the resources.  There is only one resource.
   * @return DaqDigIOAccess
   */
  public static DaqDigIOAccess getInstance()
  {
    if (daqIOAccess == null)
    {
      daqIOAccess = new DaqDigIOAccess();
      if( !SIM_MODE )
      {  
        daqIOAccess.allocateResources();
        daqIOAccess.testDaqDigIOCard();
      }
    }
    return daqIOAccess;
  }
  /**
   * Method getSelectedLanesForRace.  Returns selectedLanesForRace of the DaqDigIOAccess
   * @return boolean[]: Returns the selectedLanesForRace.
   */
  public static boolean[] getSelectedLanesForRace()
  {
    return selectedLanesForRace;
  }
  /**
   * Method setSelectedLanesForRace.  Sets selectedLanesForRace of the DaqDigIOAccess 
   * as well as the lane count, if the Selected lanes is ZERO, then the default will be the
   * LANES count.  The purpose of this is for the actual call to the fireReadLaneSignals().<BR>
   * This method does not need to be called as the default value will be LANES (12).  
   * However to be more efficient.  It is better to call it with the exact lanes.
   * @see DaqDigIOAccess.DaqDigIOAccess#fireReadLaneSignals()
   * @param selectedLanesForRace - boolean[], The selectedLanesForRace to set.
   */
  public static void setSelectedLanesForRace( boolean[] selectedLanesForRace )
  {
    DaqDigIOAccess.selectedLanesForRace = selectedLanesForRace;
    int tempValue = 0;
    for( int i = 0; i < DaqDigIOAccess.selectedLanesForRace.length; i++ )
    {
      tempValue += ( DaqDigIOAccess.selectedLanesForRace[i] ) ? 1 : 0;
    }
    selectedLanesForRaceCount = (tempValue > 0) ? tempValue : LANES;
  }
  /**
   * Method sleep.  A Method to Sleep if neccessary
   * @param millis - int, the time in millis
   */
  public static void sleep(int millis)
  {
    try
    {
      Thread.sleep(millis);
    }
    catch(InterruptedException exc )
    {
    }
  }
  /**
   * Method allocateResources.  This method will fire a native request to
   * allocate resources for all subsequent calls to the DaqDigIOAccess object.<BR>
   * Native method, and all activity takes place in a native DLL.
   * @see DaqDigIOAccess.DaqDigIOAccess#freeAll()
   */
  private native void allocateResources();
  /**
   * Method destroy.  Initiates a request to the freeAll native mthod and frees
   * any additional resources local this class.
   * @see DaqDigIOAccess.DaqDigIOAccess#freeAll()
   */
  public void destroy()
  {
    if( !SIM_MODE )
    {  
      freeAll();
    }
    deviceName = null;
    errorMsg = null;
  }
  /**
   * Method dumpDaqDigIOCard.  Test Method to dump details to standard out.
   */
  private void dumpDaqDigIOCard()
  {
    System.out.println("DAQ CardCard Details:");
    System.out.println("Device Name:\t" + deviceName);
  }
  /**
   * Method fireGateOffTest.  Method to test the Firing of the Gate.
   * @see DaqDigIOAccess.DaqDigIOAccess#testFireGateOff()
   */
  public void fireGateOffTest()
  {
    resetValues();
    if( !SIM_MODE )
    {  
      testFireGateOff();
      gateArmed = false;
    }
  }
  /**
   * Method fireGateOnTest.  Method to test the disarming of the gate.
   * @see DaqDigIOAccess.DaqDigIOAccess#testFireGateOn()
   */
  public void fireGateOnTest()
  {
    resetValues();
    if ( !SIM_MODE )
    {  
      testFireGateOn();
      gateArmed = true;
    }
  }
  /**
   * Method fireReadLaneSignals.  This method is used to Run the DAQ Card.
   * It will testReadLanesSignals for the DLL, passing the timeout value
   * and the class variable selectedLanesForRaceCount.  This value will
   * vary depending on the number of lanes set.<BR>
   * @see DaqDigIOAccess.DaqDigIOAccess#setSelectedLanesForRace(boolean[])
   * @see DaqDigIOAccess.DaqDigIOAccess#testReadLaneSignals(int, int)
   */
  public void fireReadLaneSignals()
  {
    resetValues();
    if ( !SIM_MODE && this.deviceName != null)
    {  // the selectedLanesForRaceCount, indicates the number of lanes being used.
      testReadLaneSignals(this.timeout, DaqDigIOAccess.selectedLanesForRaceCount);
    }
  }
  /**
   * Method fireReadLaneSignalsTest.  This method is used to TEST the DAQ Card.
   * It will testReadLanesSignals for the DLL, passing the timeout value
   * and the class constant LANES.  This value will always be 12.<BR>
   * @see DaqDigIOAccess.DaqDigIOAccess#setSelectedLanesForRace(boolean[])
   * @see DaqDigIOAccess.DaqDigIOAccess#testReadLaneSignals(int, int)
   */
  public void fireReadLaneSignalsTest()
  {
    resetValues();
    if ( !SIM_MODE && this.deviceName != null)
    {  
      testReadLaneSignals(this.timeout, DaqDigIOAccess.LANES);
    }
  }
  /**
   * Method freeAll.  Frees any system reources allocated by the native
   * interface method allocateResources().
   * @see DaqDigIOAccess.DaqDigIOAccess#allocateResources()
   */
  private native void freeAll();

  /**
   * Method getDeviceName.  Returns deviceName of the DaqDigIOAccess
   * @return String: Returns the deviceName.
   * 
   * @uml.property name="deviceName"
   */
  public String getDeviceName() {
    if (SIM_MODE) {
      deviceName = "Bubba";
    }
    return deviceName;
  }

  /**
   * Method getErrorMsg.  Returns any error messages, recieved from the DLL
   * @return String - The error message recieved.
   * 
   * @uml.property name="errorMsg"
   */
  public String getErrorMsg() {
    return errorMsg;
  }

  /**
   * Method getLaneTimes.  Returns laneTimes of the DaqDigIOAccess
   * @see DaqDigIOAccess.DaqDigIOAccess#setTaggedLanes(int[])
   * @see DaqDigIOAccess.DaqDigIOAccess#getTaggedLanes()
   * @see DaqDigIOAccess.DaqDigIOAccess#setLaneTimes(double[])
   * @return double[]: Returns the laneTimes.
   * 
   * @uml.property name="laneTimes"
   */
  public double[] getLaneTimes() {
    if (SIM_MODE) {
      for (int i = 0; i < laneTimes.length; i++) {
        laneTimes[i] = timeout;
      }
    }
    return laneTimes;
  }

  /**
   * Method getTaggedLanes.  Returns taggedLanes of the DaqDigIOAccess
   * @see DaqDigIOAccess.DaqDigIOAccess#setTaggedLanes(int[])
   * @see DaqDigIOAccess.DaqDigIOAccess#getLaneTimes()
   * @see DaqDigIOAccess.DaqDigIOAccess#setLaneTimes(double[])
   * @return boolean[]: Returns the taggedLanes.
   * 
   * @uml.property name="taggedLanes"
   */
  public boolean[] getTaggedLanes() {
    return taggedLanes;
  }

  /**
   * Method getTimeout.  Returns timeout of the DaqDigIOAccess in seconds
   * @see DaqDigIOAccess.DaqDigIOAccess#setTimeout(int)
   * @return int: Returns the timeout in seconds.
   * 
   * @uml.property name="timeout"
   */
  public int getTimeout() {
    return timeout;
  }




  /**
   * Method hasError.  Returns a boolean if an error exists in the system, 
   * and a corresponding message is wating in the buffer.
   * @return boolean, an error exists, which also has a message corresponding.
   */
  public boolean hasError()
  {
    boolean rc = (this.errorMsg != null);
    return rc;
  }

  /**
   * Method isGateArmed.  Returns gateArmed of the DaqDigIOAccess
   * @return boolean: Returns the gateArmed. True if the gate is armed, otherwise false.
   * 
   * @uml.property name="gateArmed"
   */
  public boolean isGateArmed() {
    return gateArmed;
  }




  /**
   * Method resetValues.  Resets the values, of the lane times and tagged lanes 
   * to their intial state.
   */
  public void resetValues()
  {
    errorMsg = null;
    for(int i = 0; i < LANES; i++ )
    {
      this.laneTimes[i] = 100.0;
      this.taggedLanes[i] = false;
    }
  }

  /**
   * Method setDeviceDetails.  <B>CALLBACK</B> to set the device name of the DAQ DIG IO card.
   * If the Card name is being set then the Aquisition of the card was valid.  
   * In SIMULATION mode, the device name will be defaulted.<BR>
   * DO NOT Use THIS METHOD, It is a Call Back from the DLL
   * @param devName - String, the device name of the card.
   * 
   * @uml.property name="deviceName"
   */
  public void setDeviceDetails(String devName) {
    this.deviceName = devName;
  }

  /**
   * Method setErrorMsg.  <B>CALLBACK</B> to set the Error Msg recieved from the DAQ DIG IO card.<BR>
   * DO NOT Use THIS METHOD, It is a Call Back from the DLL
   * @param errMsg - String, the error meesage.
   * 
   * @uml.property name="errorMsg"
   */
  public void setErrorMsg(String errMsg) {
    this.errorMsg = errMsg;
  }




  /**
   * Method setLaneTimes.  <B>CALLBACK</B> to set the LaneTimes from the DAQ DIG IO card.
   * The data is being in returned in Micro-Second values.  
   * The values must be normalized to seconds.<BR>
   * DO NOT Use THIS METHOD, It is a Call Back from the DLL<BR>
   * @see DaqDigIOAccess.DaqDigIOAccess#getLaneTimes()
   * @see DaqDigIOAccess.DaqDigIOAccess#getTaggedLanes()
   * @see DaqDigIOAccess.DaqDigIOAccess#setTaggedLanes(int[])
   * @param times - double[], an array of all lanes and their associated times.
   */
  public void setLaneTimes(double[] times)
  {
    for(int i = 0; i < LANES; i++ )
    {
      this.laneTimes[i] = times[i] / 1000000;
    }
  }
  /**
   * Method setTaggedLanes.  <B>CALLBACK</B> to set the taggedLanes from the DAQ DIG IO card.<BR>
   * DO NOT Use THIS METHOD, It is a Call Back from the DLL<BR>
   * @see DaqDigIOAccess.DaqDigIOAccess#getTaggedLanes()
   * @see DaqDigIOAccess.DaqDigIOAccess#getLaneTimes()
   * @see DaqDigIOAccess.DaqDigIOAccess#setLaneTimes(double[])
   * @param taggedLanes - int[], The taggedLanes to set.
   */
  public void setTaggedLanes( int[] taggedLanes )
  {
    for(int i = 0; i < LANES; i++ )
    { // Convert the C CallBack int array of bools to Java Bools
      this.taggedLanes[i] = (taggedLanes[i] != 0 );
    }
  }

  /**
   * Method setTimeout.  Sets timeout of the DaqDigIOAccess before it will expire.  The timeout prevents
   * the Card from hanging and wainting for input and returns control back to the application.
   * @param timeout - int, The timeout to set.
   * 
   * @uml.property name="timeout"
   */
  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  /**
   * Method testDaqDigIOCard.  This method will try the DaqDigIOCard, if the
   * card is good and valid, the details of the card will be populated in this
   * class.<BR>
   * Native method, and all activity takes place in a native DLL.
   * @see DaqDigIOAccess.DaqDigIOAccess#getInstance()
   */
  private native void testDaqDigIOCard();
  /**
   * Method testFireGateOff.  Fires the Gate processing to be off.<BR>
   * Native method, and all activity takes place in a native DLL.
   * @see DaqDigIOAccess.DaqDigIOAccess#fireGateOffTest()
   */
  private native void testFireGateOff();
  /**
   * Method testFireGateOn.  Fires the Gate Processing to be on.<BR>
   * Native method, and all activity takes place in a native DLL.
   * @see DaqDigIOAccess.DaqDigIOAccess#fireGateOnTest()
   */
  private native void testFireGateOn();
  /**
   * Method testReadLaneSignals.  Provides a Mechanism to read the Gates to see.<BR>
   * Although the Name indicates it is a test sequence in reality it is used for Both 
   * Test sequences and the Actual execution.<BR>
   * Method requires two parameters, the timoutVal and the laneCount.  
   * If the <B>timeoutVal</B> has been reached before the <B>laneCount</B> threshhold, 
   * the application will regain control.  and all remaining lanes will be containg 
   * the <B>timeoutVal</B>.<BR>
   * If the Number of lanes read had been is equal to the the number lanes being used 
   * (<B>laneCount</B>), then the data control has finished its processing, and returns 
   * control to the application.<BR> 
   * NOTE: the number of lanes must be some number between 1 and the final constant variable
   * LANES inclusive.<BR>
   * Native method, and all activity takes place in a native DLL.
   * @param timeoutVal - int, the Time to wait ( in seconds ) before the DLL exipires and 
   * returns control to the application.
   * @param laneCount - int, the lane count, the number of lanes that will be examined 
   * before returning control back to the application.
   */
  private native void testReadLaneSignals(int timeoutVal, int laneCount);
  /**
   * Method main.  Main Test Driver
   * @param args
   */
  public static void main( String args[] )
  {
    System.out.println("Start DaqDigIOCard Test");
    DaqDigIOAccess daqAccess = DaqDigIOAccess.getInstance();
    
    daqAccess.dumpDaqDigIOCard();
    sleep(1000);
    System.out.println("\n\nGate Being Fire ON...");
    daqAccess.fireGateOnTest();
    sleep(1000);
    System.out.println("\n\nGate Being Fire OFF...");
    daqAccess.fireGateOffTest();
    sleep(1000);
    System.out.println("\n\nLanes Ready to be fired...");
    daqAccess.fireReadLaneSignalsTest();
    sleep(1000);
    System.out.println("\n\nDestroy Resources...");
    daqAccess.destroy();
    daqAccess = null;
  }
}
