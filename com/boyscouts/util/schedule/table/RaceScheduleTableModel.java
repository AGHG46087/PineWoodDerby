/*
 * @author:		Owner
 * date:		Jan 2, 2004
 * Package:		com.boyscouts.util.schedule.table
 * File Name:		RaceScheduleTableModel.java
 */
package com.boyscouts.util.schedule.table;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.hgtable.ColumnDataCellTooltip;

/**
 * author:      hgrein<BR>
 * date:        Jun 7, 2004<BR>
 * Package:     com.boyscouts.util.schedule.table<BR>
 * File Name:   RaceScheduleTableModel.java<BR>
 * Type Name:   RaceScheduleTableModel<BR>
 * Description: A Tabel Model to represent the Table Data, Package Scope.
 * This class is to be used in conjunction with the RowHeaderListModel.
 * It is a highly specific design geared towards the Race a Rama application.
 * This renderer will be a single cell in a List.
 */
class RaceScheduleTableModel extends DefaultTableModel
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3977581394228950581L;
  /**
   * Method getToolTipText.  Will retrieve the tooltip for the value of the
   * @param row - int, the row of the table
   * @param col - int, the column of the table
   * @return String, the tooltip of the cell.
   */
  public String getToolTipText( int row, int col )
  {
    String rc = "";
    Vector data = this.getDataVector();
    Vector theRow = (Vector) data.elementAt(row);
    Object obj = theRow.elementAt(col);

    if (obj instanceof ColumnDataCellTooltip)
    {
      rc = ((ColumnDataCellTooltip) obj).getCellToolTip(col);
    }

    return rc;
  }
  /**
   * Constructor for RaceScheduleTableModel. 
   * @param data - Vector of Vector of Objects.
   * @param columnNames - Vector of Strings.
   */
  public RaceScheduleTableModel( Vector data, Vector columnNames )
  {
    super(data, columnNames);
  }

}
