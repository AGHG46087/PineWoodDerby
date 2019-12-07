package com.boyscouts.publish.results;

import com.boyscouts.domain.RacerPerson;

/**
 * author:		 	Owner
 * date:		    Dec 9, 2003
 * Package:			com.boyscouts.domain
 * File Name:		XMLPublishComparator.java
 * Type Name:		XMLPublishComparator
 * Description:	A Comparator for the Registered Person Class. This will allow a A Column to be sorted.
 *              Also the order can be ascending or descending
 * @see com.boyscouts.domain.RacerPersonComparator
 */

class XMLPublishComparator implements java.util.Comparator
{

  /**
   * Field <code>sortCol</code> : WebPublishRacerSortField
   * 
   * @uml.property name="sortCol"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  protected WebPublishRacerSortField sortCol = WebPublishRacerSortField.LNAME_FNAME;

  /**
   * Field <code>sortAsc</code> : boolean
   * 
   * @uml.property name="sortAsc"
   */
  protected boolean sortAsc;

  /**
   * XMLPublishComparator constructor comment.
   */
  public XMLPublishComparator()
  {
    super();
  }
  /**
   * Constructor for XMLPublishComparator. 
   * @param sortCol - WebPublishRacerSortField, the Field to Sort.
   * @param sortAsc - boolean, true for ascending, false for descending.
   */
  public XMLPublishComparator( WebPublishRacerSortField sortCol, boolean sortAsc )
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
    int int1, int2;
    String str1, str2;
    switch (sortCol.getOrdinal())
    {
      case 0 : // LastName - FirstName
        str1 = (String) s1.getLastName() + ", " + (String) s1.getFirstName();
        str2 = (String) s2.getLastName() + ", " + (String) s2.getFirstName();
        result = str1.compareTo(str2);
        break;
      case 1 : // Placement
        int1 = (int) s1.getPoints().intValue();
        int2 = (int) s2.getPoints().intValue();
        result = int1 < int2 ? 1 : (int1 > int2 ? -1 : 0);
        break;
      case 2 : // Pack - LastName - First Name
        str1 = (String) s1.getPack() + ", " + (String) s1.getLastName() + ", " + (String) s1.getFirstName();
        str2 = (String) s2.getPack() + ", " + (String) s2.getLastName() + ", " + (String) s2.getFirstName();
        result = str1.compareTo(str2);
        break;
      case 3 : // Den - LastName - First Name
        str1 = (String) s1.getDen() + ", " + (String) s1.getLastName() + ", " + (String) s1.getFirstName();
        str2 = (String) s2.getDen() + ", " + (String) s2.getLastName() + ", " + (String) s2.getFirstName();
        result = str1.compareTo(str2);
        break;
      default :
        break;
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
    if (obj instanceof XMLPublishComparator)
    {
      XMLPublishComparator compObj = (XMLPublishComparator) obj;
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
   * @return WebPublishRacerSortField
   * 
   * @uml.property name="sortCol"
   */
  public WebPublishRacerSortField getSortCol()
  {
    return (sortCol);
  }

}