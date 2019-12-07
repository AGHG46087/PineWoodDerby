/*
 * @author:			Owner
 * date:				Dec 9, 2003
 * Package:			com.boyscouts.domain
 * File Name:		RaceData.java
 */
package com.boyscouts.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import com.boyscouts.util.track.CarPerformance;
import com.boyscouts.util.track.TrackUtils;
import com.hgtable.ColumnDataCellValue;
import com.hgutil.ParseData;
import com.hgutil.data.FixedDouble;

/**
 * author:      hgrein<BR>
 * date:        Jun 2, 2004<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   RaceData.java<BR>
 * Type Name:   RaceData<BR>
 * Description: ValueBean - Race Data, NOTE:  If the Columns in the Database 
 * Changes  it would be a good idea to also make a change in the
 * method <B>generateHeaderData()</B>
 */

public class RaceData implements ColumnDataCellValue, Serializable, Comparable
{
  /** Field <code>serialVersionUID</code> : long */
  static final long serialVersionUID = 2586093769867865475L;

  /**
   * Field <code>entryID</code>: Integer
   * 
   * @uml.property name="entryID"
   */
  private Integer entryID = null;

  /**
   * Field <code>id</code>: Integer
   * 
   * @uml.property name="id"
   */
  private Integer id = null;

  /**
   * Field <code>vehicleNumber</code>: String
   * 
   * @uml.property name="vehicleNumber"
   */
  private String vehicleNumber = null;

  /**
   * Field <code>round</code>: Integer
   * 
   * @uml.property name="round"
   */
  private Integer round = null;

  /**
   * Field <code>heat</code>: Integer
   * 
   * @uml.property name="heat"
   */
  private Integer heat = null;

  /**
   * Field <code>lane</code>: Integer
   * 
   * @uml.property name="lane"
   */
  private Integer lane = null;

  /**
   * Field <code>time</code>: FixedDouble
   * 
   * @uml.property name="time"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private FixedDouble time = null;

  /**
   * Field <code>placement</code>: Integer
   * 
   * @uml.property name="placement"
   */
  private Integer placement = null;

  /**
   * Field <code>date</code>: Date
   * 
   * @uml.property name="date"
   */
  private Date date = null;

  /**
   * Field <code>carPerformance</code> : CarPerformance
   * 
   * @uml.property name="carPerformance"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private CarPerformance carPerformance = null;

  /**
   * Field <code>ascending</code>: boolean
   * 
   * @uml.property name="ascending"
   */
  private boolean ascending = true;

  /** Field <code>MAX_POINTS</code> : int */
  public static final int MAX_POINTS = 12;
  /**
   * Default Constructor for RaceData.
   */
  public RaceData()
  {
  }
  /**
   * Copy Contructor for RaceData.
   * @param raceData
   */
  public RaceData( RaceData raceData )
  {
    this(raceData.getDataAsVector());
  }
  /**
   * Constructor for RacerPerson.
   * @param dbRowData
   */
  public RaceData( Vector dbRowData )
  {
    if (dbRowData != null)
    {
      for (int i = 0; i < dbRowData.size(); i++)
      {
        Object obj = dbRowData.elementAt(i);
        setDataAt(i, obj);
      }
    }
  }
  /**
   * Overridden Method:
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   * @param obj - Object, the Object to comapre to
   * @return int
   */
  public int compareTo( Object obj )
  {
    int rc = -1;
    if (obj instanceof RaceData)
    {
      RaceData thatData = (RaceData) obj;
      this.getEntryID().compareTo(thatData.getEntryID());
    }
    else if (obj == null)
    {
      return rc;
    }
    if (!isAscending())
    {
      rc = -rc;
    }
    return (rc);
  }
  /**
   * Overridden Method:
   * @see java.lang.Object#equals(java.lang.Object)
   * @param obj - The Object to Compare to
   * @return boolean
   */
  public boolean equals( Object obj )
  {
    boolean rc = (this == obj);
    if (!rc && obj instanceof RaceData)
    {
      RaceData that = (RaceData) obj;
      rc = this.getId().equals(that.getId());
      rc &= this.getVehicleNumber().equals(that.getVehicleNumber());
      rc &= this.getLane().equals(that.getLane());
      rc &= this.getRound().equals(that.getRound());
    }
    return rc;
  }
  /**
   * Overridden Method:
   * @see com.hgtable.ColumnDataCellValue#getColumnDataCell(int)
   * @param col - int, the Index of the Column to be displayed.
   * @return Object, The Data itself
   */
  public Object getColumnDataCell( int col )
  {
    Object obj = "";
    switch (col)
    {
      case 0 :
        obj = getEntryID();
        break;
      case 1 :
        obj = getId();
        break;
      case 2 :
        obj = getVehicleNumber();
        break;
      case 3 :
        obj = getRound();
        break;
      case 4 :
        obj = getHeat();
        break;
      case 5 :
        obj = getLane();
        break;
      case 6 :
        obj = getTime();
        break;
      case 7 :
        //obj = getPlacement();
        // This will Load a JLabel, Associated With a Placement
        obj = WinPlaceShow.getWinPlaceShow(getPlacement());
        if (WinPlaceShow.OUT_OF_RANGE.equals(obj))
        { // If out PLacement, is out of Range for a NO Win PLace Show, then
          // show a standard Integer
          obj = getPlacement();
        }
        break;
      case 8 :
        obj = getDate();
        break;
      default :
        break;
    }
    return obj;
  }
  /**
   * Method getDataAsVector. Returns the Data as a Vector, Note the Data is
   * returned in the Same order as stored in the Database.
   * @return Vector
   */
  public Vector getDataAsVector()
  {
    Vector v = new Vector();
    v.add(getEntryID());
    v.add(getId());
    v.add(getVehicleNumber());
    v.add(getRound());
    v.add(getHeat());
    v.add(getLane());
    v.add(getTime());
    v.add(getPlacement());
    v.add(getDate());
    return v;
  }

  /**
   * Method Returns the Date of the Entrered Record
   * @return Returns the date : Date.
   * 
   * @uml.property name="date"
   */
  public Date getDate()
  {
    if (date == null)
    {
      date = new Date();
    }
    return date;
  }

  /**
   * Returns the EntryID of the Database Record
   * @return Returns the entryID : Integer.
   * 
   * @uml.property name="entryID"
   */
  public Integer getEntryID()
  {
    if (entryID == null)
    {
      entryID = new Integer(-1);
    }
    return entryID;
  }

  /**
   * Returns the Heat number
   * @return Returns the heat : Integer.
   * 
   * @uml.property name="heat"
   */
  public Integer getHeat()
  {
    if (heat == null)
    {
      heat = new Integer(-1);
    }
    return heat;
  }

  /**
   * method getId. Returns the ID of the Racer for which the record is
   * associated.
   * @return Returns the id : Integer.
   * 
   * @uml.property name="id"
   */
  public Integer getId()
  {
    if (id == null)
    {
      id = new Integer(-1);
    }
    return id;
  }

  /**
   * Returns the Lane for which this record is displayed.
   * @return Returns the lane : Integer.
   * 
   * @uml.property name="lane"
   */
  public Integer getLane()
  {
    if (lane == null)
    {
      lane = new Integer(-1);
    }
    return lane;
  }

  /**
   * Returns the Current Placement of the racer for this heat
   * @see com.boyscouts.domain.RaceData#getPlacementIntValue()
   * @return Returns the placement : Placement.
   * 
   * @uml.property name="placement"
   */
  public Integer getPlacement()
  {
    if (placement == null)
    {
      placement = new Integer(-1);
    }
    return placement;
  }

  /**
   * Returns the Current Placement of the racer for this heat
   * @see com.boyscouts.domain.RaceData#getPlacement()
   * @return Returns the placement : int.
   */
  public int getPlacementIntValue()
  {
    return getPlacement().intValue();
  }

  /**
   * Returns the The Round that this heat is run.
   * @return Returns the round : Integer.
   * 
   * @uml.property name="round"
   */
  public Integer getRound()
  {
    if (round == null)
    {
      round = new Integer(-1);
    }
    return round;
  }

  /**
   * Returns the Time to run this heat for this Racer.
   * @return Returns the time : FixedDouble.
   * 
   * @uml.property name="time"
   */
  public FixedDouble getTime()
  {
    if (time == null)
    {
      time = new FixedDouble(100.0);
    }
    return time;
  }

  /**
   * Method getvehicleNumber. Returns vehicleNumber of the RaceData
   * @return String: Returns the vehicleNumber.
   * 
   * @uml.property name="vehicleNumber"
   */
  public String getVehicleNumber()
  {
    if (vehicleNumber == null)
    {
      vehicleNumber = "";
    }
    return vehicleNumber;
  }

  /**
   * Overridden Method:
   * @see java.lang.Object#hashCode()
   * @return int
   */
  public int hashCode()
  {
    int rc = 7;
    rc = rc * 11 + this.getId().hashCode();
    rc = rc * 11 + this.getLane().hashCode();
    rc = rc * 11 + this.getRound().hashCode();
    rc = rc * 11 + this.getVehicleNumber().hashCode();
    return rc;
  }

  /**
   * Returns if the Sort Order is Ascending or Descending. <BR><B>True</B>-
   * Ascending. <BR><B>False</B>- Descending <BR>
   * @return Returns the ascending : boolean.
   * 
   * @uml.property name="ascending"
   */
  public boolean isAscending()
  {
    return ascending;
  }

  /**
   * Method isEmptyRecord.  Returns True if the Record is not empty. Meaning that any of the Fields can be
   * set other than Their associated Default Values
   * @return boolean
   */
  public boolean isEmptyRecord()
  {
    boolean rc = true;
    RaceData emptyRD = new RaceData();
    rc = this.equals(emptyRD);
    return rc;
  }
  /**
   * Method isValidDataRecord.  Returns True if the Data Record is Valid and Not Empty.
   * Only the ID and The Vehicle Name are Comppared as these are the keys into the Relational Object
   * RacerPerson
   * @return boolean, true if Valid, false if not valid
   */
  public boolean isValidDataRecord()
  {
    boolean rc = false;
    rc = !this.getId().equals(new Integer(-1));
    rc &= !this.getVehicleNumber().equals("");
    return rc;
  }

  /**
   * Method setAscending. Sets the Comparisons to be ascending or descending
   * @param ascending : boolean The ascending to set.
   * 
   * @uml.property name="ascending"
   */
  public void setAscending( boolean ascending )
  {
    this.ascending = ascending;
  }

  /**
   * Method setDataAt. Method is used to populate the data Structure from the
   * DB Row Vector
   * @param i - int index of the Current Column
   * @param obj - Object, the Data
   */
  private void setDataAt( int i, Object obj )
  {
    switch (i)
    {
      case 0 :
        setEntryID(obj);
        break;
      case 1 :
        setId(obj);
        break;
      case 2 :
        setVehicleNumber((String) obj);
        break;
      case 3 :
        setRound(obj);
        break;
      case 4 :
        setHeat(obj);
        break;
      case 5 :
        setLane(obj);
        break;
      case 6 :
        setTime(obj);
        break;
      case 7 :
        setPlacement(obj);
        break;
      case 8 :
        setDate(obj);
        break;
      default :
        break;
    }
  }

  /**
   * Method setDate. Sets the date of the Current Record
   * @param date : Date The date to set.
   * 
   * @uml.property name="date"
   */
  public void setDate( Date date )
  {
    this.date = date;
  }

  /**
   * Method setDate. Sets the date of the Current Record
   * @param date : Object The date to set.
   */
  public void setDate( Object date )
  {
    Date theDate = null;
    if (date instanceof java.sql.Date)
    {
      theDate = (java.util.Date) date;
    }
    else
    {
      theDate = ParseData.parseDate("" + date);
    }
    setDate(theDate);
  }

  /**
   * Sets the Entry ID of the Database Record
   * @param entry : Integer The entryID to set.
   * 
   * @uml.property name="entryID"
   */
  public void setEntryID( Integer entry )
  {
    this.entryID = entry;
  }

  /**
   * Sets the Entry ID of the Database Record
   * @param entry : Object The entryID to set.
   */
  public void setEntryID( Object entry )
  {
    setEntryID(new Integer(ParseData.parseNum("" + entry, -1)));
  }
  /**
   * Method setHeat. Sets the number of the heat
   * @param heat : int The heat to set.
   */
  public void setHeat( int heat )
  {
    setHeat(new Integer(heat));
  }

  /**
   * Method setHeat. Sets the number of the heat
   * @param heat : Integer The heat to set.
   * 
   * @uml.property name="heat"
   */
  public void setHeat( Integer heat )
  {
    this.heat = heat;
  }

  /**
   * Method setHeat. Sets the number of the heat
   * @param heat : Object The heat to set.
   */
  public void setHeat( Object heat )
  {
    setHeat(new Integer(ParseData.parseNum("" + heat, -1)));
  }

  /**
   * Method setId. Set the ID of the racers for which this record is
   * associated
   * @param id : Integer The id to set.
   * 
   * @uml.property name="id"
   */
  public void setId( Integer id )
  {
    this.id = id;
  }

  /**
   * Method setId. Set the ID of the racers for which this record is
   * associated
   * @param id : Integer The id to set.
   */
  public void setId( Object id )
  {
    setId(new Integer(ParseData.parseNum("" + id, -1)));
  }
  /**
   * Method setLane. Sets the Lane for which this heat is running
   * @param lane : int The lane to set.
   */
  public void setLane( int lane )
  {
    setLane(new Integer(lane));
  }

  /**
   * Method setLane. Sets the Lane for which this heat is running
   * @param lane : Integer The lane to set.
   * 
   * @uml.property name="lane"
   */
  public void setLane( Integer lane )
  {
    this.lane = lane;
  }

  /**
   * Method setLane. Sets the Lane for which this heat is running
   * @param lane : Object The lane to set.
   */
  public void setLane( Object lane )
  {
    setLane(new Integer(ParseData.parseNum("" + lane, -1)));
  }
  /**
   * Method setPlacement. Sets the Placement of the current heat.
   * @param placement : int The placement to set.
   */
  public void setPlacement( int placement )
  {
    Integer place = new Integer(placement);
    setPlacement(place);
  }

  /**
   * Method setPlacement. Sets the Placement of the current heat.
   * @param placement : Integer The placement to set.
   * 
   * @uml.property name="placement"
   */
  public void setPlacement( Integer placement )
  {
    this.placement = placement;
  }

  /**
   * Method setPlacement. Sets the Placement of the current heat.
   * @param placement : Object The placement to set.
   */
  public void setPlacement( Object placement )
  {
    Integer thePlace = new Integer(ParseData.parseNum("" + placement, -1));
    setPlacement(thePlace);
  }
  /**
   * Method setRound. Sets the Round for this heat.
   * @param round : int The round to set.
   */
  public void setRound( int round )
  {
    setRound(new Integer(round));
  }

  /**
   * Method setRound. Sets the Round for this heat.
   * @param round : Integer The round to set.
   * 
   * @uml.property name="round"
   */
  public void setRound( Integer round )
  {
    this.round = round;
  }

  /**
   * Method setRound. Sets the Round for this heat.
   * @param round : Object The round to set.
   */
  public void setRound( Object round )
  {
    setRound(new Integer(ParseData.parseNum("" + round, -1)));
  }
  /**
   * Method setTime. Sets the Time to run the current heat for this Round for
   * this Racer.
   * @param time : double The time to set.
   */
  public void setTime( double time )
  { // invoke the other setTime
    setTime(new FixedDouble(time));
  }

  /**
   * Method setTime. Sets the Time to run the current heat for this Round for
   * this Racer.
   * @param time : FixedDouble The time to set.
   * 
   * @uml.property name="time"
   */
  public void setTime( FixedDouble time )
  { // Set the Time here, and Calcualte Performace
    this.time = time;
  }

  /**
   * Method setTime. Sets the Time to run the current heat for this Round for
   * this Racer.
   * @param time : Object The time to set.
   */
  public void setTime( Object time )
  { // Invoke the Other setTime
    setTime(new FixedDouble(time));
  }

  /**
   * Method setvehicleNumber. Sets vehicleNumber of the RaceData
   * @param vehicleNumber : String, The vehicleNumber to set.
   * 
   * @uml.property name="vehicleNumber"
   */
  public void setVehicleNumber( String vehicleNumber )
  {
    this.vehicleNumber = ParseData.caplitizeFirstChar(vehicleNumber);
  }

  /**
   * Overridden Method:
   * @see java.lang.Object#toString()
   * @return String
   */
  public String toString()
  {
    String sb = "";
    sb += "ENTRY=" + getEntryID() + "\n";
    sb += "ID=" + getVehicleNumber() + "\n";
    sb += "VEHICLE_NUMBER=" + getVehicleNumber() + "\n";
    sb += "ROUND=" + getRound() + "\n";
    sb += "HEAT=" + getHeat() + "\n";
    sb += "LANE=" + getLane() + "\n";
    sb += "TIME=" + getTime() + "\n";
    sb += "PLACEMENT=" + getPlacement() + "\n";
    sb += "DATE=" + getDate() + "\n";
    return sb;
  }
  /**
   * Method generateHeaderData. Self Generates a Column Titles for the
   * information that it knows about.
   * @return Vector
   */
  public static Vector generateHeaderData()
  {
    Vector v = new Vector();
    v.add("Entry");
    v.add("ID");
    v.add("VehicleNumber");
    v.add("Round");
    v.add("Heat");
    v.add("Lane");
    v.add("RaceTime");
    v.add("Place");
    v.add("EntryDate");
    return v;
  }

  /**
   * Method getcarPerformance.  Returns carPerformance of the RaceData
   * @return CarPerformance: Returns the carPerformance.
   * 
   * @uml.property name="carPerformance"
   */
  public CarPerformance getCarPerformance()
  {
    if (carPerformance == null)
    {
      TrackUtils trackUtils = TrackUtils.getInstance();
      carPerformance = trackUtils.computePerformance(this.getTime().doubleValue());
    }
    return carPerformance;
  }

  /**
   * Method getPoints.  
   * @return int
   */
  public int getPoints()
  {
    int thePlacement = getPlacementIntValue() - 1;
    int points = (thePlacement >= 0) ? (MAX_POINTS - thePlacement) : 0;
    return points;
  }
}