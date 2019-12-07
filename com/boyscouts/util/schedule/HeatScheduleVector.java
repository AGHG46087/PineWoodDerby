/*
 * Package:		com.boyscouts.util.schedule
 * File Name:	HeatScheduleVector.java
 */
package com.boyscouts.util.schedule;

import java.util.Vector;

/**
 * author:      hgrein<BR>
 * date:        Jun 2, 2004<BR>
 * Package:     com.boyscouts.util.schedule<BR>
 * File Name:   HeatScheduleVector.java<BR>
 * Type Name:   HeatScheduleVector<BR>
 * Description: A Container that maintains racers for a single heat within a Race.
 * Any PersonRacerSchedule that is not valid will be rejected.  
 * You may add as many PersonRacerSchedule as you have lanes for a current heat within a larger race.
 */

public class HeatScheduleVector
{

  /**
   * Field <code>heatPersons</code> : Vector
   * 
   * @uml.property name="heatPersons"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="com.boyscouts.util.schedule.PersonRacerSchedule"
   */
  private Vector heatPersons = new Vector();

  /**
   * Method add.  Adds a PersonRacerSchedule to the container.  If the PersonRacerSchedule is empty or
   * the Racer is not present in the PersonRacerSchedule object it is rejected and not added.
   * @param prs - PersonRacerSchedule, object to be added.
   */
  public void add( PersonRacerSchedule prs )
  {
    if (prs != null)
    {
      if (!prs.getPlayer().isEmpty() && prs.getRacer() != null)
      {
        heatPersons.add(prs);
      }
    }
  }

  /**
   * Method getheatPersons.  Returns heatPersons of the HeatScheduleVector
   * @return Vector: Returns the heatPersons.
   * 
   * @uml.property name="heatPersons"
   */
  public Vector getHeatPersons()
  {
    return this.heatPersons;
  }

  /**
   * Method getPersonRacerSchedule.  Returns a Single PersonRacerSchedule for the current heat.
   * @param index - int, the index of the PersonRacerSchedule desired to be returned. 
   * if the index is out of bounds a empty RacerPersonSchedule is returned
   * @return PersonRacerSchedule
   */
  public PersonRacerSchedule getPersonRacerSchedule( int index )
  {
    PersonRacerSchedule prs = null;
    if (0 <= index && index < size())
    {
      prs = (PersonRacerSchedule) heatPersons.elementAt(index);
    }
    else
    {
      prs = new PersonRacerSchedule(null, null, -1, -1);
    }
    return prs;
  }
  /**
   * Method size.  Returns the size of this container,  this will indicate, how many heats are available
   * @return int.
   */
  public int size()
  {
    int size = heatPersons.size();
    return size;
  }
}