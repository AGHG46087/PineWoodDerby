/*
 * @author:			Owner
 * date:				Dec 9, 2003
 * Package:			com.boyscouts.domain
 * File Name:		RacerContainer.java
 */
package com.boyscouts.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.hgtable.ColumnHeaderData;
import com.hgutil.ParseData;

/**
 * author:      hgrein<BR>
 * date:        Jun 2, 2004<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   RacerContainer.java<BR>
 * Type Name:   RacerContainer<BR>
 * Description: Container of Racer Persons
 */

public class RacerContainer implements Serializable
{

  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3258125847641666102L;

  /**
   * Field <code>racers</code> : Vector
   * 
   * @uml.property name="racers"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="com.boyscouts.domain.RacerPerson"
   */
  private Vector racers = null;

  /**
   * Field <code>headers</code> : Vector
   * 
   * @uml.property name="headers"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="com.hgtable.ColumnHeaderData"
   */
  private Vector headers = null;

  /**
   * Field <code>raceName</code> : String
   * 
   * @uml.property name="raceName"
   */
  private String raceName = null;

  /**
   * Constructor for RacerContainer. Default Constuctor. Creates an Empty Container
   * It uses the static method <B>generateHeaderData()</B> from the 
   * <B>RacerPerson</B> class to generate the headers. and a Blank RacerPerson for
   * a Empty Record 
   */
  public RacerContainer()
  {
    Vector headerData = RacerPerson.generateHeaderData();
    setHeaders(headerData);

    RacerPerson racerPerson = new RacerPerson();
    addRacerPerson(racerPerson);
  }
  /**
   * Constructor for RacerContainer. Overloaded Constructor. Creates an Empty Container
   * It uses the static method <B>generateHeaderData()</B> from the 
   * <B>RacerPerson</B> class to generate the headers. and a RacerPerson for
   * a Parameter
   * @param racerPerson - RacerPerson
   */
  public RacerContainer( RacerPerson racerPerson )
  {
    Vector headerData = RacerPerson.generateHeaderData();
    setHeaders(headerData);

    addRacerPerson(racerPerson);
  }
  /**
   * Constructor for RacerContainer. 
   * @param tableData - Vector, the Data as a Vector of Vectors, ( Cell Data )
   */
  public RacerContainer( Vector tableData )
  {
    Vector headerData = RacerPerson.generateHeaderData();
    setHeaders(headerData);
    if (tableData != null)
    {
      for (int i = 0; i < tableData.size(); i++)
      {
        addRacerPerson((Vector) tableData.elementAt(i));
      }
    }
  }
  /**
   * Method addRaceData.  This Method will add a Single RaceDataObject to the
   * RacerPerson it is associated with.  If the RaceData is null, or the current
   * Racer Container is null it will not add it.  
   * @param rd - RaceData, the RaceData to add to the RacerPerson
   */
  public void addRaceData( RaceData rd )
  {
    if (rd == null || racers == null)
    {
      return;
    }
    RacerPerson racerPerson = new RacerPerson();
    racerPerson.setId(rd.getId());
    racerPerson = getRacePerson(racerPerson);
    racerPerson.addRaceData(rd);
  }
  /**
   * Method addRaceData.  This Method will add all RaceDataObjects to the
   * RacerPerson it is associated with.  If the RaceDataContainer is null, 
   * or the current Racer Container is null it will not add it.  
   * @param raceDataContainer - RaceDataContainer, the RaceDataContainer to add to any RacerPerson
   */
  public void addRaceData( RaceDataContainer raceDataContainer )
  {
    if (raceDataContainer == null)
    {
      return;
    }
    int raceDataSize = raceDataContainer.getSize();
    for (int i = 0; i < raceDataSize; i++)
    {
      RaceData rd = raceDataContainer.elementAt(i);
      this.addRaceData(rd);
    }
  }
  /**
   * Method addRacerPerson.  A RacePerson Valuebean can be added to the structure.
   * @param racerPerson - RacerPerson, The Data valuebean to add.
   * @return boolean, true if the data was added, false if not
   */
  public boolean addRacerPerson( RacerPerson racerPerson )
  {
    boolean racerAdded = false;
    if (racerPerson == null)
    {
      return racerAdded;
    }
    if (racers == null)
    {
      racers = new Vector();
    }
    if (racers.contains(racerPerson))
    {
      JOptionPane.showMessageDialog(null, "Racer Person already Registered!\n" + racerPerson, "Racer Already Registered!", JOptionPane.INFORMATION_MESSAGE);
      return racerAdded;
    }
    if (racers.size() == 1)
    {
      RacerPerson tempRP = (RacerPerson) racers.get(0);
      if (tempRP.isEmptyRecord())
      {
        racers.clear();
      }
    }
    racerPerson.setRaceName(raceName);
    racers.add(racerPerson);
    racerAdded = true;
    return racerAdded;
  }
  /**
   * Method addRacerPerson.  A RacePerson Valuebean can be added to the structure.
   * @param dbRowData - Vector, the Coulumn within a single Row in the Database
   * @return boolean, true if the data was added, false if not
   */
  private boolean addRacerPerson( Vector dbRowData )
  {
    boolean racerAdded = false;
    if (dbRowData == null)
    {
      return racerAdded;
    }
    RacerPerson racerPerson = new RacerPerson(dbRowData);
    racerAdded = addRacerPerson(racerPerson);
    return racerAdded;
  }
  /**
   * Method determinePlacement.  Sort the Racers by their PLacement - This is determined 
   * by the total number of Points, and insert the placement value
   */
  private void determinePlacement()
  {
    int containerSize = this.getSize();
    if (containerSize > 0)
    {
      Vector data = this.racers;
      Collections.sort(data, new RacerPersonComparator(RacerPersonFieldName.POINTS, true));
      // Now we will ensure the Placment indicators are set.

      for (int i = 0; i < containerSize; i++)
      {
        RacerPerson rp = this.elementAt(i);
        rp.setPlacement(i + 1); // Make the Placement positive based
      }
    }
  }
  /**
   * Method dumpContainer.  Dumps the Container data as if it was a Database.
   */
  public void dumpContainer()
  {
    final int PAD_LENGTH = 20;
    System.out.println(this.toString());
    System.out.println("RacerContainer :  Results ");
    if (headers == null || racers == null)
    {
      System.out.println("Bad Result Set: headers=[" + headers + "], racers=[" + racers + "]");
      return;
    }
    for (int i = 0; i < headers.size(); i++)
    {
      Object obj = headers.elementAt(i);
      String temp = "";
      if (obj instanceof String)
      {
        temp = (String) obj;
      }
      else if (obj instanceof ColumnHeaderData)
      {
        temp = ((ColumnHeaderData) obj).getTitle();
      }
      if (i < headers.size() - 1)
      {
        temp += ',';
      }
      System.out.print(ParseData.padString(temp, PAD_LENGTH));
    }
    System.out.println("\n" + ParseData.padChars('-', (headers.size() + 1) * PAD_LENGTH));
    int rowSize = this.getSize();
    for (int i = 0; i < rowSize; i++)
    {
      RacerPerson rp = this.elementAt(i);
      Vector data = rp.getDataAsVector();
      for (int j = 0; j < data.size(); j++)
      {
        String temp = "" + data.elementAt(j);
        if (j < data.size() - 1)
        {
          temp += ',';
        }
        System.out.print(ParseData.padString(temp, PAD_LENGTH));
      }
      System.out.println();
    }
    System.out.println("\n" + ParseData.padChars('-', (headers.size() + 1) * PAD_LENGTH));
    System.out.println(rowSize + " rows in container");

  }
  /**
   * Method elementAt.  Returns the element at the index specified
   * @param index - int, the index of the element
   * @return RacerPerson - The Racer person at index, NOTE: the return value can be null.
   */
  public RacerPerson elementAt( int index )
  {
    RacerPerson racerPerson = null;
    if (racers != null)
    {
      if (0 <= index && index < racers.size())
      {
        racerPerson = (RacerPerson) racers.elementAt(index);
      }
    }
    return racerPerson;
  }

  /**
   * Returns the Header for the Container
   * @return Returns the headers : Vector.
   * 
   * @uml.property name="headers"
   */
  public Vector getHeaders()
  {
    return headers;
  }

  /**
   * Method getRacePerson.  Method Returns a RacerPerson Object
   * if it matches the Criteria of the Passed in Object. <BR>
   * i.e. ID, LastName, FirstName<BR>
   * If it is desired to only find a Person with the ID. then Create
   * an empty RacerPerson,<BR>
   * <PRE>
   * RacerPerson racerPerson = new RacerPerson();
   * RacerPerson.setCompareIDOnly(true);
   * racerPerson.setId(rd.getId());
   * <B>racerPerson = getRacePerson(racerPerson);</B>
   * RacerPerson.setCompareIDOnly(false);
   * </PRE><BR>
   * If the RacerPerson object is not found, then the Formal Parameter
   * is returned.
   * @param racerPerson - RacerPerson, The Person to search for, 
   *        ensure the ID is set.
   * @return RacerPerson - IF found then the found RacerPerson is 
   *         returned, if not found - then the formal parameter is returned
   */
  private RacerPerson getRacePerson( RacerPerson racerPerson )
  {
    RacerPerson tempRacer = null;
    RacerPerson.setCompareIDOnly(true);
    int index = racers.indexOf(racerPerson);
    tempRacer = this.elementAt(index);
    if (tempRacer == null)
    {
      tempRacer = racerPerson;
    }
    RacerPerson.setCompareIDOnly(false);
    return tempRacer;
  }

  /**
   * Method getRacers.  Returns the Data of the container
   * @return Returns the racers : Vector.
   * 
   * @uml.property name="racers"
   */
  public Vector getRacers()
  {
    updateRacerTimes();
    return racers;
  }

  /**
   * Method getSize.  Returns the Size of the container
   * @return int, the size of the container.
   */
  public int getSize()
  {
    int size = 0;
    if (racers != null)
    {
      size = racers.size();
      if ((size == 1) && ((RacerPerson) racers.get(0)).isEmptyRecord())
      {
        size = 0;
      }
    }
    return size;
  }
  /**
   * Method resetContainedRacerData. This method will reset all the Racers contained within
   * this container to the status of having new fresh data.
   * NOTE: This should only be used in the instance of refreshing the Race and want to 
   * use the same people.
   */
  public void resetContainedRacerData()
  {
    int containerSize = this.getSize();
    for (int i = 0; i < containerSize; i++)
    {
      RacerPerson rp = this.elementAt(i);
      rp.initData();
    }
  }

  /**
   * Method set Headers. allows for seeting the headers
   * @param headers : Vector The headers to set.
   * 
   * @uml.property name="headers"
   */
  public void setHeaders( Vector headers )
  {
    this.headers = (Vector) headers.clone();
    Vector newHeader = new Vector();
    for (int i = 0; i < this.headers.size(); i++)
    {
      ColumnHeaderData columnHeaders = new ColumnHeaderData(this.headers.elementAt(i), 100, JLabel.RIGHT);
      newHeader.add(columnHeaders);
    }
    this.headers = newHeader;
  }

  /**
   * Method setRaceName.  Sets the Race Name of the Race for this Racer.
   * In addition it will update all existing Racers in the container with 
   * the current Race Name
   * @param raceName - String
   * 
   * @uml.property name="raceName"
   */
  public void setRaceName( String raceName )
  {
    int containerSize = this.getSize();
    for (int i = 0; i < containerSize; i++)
    {
      RacerPerson rp = this.elementAt(i);
      rp.setRaceName(raceName);
    }
    this.raceName = raceName;
  }

  /**
   * Overridden Method:  
   * @see java.lang.Object#toString()
   * @return String
   */
  public String toString()
  {
    String sb = "RacerContainer:\n";
    sb += "HEADERS_SIZE=" + ((headers != null) ? "" + headers.size() : "null") + "\n";
    sb += "RACERS_SIZE=" + ((racers != null) ? "" + racers.size() : "null") + "\n";
    return sb;
  }
  /**
   * Method updateRacerTimes.  Method to update all Racer times in the container
   */
  public void updateRacerTimes()
  {
    if (racers != null)
    {
      for (int i = 0; i < racers.size(); i++)
      {
        RacerPerson rp = (RacerPerson) racers.elementAt(i);
        rp.updateRacerTimes();
      }
      // Second Update the Overall Race Display
      determinePlacement();
    }

  }

}