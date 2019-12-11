/*
 * RacerPerson.java
 */
package com.boyscouts.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import com.boyscouts.util.track.CarPerformance;
import com.boyscouts.util.track.TrackUtils;
import com.hgtable.ColumnDataCellTooltip;
import com.hgtable.ColumnDataCellValue;
import com.hgutil.ParseData;
import com.hgutil.data.FixedDouble;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   RacerPerson.java<BR>
 * Type Name:   RacerPerson<BR>
 * Description: Value bean representing one Person
 */

public class RacerPerson implements ColumnDataCellValue, ColumnDataCellTooltip, Comparable, Serializable, Cloneable
{
  /** Field <code>serialVersionUID</code> : long */
  static final long serialVersionUID = 7320475355890638660L;

  /**
   * Field <code>id</code>: Integer
   * 
   * @uml.property name="id"
   */
  private Integer id = null;

  /**
   * Field <code>district</code>: String
   * 
   * @uml.property name="district"
   */
  private String district = null;

  /**
   * Field <code>pack</code>: String
   * 
   * @uml.property name="pack"
   */
  private String pack = null;

  /**
   * Field <code>den</code>: String
   * 
   * @uml.property name="den"
   */
  private String den = null;

  /**
   * Field <code>lastName</code>: String
   * 
   * @uml.property name="lastName"
   */
  private String lastName = null;

  /**
   * Field <code>firstName</code>: String
   * 
   * @uml.property name="firstName"
   */
  private String firstName = null;

  /**
   * Field <code>vehicleNumber</code>: String
   * 
   * @uml.property name="vehicleNumber"
   */
  private String vehicleNumber = null;

  /**
   * Field <code>address</code>: String
   * 
   * @uml.property name="address"
   */
  private String address = null;

  /**
   * Field <code>city</code>: String
   * 
   * @uml.property name="city"
   */
  private String city = null;

  /**
   * Field <code>state</code>: String
   * 
   * @uml.property name="state"
   */
  private String state = null;

  /**
   * Field <code>postal</code>: String
   * 
   * @uml.property name="postal"
   */
  private String postal = null;

  /**
   * Field <code>phone</code>: PhoneNumber
   * 
   * @uml.property name="phone"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private PhoneNumber phone = null;

  /**
   * Field <code>minTime</code>: FixedDouble
   * 
   * @uml.property name="minTime"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private FixedDouble minTime = null;

  /**
   * Field <code>maxTime</code>: FixedDouble
   * 
   * @uml.property name="maxTime"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private FixedDouble maxTime = null;

  /**
   * Field <code>avgTime</code>: FixedDouble
   * 
   * @uml.property name="avgTime"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private FixedDouble avgTime = null;

  /**
   * Field <code>stdDev</code>: FixedDouble
   * 
   * @uml.property name="stdDev"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private FixedDouble stdDev = null;

  /**
   * Field <code>dateRegistered</code>: Date
   * 
   * @uml.property name="dateRegistered"
   */
  private Date dateRegistered = null;

  /**
   * Field <code>placement</code>: Integer
   * 
   * @uml.property name="placement"
   */
  private Integer placement = null;

  /**
   * Field <code>points</code> : int
   * 
   * @uml.property name="points"
   */
  private Integer points = null;

  /**
   * Field <code>raceName</code>: String
   * 
   * @uml.property name="raceName"
   */
  private String raceName = null;

  /**
   * Field <code>raceDataContainer</code>: RaceDataContainer
   * 
   * @uml.property name="raceDataContainer"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private RaceDataContainer raceDataContainer = null;

  /**
   * Field <code>carPerformance</code> : CarPerformance
   * 
   * @uml.property name="carPerformance"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private CarPerformance carPerformance = null;

  /** Field <code>compareIDOnly</code>: boolean */
  private static boolean compareIDOnly = false;
  /**
   * Method generateHeaderData.  Self Generates a Column Titles for the information that it knows about.
   * @return Vector
   */
  public static Vector generateHeaderData()
  {
    Vector v = new Vector();
    for (int i = 0; i < RacerPersonFieldName.LIST.length; i++)
    {
      v.add("" + RacerPersonFieldName.LIST[i]);
    }
    return v;
  }
  /**
   * Method isCompareIDOnly.  Returns compareIDOnly of the RacerPerson
   * @return boolean: Returns the compareIDOnly.
   */
  public static boolean isCompareIDOnly()
  {
    return compareIDOnly;
  }
  /**
   * Method setCompareIDOnly. Sets compareIDOnly of the RacerPerson.
   * If it is desired to only find a Person with the ID. then Create
   * an empty RacerPerson,<BR>
   * <PRE>
   * RacerPerson racerPerson = new RacerPerson();
   * RacerPerson.setCompareIDOnly(true);
   * racerPerson.setId(rd.getId());
   * <B>racerPerson = getRacePerson(racerPerson);</B>
   * RacerPerson.setCompareIDOnly(false);
   * </PRE><BR>
   * NOTE:  Important to set the flag back to it original State or all other comparisons will
   * be on the ID only.
   * @param compareIDOnly : boolean, The compareIDOnly to set.
   */
  public static void setCompareIDOnly( boolean compareIDOnly )
  {
    RacerPerson.compareIDOnly = compareIDOnly;
  }
  /**
   * Constructor for RacerPerson. 
   * 
   */
  public RacerPerson()
  {
    initData();
  }
  /**
   * Constructor for RacerPerson. Creates a RacerPerson using another Racer Person
   * @param racerPerson - RacerPerson, the data to copy from
   */
  public RacerPerson( RacerPerson racerPerson )
  {
    this(racerPerson.getDataAsVector());
  }
  /**
   * Constructor for RacerPerson. Creates a RacerPerson from a Vector. 
   * NOTE: the data vector is what is matched from the database.
   * @param dbRowData - Vector, the data from the database.
   */
  public RacerPerson( Vector dbRowData )
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
   * Method addRaceData.  This method will add a RaceData object to the
   * RaceDataContainer held by this RacerPerson, NoteAny new RaceData added to
   * the Racer Fire a request to updates the RacerPerson Times
   * @param rd - RaceData, the Object to be added
   */
  public void addRaceData( RaceData rd )
  {
    if (rd == null)
    {
      return;
    }
    if (this.raceDataContainer == null)
    {
      this.raceDataContainer = new RaceDataContainer();
    }
    raceDataContainer.addRaceData(rd);
  }
  /**
   * Method addRaceData.  This method will add a All RaceData objects to the
   * RaceDataContainer held by this RacerPerson
   * @param theDataContainer - RaceDataContainer, the Objects to be added
   */
  public void addRaceData( RaceDataContainer theDataContainer )
  {
    if (theDataContainer == null)
    {
      return;
    }
    int raceDataSize = theDataContainer.getSize();
    for (int i = 0; i < raceDataSize; i++)
    {
      RaceData rd = theDataContainer.elementAt(i);
      this.addRaceData(rd);
    }
  }
  /**
   * Overridden Method:  
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   * @param obj - Object, The Object to compare to
   * @return int, value &lt; 0 is less than, value = 0 if equal, &gt; if greater than. 
   */
  public int compareTo( Object obj )
  {
    int rc = -1;
    if (obj instanceof RacerPerson)
    {
      RacerPerson thatData = (RacerPerson) obj;
      this.id.compareTo(thatData.getId());
    }
    return (rc);
  }
  /**
   * Overridden Method:  Based on Flag CompareID.  
   * If the Flag is true, it compare only the ID of the RacerPerson.  
   * If the Flag is false It will compare the ID, Last Name, and First Name. 
   * @see java.lang.Object#equals(java.lang.Object)
   * @param obj - Object, the Object to compare to
   * @return boolean, True if equal, false otherwise. 
   */
  public boolean equals( Object obj )
  {
    boolean rc = (this == obj);
    if (!rc && obj instanceof RacerPerson)
    {
      RacerPerson that = (RacerPerson) obj;
      if (isCompareIDOnly())
      {
        rc = this.getId().equals(that.getId());
      }
      else
      {
        rc = this.getId().equals(that.getId());
        rc &= this.getLastName().equals(that.getLastName());
        rc &= this.getFirstName().equals(that.getFirstName());
      }
    }
    return rc;
  }

  /**
   * Method getAddress.  Returns the RacerPersons address
   * @return String, the Address
   * 
   * @uml.property name="address"
   */
  public String getAddress()
  {
    if (address == null)
    {
      address = "";
    }
    return address;
  }

  /**
   * Method getAvgTime.  Returns the Average Time
   * @return FixedDouble, The Average Time
   * 
   * @uml.property name="avgTime"
   */
  public FixedDouble getAvgTime()
  {
    if (avgTime == null)
    {
      avgTime = new FixedDouble();
    }
    return avgTime;
  }

  /**
   * Method getCarPerformance.  Returns carPerformance of the RacerPerson based on the Average Time
   * @return CarPerformance: Returns the carPerformance.
   * 
   * @uml.property name="carPerformance"
   */
  public CarPerformance getCarPerformance()
  {
    if (carPerformance == null)
    {
      TrackUtils trackUtils = TrackUtils.getInstance();
      carPerformance = trackUtils.computePerformance(this.getAvgTime().doubleValue());
    }
    return carPerformance;
  }

  /**
   * Overridden Method:  
   * @see com.hgtable.ColumnDataCellTooltip#getCellToolTip(int)
   * @param col - int, Index of the Col we are showing for this Record to display the Tooltip
   * @return String, The Tool Tip Text
   */
  public String getCellToolTip( int col )
  {
    String rc = "<HTML><B><H3>";
    switch (col)
    {
      case 0 :
        rc += "ID= " + getId();
        break;
      case 1 :
        rc += "District= " + getDistrict();
        break;
      case 2 :
        rc += "Pack= " + getPack();
        break;
      case 3 :
        rc += "Den= " + getDen();
        break;
      case 4 :
        rc += "Last Name= " + getLastName();
        break;
      case 5 :
        rc += "First Name= " + getFirstName();
        break;
      case 6 :
        rc += "Vehicle= " + getVehicleNumber();
        break;
      case 7 :
        rc += "Address= " + getAddress();
        break;
      case 8 :
        rc += "City= " + getCity();
        break;
      case 9 :
        rc += "State= " + getState();
        break;
      case 10 :
        rc += "Postal= " + getPostal();
        break;
      case 11 :
        rc += "Phone= " + getPhone();
        break;
      case 12 :
        rc += "Min Time= " + getMinTime();
        break;
      case 13 :
        rc += "Max Time= " + getMaxTime();
        break;
      case 14 :
        rc += "Avg Time= " + getAvgTime();
        break;
      case 15 :
        rc += "Std Dev= " + getStdDev();
        break;
      case 16 :
        rc += "Date Registered= " + getDateRegistered();
        break;
      default :
        rc = "";
        break;
    }
    if (rc.length() > 1)
    {
      rc += "</H3></B></HTML>";
    }
    return rc;
  }

  /**
   * Method getCity.  Returns the City of the racer Person
   * @return String, The City
   * 
   * @uml.property name="city"
   */
  public String getCity()
  {
    if (city == null)
    {
      city = "";
    }
    return city;
  }

  /**
   * Overridden Method:  Returns the Requested field by Column
   * @see com.hgtable.ColumnDataCellValue#getColumnDataCell(int)
   * @param col - int, the column to view
   * @return Object - The Data for the Column Specified.
   */
  public Object getColumnDataCell( int col )
  {
    Object obj = "";
    switch (col)
    {
      case 0 :
        obj = getId();
        break;
      case 1 :
        obj = getDistrict();
        break;
      case 2 :
        obj = getPack();
        break;
      case 3 :
        obj = getDen();
        break;
      case 4 :
        obj = getLastName();
        break;
      case 5 :
        obj = getFirstName();
        break;
      case 6 :
        obj = getVehicleNumber();
        break;
      case 7 :
        obj = getAddress();
        break;
      case 8 :
        obj = getCity();
        break;
      case 9 :
        obj = getState();
        break;
      case 10 :
        obj = getPostal();
        break;
      case 11 :
        obj = getPhone();
        break;
      case 12 :
        obj = getMinTime();
        break;
      case 13 :
        obj = getMaxTime();
        break;
      case 14 :
        obj = getAvgTime();
        break;
      case 15 :
        obj = getStdDev();
        break;
      case 16 :
        obj = getDateRegistered();
        break;
      case 17 :
        obj = getRaceName();
        break;
      case 18 :
        obj = getPlacement();
        break;
      case 19 :
        obj = getPoints();
        break;
      default :
        break;
    }
    return obj;
  }
  /**
   * Method getDataAsVector.  Returns all the data in a vector
   * @return Vector
   */
  public Vector getDataAsVector()
  {
    Vector v = new Vector();
    v.add(getId());
    v.add(getDistrict());
    v.add(getPack());
    v.add(getDen());
    v.add(getLastName());
    v.add(getFirstName());
    v.add(getVehicleNumber());
    v.add(getAddress());
    v.add(getCity());
    v.add(getState());
    v.add(getPostal());
    v.add(getPhone());
    v.add(getMinTime());
    v.add(getMaxTime());
    v.add(getAvgTime());
    v.add(getStdDev());
    v.add(getDateRegistered());
    v.add(getRaceName());
    v.add(getPlacement());
    v.add(getPoints());
    return v;
  }

  /**
   * Method getDateRegistered.  Returns the Date Registered
   * @return Date, the DAte registered.
   * 
   * @uml.property name="dateRegistered"
   */
  public Date getDateRegistered()
  {
    if (dateRegistered == null)
    {
      dateRegistered = new Date();
    }
    return dateRegistered;
  }

  /**
   * Method getDen.  Gets the Den of the Racer Person
   * @return String, the Den
   * 
   * @uml.property name="den"
   */
  public String getDen()
  {
    if (den == null)
    {
      den = "";
    }
    return den;
  }

  /**
   * Method getDistrict.  Returns the District of the racer Person
   * @return String, the District
   * 
   * @uml.property name="district"
   */
  public String getDistrict()
  {
    if (district == null)
    {
      district = "";
    }
    return district;
  }

  /**
   * Method getFirstName.  Gets the First Name of the Racer Person
   * @return String, the First Name
   * 
   * @uml.property name="firstName"
   */
  public String getFirstName()
  {
    if (firstName == null)
    {
      firstName = "";
    }
    return firstName;
  }

  /**
   * Method getId.  Returns the ID, This id is the one that corresponds to the data in the database
   * @see RacerPerson#getIdAsInt()
   * @return Integer, The ID of the RacerPerson
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
   * Method getIdAsInt.  Returns the ID, This id is the one that corresponds to the data in the database
   * @see RacerPerson#getId()
   * @return int, The ID of the RacerPerson
   */
  public int getIdAsInt()
  {
    return getId().intValue();
  }

  /**
   * Method getLastName.  Returns the Last Name of the RacerPerson
   * @return String, the Last Name
   * 
   * @uml.property name="lastName"
   */
  public String getLastName()
  {
    if (lastName == null)
    {
      lastName = "";
    }
    return lastName;
  }

  /**
   * Method getMaxTime.  Returns the Max Time of the Racer Person
   * @return FixedDouble, the Max Time of the Racer.
   * 
   * @uml.property name="maxTime"
   */
  public FixedDouble getMaxTime()
  {
    if (maxTime == null)
    {
      maxTime = new FixedDouble(0.0);
    }
    return maxTime;
  }

  /**
   * Method getMinTime.  Returns the Min Time of the RacerPerson
   * @return FixedDouble, The Min Time of the Racer
   * 
   * @uml.property name="minTime"
   */
  public FixedDouble getMinTime()
  {
    if (minTime == null)
    {
      minTime = new FixedDouble();
    }
    return minTime;
  }

  /**
   * Method getPack.  Returns the PAck of the Racer Person
   * @return String, the Pack of the Racer
   * 
   * @uml.property name="pack"
   */
  public String getPack()
  {
    if (pack == null)
    {
      pack = "";
    }
    return pack;
  }

  /**
   * Method getPhone.  Returns the Phone of the Racer Person
   * @return PhoneNumber, The Phone Number of the Racer
   * 
   * @uml.property name="phone"
   */
  public PhoneNumber getPhone()
  {
    if (phone == null)
    {
      phone = new PhoneNumber("000", "000-0000");
    }
    return phone;
  }

  /**
   * Method getPlacement.  Gets the placement of the racer during a race
   * @return Integer, the Placement
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
   * Method getPoints.  Returns points of the RacerPerson accumulated during a race
   * @return Integer, the points accumulated during a race.
   * 
   * @uml.property name="points"
   */
  public Integer getPoints()
  {
    if (points == null)
    {
      points = new Integer(-1);
    }
    return points;
  }

  /**
   * Method getPostal.  Returns the Postal Code of the Racer Person
   * @return String, the Postal Code of the racer
   * 
   * @uml.property name="postal"
   */
  public String getPostal()
  {
    if (postal == null)
    {
      postal = "";
    }
    return postal;
  }

  /**
   * Method getRaceDataContainer.  Returns raceDataContainer of the RacerPerson. 
   * This is the all the heats for a RacerPerson within a current race.
   * @return RaceDataContainer
   * 
   * @uml.property name="raceDataContainer"
   */
  public RaceDataContainer getRaceDataContainer()
  {
    return raceDataContainer;
  }

  /**
   * Method getRaceName.   Returns raceName of the RacerPerson
   * @return String
   * 
   * @uml.property name="raceName"
   */
  public String getRaceName()
  {
    if (raceName == null)
    {
      raceName = "";
    }
    return raceName;
  }

  /**
   * Method getSessionCopy.  Returns a Session Copy of the this current RacerPerson.  
   * This is almost a complete Identical copy of the original.  
   * Some of the data has been cleared to protect the innocent
   * @return RacerPerson
   */
  public RacerPerson getSessionCopy()
  {
    RacerPerson rp = null;
    try
    {
      rp = (RacerPerson) clone();
      rp.id = new Integer(this.getId().intValue());
      rp.district = this.getDistrict();
      rp.pack = this.getPack();
      rp.den = this.getDen();
      rp.lastName = this.getLastName();
      rp.firstName = this.getFirstName();
      rp.vehicleNumber = this.getVehicleNumber();
      rp.address = this.getAddress();
      rp.city = this.getCity();
      rp.state = this.getState();
      rp.postal = this.getPostal();
      rp.phone = this.getPhone().getSessionCopy();
      rp.raceName = this.getRaceName();
      rp.dateRegistered = this.getDateRegistered(); // null;
      rp.placement = this.getPlacement(); //null;
      rp.points = this.getPoints(); // null;
      rp.raceDataContainer = null;
      rp.minTime = this.getMinTime(); // null;
      rp.maxTime = this.getMaxTime(); // null;
      rp.avgTime = this.getAvgTime(); // null;
      rp.stdDev = this.getStdDev(); // null;
      rp.carPerformance = null;
    }
    catch (CloneNotSupportedException exc)
    {
    }
    return rp;
  }

  /**
   * Method getState.  Returns the State of the RacerPerson
   * @return String, The State
   * 
   * @uml.property name="state"
   */
  public String getState()
  {
    if (state == null)
    {
      state = "";
    }
    return state;
  }

  /**
   * Method getStdDev.  Gets the Standard Deviation of the Racer Person.  
   * The Standard deviation is how well the racer is performing against a perfect car.
   * The Deviation value is the closer to zero a deviation is the better the value 
   * @return FixedDouble
   * 
   * @uml.property name="stdDev"
   */
  public FixedDouble getStdDev()
  {
    if (stdDev == null)
    {
      stdDev = new FixedDouble();
    }
    return stdDev;
  }

  /**
   * Method getVehicleNumber.  Returns the vehicle number
   * @return String, The Vehicle Number
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
   * Overridden Method: Based on the CompareID only flag.  Computes a hash code of the current class object.
   * @see java.lang.Object#hashCode()
   * @return int - Computes the HashCode, in accordance with the equals() method.  
   */
  public int hashCode()
  {
    int rc = 7;
    if (isCompareIDOnly())
    {
      rc = rc * 11 + this.getId().hashCode();
    }
    else
    {
      rc = rc * 11 + this.getId().hashCode();
      rc = rc * 11 + this.getLastName().hashCode();
      rc = rc * 11 + this.getFirstName().hashCode();
    }
    return rc;
  }
  /**
   * Method initData.  Resets the RacersPersons Statistical data. 
   * This has package scope - No qualifier on the Method as it 
   * will be used by the container
   */
  void initData()
  {
    this.minTime = null;
    this.maxTime = null;
    this.avgTime = null;
    this.stdDev = null;
    this.dateRegistered = null;
    this.placement = null;
    this.points = null;
    //this.raceName = null;
    if (this.raceDataContainer != null)
    {
      this.raceDataContainer.clear();
    }
    this.raceDataContainer = null;
    this.carPerformance = null;
  }
  /**
   * Method isEmptyRecord.  Returns if the record is empty
   * @return boolean - True if the Record is Empty, False if it is populated
   */
  public boolean isEmptyRecord()
  {
    boolean rc = true;
    RacerPerson emptyRP = new RacerPerson();
    rc = this.equals(emptyRP);
    return rc;
  }

  /**
   * Method setAddress.  Set the RacersPersons address
   * @param address - String, The address to set.
   * 
   * @uml.property name="address"
   */
  public void setAddress( String address )
  {
    this.address = address; //stripPunctuation(address);
  }

  /**
   * Method setAvgTime. Set the Average Time
   * @param avgTime : double The avgTime to set.
   */
  public void setAvgTime( double avgTime )
  {
    this.avgTime = new FixedDouble(avgTime);
  }
  /**
   * Method setAvgTime. Set the Average Time
   * @param avgTime : Object The avgTime to set.
   */
  public void setAvgTime( Object avgTime )
  {
    this.avgTime = new FixedDouble(avgTime);
  }

  /**
   * Method setCity. Sets the City of the Racer Person
   * @param city : String The city to set.
   * 
   * @uml.property name="city"
   */
  public void setCity( String city )
  {
    this.city = ParseData.caplitizeFirstChar(city);
  }

  /**
   * Method setDataAt. Set the Data at the Field Object.
   * @param i - int, the Column
   * @param obj - Object, the Data Object
   */
  private void setDataAt( int i, Object obj )
  {
    try
    {
      switch (i)
      {
        case 0 :
          setId(obj);
          break;
        case 1 :
          setDistrict((String) obj);
          break;
        case 2 :
          setPack((String) obj);
          break;
        case 3 :
          setDen((String) obj);
          break;
        case 4 :
          setLastName((String) obj);
          break;
        case 5 :
          setFirstName((String) obj);
          break;
        case 6 :
          setVehicleNumber((String) obj);
          break;
        case 7 :
          setAddress((String) obj);
          break;
        case 8 :
          setCity((String) obj);
          break;
        case 9 :
          setState((String) obj);
          break;
        case 10 :
          setPostal((String) obj);
          break;
        case 11 :
          setPhone((String) obj);
          break;
        case 12 :
          setMinTime(obj);
          break;
        case 13 :
          setMaxTime(obj);
          break;
        case 14 :
          setAvgTime(obj);
          break;
        case 15 :
          setStdDev(obj);
          break;
        case 16 :
          setDateRegistered(obj);
          break;
        case 17 :
          setRaceName(obj);
          break;
        case 18 :
          setPlacement((Integer) obj);
          break;
        case 19 :
          setPoints((Integer) obj);
          break;
        default :
          break;
      }
    }
    catch (ClassCastException exc)
    { // Swallow Exception
    }
  }

  /**
   * Method setDateRegistered. Set the Date Registered
   * @param dateRegistered : Date The dateRegistered to set.
   * 
   * @uml.property name="dateRegistered"
   */
  public void setDateRegistered( Date dateRegistered )
  {
    this.dateRegistered = dateRegistered;
  }

  /**
   * Method setDateRegistered. Set the Date Registered
   * @param dateRegistered : Object The dateRegistered to set.
   */
  public void setDateRegistered( Object dateRegistered )
  {
    Date theDate = null;
    if (dateRegistered instanceof java.sql.Date)
    {
      theDate = (java.util.Date) dateRegistered;
    }
    else
    {
      theDate = ParseData.parseDate("" + dateRegistered);
    }
    setDateRegistered(theDate);
  }

  /**
   * Method setDen. Sets the Den of the Racer Person
   * @param den : String The den to set.
   * 
   * @uml.property name="den"
   */
  public void setDen( String den )
  {
    this.den = ParseData.caplitizeFirstChar(den);
  }

  /**
   * Method setDistrict. Sets the District of the Racer Person
   * @param district : String The district to set.
   * 
   * @uml.property name="district"
   */
  public void setDistrict( String district )
  {
    this.district = ParseData.caplitizeFirstChar(district);
  }

  /**
   * Method setFirstName. Set the First Name of the Racer Person
   * @param firstName : String The firstName to set.
   * 
   * @uml.property name="firstName"
   */
  public void setFirstName( String firstName )
  {
    this.firstName = ParseData.caplitizeFirstChar(firstName);
  }

  /**
   * Method setId. Set the ID
   * @param id : Integer The id to set.
   * 
   * @uml.property name="id"
   */
  public void setId( Integer id )
  {
    this.id = id;
  }

  /**
   * Method setId. Set the ID
   * @param id : Object The id to set.
   */
  public void setId( Object id )
  {
    setId(new Integer(ParseData.parseNum("" + id, -1)));
  }

  /**
   * Method setLastName. Set the Last Name of the Racer Person
   * @param lastName : String The lastName to set.
   * 
   * @uml.property name="lastName"
   */
  public void setLastName( String lastName )
  {
    this.lastName = ParseData.caplitizeFirstChar(lastName);
  }

  /**
   * Method setMaxTime. Set the MAx Time of the Racer Person
   * @param maxTime : double The maxTime to set.
   */
  public void setMaxTime( double maxTime )
  {
    this.maxTime = new FixedDouble(maxTime);
  }
  /**
   * Method setMaxTime. Set the MAx Time of the Racer Person
   * @param maxTime : Object The maxTime to set.
   */
  public void setMaxTime( Object maxTime )
  {
    this.maxTime = new FixedDouble(maxTime);
  }
  /**
   * Method setMinTime. Sets the Min Time of the Racer Person
   * @param minTime : double The minTime to set.
   */
  public void setMinTime( double minTime )
  {
    this.minTime = new FixedDouble(minTime);
  }
  /**
   * Method setMinTime. Sets the Min Time of the Racer Person
   * @param minTime : Object The minTime to set.
   */
  public void setMinTime( Object minTime )
  {
    this.minTime = new FixedDouble(minTime);
  }

  /**
   * Method setPack. Set the Pack of the RacerPerson
   * @param pack : String The pack to set.
   * 
   * @uml.property name="pack"
   */
  public void setPack( String pack )
  {
    this.pack = ParseData.caplitizeFirstChar(pack);
  }

  /**
   * Method setPhone. Set the Phoen of the Racer Person
   * @param phone : PhoneNumber The phone to set.
   * 
   * @uml.property name="phone"
   */
  public void setPhone( PhoneNumber phone )
  {
    this.phone = phone;
  }

  /**
   * Method setPhone. Set the Phoen of the Racer Person
   * @param phone : String The phone to set.
   */
  public void setPhone( String phone )
  {
    PhoneNumber phoneNumber = new PhoneNumber(phone);
    setPhone(phoneNumber);
  }
  /**
   * Method setPlacement. Sets the placement of the racer
   * @param placement : int The placement to set.
   */
  public void setPlacement( int placement )
  {
    setPlacement(new Integer(placement));
  }

  /**
   * Method setPlacement. Sets the placement of the racer
   * @param placement : Integer The placement to set.
   * 
   * @uml.property name="placement"
   */
  public void setPlacement( Integer placement )
  {
    this.placement = placement;
  }

  /**
   * Method setPoints.  set the points earned by the racer.
   * @param newPoints -int, the new Points to set
   */
  private void setPoints( int newPoints )
  {
    setPoints(new Integer(newPoints));
  }

  /**
   * Method setPoints.  set the points earned by the racer.
   * @param newPoints - Integer, the new Points to set
   * 
   * @uml.property name="points"
   */
  private void setPoints( Integer newPoints )
  {
    points = newPoints;
  }

  /**
   * Method setPostal. Set the Postal Code of the RacerPerson
   * @param postal : String The postal to set.
   * 
   * @uml.property name="postal"
   */
  public void setPostal( String postal )
  {
    this.postal = postal;
  }

  /**
   * Method setRaceDataContainer. Sets raceDataContainer of the RacerPerson
   * <BR><B>NOTE: This method will invoke the addRaceData(RaceDataContainer)
   * </B>
   * @param raceData : RaceDataContainer, The raceDataContainer to
   *          set.
   * 
   * @uml.property name="raceDataContainer"
   */
  public void setRaceDataContainer( RaceDataContainer raceData )
  {
    this.raceDataContainer = raceData;
  }

  /**
   * Method setRaceName. Sets raceName of the RacerPerson
   * @param raceName : Object, The raceName to set.
   */
  public void setRaceName( Object raceName )
  {
    setRaceName("" + raceName);
  }

  /**
   * Method setraceName. Sets raceName of the RacerPerson
   * @param raceName : String, The raceName to set.
   * 
   * @uml.property name="raceName"
   */
  public void setRaceName( String raceName )
  {
    this.raceName = raceName;
  }

  /**
   * Method setState. Set the State of the Racer Person
   * @param state : String The state to set.
   * 
   * @uml.property name="state"
   */
  public void setState( String state )
  {
    if (state != null)
    {
      state = state.toUpperCase();
    }
    this.state = state;
  }

  /**
   * Method setStdDev. Set the Standard Deviation of the Racer Person
   * @param stdDev : double The stdDev to set.
   */
  public void setStdDev( double stdDev )
  {
    this.stdDev = new FixedDouble(stdDev);
  }
  /**
   * Method setStdDev. Set the Standard Deviation of the Racer Person
   * @param stdDev : Object The stdDev to set.
   */
  public void setStdDev( Object stdDev )
  {
    this.stdDev = new FixedDouble(stdDev);
  }

  /**
   * Method setVehicleNumber. Set the vehicle number
   * @param vehicleNumber : Integer The vehicleNumber to set.
   * 
   * @uml.property name="vehicleNumber"
   */
  public void setVehicleNumber( String vehicleNumber )
  {
    this.vehicleNumber = ParseData.caplitizeFirstChar(vehicleNumber);
  }

  /**
   * Overridden Method: Provides a String representation.
   * @see java.lang.Object#toString()
   * @return String
   */
  public String toString()
  {
    String sb = "";
    sb += "ID=" + getId() + "\n";
    sb += "DISTRICT=" + getDistrict() + "\n";
    sb += "PACK=" + getPack() + "\n";
    sb += "DEN=" + getDen() + "\n";
    sb += "LAST_NAME=" + getLastName() + "\n";
    sb += "FIRST_NAME=" + getFirstName() + "\n";
    sb += "VEHICLE_NUMBER=" + getVehicleNumber() + "\n";
    sb += "ADDRESS=" + getAddress() + "\n";
    sb += "CITY=" + getCity() + "\n";
    sb += "STATE=" + getState() + "\n";
    sb += "POSTAL=" + getPostal() + "\n";
    sb += "PHONE_NUMBER=" + getPhone() + "\n";
    sb += "MIN_TIME=" + getMinTime() + "\n";
    sb += "MAX_TIME=" + getMaxTime() + "\n";
    sb += "AVG_TIME=" + getAvgTime() + "\n";
    sb += "STD_DEV=" + getStdDev() + "\n";
    sb += "DATE_REGISTERED=" + getDateRegistered() + "\n";
    sb += "PLACEMENT=" + getPlacement() + "\n";
    sb += "POINTS=" + getPoints() + "\n";
    return sb;
  }
  /**
   * Method updateRacerTimes. Method will Update all RacersTimes, as recorded
   * by the RaceDataContainer
   */
  public void updateRacerTimes()
  {
    if (raceDataContainer == null)
    {
      return;
    }
    this.setMinTime(raceDataContainer.getMinTime());
    this.setMaxTime(raceDataContainer.getMaxTime());
    this.setAvgTime(raceDataContainer.getAvgTime());
    this.setStdDev(raceDataContainer.getStdDev());
    this.setPoints(raceDataContainer.getTotalPoints());
  }

}
