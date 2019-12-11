/*
 * @author:			Owner
 * Package:			com.boyscouts.domain
 * File Name:		WebPublishRacerSortField.java
 */
package com.boyscouts.publish.results;

import java.io.Serializable;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.publish.results<BR>
 * File Name:   WebPublishRacerSortField.java<BR>
 * Type Name:   WebPublishRacerSortField<BR>
 * Description: Column Names for the Person Registered Object.
 */

class WebPublishRacerSortField implements Comparable, Serializable
{

  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3976741371673587764L;

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

  /** Field <code>NAME</code> : WebPublishRacerSortField */
  public static final WebPublishRacerSortField LNAME_FNAME = new WebPublishRacerSortField("LastNameFirstName");
  /** Field <code>PLACEMENT</code> : WebPublishRacerSortField - sorting on this actually is by avergae value*/
  public static final WebPublishRacerSortField PLACEMENT = new WebPublishRacerSortField("Placement");
  /** Field <code>PACK</code> : WebPublishRacerSortField */
  public static final WebPublishRacerSortField PACK = new WebPublishRacerSortField("Pack");
  /** Field <code>DEN</code> : WebPublishRacerSortField */
  public static final WebPublishRacerSortField DEN = new WebPublishRacerSortField("Den");
  /** Field <code>LIST</code> : WebPublishRacerSortField[] */
  public static final WebPublishRacerSortField[] LIST = {LNAME_FNAME, PLACEMENT, PACK, DEN};

  /**
   * Private Constructor for WebPublishRacerSortField. 
   * @param name - String, the literal required to create the enumeration.
   */
  private WebPublishRacerSortField( String name )
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
    return (ordinal - ((WebPublishRacerSortField) obj).ordinal);
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

    if (!rc && (obj instanceof WebPublishRacerSortField))
    {
      WebPublishRacerSortField that = (WebPublishRacerSortField) obj;
      rc = (this.fieldName.equals(that.fieldName) && (this.ordinal == that.ordinal));
    }
    return rc;
  }
  /**
   * Method getWebPublishRacerSortField.  Returns the enumeration of the String requested value
   * String is the actual string literal used to create the enumeration.
   * @param value - String, the string literal
   * @return WebPublishRacerSortField 
   */
  public static WebPublishRacerSortField getWebPublishRacerSortField( String value )
  {
    WebPublishRacerSortField retVal = WebPublishRacerSortField.LNAME_FNAME;
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
   * Returns the ordinal.
   * @return int
   * 
   * @uml.property name="ordinal"
   */
  public int getOrdinal()
  {
    return ordinal;
  }

  /**
   * Method getOrdinal.  Returns the ordinal value.
   * @return int
   */
  public int toOrd()
  {
    return ordinal;
  }
}
