/*
 * @author:		Owner
 * date:		Jan 2, 2004
 * Package:		com.boyscouts.util.schedule.table
 * File Name:		RowHeaderListModel.java
 */
package com.boyscouts.util.schedule.table;

import java.util.Vector;

import javax.swing.AbstractListModel;

/**
 * author:      hgrein<BR>
 * date:        Jun 7, 2004<BR>
 * Package:     com.boyscouts.util.schedule.table<BR>
 * File Name:   RowHeaderListModel.java<BR>
 * Type Name:   RowHeaderListModel<BR>
 * Description: Class Object to maintain the List of the Row Titles, Package Scope Only.
 * This class is to be used in conjunction with the RowHeaderRenderer.
 * It is a highly specific design geared towards the Race a Rama application.
 * This renderer will be a single cell in a List.
 */
class RowHeaderListModel extends AbstractListModel
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3257563988576776502L;
  Vector headers = null;
  public RowHeaderListModel( Vector rowTitles )
  {
    headers = rowTitles;
  }
  /**
   * Overridden Method:  
   * @see javax.swing.ListModel#getSize()
   * @return int, the size of the list.
   */
  public int getSize()
  {
    return headers.size();
  }
  /**
   * Overridden Method:  Returns the cell at the specified index.
   * @see javax.swing.ListModel#getElementAt(int)
   * @param index - int, the index to return.
   * @return Object, the value of the cell.
   */
  public Object getElementAt( int index )
  {
    return headers.elementAt(index);
  }
}
