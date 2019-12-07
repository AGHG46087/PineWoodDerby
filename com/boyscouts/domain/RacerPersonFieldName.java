/*
 * @author:			Owner
 * date:				Dec 9, 2003
 * Package:			com.boyscouts.domain
 * File Name:		RacerPersonFieldName.java
 */
package com.boyscouts.domain;

import java.io.Serializable;

/**
 * author:      hgrein<BR>
 * date:        Jun 2, 2004<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   RacerPersonFieldName.java<BR>
 * Type Name:   RacerPersonFieldName<BR>
 * Description: Column Names for the Person Registered Object
 */

public class RacerPersonFieldName implements Comparable, Serializable
{
  /** Field <code>serialVersionUID</code> : long */
  static final long serialVersionUID = -8311999487291064593L;
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

  /** Field <code>ID</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName ID = new RacerPersonFieldName("ID");
  /** Field <code>DISTRICT</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName DISTRICT = new RacerPersonFieldName("District");
  /** Field <code>PACK</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName PACK = new RacerPersonFieldName("Pack");
  /** Field <code>DEN</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName DEN = new RacerPersonFieldName("Den");
  /** Field <code>LAST_NAME</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName LAST_NAME = new RacerPersonFieldName("LastName");
  /** Field <code>FIRST_NAME</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName FIRST_NAME = new RacerPersonFieldName("FirstName");
  /** Field <code>VEHICLE_NUMBER</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName VEHICLE_NUMBER = new RacerPersonFieldName("VehicleNumber");
  /** Field <code>ADDRESS</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName ADDRESS = new RacerPersonFieldName("Address");
  /** Field <code>CITY</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName CITY = new RacerPersonFieldName("City");
  /** Field <code>STATE</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName STATE = new RacerPersonFieldName("State");
  /** Field <code>POSTAL</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName POSTAL = new RacerPersonFieldName("Postal");
  /** Field <code>PHONE</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName PHONE = new RacerPersonFieldName("Phone");
  /** Field <code>MIN_VALUE</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName MIN_VALUE = new RacerPersonFieldName("MinTime");
  /** Field <code>MAX_VALUE</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName MAX_VALUE = new RacerPersonFieldName("MaxTime");
  /** Field <code>AVG_VALUE</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName AVG_VALUE = new RacerPersonFieldName("AvgTime");
  /** Field <code>STD_DEV</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName STD_DEV = new RacerPersonFieldName("StdDev");
  /** Field <code>DATE_REGISTERED</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName DATE_REGISTERED = new RacerPersonFieldName("RegisterDate");
  /** Field <code>RACE_NAME</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName RACE_NAME = new RacerPersonFieldName("RaceName");
  /** Field <code>PLACEMENT</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName PLACEMENT = new RacerPersonFieldName("Placement");
  /** Field <code>POINTS</code> : RacerPersonFieldName */
  public static final RacerPersonFieldName POINTS = new RacerPersonFieldName("Points");

  /** Field <code>LIST</code> : RacerPersonFieldName[] */
  public static final RacerPersonFieldName[] LIST = {ID, DISTRICT, PACK, DEN, LAST_NAME, FIRST_NAME, VEHICLE_NUMBER, ADDRESS, CITY, STATE, POSTAL, PHONE,
                                                     MIN_VALUE, MAX_VALUE, AVG_VALUE, STD_DEV, DATE_REGISTERED, RACE_NAME, PLACEMENT, POINTS};

  /**
   * Private Constructor for RacerPersonFieldName. 
   * @param name - String, the literal required to create the enumeration.
   */
  private RacerPersonFieldName( String name )
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
    return (ordinal - ((RacerPersonFieldName) obj).ordinal);
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

    if (!rc && (obj instanceof RacerPersonFieldName))
    {
      RacerPersonFieldName that = (RacerPersonFieldName) obj;
      rc = (this.fieldName.equals(that.fieldName) && (this.ordinal == that.ordinal));
    }
    return rc;
  }
  /**
   * Method getRacerPersonFieldName.  Returns the enumeration of the String requested value
   * String is the actual string literal used to create the enumeration.
   * @param value - String, the string literal
   * @return RacerPersonFieldName 
   */
  public static RacerPersonFieldName getRacerPersonFieldName( String value )
  {
    RacerPersonFieldName retVal = RacerPersonFieldName.ID;
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
   * Overridden Method:  returns the hashcode representation of the enumeration
   * @see java.lang.Object#hashCode()
   * @return int
   */
  public int hashCode()
  {
    int rc = 7;

    rc = rc * 11 + this.fieldName.hashCode();
    rc = rc * 11 + this.ordinal;

    return rc;
  }

  /**
   * Method readResolve.  A little advertised method used for deserialization since the constructor is private
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