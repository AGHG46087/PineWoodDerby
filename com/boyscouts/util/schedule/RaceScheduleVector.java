/*
 * Package:		com.boyscouts.util.schedule
 * File Name:	RaceScheduleVector.java
 */
package com.boyscouts.util.schedule;

import java.util.Vector;

/**
 * author:      hgrein<BR>
 * date:        Jun 2, 2004<BR>
 * Package:     com.boyscouts.util.schedule<BR>
 * File Name:   RaceScheduleVector.java<BR>
 * Type Name:   RaceScheduleVector<BR>
 * Description: A Container that maintains a list of heats for a Race.
 * Any HeatScheduleVector that is not valid will be rejected.  
 * You may add as many HeatScheduleVector as you have heats for a current race.
 */

public class RaceScheduleVector
{

  /**
   * Field <code>racerScheduleVector</code> : Vector
   * 
   * @uml.property name="racerScheduleVector"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="com.boyscouts.util.schedule.HeatScheduleVector"
   */
  private Vector racerScheduleVector = new Vector();

  /**
   * Method add.  Adds a HeatScheduleVector object to the entire race schedule.  If the HeatScheduleVector is
   * empty or null, it will be rejected and not added.
   * @param hsv - HeatScheduleVector, the object to be added.
   */
  public void add( HeatScheduleVector hsv )
  {
    if (hsv != null && hsv.size() > 0)
    {
      racerScheduleVector.add(hsv);
    }
  }
  /**
   * Method clear.  Clears all the heats from the current Container.
   */
  public void clear()
  {
    racerScheduleVector.clear();
  }

  /**
   * Method getDataAsVectorOfVector.  This method is a convience, 
   * it reutrns the raw data as a Vector of Vector of PersonRacerSchedule objects.
   * where the Top Level vector is the Race representation caontaining a list of Heats.
   * Each heat is a vector containing PersonRacerSchedule (identifying the Racer and the lane )
   * This will not be a Vector of HeatScheduleVector.
   * @return Vector
   */
  public Vector getDataAsVectorOfVector()
  {
    Vector vov = new Vector();
    for (int heat = 0; heat < this.size(); heat++)
    {
      HeatScheduleVector hsv = this.getHeatScheduleVector(heat);
      if (hsv != null)
      {
        Vector currentHeat = hsv.getHeatPersons();
        vov.add(currentHeat);
      }
    }
    return vov;
  }
  /**
   * Method getHeatScheduleVector.  Returns a Single HeatScheduleVector requested by the index
   * @param heat - int, the heat that is deisred to be retrieved.  If the heat index is out of
   * bounds a empty HeatScheduleVector will be returned.
   * @return HeatScheduleVector
   */
  public HeatScheduleVector getHeatScheduleVector( int heat )
  {
    HeatScheduleVector heatVector = null;

    if (0 <= heat && heat < this.size())
    {
      heatVector = (HeatScheduleVector) racerScheduleVector.elementAt(heat);
    }
    else
    {
      heatVector = new HeatScheduleVector();
    }
    return heatVector;
  }
  /**
   * Method size.  Returns the Size of the RaceScheduleVector.  The size indicates how
   * many heats are in the current race.
   * @return int
   */
  public int size()
  {
    int size = racerScheduleVector.size();
    return size;
  }

}
