package com.boyscouts.util.schedule;

/*
 * Tim Cutler
 */
import java.util.*;

import com.boyscouts.domain.Log;

public class Player
{

  /**
   * 
   * @uml.property name="who"
   */
  private int who = -1;

  /**
   * 
   * @uml.property name="slots"
   * @uml.associationEnd multiplicity="(0 -1)" inverse="player:com.boyscouts.util.schedule.Slot"
   */
  public ArrayList slots = new ArrayList(10);

  // this constructor creates an empty player
  public Player()
  {
  }

  public Player( int who )
  {
    this.who = who;
  }

  public String toString()
  {
    return (isEmpty() ? " " : "" + (who + 1));
  }

  public void assignSlot( Slot slot )
  {
    if (isEmpty())
      slots.clear();
    else
    {
      if (slots.contains(slot))
        Log.debug("player " + this + ", assignSlot(" + slot + ") already present");
      else
        slots.add(slot);
    }
  }

  public void removeSlot( Slot slot )
  {
    if (isEmpty())
      slots.clear();
    else
    {
      if (slots.contains(slot))
        slots.remove(slot);
      else
        Log.debug("player " + this + ", removeSlot(" + slot + ") missing slot");
    }
  }

  public boolean isEmpty()
  {
    return (who < 0);
  }

  /**
   * 
   * @uml.property name="who"
   */
  public int getWho()
  {
    return who;
  }

  /**
   * Method getPlayerNumber.  
   * @return String
   */
  public String getPlayerNumber()
  {
    String number = "" + (1 + getWho());
    return number;
  }

}
