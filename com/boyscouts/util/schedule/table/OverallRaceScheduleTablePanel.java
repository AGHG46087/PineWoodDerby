/*
 * @author:		Owner
 * Package:		examples.swing.jtable.rowHeader
 * File Name:		OverallRaceScheduleTablePanel.java
 */
package com.boyscouts.util.schedule.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.boyscouts.domain.RacerPerson;
import com.boyscouts.util.schedule.PersonRacerSchedule;
import com.boyscouts.util.schedule.RaceScheduleVector;
import com.hgmenu.HGMenuItem;
import com.hgutil.ParseData;
import com.hgutil.swing.event.PopupListener;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.util.schedule.table<BR>
 * File Name:   OverallRaceScheduleTablePanel.java<BR>
 * Type Name:   OverallRaceScheduleTablePanel<BR>
 * Description: A Panel That Contains, the Table displaying the Overall Race Schedule Data
 */

public class OverallRaceScheduleTablePanel extends JPanel implements Printable
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3977014054869021493L;
  /** Field <code>TITLE_TEXT</code> : String */
  private final String TITLE_TEXT;
  /** Field <code>PRINT_LIST_CMD</code> : String */
  private static final String PRINT_LIST_CMD = "Print Table";
  /** Field <code>PRINT_PREVIEW_CMD</code> : String */
  private static final String PRINT_PREVIEW_CMD = "Print Preview";
  /** Field <code>maxNumPage</code> : int */
  private int maxNumPage = 1;

  /**
   * Field <code>title</code> : JLabel
   * 
   * @uml.property name="title"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JLabel title = new JLabel("");

  /**
   * Field <code>table</code> : RaceScheduleTable
   * 
   * @uml.property name="table"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RaceScheduleTable table;

  /**
   * Field <code>theData</code> : Vector
   * 
   * @uml.property name="theData"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private Vector theData = null;

  /**
   * Field <code>popupListener</code> : PopupListener
   * 
   * @uml.property name="popupListener"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private PopupListener popupListener = null;

  /**
   * author:      Hans-Jurgen Greiner<BR>
   * Package:     com.boyscouts.util.schedule.table<BR>
   * File Name:   OverallRaceScheduleTablePanel.java<BR>
   * Type Name:   PopupTrigger<BR>
   * Description: A Trigger to manage the Popup menu on the Master table
   */
  private class PopupTrigger implements ActionListener
  {
    /**
     * Overridden Method:  
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param evt
     */
    public void actionPerformed( ActionEvent evt )
    {
      String cmd = evt.getActionCommand();
      if (cmd.equals(PRINT_LIST_CMD))
      {
        printData();
      }
      else if (cmd.equals(PRINT_PREVIEW_CMD))
      {
        printPreview();
      }
      else
      {
        JOptionPane.showMessageDialog(OverallRaceScheduleTablePanel.this, "PopupCmd: [" + cmd + "] option not supported at this time", "information",
                                      JOptionPane.INFORMATION_MESSAGE);
      }
    }

  }
  /**
   * Constructor for OverallRaceScheduleTablePanel. 
   * @param rowTitles - Vector, List of Row Titles
   * @param colTitles - Vector, List of Column Titles
   * @param data - RaceScheduleVector, Vector of Vectors, List containing PersonRacerSchedule Objects
   */
  public OverallRaceScheduleTablePanel( final Vector rowTitles, final Vector colTitles, RaceScheduleVector data )
  {
    this.theData = data.getDataAsVectorOfVector();
    RaceScheduleTableModel dm = new RaceScheduleTableModel(this.theData, colTitles);
    this.table = new RaceScheduleTable(dm);

    String dateInfo = ParseData.format(new java.util.Date(), "yyyyMMdd_hhmmss");
    String raceName = extractRaceName(this.theData);
    TITLE_TEXT = "Overall Race Schedule_" + dateInfo + "_" + raceName;

    ListModel lm = new RowHeaderListModel(rowTitles);
    JList rowHeader = new JList(lm);
    rowHeader.setFixedCellWidth(70);
    rowHeader.setFixedCellHeight(table.getRowHeight());
    rowHeader.setCellRenderer(new RowHeaderRenderer(table));

    JScrollPane scroll = new JScrollPane(table);
    scroll.setRowHeaderView(rowHeader);

    this.title.setText(TITLE_TEXT);
    this.addPopupMenuToTable();
    this.setLayout(new BorderLayout());
    this.add(scroll, BorderLayout.CENTER);
  }
  /**
   * Method extractRaceName.  This method will extract the Race Name, If a Race Name is not setup as of yet.
   * then it will default to NO_RACE_NAME
   * @param theRacerData - Vector, the Data container.
   * @return String
   */
  private static String extractRaceName( Vector theRacerData )
  {
    String raceName = "NO_RACE_NAME";
    try
    { // There is a potential that the users did not setup racers.  So we have a potential
      // for a Index out of bounds or NullPointerException. If either ocurrs, we have a Default Value
      raceName = ((PersonRacerSchedule)((Vector)theRacerData.elementAt(0)).elementAt(0)).getRacer().getRaceName();
    }
    catch( Throwable exc )
    { // Do Nothing,  
    }
    return raceName;
  }
  /**
   * Method addPopupMenuToTable.  Method to add a popup menu to the JTable
   */
  private void addPopupMenuToTable()
  {

    if (popupListener == null)
    {
      popupListener = new PopupListener(createPopupMenu());
    }

    table.addMouseListener(popupListener);
  }
  /**
   * Method createPopupMenu.  Creates a popup edit menu for the table.
   * @return JPopupMenu, the popup menu.
   */
  private JPopupMenu createPopupMenu()
  {
    // Add all the Horizontal elements
    JPopupMenu result = null;
    ImageIcon ii = null;

    PopupTrigger popupTrigger = new PopupTrigger();

    ii = new ImageIcon(PrintPreview.class.getResource("/resources/images/paper.gif"));
    JMenuItem printPreview = HGMenuItem.makeMenuItem(PRINT_PREVIEW_CMD, PRINT_PREVIEW_CMD, popupTrigger);
    printPreview.setIcon(ii);

    ii = new ImageIcon(PrintPreview.class.getResource("/resources/images/printer.gif"));
    JMenuItem printTable = HGMenuItem.makeMenuItem(PRINT_LIST_CMD, PRINT_LIST_CMD, popupTrigger);
    printTable.setIcon(ii);

    result = HGMenuItem.makePopupMenu(new Object[]{printPreview, printTable}, null);
    // Set the Alignment and return the MenuBar
    result.setAlignmentX(JMenuBar.LEFT_ALIGNMENT);
    return result;
  }

  /**
   * Method printData.  Utility method to invoke the print() method.
   * @see com.boyscouts.util.schedule.table.OverallRaceScheduleTablePanel#print(java.awt.Graphics, java.awt.print.PageFormat, int)
   */
  public void printData()
  {
    try
    {
      PrinterJob prnJob = PrinterJob.getPrinterJob();
      prnJob.setPrintable(this);
      if (!prnJob.printDialog())
      {
        return;
      }
      maxNumPage = 1;
      prnJob.print();
    }
    catch (PrinterException e)
    {
      e.printStackTrace();
    }
  }
  /**
   * Method printPreview.  Displays a printPreview in a Frame
   * 
   */
  private void printPreview()
  {
    /**
     * @author:     Hans-Jurgen Greiner<BR>
     * Package:     com.boyscouts.util.schedule.table<BR>
     * File Name:   OverallRaceScheduleTablePanel.java<BR>
     * Type Name:   DisplayPrintPreview<BR>
     * Description: A Task to display the PrintPreview Frame
     */
    class DisplayPrintPreview extends TimerTask
    {
      /**
       * Overridden Method:  
       * @see java.lang.Runnable#run()
       * 
       */
      public void run()
      {
        this.cancel(); // Ensure we kill this thread
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        new PrintPreview(OverallRaceScheduleTablePanel.this, title.getText() + " preview", PageFormat.LANDSCAPE);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      }
    }
    Timer t = new Timer();
    t.schedule(new DisplayPrintPreview(), 100, 100);
  }
  /**
   * Method format.  Formats the Value passes as a parameter
   * @param value - int, the Value to be formated
   * @return String.
   */
  private String format( int value )
  {
    DecimalFormat df = new DecimalFormat("#00");
    return df.format(value);
  }
  /**
   * Overridden Method:  The Method will generate a print object and place the image 
   * in the Graphics object.  This is used by supporting classes to send to the printer.
   * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
   * @param pg - Graphics, The Page Graphics object
   * @param pageFormat - PageFormat, the Format of the Page, Landscape or Portrait
   * @param pageIndex - int, The Index of the Page to print.
   * @return int, more pages or NO_SUCH_PAGE
   * @throws PrinterException - In the case of error.
   */
  public int print( Graphics pg, PageFormat pageFormat, int pageIndex ) throws PrinterException
  {
    if (pageIndex >= maxNumPage)
      return NO_SUCH_PAGE;

    pg.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
    int wPage = 0;
    int hPage = 0;
    if (pageFormat.getOrientation() == PageFormat.PORTRAIT)
    {
      wPage = (int) pageFormat.getImageableWidth();
      hPage = (int) pageFormat.getImageableHeight();
    }
    else
    {
      wPage = (int) pageFormat.getImageableWidth();
      wPage += wPage / 2;
      hPage = (int) pageFormat.getImageableHeight();
      pg.setClip(0, 0, wPage, hPage);
    }

    int y = 0;
    pg.setFont(title.getFont());
    pg.setColor(Color.black);
    Font fn = pg.getFont();
    FontMetrics fm = pg.getFontMetrics();
    y += fm.getAscent();
    pg.drawString(title.getText(), 0, y);
    y += 20; // space between title and table headers

    Font headerFont = table.getFont().deriveFont(Font.BOLD);
    pg.setFont(headerFont);
    fm = pg.getFontMetrics();

    TableColumnModel colModel = table.getColumnModel();
    int nColumns = colModel.getColumnCount();
    int x[] = new int[nColumns];
    int heatPos = 0;
    x[0] = 40;

    int h = fm.getAscent();
    y += h; // add ascent of header font because of baseline

    pg.drawString("Heat#", heatPos, y); // Draw the Heat# Column Header
    int nRow, nCol;

    for (nCol = 0; nCol < nColumns; nCol++)
    {
      TableColumn tk = colModel.getColumn(nCol);
      int width = 50; //tk.getWidth();
      if (x[nCol] + width > wPage)
      {
        nColumns = nCol;
        break;
      }
      if (nCol + 1 < nColumns)
      {
        x[nCol + 1] = x[nCol] + width;
      }
      String temp = (String) tk.getIdentifier();
      String colHeader = "Ln" + temp.substring(temp.lastIndexOf("#") + 1); //
      //colHeader = "Ln " + format(nCol + 1);
      pg.drawString(colHeader, x[nCol], y); // Draw the Lane # Column Header 
    }

    pg.setFont(table.getFont());
    fm = pg.getFontMetrics();

    int header = y;
    h = fm.getHeight();
    int rowH = Math.max((int) (h * 1.5), 10);
    int rowPerPage = (hPage - header) / rowH;
    maxNumPage = Math.max((int) Math.ceil(table.getRowCount() / (double) rowPerPage), 1);

    TableModel tblModel = table.getModel();
    int iniRow = pageIndex * rowPerPage;
    int endRow = Math.min(table.getRowCount(), iniRow + rowPerPage);

    for (nRow = iniRow; nRow < endRow; nRow++)
    {
      y += h;
      Vector theRow = (Vector) theData.elementAt(nRow);
      Color rowColor = (nRow % 2 == 0) ? Color.BLUE : Color.RED;
      pg.setColor(rowColor);
      pg.drawString(format(1 + nRow), heatPos, y); // Draw the Heat Number Text
      for (nCol = 0; nCol < nColumns; nCol++)
      {
        int col = table.getColumnModel().getColumn(nCol).getModelIndex();
        PersonRacerSchedule obj = (PersonRacerSchedule) table.getValueAt(nRow, col);
        RacerPerson rp = obj.getRacer();
        String str = "  " + rp.getVehicleNumber();
        pg.drawString(str, x[nCol], y);
      }
    }

    System.gc();
    return PAGE_EXISTS;
  }
}
