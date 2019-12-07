/*
 * @author:			Owner
 * date:				Dec 9, 2003
 * Package:			com.boyscouts.domain
 * File Name:		PhoneNumber.java
 */
package com.boyscouts.domain;

import java.io.Serializable;

/**
 * author:      hgrein<BR>
 * date:        Jun 2, 2004<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   PhoneNumber.java<BR>
 * Type Name:   PhoneNumber<BR>
 * Description: Maintains a Phone Number
 */

public class PhoneNumber implements Comparable, Serializable, Cloneable
{
  /** Field <code>serialVersionUID</code> : long */
  static final long serialVersionUID = -7914928036814403013L;

  /**
   * Field <code>areaCode</code> : String
   * 
   * @uml.property name="areaCode"
   */
  private String areaCode = "";

  /**
   * Field <code>number</code> : String
   * 
   * @uml.property name="number"
   */
  private String number = "";

  /**
   * Constructor for PhoneNumber. 
   * @param fullNumber - String, the full phone number
   */
  public PhoneNumber( String fullNumber )
  {
    this.areaCode = extractAreaCode(fullNumber);
    this.number = extractNumber(fullNumber);
  }
  /**
   * Constructor for PhoneNumber. 
   * @param areaCode - String, The area code of the Phone Number
   * @param number - String, The base number of the Phone Number
   */
  public PhoneNumber( String areaCode, String number )
  {
    this.areaCode = areaCode;
    this.number = number;
  }
  /**
   * Overridden Method:  
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   * @param o - Object
   * @return int
   */
  public int compareTo( Object o )
  {
    int rc = -1;
    if (o instanceof PhoneNumber)
    {
      PhoneNumber thatPhone = (PhoneNumber) o;
      rc = this.toString().compareTo(thatPhone.toString());
    }
    return rc;
  }
  /**
   * Method extractAreaCode.  This methods extract the Area Code from the Phone Number
   * @param fullNumber - String, the Complete Phone Number
   * @return String, the area code
   */
  private String extractAreaCode( String fullNumber )
  {
    String temp = "";
    fullNumber = stripPunctuation(fullNumber);
    if (fullNumber.length() >= 10)
    {
      temp = fullNumber.substring(0, 3);
    }
    return temp;
  }
  /**
   * Method extractNumber.  This method extracts the base number from the Complete Phone number
   * @param fullNumber - String, the complete phone number
   * @return String, the base phone number
   */
  private String extractNumber( String fullNumber )
  {
    String temp = "";
    fullNumber = stripPunctuation(fullNumber);
    if (fullNumber.length() >= 10)
    {
      temp = fullNumber.substring(3);
    }
    else if (fullNumber.length() >= 7)
    {
      temp = fullNumber.substring(0);
    }
    return temp;
  }
  /**
   * Method extractPrefix.  a Method to extract the Prefix of the Base Phone Number.  
   * If the area code is deisred, then, the extractAreaCode is the method desired to be used.
   * @param fullNumber - String, the Base Phone Number
   * @return String - the Prefix of a Phone Number
   */
  private String extractPrefix( String fullNumber )
  {
    String temp = "";
    fullNumber = extractNumber(fullNumber);
    if (fullNumber.length() == 7)
    {
      temp = fullNumber.substring(0, 3);
    }

    return temp;
  }
  /**
   * Method extractSuffix.  This method extracts the suffix from a Base Phone Number
   * @param fullNumber - String, the Base Phone Number
   * @return String, the suffix number
   */
  private String extractSuffix( String fullNumber )
  {
    String temp = "";
    fullNumber = extractNumber(fullNumber);
    if (fullNumber.length() == 7)
    {
      temp = fullNumber.substring(3);
    }

    return temp;
  }

  /**
   * Method getAreaCode.  Returnsw the Area Code of the Phone Number
   * @return String, the area code
   * 
   * @uml.property name="areaCode"
   */
  public String getAreaCode()
  {
    return areaCode;
  }

  /**
   * Method getNumber.  Returns the base Phone number
   * @return String, the base phone number
   * 
   * @uml.property name="number"
   */
  public String getNumber()
  {
    return number;
  }

  /**
   * Method getSessionCopy.  Returns a Session Copy of the Object
   * @return PhoneNumber
   */
  public PhoneNumber getSessionCopy()
  {
    PhoneNumber pn = null;
    try
    {
      pn = (PhoneNumber) clone();
      pn.areaCode = this.getAreaCode();
      pn.number = this.getNumber();
    }
    catch (CloneNotSupportedException exc)
    {
    }
    return pn;
  }

  /**
   * Method setAreaCode.  Sets the Area Code
   * @param areaCode - String, the area code to set
   * 
   * @uml.property name="areaCode"
   */
  public void setAreaCode( String areaCode )
  {
    this.areaCode = areaCode;
  }

  /**
   * Method setNumber.  Sets the Base Number
   * @param number - String, the base number to set.
   * 
   * @uml.property name="number"
   */
  public void setNumber( String number )
  {
    this.number = number;
  }

  /**
   * Method stripPunctuation.  Removes all punctuation from a complete Phone number
   * @param fullNumber - String, the Full Phone Number
   * @return String - The modified phone number with punctuation removed.
   */
  private String stripPunctuation( String fullNumber )
  {
    String temp = "";
    if (fullNumber != null && fullNumber.length() >= 1)
    {
      for (int i = 0; i < fullNumber.length(); i++)
      {
        char ch = fullNumber.charAt(i);
        if (Character.isDigit(ch))
        {
          temp += ch;
        }
      }
    }

    return temp;
  }
  /**
   * Overridden Method:  Returns a string representation of the Phone Number
   * @see java.lang.Object#toString()
   * @return String
   */
  public String toString()
  {
    String area = getAreaCode();
    String prefix = extractPrefix(getNumber());
    String suffix = extractSuffix(getNumber());
    String temp = area + "-" + prefix + "-" + suffix;
    return temp;
  }
}