package com.boyscouts.util.schedule;

/*
 * Created on Dec 21, 2003
 * Tim Cutler
 */
import java.util.*;

public class Players
{

  public final static Player emptyPlayer = new Player();

  /**
   * 
   * @uml.property name="playerCount"
   */
  private int playerCount;

  /**
   * 
   * @uml.property name="playerList"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="com.boyscouts.util.schedule.Player"
   */
  private List playerList;

  /**
   * 
   * @uml.property name="scratchList"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="com.boyscouts.util.schedule.Player"
   */
  private List scratchList;

  public Players( int playerCount )
  {
    this.playerCount = playerCount;
    playerList = new ArrayList(playerCount);
    scratchList = new ArrayList(playerCount);
    for (int i = 0; i < playerCount; i++)
    {
      playerList.add(i, new Player(i));
      scratchList.add(i, playerList.get(i));
    }
  }

  /**
   * 
   * @uml.property name="playerCount"
   */
  public int getPlayerCount()
  {
    return playerCount;
  }

  public Player getPlayer( int which )
  {
    return (Player) scratchList.get(which);
  }

  public void shift( int which )
  {
    int ix = playerCount - 1;
    Player p = (Player) scratchList.get(which);
    for (int i = which; i < ix; i++)
    {
      scratchList.set(i, scratchList.get(i + 1));
    }
    scratchList.set(ix, p);
  }

  public void scramble()
  {
    Collections.shuffle(playerList);
  }

  public void setScratchList( int count )
  {
    for (int i = 0; i < playerCount; i++)
    {
      scratchList.set(i, playerList.get((i + count) % playerCount));
    }
  }

  public void setScratchList()
  {
    for (int i = 0; i < playerCount; i++)
    {
      scratchList.set(i, playerList.get(i));
    }
  }

  public void displayScratchList()
  {
    System.out.print("    display scratch list: ");
    for (int i = 0; i < playerCount; i++)
    {
      System.out.print(" " + ((Player) scratchList.get(i)));
    }
    System.out.println();
  }

}