/*
 * @author:			Owner
 * Package:			com.boyscouts.domain
 * File Name:		RaceDataContainer.java
 */
package com.boyscouts.domain;

import java.io.Serializable;
import java.util.Vector;

import com.hgutil.ParseData;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   RaceDataContainer.java<BR>
 * Type Name:   RaceDataContainer<BR>
 * Description: Container for RacerPerson Data heat information
 */

public class RaceDataContainer implements Serializable
{
  /** Field <code>serialVersionUID</code> : long */
  static final long serialVersionUID = 7300310734580905292L;

  /**
   * Field <code>racerData</code> : Vector
   * 
   * @uml.property name="racerData"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="com.boyscouts.domain.RaceData"
   */
  private Vector racerData = null;

  /**
   * Field <code>headers</code> : Vector
   * 
   * @uml.property name="headers"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="java.lang.String"
   */
  private Vector headers = null;

  /**
   * Constructor for RaceDataContainer.  Creates an Empty Container
   * It uses the static method <B>generateHeaderData()</B> from the 
   * <B>RaceData</B> class to generate the headers. and a Blank RaceData for
   * a Empty Record 
   */
  public RaceDataContainer()
  {
    Vector headerData = RaceData.generateHeaderData();
    setHeaders(headerData);

    RaceData raceData = new RaceData();
    addRaceData(raceData);
  }
  /**
   * Constructor for RaceDataContainer. Creates an populated Container
   * @param headers - Vector, The Data as a Column Headers
   * @param tableData - Vector, the Data as a Vector of Vectors, ( Cell Data )
   */
  public RaceDataContainer( Vector headers, Vector tableData )
  {
    setHeaders(headers);
    if (tableData != null)
    {
      for (int i = 0; i < tableData.size(); i++)
      {
        addRaceData((Vector) tableData.elementAt(i));
      }
    }
  }
  /**
   * Method addRaceData.  A RaceData Valuebean can be added to the structure.
   * @param raceData - RaceData, The Data valuebean to add.
   * @return boolean, true if the data was added, false if not
   */
  public boolean addRaceData( RaceData raceData )
  {
    boolean dataAdded = false;
    if (raceData == null)
    {
      return dataAdded;
    }
    if (racerData == null)
    {
      racerData = new Vector();
    }
    if (racerData.contains(raceData))
    {
      racerData.remove(raceData);
      Log.debug("Race Data already Exists - Remove!\n" + raceData);
    }
    if (racerData.size() == 1)
    {
      RaceData tempRD = (RaceData) racerData.get(0);
      if (tempRD.isEmptyRecord())
      {
        racerData.clear();
      }
    }
    racerData.add(raceData);
    dataAdded = true;
    return dataAdded;
  }
  /**
   * Method addRaceData.   A RaceData Valuebean can be added to the structure.
   * @param dbRowData - Vector, the Coulumn within a single Row in the Database
   * @return boolean, true if the data was added, false if not
   */
  private boolean addRaceData( Vector dbRowData )
  {
    boolean dataAdded = false;
    if (dbRowData == null)
    {
      return dataAdded;
    }
    RaceData raceData = new RaceData(dbRowData);
    dataAdded = addRaceData(raceData);
    return dataAdded;
  }
  /**
   * Method clear.  Removes all the Data in the RacerdataContainer 
   */
  public void clear()
  {
    if (racerData != null)
    {
      racerData.clear();
    }
  }
  /**
   * Method dumpContainer.  Dumps the Container data as if it was a Database.
   */
  public void dumpContainer()
  {
    final int PAD_LENGTH = 20;
    System.out.println(this.toString());
    System.out.println("RacerDataContainer :  Results ");
    if (headers == null || racerData == null)
    {
      System.out.println("Bad Result Set: headers=[" + headers + "], racerData=[" + racerData + "]");
      return;
    }
    for (int i = 0; i < headers.size(); i++)
    {
      String temp = (String) headers.elementAt(i);
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
      RaceData rp = this.elementAt(i);
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
    System.out.println();
    System.out.println("MIN TIME: " + getMinTime());
    System.out.println("MAX TIME: " + getMaxTime());
    System.out.println("AVG TIME: " + getAvgTime());
    System.out.println("STD DEV : " + getStdDev());
    System.out.println(ParseData.padChars('-', (headers.size() + 1) * PAD_LENGTH));
    System.out.println(rowSize + " rows in container");

  }
  /**
   * Method elementAt.  Returns the element at the index specified
   * @param index - int, the index of the element
   * @return RaceData
   */
  public RaceData elementAt( int index )
  {
    RaceData raceData = null;
    if (racerData != null)
    {
      if (0 <= index && index < racerData.size())
      {
        raceData = (RaceData) racerData.elementAt(index);
      }
    }
    return raceData;
  }
  /**
   * Method getAvgTime.  Returns the Average Time to complete all the heats
   * @return Double, the Average Time
   */
  public Double getAvgTime()
  {
    double value = 0.0;
    int listSize = getSize();
    for (int i = 0; i < listSize; i++)
    {
      RaceData rd = elementAt(i);
      double heatTime = rd.getTime().doubleValue();
      value += heatTime;
    }
    // Divide the value by the number of Elements in the Container
    value /= listSize;
    return (new Double(value));
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
   * Method getMaxTime.  Returns the Maximum Time to complete all the heats
   * @return Double, the Maximum Time
   */
  public Double getMaxTime()
  {
    double value = -100.0;
    int listSize = getSize();
    for (int i = 0; i < listSize; i++)
    {
      RaceData rd = elementAt(i);
      double heatTime = rd.getTime().doubleValue();
      if (heatTime > value)
      {
        value = heatTime;
      }
    }
    return (new Double(value));
  }
  /**
   * Method getMinTime.  Returns the Minimum Time to complete all the heats
   * @return Double, the Minimum Time
   */
  public Double getMinTime()
  {
    double value = 100.0;
    int listSize = getSize();
    for (int i = 0; i < listSize; i++)
    {
      RaceData rd = elementAt(i);
      double heatTime = rd.getTime().doubleValue();
      if (heatTime < value)
      {
        value = heatTime;
      }
    }
    return (new Double(value));
  }

  /**
   * Method getRacerData.  Returns the Data of the container
   * @return Vector
   * 
   * @uml.property name="racerData"
   */
  public Vector getRacerData()
  {
    return racerData;
  }

  /**
   * Method getSize.  Returns the Size of the container
   * @return int
   */
  public int getSize()
  {
    int size = 0;
    if (racerData != null)
    {
      size = racerData.size();
      if ((size == 1) && ((RaceData) racerData.get(0)).isEmptyRecord())
      {
        size = 0;
      }
    }
    return size;
  }
  /**
   * Method getStdDev.  Returns the Standard Deviation of the Population Time to complete 
   * all the heats.  Standard deviation tells how tightly a set of values is clustered around 
   * the average of those same values. It's a measure of dispersal, or variation, in a 
   * group of numbers.<BR>
   * Higher standard deviation is often interpreted as higher volatility. In comparison, 
   * lower standard deviation would likely be an indicator of stability. The most consistent 
   * values will usually be the set of numbers with the lowest standard deviation.
   * @return Double, the Standard Deviation
   */
  public Double getStdDev()
  {
    double value = 0.0;
    int listSize = getSize();
    if (listSize > 1)
    {
      double summedSquaredDeviation = 0.0;
      double averageValue = getAvgTime().doubleValue();
      // Sum of the Squared Deviation from Each Heat
      for (int i = 0; i < listSize; i++)
      {
        RaceData rd = elementAt(i);
        double heatTime = rd.getTime().doubleValue();
        double deviation = heatTime - averageValue; // Compute the Devaition from Average
        double squaredDeviation = Math.pow(deviation, 2); // Square the Devaition
        summedSquaredDeviation += squaredDeviation; // Sum othe sqared Deviation
      }
      // Compute the Standard Deviation by Dividing the SummSquared Deviation by (population minus 1)
      value = summedSquaredDeviation / (listSize - 1);
    }
    return (new Double(value));
  }
  /**
   * Method getTotalPoints.  Returns the sum of all the points for the racers race dataq
   * @return int - the sum of all the points
   */
  public int getTotalPoints()
  {
    int totalPoints = 0;
    int listSize = getSize();

    for (int i = 0; i < listSize; i++)
    {
      RaceData rd = elementAt(i);
      int tempPoints = rd.getPoints();
      if (0 <= tempPoints && tempPoints <= RaceData.MAX_POINTS)
      {
        totalPoints += tempPoints;
      }
    }

    return totalPoints;
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
  }

  /**
   * Overridden Method:  
   * @see java.lang.Object#toString()
   * @return String
   */
  public String toString()
  {
    String sb = "RacerDataContainer:\n";
    sb += "HEADERS_SIZE=" + ((headers != null) ? "" + headers.size() : "null") + "\n";
    sb += "DATA_SIZE=" + ((racerData != null) ? "" + racerData.size() : "null") + "\n";
    return sb;
  }
}
