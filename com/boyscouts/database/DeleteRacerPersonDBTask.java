/*
 * @author:		Owner
 * Package:		com.boyscouts.database
 * File Name:		DeleteRacerPersonDBTask.java
 */
package com.boyscouts.database;

import com.boyscouts.database.exception.DBAccessException;
import com.boyscouts.domain.RacerPerson;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.database<BR>
 * File Name:   DeleteRacerPersonDBTask.java<BR>
 * Type Name:   DeleteRacerPersonDBTask<BR>
 * Description: A DBTask to Delete the RacerPerson.  The class decends from DBTask, 
 * setting up the relationship as being a TimerTask specifically for the Databse Access.  
 * This particular task is designed for the sole purpose of providing a means to delete
 * an existing record in the database in a A-Synchronous approach. It could be used for a 
 * just a single RacerPerson and the Task will kill itself after it has completed updating 
 * the entirety of all object.  If there is an error with one object it will attempt to 
 * process the others.
 */

public class DeleteRacerPersonDBTask extends DBTask
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
  public DeleteRacerPersonDBTask( RacerPerson rp )
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
      RaceARamaDB.delete(rp);
    }
    catch (DBAccessException exc)
    {
      log("Delete Racer Person Task Failed", exc);
    }
  }
}
