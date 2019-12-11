package com.boyscouts.util.schedule;

/*
 * Tim Cutler
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;

import com.boyscouts.domain.Log;

public class Schedule
{

  /**
   * 
   * @uml.property name="errorMessage"
   */
  private String errorMessage = null;

  private boolean[] error = {false, false, false};

  /**
   * 
   * @uml.property name="players"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private Players players = null;

  /**
   * 
   * @uml.property name="slots"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private Slots slots = null;

  /**
   * 
   * @uml.property name="raceCount"
   */
  private int raceCount = 0;

  private int trackCount = 0;

  /**
   * 
   * @uml.property name="playerCount"
   */
  private int playerCount = 0;

  /**
   * 
   * @uml.property name="racesPerPlayer"
   */
  private int racesPerPlayer = 0;

  /**
   * 
   * @uml.property name="actualTrackCount"
   */
  private int actualTrackCount = 0;

  private int[] mapActualTrackToVirtualTrack = null;

  /////////////////////////////////////////////////////////////////////////////
  public Schedule( int trackCount, int playerCount, int racesPerPlayer, boolean[] selectedTrack )
  {

    actualTrackCount = trackCount;
    mapActualTrackToVirtualTrack = new int[trackCount];

    int virtualTrack = 0;

    for (int t = 0; t < trackCount; t++)
    {
      if (selectedTrack[t])
      {
        mapActualTrackToVirtualTrack[t] = virtualTrack;
        virtualTrack++;
      }
      else
      {
        mapActualTrackToVirtualTrack[t] = -1; // no mapping implies empty
      }
    }
    Log.debug("Schedule(" + trackCount + " tracks, " + playerCount + " players, " + racesPerPlayer + " races per player, and " + selectedTrack
              + " track selection boolean array)");

    initialize(virtualTrack, playerCount, racesPerPlayer);
  }

  /////////////////////////////////////////////////////////////////////////////
  public Schedule( int trackCount, int playerCount, int racesPerPlayer )
  {
    actualTrackCount = trackCount; // actual and virtual are the same
    mapActualTrackToVirtualTrack = new int[trackCount];
    for (int t = 0; t < trackCount; t++)
    {
      mapActualTrackToVirtualTrack[t] = t;
    }
    System.out.println("Schedule(" + trackCount + " tracks, " + playerCount + " players, " + racesPerPlayer + " races per player)");
    initialize(trackCount, playerCount, racesPerPlayer);
  }

  /////////////////////////////////////////////////////////////////////////////
  private void initialize( int trackCount, int playerCount, int racesPerPlayer )
  {
    Log.debug("initialize: " + trackCount + " actual tracks");

    this.trackCount = trackCount;
    this.playerCount = playerCount;
    this.racesPerPlayer = racesPerPlayer;

    int slotsToUse = this.racesPerPlayer * this.playerCount;

    this.raceCount = slotsToUse / this.trackCount;
    if (this.raceCount * this.trackCount < slotsToUse)
      this.raceCount++;

    slots = new Slots(this.raceCount, this.trackCount);
    players = new Players(this.playerCount);
  }

  /**
   * 
   * @uml.property name="playerCount"
   */
  public int getPlayerCount()
  {
    return playerCount;
  }

  /**
   * 
   * @uml.property name="raceCount"
   */
  public int getRaceCount()
  {
    return raceCount;
  }

  /**
   * 
   * @uml.property name="actualTrackCount"
   */
  public int getTrackCount()
  {
    return actualTrackCount;
  }

  public int getSlotCount()
  {
    return slots.getSlotCount();
  }

  public Player getPlayer( int race, int track )
  {
    int i = mapActualTrackToVirtualTrack[track];
    if (i < 0)
      return Players.emptyPlayer;
    return slots.getPlayer(race, i);
  }

  /**
   * 
   * @uml.property name="racesPerPlayer"
   */
  public int getRacesPerPlayer()
  {
    return racesPerPlayer;
  }

  public boolean initialAssignment()
  {
    int s, t, r, p, pp, rr, tt, shiftCount;
    int slotsToUse = racesPerPlayer * playerCount;

    s = 0;
    while (slotsToUse < slots.getSlotCount())
    {
      r = s % raceCount;
      t = s % trackCount;
      slots.setEmptyReserved(r, t);
      s++;
    }

    players.scramble(); // comment out when debugging is helpful
    players.setScratchList();

    for (p = 0, s = 0; s < raceCount * trackCount; s++)
    {
      t = s % trackCount; // the track as a function of slot
      r = s / trackCount; // the race as a function of slot
      pp = p % playerCount;

      boolean firstSet = (p < playerCount);

      if (!firstSet)
      {
        boolean needShift = true;
        shiftCount = 0;
        while (shiftCount < (playerCount - pp - 1))
        {
          needShift = false;
          int whatPlayer = players.getPlayer(pp).getWho();

          // check to see if player is already been in this race
          for (tt = 0; tt < t && !needShift; tt++)
            needShift = (slots.getSlot(r, tt).getPlayer().getWho() == whatPlayer);

          // check to see if player is already been on this track
          for (rr = 0; rr < r && !needShift; rr++)
            needShift = (slots.getSlot(rr, t).getPlayer().getWho() == whatPlayer);

          if (needShift)
          {
            shiftCount++;
            players.shift(pp);
          }
          else
            break;
        }
        if (shiftCount > 3)
          Log.debug("" + shiftCount + " shifts required");
      }

      boolean reserved = slots.assignPlayer(r, t, players.getPlayer(pp));
      if (reserved)
      {
        continue;
      }

      p++;
      if (p % playerCount == 0)
      {
        players.setScratchList();
      }
    }

    return check();
  }

  /**
   * 
   * @uml.property name="errorMessage"
   */
  /////////////////////////////////////////////////////////////////////////////
  public String getErrorMessage()
  {
    return (errorMessage == null ? "no errors" : errorMessage);
  }

  /**
   * 
   * @uml.property name="errorMessage"
   */
  /////////////////////////////////////////////////////////////////////////////
  private void setErrorMessage( String msg )
  {
    if (msg != null)
      System.out.println("Schedule check error: " + msg);
    errorMessage = msg;
  }

  /////////////////////////////////////////////////////////////////////////////
  private boolean check()
  {
    int p = 0;
    int count = 0;
    Player pp = null;
    Slot slot = null;
    Iterator iteratorPlayerSlots = null;
    int[] trackUsage = new int[getTrackCount()];
    int[] raceUsage = new int[getRaceCount()];
    int whichError = 0;

    // Errors are analyzed in increasing order of severity so last message
    //  kept is the most in need of attention.
    errorMessage = null;

    //-------------------------------------------------------------------------
    // Check that no player is raced on the same track twice.
    // Relatively cosmetic type of error.
    whichError = 0;
    error[whichError] = false;

    for (p = 0; p < players.getPlayerCount(); p++)
    {
      pp = players.getPlayer(p);
      if (pp.isEmpty())
        continue;

      int t = 0;
      iteratorPlayerSlots = pp.slots.iterator();

      for (t = 0; t < getTrackCount(); t++)
      {
        trackUsage[t] = 0;
      }

      while (iteratorPlayerSlots.hasNext())
      {
        slot = (Slot) iteratorPlayerSlots.next();
        t = slot.getTrack();
        trackUsage[t]++;
        if (trackUsage[t] > 1)
        {
          setErrorMessage("player " + pp + " uses track " + t + " more than once");
          error[whichError] = true;
        }
      }
    }

    //-------------------------------------------------------------------------
    // Check that each player race count is the same
    whichError++;
    error[whichError] = false;

    for (p = 0; p < players.getPlayerCount(); p++)
    {
      pp = players.getPlayer(p);
      if (pp.isEmpty())
        continue;

      count = 0;
      iteratorPlayerSlots = pp.slots.iterator();

      while (iteratorPlayerSlots.hasNext())
      {
        slot = (Slot) iteratorPlayerSlots.next();
        count++;
      }

      if (count != getRacesPerPlayer())
      {
        setErrorMessage("player " + pp + " races " + count + "/" + getRacesPerPlayer());
        error[whichError] = true;
      }
    }

    //-------------------------------------------------------------------------
    // Check that no player is twice in same race.
    whichError++;
    error[whichError] = false;

    for (p = 0; p < players.getPlayerCount(); p++)
    {
      pp = players.getPlayer(p);
      if (pp.isEmpty())
        continue;
      int r = -1;

      iteratorPlayerSlots = pp.slots.iterator();

      for (r = 0; r < getRaceCount(); r++)
      {
        raceUsage[r] = 0;
      }

      while (iteratorPlayerSlots.hasNext())
      {
        slot = (Slot) iteratorPlayerSlots.next();
        r = slot.getRace();
        raceUsage[r]++;
        if (raceUsage[r] > 1)
        {
          setErrorMessage("player " + pp + " in race " + r + " more than once");
          error[whichError] = true;
        }
      }
    }

    return error[0] || error[1] || error[2];
  }

  /////////////////////////////////////////////////////////////////////////////
  public boolean isErrorSerious()
  {
    return error[1] || error[2];
  }

  /////////////////////////////////////////////////////////////////////////////
  public static void main( String[] args )
  {
    int lanes = 4;
    int players = 4;
    int racesPerPlayer = 4;

    boolean[] selectedLanes = new boolean[lanes];

    for (int t = 0; t < lanes; t++)
      selectedLanes[t] = (t % 2 == 0 ? false : true);

    Schedule schedule = new Schedule(lanes, players, racesPerPlayer, selectedLanes);

    schedule.initialAssignment();

    //-----------------------------------------------------------------
    String title = "Initial Assignment Result";
    int tracks = schedule.getTrackCount();
    int races = schedule.getRaceCount();

    int dx = 40;
    int dy = 30;
    JFrame fs = new JFrame(title);
    JLabel label = null;

    int gridCols = races + 1;
    int gridRows = tracks + 1;

    fs.getContentPane().setLayout(new GridLayout(tracks + 1, races + 1));

    label = new JLabel(" Race");
    label.setSize(dx, dy);
    fs.getContentPane().add(label);

    for (int r = 0; r < races; r++)
    {
      label = new JLabel("  " + r);
      label.setSize(dx, dy);
      fs.getContentPane().add(label);
    }

    for (int t = 0; t < tracks; t++)
    {

      label = new JLabel("Trk" + t);
      label.setSize(dx, dy);
      fs.getContentPane().add(label);

      for (int r = 0; r < races; r++)
      {

        label = new JLabel(" " + schedule.getPlayer(r, t));
        label.setSize(dx, dy);
        label.setBorder(BorderFactory.createRaisedBevelBorder());
        fs.getContentPane().add(label);
      }
    }

    fs.setSize(dx * (races + 1), dy * (tracks + 1));
    fs.setResizable(true);
    fs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fs.setVisible(true);
  }
}
