/*
 * @author:		Owner
 * Package:		com.boyscouts.database
 * File Name:		InsertRacerPersonDBTask.java
 */
package com.boyscouts.database;

import com.boyscouts.database.exception.DBAccessException;
import com.boyscouts.domain.RacerPerson;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.database<BR>
 * File Name:   InsertRacerPersonDBTask.java<BR>
 * Type Name:   InsertRacerPersonDBTask<BR>
 * Description: A DBTask to Insert the RacerPerson.  The class decends from DBTask, 
 * setting up the relationship as being a TimerTask specifically for the Databse Access.  
 * This particular task is designed for the sole purpose of providing a means to insert
 * a non-existing record in the database in a A-Synchronous approach. It could be used for a 
 * just a single RacerPerson and the Task will kill itself after it has completed inserting t
 * he entirety of all object.  If there is an error with one object it will attempt to 
 * process the others.
 */

public class InsertRacerPersonDBTask extends DBTask
{

  /**
   * Field <code>rp</code> : RacerPerson
   * 
   * @uml.property name="rp"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RacerPerson rp = null;

  /**
   * Constructor for DeleteRacerPersonDBTask. 
   */
  public InsertRacerPersonDBTask( RacerPerson rp )
  {
    this.rp = rp;
  }
  /**
   * Overridden Method:  
   * @see java.lang.Runnable#run()
   */
  public void run()
  {
    this.cancel(); // Ensure We are Done
    try
    {
      RaceARamaDB.insert(rp);
    }
    catch (DBAccessException exc)
    {
      log("Insert Racer Person Task Failed", exc);
    }
  }
}
