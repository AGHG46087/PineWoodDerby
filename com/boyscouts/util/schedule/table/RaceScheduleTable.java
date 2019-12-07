/*
 * @author:		Owner
 * date:		Jan 2, 2004
 * Package:		com.boyscouts.util.schedule.table
 * File Name:		RaceScheduleTable.java
 */
package com.boyscouts.util.schedule.table;

import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * author:      hgrein<BR>
 * date:        Jun 7, 2004<BR>
 * Package:     com.boyscouts.util.schedule.table<BR>
 * File Name:   RaceScheduleTable.java<BR>
 * Type Name:   RaceScheduleTable<BR>
 * Description: A Table to Represent the Data in a Tabular form, Package Scope.
 * This class is to be used in conjunction with the RowHeaderRenderer.
 * It is a highly specific design geared towards the Race a Rama application.
 * This renderer will be a single cell in a List.
 */
class RaceScheduleTable extends JTable
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3258131358017532978L;
  /**
   * Constructor for RaceScheduleTable. 
   * @param dm - TableModel, the model to be used for the table
   */
  public RaceScheduleTable( TableModel dm )
  {
    super(dm);
    this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    setupRenderer(dm);
  }
  /**
   * Method setupRenderer.  Sets the intial renderer for the model being used.
   * @param dm - TableModel, the model to be used for the table
   */
  private void setupRenderer( TableModel dm )
  {
    int cols = dm.getColumnCount();
    for (int headerIndex = 0; headerIndex < cols; headerIndex++)
    {
      DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

      int alignment = JLabel.CENTER;
      int defaultWidth = 100;

      renderer.setHorizontalAlignment(alignment);
      this.setDefaultRenderer(dm.getColumnClass(headerIndex), renderer);
    }
  }
  /**
   * Overridden Method:  Method to get the ToolTip from the Model. This will attempt to get the
   * Tooltip from the table first.  If the Model is a instanceof HGTableModel
   * The method will atempt to get the Tooltip from there.  If the Tooltip is 
   * null or empty it will return the tooltip from the table.
   * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
   * @see com.hgtable.HGTableModel#getToolTipText(int, int)
   * @param event - MouseEvent, The Mouse Event To handle
   * @return String, The Tool Tip
   */
  public String getToolTipText( MouseEvent event )
  {
    String rc = super.getToolTipText(event);
    TableModel dm = this.getModel();
    if (dm instanceof RaceScheduleTableModel)
    {
      TableColumnModel colModel = this.getColumnModel();
      int columnModelIndex = colModel.getColumnIndexAtX(event.getX());
      int col = colModel.getColumn(columnModelIndex).getModelIndex();
      int row = rowAtPoint(event.getPoint());
      String tempRc = ((RaceScheduleTableModel) dm).getToolTipText(row, col);
      rc = (tempRc != null && tempRc.length() > 1) ? tempRc : rc;
    }
    return rc;
  }

}
