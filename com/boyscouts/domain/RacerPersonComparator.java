package com.boyscouts.domain;

import java.util.Date;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   RacerPersonComparator.java<BR>
 * Type Name:   RacerPersonComparator<BR>
 * Description: A Comparator for the Registered Person Class. This will allow a Any Column to be sorted.
 * Also the order can be ascending or descending
 */

public class RacerPersonComparator implements java.util.Comparator
{

  /**
   * Field <code>sortCol</code> : RacerPersonFieldName
   * 
   * @uml.property name="sortCol"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  protected RacerPersonFieldName sortCol = RacerPersonFieldName.ID;

  /**
   * Field <code>sortAsc</code> : boolean
   * 
   * @uml.property name="sortAsc"
   */
  protected boolean sortAsc;

  /**
   * RacerPersonComparator constructor comment.
   */
  public RacerPersonComparator()
  {
    super();
  }
  /**
   * Constructor for RacerPersonComparator. 
   * @param sortCol - RacerPersonFieldName, The RacerPersonFieldName to sort on.
   * @param sortAsc - boolean, true for ascending, false for descending.
   */
  public RacerPersonComparator( RacerPersonFieldName sortCol, boolean sortAsc )
  {
    this.sortCol = sortCol;
    this.sortAsc = sortAsc;
  }
  /**
   * Overridden Method:  Compares the selected Columns and specified Sort ascending or descending.
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   * @param o1 - Object, The Left Side of the Comparison
   * @param o2 - Object, The Rigth Side of the Comparison
   * @return int - Less than 0 if less than, 0 if equal, Greater tan 0 if greater than
   */
  public int compare( Object o1, Object o2 )
  {
    if (!(o1 instanceof RacerPerson) || !(o2 instanceof RacerPerson))
    {
      return 0;
    }
    RacerPerson s1 = (RacerPerson) o1;
    RacerPerson s2 = (RacerPerson) o2;
    int result = 0;
    double d1, d2;
    int int1, int2;
    String str1, str2;
    try
    {
      switch (sortCol.getOrdinal())
      {
        case 0 :
          // id
          int1 = (int) s1.getId().intValue();
          int2 = (int) s2.getId().intValue();
          result = int1 < int2 ? -1 : (int1 > int2 ? 1 : 0);
          break;
        case 1 :
          // District
          str1 = (String) s1.getDistrict();
          str2 = (String) s2.getDistrict();
          result = str1.compareTo(str2);
          break;
        case 2 :
          // Pack
          str1 = (String) s1.getPack();
          str2 = (String) s2.getPack();
          result = str1.compareTo(str2);
          break;
        case 3 :
          // Den
          str1 = (String) s1.getDen();
          str2 = (String) s2.getDen();
          result = str1.compareTo(str2);
          break;
        case 4 :
          // LastName
          str1 = (String) s1.getLastName();
          str2 = (String) s2.getLastName();
          result = str1.compareTo(str2);
          break;
        case 5 :
          // FirstName
          str1 = (String) s1.getFirstName();
          str2 = (String) s2.getFirstName();
          result = str1.compareTo(str2);
          break;
        case 6 :
          // VehicleNumber
          str1 = (String) s1.getVehicleNumber();
          str2 = (String) s2.getVehicleNumber();
          result = str1.compareTo(str2);
          break;
        case 7 :
          // Address
          str1 = (String) s1.getAddress();
          str2 = (String) s2.getAddress();
          result = str1.compareTo(str2);
          break;
        case 8 :
          // City
          str1 = (String) s1.getCity();
          str2 = (String) s2.getCity();
          result = str1.compareTo(str2);
          break;
        case 9 :
          // State
          str1 = (String) s1.getState();
          str2 = (String) s2.getState();
          result = str1.compareTo(str2);
          break;
        case 10 :
          // Postal
          str1 = (String) s1.getPostal();
          str2 = (String) s2.getPostal();
          result = str1.compareTo(str2);
          break;
        case 11 :
          // Phone Number
          PhoneNumber ph1 = (PhoneNumber) s1.getPhone();
          PhoneNumber ph2 = (PhoneNumber) s2.getPhone();
          result = ph1.compareTo(ph2);
          break;
        case 12 :
          // Min Time
          d1 = s1.getMinTime().doubleValue();
          d2 = s2.getMinTime().doubleValue();
          result = d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);
          break;
        case 13 :
          // Max Time
          d1 = s1.getMaxTime().doubleValue();
          d2 = s2.getMaxTime().doubleValue();
          result = d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);
          break;
        case 14 :
          // Avg Time
          d1 = s1.getAvgTime().doubleValue();
          d2 = s2.getAvgTime().doubleValue();
          result = d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);
          break;
        case 15 :
          // Std Dev
          d1 = s1.getStdDev().doubleValue();
          d2 = s2.getStdDev().doubleValue();
          result = d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);
          break;
        case 16 :
          // Registered Date
          Date date1 = s1.getDateRegistered();
          Date date2 = s2.getDateRegistered();
          result = date1.compareTo(date2);
          break;
        case 17 :
          // Race Name
          str1 = (String) s1.getRaceName();
          str2 = (String) s2.getRaceName();
          result = str1.compareTo(str2);
          break;
        case 18 :
          // Placement
          int1 = (int) s1.getPlacement().intValue();
          int2 = (int) s2.getPlacement().intValue();
          result = int1 < int2 ? -1 : (int1 > int2 ? 1 : 0);
          break;
        case 19 :
          // Points - Note that the Order is reversed, the Greater the Number the order is sorted.
          int1 = (int) s1.getPoints().intValue();
          int2 = (int) s2.getPoints().intValue();
          result = int1 < int2 ? 1 : (int1 > int2 ? -1 : 0);
          break;
        default :
          break;
      }
    }
    catch (NullPointerException exc)
    { // Swallow Exception.  This will ocurr when the Field Is Not sortable
    }

    if (!sortAsc)
    {
      result = -result;
    }
    return result;
  }
  /**
   * Overridden Method:  
   * @see java.lang.Object#equals(java.lang.Object)
   * @param obj - Object, the cobnject to comapre to.
   * @return boolean - Returns true if the Object is equal otherwise false
   */
  public boolean equals( Object obj )
  {
    if (obj instanceof RacerPersonComparator)
    {
      RacerPersonComparator compObj = (RacerPersonComparator) obj;
      return ((compObj.getSortCol().equals(sortCol)) && (compObj.getSortAsc() == sortAsc));
    }
    return false;
  }

  /**
   * Method getSortAsc.  Returns if we are sorting in Ascending or Descending order
   * @return boolean
   * 
   * @uml.property name="sortAsc"
   */
  public boolean getSortAsc()
  {
    return (sortAsc);
  }

  /**
   * Method getSortCol.  Returns the Sort Column
   * @return RacerPersonFieldName
   * 
   * @uml.property name="sortCol"
   */
  public RacerPersonFieldName getSortCol()
  {
    return (sortCol);
  }

  /**
   * Overridden Method: Generates a hashcode respresentation of the class.  
   * @see java.lang.Object#hashCode()
   * @return int
   */
  public int hashCode()
  {
    int rc = 7;

    rc = rc * 11 + this.sortCol.hashCode();
    rc = rc * 11 + ((this.sortAsc) ? 1 : 0);

    return rc;
  }

}
