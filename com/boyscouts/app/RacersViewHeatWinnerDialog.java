/*
 * Package:		com.boyscouts.app
 * File Name:	RacersViewHeatWinnerDialog.java
 */
package com.boyscouts.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.boyscouts.domain.OverallWinPlaceShow;
import com.boyscouts.domain.RaceData;
import com.boyscouts.domain.RaceDataContainer;
import com.boyscouts.domain.RacerContainer;
import com.boyscouts.domain.RacerPerson;
import com.hgutil.data.FixedDouble;

/**
 * author:      Hans-Jurgen Greiner<BR>
 * Package:     com.boyscouts.app<BR>
 * File Name:   RacersViewHeatWinnerDialog.java<BR>
 * Type Name:   RacersViewHeatWinnerDialog<BR>
 * Description: Provides a view of the Winners for a current Heat.
 */

public class RacersViewHeatWinnerDialog extends JDialog
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3258409547378209077L;

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
   * Field <code>topRacers</code> : Vector
   * 
   * @uml.property name="topRacers"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="com.boyscouts.domain.RacerPerson"
   */
  private Vector topRacers = null;

  /**
   * Field <code>topRacerData</code> : Vector
   * 
   * @uml.property name="topRacerData"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="com.boyscouts.domain.RaceData"
   */
  private Vector topRacerData = null;

  /** Field <code>topRacerCount</code> : int */
  private int topRacerCount = 0;

  /**
   * Constructor for RacersViewHeatWinnerDialog. 
   * @param frame - JFrame, The Frame for the reference to be made
   * @param racersCont - RacersContainer, the Container of the All the racers
   * @param raceDataCont - RaceDataContainer, The Container of the Current Race Data
   */
  public RacersViewHeatWinnerDialog( JFrame frame, RaceDataContainer raceDataCont, RacerContainer racersCont )
  {
    super(frame, false);
    findTopRacers(racersCont, raceDataCont);
    initialize(frame);
    // If there was at least one racer that finished then display the Results
    // and cycle the winners.
    boolean displayDialog = (this.topRacerCount > 0);
    this.setVisible(displayDialog);
    if (displayDialog)
    {
      cycleWinnerDisplay();
    }
    else
    {
      this.dispose();
    }
  }
  /**
   * Method initialize.  Provides Initialization for the cards and shows the display
   */
  private void initialize( JFrame frame )
  {
    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    setSize(300, 200);
    centerOnParent(frame);

    myLayout = new CardLayout();
    cardPanel = new JPanel(myLayout);
    cardPanel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
    String panelID = null;
    for (int i = 0; i < topRacerCount; i++)
    { // Create a Panel Id using the Placement Value
      int placementValue = ((RaceData) topRacerData.elementAt(i)).getPlacementIntValue();
      panelID = "TOP_RACER_" + placementValue;
      cardPanel.add(createTopRacerPanel(i), panelID);
    }
    Container cont = this.getContentPane();
    cont.setLayout(new BorderLayout());
    cont.add(cardPanel, BorderLayout.CENTER);
//    this.setLayout(new BorderLayout());
//    this.add(cardPanel, BorderLayout.CENTER);

    panelID = "TOP_RACER_" + topRacerCount;
    myLayout.show(RacersViewHeatWinnerDialog.this.cardPanel, panelID);
    this.repaint();

  }
  /**
   * Method centerOnParent.  Method will center the Dialog Box on the Screen with respect to the parent.
   */
  private void centerOnParent( JFrame frame )
  {
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
   * Method cycleCardLayout.  
   * 
   */
  public void cycleWinnerDisplay()
  {
    /**
     * @author:     Hans-Jurgen Greiner<BR>
     * Package:     com.boyscouts.app<BR>
     * File Name:   RacersViewHeatWinnerDialog.java<BR>
     * Type Name:   CycleDisplayTask<BR>
     * Description: Cycles through the Display of Winners.
     */
    // A Class Task to Display the Cards in Reverse order,  Meaning the Highest to the Lowest.
    // Essentially it is a Count Down.
    class CycleDisplayTask extends TimerTask
    {
      private int currentCard = RacersViewHeatWinnerDialog.this.topRacerCount;

      /**
       * Overridden Method:  
       * @see java.lang.Runnable#run()
       * 
       */
      public void run()
      { // Ensure we have the Class Canceled as soon as it is done.
        if (currentCard <= 0)
        {
          this.cancel();
          RacersViewHeatWinnerDialog.this.setVisible(false);
          RacersViewHeatWinnerDialog.this.dispose();
          return;
        }
        String cardToDisplay = "TOP_RACER_" + currentCard;
        myLayout.show(RacersViewHeatWinnerDialog.this.cardPanel, cardToDisplay);
        currentCard--;
      }
    }
    Timer t = new Timer();
    t.schedule(new CycleDisplayTask(), 100, 3000);

  }
  /**
   * Method createTopRacerPanel.  Create a JPanel ( card ) for each of the Top Winners
   * @param i - index of the Top Racer
   * @return JPanel
   */
  private JPanel createTopRacerPanel( int i )
  {
    RacerPerson rp = (RacerPerson) topRacers.elementAt(i);
    RaceData rd = (RaceData) topRacerData.elementAt(i);

    String fname = rp.getFirstName();
    String lname = rp.getLastName();
    String carNumber = rp.getVehicleNumber();
    FixedDouble timeValue = rd.getTime();
    Integer lane = rd.getLane();

    String displayValue = "<html>NAME: <FONT face=arial color=#006699 size=+1>" + fname + " " + lname + "</FONT><BR>"
                          + "CAR:  <FONT face=arial color=#006699 size=+1>" + carNumber + "</FONT><BR>" + "LANE: <FONT face=arial color=#006699 size=+1>"
                          + lane + "</FONT><BR>" + "TIME: <FONT face=arial color=#006699 size=+1>" + timeValue + "</FONT></html>";

    JLabel nameLbl = new JLabel(displayValue);
    JLabel winnerLbl = OverallWinPlaceShow.getOverallWinPlaceShow(rd.getPlacement());

    JPanel retPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    retPanel.add(winnerLbl);
    retPanel.add(nameLbl);
    return retPanel;
  }
  /**
   * Method findTopRacers.  Loactes the Top Racers in the Containers and Stores off the Data for later use.
   * @param racersCont - RacersContainer, the Container of the All the racers
   * @param raceDataCont - RaceDataContainer, The Container of the Current Race Data
   */
  private void findTopRacers( RacerContainer racersCont, RaceDataContainer raceDataCont )
  {
    // Get the Number of Racer Data and the Vector of Race Data
    int racerDataCount = raceDataCont.getSize();
    Vector theDataOfRacers = raceDataCont.getRacerData();
    // Get the Total Number of Racers and all their data
    int totalRacersPersons = racersCont.getSize();
    Vector theRacers = racersCont.getRacers();
    // Create a vector to store their Data.
    topRacers = new Vector();
    topRacerData = new Vector();
    for (int i = 0; i < racerDataCount; i++)
    { // Get the Next Race Data Object
      RaceData rd = (RaceData) theDataOfRacers.elementAt(i);
      int placementValue = rd.getPlacementIntValue();
      // If the placement value is greater than 0 and less than or equal to 5
      // then process the winner.
      if (0 < placementValue && placementValue <= 5)
      {
        boolean foundRacer = false;
        for (int j = 0; (!foundRacer) && (j < totalRacersPersons); j++)
        { // Get the next RacePerson Object
          RacerPerson rp = (RacerPerson) theRacers.elementAt(j);
          // Look at the vehicle Number for a Match.
          if (rd.getVehicleNumber().equals(rp.getVehicleNumber()))
          { // Add the Racers and Thre Data to the Vectors for usage.
            topRacers.add(rp);
            topRacerData.add(rd);
            foundRacer = true;
          }
        }
      }
    }
    this.topRacerCount = topRacers.size();
  }

}
