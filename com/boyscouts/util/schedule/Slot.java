package com.boyscouts.util.schedule;

/*
 * Tim Cutler
 */
public class Slot
{

  /**
   * 
   * @uml.property name="race"
   */
  private int race = -1;

  /**
   * 
   * @uml.property name="track"
   */
  private int track = -1;

  public static final Player empty = new Player();
  public static final Player emptyReserved = new Player();

  /**
   * 
   * @uml.property name="player"
   * @uml.associationEnd multiplicity="(1 1)" inverse="slots:com.boyscouts.util.schedule.Player"
   */
  Player player = null;

  public Slot( int race, int track )
  {
    this.race = race;
    this.track = track;
    player = empty;
  }

  public String toString()
  {
    return "slot(" + race + "," + track + ")";
  }

  /**
   * 
   * @uml.property name="player"
   */
  public Player getPlayer()
  {
    return player;
  }

  /**
   * 
   * @uml.property name="race"
   */
  public int getRace()
  {
    return race;
  }

  /**
   * 
   * @uml.property name="track"
   */
  public int getTrack()
  {
    return track;
  }

  public Player assignPlayer( Player player )
  {
    Player old = this.player;
    this.player.removeSlot(this);
    this.player = player;
    this.player.assignSlot(this);
    return old;
  }

  public Player setEmpty()
  {
    Player old = this.player;
    this.player.removeSlot(this);
    this.player = empty;
    return old;
  }

  public Player setEmptyReserved()
  {
    Player old = this.player;
    this.player.removeSlot(this);
    this.player = emptyReserved;
    return old;
  }

  public boolean IsEmpty()
  {
    return this.player == empty;
  }

  public boolean IsEmptyReserved()
  {
    return this.player == emptyReserved;
  }
}
