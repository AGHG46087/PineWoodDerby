/*
 * Package:		com.boyscouts.database.util
 * File Name:	DBLoad.java
 */
package com.boyscouts.database.util;

import com.boyscouts.database.RaceARamaDB;
import com.boyscouts.database.exception.DBAccessException;
import com.boyscouts.domain.RacerPerson;
import com.hgutil.StandardInput;

/**
 * author:      Hans-Jurgen Greiner<BR>
 * Package:     com.boyscouts.database.util<BR>
 * File Name:   DBLoad.java<BR>
 * Type Name:   DBLoad<BR>
 * Description: DB Load to assist Populating the Database.  
 * The data must be a comma seperated vector (.csv) which is also readable from excel.
 * 
 */
public class DBLoad
{
  private String fileName = "Scout.csv";
  private static int number = 1;

  /**
   * Constructor for DBLoad. 
   * 
   */
  public DBLoad()
  {
    super();
  }
  /**
   * Method createCreateRacerPerson.  Creates a RacerPerson Object from the line read from the database
   * @param line - String[], an array of fields to be populated in a racerPerson
   * @return RacerPerson, the racer created to be added to the database.
   */
  private RacerPerson createCreateRacerPerson( String[] line )
  {
    RacerPerson rp = new RacerPerson();

    for (int i = 0; i < line.length; i++)
    {
      String item = line[i];
      switch (i)
      {
        case 0 :
          rp.setDen(item);
          break;
        case 1 :
          rp.setLastName(item);
          break;
        case 2 :
          rp.setFirstName(item);
          break;
        case 3 :
          rp.setAddress(item);
          break;
        case 4 :
          rp.setCity(item);
          break;
        case 5 :
          rp.setState(item);
          break;
        case 6 :
          rp.setPostal(item);
          break;
        case 7 :
          rp.setPhone(item);
          break;
      }
    }
    rp.setDistrict("1");
    rp.setPack("000");
    rp.setVehicleNumber("" + (number++));

    return rp;
  }
  /**
   * Method insertIntoDB.  Inserts a RacerPerson into the database
   * @param rp - RacerPerson, the racer person to add to the database
   */
  private void insertIntoDB( RacerPerson rp )
  {
    System.out.println(rp);
    try
    {
      RaceARamaDB.insert(rp);
    }
    catch (DBAccessException exc)
    {
      System.err.println(exc);
    }
  }
  /**
   * Method process.  Main processing method, reads a line of data from the .csv file.
   * Named scouts.csv.  The First Line is assumed to be column Names starting with a # symbol.
   */
  public void process()
  {
    StandardInput stdin = new StandardInput(fileName);
    String[] line = stdin.readLine(",");
    try
    {
      while (line != null)
      {
        line = stdin.readLine(",");
        RacerPerson rp = createCreateRacerPerson(line);
        insertIntoDB(rp);
      }
    }
    catch (Exception exc)
    {
      System.out.println(exc);
    }

  }

  /**
   * Method main.  
   * @param args
   */
  public static void main( String[] args )
  {
    DBLoad dbLoad = new DBLoad();
    dbLoad.process();
  }
}
