/*
 * @author:		Owner
 * date:		Dec 29, 2003
 * Package:		com.boyscouts.app
 * File Name:		RaceSchedulePanel.java
 */
package com.boyscouts.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumnModel;

import DaqDigIOAccess.DaqDigIOAccess;

import com.boyscouts.domain.Log;
import com.boyscouts.domain.RaceData;
import com.boyscouts.domain.RaceDataComparator;
import com.boyscouts.domain.RaceDataContainer;
import com.boyscouts.domain.RaceDataFieldName;
import com.boyscouts.domain.RaceDataTableModel;
import com.boyscouts.domain.RacerPerson;
import com.boyscouts.util.schedule.HeatScheduleVector;
import com.boyscouts.util.schedule.PersonRacerSchedule;
import com.boyscouts.util.schedule.Player;
import com.boyscouts.util.schedule.RaceScheduleVector;
import com.boyscouts.util.schedule.Schedule;
import com.boyscouts.util.schedule.table.OverallRaceScheduleTablePanel;
import com.hgtable.HGTable;
import com.hgutil.swing.GridBagHelper;
import com.hgutil.swing.IntegerTextField;

/**
 * author: Owner <BR>date: Dec 29, 2003 <BR>Package: com.boyscouts.app <BR>
 * File Name: RaceSchedulePanel.java <BR>Type Name: RaceSchedulePanel <BR>
 * Description: Creates the Scheduling Panel. The Scheduling Panel provides
 * support for Scheduling and Running the Race
 */

public class RaceSchedulePanel extends JPanel
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3688507684001165364L;
  /** Field <code>UPDATE_WAIT_TIME</code> : int */
  private static final int UPDATE_WAIT_TIME = 2000;
  /** Field <code>SCHEDULE_RACE</code> : String */
  private static final String SCHEDULE_RACE = "SCHEDULE_RACE";
  /** Field <code>RACE</code> : String */
  private static final String RACE = "RACE";
  /** Field <code>NEXT_RACE</code> : String */
  private static final String NEXT_RACE = "NEXT_RACE";
  /** Field <code>REDO_RACE</code> : String */
  private static final String REDO_RACE = "REDO_RACE";
  /** Field <code>STEP_ONE</code> : String */
  private static final String STEP_ONE = "STEP_ONE";
  /** Field <code>STEP_TWO</code> : String */
  private static final String STEP_TWO = "STEP_TWO";
  /** Field <code>WAIT_CURSOR</code> : Cursor */
  private static final Cursor WAIT_CURSOR = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
  /** Field <code>DEFAULT_CURSOR</code> : Cursor */
  private static final Cursor DEFAULT_CURSOR = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
  /** Field <code>currentRaceNumber</code> : int */
  private int currentRaceNumber = -1;

  /**
   * Field <code>mainApp</code> : RaceARama
   * 
   * @uml.property name="mainApp"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RaceARama mainApp = null;

  /** Field <code>myLayout</code> : CardLayout */
  private CardLayout myLayout = null;

  /**
   * Field <code>cardPanel</code> : JPanel
   * 
   * @uml.property name="cardPanel"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JPanel cardPanel = null;

  /**
   * Field <code>actionTrigger</code> : ActionTrigger
   * 
   * @uml.property name="actionTrigger"
   * @uml.associationEnd multiplicity="(1 1)" inverse="this$0:com.boyscouts.app.RaceSchedulePanel$ActionTrigger"
   */
  private ActionTrigger actionTrigger = new ActionTrigger();

  /**
   * Field <code>totalOfRacers</code> : IntegerTextField
   * 
   * @uml.property name="totalOfRacers"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private IntegerTextField totalOfRacers = new IntegerTextField(0, 3, 3);

  /**
   * Field <code>numberOfLanes</code> : IntegerTextField
   * 
   * @uml.property name="numberOfLanes"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private IntegerTextField numberOfLanes = new IntegerTextField(12, 2, 2);

  /**
   * Field <code>numberOfHeats</code> : IntegerTextField
   * 
   * @uml.property name="numberOfHeats"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private IntegerTextField numberOfHeats = new IntegerTextField(0, 2, 2);

  /**
   * Field <code>fullRaceScedulePanel</code> : JPanel
   * 
   * @uml.property name="fullRaceScedulePanel"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JPanel fullRaceScedulePanel = null;

  /**
   * Field <code>singleRaceScedulePanel</code> : JPanel
   * 
   * @uml.property name="singleRaceScedulePanel"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JPanel singleRaceScedulePanel = null;

  /**
   * Field <code>schedule</code> : Schedule
   * 
   * @uml.property name="schedule"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private Schedule schedule = null;

  /**
   * Field <code>racerScheduleData</code> : RaceScheduleVector
   * 
   * @uml.property name="racerScheduleData"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RaceScheduleVector racerScheduleData = new RaceScheduleVector();

  /**
   * Field <code>raceBtn</code> : JButton
   * 
   * @uml.property name="raceBtn"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JButton raceBtn = new JButton("Race");

  /**
   * Field <code>nextRaceBtn</code> : JButton
   * 
   * @uml.property name="nextRaceBtn"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JButton nextRaceBtn = new JButton("Next Race");

  /**
   * Field <code>redoCurrentRaceBtn</code> : JButton
   * 
   * @uml.property name="redoCurrentRaceBtn"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JButton redoCurrentRaceBtn = new JButton("Redo Race");

  /** Field <code>LANES</code> : int */
  private static final int LANES = DaqDigIOAccess.LANES;

  /**
   * author:      Owner<BR>
   * date:        Dec 11, 2003<BR>
   * Package:     com.boyscouts.publish.results<BR>
   * File Name:   WebPublishFrame.java<BR>
   * Type Name:   ActionTrigger<BR>
   * Description: Button Listener
   */
  private class ActionTrigger implements ActionListener
  {
    /**
     * Overridden Method:  
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param evt - ActionEvent
     */
    public void actionPerformed( ActionEvent evt )
    {
      String cmd = evt.getActionCommand();
      RaceSchedulePanel.this.setCursor(RaceSchedulePanel.WAIT_CURSOR);
      if (cmd.equals(SCHEDULE_RACE))
      {
        scheduleTheRace();
      }
      else if (cmd.equals(RACE))
      {
        startCurrentRace();
      }
      else if (cmd.equals(NEXT_RACE))
      {
        setupNextRace();
      }
      else if (cmd.equals(REDO_RACE))
      {
        reExecuteCurrentRace();
      }
      RaceSchedulePanel.this.setCursor(RaceSchedulePanel.DEFAULT_CURSOR);
    }
  }
  /**
   * Constructor for RaceSchedulePanel. 
   * @param mainApp
   */
  public RaceSchedulePanel( RaceARama mainApp )
  {
    this.mainApp = mainApp;

    initialize();
  }
  /**
   * Method createMasterScheduleDisplay.  This method will create the Master Schedule Display Table 
   * and update the panel with the Table.  
   */
  private void createMasterScheduleDisplay()
  {
    if (racerScheduleData != null)
    {
      racerScheduleData.clear();
    }
    // Get the the Number of Races to be Run
    int raceCount = schedule.getRaceCount();
    // Get the Number of Lanes to be run
    int laneCount = schedule.getTrackCount();
    Vector rowLabels = new Vector();
    Vector colLabels = new Vector();
    // Generate the Row Header Labels
    for (int i = 0; i < raceCount; i++)
    {
      rowLabels.add("Heat # " + (i + 1));
    }
    boolean[] selectedLanes = DaqDigIOAccess.getSelectedLanesForRace();
    for (int i = 0; i < selectedLanes.length; i++)
    {
      if (selectedLanes[i])
      {
        colLabels.add("Lane # " + (i + 1));
      }
    }
    //    // Generate the Data to inserted in the Table, A Vector of Vectors
    //    for (int race = 0; race < raceCount; race++)
    //    { // First Vector a Race, is a vector of Racers in a Lane
    //      Vector lanePersons = new Vector();
    //      //for (int lane = 0; lane < selectedLanes.length; lane++)
    //      for (int lane = 0; lane < selectedLanes.length; lane++) // laneCount
    //      { // each Lane has a Person, either Real or Ficticous, add them only for the lane requested
    //        lanePersons.add(this.getPersonRacerSchedule(race, lane));
    //      }
    //      // Add the Race to the Data.
    //      racerScheduleData.add(lanePersons);
    //    }
    // Generate the Data to inserted in the Table, A Vector of Vectors
    for (int race = 0; race < raceCount; race++)
    { // First Vector a Race, is a vector of Racers in a Lane
      HeatScheduleVector heatVector = new HeatScheduleVector();
      Vector lanePersons = new Vector();
      //for (int lane = 0; lane < selectedLanes.length; lane++)
      for (int lane = 0; lane < laneCount; lane++) // 
      { // each Lane has a Person, either Real or Ficticous, add them only for the lane requested
        heatVector.add(this.getPersonRacerSchedule(race, lane));
      }
      // Add the Race to the Data.
      racerScheduleData.add(heatVector);
    }
    JLabel titleLbl = new JLabel("Master Race Schedule");
    titleLbl.setHorizontalAlignment(JLabel.CENTER);
    // Add the Row Labels Vector, the Column Labels Vector, and the RacerData Vector of Vectors.
    OverallRaceScheduleTablePanel schedPanel = new OverallRaceScheduleTablePanel(rowLabels, colLabels, racerScheduleData);
    // Add the New Table to the Panel
    fullRaceScedulePanel.removeAll();
    fullRaceScedulePanel.add(titleLbl, BorderLayout.NORTH);
    fullRaceScedulePanel.add(schedPanel, BorderLayout.CENTER);

  }
  /**
   * Method createSingleRaceScheduleDisplay.  Creates and Builds the Panel Displaying the Single Race Elements
   */
  private void createSingleRaceScheduleDisplay()
  {
    // Get the current HEAT Number  and Pull the List of Competitors from the Vector
    // Remember the Vector Data is a Vector of Vector of PersonRacerScedule(s)
    int heat = mainApp.getHeatNumber();
    HeatScheduleVector currentHeatPersons = racerScheduleData.getHeatScheduleVector(heat);
    // Now we get the Current RaceData, clear any old data and Populate it 
    // with the current Race Data
    RaceDataContainer currentRaceData = mainApp.getCurrentRaceData();
    currentRaceData.clear();

    for (int index = 0; index < currentHeatPersons.size(); index++)
    {
      PersonRacerSchedule personRacerSchedule = currentHeatPersons.getPersonRacerSchedule(index);

      RaceData rd = personRacerSchedule.createRaceDataForRacer();
      currentRaceData.addRaceData(rd);
    }

    String titleText = "Heat # " + (heat + 1);
    RaceDataTableModel tableModel = new RaceDataTableModel(currentRaceData);
    HGTable table = new HGTable(tableModel);
    table.setRowSelectionAllowed(true);
    table.setColumnSelectionAllowed(false);
    table.setMinWidthToText(false);
    table.setToolTipText(titleText);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.getViewport().add(table);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Racers"));

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.add(raceBtn);
    btnPanel.add(nextRaceBtn);
    btnPanel.add(redoCurrentRaceBtn);
    raceBtn.setEnabled(true);
    nextRaceBtn.setEnabled(false);
    redoCurrentRaceBtn.setEnabled(false);

    // Fill in the Panel
    JLabel titleLbl = new JLabel(titleText);
    titleLbl.setHorizontalAlignment(JLabel.CENTER);
    singleRaceScedulePanel.removeAll();
    singleRaceScedulePanel.add(titleLbl, BorderLayout.NORTH);
    singleRaceScedulePanel.add(scrollPane, BorderLayout.CENTER);
    singleRaceScedulePanel.add(btnPanel, BorderLayout.SOUTH);

    // First Remove the Entry ID, Undesirable View
    TableColumnModel model = table.getColumnModel();
    model.removeColumn(table.getColumn(RaceDataFieldName.ENTRY.toString()));
    model.removeColumn(table.getColumn(RaceDataFieldName.DATE.toString()));
    // Re-Arrange the Columns as the Data model works for the Database, but user View is odd.
    // First Define an Array of the Fields to Move
    RaceDataFieldName[] listOrder = {RaceDataFieldName.LANE, RaceDataFieldName.VEHICLE_NUMBER, RaceDataFieldName.ROUND, RaceDataFieldName.HEAT,
                                     RaceDataFieldName.RACE_TIME, RaceDataFieldName.PLACEMENT};
    int currIndex = 0;
    // Now iterate through the chosen fields 
    for (int colIndex = 0; colIndex < listOrder.length; colIndex++)
    { //  lookup by name, then get current index, and set the new index, that is our loop variable
      String temp = "" + listOrder[colIndex];
      currIndex = model.getColumnIndex(temp);
      model.moveColumn(currIndex, colIndex);
    }

    table.tableChanged(new TableModelEvent(tableModel));
    table.repaint();
  }
  /**
   * Method createStepOnePanel.  Creates the Panel where the user initiates the Scheduling
   * @return JPanel
   */
  private JPanel createStepOnePanel()
  {
    JLabel lblRacers = new JLabel("Racers");
    totalOfRacers.setEditable(false);
    totalOfRacers.setValue(this.mainApp.getRacersContainer().getSize());

    JLabel lblLanes = new JLabel("Lanes");
    numberOfLanes.setEditable(false);
    numberOfLanes.setValue(12);

    JLabel lblRaces = new JLabel("#Heats");
    numberOfHeats.setEditable(true);
    numberOfHeats.setValue(1);
    numberOfHeats.setForeground(Color.BLUE);

    JButton submitBtn = new JButton("Schedule");
    submitBtn.addActionListener(actionTrigger);
    submitBtn.setActionCommand(SCHEDULE_RACE);

    String msg = getString("RaceSchedulePanel.schedule.notice.text");
    JTextArea noticeArea = new JTextArea(msg, 5, 20);
    GridBagHelper retPanel = new GridBagHelper();
    retPanel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
    retPanel.addComponent(lblRacers, 1, 1);
    retPanel.addComponent(totalOfRacers, 2, 1);
    retPanel.addComponent(lblLanes, 1, 2);
    retPanel.addComponent(numberOfLanes, 2, 2);
    retPanel.addComponent(lblRaces, 1, 3);
    retPanel.addComponent(numberOfHeats, 2, 3);
    retPanel.addFilledComponent(submitBtn, 3, 1, 3, 1, GridBagConstraints.HORIZONTAL);

    retPanel.addFilledComponent(noticeArea, 3, 4, 3, 4, GridBagConstraints.BOTH);

    return retPanel;
  }
  /**
   * Method createStepTwoPanel.  Creates the Panel where the user sees the 
   * @return JSplitPane
   */
  private JSplitPane createStepTwoPanel()
  {
    JLabel lbl1 = new JLabel("Full Race Schedule");
    fullRaceScedulePanel = new JPanel(new BorderLayout());
    fullRaceScedulePanel.add(lbl1, BorderLayout.NORTH);

    JLabel lbl2 = new JLabel("Single Race Schedule");
    singleRaceScedulePanel = new JPanel(new BorderLayout());
    singleRaceScedulePanel.add(lbl2, BorderLayout.NORTH);

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, fullRaceScedulePanel, singleRaceScedulePanel);
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
    s = "RaceSchedulePanel: " + s;
    Log.debug(s);
  }
  /**
   * Method getPersonRacerSchedule.  Creates a PersonRacerShedule Object for the Player at the Race and Lane,
   * Note the Player can be empty and the RacerPerson can both be null or empty
   * @param race - int, The race of person in the schedule
   * @param lane - int, The lane of person in the schedule
   * @return PersonRacerSchedule, continer of the Player in the schedule and the RacerPerson
   */
  private PersonRacerSchedule getPersonRacerSchedule( int race, int lane )
  {
    Player player = schedule.getPlayer(race, lane);
    RacerPerson rp = null;
    if (!player.isEmpty())
    {
      int index = player.getWho();
      rp = mainApp.getRacersContainer().elementAt(index);
    }
    // Create a PersonRacerSchedule object even if the RacerPerson is null or empty
    PersonRacerSchedule personRaceSchedule = new PersonRacerSchedule(rp, player, race, lane);

    return personRaceSchedule;
  }
  /**
   * Method getString. Method returns a String from a Specified Key
   * @param key - The Key to Look Up
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
   * Method initialize.  Initializes the Race Schedule Panel, gets a reference to the RacerScheduleData
   * and creates the Display panels.
   */
  private void initialize()
  {
    raceBtn.setActionCommand(RACE);
    raceBtn.addActionListener(actionTrigger);
    nextRaceBtn.setActionCommand(NEXT_RACE);
    nextRaceBtn.addActionListener(actionTrigger);
    redoCurrentRaceBtn.setActionCommand(REDO_RACE);
    redoCurrentRaceBtn.addActionListener(actionTrigger);

    myLayout = new CardLayout();
    cardPanel = new JPanel(myLayout);
    cardPanel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
    cardPanel.add(createStepOnePanel(), STEP_ONE);
    cardPanel.add(createStepTwoPanel(), STEP_TWO);
    this.setLayout(new BorderLayout());
    this.add(cardPanel, BorderLayout.CENTER);
  }

  /**
   * Method scheduleTheRace.  Method will generate the Master Schedule for all the Races.  Also it will
   * Create the Display for the Master Race Schedule and the the First Single Race Schedule.
   */
  private void scheduleTheRace()
  {
    int lanes = numberOfLanes.getValue();
    int players = totalOfRacers.getValue();
    int racesPerPlayer = numberOfHeats.getValue();
    boolean validRaceCount = (1 <= racesPerPlayer && racesPerPlayer < 100);
    boolean validNumberRacers = (1 < players);
    // Display the dialog for the lane selection
    LaneSelectionDialog lsd = new LaneSelectionDialog(mainApp, lanes);
    boolean[] selectedLanes = lsd.getSelectedLanes();
    DaqDigIOAccess.setSelectedLanesForRace(selectedLanes);

    if (validRaceCount && validNumberRacers)
    { // The Lanes, the Players, the RacersPerPlayer, and the Active Selected Lanes
    //      schedule = new Schedule(lanes, players, racesPerPlayer, selectedLanes);
      schedule = new Schedule(selectedLanes.length, players, racesPerPlayer, selectedLanes);
      schedule.initialAssignment();
      createMasterScheduleDisplay();
      createSingleRaceScheduleDisplay();
      myLayout.next(RaceSchedulePanel.this.cardPanel);
    }
    else
    {
      String msg = "The Number of Races[" + racesPerPlayer + "] or Racers[" + players + "] is invalid,\nThe value must be postive and greater than 1";
      JOptionPane.showMessageDialog(null, msg, "Invalid Schedule Data", JOptionPane.ERROR_MESSAGE);
    }
  }
  /**
   * Method reExecuteCurrentRace.  Method permits the User To re-execute the 
   * current race, it will reset the initial values as necesary.
   * <ul>
   *   <li>Disable all buttons</li>
   *   <li>Get All the Race Data</li>
   *   <li>Reset the Current Times to Max</li>
   *   <li>Reset the Placements</li>
   *   <li>Fire any client updates.</li>
   *   <li>Enable the Race Button</li>
   * </ul>
   */
  private void reExecuteCurrentRace()
  {
    nextRaceBtn.setEnabled(false);
    raceBtn.setEnabled(false);
    redoCurrentRaceBtn.setEnabled(false);
    RaceDataContainer currentRaceData = mainApp.getCurrentRaceData();
    int containerSize = currentRaceData.getSize();
    for (int i = 0; i < containerSize; i++)
    {
      RaceData rd = currentRaceData.elementAt(i);
      rd.setTime(100.0);
      rd.setPlacement(-1);
    }

    mainApp.fireClientUpdates(true);
    raceBtn.setEnabled(true);
  }
  /**
   * Method resetRaceSchedule.  Reset the Schedule Panel, to the initial state.
   */
  public void resetRaceSchedule()
  {
    schedule = null;
    fullRaceScedulePanel.removeAll();
    singleRaceScedulePanel.removeAll();
    myLayout.first(RaceSchedulePanel.this.cardPanel);
  }
  /**
   * Method setupNextRace.  Initiates the setup of subsequent Races<BR>
   * <ul>
   *   <li>Disable both buttons</li>
   *   <li>Increment the Heat Counter</li>
   *   <li>If More Races exist populate the next round of races</li>
   *   <li>If More Races exist Fire Any Updates to the MainApp, it will notify any clients</li>
   * </ul>
   */
  private void setupNextRace()
  {
    nextRaceBtn.setEnabled(false);
    raceBtn.setEnabled(false);
    redoCurrentRaceBtn.setEnabled(false);

    mainApp.incrementHeatNumber();
    int raceCount = schedule.getRaceCount();
    int heatNumber = mainApp.getHeatNumber();
    if (raceCount > heatNumber)
    {
      createSingleRaceScheduleDisplay();
      mainApp.fireClientUpdates(true);
    }
    else
    {
      String msg = "All Races have been completed!\nBegin Web Publishing of Results?";
      int answer = JOptionPane.showConfirmDialog(mainApp, msg, "All Races Completed, Publish?", JOptionPane.YES_NO_OPTION);
      if (answer == JOptionPane.YES_OPTION)
      {
        mainApp.publishRaceResults();
      }
    }
  }

  /**
   * Method startCurrentRace.  Begins the Start of the Current Race<BR>
   * <ul>
   *   <li>Disable both buttons</li>
   *   <li>Aquire an instance to the DaqDigIOAccess object</li>
   *   <li>Invoke the <PRE>fireReadLaneSignals()</PRE> of the DaqDigIOAccess</li>
   *   <li>Check if the DaqDigIOAccess has an error. If so display it, else contine processing</li>
   *   <li>Continue...Get a list of the Lane Times</li>
   *   <li>Get The the race data container.</li>
   *   <li>Update the Race Data Container times and Placement</li>
   *   <li>Fire Updates in the Main App, it will notify all that it needs to notify</li>
   *   <li>Check for the Last Race</li>
   *   <li>enable the next race button if more races available</li>
   * </ul>
   */
  private void startCurrentRace()
  {
    nextRaceBtn.setEnabled(false);
    raceBtn.setEnabled(false);
    redoCurrentRaceBtn.setEnabled(false);

    DaqDigIOAccess daqAccess = DaqDigIOAccess.getInstance();
    daqAccess.fireReadLaneSignals();

    if (daqAccess.hasError())
    {
      String msg = "DAQ CARD ERROR.\nEncountered an Error during processing!\n" + daqAccess.getErrorMsg();
      JOptionPane.showMessageDialog(null, msg, "DAQ LANE TEST", JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      double[] laneTimes = daqAccess.getLaneTimes();
      RaceDataContainer raceDataContainer = mainApp.getCurrentRaceData();
      updateRaceDataContainerTimesAndPlacement(raceDataContainer, laneTimes);
      // The Display of the Top Racers of the current heat can be found in the 
      // RacersViewFrame's updateDisplay() method.
    }

    mainApp.fireClientUpdates(true);
    int raceCount = schedule.getRaceCount();
    int heatNumber = mainApp.getHeatNumber();
    if (raceCount > heatNumber)
    {
      nextRaceBtn.setEnabled(true);
    }
    redoCurrentRaceBtn.setEnabled(true);
  }

  /**
   * Method update(). will update the Display, and the Fields required to populate the Data.
   * Overridden Method:  <BR>
   * NOTE: This method also initalizes he Number of racers and the number of lanes. With the Use
   * of the contants LANES (see the declareation). The Lanes Count is determined.
   * @see java.awt.Component#update(java.awt.Graphics)
   * @param g - Graphics, the Graphics Object to Update.
   */
  public void update( Graphics g )
  {
    int tempRaceNumb = RaceARama.getRaceCount();
    if (tempRaceNumb != this.currentRaceNumber)
    {
      resetRaceSchedule();
      this.currentRaceNumber = tempRaceNumb;
    }

    int racersCount = mainApp.getRacersContainer().getSize();
    int laneCount = (racersCount < LANES) ? racersCount : LANES;
    int heatCount = racersCount;

    totalOfRacers.setValue(racersCount);
    numberOfLanes.setValue(laneCount);
    numberOfHeats.setValue(heatCount);

    if (RaceARama.SIM_REPORT && racersCount < 2)
    {
      JOptionPane.showMessageDialog(null, "Simulation Data of 33 racers,\n will be scheduled to run in simultion mode.", "SIMULATION MODE",
                                    JOptionPane.INFORMATION_MESSAGE);
      totalOfRacers.setValue(33); // Test Value
    }
    super.update(g);
  }
  /**
   * Method updateRaceDataContainerTimesAndPlacement. 
   * Updates the Times for each Racer then applies the placement
   * @param raceDataContainer - RaceDataContainer, the Data to update.
   * @param times - double[] - The Array of times for all lanes.
   */
  private void updateRaceDataContainerTimesAndPlacement( RaceDataContainer raceDataContainer, double[] times )
  {
    // Set ALL the Times - Since we do not know exactly how many and how they sorted.
    // Iterate through the container then add get the lane, remember the lane is positve.
    // So we need to subtract one to make it a lane index in the times.
    Vector data = raceDataContainer.getRacerData();
    for (int i = 0; i < data.size(); i++)
    {
      RaceData rd = (RaceData) data.elementAt(i);
      int lane = rd.getLane().intValue() - 1; // Make it Natural, it is positve based now.
      rd.setTime(times[lane]);
    }
    // Next sort the container by the Times
    Collections.sort(data, new RaceDataComparator(RaceDataFieldName.RACE_TIME, true));
    // Next assign the placement based on the Sorted Order
    int containerSize = raceDataContainer.getSize();
    for (int i = 0; i < containerSize; i++)
    {
      RaceData rd = raceDataContainer.elementAt(i);
      rd.setPlacement(i + 1);
    }
    // Update the Racers Times and Ordering
    mainApp.getRacersContainer().updateRacerTimes();
    // Re-Sort Based on Lane
    Collections.sort(data, new RaceDataComparator(RaceDataFieldName.LANE, true));
  }

}