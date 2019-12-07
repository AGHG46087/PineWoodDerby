/*
 * Package:		com.boyscouts.app
 * File Name:	LaneSelectionDialog.java
 */
package com.boyscouts.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * author:      Hans-Jurgen Greiner<BR>
 * date:        May 21, 2004<BR>
 * Package:     com.boyscouts.app<BR>
 * File Name:   LaneSelectionDialog.java<BR>
 * Type Name:   LaneSelectionDialog<BR>
 * Description: Class Dialog to select which lanes of the track to use.
 */

public class LaneSelectionDialog extends JDialog
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3762532334540697907L;
  /** Field <code>LANES</code> : int */
  private static final int LANES = 12;
  /** Field <code>SUBMIT</code> : String */
  private static final String SUBMIT = "SUBMIT";
  /** Field <code>CANCEL</code> : String */
  private static final String CANCEL = "CANCEL";

  /**
   * Field <code>selectedLanes</code> : boolean[]
   * 
   * @uml.property name="selectedLanes"
   */
  private boolean[] selectedLanes = new boolean[LANES];

  /**
   * Field <code>checkLanes</code> : JCheckBox[]
   * 
   * @uml.property name="checkLanes"
   * @uml.associationEnd multiplicity="(0 -1)"
   */
  private JCheckBox[] checkLanes = new JCheckBox[LANES];

  /** Field <code>laneCount</code> : int */
  private int laneCount = LANES;

  /**
   * author:      Hans-Jurgen Greiner<BR>
   * date:        May 21, 2004<BR>
   * Package:     com.boyscouts.app<BR>
   * File Name:   LaneSelectionDialog.java<BR>
   * Type Name:   ActionTrigger<BR>
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
        readyToClose = validateSelectedLanes();
      }
      else if (cmd.equals(CANCEL))
      {
        readyToClose = true;
      }
      if (readyToClose)
      {
        LaneSelectionDialog.this.dispose();
      }
    }

  }

  /**
   * Constructor for LaneSelectionDialog. 
   * 
   */
  public LaneSelectionDialog( JFrame frame, int laneCount )
  {
    super(frame, true);
    this.laneCount = laneCount;
    initialize(frame);

    boolean displayDialog = (this.laneCount < LANES);
    this.setVisible(displayDialog);
    if (!displayDialog)
    {
      this.dispose();
    }
  }
  /**
   * Method centerOnParent.  Method will center the Dialog Box on the Screen with respect to the parent.
   */
  private void centerOnParent( JFrame frame )
  {
    if (frame == null)
    {
      return;
    }
    Dimension screenDim = frame.getSize();

    Rectangle screenRect = new Rectangle(0, 0, screenDim.width, screenDim.height);

    // Make sure we don't place the demo off the screen.
    int centerWidth = screenRect.width < this.getSize().width ? screenRect.x : screenRect.x + screenRect.width / 2 - this.getSize().width / 2;
    int centerHeight = screenRect.height < this.getSize().height ? screenRect.y : screenRect.y + screenRect.height / 2 - this.getSize().height / 2;

    // Now we just need to add the relative position of the Parent Frame
    Point parentPoint = frame.getLocationOnScreen();

    centerWidth += parentPoint.x;
    centerHeight += parentPoint.y;
    // Now we can set the Location To where We need to.
    this.setLocation(centerWidth, centerHeight);
  }

  /**
   * Method getselectedLanes.  Returns selectedLanes of the LaneSelectionDialog
   * @return boolean[]: Returns the selectedLanes.
   * 
   * @uml.property name="selectedLanes"
   */
  public boolean[] getSelectedLanes()
  {
    return selectedLanes;
  }

  /**
   * Method initialize.  
   * @param frame
   */
  private void initialize( JFrame frame )
  {
    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

    JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    // First init the CheckBoxes and the selected lanes to their inital state.
    // And Add them to the panel
    for (int i = 0; i < LANES; i++)
    {
      String text = "" + (i + 1);
      selectedLanes[i] = (i < laneCount);
      checkLanes[i] = new JCheckBox(text, selectedLanes[i]);
      checkLanes[i].setHorizontalTextPosition(JCheckBox.CENTER);
      checkLanes[i].setVerticalTextPosition(JCheckBox.TOP);
      checkPanel.add(checkLanes[i]);
    }

    ActionTrigger actionTrigger = new ActionTrigger();
    // Submit Button
    JButton submitBtn = new JButton("Submit");
    submitBtn.setActionCommand(SUBMIT);
    submitBtn.addActionListener(actionTrigger);
    // Cancel button
    JButton cancelBtn = new JButton("Cancel");
    cancelBtn.setActionCommand(CANCEL);
    cancelBtn.addActionListener(actionTrigger);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.add(submitBtn);
    btnPanel.add(cancelBtn);

    JLabel descriptionText = new JLabel("<html>There are [" + laneCount + "] racers in the current race.<br>Please select the lanes to be used.</html>");
    JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    textPanel.add(descriptionText);

    JPanel allData = new JPanel(new GridLayout(3, 1, 10, 10));
    allData.add(textPanel);
    allData.add(checkPanel);
    allData.add(btnPanel);

    //    this.setLayout(new BorderLayout());
    //    this.add(allData, BorderLayout.CENTER);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(allData, BorderLayout.CENTER);

    setSize(350, 200);
    centerOnParent(frame);

  }
  /**
   * Method updateSelectedLanes.  Updates the selectedLanes array and returns the count of selected Lanes.
   */
  private int updateSelectedLanes()
  {
    int retVal = 0;
    for (int i = 0; i < LANES; i++)
    {
      selectedLanes[i] = checkLanes[i].isSelected();
      retVal += (selectedLanes[i]) ? 1 : 0;
    }
    return retVal;
  }
  /**
   * Method validateSelectedLanes.  Counts the tagged array and returns the count of selected Lanes.
   * If the lanes count is valid than it will return true.  If the Lane count is invalid it will 
   * return false.
   * @return boolean - true if valid, otherwise false.
   */
  private boolean validateSelectedLanes()
  {
    int count = 0;
    boolean retVal = false;
    for (int i = 0; i < LANES; i++)
    {
      count += (checkLanes[i].isSelected()) ? 1 : 0;
    }
    retVal = (count == this.laneCount);
    if (!retVal)
    {
      String temp = (count < this.laneCount) ? "is less than the minimum" : "is greater than the maximum";
      String msg = "<HTML>The <B><FONT face=arial color=#FF0000>" + count + "</FONT></B>" + " lanes selected,<BR>" + temp + " required lanes of "
                   + "<B><FONT face=arial color=#FF0000>" + this.laneCount + "</FONT></B>,<BR> please adjust the total "
                   + "selected lanes to <B><FONT face=arial color=#FF0000>" + this.laneCount + "</FONT></B></HTML>";
      // Re-use temp for the title message
      temp = (count < this.laneCount) ? "too few lanes" : "too many lanes";
      JOptionPane.showMessageDialog(LaneSelectionDialog.this, msg, "Bad Lane Selection Count -" + temp, JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      updateSelectedLanes();
    }
    return retVal;
  }

  /**
   * Method main.  Test Main
   * @param args
   */
  public static void main( String[] args )
  {
    LaneSelectionDialog lsd = new LaneSelectionDialog(null, 11);
    boolean[] myLanes = lsd.getSelectedLanes();
    for (int i = 0; i < myLanes.length; i++)
    {
      System.out.println("LANE[" + i + "] = " + myLanes[i]);
    }
  }
}
