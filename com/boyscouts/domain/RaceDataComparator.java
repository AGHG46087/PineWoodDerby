package com.boyscouts.domain;

import java.util.Date;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   RaceDataComparator.java<BR>
 * Type Name:   RaceDataComparator<BR>
 * Description: A Comparator for the RaceData Class, This will allow a Any Column to be sorted.
 * Also the order can be ascending or descending
 */

public class RaceDataComparator implements java.util.Comparator
{

  /**
   * Field <code>sortCol</code> : RaceDataFieldName
   * 
   * @uml.property name="sortCol"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  protected RaceDataFieldName sortCol = RaceDataFieldName.LANE;

  /**
   * Field <code>sortAsc</code> : boolean
   * 
   * @uml.property name="sortAsc"
   */
  protected boolean sortAsc = false;

  /**
   * RaceDataComparator constructor comment.
   */
  public RaceDataComparator()
  {
    super();
  }
  /**
   * Constructor for RaceDataComparator. 
   * @param sortCol - RaceDataFieldName, The Field Name to sort on
   * @param sortAsc - boolean, true for ascending, false for descending.
   */
  public RaceDataComparator( RaceDataFieldName sortCol, boolean sortAsc )
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
    if (!(o1 instanceof RaceData) || !(o2 instanceof RaceData))
    {
      return 0;
    }
    RaceData s1 = (RaceData) o1;
    RaceData s2 = (RaceData) o2;
    int result = 0;
    double d1, d2;
    int int1, int2;
    try
    {
      switch (sortCol.getOrdinal())
      {
        case 0 : // entry
          int1 = (int) s1.getEntryID().intValue();
          int2 = (int) s2.getEntryID().intValue();
          result = int1 < int2 ? -1 : (int1 > int2 ? 1 : 0);
          break;
        case 1 : // ID
          int1 = (int) s1.getId().intValue();
          int2 = (int) s2.getId().intValue();
          result = int1 < int2 ? -1 : (int1 > int2 ? 1 : 0);
          break;
        case 2 : // Vehicle Number
          String str1 = s1.getVehicleNumber();
          String str2 = s2.getVehicleNumber();
          result = str1.compareTo(str2);
          break;
        case 3 : // Round
          int1 = (int) s1.getRound().intValue();
          int2 = (int) s2.getRound().intValue();
          result = int1 < int2 ? -1 : (int1 > int2 ? 1 : 0);
          break;
        case 4 : // Heat
          int1 = (int) s1.getHeat().intValue();
          int2 = (int) s2.getHeat().intValue();
          result = int1 < int2 ? -1 : (int1 > int2 ? 1 : 0);
          break;
        case 5 : // Lane
          int1 = (int) s1.getLane().intValue();
          int2 = (int) s2.getLane().intValue();
          result = int1 < int2 ? -1 : (int1 > int2 ? 1 : 0);
          break;
        case 6 : // time
          d1 = s1.getTime().doubleValue();
          d2 = s2.getTime().doubleValue();
          result = d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);
          break;
        case 7 : // Placement
          int1 = (int) s1.getPlacement().intValue();
          int2 = (int) s2.getPlacement().intValue();
          result = int1 < int2 ? -1 : (int1 > int2 ? 1 : 0);
          break;
        case 8 : // date
          Date date1 = s1.getDate();
          Date date2 = s2.getDate();
          result = date1.compareTo(date2);
          break;
        default :
          break;
      }
    }
    catch (NullPointerException exc)
    { // Swallow Exception - Field Not sortable
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
    if (obj instanceof RaceDataComparator)
    {
      RaceDataComparator compObj = (RaceDataComparator) obj;
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
   * @return RaceDataFieldName
   * 
   * @uml.property name="sortCol"
   */
  public RaceDataFieldName getSortCol()
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
