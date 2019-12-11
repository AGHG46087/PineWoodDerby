/*
 * @author:		Owner
 * Package:		com.boyscouts.util.schedule
 * File Name:		PersonRacerSchedule.java
 */
package com.boyscouts.util.schedule;

import com.boyscouts.domain.RaceData;
import com.boyscouts.domain.RaceDataContainer;
import com.boyscouts.domain.RacerPerson;
import com.hgtable.ColumnDataCellTooltip;

/**
 * author:     Owner
 * Package:     com.boyscouts.util.schedule
 * File Name:   PersonRacerSchedule.java
 * Type Name:   PersonRacerSchedule
 * Description: Represents the Tie between a Racer Person and a Person Object scheduled in a Race.  
 * This object will contain a reference to a Racer Person and the Player formt neh Scheduling
 */

public class PersonRacerSchedule implements ColumnDataCellTooltip
{

  /**
   * Field <code>racer</code> : RacerPerson
   * 
   * @uml.property name="racer"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RacerPerson racer = null;

  /**
   * Field <code>player</code> : Player
   * 
   * @uml.property name="player"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private Player player = null;

  /** Field <code>race</code> : int */
  private int race = 0;
  /** Field <code>lane</code> : int */
  private int lane = 0;

  /**
   * Constructor for PersonRacerSchedule. 
   * @param rp - RacerPerson, The Racer Person
   * @param player - Player, The Schedule selected Player
   * @param race - int, the Race/Heat Number
   * @param lane - int, the Lane to be racing in, for the the heat
   */
  public PersonRacerSchedule( RacerPerson rp, Player player, int race, int lane )
  {
    this.racer = rp;
    this.player = player;
    this.race = race;
    this.lane = lane;
  }
  /**
   * Method createRaceDataForRacer.  Creates a RaceData Object for the RacerPerson contained by this object.
   * If the RacerPerson is null or empty the RaceData will be empty.   
   * @return RaceData - the Race Data object being created for the Racer
   */
  public RaceData createRaceDataForRacer()
  {
    RaceData rd = new RaceData();

    RacerPerson.setCompareIDOnly(true);
    if (racer != null && !racer.isEmptyRecord())
    {
      rd.setId(racer.getId());
      rd.setVehicleNumber(racer.getVehicleNumber());
      // Add the RaceData to the Racer
      racer.addRaceData(rd);
    }
    RacerPerson.setCompareIDOnly(false);

    rd.setRound(this.getRound());
    rd.setHeat(this.getHeat());
    rd.setLane(this.getLane());
    return rd;
  }
  /**
   * Generates a ToolTip for this Object
   * Overridden Method:  
   * @see com.hgtable.ColumnDataCellTooltip#getCellToolTip(int)
   * @param col - int, the Column of the Cell to display
   * @return - Stirng, the ToolTip in the form of HTML
   */
  public String getCellToolTip( int col )
  {
    String retVal = "";
    boolean recordsNull = (player == null || racer == null);
    if (!recordsNull)
    {
      if (!(player.isEmpty() || racer.isEmptyRecord()))
      {
        retVal = "<HTML>";
        retVal += "car=<B><FONT face=arial color=#006699 size=+1>" + racer.getVehicleNumber() + "</FONT></B><BR>";
        retVal += "<B><FONT face=arial color=#006699 size=+1>" + racer.getLastName() + ", " + racer.getFirstName() + "</FONT></B><BR>";
        retVal += "place=<B><FONT face=arial color=#006699 size=+1>" + racer.getPlacement() + "</FONT></B><BR>";
        retVal += "playerID=<B>" + player.getPlayerNumber() + "</B>";
        retVal += "</HTML>";
        // NOTE: getPlayerNumber() returns (getWho() + 1)
      }
    }
    else if (player != null && !player.isEmpty())
    {
      retVal = "<HTML>";
      retVal += "<B><H4>" + player + "</H4></B>";
      retVal += "</HTML>";
    }
    return retVal;
  }
  /**
   * Method getHeat.  Returns the current Heat that this Person will be racing
   * @return int
   */
  private int getHeat()
  {
    return (race + 1);
  }
  /**
   * Method getLane.  Returns the Current Lane that this person will be racing
   * @return int
   */
  private int getLane()
  {
    return (lane + 1);
  }

  /**
   * Method getplayer.  Returns player of the PersonRacerSchedule
   * @return Player: Returns the player.
   * 
   * @uml.property name="player"
   */
  public Player getPlayer()
  {
    return player;
  }

  /**
   * Method getracer.  Returns racer of the PersonRacerSchedule
   * @return RacerPerson: Returns the racer.
   * 
   * @uml.property name="racer"
   */
  public RacerPerson getRacer()
  {
    return racer;
  }

  /**
   * Method getRound.  Returns the Current Round that this person will be racing.  When the Starting Racer
   * Rolls around for the next instance the Round will increment
   * @return int
   */
  private int getRound()
  {
    int retVal = 0;
    if (racer != null && !racer.isEmptyRecord())
    {
      RaceDataContainer rdc = racer.getRaceDataContainer();
      if (rdc != null)
      {
        retVal = rdc.getSize();
      }
    }
    return retVal;
  }
  /**
   * Overridden Method:  
   * @see java.lang.Object#toString()
   * @return String
   */
  public String toString()
  {
    //String rc = "" + player;
    String rc = "";
    try
    {
      rc = "" + racer.getVehicleNumber() + "/" + racer.getLastName();
    }
    catch (Exception exc)
    {
      System.err.println("Exception: racer=[" + racer + "], player=[" + player + "], race=[" + race + "], lane=[" + lane + "]");
    }
    if (rc.length() > 12)
    {
      rc = rc.substring(0, 12) + "...";
    }
    return rc;
  }

}
