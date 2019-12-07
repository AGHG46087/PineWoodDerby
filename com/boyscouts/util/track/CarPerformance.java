/*
 * @author:		Owner
 * date:		Dec 22, 2003
 * Package:		com.boyscouts.util.track
 * File Name:		CarPerformance.java
 */
package com.boyscouts.util.track;

import java.io.Serializable;

import com.hgutil.data.FixedDouble;

/**
 * author:      hgrein<BR>
 * date:        Jun 7, 2004<BR>
 * Package:     com.boyscouts.util.track<BR>
 * File Name:   CarPerformance.java<BR>
 * Type Name:   CarPerformance<BR>
 * Description: A ValueBean to Carying Performance Information About a Vehicle
 */

public class CarPerformance implements Serializable
{
  /** Field <code>serialVersionUID</code> : long */
  static final long serialVersionUID = -3407657006813544774L;

  /**
   * Field <code>errorMsg</code> : String
   * 
   * @uml.property name="errorMsg"
   */
  private String errorMsg = null;

  /**
   * Field <code>hasError</code> : boolean
   * 
   * @uml.property name="hasError"
   */
  private boolean hasError = false;

  /**
   * Field <code>vehicleCompletedRaceTime</code> : FixedDouble
   * 
   * @uml.property name="vehicleCompletedRaceTime"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private FixedDouble vehicleCompletedRaceTime = new FixedDouble();

  /**
   * Field <code>realAverageSpeed</code> : FixedDouble
   * 
   * @uml.property name="realAverageSpeed"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private FixedDouble realAverageSpeed = new FixedDouble();

  /**
   * Field <code>realEfficiency</code> : FixedDouble
   * 
   * @uml.property name="realEfficiency"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private FixedDouble realEfficiency = new FixedDouble();

  /**
   * Field <code>deltaEfficiency</code> : FixedDouble
   * 
   * @uml.property name="deltaEfficiency"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private FixedDouble deltaEfficiency = new FixedDouble();

  /**
   * Field <code>deltaTime</code> : FixedDouble
   * 
   * @uml.property name="deltaTime"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private FixedDouble deltaTime = new FixedDouble();

  /**
   * Field <code>deltaAverageSpeed</code> : FixedDouble
   * 
   * @uml.property name="deltaAverageSpeed"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private FixedDouble deltaAverageSpeed = new FixedDouble();

  /**
   * Constructor for CarPerformance. 
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(double)
   * @param vehicleCompletedRaceTime - double, the RaceTime.
   */
  public CarPerformance( double vehicleCompletedRaceTime )
  {
    setVehicleCompletedRaceTime(vehicleCompletedRaceTime);
  }
  /**
   * Constructor for CarPerformance. 
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(java.lang.Double)
   * @param vehicleCompletedRaceTime - Double, the RAce Time
   */
  public CarPerformance( Double vehicleCompletedRaceTime )
  {
    setVehicleCompletedRaceTime(vehicleCompletedRaceTime);
  }
  /**
   * Constructor for CarPerformance. 
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(com.hgutil.data.FixedDouble)
   * @param vehicleCompletedRaceTime - FixedDouble, the RAce Time
   */
  public CarPerformance( FixedDouble vehicleCompletedRaceTime )
  {
    setVehicleCompletedRaceTime(vehicleCompletedRaceTime);
  }

  /**
   * Method getDeltaAverageSpeed.  Returns deltaAverageSpeed of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setDeltaAverageSpeed(double)
   * @see com.boyscouts.util.track.CarPerformance#setDeltaAverageSpeed(com.hgutil.data.FixedDouble)
   * @return FixedDouble: Returns the deltaAverageSpeed.
   * 
   * @uml.property name="deltaAverageSpeed"
   */
  public FixedDouble getDeltaAverageSpeed()
  {
    return deltaAverageSpeed;
  }

  /**
   * Method getDeltaEfficiency.  Returns deltaEfficiency of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setDeltaEfficiency(double)
   * @see com.boyscouts.util.track.CarPerformance#setDeltaEfficiency(com.hgutil.data.FixedDouble)
   * @return FixedDouble: Returns the deltaEfficiency.
   * 
   * @uml.property name="deltaEfficiency"
   */
  public FixedDouble getDeltaEfficiency()
  {
    return deltaEfficiency;
  }

  /**
   * Method getDeltaTime.  Returns deltaTime of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setDeltaTime(double)
   * @see com.boyscouts.util.track.CarPerformance#setDeltaTime(com.hgutil.data.FixedDouble)
   * @return FixedDouble: Returns the deltaTime.
   * 
   * @uml.property name="deltaTime"
   */
  public FixedDouble getDeltaTime()
  {
    return deltaTime;
  }

  /**
   * Method getErrorMsg.  Returns errorMsg of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setErrorMsg(java.lang.String)
   * @return String: Returns the errorMsg.
   * 
   * @uml.property name="errorMsg"
   */
  public String getErrorMsg()
  {
    return errorMsg;
  }

  /**
   * Method getRealAverageSpeed.  Returns realAverageSpeed of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setRealAverageSpeed(double)
   * @see com.boyscouts.util.track.CarPerformance#setRealAverageSpeed(com.hgutil.data.FixedDouble)
   * @return FixedDouble: Returns the realAverageSpeed.
   * 
   * @uml.property name="realAverageSpeed"
   */
  public FixedDouble getRealAverageSpeed()
  {
    return realAverageSpeed;
  }

  /**
   * Method getRealEfficiency.  Returns realEfficiency of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setRealEfficiency(double)
   * @see com.boyscouts.util.track.CarPerformance#setRealEfficiency(com.hgutil.data.FixedDouble)
   * @return FixedDouble: Returns the realEfficiency.
   * 
   * @uml.property name="realEfficiency"
   */
  public FixedDouble getRealEfficiency()
  {
    return realEfficiency;
  }

  /**
   * Method getVehicleCompletedRaceTime.  Returns vehicleCompletedRaceTime of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(double)
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(java.lang.Double)
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(com.hgutil.data.FixedDouble)
   * @return FixedDouble: Returns the vehicleCompletedRaceTime.
   * 
   * @uml.property name="vehicleCompletedRaceTime"
   */
  public FixedDouble getVehicleCompletedRaceTime()
  {
    return vehicleCompletedRaceTime;
  }

  /**
   * Method hasError.  Returns hasError of the CarPerformance.
   * @see CarPerformance#setHasError(boolean)
   * @return boolean: Returns the hasError.
   */
  public boolean hasError()
  {
    return hasError;
  }
  /**
   * Method setDeltaAverageSpeed.  Sets deltaAverageSpeed of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setDeltaAverageSpeed(com.hgutil.data.FixedDouble)
   * @see com.boyscouts.util.track.CarPerformance#getDeltaAverageSpeed()
   * @param deltaAverageSpeed : double, The deltaAverageSpeed to set.
   */
  public void setDeltaAverageSpeed( double deltaAverageSpeed )
  {
    this.deltaAverageSpeed.setValue(deltaAverageSpeed);
  }
  /**
   * Method setDeltaAverageSpeed.  Sets deltaAverageSpeed of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setDeltaAverageSpeed(double)
   * @see com.boyscouts.util.track.CarPerformance#getDeltaAverageSpeed()
   * @param deltaAverageSpeed : FixedDouble, The deltaAverageSpeed to set.
   */
  public void setDeltaAverageSpeed( FixedDouble deltaAverageSpeed )
  {
    this.deltaAverageSpeed.setValue(deltaAverageSpeed);
  }
  /**
   * Method setDeltaEfficiency.  Sets deltaEfficiency of the CarPerformance
   * @see com.boyscouts.util.track.CarPerformance#setDeltaEfficiency(com.hgutil.data.FixedDouble)
   * @see com.boyscouts.util.track.CarPerformance#getDeltaEfficiency()
   * @param deltaEfficiency : double, The deltaEfficiency to set.
   */
  public void setDeltaEfficiency( double deltaEfficiency )
  {
    this.deltaEfficiency.setValue(deltaEfficiency);
  }
  /**
   * Method setDeltaEfficiency.  Sets deltaEfficiency of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setDeltaEfficiency(double)
   * @see com.boyscouts.util.track.CarPerformance#getDeltaEfficiency()
   * @param deltaEfficiency : FixedDouble, The deltaEfficiency to set.
   */
  public void setDeltaEfficiency( FixedDouble deltaEfficiency )
  {
    this.deltaEfficiency.setValue(deltaEfficiency);
  }
  /**
   * Method setDeltaTime.  Sets deltaTime of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setDeltaTime(com.hgutil.data.FixedDouble)
   * @see com.boyscouts.util.track.CarPerformance#getDeltaTime()
   * @param deltaTime : double, The deltaTime to set.
   */
  public void setDeltaTime( double deltaTime )
  {
    this.deltaTime.setValue(deltaTime);
  }
  /**
   * Method setDeltaTime.  Sets deltaTime of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setDeltaTime(double)
   * @see com.boyscouts.util.track.CarPerformance#getDeltaTime()
   * @param deltaTime : FixedDouble, The deltaTime to set.
   */
  public void setDeltaTime( FixedDouble deltaTime )
  {
    this.deltaTime.setValue(deltaTime);
  }

  /**
   * Method setErrorMsg.  Sets errorMsg of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setHasError(boolean)
   * @see com.boyscouts.util.track.CarPerformance#hasError()
   * @see com.boyscouts.util.track.CarPerformance#getErrorMsg()
   * @param errorMsg : String, The errorMsg to set.
   * 
   * @uml.property name="errorMsg"
   */
  public void setErrorMsg( String errorMsg )
  {
    this.errorMsg = errorMsg;
    setHasError((this.errorMsg != null) && (this.errorMsg.length() > 0));
  }

  /**
   * Method setHasError.  Sets hasError of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setErrorMsg(java.lang.String)
   * @see com.boyscouts.util.track.CarPerformance#hasError()
   * @see com.boyscouts.util.track.CarPerformance#getErrorMsg()
   * @param hasError : boolean, The hasError to set.
   * 
   * @uml.property name="hasError"
   */
  private void setHasError( boolean hasError )
  {
    this.hasError = hasError;
  }

  /**
   * Method setRealAverageSpeed.  Sets realAverageSpeed of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setRealAverageSpeed(com.hgutil.data.FixedDouble)
   * @see com.boyscouts.util.track.CarPerformance#getRealAverageSpeed()
   * @param realAverageSpeed : double, The realAverageSpeed to set.
   */
  public void setRealAverageSpeed( double realAverageSpeed )
  {
    this.realAverageSpeed.setValue(realAverageSpeed);
  }
  /**
   * Method setRealAverageSpeed.  Sets realAverageSpeed of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setRealAverageSpeed(double)
   * @see com.boyscouts.util.track.CarPerformance#getRealAverageSpeed()
   * @param realAverageSpeed : FixedDouble, The realAverageSpeed to set.
   */
  public void setRealAverageSpeed( FixedDouble realAverageSpeed )
  {
    this.realAverageSpeed.setValue(realAverageSpeed);
  }
  /**
   * Method setRealEfficiency.  Sets realEfficiency of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setRealEfficiency(com.hgutil.data.FixedDouble)
   * @see com.boyscouts.util.track.CarPerformance#getRealEfficiency()
   * @param realEfficiency : double, The realEfficiency to set.
   */
  public void setRealEfficiency( double realEfficiency )
  {
    this.realEfficiency.setValue(realEfficiency);
  }
  /**
   * Method setRealEfficiency.  Sets realEfficiency of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setRealEfficiency(double)
   * @see com.boyscouts.util.track.CarPerformance#getRealEfficiency()
   * @param realEfficiency : FixedDouble, The realEfficiency to set.
   */
  public void setRealEfficiency( FixedDouble realEfficiency )
  {
    this.realEfficiency.setValue(realEfficiency);
  }
  /**
   * Method setVehicleCompletedRaceTime.  Sets vehicleCompletedRaceTime of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(com.hgutil.data.FixedDouble)
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(java.lang.Double)
   * @see com.boyscouts.util.track.CarPerformance#getVehicleCompletedRaceTime()
   * @param realTotalTime : double, The vehicleCompletedRaceTime to set.
   */
  public void setVehicleCompletedRaceTime( double realTotalTime )
  {
    this.vehicleCompletedRaceTime.setValue(realTotalTime);
  }
  /**
   * Method setVehicleCompletedRaceTime.  Sets vehicleCompletedRaceTime of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(com.hgutil.data.FixedDouble)
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(double)
   * @see com.boyscouts.util.track.CarPerformance#getVehicleCompletedRaceTime()
   * @param realTotalTime : Double, The vehicleCompletedRaceTime to set.
   */
  public void setVehicleCompletedRaceTime( Double realTotalTime )
  {
    this.vehicleCompletedRaceTime.setValue(realTotalTime);
  }
  /**
   * Method setVehicleCompletedRaceTime.  Sets vehicleCompletedRaceTime of the CarPerformance.
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(double)
   * @see com.boyscouts.util.track.CarPerformance#setVehicleCompletedRaceTime(java.lang.Double)
   * @see com.boyscouts.util.track.CarPerformance#getVehicleCompletedRaceTime()
   * @param realTotalTime : FixedDouble, The vehicleCompletedRaceTime to set.
   */
  public void setVehicleCompletedRaceTime( FixedDouble realTotalTime )
  {
    this.vehicleCompletedRaceTime.setValue(realTotalTime);
  }

  /**
   * Overridden Method:  Returns a String Representation of the Car Performance
   * @see java.lang.Object#toString()
   * @return String
   */
  public String toString()
  {
    String sb = "";
    sb += "vehicleCompletedRaceTime=\"" + getVehicleCompletedRaceTime() + "\" ";
    sb += "realAverageSpeed=\"" + getRealAverageSpeed() + "\" ";
    sb += "realEfficiency=\"" + getRealEfficiency() + "\" ";
    sb += "deltaEfficiency=\"" + getDeltaEfficiency() + "\" ";
    sb += "deltaTime=\"" + getDeltaTime() + "\" ";
    sb += "deltaAverageSpeed=\"" + getDeltaAverageSpeed() + "\" ";
    return sb;
  }
}
