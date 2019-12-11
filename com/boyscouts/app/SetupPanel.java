/*
 * @author: Owner 
 * Package: com.boyscouts.app File Name:
 * SetupPanel.java
 */
package com.boyscouts.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DaqDigIOAccess.DaqDigIOAccess;

import com.boyscouts.domain.FieldLengths;
import com.boyscouts.domain.LightLabel;
import com.boyscouts.domain.Log;
import com.boyscouts.util.track.CarPerformance;
import com.boyscouts.util.track.TrackUtils;
import com.hgutil.swing.DoubleTextField;
import com.hgutil.swing.GridBagHelper;
import com.hgutil.swing.IntegerTextField;
import com.hgutil.swing.MaxLengthTextField;

/**
 * author: Owner 
 * Package: com.boyscouts.app 
 * File Name: SetupPanel.java 
 * Type Name: SetupPanel 
 * Description: Creates the Setup Panel
 */

public class SetupPanel extends JPanel implements FieldLengths
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3977857367419728944L;
  /** Field <code>LANES</code> : int */
  private static final int LANES = 12;
  /** Field <code>SAVE_TRACK</code> : String */
  private static final String SAVE_TRACK = "SAVE_TRACK";
  /** Field <code>CLEAR_TRACK</code> : String */
  private static final String CLEAR_TRACK = "CLEAR_TRACK";

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
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JPanel cardPanel = null;

  /**
   * Field <code>actionTrigger</code> : ActionTrigger
   * 
   * @uml.property name="actionTrigger"
   * @uml.associationEnd multiplicity="(1 1)" inverse="this$0:com.boyscouts.app.SetupPanel$ActionTrigger"
   */
  private ActionTrigger actionTrigger = new ActionTrigger();

  /** Field <code>MAX_ITEMS</code> : int */
  private static final int MAX_ITEMS = 8;

  /**
   * Field <code>lengths</code> : IntegerTextField[]
   * 
   * @uml.property name="lengths"
   * @uml.associationEnd multiplicity="(0 -1)"
   */
  private IntegerTextField[] lengths = new IntegerTextField[MAX_ITEMS];

  /**
   * Field <code>heights</code> : IntegerTextField[]
   * 
   * @uml.property name="heights"
   * @uml.associationEnd multiplicity="(0 -1)"
   */
  private IntegerTextField[] heights = new IntegerTextField[MAX_ITEMS];

  /**
   * Field <code>saveSettingsBtn</code> : JButton
   * 
   * @uml.property name="saveSettingsBtn"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JButton saveSettingsBtn = null;

  /**
   * Field <code>clearSettingsBtn</code> : JButton
   * 
   * @uml.property name="clearSettingsBtn"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JButton clearSettingsBtn = null;

  /**
   * Field <code>fullTrackLength</code> : DoubleTextField
   * 
   * @uml.property name="fullTrackLength"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private DoubleTextField fullTrackLength = new DoubleTextField(0.0, 4);

  /**
   * Field <code>idealTotalTime</code> : DoubleTextField
   * 
   * @uml.property name="idealTotalTime"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private DoubleTextField idealTotalTime = new DoubleTextField(0.0, 4);

  /**
   * Field <code>idealFinishSpeed</code> : DoubleTextField
   * 
   * @uml.property name="idealFinishSpeed"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private DoubleTextField idealFinishSpeed = new DoubleTextField(0.0, 4);

  /**
   * Field <code>idealAverageSpeed</code> : DoubleTextField
   * 
   * @uml.property name="idealAverageSpeed"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private DoubleTextField idealAverageSpeed = new DoubleTextField(0.0, 4);

  /** Field <code>STEP_ONE</code> : String */
  private static final String STEP_ONE = "STEP_ONE";
  /** Field <code>STEP_TWO</code> : String */
  private static final String STEP_TWO = "STEP_TWO";

  /**
   * Field <code>daqCardName</code> : JTextField
   * 
   * @uml.property name="daqCardName"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private MaxLengthTextField daqCardName = new MaxLengthTextField(20, 20);

  /**
   * Field <code>timeoutValue</code> : IntegerTextField
   * 
   * @uml.property name="timeoutValue"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private IntegerTextField timeoutValue = new IntegerTextField(10, 3, 3);

  /** Field <code>SET_TIMEOUT_VALUE</code> : String */
  private static final String SET_TIMEOUT_VALUE = "SET_TIMEOUT_VALUE";

  /**
   * Field <code>trackTestLights</code> : LightLabel[]
   * 
   * @uml.property name="trackTestLights"
   * @uml.associationEnd multiplicity="(0 -1)"
   */
  private LightLabel[] trackTestLights = new LightLabel[LANES];

  /** Field <code>FIRE_LANES</code> : String */
  private static final String FIRE_LANES = "FIRE_LANES";
  /** Field <code>DAQ_AQUIRE</code> : String */
  private static final String DAQ_AQUIRE = "DAQ_AQUIRE";
  /** Field <code>ARM_GATE</code> : String */
  private static final String ARM_GATE = "ARM_GATE";
  /** Field <code>DISARM_GATE</code> : String */
  private static final String DISARM_GATE = "DISARM_GATE";

  /**
   * author:	 		Owner
   * Package:			com.boyscouts.app
   * File Name:		SetupPanel.java
   * Type Name:		ActionTrigger
   * Description:	Provides the mouse click events for the outer class
   */

  private class ActionTrigger implements ActionListener
  {
    /** Field <code>daqDigIOEngaged</code> : boolean */
    private boolean daqDigIOEngaged = false;

    /**
     * Field <code>daqAccess</code> : DaqDigIOAccess
     * 
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private DaqDigIOAccess daqAccess = null;

    /**
     * Overridden Method:  
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param evt
     */
    public void actionPerformed( ActionEvent evt )
    {
      String cmd = evt.getActionCommand();
      debug("Command recieved: " + cmd);
      if (cmd.equals(SAVE_TRACK))
      {
        calcTrackLengthAndHeightSettings();
      }
      else if (cmd.equals(CLEAR_TRACK))
      {
        clearTrackLengthAndHeightSettings();
      }
      else if (cmd.equals(STEP_TWO))
      {
        myLayout.next(SetupPanel.this.cardPanel);
      }
      else if (cmd.equals(STEP_ONE))
      {
        myLayout.first(SetupPanel.this.cardPanel);
      }
      else if (cmd.equals(DAQ_AQUIRE))
      {
        daqAccess = DaqDigIOAccess.getInstance();
        daqCardName.setText(daqAccess.getDeviceName());
        daqDigIOEngaged = true;
      }
      else if (cmd.equals(SET_TIMEOUT_VALUE))
      {
        setDaqAccessTimeout();
      }
      else if (cmd.equals(ARM_GATE))
      {
        armTheGate();
      }
      else if (cmd.equals(DISARM_GATE))
      {
        disarmTheGate();
      }
      else if (cmd.equals(FIRE_LANES))
      {
        fireLanesTriggerTest();
      }
    }
    /**
     * Method armTheGate.  Method to arm the Gate. 
     */
    private void armTheGate()
    {
      if (daqDigIOEngaged)
      { // We have been engaged, continue processing
        if (daqAccess.isGateArmed())
        { // is the Gate already armed.
          JOptionPane.showMessageDialog(null, "DAQ Card is already Armed.\n PLease Disarm before proceeding", "DAQ ARMING", JOptionPane.ERROR_MESSAGE);
        }
        else
        { // We are good to go - Arm it baby, and never say die.
          daqAccess.fireGateOnTest();
          if (daqAccess.hasError())
          { // Dag nab it all, We had some issue.  I am sure I am going to hear about this.
            String msg = "Daq Card ARM.\nEncountered an Error during processing!\n" + daqAccess.getErrorMsg();
            JOptionPane.showMessageDialog(null, msg, "DAQ LANE TEST", JOptionPane.ERROR_MESSAGE);
          }
          else
          { // Yeah baby, yeah, do I make you horny, do I?
NFORMATION_MESSAGE);
          }
        }
      }
      else
      { // When will they learn
        JOptionPane.showMessageDialog(null, "Daq Card Needs to be aquired\nBefore the ARM operation can be performed", "DAQ ARMING", JOptionPane.ERROR_MESSAGE);
      }
    }
    /**
     * Method disarmTheGate.  Test the Gate being disarmed
     */
    private void disarmTheGate()
    {
      if (daqDigIOEngaged)
      { // We have been engaged, continue processing
        daqAccess.fireGateOffTest();
        if (daqAccess.hasError())
        { // Go Love a duck, we have another error
          String msg = "Daq Card DISARM.\nEncountered an Error during processing!\n" + daqAccess.getErrorMsg();
          JOptionPane.showMessageDialog(null, msg, "DAQ LANE TEST", JOptionPane.ERROR_MESSAGE);
        }
        else
        { // Shagadelic baby, yeah
nPane.INFORMATION_MESSAGE);
        }
      }
      else
      { // Another one that does not follow directions
        JOptionPane.showMessageDialog(null, "Daq Card Needs to be aquired\nBefore the DISARM operation can be performed", "DAQ DISARMING",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
    /**
     * Method fireLanesTriggerTest.  Test the Lanes mechanism
     */
    private void fireLanesTriggerTest()
    {
      if (daqDigIOEngaged)
      { // We have been engaged, continue processing
        if (daqAccess.isGateArmed())
        { // is the Gate already armed.
          JOptionPane.showMessageDialog(null, "DAQ Card is already Armed.\n PLease Disarm before proceeding", "DAQ LANE TEST", JOptionPane.ERROR_MESSAGE);
        }
        else
        { // Fire at will baby.
          int value = timeoutValue.getValue();
          String msg = "Prior to Lane Test\nBe prepared to manually flag all lanes\nBefore the Timeout Value of [" + value + "] seconds";
          JOptionPane.showMessageDialog(null, msg, "DAQ LANE TEST", JOptionPane.INFORMATION_MESSAGE);
          // Refresh the display
          daqAccess.fireReadLaneSignalsTest();
          if (daqAccess.hasError())
          { // GEEEEZ, here we go again
            msg = "Daq Card LANE TEST.\nEncountered an Error during processing!\n" + daqAccess.getErrorMsg();
            JOptionPane.showMessageDialog(null, msg, "DAQ LANE TEST", JOptionPane.ERROR_MESSAGE);
          }
          else
            boolean[] taggedLanes = daqAccess.getTaggedLanes();
            for (int i = 0; i < taggedLanes.length; i++)
            {
              trackTestLights[i].showGoodBad(taggedLanes[i]);
            }
            msg = "Daq Card LANE TEST,\nGood Lanes are GREEN,\nBad are RED";
            JOptionPane.showMessageDialog(null, msg, "DAQ LANE TEST", JOptionPane.INFORMATION_MESSAGE);
          }
        }
      }
      else
      { // This is going nowhere, How many times I said , read the manual
        JOptionPane.showMessageDialog(null, "Daq Card Needs to be aquired\nBefore the LANE TEST operation can be performed", "DAQ LANE TEST",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
    /**
     * Method setDaqAccessTimeout.  
     * 
     */
    private void setDaqAccessTimeout()
    {
      if (daqDigIOEngaged)
      {
        int value = timeoutValue.getValue();
        daqAccess.setTimeout(value);
      }
      else
      {
        JOptionPane.showMessageDialog(null, "Daq Card Needs to be aquired\nBefore the TIMEOUT operation can be performed", "DAQ TIMEOUT",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  /**
   * Constructor for SetupPanel. Constructor initializes the Panel
   * @param mainApp - RaceARama, the Main Applicaiton
   */
  public SetupPanel( RaceARama mainApp )
  {
    this.mainApp = mainApp;
    init();
  }
  /**
   * Method calcTrackLengthAndHeightSettings.  
   * Method will initiate the the calcualtion of the Track Performance and Display 
   * the values if there are no errors - Otherwise a Message Box will appear 
   * with the Error Value.
   */
  private void calcTrackLengthAndHeightSettings()
  {
    TrackUtils trackUtils = TrackUtils.getInstance();
    trackUtils.clearTrackSegments();
    for (int index = 0; index < MAX_ITEMS; index++)
    {
      int lengthValue = lengths[index].getValue();
      int heightValue = heights[index].getValue();
      trackUtils.addSegment(lengthValue, heightValue);
    }
    // Initiate a Check For any Errors. 
    // Set a Time of 5 Minutes in Seconds for the Calculation.
    // This will ensure we are not faster than the Fastest Car.
    final int MINUTE = 60;
    CarPerformance cp = trackUtils.computePerformance(MINUTE * 5);
    if (cp.hasError())
    {
      String msg = cp.getErrorMsg() + ",\nPlease adjust any segments neccessary";
      JOptionPane.showMessageDialog(mainApp, msg, "Error on Track Lengths", JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      idealAverageSpeed.setValue(trackUtils.getIdealAverageSpeed());
      idealFinishSpeed.setValue(trackUtils.getIdealFinishSpeed());
      idealTotalTime.setValue(trackUtils.getIdealTotalTime());
      fullTrackLength.setValue(trackUtils.getTrackLengthInches());
    }
  }
  /**
   * Method clearTrackLengthAndHeightSettings.  Method Will Clear all values stored in the 
   * TrackUtils object and TextFields Displayed on the Screen.
   */
  private void clearTrackLengthAndHeightSettings()
  {
    TrackUtils trackUtils = TrackUtils.getInstance();
    trackUtils.clearTrackSegments();
    for (int index = 0; index < MAX_ITEMS; index++)
    {
      lengths[index].setValue(0);
      heights[index].setValue(0);
      int lengthValue = lengths[index].getValue();
      int heightValue = heights[index].getValue();
      trackUtils.addSegment(lengthValue, heightValue);
    }
    trackUtils.save();
    idealAverageSpeed.setValue(trackUtils.getIdealAverageSpeed());
    idealFinishSpeed.setValue(trackUtils.getIdealFinishSpeed());
    idealTotalTime.setValue(trackUtils.getIdealTotalTime());
    fullTrackLength.setValue(trackUtils.getTrackLengthInches());
  }
  /**
   * Method createAllPanels.  Creates all Sub Panels for this Panel
   */
  private void createAllPanels()
  {

    myLayout = new CardLayout();
    cardPanel = new JPanel(myLayout);
    cardPanel.add(createStepOnePanel(), STEP_ONE);
    cardPanel.add(createStepTwoPanel(), STEP_TWO);

    this.setLayout(new BorderLayout());
    this.add(cardPanel, BorderLayout.CENTER);
  }

  /**
   * Method createStepOnePanel.  Creates a panel for the Step one of the Setup page.  This is mainly
   * Track Details.
   * @return GridBagHelper
   */
  private GridBagHelper createStepOnePanel()
  {
    GridBagHelper stepOnePanel = new GridBagHelper();
    final int SPAN_1 = 1;
    final int SPAN_2 = 2;
    //    final int SPAN_3 = 3;
    final int SPAN_4 = 4;

    saveSettingsBtn = new JButton("Save");
    saveSettingsBtn.setActionCommand(SAVE_TRACK);
    saveSettingsBtn.addActionListener(actionTrigger);
    clearSettingsBtn = new JButton("Clear");
    clearSettingsBtn.setActionCommand(CLEAR_TRACK);
    clearSettingsBtn.addActionListener(actionTrigger);

    JPanel trackUtilsPanel = getTrackUtilsPanel();
    trackUtilsPanel.setBorder(BorderFactory.createTitledBorder("Track Performance"));
    JPanel lengthsPanel = getLengthPanel();
    JPanel heightsPanel = getHeightPanel();

    int row = 1;
    int col = 1;

    GridBagHelper lengthHeightPanel = new GridBagHelper();
    lengthHeightPanel.addComponent(lengthsPanel, row, col, SPAN_2, SPAN_1);
    lengthHeightPanel.addComponent(heightsPanel, ++row, col, SPAN_2, SPAN_1);
    lengthHeightPanel.addComponent(clearSettingsBtn, ++row, col, SPAN_1, SPAN_1, GridBagConstraints.EAST, GridBagConstraints.RELATIVE);
    lengthHeightPanel.addComponent(saveSettingsBtn, row, ++col, SPAN_1, SPAN_1, GridBagConstraints.EAST, GridBagConstraints.REMAINDER);
    lengthHeightPanel.setBorder(BorderFactory.createTitledBorder("Track Length/Height"));

    row = 1;
    col = 1;
    stepOnePanel.addComponent(lengthHeightPanel, row, col);
    stepOnePanel.addAnchoredComponent(trackUtilsPanel, row, ++col, GridBagConstraints.NORTHEAST);

    row++;
    col = 1;

    // We no longer need the Dummy Panel, As we now have a new Panel to display, 
    // the Graph Configuration of the Track
    // JPanel temp = new JPanel();
    JPanel temp = TrackUtils.getInstance().getTrackGraph();
    temp.setPreferredSize(FieldLengths.HUGE_FIELD);
    stepOnePanel.addComponent(temp, row, col, SPAN_4, SPAN_4, GridBagConstraints.SOUTH, GridBagConstraints.BOTH);
    stepOnePanel.setBorder(BorderFactory.createTitledBorder("Track Setup Details"));

    return stepOnePanel;
  }
  /**
   * Method createStepTwoPanel.  Creates a panel for the Step of the Setup panel.
   * This is mainly Track Testing Details
   * @return JPanel
   */
  private JPanel createStepTwoPanel()
  {
    JButton trackTestBtn = new JButton("Track Setup");
    trackTestBtn.setActionCommand(STEP_ONE);
    trackTestBtn.addActionListener(actionTrigger);

    daqCardName.setText("");
    daqCardName.setEditable(false);
    daqCardName.setForeground(Color.BLUE);
    JButton daqCardAquire = new JButton("Aquire");
    daqCardAquire.setActionCommand(DAQ_AQUIRE);
    daqCardAquire.addActionListener(actionTrigger);
    JPanel tmp0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    tmp0.add(daqCardName);
    tmp0.add(daqCardAquire);
    tmp0.add(trackTestBtn);

    JPanel p1 = new JPanel(new GridLayout(1, 1));
    p1.setBorder(BorderFactory.createTitledBorder("DAQ IO Card Name"));
    p1.add(tmp0);

    JLabel lbl2 = new JLabel("Gate Arm/Disarm");
    JPanel tmp1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    tmp1.add(lbl2);
    JButton armGate = new JButton("Arm");
    armGate.setActionCommand(ARM_GATE);
    armGate.addActionListener(actionTrigger);
    JButton disarmGate = new JButton("Disarm");
    disarmGate.setActionCommand(DISARM_GATE);
    disarmGate.addActionListener(actionTrigger);
    JPanel tmp2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    tmp2.add(armGate);
    tmp2.add(disarmGate);
    JPanel p2 = new JPanel(new GridLayout(2, 1));
    p2.setBorder(BorderFactory.createTitledBorder("Gate Functions"));
    p2.add(tmp1);
    p2.add(tmp2);

    JLabel lbl3 = new JLabel("Timeout (secs)");
    timeoutValue.setEnabled(true);
    timeoutValue.setForeground(Color.BLUE);
    JButton saveTimeout = new JButton("Set Value");
    saveTimeout.setActionCommand(SET_TIMEOUT_VALUE);
    saveTimeout.addActionListener(actionTrigger);
    tmp1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    tmp1.add(lbl3);
    tmp2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    tmp2.add(timeoutValue);
    tmp2.add(saveTimeout);
    JPanel p3 = new JPanel(new GridLayout(2, 1));
    p3.setBorder(BorderFactory.createTitledBorder("Time Out Settings"));
    p3.add(tmp1);
    p3.add(tmp2);

    JPanel lightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton fireLanes = new JButton("Lane Test");
    fireLanes.setActionCommand(FIRE_LANES);
    fireLanes.addActionListener(actionTrigger);
    JPanel tmp3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    tmp3.add(fireLanes);
    for (int i = 0; i < trackTestLights.length; i++)
    {
      trackTestLights[i] = new LightLabel(i + 1);
      trackTestLights[i].setVerticalTextPosition(JLabel.TOP);
      trackTestLights[i].setHorizontalTextPosition(JLabel.CENTER);
      lightPanel.add(trackTestLights[i]);
    }
    JPanel p4 = new JPanel(new GridLayout(2, 1));
    p4.add(lightPanel);
    p4.add(tmp3);
    p4.setBorder(BorderFactory.createTitledBorder("Lane Validation and Testing"));

    JPanel northPanel = new JPanel(new GridLayout(1, 1));
    northPanel.add(p1);

    JPanel center1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    center1.add(p2);
    center1.add(p3);
    JPanel center2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    center2.add(p4);
    JPanel centerPanel = new JPanel(new GridLayout(2, 1));
    centerPanel.add(center1);
    centerPanel.add(center2);

    JPanel stepTwoPanel = new JPanel(new BorderLayout());
    stepTwoPanel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
    stepTwoPanel.add(northPanel, BorderLayout.NORTH);
    stepTwoPanel.add(centerPanel, BorderLayout.CENTER);
    stepTwoPanel.add(new JLabel(""), BorderLayout.SOUTH);

    return stepTwoPanel;
  }

  /**
   * Method debug.  Sends a Debug Message to the Logger.
   * @param s - String, the Message to Send
   */
  private void debug( String s )
  {
    s = "SetupPanel: " + s;
    Log.debug(s);
  }
  /**
   * Method getHeightPanel.  Creates a Panel with all the Height Segment Labels and Text Boxes
   * @return JPanel - The Panel Containing the Fields
   */
  private JPanel getHeightPanel()
  {
    TrackUtils trackUtils = TrackUtils.getInstance();
    JLabel[] heightsLbl = new JLabel[MAX_ITEMS];
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    for (int index = 0; index < heightsLbl.length; index++)
    {
      int heightValue = trackUtils.getHeightSegment(index);
      heightsLbl[index] = new JLabel("H" + (index + 1));
      heightsLbl[index].setToolTipText(getString("SetupPanel.heights.label.tooltip_" + index));
      heights[index] = new IntegerTextField(heightValue, 3, 3);
      heights[index].setToolTipText(getString("SetupPanel.heights.label.tooltip_" + index));
      JPanel p = new JPanel(new GridLayout(2, 1));
      p.add(heightsLbl[index]);
      p.add(heights[index]);
      panel.add(p);
    }
    return panel;
  }
  /**
   * Method getLengthPanel.  Creates a Panel with all the Length Segment Labels and Text Boxes
   * @return JPanel - The Panel Containing the Fields
   */
  private JPanel getLengthPanel()
  {
    TrackUtils trackUtils = TrackUtils.getInstance();
    JLabel[] lengthsLbl = new JLabel[MAX_ITEMS];
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    for (int index = 0; index < lengthsLbl.length; index++)
    {
      int lengthValue = trackUtils.getLengthSegment(index);
      lengthsLbl[index] = new JLabel("L" + (index + 1));
      lengthsLbl[index].setToolTipText(getString("SetupPanel.lengths.label.tooltip_" + index));
      lengths[index] = new IntegerTextField(lengthValue, 3, 3);
      lengths[index].setToolTipText(getString("SetupPanel.lengths.label.tooltip_" + index));
      JPanel p = new JPanel(new GridLayout(2, 1));
      p.add(lengthsLbl[index]);
      p.add(lengths[index]);
      panel.add(p);
    }
    return panel;
  }
  /**
   * Method getString.  Returns a String Located in the Data Source 
   * @param key - String, the Key is the Lookup value in the data source
   * @return String - The result Value desired.
   */
  private String getString( String key )
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
   * Method getTrackUtilsPanel.  Returns a Panel that Contains the Performance information from TrackUtils
   * @return JPanel - The Panel Containing the Fields
   */
  private JPanel getTrackUtilsPanel()
  {
    TrackUtils trackUtils = TrackUtils.getInstance();

    JLabel lblTrackLength = new JLabel("Track Length");
    fullTrackLength.setEditable(false);
    fullTrackLength.setValue(trackUtils.getTrackLengthInches());
    JPanel p1 = new JPanel(new GridLayout(2, 1));
    p1.add(lblTrackLength);
    p1.add(fullTrackLength);

    JLabel lblIdealTime = new JLabel("Ideal Time");
    idealTotalTime.setEditable(false);
    idealTotalTime.setValue(trackUtils.getIdealTotalTime());
    JPanel p2 = new JPanel(new GridLayout(2, 1));
    p2.add(lblIdealTime);
    p2.add(idealTotalTime);

    JLabel lblAverageSpeed = new JLabel("Ideal Avg mph");
    idealAverageSpeed.setEditable(false);
    idealAverageSpeed.setValue(trackUtils.getIdealTotalTime());
    JPanel p3 = new JPanel(new GridLayout(2, 1));
    p3.add(lblAverageSpeed);
    p3.add(idealAverageSpeed);

    JLabel lblFinishSpeed = new JLabel("Ideal Finish mph");
    idealFinishSpeed.setEditable(false);
    idealAverageSpeed.setValue(trackUtils.getIdealTotalTime());
    JPanel p4 = new JPanel(new GridLayout(2, 1));
    p4.add(lblFinishSpeed);
    p4.add(idealFinishSpeed);

    JButton trackTestBtn = new JButton("Track Test");
    trackTestBtn.setActionCommand(STEP_TWO);
    trackTestBtn.addActionListener(actionTrigger);
    JPanel p5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    p5.setBorder(BorderFactory.createTitledBorder("Track Test Functions"));
    p5.add(trackTestBtn);

    JPanel perfPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    perfPanel.add(p1);
    perfPanel.add(p2);
    perfPanel.add(p3);
    perfPanel.add(p4);

    JPanel panel = new JPanel(new GridLayout(2, 1));
    panel.add(perfPanel);
    panel.add(p5);

    return panel;

  }
  /**
   * Method init.  Sets up the Basic Initialization for this Panel
   */
  private void init()
  {
    this.createAllPanels();
  }

}
