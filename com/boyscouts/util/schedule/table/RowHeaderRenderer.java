/*
 * @author:		Owner
 * date:		Jan 2, 2004
 * Package:		com.boyscouts.util.schedule.table
 * File Name:		RowHeaderRenderer.java
 */
package com.boyscouts.util.schedule.table;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

/**
 * author:      hgrein<BR>
 * date:        Jun 7, 2004<BR>
 * Package:     com.boyscouts.util.schedule.table<BR>
 * File Name:   RowHeaderRenderer.java<BR>
 * Type Name:   RowHeaderRenderer<BR>
 * Description: A JLabel Used as a Cell Renderer, Package Scope Only.  
 * This class is to be used in conjunction with the RowHeaderListModel.
 * It is a highly specific design geared towards the Race a Rama application.
 * This renderer will be a single cell in a List.
 */
class RowHeaderRenderer extends JLabel implements ListCellRenderer
{

  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3978988782030829879L;

  /**
   * Constructor for RowHeaderRenderer. 
   * @param table - JTable , the table associated with this list.
   */
  RowHeaderRenderer( JTable table )
  {
    JTableHeader header = table.getTableHeader();
    setOpaque(true);
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(JLabel.CENTER);
    setForeground(header.getForeground());
    setBackground(header.getBackground());
    setFont(header.getFont());
  }

  /**
   * Overridden Method:  
   * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
   * @param list - JList, the List of data.
   * @param value - Object, the value of the object.
   * @param index - int, the index of item
   * @param isSelected - boolean, is selected by the user.
   * @param cellHasFocus - boolean, has focus.
   * @return Component, the component used to renderer the cell.
   */
  public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus )
  {
    setText((value == null) ? "" : value.toString());
    return this;
  }
}