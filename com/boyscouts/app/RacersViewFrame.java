/*
 * @author:		Owner
 * Package:		com.boyscouts.app
 * File Name:		RacersViewFrame.java
 */
package com.boyscouts.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.boyscouts.domain.Log;
import com.boyscouts.domain.RaceDataContainer;
import com.boyscouts.domain.RaceDataFieldName;
import com.boyscouts.domain.RaceDataTableModel;
import com.boyscouts.domain.RacerContainer;
import com.boyscouts.domain.RacerPersonFieldName;
import com.boyscouts.domain.RegisteredRacersTableModel;
import com.hgtable.HGTable;

/**
 * author:      Owner
 * Package:     com.boyscouts.app
 * File Name:   RacersViewFrame.java
 * Type Name:   RacersViewFrame
 * Description: Creates the Racers View Frame
 */

public class RacersViewFrame extends JFrame
{

  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3257569494724457778L;

  /**
   * Field <code>mainApp</code> : RaceARama
   * 
   * @uml.property name="mainApp"
   * @uml.associationEnd multiplicity="(1 1)" inverse="racerViewFrame:com.boyscouts.app.RaceARama"
   */
  private RaceARama mainApp = null;

  /**
   * Field <code>overallViewPanel</code> : JPanel
   * 
   * @uml.property name="overallViewPanel"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JPanel overallViewPanel = new JPanel(new BorderLayout());

  /**
   * Field <code>singleRacePanel</code> : JPanel
   * 
   * @uml.property name="singleRacePanel"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JPanel singleRacePanel = new JPanel(new BorderLayout());

  /**
   * Field <code>singleTable</code> : HGTable
   * 
   * @uml.property name="singleTable"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private HGTable singleTable = null;

  /**
   * Field <code>overallTable</code> : HGTable
   * 
   * @uml.property name="overallTable"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private HGTable overallTable = null;

  /** Field <code>currentHEAT</code> : int */
  private int currentHEAT = 0;

  private int currentRACE = 0;
  /**
   * Constructor for RacersViewFrame. Contructor for a Frame that 
   * displays the Racers View as it is moving along
   * @param mainApp - RaceARama, the Main Application
   */
  public RacersViewFrame( RaceARama mainApp )
  {
    this.mainApp = mainApp;
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    this.setTitle(getString("RacersViewFrame.JFrame.title"));
    Container cont = this.getContentPane();
    cont.setLayout(new BorderLayout());
    cont.add(createSplitPane());

    this.setSize(610, 400);
    centerOnScreen(this);

  }
  /**
   * Method centerOnScreen.  Centers the Application on the screen, based on the Screen Dimensions
   * and the Dimesions of the Application
   * @param comp - JFrame, the Frame to be centered.
   */
  private void centerOnScreen( JFrame comp )
  {
    Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();

    Rectangle screenRect = new Rectangle(0, 0, screenDim.width, screenDim.height);

    // Make sure we don't place the demo off the screen.
    int centerWidth = screenRect.width < comp.getSize().width ? screenRect.x : screenRect.x + screenRect.width / 2 - comp.getSize().width / 2;
    int centerHeight = screenRect.height < comp.getSize().height ? screenRect.y : screenRect.y + screenRect.height / 2 - comp.getSize().height / 2;

    comp.setLocation(centerWidth, centerHeight);
  }
  /**
   * Method createOverallRaceReviewPanel.  Sets up the Race information for the Overall Display
   */
  private void createOverallRaceReviewPanel()
  {
    // Get the current RACE Number  
    currentRACE = RaceARama.getRaceCount();
    // Get a Reference to the Container
    RacerContainer racerContainer = mainApp.getRacersContainer();
    // Create the Table Model and Table
    RegisteredRacersTableModel tableModel = new RegisteredRacersTableModel(racerContainer);
    overallTable = new HGTable(tableModel);
    overallTable.setRowSelectionAllowed(true);
    overallTable.setColumnSelectionAllowed(false);

    // Now Create the Scroll Pane.
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.getViewport().add(overallTable);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Racers"));

    // Fill in the Panel
    String titleText = "Registered Racers";
    JLabel titleLbl = new JLabel(titleText);
    titleLbl.setHorizontalAlignment(JLabel.CENTER);
    overallViewPanel.removeAll();
    overallViewPanel.add(titleLbl, BorderLayout.NORTH);
    overallViewPanel.add(scrollPane, BorderLayout.CENTER);

    // First Remove the Undesirable View of Race Data
    RacerPersonFieldName[] removeFields = {RacerPersonFieldName.ID, RacerPersonFieldName.DISTRICT, RacerPersonFieldName.PACK, RacerPersonFieldName.DEN,
                                           RacerPersonFieldName.ADDRESS, RacerPersonFieldName.CITY, RacerPersonFieldName.STATE, RacerPersonFieldName.PHONE,
nFieldName.MIN_VALUE,
                                           RacerPersonFieldName.MAX_VALUE, RacerPersonFieldName.AVG_VALUE, RacerPersonFieldName.STD_DEV,
                                           RacerPersonFieldName.RACE_NAME};
    // Begin Removal of Columns not desired to be seen.
    TableColumnModel tableColumnModel = overallTable.getColumnModel();
    for (int colIndex = 0; colIndex < removeFields.length; colIndex++)
    {
      String temp = "" + removeFields[colIndex];
      tableColumnModel.removeColumn(overallTable.getColumn(temp));
    }
    // Re-Arrange the Columns as the Data model works for the Database, but user View is odd.
    // First Define an Array of the Fields to Move
    RacerPersonFieldName[] listOrder = {RacerPersonFieldName.PLACEMENT, RacerPersonFieldName.LAST_NAME, RacerPersonFieldName.FIRST_NAME,
                                        RacerPersonFieldName.VEHICLE_NUMBER, RacerPersonFieldName.POINTS};
    // Now iterate through the chosen fields 
    for (int colIndex = 0; colIndex < listOrder.length; colIndex++)
    { //  lookup by name, then get current index, and set the new index, that is our loop variable
      String temp = "" + listOrder[colIndex];
      int currIndex = tableColumnModel.getColumnIndex(temp);
      tableColumnModel.moveColumn(currIndex, colIndex);
    }
    // Repaint the Table
    overallTable.tableChanged(new TableModelEvent(tableModel));
    overallTable.repaint();
  }
  /**
   * Method createSingleRaceScheduleDisplay.  Sets up the Race information for the Single Race Display
   * 
   */
  private void createSingleRaceScheduleDisplay()
  {
    // Get the current HEAT Number  
    currentHEAT = mainApp.getHeatNumber();
    // Now we get the Current RaceData, 
    RaceDataContainer currentRaceData = mainApp.getCurrentRaceData();

    String titleText = "Heat # " + (currentHEAT + 1);
    RaceDataTableModel tableModel = new RaceDataTableModel(currentRaceData);
    singleTable = new HGTable(tableModel);
    singleTable.setRowSelectionAllowed(true);
    singleTable.setColumnSelectionAllowed(false);
    singleTable.setMinWidthToText(false);
    singleTable.setToolTipText(titleText);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.getViewport().add(singleTable);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Racers"));

    // Fill in the Panel
    JLabel titleLbl = new JLabel(titleText);
    titleLbl.setHorizontalAlignment(JLabel.CENTER);
    singleRacePanel.removeAll();
    singleRacePanel.add(titleLbl, BorderLayout.NORTH);
    singleRacePanel.add(scrollPane, BorderLayout.CENTER);

    // First Remove the Entry ID, Undesirable View
    TableColumnModel model = singleTable.getColumnModel();
    model.removeColumn(singleTable.getColumn(RaceDataFieldName.ENTRY.toString()));
    model.removeColumn(singleTable.getColumn(RaceDataFieldName.ROUND.toString()));
    model.removeColumn(singleTable.getColumn(RaceDataFieldName.ID.toString()));
    // Re-Arrange the Columns as the Data model works for the Database, but user View is odd.
    // First Define an Array of the Fields to Move
    RaceDataFieldName[] listOrder = {RaceDataFieldName.LANE, RaceDataFieldName.VEHICLE_NUMBER, RaceDataFieldName.HEAT,
                                     RaceDataFieldName.RACE_TIME, RaceDataFieldName.PLACEMENT};
    // Now iterate through the chosen fields 
    for (int colIndex = 0; colIndex < listOrder.length; colIndex++)
    { //  lookup by name, then get current index, and set the new index, that is our loop variable
      String temp = "" + listOrder[colIndex];
      int currIndex = model.getColumnIndex(temp);
      model.moveColumn(currIndex, colIndex);
    }

    singleTable.tableChanged(new TableModelEvent(tableModel));
    singleTable.repaint();
  }
  /**
   * Method createSplitPane.  Creates a Split Pane and Displays the Two Panes together
   * @return JSplitPane
   */
  private JSplitPane createSplitPane()
  {
    createSingleRaceScheduleDisplay();
    createOverallRaceReviewPanel();

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, overallViewPanel, singleRacePanel);
    splitPane.setOneTouchExpandable(true);
    splitPane.setContinuousLayout(true);
    splitPane.setDividerLocation(100);

    return splitPane;
  }
  /**
   * Method debug.  
   * @param s
   */
  protected void debug( String s )
  {
    s = "RacersViewFrame: " + s;
    Log.debug(s);
  }
  /**
   * Method getString.  Returns the Value from a specified Key
   * @param key - String, the Key
   * @return String
   */
  protected String getString( String key )
  {
    debug("getString(" + key + ") - retrieving Key...");
    String value = null;
    if (mainApp != null)
    {
      value = mainApp.getString(key);
    }
    if (value == null)
    {
      value = "Could not find resource: " + key + "  ";
    }
    debug("getString(" + key + ") - value ==> " + value);
    debug("getString(" + key + ") - retrieving Key...Done");
    return value;
  }
  /**
   * 
   */
  {
    boolean displayWinnerDialog = true;
    if (currentHEAT != mainApp.getHeatNumber())
    {
      createSingleRaceScheduleDisplay();
      displayWinnerDialog = false;
    }
    if (currentRACE != RaceARama.getRaceCount())
    {
      createOverallRaceReviewPanel();
      displayWinnerDialog = false;
    }
    TableModel tableModel = null;
    // Repaint the Table
    tableModel = singleTable.getModel();
    singleTable.tableChanged(new TableModelEvent(tableModel));
    singleTable.repaint();

    // Repaint the Table
    tableModel = overallTable.getModel();
    overallTable.tableChanged(new TableModelEvent(tableModel));
    overallTable.repaint();
    Component comp = this.getContentPane().getComponent(0);

    if (displayWinnerDialog)
      RaceDataContainer currentRaceData = mainApp.getCurrentRaceData();
      RacerContainer racerCont = mainApp.getRacersContainer();
      new RacersViewHeatWinnerDialog(this, currentRaceData, racerCont);
    }
  }

}
