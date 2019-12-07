/*
 * @author:			Owner
 * date:				Dec 9, 2003
 * Package:			com.boyscouts.database
 * File Name:		DBResults.java
 */
package com.boyscouts.database;

import java.util.Vector;

/**
 * author:      hgrein<BR>
 * date:        Jun 3, 2004<BR>
 * Package:     com.boyscouts.database<BR>
 * File Name:   DBResults.java<BR>
 * Type Name:   DBResults<BR>
 * Description: Class container of Vectors maintaing data about a Result Set returned from a Databse.
 * The Data provided is a Vector of Strings for the column headers.  
 * And a Vector of Vectors of Data Objects. for each cell.
 * The Outer Vecotr Represents each Row in the Data base, in the inner Vector represents
 * each Column in a Row.
 */

public class DBResults
{

  /**
   * Field <code>columnHeaders</code> : Vector
   * 
   * @uml.property name="columnHeaders"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="java.lang.String"
   */
  private Vector columnHeaders = null;

  /**
   * Field <code>rowData</code> : Vector
   * 
   * @uml.property name="rowData"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private Vector rowData = null;

  /**
   * Method padChars.  Pads the char specified for a the length specified and returns a string.
   * @param ch - char, the character to pad the string with.
   * @param length - int, How many of the specified chars.
   * @return String, the result.
   */
  private static String padChars( char ch, int length )
  {
    String temp = "";
    for (int i = 0; i < length; i++)
    {
      temp += ch;
    }
    return temp;
  }
  /**
   * Method padString.  Pads a String with Whitespace and ensures the 
   * string has a maximum length specified by the length parameter.
   * @param inValue - String, the String to be padded and truncated to the length
   * @param length - int, the max length the string should be.
   * @return String, the resultant string.
   */
  private static String padString( String inValue, int length )
  {
    String temp = "";
    if (inValue != null)
    {
      temp += inValue;
    }
    temp += padChars(' ', length);
    temp = temp.substring(0, length);
    return temp;
  }

  /**
   * Constructor for DBResults. 
   * 
   */
  public DBResults()
  {
    this.columnHeaders = new Vector();
    this.rowData = new Vector();
  }
  /**
   * Constructor for DBResults. 
   * @param columnHeaders - Vector, the Columns Headers
   * @param rowData - Vector, the Vector of Vector of Object.
   */
  public DBResults( Vector columnHeaders, Vector rowData )
  {
    this.columnHeaders = columnHeaders;
    this.rowData = rowData;
  }
  /**
   * Method destroy.  Clears all the Vectors
   */
  public void destroy()
  {
    this.columnHeaders.clear();
    this.rowData.clear();
    this.columnHeaders = null;
    this.rowData = null;

  }
  /**
   * Method dump. Dumps the Results.
   */
  public void dump()
  {
    final int PAD_LENGTH = 20;
    System.out.println(this.toString());
    System.out.println("DBREsults :  Results ");
    if (columnHeaders == null || rowData == null)
    {
      System.out.println("Bad Result Set: headers=[" + columnHeaders + "], racers=[" + rowData + "]");
      return;
    }
    for (int i = 0; i < columnHeaders.size(); i++)
    {
      Object obj = columnHeaders.elementAt(i);
      String temp = "";
      if (obj instanceof String)
      {
        temp = (String) obj;
      }
      else
      {
        temp = "" + obj;
      }
      if (i < columnHeaders.size() - 1)
      {
        temp += ',';
      }
      System.out.print(padString(temp, PAD_LENGTH));
    }
    System.out.println("\n" + padChars('-', (columnHeaders.size() + 1) * PAD_LENGTH));
    int rowSize = rowData.size();
    for (int i = 0; i < rowSize; i++)
    {
      Vector data = (Vector) rowData.elementAt(i);
      for (int j = 0; j < data.size(); j++)
      {
        String temp = "" + data.elementAt(j);
        if (j < data.size() - 1)
        {
          temp += ',';
        }
        System.out.print(padString(temp, PAD_LENGTH));
      }
      System.out.println();
    }
    System.out.println("\n" + padChars('-', (columnHeaders.size() + 1) * PAD_LENGTH));
    System.out.println(rowSize + " rows in container");

  }

  /**
   * Method getColumnHeaders.  Returns the columnHeaders
   * @return Vector - of Strings.
   * 
   * @uml.property name="columnHeaders"
   */
  public Vector getColumnHeaders()
  {
    return columnHeaders;
  }

  /**
   * Method getRowData.  Returns the rowData 
   * @return Vector - This is a Vector of Vector of Objects.
   * 
   * @uml.property name="rowData"
   */
  public Vector getRowData()
  {
    return rowData;
  }

  /**
   * Method setColumnHeaders.  The columnHeaders to set.
   * @param columnHeaders - Vector, of Strings
   * 
   * @uml.property name="columnHeaders"
   */
  public void setColumnHeaders( Vector columnHeaders )
  {
    this.columnHeaders = columnHeaders;
  }

  /**
   * Method setRowData.  The rowData to set.
   * @param rowData - Vector, This is a Vector of Vectro of Objects.
   * 
   * @uml.property name="rowData"
   */
  public void setRowData( Vector rowData )
  {
    this.rowData = rowData;
  }

}