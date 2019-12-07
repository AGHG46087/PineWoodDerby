/*
 * @author:			Owner
 * date:				Dec 9, 2003
 * Package:			com.boyscouts.database
 * File Name:		RaceARamaDB.java
 */
package com.boyscouts.database;

import java.sql.Date;
import java.util.GregorianCalendar;

import com.boyscouts.database.exception.DBAccessException;
import com.boyscouts.database.exception.DBAccessInsertException;
import com.boyscouts.database.exception.DBAccessUpdateException;
import com.boyscouts.domain.Log;
import com.boyscouts.domain.RaceData;
import com.boyscouts.domain.RaceDataContainer;
import com.boyscouts.domain.RacerContainer;
import com.boyscouts.domain.RacerPerson;
import com.boyscouts.domain.RacerPersonFieldName;

/**
 * author:      hgrein<BR>
 * date:        Jun 3, 2004<BR>
 * Package:     com.boyscouts.database<BR>
 * File Name:   RaceARamaDB.java<BR>
 * Type Name:   RaceARamaDB<BR>
 * Description: Facade to process data on the Database.  This class is provided as a means for 
 * processing data and provides methods that abstract the Database access to a simple
 * Method call and passing the objects of interests to perform the task requested.
 */
final public class RaceARamaDB
{
  /** Field <code>dbAccess</code> : DBAccess */
  private static DBAccess dbAccess = DBAccess.getInstance();

  /**
   * Method delete.  This method will delete a existing Record of Race Data from the Database
   * @param raceData - RaceData, The Data to Delte from the Database
   * @throws DBAccessException - On Failure to Open or Delete
   */
  public static void delete( RaceData raceData ) throws DBAccessException
  {
    int uniqueID = raceData.getEntryID().intValue();

    String deleteQuery = "DELETE FROM RaceTable WHERE Entry = " + uniqueID;
    try
    {
      synchronized (dbAccess)
      {
        Log.debug("Delete RaceData: " + deleteQuery);
        dbAccess.insertOrUpdateDB(deleteQuery);
      }
    }
    catch (DBAccessException exc)
    {
      String msg = "Failed to DELETE Race Data in DB: " + exc.getMessage();
      throw new DBAccessUpdateException(msg, exc);
    }
  }
  /**
   * Method delete.  This method will delete a existing Record of Race Person from the Database
   * Since the User is being deleted... The race data associated with the user will also be deleted.
   * @param racerPerson - RacerPerson, the Record to Delete from the Database.
   * @throws DBAccessException - On Failure to Open or Delete.
   */
  public static void delete( RacerPerson racerPerson ) throws DBAccessException
  {
    int uniqueID = racerPerson.getIdAsInt();

    String deleteQuery = "DELETE FROM RacersTable WHERE ID = " + uniqueID;
    String deleteRaceInfo = "DELETE FROM RaceTable WHERE ID = " + uniqueID;
    try
    {
      synchronized (dbAccess)
      {
        int count = 0;
        Log.debug("Delete RacerPerson: " + deleteQuery);
        Log.debug("Delete RacerData: " + deleteRaceInfo);
        count = dbAccess.insertOrUpdateDB(deleteQuery);
        Log.debug("Delete record(s) from RacersTable count = " + count);
        count = dbAccess.insertOrUpdateDB(deleteRaceInfo);
        Log.debug("Delete record(s) from RaceTable count = " + count);
      }
    }
    catch (DBAccessException exc)
    {
      String msg = "Failed to DELETE Race Person in DB: " + exc.getMessage();
      throw new DBAccessUpdateException(msg, exc);
    }
  }
  /**
   * Method deleteDetailsOnly.  This method will delete aall existing RaceDetails from the Database
   * Since the User Data is being deleted... 
   * @param racerPerson - RacerPerson, the Record to Delete from the Database.
   * @throws DBAccessException - On Failure to Open or Delete.
   */
  public static void deleteRaceDetailsOnly( RacerPerson racerPerson ) throws DBAccessException
  {
    int uniqueID = racerPerson.getIdAsInt();

    String deleteRaceInfo = "DELETE FROM RaceTable WHERE ID = " + uniqueID;
    try
    {
      synchronized (dbAccess)
      {
        int count = 0;
        Log.debug("DeleteRaceDetailsOnly RacerData: " + deleteRaceInfo);
        count = dbAccess.insertOrUpdateDB(deleteRaceInfo);
        Log.debug("Delete record(s) from RaceTable count = " + count);
      }
    }
    catch (DBAccessException exc)
    {
      String msg = "Failed to DELETE Race Details in DB: " + exc.getMessage();
      throw new DBAccessUpdateException(msg, exc);
    }
  }
  /**
   * Method generateUniqueID.  Methods generates a unique ID by using the current 
   * system time in milliseconds and casting to an int.  The cast is known to lose the precision
   * and since the MS Access database - lon integer is the same size as int.  It makes for a nice fit.
   * 
   * @return int. the generic Unique ID.
   */
  private static int generateUniqueID()
  {
    int uniqueID = (int) System.currentTimeMillis(); // Convert to an int for a unique ID

    return uniqueID;
  }
  /**
   * Method getAllRacerData.  Generic method to retreive ALL racers Date registered in the database.
   * @return RaceDataContainer
   */
  public static RaceDataContainer getAllRacerData()
  {
    String queryAll = "SELECT * FROM RaceTable ORDER BY ID, Entry;";
    RaceDataContainer container = null;

    try
    {
      synchronized (dbAccess)
      {
        Log.debug("QUERY RaceData: " + queryAll);
        DBResults results = dbAccess.queryDB(queryAll);
        container = new RaceDataContainer(results.getColumnHeaders(), results.getRowData());
        results.destroy();
      }
    }
    catch (DBAccessException exc)
    {
      String msg = "Failed to QUERY Race Person in DB, Query=[" + queryAll + "]: " + exc.getMessage();
      Log.logError(msg);
    }

    return container;
  }
  /**
   * Method getAllRacers.  Generic method to retrieve ALL Registered Racers in the Database
   * for a given Date and Race Name
   * @return RacerContainer
   */
  public static RacerContainer getAllRacers( GregorianCalendar date, String raceName )
  {
    String queryAll = "SELECT * FROM RacersTable ORDER BY ID;";
    Date targetDate = null;
    String targetRaceName = null;
    RacerContainer container = null;
    // Convert some data stuff.
    if (date != null)
    {
      targetDate = getSqlDate(date);
    }
    if (raceName != null && raceName.trim().length() > 0)
    {
      targetRaceName = raceName.trim();
    }
    // Determine the Query we are going to perform
    if (targetDate != null && targetRaceName != null)
    { // Date and RaceName
      queryAll = "SELECT * FROM RacersTable WHERE RegisterDate >= #" + targetDate + "# AND RaceName = '" + raceName + "' ORDER BY ID;";
    }
    else if (targetDate != null)
    { // Date
      queryAll = "SELECT * FROM RacersTable WHERE RegisterDate >= #" + targetDate + "# ORDER BY ID;";
    }
    else if (targetRaceName != null)
    { // RaceName
      queryAll = "SELECT * FROM RacersTable WHERE RaceName = '" + raceName + "' ORDER BY ID;";
    }
    else
    { // Everything
      queryAll = "SELECT * FROM RacersTable ORDER BY ID;";
    }

    try
    {
      synchronized (dbAccess)
      {
        Log.debug("QUERY RacerPerson: " + queryAll);
        DBResults results = dbAccess.queryDB(queryAll);
        container = new RacerContainer(results.getRowData());
        results.destroy();
      }
    }
    catch (DBAccessException exc)
    {
    }

    return container;
  }
  /**
   * Method getSqlDate.  Utility method to obtain the SQL date given a particular Gregorian Calandar.
   * @param calendarDate - GregorianCalendar, Converts the Gregorian Calendar date to a java.sql.date
   * @return java.sql.Date
   */
  private static java.sql.Date getSqlDate( java.util.GregorianCalendar calendarDate )
  {
    //    int year = calendarDate.get(Calendar.YEAR) - 1900;
    //    int month = calendarDate.get(Calendar.MONTH);
    //    int day = calendarDate.get(Calendar.DAY_OF_MONTH);
    //    java.sql.Date date = new java.sql.Date(year, month, day);
    java.sql.Date date = new java.sql.Date(calendarDate.getTimeInMillis());
    return date;
  }
  /**
   * Method getSQLDate.   Returns the Java Sql Date For Todays Date
   * @return java.sql.Date
   */
  private static java.sql.Date getSQLDate()
  {
    GregorianCalendar calendar = new GregorianCalendar();
    //    int year = calendar.get(Calendar.YEAR) - 1900;
    //    int month = calendar.get(Calendar.MONTH);
    //    int day = calendar.get(Calendar.DAY_OF_MONTH);
    //    java.sql.Date date = new java.sql.Date(year, month, day);
    java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());
    return date;
  }
  /**
   * Method insert.  This method will insert a new RaceData Record of Race Data into the Database
   * @param raceData - RaceData, The Record to insert
   * @throws DBAccessException - On Failure to Open or Insert
   */
  public static void insert( RaceData raceData ) throws DBAccessException
  {
    java.sql.Date date = getSQLDate();
    int uniqueID = generateUniqueID();

    String insertQuery = "INSERT INTO RaceTable VALUES( " + uniqueID + ", " + // Autonumber/Entry Field unique ID.
                         raceData.getId() + ", " + // ID
                         "'" + raceData.getVehicleNumber() + "', " + // VehicleNumber
                         raceData.getRound() + ", " + // Round
                         raceData.getHeat() + ", " + // Heat
                         raceData.getLane() + ", " + // Lane
                         raceData.getTime() + ", " + // Time
                         raceData.getPlacementIntValue() + ", #" + // the Place for the winner circle
                         date + "#)"; // The Time Stamp of the Update.  Dates are wrapped in #date# format

    try
    {
      synchronized (dbAccess)
      {
        Log.debug("Insert RaceData: " + insertQuery);
        dbAccess.insertOrUpdateDB(insertQuery);
        raceData.setEntryID(new Integer(uniqueID));
      }
    }
    catch (DBAccessException exc)
    {
      String msg = "Failed to INSERT Race Data in DB: " + exc.getMessage();
      throw new DBAccessInsertException(msg, exc);
    }
  }
  /**
   * Method insert.  This method will insert a new Record of Race Person into the Database
   * @param racerPerson - RacerPerson, the Data to insert into the Database
   * @throws DBAccessException - On Faioure to Open or Insert
   */
  public static void insert( RacerPerson racerPerson ) throws DBAccessException
  {
    java.sql.Date date = getSQLDate();
    int uniqueID = generateUniqueID();

    String insertQuery = "INSERT INTO RacersTable VALUES( " + uniqueID + ", " + // Autonumber/Entry Field unique ID.
                         "'" + racerPerson.getDistrict() + "', " + // District - String
                         "'" + racerPerson.getPack() + "', " + // Pack - String
                         "'" + racerPerson.getDen() + "', " + // Den - String
                         "'" + racerPerson.getLastName() + "', " + // LastName - String
                         "'" + racerPerson.getFirstName() + "', " + // FirstName - String
                         "'" + racerPerson.getVehicleNumber() + "', " + // VehicleNumber - int
                         "'" + racerPerson.getAddress() + "', " + // Address - String
                         "'" + racerPerson.getCity() + "', " + // City - String 
                         "'" + racerPerson.getState() + "', " + // State - String
                         "'" + racerPerson.getPostal() + "', " + // Postal - String
                         "'" + racerPerson.getPhone() + "', " + // Phone - String
                         racerPerson.getMinTime() + ", " + // MinTime - double
                         racerPerson.getMaxTime() + ", " + // MaxTime - double
                         racerPerson.getAvgTime() + ", " + // AvgTime - double
                         racerPerson.getStdDev() + ", " + // StdDev - double
                         "#" + date + "#, " + // RegisterDate - date. The Time Stamp of the Update.  Dates are wrapped in #date# format
                         "'" + racerPerson.getRaceName() + "')"; // Race Name

    try
    {
      synchronized (dbAccess)
      {
        Log.debug("Insert RacerPerson: " + insertQuery);
        dbAccess.insertOrUpdateDB(insertQuery);
        racerPerson.setId(new Integer(uniqueID));
      }
    }
    catch (DBAccessException exc)
    {
      String msg = "Failed to INSERT Race Person in DB: " + exc.getMessage();
      throw new DBAccessInsertException(msg, exc);
    }
  }
  /**
   * Method searchForRacers.  Method abstraction to search for all racers that sounds like the 
   * String value passed as a formal parameter, and the Field Name desired to be selected.
   * @param value - String, The Value of the Field to be searched on.
   * @param field - RacerPersonFieldName, the Column Field Name
   * @return DBResults - The results of the Request.
   * @throws DBAccessException - In the Case of an Exception during processing
   */
  public static DBResults searchForRacers( String value, RacerPersonFieldName field ) throws DBAccessException
  {
    DBResults results = null;
    String queryAll = "SELECT * FROM RacersTable WHERE " + field + " LIKE '" + value + "%' ORDER BY " + field + ";";
    try
    {
      synchronized (dbAccess)
      {
        Log.debug("Search for Racers: " + queryAll);
        results = dbAccess.queryDB(queryAll);
      }
    }
    catch (DBAccessException exc)
    {
      throw exc;
    }

    return results;
  }
  /**
   * Method update.  This method will update a existing Record of Race Data into the Database
   * @param raceData - RaceData, The Data to update in the Database
   * @throws DBAccessException - On Failure to open or Update
   */
  public static void update( RaceData raceData ) throws DBAccessException
  {
    java.sql.Date date = getSQLDate();
    int uniqueID = raceData.getEntryID().intValue();

    String updateQuery = "UPDATE RaceTable " // Which Table
                         + "SET ID=" + raceData.getId() + ", " // The Id of the User
                         + "VehicleNumber='" + raceData.getVehicleNumber() + "', " // The Vehicle Number
                         + "Round=" + raceData.getRound() + ", " // The Round in the Race
                         + "Heat=" + raceData.getHeat() + ", " // The heat in the race
                         + "Lane=" + raceData.getLane() + ", " // The Lane for this heat
                         + "RaceTime=" + raceData.getTime() + ", " // The time to complete
                         + "Place=" + raceData.getPlacementIntValue() + ", " // The Place positioned
                         + "EntryDate=#" + date + "# " // The date and time
                         + "WHERE Entry = " + uniqueID + ";"; // The Unique ID
    try
    {
      synchronized (dbAccess)
      {
        Log.debug("Update RaceData: " + updateQuery);
        dbAccess.insertOrUpdateDB(updateQuery);
      }
    }
    catch (DBAccessException exc)
    {
      String msg = "Failed to UPDATE Race Data in DB: " + exc.getMessage();
      throw new DBAccessUpdateException(msg, exc);
    }
  }
  /**
   * Method update.  This method will update a existing Record of Race Data into the Database
   * @param racerPerson - RacerPerson, The Data to update in the Datase
   * @throws DBAccessException - On Failure to Open or Update
   */
  public static void update( RacerPerson racerPerson ) throws DBAccessException
  {
    java.sql.Date date = getSQLDate();
    int uniqueID = racerPerson.getIdAsInt();
    String updateQuery = "UPDATE RacersTable " + // Which Table to update
                         "SET District='" + racerPerson.getDistrict() + "', " // District
                         + "Pack='" + racerPerson.getPack() + "', " // Pack
                         + "Den='" + racerPerson.getDen() + "', " // Den
                         + "LastName='" + racerPerson.getLastName() + "', " // Last Name
                         + "FirstName='" + racerPerson.getFirstName() + "', " // First Name
                         + "VehicleNumber='" + racerPerson.getVehicleNumber() + "', " // Vehicle Number
                         + "Address='" + racerPerson.getAddress() + "', " // Address
                         + "City='" + racerPerson.getCity() + "', " // City
                         + "State='" + racerPerson.getState() + "', " // State
                         + "Postal='" + racerPerson.getPostal() + "', " // Postal Code
                         + "Phone='" + racerPerson.getPhone() + "', " // Phone Number
                         + "MinTime=" + racerPerson.getMinTime() + ", " // Minimum time
                         + "MaxTime=" + racerPerson.getMaxTime() + ", " // Maximum Time
                         + "AvgTime=" + racerPerson.getAvgTime() + ", " // Average Time
                         + "StdDev=" + racerPerson.getStdDev() + ", " // Standard Deviation
                         + "RegisterDate=#" + date + "#, " // Register Date
                         + "RaceName='" + racerPerson.getRaceName() + "' " // Race Name
                         + "WHERE ID = " + uniqueID + ";"; // Unique ID

    try
    {
      synchronized (dbAccess)
      {
        Log.debug("Update RacerPerson: " + updateQuery);
        dbAccess.insertOrUpdateDB(updateQuery);
      }
    }
    catch (DBAccessException exc)
    {
      String msg = "Failed to UPDATE Race Person in DB: " + exc.getMessage();
      throw new DBAccessUpdateException(msg, exc);
    }
  }

  /**
   * Method main.  Test the Engine.
   * @param args
   */
  public static void main( String[] args )
  {
    for (int argc = 0; argc < args.length; argc++)
    {
      if (args[argc].equals("-s"))
      {
        try
        {
          DBResults results = searchForRacers("Greiner", RacerPersonFieldName.LAST_NAME);
          results.dump();
        }
        catch (DBAccessException exc)
        {
          Log.logError("FAILED SEARCH: " + exc.getMessage());
        }
      }
      if (args[argc].equals("-d"))
      {
        RaceDataContainer raceDataContain = getAllRacerData();
        RaceData rd = raceDataContain.elementAt(raceDataContain.getSize() - 1);
        try
        {
          rd.setPlacement(new Integer(10));
          insert(rd);
        }
        catch (DBAccessException exc)
        {
          Log.logError(exc);
        }
        try
        {
          rd.setRound(new Integer(10));
          update(rd);
        }
        catch (DBAccessException exc)
        {
          Log.logError(exc);
        }
        raceDataContain = getAllRacerData();
        raceDataContain.dumpContainer();
        try
        {
          if (raceDataContain.getSize() > 1)
          {
            delete(rd);
          }
        }
        catch (DBAccessException exc)
        {
          Log.logError(exc);
        }
        raceDataContain = getAllRacerData();
        raceDataContain.dumpContainer();
      }
      if (args[argc].equals("-r"))
      {
        RacerContainer racerContain = getAllRacers(null, null);
        racerContain.dumpContainer();
        RacerPerson rp = racerContain.elementAt(racerContain.getSize() - 1);
        try
        {
          for (int i = 0; i < 30; i++)
          {
            String temp = "Greiner";
            rp.setLastName(temp + i);
            rp.setVehicleNumber("" + i);
            if (racerContain.getSize() > 1)
            {
              // delete(rp);
            }
            //            insert(rp);
          }
          rp.setLastName("Duck");
          update(rp);
        }
        catch (DBAccessException exc)
        {
          Log.logError(exc);
        }
        GregorianCalendar myCalendar = new GregorianCalendar(2003, 11, 15);
        racerContain = getAllRacers(myCalendar, "");
        racerContain.dumpContainer();

      }
    }
  }
}