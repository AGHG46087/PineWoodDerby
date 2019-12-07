/*
 * @author:		Owner
 * date:		Jan 14, 2004
 * Package:		com.boyscouts.domain
 * File Name:		LightLabel.java
 */
package com.boyscouts.domain;

import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * author:      hgrein<BR>
 * date:        Jun 2, 2004<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   LightLabel.java<BR>
 * Type Name:   LightLabel<BR>
 * Description: Presents a Label as Image in Red, Yellow, Green
 */

public class LightLabel extends JLabel
{

  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3258131362362897206L;

  // The variables are for the Images.  
  /**
   * Field <code>yellowIcon</code> : ImageIcon
   * 
   * @uml.property name="yellowIcon"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private ImageIcon yellowIcon = null;

  /** Field <code>yellowImage</code> : Image */
  private transient Image yellowImage = null;
  /** Field <code>yellowImageOriginal</code> : Image */
  private transient Image yellowImageOriginal = null;

  /**
   * Field <code>redIcon</code> : ImageIcon
   * 
   * @uml.property name="redIcon"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private ImageIcon redIcon = null;

  /** Field <code>redImage</code> : Image */
  private transient Image redImage = null;
  /** Field <code>redImageOriginal</code> : Image */
  private transient Image redImageOriginal = null;

  /**
   * Field <code>greenIcon</code> : ImageIcon
   * 
   * @uml.property name="greenIcon"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private ImageIcon greenIcon = null;

  /** Field <code>greenImage</code> : Image */
  private transient Image greenImage = null;
  /** Field <code>greenImageOriginal</code> : Image */
  private transient Image greenImageOriginal = null;

  /** Field <code>SLEEP_DURATION</code> : int,  1 Millisecond of time, ( 1/1000 of a second )*/
  private static final int SLEEP_DURATION = 1;

  /**
   * Constructor for LightLabel. 
   * 
   */
  public LightLabel()
  {
    this("");
  }
  /**
   * Constructor for LightLabel. 
   * @param number - int
   */
  public LightLabel( int number )
  {
    this("" + number);
  }
  /**
   * Constructor for LightLabel. 
   * @param text
   */
  public LightLabel( String text )
  {
    super(text);
    loadImage();
    showYellow();
  }

  /**
   * Method showYellow.  
   * 
   */
  public void showYellow()
  {
    setIcon(yellowIcon);
  }
  /**
   * Method showRed.  
   * 
   */
  public void showRed()
  {
    setIcon(redIcon);
  }
  /**
   * Method showGreen.  
   * 
   */
  public void showGreen()
  {
    setIcon(greenIcon);
  }

  /**
   * Method loadImage.  Loads the images of the Lights., red, green, yellow.
   * 
   */
  private void loadImage()
  {
    String imageName = "";
    ImageIcon ii = null;
    // Since we will need to load the Graphics images - 
    // Lets give a small Moment of time for the Machine to catch up.
    // and not kill our machine,  
    try
    {
      Thread.sleep(SLEEP_DURATION);
    }
    catch (InterruptedException exc)
    { // Swallow exception
    }
    // Continue processing.
    imageName = "/resources/images/yellow_light.gif";
    // Load the Image Icon, and Get the Image to display into the original Label
    ii = new ImageIcon(LightLabel.class.getResource(imageName));
    yellowImageOriginal = ii.getImage();

    imageName = "/resources/images/red_light.gif";
    // Load the Image Icon, and Get the Image to display into the original Label
    ii = new ImageIcon(LightLabel.class.getResource(imageName));
    redImageOriginal = ii.getImage();

    imageName = "/resources/images/green_light.gif";
    // Load the Image Icon, and Get the Image to display into the original Label
    ii = new ImageIcon(LightLabel.class.getResource(imageName));
    greenImageOriginal = ii.getImage();

    // Scale the image by the percentage described.
    int width = 16; // yellowImageOriginal.getWidth(this) * percent / 100;
    int height = 14; //yellowImageOriginal.getHeight(this) * percent / 100;
    // Set the width of the Card Label to something that is more appropiate

    super.setSize(width, height);
    // Get a copy of a Scaled down to size image of the Card
    yellowImage = yellowImageOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    redImage = redImageOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    greenImage = greenImageOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);

    // Track the Loading of the Image so as to not over tax the system
    MediaTracker tracker = new MediaTracker(this);
    // Load the images to be tracked by the Media Tracker
    tracker.addImage(yellowImage, 1);
    tracker.addImage(redImage, 2);
    tracker.addImage(greenImage, 3);

    try
    { // Wait until we have the image loaded into memory
      tracker.waitForAll(1);
    }
    catch (InterruptedException exc)
    { // Swallow this exception
    }

    // Now set our Image Icons to the appropiate image that was loaded.
    yellowIcon = new ImageIcon(yellowImage);
    redIcon = new ImageIcon(redImage);
    greenIcon = new ImageIcon(greenImage);
  }
  /**
   * Method showGoodBad.  This Method will either display a RED or GREEN Icon, Depending
   * on the Value, true indicates GOOD ( GREEN ), false indicates BAD (RED)
   * @param goodBad - boolean
   */
  public void showGoodBad( boolean goodBad )
  {
    if (goodBad)
    {
      this.showGreen();
    }
    else
    {
      this.showRed();
    }
  }
}