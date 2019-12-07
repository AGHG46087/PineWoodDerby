/*
 * @author:			Owner
 * date:				Dec 9, 2003
 * Package:			com.boyscouts.domain
 * File Name:		RaceDataFieldName.java
 */
package com.boyscouts.domain;

import java.io.Serializable;

/**
 * author:      hgrein<BR>
 * date:        Jun 2, 2004<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   RaceDataFieldName.java<BR>
 * Type Name:   RaceDataFieldName<BR>
 * Description: Column Names for the RaceData Object
 */

public class RaceDataFieldName implements Comparable, Serializable
{
  /** Field <code>serialVersionUID</code> : long */
  static final long serialVersionUID = 2964504653188194492L;
  /** Field <code>nextOrdinal</code> : int */
  private static int nextOrdinal = 0;

  /**
   * Field <code>fieldName</code> : String
   * 
   * @uml.property name="fieldName"
   */
  private final String fieldName;

  /**
   * Field <code>ordinal</code> : int
   * 
   * @uml.property name="ordinal"
   */
  private final int ordinal = nextOrdinal++;

  /** Field <code>ENTRY</code> : RaceDataFieldName */
  public static final RaceDataFieldName ENTRY = new RaceDataFieldName("Entry");
  /** Field <code>ID</code> : RaceDataFieldName */
  public static final RaceDataFieldName ID = new RaceDataFieldName("ID");
  /** Field <code>VEHICLE_NUMBER</code> : RaceDataFieldName */
  public static final RaceDataFieldName VEHICLE_NUMBER = new RaceDataFieldName("VehicleNumber");
  /** Field <code>ROUND</code> : RaceDataFieldName */
  public static final RaceDataFieldName ROUND = new RaceDataFieldName("Round");
  /** Field <code>HEAT</code> : RaceDataFieldName */
  public static final RaceDataFieldName HEAT = new RaceDataFieldName("Heat");
  /** Field <code>LANE</code> : RaceDataFieldName */
  public static final RaceDataFieldName LANE = new RaceDataFieldName("Lane");
  /** Field <code>RACE_TIME</code> : RaceDataFieldName */
  public static final RaceDataFieldName RACE_TIME = new RaceDataFieldName("RaceTime");
  /** Field <code>PLACEMENT</code> : RaceDataFieldName */
  public static final RaceDataFieldName PLACEMENT = new RaceDataFieldName("Place");
  /** Field <code>DATE</code> : RaceDataFieldName */
  public static final RaceDataFieldName DATE = new RaceDataFieldName("EntryDate");

  /** Field <code>LIST</code> : RaceDataFieldName[] */
  public static final RaceDataFieldName[] LIST = {ENTRY, ID, VEHICLE_NUMBER, ROUND, HEAT, LANE, RACE_TIME, PLACEMENT, DATE};

  /**
   * Private Constructor for RaceDataFieldName. 
   * @param name - String, the literal required to create the enumeration.
   */
  private RaceDataFieldName( String name )
  {
    this.fieldName = name;
  }

  /**
   * Overridden Method:  Returns a string representation of the enumeration
   * @see java.lang.Object#toString()
   * @return String
   */
  public String toString()
  {
    return this.fieldName;
  }

  /**
   * Overridden Method:  
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   * @param obj - Object, The Object to comapre to.
   * @return int, value &lt; 0 is less than, value = 0 if equal, &gt; if greater than. 
   */
  public int compareTo( Object obj )
  {
    return (ordinal - ((RaceDataFieldName) obj).ordinal);
  }

  /**
   * Overridden Method:  Compares the quality of the two objects.
   * @see java.lang.Object#equals(java.lang.Object)
   * @param obj - Object, the object to compare with it.
   * @return boolean, true if equal, false if not equal
   */
  public boolean equals( Object obj )
  {
    boolean rc = (this == obj);

    if (!rc && (obj instanceof RaceDataFieldName))
    {
      RaceDataFieldName that = (RaceDataFieldName) obj;
      rc = (this.fieldName.equals(that.fieldName) && (this.ordinal == that.ordinal));
    }
    return rc;
  }
  /**
   * Method getRaceDataFieldName.  Returns the enumeration of the String requested value
   * String is the actual string literal used to create the enumeration.
   * @param value - String, the string literal
   * @return RaceDataFieldName
   */
  public static RaceDataFieldName getRaceDataFieldName( String value )
  {
    RaceDataFieldName retVal = RaceDataFieldName.ID;
    if (value != null && value.length() > 1)
    {
      boolean done = false;
      for (int i = 0; i < LIST.length && !done; i++)
      {
        if (LIST[i].fieldName.equals(value))
        {
          retVal = LIST[i];
          done = true;
        }
      }
    }
    return retVal;
  }
  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode()
  {
    int rc = 7;

    rc = rc * 11 + this.fieldName.hashCode();
    rc = rc * 11 + this.ordinal;

    return rc;
  }

  /**
   * Method readResolve.  
   * @return Object
   */
  Object readResolve()
  {
    return LIST[ordinal];
  }

  /**
   * Method getFieldName.  Returns the name.
   * @return String
   * 
   * @uml.property name="fieldName"
   */
  public String getFieldName()
  {
    return fieldName;
  }

  /**
   * Method getOrdinal.  Returns the ordinal value.
   * @return int
   * 
   * @uml.property name="ordinal"
   */
  public int getOrdinal()
  {
    return ordinal;
  }

  /**
   * Method toOrd.  Returns the ordinal value.
   * @return int
   */
  public int toOrd()
  {
    return ordinal;
  }
}
