/*
 * @author:		Owner
 * Package:		com.boyscouts.database
 * File Name:		UpdateRacerPersonDBTask.java
 */
package com.boyscouts.database;

import com.boyscouts.database.exception.DBAccessException;
import com.boyscouts.domain.RacerContainer;
import com.boyscouts.domain.RacerPerson;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.database<BR>
 * File Name:   UpdateRacerPersonDBTask.java<BR>
 * Type Name:   UpdateRacerPersonDBTask<BR>
 * Description: A DBTask to Update the RacerPerson.  The class decends from DBTask, 
 * setting up the relationship as being a TimerTask specifically for the Databse Access.  
 * This particular task is designed for the sole purpose of providing a means to update
 * and existing record in the database in a A-Synchronous approach. It could be used for a 
 * Container of RacerPersons or just a single RacerPerson and the Task will kill itself 
 * after it has completed updating the entirety of all object.  If there is an error with
 * one object it will attempt to process the others.
 */

public class UpdateRacerPersonDBTask extends DBTask
{

  /**
   * Field <code>racerContainer</code> : RacerContainer
   * 
   * @uml.property name="racerContainer"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private RacerContainer racerContainer = null;

  /**
   * Field <code>racerPerson</code> : RacerPerson
   * 
   * @uml.property name="racerPerson"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private RacerPerson racerPerson = null;

  /** Field <code>DATA_COUNT</code> : int */
  private final int DATA_COUNT;
  /** Field <code>index</code> : int */
  private int index = -1;
  /** Field <code>iterations</code> : int */
  private int iterations = 0;
  /**
   * Constructor for UpdateRacerPersonDBTask. 
   * @param rp - RacerPerson, to insert only a RacerPerson Object
   */
  public UpdateRacerPersonDBTask( RacerPerson rp )
  {
    this.racerPerson = rp;
    this.racerContainer = null;
    this.DATA_COUNT = -1;
  }
  /**
   * Constructor for UpdateRacerPersonDBTask. 
   * @param racerContainer - RacerContainer, to update all data all at one shot
   */
  public UpdateRacerPersonDBTask( RacerContainer racerContainer )
  {
    this.racerContainer = racerContainer;
    this.DATA_COUNT = this.racerContainer.getSize();
    racerPerson = null;
  }

  /**
   * Overridden Method:  
   * @see java.lang.Runnable#run()
   */
  public void run()
  {
    this.iterations++;
    debug("Update RacerPerson", "Attempt RacerPerson Insertion, count=[" + this.iterations + "]");
    try
    {
      if (racerPerson != null)
      {
        updateRacerPerson(racerPerson);
      }
      else if (racerContainer != null)
      {
        index++;
        updateRacerPerson(racerContainer.elementAt(index));
      }
    }
    catch (DBAccessException exc)
    {
      log("Update Racer Data Task Failed", exc);
    }
    // Ensure We cancel the Object, The Second part of the if statement is not really neccessary,
    // It is a Safety Precaution, in case some one chanfges the Settings on the Data indexes
    if (index >= (this.DATA_COUNT - 1) || this.racerContainer == null)
    {
      this.cancel(); // Ensure we are done
      racerContainer = null;
      racerPerson = null;
      debug("Update RaceData", "Completed RacerPerson Update, count=[" + this.iterations + "]");
    }
  }
  /**
   * Method updateRacerPerson.  Insert the RacerPerson into the Database
   * @param rp - RacerPerson, the racerPerson Object to insert
   * @throws DBAccessException - on Failure
   */
  private void updateRacerPerson( RacerPerson rp ) throws DBAccessException
  {
    if (rp != null)
    {
      if (!rp.isEmptyRecord())
      {
        RaceARamaDB.update(rp);
      }
    }
  }
}
