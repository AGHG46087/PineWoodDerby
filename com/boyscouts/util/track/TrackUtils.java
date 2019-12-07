/*
 * @author:		Owner
 * date:		Dec 22, 2003
 * Package:		com.boyscouts.util.track
 * File Name:		TrackUtils.java
 */
package com.boyscouts.util.track;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JPanel;

import com.hgutil.ParseData;
import com.hgutil.data.FixedDouble;

/**
 * author:      hgrein<BR>
 * date:        Jun 7, 2004<BR>
 * Package:     com.boyscouts.util.track<BR>
 * File Name:   TrackUtils.java<BR>
 * Type Name:   TrackUtils<BR>
 * Description: Utilities for computations required throughout the program. This class is Singleton
 */

public class TrackUtils
{

  /**
   * Field <code>trackSegments</code> : TrackSegments
   * 
   * @uml.property name="trackSegments"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private TrackSegments trackSegments = null;

  /**
   * Field <code>panel</code> : JPanel
   * 
   * @uml.property name="panel"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JPanel panel = null;

  /**
   * Field <code>trackLengthInches</code> : double
   * 
   * @uml.property name="trackLengthInches"
   */
  private double trackLengthInches = -1;

  /**
   * Field <code>trackLengthFeet</code> : double
   * 
   * @uml.property name="trackLengthFeet"
   */
  private double trackLengthFeet = -1;

  /**
   * Field <code>idealTotalTime</code> : double
   * 
   * @uml.property name="idealTotalTime"
   */
  private double idealTotalTime = -1;

  /**
   * Field <code>idealFinishSpeed</code> : double
   * 
   * @uml.property name="idealFinishSpeed"
   */
  private double idealFinishSpeed = -1;

  /**
   * Field <code>idealAverageSpeed</code> : double
   * 
   * @uml.property name="idealAverageSpeed"
   */
  private double idealAverageSpeed = -1;

  /** Field <code>errorMessage</code> : String */
  private String errorMessage = null;
  /** Field <code>performanceCalculated</code> : boolean */
  private boolean performanceCalculated = false;

  /** Field <code>trackUtils</code> : TrackUtils */
  private static TrackUtils trackUtils = null;
  // Constants
  /** Field <code>SEGMENTS</code> : int */
  private static final int SEGMENTS = 8; // The Number of Track Segments
  /** Field <code>SEGMENTS_PLUS_ONE</code> : int */
  private static final int SEGMENTS_PLUS_ONE = 9; // The Number of Height Measurements
  /** Field <code>GRAVITY</code> : double */
  private static final double GRAVITY = 32.174; // Earth's surface gravitational acceleration
  /** Field <code>INCHES_TO_FEET</code> : double */
  private static final double INCHES_TO_FEET = 1.0 / 12.0; // Conversion from inches to feet
  /** Field <code>FEET_TO_INCHES</code> : double */
  private static final double FEET_TO_INCHES = 12.0; // Conversion from feet to inches
  /** Field <code>FEET_PER_SEC_TO_MPH</code> : double */
  private static final double FEET_PER_SEC_TO_MPH = 3600.0 / 5280.0; // Conversion from fps to mph
  /** Field <code>SAVE_DIR</code> : String */
  private static final String SAVE_DIR = "C:" + File.separator + "Temp" + File.separator + "RaceARama";
  /** Field <code>TRACK_SEGMENTS</code> : String */
  private static final String TRACK_SEGMENTS = "RaceTrackSegments.dat";

  /**
   * Method getInstance.  Returns the Singleton instance of TrackUtils
   * @return TrackUtils
   */
  public static TrackUtils getInstance()
  {
    if (trackUtils == null)
    {
      trackUtils = new TrackUtils();
    }
    return trackUtils;
  }
  /**
   * Method loadTrackSegments.  Method to load the Persistent Track Segments
   * @return TrackSegments, the Saved TrackSegments
   */
  private synchronized static TrackSegments loadTrackSegments()
  {
    TrackSegments trackSegs = null;
    File saveDir = new File(SAVE_DIR);
    File saveFile = new File(saveDir, TRACK_SEGMENTS);
    if (!saveDir.exists())
    {
      saveDir.mkdirs();
    }

    try
    {
      if (saveFile.exists() && saveFile.canRead())
      {
        // Open a new output stream to read the data
        java.io.ObjectInputStream istream = new java.io.ObjectInputStream(new java.io.FileInputStream(saveFile));

        // Read the data from the file
        trackSegs = (TrackSegments) istream.readObject();

        // Close the data
        istream.close();
      }

    }
    catch (ClassNotFoundException exc)
    {
      System.err.println("createDataContainer() - ClassNotFoundException: Failed reading data to [" + saveFile.getPath() + "]");
      System.err.println("createDataContainer() - exc info ==> " + exc);
    }
    catch (java.io.NotSerializableException exc)
    {
      System.err.println("createDataContainer() - NotSerializableException: Failed reading data to [" + saveFile.getPath() + "]");
      System.err.println("createDataContainer() - exc info ==> " + exc);
    }
    catch (java.io.IOException exc)
    {
      System.err.println("createDataContainer() - IOException: Failed reading data to [" + saveFile.getPath() + "]");
      System.err.println("createDataContainer() - exc info ==> " + exc);
    }
    if (trackSegs == null)
    {
      trackSegs = new TrackSegments();
    }
    else
    {
      for (int i = 0; i < trackSegs.numberOfSegements(); i++)
      {
        //        System.out.println("seg["+i+"] = h=["+trackSegs.getHeightSegment(i)+"], w=["+trackSegs.getLengthSegment(i)+"]");
      }
    }
    return trackSegs;
  }
  /**
   * Method saveData.  Method to maintain Persistance of a Vector Model to a filename
   * @param data - TrackSegments, the TrackSegments
   */
  private synchronized static void saveData( TrackSegments data )
  {
    File saveDir = new File(SAVE_DIR);
    File saveFile = new File(saveDir, TRACK_SEGMENTS);
    if (!saveDir.exists())
    {
      saveDir.mkdirs();
    }
    else if (saveFile.exists() && saveFile.canRead())
    {
      try
      {
        Thread.sleep(100);
        saveFile.delete();
      }
      catch (InterruptedException exc)
      {
      }
    }
    java.io.ObjectOutputStream ostream = null;
    try
    {
      // Open a new output stream to save the data
      ostream = new java.io.ObjectOutputStream(new java.io.FileOutputStream(saveFile));
      // Write the data to the file
      ostream.writeObject(data);
      // Close the data
      ostream.close();
    }
    catch (java.io.NotSerializableException exc)
    {
      System.err.println("serializeData() - NotSerializableException: Failed saving data to [" + saveFile.getPath() + "]");
      System.err.println("serializeData() - exc info ==> " + exc);
    }
    catch (java.io.IOException exc)
    {
      System.err.println("serializeData() - IOException: Failed saving data to [" + saveFile.getPath() + "]");
      System.err.println("serializeData() - exc info ==> " + exc);
    }
    // Enforce the idea of closing the output stream
    finally
    {
      if (ostream != null)
      {
        try
        {
          ostream.close();
        }
        catch (Exception exc)
        {
        }
      }
    }
  }
  /**
   * Constructor for TrackUtils. 
   */
  private TrackUtils()
  {
    trackSegments = loadTrackSegments();
    calculateIdealCarPerformance();
    panel = new TrackGraph(this.trackSegments);
  }
  /**
   * Method addSegment.  Adds a Segment via a Dimension Object.
   * Given the Width and height in a Dimesion Object.
   * @see com.boyscouts.util.track.TrackUtils#addSegment(int,int)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.lang.String, java.lang.String)
   * @param dim - Dimension, the Width and Height
   */
  public void addSegment( Dimension dim )
  {
    this.trackSegments.addSegment(dim);
    this.errorMessage = null;
    this.performanceCalculated = false;
  }
  /**
   * Method addSegment.  Adds a Segment via a int length and height. 
   * Given the Width and height in two parameters.
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.awt.Dimension)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.lang.String, java.lang.String)
   * @param length - int, The length in inches
   * @param height - int, The Height in inches
   */
  public void addSegment( int length, int height )
  {
    Dimension dim = new Dimension(length, height);
    this.addSegment(dim);
  }
  /**
   * Method addSegment.  Adds a Segment via a String length and height.
   * Given the Width and height in two parameters.
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.awt.Dimension)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(int,int)
   * @param length - String, The length in inches
   * @param height - String, The heigth in inches
   */
  public void addSegment( String length, String height )
  {
    int nLength = ParseData.parseNum(length, 0);
    int nHeight = ParseData.parseNum(height, 0);
    this.addSegment(nLength, nHeight);
  }
  /**
   * Method calculateIdealCarPerformance.  This method will calculate the the Track Length 
   * based on the segments, and then calculate the ideal Car Performance. The Ideal Car is a
   * car in a world where everything is Perfect. 
   */
  private synchronized void calculateIdealCarPerformance()
  {
    if (this.isReady())
    { // Initialize the total time and total track length to zero.
      this.errorMessage = null;
      double totalTime = 0;
      double totalTrackLength = 0;
      // Define (and initialize to zero) the arrays to hold the form inputs
      double[] trackLengths = new double[SEGMENTS];
      double[] trackHeights = new double[SEGMENTS_PLUS_ONE];
      double[] relativeHeights = new double[SEGMENTS];
      // Define (and intialize to zero) the necessary computational arrays
      double[] theta = new double[SEGMENTS];
      double[] accel = new double[SEGMENTS];
      double[] time = new double[SEGMENTS];
      double[] velocity = new double[SEGMENTS_PLUS_ONE];
      // Get the inputs from the form and convert to units of feet:
      for (int i = 0; i < SEGMENTS; i++)
      {
        int inches = trackSegments.getLengthSegment(0);
        trackLengths[i] = (inches * INCHES_TO_FEET);
      }
      for (int i = 0; i < SEGMENTS_PLUS_ONE; i++)
      {
        int inches = trackSegments.getHeightSegment(i);
        trackHeights[i] = (inches * INCHES_TO_FEET);
      }
      // Turn the absolute heights (from the floor) into relative heights.
      for (int i = 0; i < SEGMENTS; i++)
      {
        relativeHeights[i] = trackHeights[i] - trackHeights[i + 1];
      }
      // ERROR CHECKING ON INPUT TRACK CONFIGURATION
      // Check to make sure 1st Length of track is not zero
      if (trackLengths[1] <= 0.0)
      {
        this.errorMessage = "<html><h3>Error:</h3> First Track Segment Length must have a value Greater than 0...<BR>";
        this.errorMessage += "Track Segment L<SUB>1</SUB></html>";
      }
      else if (trackHeights[0] <= 0.0)
      {
        this.errorMessage = "<html><h3>Error:</h3> First Track Segment Height must have a value Greater than 0...<BR>";
        this.errorMessage += "Track Segment H<SUB>1</SUB></html>";
      }
      for (int i = 0; (i < SEGMENTS) && (errorMessage == null); i++)
      {
        if ((trackLengths[i] <= 0.0) && (relativeHeights[i] != 0.0))
        {
          this.errorMessage = "<html><h3>Error:</h3> Zero length segment, AND a non-zero height...<BR>";
          this.errorMessage += "Track Segment L<SUB>" + (i + 1) + "</SUB>, H<SUB>" + (i + 1) + "</SUB></html>";
        }
      }
      for (int i = 0; (i < SEGMENTS) && (errorMessage == null); i++)
      {
        // Negative input values on Lengths
        if (trackLengths[i] < 0.0)
        {
          this.errorMessage = "<html><h3>Error:</h3> Negative length...<BR>";
          this.errorMessage += "Track Segment L<SUB>" + (i + 1) + "</SUB></html>";
          continue;
        }
        // Negative input values on Heights
        if (trackHeights[i] < 0.0)
        {
          this.errorMessage = "<html><h3>Error:</h3> Negative height...<BR>";
          this.errorMessage += "Track Segment H<SUB>" + (i + 1) + "</SUB></html>";
          continue;
        }
        // Check for track segments taller than they are long
        if (relativeHeights[i] > trackLengths[i])
        {
          this.errorMessage = "<html><h3>Error:</h3> Track segment taller than it is long...<BR>";
          this.errorMessage += "Track Segment L<SUB>" + (i + 1) + "</SUB>, H<SUB>" + (i + 1) + "</SUB></html>";
          continue;
        }
        // Check for uphill legs
        if (relativeHeights[i] < 0.0)
        {
          this.errorMessage = "<html><h3>Error:</h3> Uphill Leg in track segment ...<BR>";
          this.errorMessage += "Track Segment L<SUB>" + (i + 1) + "</SUB>, H<SUB>" + (i + 1) + "</SUB></html>";
          continue;
        }
      }
      // Sum the lengths of the track segments into the total track length.
      for (int i = 0; (i < SEGMENTS) && (errorMessage == null); i++)
      {
        totalTrackLength += trackLengths[i];
      }
      // Compute the ramp angles and resulting component of gravitational
      // acceleration
      for (int i = 0; (i < SEGMENTS) && (errorMessage == null); i++)
      {
        if (trackLengths[i] <= 0.0)
        {
          theta[i] = 0.0;
        }
        else
        {
          theta[i] = Math.asin(relativeHeights[i] / trackLengths[i]);
        }
        accel[i] = GRAVITY * Math.sin(theta[i]);
      }
      // Use the Newtonion formula for the relationship between distance,
      // velocity, and acceleration: Xfinal = Xinitial + XDOTinitial * time +
      // 0.5 XDBLDOT * time^2
      // Turning into a 2nd order quadratic equation, we can solve using the
      // old quadratic formula: 
      // time = [-b +/- SQRT(b^2 - 4ac)] / 2a
      // Note: Because of the nature of our problem, we can forget about any
      // imaginary answers to the equation. Also, once we have the time, we
      // can calculate the final velocity as well 
      // (Vfinal = Vinitial + Acceleration * time).
      for (int i = 0; (i < SEGMENTS) && (errorMessage == null); i++)
      {
        double a = 0.5 * accel[i];
        double b = velocity[i];
        double c = -1.0 * trackLengths[i];
        double radical = (Math.pow(b, 2) - (4 * a * c));
        radical = Math.sqrt(radical);
        if (a == 0.0)
        {
          time[i] = (trackLengths[i] / b);
        }
        else
        {
          time[i] = (((-1.0 * b) + radical) / (2 * a));
        }
        totalTime += time[i];
        velocity[i + 1] = velocity[i] + (accel[i] * time[i]);
      }
      this.trackLengthFeet = totalTrackLength;
      this.trackLengthInches = totalTrackLength * FEET_TO_INCHES;
      this.idealTotalTime = totalTime;
      this.idealFinishSpeed = velocity[SEGMENTS_PLUS_ONE - 1] * FEET_PER_SEC_TO_MPH;
      this.idealAverageSpeed = (totalTrackLength / totalTime) * FEET_PER_SEC_TO_MPH;
      this.performanceCalculated = true;
      // Save our Details, just in Case
      TrackUtils.saveData(this.trackSegments);
    }
    else
    {
      this.errorMessage = "<html><h3>Error:</h3> Must have at least First Track Segment value Greater than 0...\n";
      this.errorMessage += "Track Segment L<SUB>1</SUB>, H<SUB>1</SUB></html>";
    }
  }
  /**
   * Method clearTrackSegments.  Clears all TrackSegments And Data and ErrorData.
   */
  public void clearTrackSegments()
  {
    this.trackSegments.clear();
    this.errorMessage = null;
    this.performanceCalculated = false;
    this.trackLengthInches = -1;
    this.trackLengthFeet = -1;
    this.idealTotalTime = -1;
    this.idealFinishSpeed = -1;
    this.idealAverageSpeed = -1;
  }
  /**
   * Method computePerformance.  The method calculates the Performance of a vehicle
   * given a particlular time in milliseconds.  The results of the calculation are 
   * populated and returned through a valuebean <PRE>CarPerformance</PRE>.<BR>
   * For the Calcualtion to work corretly, the track Details must have at least
   * one Height and Length recorded.
   * @param VEHICLE_RACE_TIME - final double, the Race time to calcualte the performance
   * @return CarPerformance, the ValueBean containing performance details.
   */
  public CarPerformance computePerformance( final double VEHICLE_RACE_TIME )
  {
    if (!this.performanceCalculated)
    {
      calculateIdealCarPerformance();
    }
    String errorMsg = this.errorMessage;
    double totalTrackLength = this.trackLengthFeet;
    //  Here is the Time value of the CAR
    CarPerformance carPerformance = new CarPerformance(VEHICLE_RACE_TIME);
    // Do a dummy loop (one pass only) so can break out of it on an error
    // condition
    for (int i = 0; (i < 1) && (errorMsg == null); i++)
    {
      // Check for errors on input of real car time
      if (VEHICLE_RACE_TIME < 0.0)
      {
        errorMsg = "Error: Real car race time less than zero";
        continue;
      }
      // Check for real times (that really were input)that are better than
      // ideal
      if ((VEHICLE_RACE_TIME != 0.0) && (VEHICLE_RACE_TIME < idealTotalTime))
      {
        errorMsg = "Error: Real car time cannot be better than ideal time";
        continue;
      }
      // Only do the calculations for the real car if the user input a value
      // AND the value is does not exceed the ideal time.
      if ((VEHICLE_RACE_TIME > 0.0) && (VEHICLE_RACE_TIME >= idealTotalTime))
      {
        // Compute the performance of the real car:
        double out_realAverageSpeed = (totalTrackLength / VEHICLE_RACE_TIME) * FEET_PER_SEC_TO_MPH;
        double out_realEfficiency = (idealTotalTime / VEHICLE_RACE_TIME) * 100.0;
        double out_deltaEfficiency = 100.0 - out_realEfficiency;
        double out_deltaTime = VEHICLE_RACE_TIME - idealTotalTime;
        double out_deltaAverageSpeed = (idealAverageSpeed - out_realAverageSpeed) * FEET_PER_SEC_TO_MPH;
        carPerformance.setRealAverageSpeed(out_realAverageSpeed);
        carPerformance.setRealEfficiency(out_realEfficiency);
        carPerformance.setDeltaEfficiency(out_deltaEfficiency);
        carPerformance.setDeltaTime(out_deltaTime);
        carPerformance.setDeltaAverageSpeed(out_deltaAverageSpeed);
      }
    }
    carPerformance.setErrorMsg(errorMsg);
    return carPerformance;
  }
  /**
   * Method getHeightSegment.  Returns the Height for the segment specified
   * @param index - int, the Index of the Segment
   * @return int, the Height in inches
   */
  public int getHeightSegment( int index )
  {
    int rc = trackSegments.getHeightSegment(index);
    return rc;
  }
  /**
   * Method getIdealAverageSpeed.  Returns idealAverageSpeed of the TrackUtils.
   * @see com.boyscouts.util.track.TrackUtils#setIdealAverageSpeed(double)
   * @return com.hgutil.data.FixedDouble: Returns the idealAverageSpeed.
   */
  public FixedDouble getIdealAverageSpeed()
  {
    return (new FixedDouble(idealAverageSpeed));
  }
  /**
   * Method getIdealFinishSpeed.  Returns idealFinishSpeed of the TrackUtils
   * @see com.boyscouts.util.track.TrackUtils#setIdealFinishSpeed(double)
   * @return com.hgutil.data.FixedDouble: Returns the idealFinishSpeed.
   */
  public FixedDouble getIdealFinishSpeed()
  {
    return (new FixedDouble(idealFinishSpeed));
  }
  /**
   * Method getIdealTotalTime.  Returns idealTotalTime of the TrackUtils
   * @see com.boyscouts.util.track.TrackUtils#setIdealTotalTime(double)
   * @return com.hgutil.data.FixedDouble: Returns the idealTotalTime.
   */
  public FixedDouble getIdealTotalTime()
  {
    return (new FixedDouble(idealTotalTime));
  }
  /**
   * Method getLengthSegment.  Returns the Length segment for the specified index
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.awt.Dimension)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(int,int)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.lang.String, java.lang.String)
   * @param index - int, the Index of the segment
   * @return int, The Length for the segment in inches 
   */
  public int getLengthSegment( int index )
  {
    int rc = trackSegments.getLengthSegment(index);
    return rc;
  }
  /**
   * Method getTrackLengthFeet.  Returns trackLengthFeet of the TrackUtils.
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.awt.Dimension)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(int,int)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.lang.String, java.lang.String)
   * @see com.boyscouts.util.track.TrackUtils#setTrackLengthFeet(double)
   * @return com.hgutil.data.FixedDouble: Returns the trackLengthFeet.
   */
  public FixedDouble getTrackLengthFeet()
  {
    return (new FixedDouble(trackLengthFeet));
  }
  /**
   * Method gettrackLength.  Returns trackLengthInches of the TrackUtils.
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.awt.Dimension)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(int,int)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.lang.String, java.lang.String)
   * @see com.boyscouts.util.track.TrackUtils#setTrackLengthInches(double)
   * @return FixedDouble: Returns the trackLengthInches.
   */
  public FixedDouble getTrackLengthInches()
  {
    return (new FixedDouble(trackLengthInches));
  }
  /**
   * Method isReady.  Method indicates if the class is ready tp perform any calculations
   * @return boolean - true if the class is ready - false if it not ready
   */
  public boolean isReady()
  {
    boolean rc = (trackSegments.numberOfSegements() > 0);
    return rc;
  }
  /**
   * Method save.  Forces a Save of the Data.
   * @see com.boyscouts.util.track.TrackUtils#saveData(TrackSegments)
   * @see com.boyscouts.util.track.TrackUtils#loadTrackSegments()
   */
  public void save()
  {
    saveData(this.trackSegments);
  }

  /**
   * Method setIdealAverageSpeed.  Sets idealAverageSpeed of the TrackUtils
   * @see com.boyscouts.util.track.TrackUtils#getIdealAverageSpeed()
   * @param idealAverageSpeed : double, The idealAverageSpeed to set.
   * 
   * @uml.property name="idealAverageSpeed"
   */
  public void setIdealAverageSpeed( double idealAverageSpeed )
  {
    this.idealAverageSpeed = idealAverageSpeed;
  }

  /**
   * Method setIdealFinishSpeed.  Sets idealFinishSpeed of the TrackUtils
   * @see com.boyscouts.util.track.TrackUtils#getIdealFinishSpeed()
   * @param idealFinishSpeed : double, The idealFinishSpeed to set.
   * 
   * @uml.property name="idealFinishSpeed"
   */
  public void setIdealFinishSpeed( double idealFinishSpeed )
  {
    this.idealFinishSpeed = idealFinishSpeed;
  }

  /**
   * Method setIdealTotalTime.  Sets idealTotalTime of the TrackUtils
   * @see com.boyscouts.util.track.TrackUtils#getIdealTotalTime()
   * @param idealTotalTime : double, The idealTotalTime to set.
   * 
   * @uml.property name="idealTotalTime"
   */
  public void setIdealTotalTime( double idealTotalTime )
  {
    this.idealTotalTime = idealTotalTime;
  }

  /**
   * Method setTrackLengthFeet.  Sets trackLengthFeet of the TrackUtils
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.awt.Dimension)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(int,int)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.lang.String, java.lang.String)
   * @see com.boyscouts.util.track.TrackUtils#getTrackLengthFeet()
   * @param trackLengthFeet : double, The trackLengthFeet to set.
   * 
   * @uml.property name="trackLengthFeet"
   */
  public void setTrackLengthFeet( double trackLengthFeet )
  {
    this.trackLengthFeet = trackLengthFeet;
  }

  /**
   * Method setTrackLengthInches.  Sets trackLengthInches of the TrackUtils
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.awt.Dimension)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(int,int)
   * @see com.boyscouts.util.track.TrackUtils#addSegment(java.lang.String, java.lang.String)
   * @see com.boyscouts.util.track.TrackUtils#getTrackLengthInches()
   * @param trackLength : double, The trackLengthInches to set.
   * 
   * @uml.property name="trackLengthInches"
   */
  public void setTrackLengthInches( double trackLength )
  {
    this.trackLengthInches = trackLength;
  }

  /**
   * Method getTrackGraph.  Returns a Panel containing the Track in a Graph display.
   * @return javax.swing.JPanel
   * 
   * @uml.property name="panel"
   */
  public javax.swing.JPanel getTrackGraph()
  {
    return panel;
  }

}
