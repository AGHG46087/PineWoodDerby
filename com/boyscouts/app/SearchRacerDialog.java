/*
 * @author:		Owner
 * Package:		com.boyscouts.app
 * File Name:		SearchRacerDialog.java
 */
package com.boyscouts.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.boyscouts.database.DBResults;
import com.boyscouts.database.RaceARamaDB;
import com.boyscouts.database.exception.DBAccessException;
import com.boyscouts.domain.FieldLengths;
import com.boyscouts.domain.Log;
import com.boyscouts.domain.RacerContainer;
import com.boyscouts.domain.RacerPerson;
import com.boyscouts.domain.RacerPersonFieldName;
import com.boyscouts.domain.RegisteredRacersTableModel;
import com.hgtable.HGTable;
import com.hgutil.swing.GridBagHelper;
import com.hgutil.swing.event.PopupListener;

/**
 * author:     Owner
 * Package:     com.boyscouts.app
 * File Name:   SearchRacerDialog.java
 * Type Name:   SearchRacerDialog
 * Description: Displayed the Search For Items.  
 * A User can select o 1 or many racers in any order before pressing the submit button.
 */

public class SearchRacerDialog extends JDialog
{

  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3907212658607730738L;

  /**
   * Field <code>mainApp</code> : RaceARama
   * 
   * @uml.property name="mainApp"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RaceARama mainApp = null;

  /**
   * Field <code>selectedRacers</code> : RacerContainer
   * 
   * @uml.property name="selectedRacers"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private RacerContainer selectedRacers = null;

  /**
   * Field <code>popupListener</code> : PopupListener
   * 
   * @uml.property name="popupListener"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private PopupListener popupListener = null;

  /**
   * Field <code>resultTable</code> : HGTable
   * 
   * @uml.property name="resultTable"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private HGTable resultTable = null;

  /** Field <code>headerVector</code> : Vector */
  private Vector headerVector = null;
  /** Field <code>dataVector</code> : Vector */
  private Vector dataVector = null;
  /** Field <code>SUBMIT</code> : String */
  private static final String SUBMIT = "SUBMIT";
  /** Field <code>CANCEL</code> : String */
  private static final String CANCEL = "CANCEL";
  /** Field <code>value</code> : String */
  private String value = null;

  /**
   * Field <code>field</code> : RacerPersonFieldName
   * 
   * @uml.property name="field"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RacerPersonFieldName field = null;

  /**
   * author:      Owner
   * Package:     com.boyscouts.app
   * File Name:   NewRacerDialog.java
   * Type Name:   ActionTrigger
   * Description: Button Listener
   */
  private class ActionTrigger implements ActionListener
  {
    /**
     * Overridden Method:  
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param evt
     */
    public void actionPerformed( ActionEvent evt )
    {
      String cmd = evt.getActionCommand();
      boolean readyToClose = false;

      if (cmd.equals(SUBMIT))
      {
        readyToClose = true;
        selectedRacers = createContainerOfSelectedRacers();
      }
      else if (cmd.equals(CANCEL))
      {
        readyToClose = true;
      }
      if (readyToClose)
      {
        if (mainApp == null && selectedRacers != null)
        {
          for (int i = 0; i < selectedRacers.getSize(); i++)
          {
            System.out.println("index[" + i + "] = " + selectedRacers.elementAt(i));
          }
          System.exit(0);
        }
        else
        {
          SearchRacerDialog.this.dispose();
        }
      }
    }
  }
  /**
   * Constructor for SearchRacerDialog.   Creates a Search Racers Dialog, 
   * from which the results of the query are slected
   * @param frame The JFrame instance, could be a default Frame, or a RaceARama frame
   * @param value - String, the String to be searching.
   * @param field - RacerPersonFieldName, the Field to use for the search
   */
  public SearchRacerDialog( JFrame frame, String value, RacerPersonFieldName field )
  {
    super(frame, true);
    if (frame instanceof RaceARama)
    {
      mainApp = (RaceARama) frame;
    }
    this.value = value;
    this.field = field;
    try
    {
      DBResults results = RaceARamaDB.searchForRacers(value, field);
      SearchRacerDialog.this.headerVector = results.getColumnHeaders();
      SearchRacerDialog.this.dataVector = results.getRowData();
      SearchRacerDialog.this.init();
    }
    catch (DBAccessException exc)
    {
      String msg = "Search for [], by Field=[], DBAccessException: " + exc.getMessage();
      Log.logError(msg);
      JOptionPane.showMessageDialog(frame, msg, "SEARCH FAILED", JOptionPane.ERROR_MESSAGE);
    }
  }
  /**
   * Method addPopupMenuToTable.  Method to add a popup menu to the JTable
   * @param table - A JTable The Display table used for graphical decisions
   */
  private void addPopupMenuToTable( HGTable table )
  {

    if (popupListener == null)
    {
      popupListener = new PopupListener();
    }

    table.addMouseListener(popupListener);
  }
  /**
   * Method centerOnScreen.  Method will center the Dialog Box on the Screen
   */
  private void centerOnScreen()
  {
    // First Set the Location to be centered on the Screen
    Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();

    Rectangle screenRect = new Rectangle(0, 0, screenDim.width, screenDim.height);

    // Make sure we don't place the demo off the screen.
    int centerWidth = screenRect.width < this.getSize().width ? screenRect.x : screenRect.x + screenRect.width / 2 - this.getSize().width / 2;
    int centerHeight = screenRect.height < this.getSize().height ? screenRect.y : screenRect.y + screenRect.height / 2 - this.getSize().height / 2;

    this.setLocation(centerWidth, centerHeight);
  }
  /**
   * Method createContainerOfSelectedRacers.  Creates a RacerContainer, of all the Selected Racers.
   * NOTE:  If this method is called, and none of the row is not selected "Visually". the attempt will 
   * use the PopupListener to look for a Point in the Table, if there is a Row at that point, it will use
   * that row.  If Still No Row available, it will assume the First Row. Row 0.  After all the rows have 
   * been determined it will iterate through the indexes setting the selected row interval to the specified row index.
   * and invoke the method createRacerPersonObjectFromSelectedRow().  Followed by returning the Container.
   * @return RacerContainer - the Racer Container with all the racers selected in the Table.
   */
  private RacerContainer createContainerOfSelectedRacers()
  {
    RacerContainer theSelectedRacers = new RacerContainer();

    int[] selectedRows = resultTable.getSelectedRows();
    int singleRow = 0;
    if (selectedRows.length < 1)
    {
      int row = resultTable.rowAtPoint(new java.awt.Point(popupListener.getX(), popupListener.getY()));
      row = (row < 0) ? ((resultTable.getSelectedRow() >= 0) ? resultTable.getSelectedRow() : 0) : row;
      selectedRows = new int[1];
      selectedRows[0] = row;
    }

    for (int i = 0; i < selectedRows.length; i++)
    {
      singleRow = selectedRows[i];
      resultTable.setRowSelectionInterval(singleRow, singleRow);
      theSelectedRacers.addRacerPerson(createRacerPersonObjectFromSelectedRow());
    }

    return theSelectedRacers;
  }
  /**
   * Method createRacerPersonObjectFromSelectedRow.  Creates a Racer Person Object from a Selected Row.
   * @return RacerPerson - the Racer Person determined to be selected
   */
  private RacerPerson createRacerPersonObjectFromSelectedRow()
  {
    TableModel dataModel = (TableModel) resultTable.getModel();
    int cols = dataModel.getColumnCount();
    int row = resultTable.getSelectedRow();

    Vector v = new Vector();
    for (int column = 0; column < cols; column++)
    {
      v.add(dataModel.getValueAt(row, column));
    }
    RacerPerson rp = new RacerPerson(v);
    // If the ID checks as being not a valid Number, return Null;
    if (rp.getIdAsInt() == -1)
    {
      rp = null;
    }
    return rp;
  }
  /**
   * Method createScrollTable.  Creates a Scroll Pane containing a Table of the Searched Items.
   * The method starts by creating a RegisteredRacersTableModel of the search Results. which is 
   * then populated into a HGTable.  The Table selection Model is 2 ==> 
   * ListSelectionModel.MULTIPLE_INTERVAL_SELECTION. Row selection is allowed and Column selection 
   * is disabled. in addition the setting of the fields to minimum width is provided.  After which a few 
   * columns are removed.   The ID column, the Min Value, Max Value, Avg Value, Std Dev, 
   * @return JScrollPane
   */
  private JScrollPane createScrollTable()
  {
    // Create the Table Model and Table for the Search Results container.
    // RegisteredRacersTableModel tableModel = new RegisteredRacersTableModel(headerVector, dataVector);
    RegisteredRacersTableModel tableModel = new RegisteredRacersTableModel(getSearchResultsContainer());
    resultTable = new HGTable(tableModel);
    resultTable.setSelectionMode(2); // Multiple Row Selection
    resultTable.setRowSelectionAllowed(true);
    resultTable.setColumnSelectionAllowed(false);
    resultTable.setMinWidthToText(true);
    addPopupMenuToTable(resultTable);
    // Begin Removal of Columns not desired to be seen.
    TableColumnModel tableColumnModel = resultTable.getColumnModel();
    TableColumn tableColumn = null;
    String temp = resultTable.getColumnName(0);
    tableColumn = resultTable.getColumn(temp);
    tableColumnModel.removeColumn(tableColumn);

    tableColumn = resultTable.getColumn(RacerPersonFieldName.MIN_VALUE.toString());
    tableColumnModel.removeColumn(tableColumn);

    tableColumn = resultTable.getColumn(RacerPersonFieldName.MAX_VALUE.toString());
    tableColumnModel.removeColumn(tableColumn);

    tableColumn = resultTable.getColumn(RacerPersonFieldName.AVG_VALUE.toString());
    tableColumnModel.removeColumn(tableColumn);

    tableColumn = resultTable.getColumn(RacerPersonFieldName.STD_DEV.toString());
    tableColumnModel.removeColumn(tableColumn);

    resultTable.tableChanged(new TableModelEvent(tableModel));
    resultTable.repaint();
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.getViewport().add(resultTable);
    scrollPane.setPreferredSize(FieldLengths.HUGE_FIELD);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));
    return scrollPane;
  }
  /**
   * Method getSearchResultsContainer.  Creates a Racer Container of all the records
   * found in the Database Search. 
   * @return RacerContainer - The reults of the search.
   */
  private RacerContainer getSearchResultsContainer()
  {
    RacerContainer theSearchedRacers = new RacerContainer(dataVector);
    return theSearchedRacers;
  }

  /**
   * Method getSelectedRacers.  Returns selectedRacers of the NewRacerDialog
   * @return RacerContainer: Returns the selectedRacers.
   * 
   * @uml.property name="selectedRacers"
   */
  public RacerContainer getSelectedRacers()
  {
    return selectedRacers;
  }

  /**
   * Method init.  Initializes the Search Panel, to display the Table of Searched Items.
   */
  private void init()
  {
    setTitle("New Racer Person");
    setSize(500, 300);
    setResizable(false);
    centerOnScreen();

    // Creates the helper class panel to hold all my components
    GridBagHelper panel = new GridBagHelper();
    // give the panel a border gap of 5 pixels
    panel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
    getContentPane().add(BorderLayout.CENTER, panel);

    final int COL_SPAN_6 = 6;
    final int ROW_SPAN_6 = 6;
    int row = 1;
    int col = 1;

    // District label and field - Note We are using Rows 1 
    JLabel lbl1 = new JLabel("Search for [" + value + "], in Field [" + field + "], records found [" + dataVector.size() + "]");
    panel.addComponent(lbl1, row, col); // row=1, col=1
    JScrollPane scrollPane = createScrollTable();
    panel.addFilledComponent(scrollPane, ++row, col, COL_SPAN_6, ROW_SPAN_6, GridBagConstraints.BOTH);

    ActionTrigger actionTrigger = new ActionTrigger();
    JButton submitBtn = new JButton("Submit");
    submitBtn.setActionCommand(SUBMIT);
    submitBtn.addActionListener(actionTrigger);
    panel.addFilledComponent(submitBtn, row, 8);

    // Cancel button
    JButton cancelBtn = new JButton("Cancel");
    cancelBtn.setActionCommand(CANCEL);
    cancelBtn.addActionListener(actionTrigger);

    panel.addComponent(cancelBtn, ++row, 8, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

    if (mainApp == null)
    {
      setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
    }
    else
    {
      setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }
    this.repaint();
    this.setVisible(true);
  }

  /**
   * Method main.  
   * @param args
   */
  public static void main( String[] args )
  {
    new SearchRacerDialog(new JFrame(), "", RacerPersonFieldName.FIRST_NAME);
  }
}
