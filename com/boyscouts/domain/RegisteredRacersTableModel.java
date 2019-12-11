package com.boyscouts.domain;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.hgtable.ColumnDataCellValue;
import com.hgtable.HGTableModel;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   RegisteredRacersTableModel.java<BR>
 * Type Name:   RegisteredRacersTableModel<BR>
 * Description: Class Object to manage Racers in a Table. This model does contain 
 * specifics in reference to the Class Object <B>RacerPerson</B>
 */

public class RegisteredRacersTableModel extends HGTableModel
{

  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3761970475458572344L;
  /**
   * Field <code>localSortCol</code> : RacerPersonFieldName
   * 
   * @uml.property name="localSortCol"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RacerPersonFieldName localSortCol = RacerPersonFieldName.LIST[sortCol];

  /**
   * author:      hgrein<BR>
   * Package:     com.boyscouts.domain<BR>
   * File Name:   RegisteredRacersTableModel.java<BR>
   * Type Name:   ColumnListener<BR>
   * Description: Inner class used to help with the double clicking on the headers.
   */
  public class ColumnListener extends HGTableModel.MainModelColumnListener
  {

    /**
     * Constructor for ColumnListener. 
     * @param table - JTable, the table to monitor
     * @see com.hgtable.HGTableModel.MainModelColumnListener
     */
    public ColumnListener( JTable table )
    {
      super(table);
    }

    /**
     * Overridden Method:  Method sorts any column within the table in either ascending or descending order
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @param e - MouseEvent, the event to handle
     */
    public void mouseClicked( MouseEvent e )
    {
      TableColumnModel colModel = table.getColumnModel();
      int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
      int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();

      // NOTE: sortCol and sortAsc, come from HGTableModel.this.sortCol and
      //       HGTableModel.this.sortAsc repectively.
      if (modelIndex < 0)
      {
        return;
      }
      if (sortCol == modelIndex)
      {
        sortAsc = !sortAsc;
      }
      else
      {
        sortCol = modelIndex;
      }

      for (int i = 0; i < RegisteredRacersTableModel.this.columnsCount; i++)
      {
        TableColumn column = colModel.getColumn(i);
        column.setHeaderValue(getColumnName(column.getModelIndex()));
        JLabel renderer = (JLabel) column.getHeaderRenderer();
        int index = column.getModelIndex();
        renderer.setIcon(getColumnIcon(index));
      }

      table.getTableHeader().repaint();

      localSortCol = RacerPersonFieldName.LIST[modelIndex];

      Collections.sort(data, new RacerPersonComparator(localSortCol, sortAsc));
      table.tableChanged(new TableModelEvent(RegisteredRacersTableModel.this));
      table.repaint();
    }
  }
  /**
   * Constructor for RegisteredRacersTableModel. 
   * @param racerContainer - RacerContainer, the racer container to process
   */
  public RegisteredRacersTableModel( RacerContainer racerContainer )
  {
    super(racerContainer.getHeaders(), racerContainer.getRacers());

    Collections.sort(data, new RacerPersonComparator(localSortCol, sortAsc));
  }
  /**
   * Constructor for RegisteredRacersTableModel. 
   * @param columnHeaders - Vector, the Vector of Strings for column headers
   * @param theData - Vector of Vector of RacerPersons
   */
  public RegisteredRacersTableModel( Vector columnHeaders, Vector theData )
  {
    super(columnHeaders, theData);

    if (data != null)
    {
      Collections.sort(data, new RacerPersonComparator(localSortCol, sortAsc));
    }
  }
  /**
   * Overridden Method:  Adds any Mouse Listeners to Table
   * @see com.hgtable.ColumnMouseEventListener#addMouseListenersToTable(javax.swing.JTable)
   * @param tableView - JTable, the table to add a mouse listener.
   */
  public void addMouseListenersToTable( JTable tableView )
  {
    MouseListener mouseListener = new ColumnListener(tableView);

    JTableHeader header = tableView.getTableHeader();
    header.setUpdateTableInRealTime(true);
    header.addMouseListener(mouseListener);
    header.setReorderingAllowed(true);

    // Adding Mouse Events to Column Movement
    addColumnModelListener(tableView);
  }
  /**
   * Overridden Method:  Returns the The Object located at Row, Column.
   * If the object implments the <B><I>ColumnDataCellValue</I></B> interface
   * a call to the method, <B>getColumnDataCell( nCol )</B> will be made
   * and return the value, otherwise if the data will attempt to pull 
   * the data as a Vector, If there still is no use it will attempt to
   * just return a empty String object.
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   * @param nRow - int, The Row
   * @param nCol - nCol int, The Column
   * @return Object, The Object at Row, Column
   */
  public Object getValueAt( int nRow, int nCol )
  {
    if (nRow < 0 || nRow >= getRowCount())
    {
      return "";
    }

    Object obj = data.elementAt(nRow);
    if (obj instanceof ColumnDataCellValue)
    {
      return (((ColumnDataCellValue) obj).getColumnDataCell(nCol));
    }
    Object cellData = "";
    if (obj instanceof Vector)
    {
      cellData = ((Vector) obj).elementAt(nCol);
    }
    return cellData;
  }
  /**
   * Overridden Method:  Returns false for all cells
   * @see javax.swing.table.TableModel#isCellEditable(int, int)
   * @param nRow - int, The Row
   * @param nCol - int, the Column
   * @return boolean
   */
  public boolean isCellEditable( int nRow, int nCol )
  {
    boolean rc = false;
    return rc;
  }
  /**
   * Overridden Method:  Method sets the Textual Value of the Data at Row, Col. 
   * If the object in in range of available Rows
   * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
   * @param value - Object The Data to set
   * @param nRow - int, The Row in which we are currently at
   * @param nCol - int, The Column, must be the Symbol column
   */
  public void setValueAt( Object value, int nRow, int nCol )
  {
    if ((0 <= nRow && nRow < getRowCount()) && value != null)
    {
      //String tmpValue = value.toString().toUpperCase();

      // Now enforce the Entire Row, to be updated,
      // We only want to Update the row for Speed sake.
      for (nCol = 0; nCol < getColumnCount(); nCol++)
      {
        fireTableCellUpdated(nRow, nCol);
      }

    }
  }
}
