/*
 * @author:			Owner
 * Package:			com.boyscouts.domain
 * File Name:		WinPlaceShow.java
 */
package com.boyscouts.domain;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   WinPlaceShow.java<BR>
 * Type Name:   WinPlaceShow<BR>
 * Description: A Type Safe Enumeration of JLabels for And Images
 */

public class WinPlaceShow extends JLabel implements Comparable, Serializable
{
  /** Field <code>serialVersionUID</code> : long */
  static final long serialVersionUID = -4221953670442275030L;
  /** Field <code>nextOrdinal</code>: int */
  private static int nextOrdinal = 0;

  /**
   * Field <code>fieldName</code> : String
   * 
   * @uml.property name="fieldName"
   */
  private final String fieldName;

  /**
   * Field <code>ordinal</code>: int
   * 
   * @uml.property name="ordinal"
   */
  private final int ordinal = nextOrdinal++;

  /** Field <code>FIRST</code>: WinPlaceShow */
  public static final WinPlaceShow FIRST = new WinPlaceShow("First");
  /** Field <code>SECOND</code>: WinPlaceShow */
  public static final WinPlaceShow SECOND = new WinPlaceShow("Second");
  /** Field <code>THIRD</code>: WinPlaceShow */
  public static final WinPlaceShow THIRD = new WinPlaceShow("Third");
  /** Field <code>FOURTH</code>: WinPlaceShow */
  public static final WinPlaceShow FOURTH = new WinPlaceShow("Fourth");
  /** Field <code>FIFTH</code>: WinPlaceShow */
  public static final WinPlaceShow FIFTH = new WinPlaceShow("Fifth");
  /** Field <code>OUT_OF_RANGE</code>: WinPlaceShow */
  public static final WinPlaceShow OUT_OF_RANGE = new WinPlaceShow("OutOfRange");
  /** Field <code>LIST</code>: WinPlaceShow[] */
  public static final WinPlaceShow[] LIST = {FIRST, SECOND, THIRD, FOURTH, FIFTH, OUT_OF_RANGE};
  /**
   * Private Constructor for WinPlaceShow. 
   * @param name - String, the literal required to create the enumeration.
   */
  private WinPlaceShow( String name )
  {
    this.fieldName = name;
    loadImageIcon();
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
    return (ordinal - ((WinPlaceShow) obj).ordinal);
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
    if (!rc && (obj instanceof WinPlaceShow))
    {
      WinPlaceShow that = (WinPlaceShow) obj;
      rc = (this.fieldName.equals(that.fieldName) && (this.ordinal == that.ordinal));
    }
    return rc;
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
   * Method getWinPlaceShow.  Returns the enumeration of the Integer requested value
   * Integer is the actual int literal used to create the enumeration.
   * @param value - Integer, the Integer value
   * @return WinPlaceShow 
   */
  public static WinPlaceShow getWinPlaceShow( Integer value )
  {
    WinPlaceShow retVal = OUT_OF_RANGE;
    if (value != null)
    {
      int index = value.intValue();
      index--; // Normalize the index to natural based numbers
      if (0 <= index && index < LIST.length)
      {
        retVal = LIST[index];
      }
    }
    return retVal;
  }
  /**
   * Method getWinPlaceShow.  Returns the enumeration of the String requested value
   * String is the actual string literal used to create the enumeration.
   * @param value - String, the string literal
   * @return WinPlaceShow 
   */
  public static WinPlaceShow getWinPlaceShow( String value )
  {
    WinPlaceShow retVal = OUT_OF_RANGE;
    if (value != null)
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
   * Method readResolve.
   * 
   * @return Object
   */
  Object readResolve()
  {
    return LIST[ordinal];
  }

  /**
   * Returns the ordinal.
   * 
   * @return int
   * 
   * @uml.property name="ordinal"
   */
  public int getOrdinal()
  {
    return ordinal;
  }

  /**
   * @return Returns the fieldName : String.
   * 
   * @uml.property name="fieldName"
   */
  public String getFieldName()
  {
    return fieldName;
  }

  /**
   * Creates an icon from an image contained in the "images" directory.
   * @return ImageIcon
   */
  private ImageIcon createImageIcon( String filename )
  {
    String path = "/resources/images/" + filename;
    ImageIcon ii = new ImageIcon(getClass().getResource(path));
    return ii;
  }

  /**
   * Method loadImageIcon.  
   * 
   */
  private void loadImageIcon()
  {
    String imageName = fieldName + "Place_Boom.gif";
    if ("OutOfRange".toString().equals(fieldName))
    {
      imageName = fieldName + ".gif";
    }
    ImageIcon ii = createImageIcon(imageName);
    this.setIcon(ii);
  }
}
