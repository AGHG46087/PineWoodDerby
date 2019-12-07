/*
 * @author:		hgrein
 * date:		Jan 8, 2004
 * Package:		com.boyscouts.domain
 * File Name:		WinnerRaceContainer.java
 */
package com.boyscouts.domain;

import java.io.File;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.hgutil.ParseData;

/**
 * author:      Hans-Jurgen Greiner<BR>
 * date:        Jun 4, 2004<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   WinnerRaceContainer.java<BR>
 * Type Name:   WinnerRaceContainer<BR>
 * Description: This class is designed to maintain a list of 
 * winners from all races, it maintains the TOP x number of racers 
 * from the containers passed to it.
 */

public class WinnerRaceContainer implements Serializable
{
  /** Field <code>serialVersionUID</code> : long */
  static final long serialVersionUID = 6464707093484617711L;
  /** Field <code>winnerContainer</code> : WinnerRaceContainer */
  private static WinnerRaceContainer winnerContainer = null;

  /**
   * Field <code>containerTable</code> : Hashtable
   * 
   * @uml.property name="containerTable"
   * @uml.associationEnd multiplicity="(0 1)" qualifier="key:java.lang.String com.boyscouts.domain.RacerContainer"
   */
  private Hashtable containerTable = null;

  /** Field <code>topRaceSize</code> : int */
  private static int topRaceSize = 5;
  /** Field <code>SAVE_DIR</code> : String */
  private static final String SAVE_DIR = "C:" + File.separator + "Temp" + File.separator + "RaceARama";
  /** Field <code>TRACK_SEGMENTS</code> : String */
  private static final String DATA_SEGMENTS = "WinnerContainer.dat";

  /**
   * Method getInstance.  
   * @return WinnerRaceContainer
   */
  public static WinnerRaceContainer getInstance()
  {
    if (winnerContainer == null)
    {
      winnerContainer = loadDataSegments(); // new WinnerRaceContainer();
    }
    return winnerContainer;
  }
  /**
   * Method gettopRaceSize.  Returns topRaceSize of the WinnerRaceContainer
   * @return int: Returns the topRaceSize.
   */
  public static int getTopRaceSize()
  {
    return topRaceSize;
  }
  /**
   * Method settopRaceSize.  Sets topRaceSize of the WinnerRaceContainer
   * @param topRaceSize : int, The topRaceSize to set.
   */
  public static void setTopRaceSize( int topRaceSize )
  {
    WinnerRaceContainer.topRaceSize = topRaceSize;
  }
  /**
   * Method settopRaceSize.  Sets topRaceSize of the WinnerRaceContainer
   * @param topRaceSize : String, The topRaceSize to set.
   */
  public static void setTopRaceSize( String topRaceSize )
  {
    int temp = ParseData.parseNum(topRaceSize, -9999);
    if (temp > 0)
    {
      setTopRaceSize(temp);
    }
    else
    {
      JOptionPane.showMessageDialog(null, "Top Racer Count of [] is invalid,\n" + "Please re-enter the number,\nor the default value of [" + getTopRaceSize()
                                          + "] will be used.", "Top Racer Count Error", JOptionPane.ERROR_MESSAGE);
      setTopRaceSize(5);
    }
  }
  /**
   * Constructor for WinnerRaceContainer. 
   * 
   */
  private WinnerRaceContainer()
  {
    containerTable = new Hashtable();
  }
  /**
   * Method add.  
   * @param raceName
   * @param racerContainer
   */
  private void add( String raceName, RacerContainer racerContainer )
  {
    RacerContainer subsetContainer = getSubset(racerContainer);
    subsetContainer.setRaceName(raceName);
    containerTable.put(raceName, subsetContainer);
  }
  /**
   * Method addRacerContainer.  If the Racer Container is null, the method will
   * simply return.  If the container is not null. it will first Make a call to
   * sort the Racers by average time.  the Add it to then add it to the container.
   * NOTE: the adding will take either the <B><PRE>topRaceSize</PRE></B> or the 
   * Size whichever is the lesser of the two
   * @param raceName - String
   * @param racerContainer - RacerContainer
   */
  public void addRacerContainer( String raceName, RacerContainer racerContainer )
  {
    if (raceName == null || racerContainer == null)
    {
      return;
    }
    sortTopRacers(racerContainer);
    add(raceName, racerContainer);
    saveData(); // Save all the Data so far.
  }
  /**
   * Method clear.  Clears this container so that it contains no keys.
   */
  public void clear()
  {
    containerTable.clear();
  }
  /**
   * Method contains.  Tests if the specified object is a key in this container
   * @param key - String possible key. 
   * @return boolean - true if and only if the specified object is a key 
   * in this hashtable, as determined by the equals method; false otherwise. 
   */
  public boolean contains( String key )
  {
    boolean rc = false;
    if (key != null)
    {
      rc = containerTable.containsKey(key);
    }
    return rc;
  }
  /**
   * Method get.  Returns the Racer Container from the Specified key
   * @param key - String, the key specifying the Racer Container
   * @return RacerContainer
   */
  private RacerContainer get( String key )
  {
    RacerContainer rc = (RacerContainer) containerTable.get(key);
    return rc;
  }
  /**
   * Method getRaceKeys.  Returns an Vector of the keys in this container.
   * @return Vector
   */
  public Vector getRaceKeys()
  {
    Enumeration theKeys = containerTable.keys();
    Vector keyList = new Vector();
    while (theKeys.hasMoreElements())
    {
      keyList.add(theKeys.nextElement());
    }
    return keyList;
  }
  /**
   * Method getRacerContainer.  
   * @param key - String
   * @return RacerContainer
   */
  public RacerContainer getRacerContainer( String key )
  {
    RacerContainer rc = null;
    if (key != null)
    {
      rc = get(key);
    }
    return rc;
  }
  /**
   * Method getSubset.  Returns a Subset RacerContainer containing either the topRaceSize or 
   * less of the RacerContainer
   * @param racerContainer - RacerContainer, the container from which data items are pulled.
   * @return RacerContainer - the subset RacerContainer
   */
  private RacerContainer getSubset( RacerContainer racerContainer )
  {
    final int COUNT = (racerContainer.getSize() < topRaceSize) ? racerContainer.getSize() : topRaceSize;
    RacerContainer tempContainer = new RacerContainer();

    for (int index = 0; index < COUNT; index++)
    {
      RacerPerson origRP = racerContainer.elementAt(index);
      RacerPerson copyRP = origRP.getSessionCopy();
      tempContainer.addRacerPerson(copyRP);
    }
    return tempContainer;
  }
  /**
   * Method isEmpty.  Tests if this container maps no keys to values
   * @return boolean
   */
  public boolean isEmpty()
  {
    boolean rc = containerTable.isEmpty();
    return rc;
  }
  /**
   * Method remove.  Removes the RacerContainer specified by the Key
   * @param key - String
   * @return RacerContainer, can be null if not in the container
   */
  private RacerContainer remove( String key )
  {
    RacerContainer rc = (RacerContainer) containerTable.remove(key);
    return rc;
  }
  /**
   * Method removeRacerContainer.  Removes the RacerContainer specified by the Key
   * @param key - String
   * @return RacerContainer, can be null if not in the container
   */
  public RacerContainer removeRacerContainer( String key )
  {
    RacerContainer rc = null;
    if (key != null)
    {
      rc = remove(key);
    }
    return rc;
  }
  /**
   * Method saveData.  Method to maintain Persistance of a Vector Model to a filename
   */
  private synchronized static void saveData()
  {
    WinnerRaceContainer data = WinnerRaceContainer.winnerContainer;
    if (data == null)
    {
      return; // We have nothing so why bother.
    }
    File saveDir = new File(SAVE_DIR);
    File saveFile = new File(saveDir, DATA_SEGMENTS);
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
   * Method loadDataSegments.  Method to load the Persistent Data Segments
   * @return WinnerRaceContainer, the Saved WinnerRaceConteiner
   */
  private synchronized static WinnerRaceContainer loadDataSegments()
  {
    WinnerRaceContainer dataSegs = null;
    File saveDir = new File(SAVE_DIR);
    File saveFile = new File(saveDir, DATA_SEGMENTS);
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
        dataSegs = (WinnerRaceContainer) istream.readObject();

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
    if (dataSegs == null)
    {
      dataSegs = new WinnerRaceContainer();
    }
    return dataSegs;
  }
  /**
   * Method purgeSavedData.  This method will purge any data saved to a file.  
   * It should be called when the Application terminates Normal
   */
  public synchronized void purgeSavedData()
  {
    File saveDir = new File(SAVE_DIR);
    File saveFile = new File(saveDir, DATA_SEGMENTS);
    if (!saveDir.exists())
    {
      saveDir.mkdirs();
    }
    if (saveFile.exists())
    {
      saveFile.delete();
    }
  }
  /**
   * Method sortTopRacers.  
   * @param racerContainer
   */
  private void sortTopRacers( RacerContainer racerContainer )
  {
    racerContainer.updateRacerTimes();
  }
}
