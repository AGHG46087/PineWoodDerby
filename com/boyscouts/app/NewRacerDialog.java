/*
 * @author:		Owner
 * date:		Dec 10, 2003
 * Package:		cis163aa.demos.layouts
 * File Name:		NewRacerDialog.java
 */
package com.boyscouts.app;

/**
 * author:      Owner
 * date:        Dec 10, 2003
 * Package:     cis163aa.demos.layouts
 * File Name:   NewRacerDialog.java
 * Type Name:   NewRacerDialog
 * Description: Demontrates the GridBag Helper
 */
import javax.swing.*;
import javax.swing.border.*;

import com.boyscouts.domain.PhoneNumber;
import com.boyscouts.domain.RacerPerson;
import com.hgutil.swing.GridBagHelper;
import com.hgutil.swing.IntegerTextField;
import com.hgutil.swing.MaxLengthTextField;

import java.awt.*;
import java.awt.event.*;

public class NewRacerDialog extends JDialog
{

  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3257002150969358388L;

  /**
   * Field <code>district</code> : MaxLengthTextField
   * 
   * @uml.property name="district"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private MaxLengthTextField district = new MaxLengthTextField(0, 20);

  /**
   * Field <code>pack</code> : MaxLengthTextField
   * 
   * @uml.property name="pack"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private MaxLengthTextField pack = new MaxLengthTextField(0, 20);

  /**
   * Field <code>den</code> : MaxLengthTextField
   * 
   * @uml.property name="den"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private MaxLengthTextField den = new MaxLengthTextField(0, 20);

  /**
   * Field <code>lastName</code> : MaxLengthTextField
   * 
   * @uml.property name="lastName"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private MaxLengthTextField lastName = new MaxLengthTextField(0, 30);

  /**
   * Field <code>firstName</code> : MaxLengthTextField
   * 
   * @uml.property name="firstName"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private MaxLengthTextField firstName = new MaxLengthTextField(0, 30);

  /**
   * Field <code>address</code> : MaxLengthTextField
   * 
   * @uml.property name="address"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private MaxLengthTextField address = new MaxLengthTextField(0, 40);

  /**
   * Field <code>city</code> : MaxLengthTextField
   * 
   * @uml.property name="city"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private MaxLengthTextField city = new MaxLengthTextField(0, 20);

  /**
   * Field <code>state</code> : MaxLengthTextField
   * 
   * @uml.property name="state"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private MaxLengthTextField state = new MaxLengthTextField(0, 2);

  /**
   * Field <code>postal</code> : IntegerTextField
   * 
   * @uml.property name="postal"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private IntegerTextField postal = new IntegerTextField(85000, 5, 5);

  /**
   * Field <code>areaCode</code> : IntegerTextField
   * 
   * @uml.property name="areaCode"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private IntegerTextField areaCode = new IntegerTextField(602, 3, 3);

  /**
   * Field <code>phone</code> : IntegerTextField
   * 
   * @uml.property name="phone"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private IntegerTextField phone = new IntegerTextField(1111111, 7, 7);

  /**
   * Field <code>vehicleNumber</code> : IntegerTextField
   * 
   * @uml.property name="vehicleNumber"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private MaxLengthTextField vehicleNumber = new MaxLengthTextField(0, 20);

  /**
   * Field <code>submitBtn</code> : JButton
   * 
   * @uml.property name="submitBtn"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JButton submitBtn = null;

  /**
   * Field <code>mainApp</code> : RaceARama
   * 
   * @uml.property name="mainApp"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RaceARama mainApp = null;

  /**
   * Field <code>editableRacer</code> : RacerPerson
   * 
   * @uml.property name="editableRacer"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private RacerPerson editableRacer = null;

  /** Field <code>SUBMIT</code> : String */
  private static final String SUBMIT = "SUBMIT";
  /** Field <code>EDIT</code> : String */
  private static final String EDIT = "EDIT";
  /** Field <code>CANCEL</code> : String */
  private static final String CANCEL = "CANCEL";

  /**
   * Field <code>boxes</code> : JTextField[]
   * 
   * @uml.property name="boxes"
   * @uml.associationEnd multiplicity="(0 -1)"
   */
  private JTextField[] boxes = {district, pack, den, lastName, firstName, address, city, state, postal, areaCode, phone, vehicleNumber};

  /**
   * author:      Owner
   * date:        Dec 11, 2003
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
      if (cmd.equals(CANCEL))
      {
        readyToClose = true;
        editableRacer = null;
      }
      if (cmd.equals(SUBMIT))
      {
        if (!isAllFieldsPopulated())
        {
          JOptionPane.showMessageDialog(null, "ActionCmd: [" + cmd + "] All Fields must be Populated!", "information", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
          RacerPerson rp = new RacerPerson();
          populateRacerPerson(rp);
          editableRacer = rp;
          readyToClose = true;
        }
      }
      else if (cmd.equals(EDIT))
      {
        if (!isAllFieldsPopulated())
        {
          JOptionPane.showMessageDialog(null, "ActionCmd: [" + cmd + "] All Fields must be Populated!", "information", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
          populateRacerPerson(editableRacer);
          readyToClose = true;
        }
      }

      if (readyToClose)
      {
        if (mainApp == null)
        {
          System.out.println(editableRacer);
          System.exit(0);
        }
        else
        {
          NewRacerDialog.this.dispose();
        }
      }
    }

    /**
     * Method populateRacerPerson.  
     * @param rp - RacerPerson, the data to be updated from populated fields on Screen
     */
    private void populateRacerPerson( RacerPerson rp )
    {
      rp.setDistrict(district.getText());
      rp.setPack(pack.getText());
      rp.setDen(den.getText());
      rp.setLastName(lastName.getText());
      rp.setFirstName(firstName.getText());
      rp.setAddress(address.getText());
      rp.setCity(city.getText());
      rp.setState(state.getText());
      rp.setPostal(postal.getText());
      PhoneNumber ph = new PhoneNumber(areaCode.getText(), phone.getText());
      rp.setPhone(ph);
      rp.setVehicleNumber(vehicleNumber.getText());
    }
  }
  /**
   * Constructor for NewRacerDialog. 
   * @param frame - The Main Frame
   */
  public NewRacerDialog( JFrame frame )
  {
    super(frame, true);
    init(frame, false);
  }
  /**
   * Constructor for NewRacerDialog. 
   * @param frame - The Main Frame.
   * @param rp - RacerPerson, The Person To Edit.
   */
  public NewRacerDialog( JFrame frame, RacerPerson rp )
  {
    super(frame, true);
    init(frame, (rp == null));
    submitBtn.setActionCommand(EDIT);
    editableRacer = rp;
    populateFields(rp);
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
   * Method geteditableRacer.  Returns editableRacer of the NewRacerDialog
   * @return RacerPerson: Returns the editableRacer.
   * 
   * @uml.property name="editableRacer"
   */
  public RacerPerson getEditableRacer()
  {
    return editableRacer;
  }

  /**
   * Method init.  Create The Panel and lays all the Data on the Screen
   * @param frame
   */
  private void init( JFrame frame, boolean isNewRacer )
  {
    if (frame instanceof RaceARama)
    {
      mainApp = (RaceARama) frame;
    }
    String title = (isNewRacer) ? "New" : "Edit";
    title += " Racer Person";
    setTitle(title);
    setSize(500, 300);
    setResizable(false);
    centerOnScreen();

    // Creates the helper class panel to hold all my components
    GridBagHelper panel = new GridBagHelper();
    // give the panel a border gap of 5 pixels
    panel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
    getContentPane().add(BorderLayout.CENTER, panel);

    // Define preferred sizes for my entry fields
    Dimension shortField = new Dimension(40, 20);

    final int COL_SPAN_1 = 1;
    final int COL_SPAN_2 = 2;
    final int COL_SPAN_3 = 3;
    final int COL_SPAN_4 = 4;
    final int COL_SPAN_6 = 6;
    final int ROW_SPAN_1 = 1;
    int row = 1;
    int col = 1;
    // District label and field - Note We are using Rows 1 
    JLabel lbl1 = new JLabel("District");
    panel.addComponent(lbl1, row, col); // row=1, col=1
    district.setPreferredSize(shortField); //mediumField );
    panel.addFilledComponent(district, row, ++col, COL_SPAN_6, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);

    // Pack label and field - Note We are using Rows 2
    row++;
    col = 1;
    JLabel lbl2 = new JLabel("Pack");
    panel.addComponent(lbl2, row, col);
    pack.setPreferredSize(shortField); //mediumField );
    panel.addFilledComponent(pack, row, ++col, COL_SPAN_2, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);

    // Den label and field - Note We are using Rows 2
    col += COL_SPAN_2;
    JLabel lbl3 = new JLabel("Den");
    panel.addComponent(lbl3, row, col);
    den.setPreferredSize(shortField); //mediumField );
    panel.addFilledComponent(den, row, ++col, COL_SPAN_3, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);

    // LastName label and field - Note We are using Rows 4
    row++;
    col = 1;
    JLabel lbl4 = new JLabel("Last Name");
    panel.addComponent(lbl4, row, col);
    lastName.setPreferredSize(shortField);
    panel.addFilledComponent(lastName, row, ++col, COL_SPAN_4, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);

    // FirstName label and field - Note We are using Rows 5
    row++;
    col = 1;
    JLabel lbl5 = new JLabel("First Name");
    panel.addComponent(lbl5, row, col);
    firstName.setPreferredSize(shortField);
    panel.addFilledComponent(firstName, row, ++col, COL_SPAN_4, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);

    // Address label and field - Note We are using Rows 6
    row++;
    col = 1;
    JLabel lbl6 = new JLabel("Address");
    panel.addComponent(lbl6, row, col);
    address.setText("123 SomeStreet");
    address.setPreferredSize(shortField); //mediumField );
    panel.addFilledComponent(address, row, ++col, COL_SPAN_6, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);

    // City label and field - Note We are using Rows 7
    row++;
    col = 1;
    JLabel lbl7 = new JLabel("City");
    panel.addComponent(lbl7, row, col);
    city.setText("Somewhere");
    city.setPreferredSize(shortField); //mediumField );
    panel.addFilledComponent(city, row, ++col, COL_SPAN_2, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);
    //      panel.addFilledComponent( city, row, col+1, COL_SPAN_2, ROW_SPAN_1, GridBagConstraints.HORIZONTAL );

    // State label and field - Note We are using Rows 7
    col += COL_SPAN_2;
    JLabel lbl8 = new JLabel("State");
    panel.addComponent(lbl8, row, col);
    state.setText("AZ");
    state.setPreferredSize(shortField);
    panel.addFilledComponent(state, row, ++col, COL_SPAN_1, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);

    // Postal label and field - Note We are using Rows 9
    col += COL_SPAN_1;
    JLabel lbl9 = new JLabel("Zip");
    lbl9.setHorizontalAlignment(JLabel.RIGHT);
    panel.addComponent(lbl9, row, col);
    postal.setPreferredSize(shortField);
    panel.addFilledComponent(postal, row, ++col, COL_SPAN_1, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);

    // areaCode label and field - Note We are using Rows 9
    row++;
    col = 1;
    JLabel lbl10 = new JLabel("Phone");
    panel.addComponent(lbl10, row, col);
    areaCode.setPreferredSize(shortField);
    panel.addFilledComponent(areaCode, row, ++col, COL_SPAN_1, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);

    // areaCode label and field - Note We are using Rows 9
    col += COL_SPAN_1;
    phone.setPreferredSize(shortField);
    panel.addFilledComponent(phone, row, col, COL_SPAN_1, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);

    // areaCode label and field - Note We are using Rows 9
    row++;
    col += COL_SPAN_1;
    JLabel lbl11 = new JLabel("Car#");
    panel.addComponent(lbl11, row, col);
    vehicleNumber.setPreferredSize(shortField);
    panel.addFilledComponent(vehicleNumber, row, ++col, COL_SPAN_1, ROW_SPAN_1, GridBagConstraints.HORIZONTAL);

    ActionTrigger actionTrigger = new ActionTrigger();
    // Okay button

    submitBtn = new JButton("Submit");
    submitBtn.setActionCommand(SUBMIT);
    submitBtn.addActionListener(actionTrigger);
    panel.addFilledComponent(submitBtn, 1, 8);

    // Cancel button
    JButton cancelBtn = new JButton("Cancel");
    cancelBtn.setActionCommand(CANCEL);
    cancelBtn.addActionListener(actionTrigger);
    panel.addComponent(cancelBtn, 2, 8, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

    if (mainApp == null)
    {
      setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
    }
    else
    {
      setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }
  }
  /**
   * Method isAllFieldsPopulated.  Method to verify all the Fields are populated.
   * @return boolean, true - All Data is populated.  False, Something is missing.
   */
  private boolean isAllFieldsPopulated()
  {
    boolean rc = true;
    for (int i = 0; i < boxes.length; i++)
    {
      if ((boxes[i] != null) && (boxes[i].getText().trim().length() == 0))
      {
        rc = false;
      }
    }

    return rc;
  }
  /**
   * Method populateFields.  
   * @param rp - Racerperson, Populate all the Text Fields from the DataStructure ebig used to edit.
   */
  private void populateFields( RacerPerson rp )
  { // Define some Default Values as some of th Kids/parents may not want to disclose the Address.
    String defaultAddr = (rp.getAddress().trim().length() == 0) ? rp.getAddress() : "123 SomeStreet";
    String defaultCity = (rp.getCity().trim().length() == 0) ? rp.getCity() : "Somewhere";
    String defaultState = (rp.getState().trim().length() == 0) ? rp.getState() : "AZ";
    String defaultPostal = (rp.getPostal().trim().length() == 0) ? rp.getPostal() : "85000";
    district.setText(rp.getDistrict());
    pack.setText(rp.getPack());
    den.setText(rp.getDen());
    lastName.setText(rp.getLastName());
    firstName.setText(rp.getFirstName());
    address.setText(defaultAddr);
    city.setText(defaultCity);
    state.setText(defaultState);
    postal.setText(defaultPostal);
    PhoneNumber ph = rp.getPhone();
    areaCode.setText(ph.getAreaCode());
    phone.setText(ph.getNumber());
    vehicleNumber.setText(rp.getVehicleNumber());
  }
  /**
   * Method main.  
   * @param args
   */
  public static void main( String[] args )
  {
    RacerPerson rp = new RacerPerson();
    rp.setDistrict("Phoenix");
    rp.setPack("Bear");
    rp.setDen("cubs");
    rp.setLastName("fools");
    rp.setFirstName("iama_goofy");
    rp.setAddress("12345 W. Foobar.");
    rp.setCity("Phoenix");
    rp.setState("az");
    rp.setPostal("12345");
    PhoneNumber ph = new PhoneNumber("602", "123-4567");
    rp.setPhone(ph);
    rp.setVehicleNumber("1");
    new NewRacerDialog(new JFrame(), rp);
  }

}
