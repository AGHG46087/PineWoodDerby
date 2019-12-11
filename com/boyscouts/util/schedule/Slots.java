package com.boyscouts.util.schedule;

/*
 * Tim Cutler
 */
import java.util.*;

public class Slots
{

  /**
   * 
   * @uml.property name="raceCount"
   */
  private int raceCount = 0;

  /**
   * 
   * @uml.property name="trackCount"
   */
  private int trackCount = 0;

  /**
   * 
   * @uml.property name="slotCount"
   */
  private int slotCount = 0;

  /**
   * 
   * @uml.property name="slotList"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="com.boyscouts.util.schedule.Slot"
   */
  public ArrayList slotList = null;

  public Slot slot[][] = null;

  public Slots( int raceCount, int trackCount )
  {

    this.raceCount = raceCount;
    this.trackCount = trackCount;
    this.slotCount = raceCount * trackCount;

    slot = new Slot[raceCount][trackCount];
    slotList = new ArrayList(slot.length);
    for (int r = 0; r < raceCount; r++)
    {
      for (int t = 0; t < trackCount; t++)
      {
        Slot s = new Slot(r, t);
        slot[r][t] = s;
        slotList.add(s);
      }
    }
  }

  public Player getPlayer( int r, int t )
  {
    return getSlot(r, t).getPlayer();
  }

  public boolean assignPlayer( int r, int t, Player player )
  {
    boolean error = true;
    if (getSlot(r, t).IsEmpty())
    {
      getSlot(r, t).assignPlayer(player);
      error = false;
    }
    return error;
  }

  public boolean setEmptyReserved( int r, int t )
  {
    boolean error = true;
    if (getSlot(r, t).IsEmpty())
    {
      getSlot(r, t).setEmptyReserved();
      slotCount--;
      error = false;
    }
    return error;
  }

  public Slot getSlot( int r, int t )
  {
    return slot[r][t];
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
   * @uml.property name="trackCount"
   */
  public int getTrackCount()
  {
    return trackCount;
  }

  /**
   * 
   * @uml.property name="slotCount"
   */
  public int getSlotCount()
  {
    return slotCount;
  }

}
