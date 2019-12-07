/*
 * @author: Owner 
 * date: Dec 8, 2003 
 * Package: com.boyscouts.app File Name:
 * RegistrationPanel.java
 */
package com.boyscouts.app;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Timer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumnModel;

import com.boyscouts.database.DeleteRacerPersonDBTask;
import com.boyscouts.database.InsertRacerPersonDBTask;
import com.boyscouts.database.UpdateRacerPersonDBTask;
import com.boyscouts.domain.FieldLengths;
import com.boyscouts.domain.Log;
import com.boyscouts.domain.RacerContainer;
import com.boyscouts.domain.RacerPerson;
import com.boyscouts.domain.RacerPersonFieldName;
import com.boyscouts.domain.RegisteredRacersTableModel;
import com.boyscouts.domain.WinnerRaceContainer;
import com.hgmenu.HGMenuItem;
import com.hgtable.HGTable;
import com.hgtable.HGTableModel;
import com.hgutil.swing.GridBagHelper;
import com.hgutil.swing.event.PopupListener;

/**
 * author: Owner 
 * date: Dec 8, 2003 
 * Package: com.boyscouts.app 
 * File Name: RegistrationPanel.java 
 * Type Name: RegistrationPanel 
 * Description: Creates the Registration Panel.  The Registration Panel provides support for 
 * Adding/Editing/Deleting Racers
 */

public class RegistrationPanel extends GridBagHelper implements FieldLengths
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 4048795667143078962L;
  /** Field <code>NEW_RACER</code> : String */
  private static final String NEW_RACER = "NEW_RACER";
  /** Field <code>EDIT_RACER</code> : String */
  private static final String EDIT_RACER = "EDIT_RACER";
  /** Field <code>REMOVE_RACER</code> : String */
  private static final String REMOVE_RACER = "REMOVE_RACER";
  /** Field <code>SEARCH_RACER</code> : String */
  private static final String SEARCH_RACER = "SEARCH_RACER";

  /**
   * Field <code>mainApp</code> : RaceARama
   * 
   * @uml.property name="mainApp"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RaceARama mainApp = null;

  /**
   * Field <code>popupListener</code> : PopupListener
   * 
   * @uml.property name="popupListener"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private PopupListener popupListener = null;

  /**
   * Field <code>actionTrigger</code> : ActionTrigger
   * 
   * @uml.property name="actionTrigger"
   * @uml.associationEnd multiplicity="(1 1)" inverse="this$0:com.boyscouts.app.RegistrationPanel$ActionTrigger"
   */
  private ActionTrigger actionTrigger = new ActionTrigger();

  /**
   * Field <code>table</code> : HGTable
   * 
   * @uml.property name="table"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="com.boyscouts.domain.RacerPerson"
   */
  private HGTable table = null;

  /**
   * Field <code>searchBar</code> : JTextField
   * 
   * @uml.property name="searchBar"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JTextField searchBar = null;

  /**
   * Field <code>radioButtonGroup</code> : ButtonGroup
   * 
   * @uml.property name="radioButtonGroup"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="javax.swing.JRadioButton"
   */
  ButtonGroup radioButtonGroup = null;

  /**
   * Field <code>racerCount</code> : JLabel
   * 
   * @uml.property name="racerCount"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  JLabel racerCount = null;

  /** Field <code>currentRaceNumber</code> : int */
  private int currentRaceNumber;

  /**
   * author:      Owner
   * date:        Dec 8, 2003
   * Package:     com.boyscouts.app
   * File Name:   RegistrationPanel.java
   * Type Name:   ActionTrigger
   * Description: Provides the mouse click events for the outer class
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
      debug("Command recieved: " + cmd);

      if (cmd.equals(NEW_RACER))
      {
        tableAddNewRacer();
      }
      else if (cmd.equals(EDIT_RACER))
      {
        tableEditCurrentRacer();
      }
      else if (cmd.equals(REMOVE_RACER))
      {
        tableRemoveCurrentRacer();
      }
      else if (cmd.equals(SEARCH_RACER))
      {
        searchForRacer();
      }
      else
      {
        JOptionPane.showMessageDialog(RegistrationPanel.this, "ActionCmd: [" + cmd + "] option not supported at this time", "information",
                                      JOptionPane.INFORMATION_MESSAGE);
      }
    }

  }

  /**
   * author:      Owner
   * date:        Dec 8, 2003
   * Package:     com.boyscouts.app
   * File Name:   RegistrationPanel.java
   * Type Name:   ActionTrigger
   * Description: Provides the mouse click events for the outer class
   */
  private class PopupTrigger implements ActionListener
  {
    /** Field <code>INSERT_ROW_TXT</code> : String */
    private final String INSERT_ROW_TXT = getString("RegistrationPanel.popup.insert_row.text");
    /** Field <code>DELETE_ROW_TXT</code> : String */
    private final String DELETE_ROW_TXT = getString("RegistrationPanel.popup.delete_row.text");
    /** Field <code>REMOVE_ROW_TXT</code> : String */
    private final String REMOVE_ROW_TXT = getString("RegistrationPanel.popup.remove_row.text");
    /** Field <code>EDIT_ROW_TXT</code> : String */
    private final String EDIT_ROW_TXT = getString("RegistrationPanel.popup.edit_row.text");
    /**
     * Overridden Method:  
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param evt
     */
    public void actionPerformed( ActionEvent evt )
    {
      String cmd = evt.getActionCommand();
      debug("Command recieved: " + cmd);
      if (cmd.equals(INSERT_ROW_TXT))
      {
        tableAddNewRacer();
      }
      else if (cmd.equals(EDIT_ROW_TXT))
      {
        tableEditCurrentRacer();
      }
      else if (cmd.equals(REMOVE_ROW_TXT))
      {
        tableRemoveCurrentRacer();
      }
      else if (cmd.equals(DELETE_ROW_TXT))
      {
        tableDeleteCurrentRacer();
      }
      else
      {
        JOptionPane.showMessageDialog(RegistrationPanel.this, "PopupCmd: [" + cmd + "] option not supported at this time", "information",
                                      JOptionPane.INFORMATION_MESSAGE);
      }
    }

  }

  /**
   * Constructor for RegistrationPanel. 
   * @param mainApp
   */
  public RegistrationPanel( RaceARama mainApp )
  {
    this.mainApp = mainApp;

    initialize();
  }
  /**
   * Method to add a popup menu to the JTable
   * Creation date: (01/11/2002 10:49:20 AM)
   * @param table A JTable The Display table used for graphical decisions
   */
  private void addPopupMenuToTable( HGTable table )
  {

    if (popupListener == null)
    {
      popupListener = new PopupListener(createPopupMenu());
    }

    table.addMouseListener(popupListener);
  }
  /**
   * Method addRacerToContainer.  This Will Add The Data to the Container, and Update the Row Count.
   * NOTE:  The Same does not exits for the DELETE, As The Table will control wether a Object can be deleted.
   * Meaning that it will not delete the Row if it is the LAst in the List.  
   * @param rp - RacerPerson
   * @return boolean
   */
  private boolean addRacerToContainer( RacerPerson rp )
  {
    boolean rc = mainApp.getRacersContainer().addRacerPerson(rp);
    processAnyUpdates();
    return rc;
  }
  /**
   * Method createEditButtonsPanel.  Creates the Edit Buttons To Add, Edit, Delete USers.
   * @return JPanel
   */
  private JPanel createEditButtonsPanel()
  {
    JButton newRacer = new JButton("Add");
    newRacer.setActionCommand(NEW_RACER);
    newRacer.addActionListener(actionTrigger);

    JButton editRacer = new JButton("Edit");
    editRacer.setActionCommand(EDIT_RACER);
    editRacer.addActionListener(actionTrigger);

    JButton deleteRacer = new JButton("Remove");
    deleteRacer.setActionCommand(REMOVE_RACER);
    deleteRacer.addActionListener(actionTrigger);

    GridBagHelper panel = new GridBagHelper();
    panel.setBorder(BorderFactory.createTitledBorder("Modify"));
    panel.addFilledComponent(newRacer, 1, 1);
    panel.addFilledComponent(editRacer, 2, 1);
    panel.addComponent(deleteRacer, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
    return panel;
  }
  /**
   * Creates a popup edit menu for the table
   */
  private JPopupMenu createPopupMenu()
  {
    // Add all the Horizontal elements
    JPopupMenu result = null;
    String insertRowText = getString("RegistrationPanel.popup.insert_row.text");
    String removeRowText = getString("RegistrationPanel.popup.remove_row.text");
    String deleteRowText = getString("RegistrationPanel.popup.delete_row.text");
    String editRowText = getString("RegistrationPanel.popup.edit_row.text");
    //    String printListCmd = getString("RegistrationPanel.popup.print_list.text");
    //    String printRacerDetails = getString("RegistrationPanel.popup.print_racer.text");
    String controlPanelText = getString("RegistrationPanel.popup.print_racer.text");
    PopupTrigger popupTrigger = new PopupTrigger();
    result = HGMenuItem.makePopupMenu(new Object[]{insertRowText, editRowText, removeRowText, null, deleteRowText, null, controlPanelText}, popupTrigger);
    // Set the Alignment and return the MenuBar
    result.setAlignmentX(JMenuBar.LEFT_ALIGNMENT);
    return result;
  }
  /**
   * Method createRacerCountPanel.  
   * @return JPanel
   */
  private JPanel createRacerCountPanel()
  {
    racerCount = new JLabel("0");
    GridBagHelper panel = new GridBagHelper();
    panel.setBorder(BorderFactory.createTitledBorder("Racer Count"));
    panel.addComponent(racerCount, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
    return panel;
  }
  /**
   * Method createRacersScrollTable.  Creates a ScrollPane.  The Scroll Pane will contain
   * the HGTable wchich is a Model of the Racers Container.  In addition it will aslo remove from the
   * Table disply the Fields not desired to be viewed.
   * @return JScrollPane
   */
  private JScrollPane createRacersScrollTable()
  {
    // Get a Reference to the Container
    RacerContainer racerContainer = mainApp.getRacersContainer();
    // Create the Table Model and Table
    HGTableModel tableModel = new RegisteredRacersTableModel(racerContainer);
    table = new HGTable(tableModel);
    table.setRowSelectionAllowed(true);
    table.setColumnSelectionAllowed(false);
    table.setMinWidthToText(true);
    table.setToolTipText("Registered Racers");
    addPopupMenuToTable(table);

    RacerPersonFieldName[] removeFields = {RacerPersonFieldName.ID, RacerPersonFieldName.MIN_VALUE, RacerPersonFieldName.MAX_VALUE,
                                           RacerPersonFieldName.AVG_VALUE, RacerPersonFieldName.STD_DEV, RacerPersonFieldName.RACE_NAME,
                                           RacerPersonFieldName.PLACEMENT};
    // Begin Removal of Columns not desired to be seen.
    TableColumnModel tableColumnModel = table.getColumnModel();
    for (int colIndex = 0; colIndex < removeFields.length; colIndex++)
    {
      String temp = "" + removeFields[colIndex];
      tableColumnModel.removeColumn(table.getColumn(temp));
    }
    table.tableChanged(new TableModelEvent(tableModel));
    table.repaint();
    // Now Create the Scroll Pane.
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.getViewport().add(table);
    scrollPane.setPreferredSize(FieldLengths.HUGE_FIELD);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Racers"));

    return scrollPane;
  }
  /**
   * Method createSearchBarPanel.  Creates a Small Panel initally used for a Search Engine.
   * @return JPanel
   */
  private JPanel createSearchBarPanel()
  {
    searchBar = new JTextField();
    searchBar.setPreferredSize(FieldLengths.LONGER_FIELD);

    JRadioButton radioLast = new JRadioButton(RacerPersonFieldName.LAST_NAME.toString(), true);
    JRadioButton radioPhone = new JRadioButton(RacerPersonFieldName.PHONE.toString(), false);
    JRadioButton radioDen = new JRadioButton(RacerPersonFieldName.DEN.toString(), false);
    JRadioButton radioPack = new JRadioButton(RacerPersonFieldName.PACK.toString(), false);
    radioButtonGroup = new ButtonGroup();
    radioButtonGroup.add(radioLast);
    radioButtonGroup.add(radioPhone);
    radioButtonGroup.add(radioDen);
    radioButtonGroup.add(radioPack);

    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
    p.add(radioLast);
    p.add(radioPhone);
    p.add(radioDen);
    p.add(radioPack);

    JButton searchBtn = new JButton("Search");
    searchBtn.addActionListener(actionTrigger);
    searchBtn.setActionCommand(SEARCH_RACER);

    GridBagHelper panel = new GridBagHelper();
    panel.setBorder(BorderFactory.createTitledBorder("Search"));
    panel.addFilledComponent(searchBar, 1, 1, 1, 1, GridBagConstraints.RELATIVE);
    panel.addFilledComponent(searchBtn, 1, 2, 1, 1, GridBagConstraints.RELATIVE);
    panel.addFilledComponent(p, 1, 3, 1, 1, GridBagConstraints.REMAINDER);

    return panel;

  }
  /**
   * Method databaseDeleteRacerPerson.  Spwan A Task to Delete the Racer
   * @param rp - RacerPerson the Person for which all Records are being deleted
   */
  private void databaseDeleteRacerPerson( RacerPerson rp )
  {
    Timer timer = new Timer();
    timer.schedule(new DeleteRacerPersonDBTask(rp), 100, 100);
  }
  /**
   * Method databaseInsertRacerPerson.  Spawn a Task to Insert a new Racer
   * @param rp - RacerPerson, The Person that is being added to the Database
   */
  private void databaseInsertRacerPerson( RacerPerson rp )
  {
    Timer timer = new Timer();
    timer.schedule(new InsertRacerPersonDBTask(rp), 100, 100);
  }
  /**
   * Method databaseUpdateRacerPerson.  Spwan a Task to Update a Racer
   * @param rp - RacerPerson, the ERpson that is being Updated in the Database
   */
  private void databaseUpdateRacerPerson( RacerPerson rp )
  {
    Timer timer = new Timer();
    timer.schedule(new UpdateRacerPersonDBTask(rp), 100, 100);
  }
  /**
   * Method debug.
   * 
   * @param s
   */
  protected void debug( String s )
  {
    s = "RegistrationPanel: " + s;
    Log.debug(s);
  }
  /**
   * Method getSelectedSearchRadioBtnField.  Returns the FieldName for the Selected Radion Button
   * @return RacerPersonFieldName
   */
  private RacerPersonFieldName getSelectedSearchRadioBtnField()
  {
    Enumeration radios = radioButtonGroup.getElements();
    String temp = null;
    while (radios.hasMoreElements() && temp == null)
    {
      JRadioButton radioBtn = (JRadioButton) radios.nextElement();
      if (radioBtn.isSelected())
      {
        temp = radioBtn.getText();
      }
    }
    RacerPersonFieldName field = RacerPersonFieldName.getRacerPersonFieldName(temp);
    return field;
  }
  /**
   * Method getString. Method returns a String from a Specified Key
   * @param key
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
   * Method init.  Initializes the Panel and initiates all creation of the Compoentns on the Panel.
   * This Panel is a Sub Class Of GridBagHelper ( GridBagLayout )
   */
  private void initialize()
  {
    Border loweredBorder = new CompoundBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED), new EmptyBorder(5, 5, 5, 5));
    this.setBorder(loweredBorder);
    JScrollPane scrollPane = createRacersScrollTable();
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    this.addFilledComponent(scrollPane, 1, 1, 4, 3, GridBagConstraints.BOTH);
    this.addComponent(createEditButtonsPanel(), 1, 5, 1, 2, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
    this.addComponent(createRacerCountPanel(), 3, 5, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL);
    this.addFilledComponent(createSearchBarPanel(), 4, 2, 5, 1, GridBagConstraints.BOTH);
    processAnyUpdates();
  }
  /**
   * Method processAnyUpdates.  
   * 
   */
  private void processAnyUpdates()
  {
    racerCount.setText("" + mainApp.getRacersContainer().getSize());
    mainApp.fireClientUpdates(false);
  }
  /**
   * Method resetRaceRegistrants.  
   * 
   */
  private void resetRaceRegistrants()
  {
    this.removeAll();
    initialize();
    this.repaint();
  }
  /**
   * Method searchForRacer.  Provides the Means Necessary to Collect a Searchable Racer(s) 
   * and Edit and Insert into the Registered List.  The Process first reads the data form the Search Box Text Field
   * and the Selected Radio Button.  Passing the information on to the SearchRacerDialog.
   * Upon Return from the SearchRacerDialog, we ask the for the selected Racers.  Note, this value could be null, 
   * if the user selected cancel.  If we have a record and size is at least 1 racer, ask the user for some options.
   * Do we want to register only, register and edit, or cancel.  <BR>
   * On Register only it will add all Racers to the Race Registration, and nothing more.<BR>
   * On Register and Edit, We will register the Racers and provide an edit opportunity for each racer.<BR>
   * On Cancel.  Well enough said there.<BR>
   */
  private void searchForRacer()
  {
    RegisteredRacersTableModel dataModel = (RegisteredRacersTableModel) table.getModel();
    int row = table.rowAtPoint(new java.awt.Point(popupListener.getX(), popupListener.getY()));
    row = (row < 0) ? ((table.getSelectedRow() >= 0) ? table.getSelectedRow() : 0) : row;
    String value = searchBar.getText();
    RacerPersonFieldName field = getSelectedSearchRadioBtnField();
    // Search For the Racers
    SearchRacerDialog searchRacer = new SearchRacerDialog(mainApp, value, field);
    // Get the Selected Racers
    RacerContainer rc = searchRacer.getSelectedRacers();
    if (rc != null && rc.getSize() > 0)
    { // Ok, we have 1 or more racers, let her rip baby!
      int containerSize = rc.getSize();
      // OK if we are set to Add, loop through all the elements and add them to the container
      for (int i = 0; i < containerSize; i++)
      {
        RacerPerson rp = rc.elementAt(i);
        if (rp != null)
        { // We are ready, Do we want to Edit the Racers
          // OK Babay, slam him in there
          if (addRacerToContainer(rp))
          { // The Record does not already Exits, We need to update the Table, And
            // EDIT/Update DB.
            table.tableChanged(new TableModelEvent(dataModel, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
            row += (mainApp.getRacersContainer().getSize() == 1) ? 0 : 1;
            table.setRowSelectionInterval(row, row);
            table.repaint();
            databaseUpdateRacerPerson(rp);
          }
        }
      }
    }
  }
  /**
   * Method setupRaceOfTopRacers.  
   * 
   */
  public void setupRaceOfTopRacers()
  {
    resetRaceRegistrants();
    RegisteredRacersTableModel dataModel = (RegisteredRacersTableModel) table.getModel();
    int row = table.rowAtPoint(new java.awt.Point(popupListener.getX(), popupListener.getY()));
    row = (row < 0) ? ((table.getSelectedRow() >= 0) ? table.getSelectedRow() : 0) : row;
    WinnerRaceContainer winnerCont = WinnerRaceContainer.getInstance();
    Vector keys = winnerCont.getRaceKeys();
    RacerContainer rc = null;
    for (int i = 0; i < keys.size(); i++)
    { // Get the Key of the container and the Container for that key.
      String keyItem = "" + keys.elementAt(i);
      rc = winnerCont.getRacerContainer(keyItem);
      rc.resetContainedRacerData();

      if (rc != null && rc.getSize() > 0)
      { // Ok, we have 1 or more racers, let her rip baby!
        int containerSize = rc.getSize();
        // OK if we are set to Add, loop through all the elements and add them to the container
        for (int j = 0; j < containerSize; j++)
        {
          RacerPerson rp = rc.elementAt(j);
          if (rp != null)
          { // We are ready, Do we want to Edit the Racers
            // OK Babay, slam him in there
            if (addRacerToContainer(rp))
            { // The Record does not already Exits, We need to update the Table, And
              // EDIT/Update DB.
              table.tableChanged(new TableModelEvent(dataModel, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
              row += (mainApp.getRacersContainer().getSize() == 1) ? 0 : 1;
              table.setRowSelectionInterval(row, row);
              table.repaint();
              databaseUpdateRacerPerson(rp);
            }
          }
        }
      }
    }
    // Clear the Winner Circle container.
    winnerCont.clear();
  }
  /**
   * Method addNewRacerToTable.  This Method will invoke the NewRacerDialog and show it to the user.
   * After the User has filled in the Form, it will add the record ( provided the user correctly
   * submitted, the record.  if the Record retured from the dialog is null, there is no other action.
   * If the record is valid, it will inwsert it into the container/table and spawn a task to add it 
   * to the DB.
   */
  private void tableAddNewRacer()
  {
    RegisteredRacersTableModel dataModel = (RegisteredRacersTableModel) table.getModel();
    int row = table.rowAtPoint(new java.awt.Point(popupListener.getX(), popupListener.getY()));
    row = (row < 0) ? ((table.getSelectedRow() >= 0) ? table.getSelectedRow() : 0) : row;

    NewRacerDialog newRacer = new NewRacerDialog(mainApp);
    newRacer.setVisible(true);
    RacerPerson rp = newRacer.getEditableRacer();
    if (addRacerToContainer(rp))
    { // The Racer Did not Exist, Update the Table, and Insert into the Database
      table.tableChanged(new TableModelEvent(dataModel, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
      row += (mainApp.getRacersContainer().getSize() == 1) ? 0 : 1;
      table.setRowSelectionInterval(row, row);
      table.repaint();
      databaseInsertRacerPerson(rp);
    }
  }
  /**
   * Method deleteCurrentRacerFromTable.   This method will Delete a Record from the 
   * Table and Database
   */
  private void tableDeleteCurrentRacer()
  {
    RegisteredRacersTableModel dataModel = (RegisteredRacersTableModel) table.getModel();
    int row = table.rowAtPoint(new java.awt.Point(popupListener.getX(), popupListener.getY()));
    row = (row < 0) ? ((table.getSelectedRow() >= 0) ? table.getSelectedRow() : 0) : row;

    RacerPerson rp = (RacerPerson) dataModel.getData().elementAt(row);
    String temp = "Are you sure you want to Delete user?\n" + "This is Permanent, The Racer and All Details\n" + "will be deleted from the Database.\n"
                  + "To REMOVE only, Click \"No\" and try the Table Popup Menu option.\n" + "Last:    = [" + rp.getLastName() + "]\n" + "First:   = ["
                  + rp.getFirstName() + "]\n" + "Vehicle: = [" + rp.getVehicleNumber() + "]";
    int n = JOptionPane.showConfirmDialog(mainApp, temp, "Delete Registered Racer from Database", JOptionPane.YES_NO_OPTION);
    // If the User Selected YES, and We have successult Deleted Row.  Meaning that the Table
    // Has Deleted and Returned True for Confirmation, then We can continue the process.
    if ((n == JOptionPane.YES_OPTION) && (dataModel.delete(row)))
    {
      table.tableChanged(new TableModelEvent(dataModel, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
      row -= (row == 0) ? 0 : 1; // Reset the Row to ensure we are in Range.
      table.setRowSelectionInterval(row, row);
      table.repaint();
      databaseDeleteRacerPerson(rp);
      processAnyUpdates();
    }
  }
  /**
   * Method editCurrentRacerInTable.  This Method will invoke the NewRacerDialog and show it to the user.
   * After the User has filled in the Form, it will add the record ( provided the user correctly
   * submitted, the record.  if the Record retured from the dialog is null, there is no other action.
   * If the record is valid, it will update it into the container/table and spawn a task to update it 
   * to the DB.
   */
  private void tableEditCurrentRacer()
  {
    RegisteredRacersTableModel dataModel = (RegisteredRacersTableModel) table.getModel();
    int row = table.rowAtPoint(new java.awt.Point(popupListener.getX(), popupListener.getY()));
    row = (row < 0) ? ((table.getSelectedRow() >= 0) ? table.getSelectedRow() : 0) : row;

    RacerPerson rp = (RacerPerson) dataModel.getData().elementAt(row);
    NewRacerDialog newRacer = new NewRacerDialog(mainApp, rp);
    newRacer.setVisible(true);
    rp = newRacer.getEditableRacer();
    if (rp != null)
    { // The Record already exists in the Container, Simply update it, do not re-insert
      table.tableChanged(new TableModelEvent(dataModel, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
      table.setRowSelectionInterval(row, row);
      table.repaint();
      databaseUpdateRacerPerson(rp);
    }
  }
  /**
   * Method deleteCurrentRacerFromTable.   This method will REMOVE a Record from the 
   * Table only and Delete the Race Details for the Racer, But LEaving the Racer himself,
   * in the Database
   */
  private void tableRemoveCurrentRacer()
  {
    RegisteredRacersTableModel dataModel = (RegisteredRacersTableModel) table.getModel();
    int row = table.rowAtPoint(new java.awt.Point(popupListener.getX(), popupListener.getY()));
    row = (row < 0) ? ((table.getSelectedRow() >= 0) ? table.getSelectedRow() : 0) : row;
    RacerPerson rp = (RacerPerson) dataModel.getData().elementAt(row);
    String temp = "Are you sure you want to Remove Racer from this Race?\n" + "Last:    = [" + rp.getLastName() + "]\n" + "First:   = [" + rp.getFirstName()
                  + "]\n" + "Vehicle: = [" + rp.getVehicleNumber() + "]";
    int n = JOptionPane.showConfirmDialog(mainApp, temp, "Remove Registered Racer from Race", JOptionPane.YES_NO_OPTION);
    // If the User Selected YES, and We have successult Deleted Row. Meaning
    // that the Table
    // Has Deleted and Returned True for Confirmation, then We can continue
    // the process.
    if ((n == JOptionPane.YES_OPTION) && (dataModel.delete(row)))
    {
      table.tableChanged(new TableModelEvent(dataModel, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
      row -= (row == 0) ? 0 : 1; // Reset the Row to ensure we are in Range.
      table.setRowSelectionInterval(row, row);
      table.repaint();
      processAnyUpdates();
    }
  }

  /**
   * Overridden Method:  
   * @see java.awt.Component#update(java.awt.Graphics)
   * @param g - Graphics
   */
  public void update( Graphics g )
  {
    int tempRaceNumb = RaceARama.getRaceCount();
    if (tempRaceNumb != this.currentRaceNumber)
    {
      resetRaceRegistrants();
      this.currentRaceNumber = tempRaceNumb;
    }
    super.update(g);
  }
}
