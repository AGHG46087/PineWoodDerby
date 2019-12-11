/*
 * @author:		Owner
 * Package:		com.boyscouts.publish.results
 * File Name:		XMLPublishResults.java
 */
package com.boyscouts.publish.results;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Date;

import com.boyscouts.domain.RaceData;
import com.boyscouts.domain.RaceDataContainer;
import com.boyscouts.domain.RacerContainer;
import com.boyscouts.domain.RacerPerson;
import com.boyscouts.util.track.TrackUtils;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.publish.results<BR>
 * File Name:   XMLPublishResults.java<BR>
 * Type Name:   XMLPublishResults<BR>
 * Description: Generates the XML for the Racer Persons.<BR>
 * For the main entry point look for the method. generateCompleteDocument()
 * @see XMLPublishResults#generateCompleteDocument(RacerContainer)
 */

public class XMLPublishResults
{

  /**
   * Field <code>spaces</code> : int - the number of spaces to indent
   * 
   * @uml.property name="spaces"
   */
  private int spaces = 0;

  /** Field <code>document</code> : StringBuffer - the Result Set XML*/
  private static final String XML_PROLOG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
  /** Field <code>outFile</code> : PrintStream */
  private PrintStream outFile = null;
  /**
   * Constructor for XMLPublishResults. 
   * @param outFile - PrintStream, the File to which the Data will be printed.
   */
  public XMLPublishResults( PrintStream outFile )
  {
    this.outFile = outFile;
  }

  /**
   * Method addIndent.  Adds the Spaces to the indention level
   * @param level - int
   * 
   * @uml.property name="spaces"
   */
  private void addIndent( int level )
  {
    spaces += level;
  }

  /**
   * Method format.  Formats the Date as a Date Format
   * @param date - Date, the Date to format
   * @return String - result of format
   */
  private String format( Date date )
  {
    return com.hgutil.ParseData.format(date, "MM-dd-yyyy");
  }
  /**
   * Method dateTag.  Creates A DATE Tag in the form of a String.
   * @return String
   */
  private String dateTag()
  {
    String sb = "";
    String formatedDate = format(new Date());

    addIndent(2);
    sb += indent() + "<RACE_DATE>" + formatedDate + "</RACE_DATE>";
    addIndent(-2);

    return sb;
  }
  /**
   * Method printTrackDetails.  Prints the Track details to a XML formatted file.
   */
  private void printTrackDetails()
  {
    TrackUtils trackUtils = TrackUtils.getInstance();
    addIndent(2);
    println(indent() + "<TRACK_DETAILS>");
    addIndent(2);
    println(indent() + "<TRACK_LENGTH_FEET>" + trackUtils.getTrackLengthFeet() + "</TRACK_LENGTH_FEET>");
    println(indent() + "<TRACK_LENGTH_INCHES>" + trackUtils.getTrackLengthInches() + "</TRACK_LENGTH_INCHES>");
    println(indent() + "<IDEAL_TOTAL_TIME>" + trackUtils.getIdealTotalTime() + "</IDEAL_TOTAL_TIME>");
    println(indent() + "<IDEAL_FINISH_SPEED>" + trackUtils.getIdealFinishSpeed() + "</IDEAL_FINISH_SPEED>");
    println(indent() + "<IDEAL_AVERAGE_SPEED>" + trackUtils.getIdealAverageSpeed() + "</IDEAL_AVERAGE_SPEED>");
    addIndent(-2);
    println(indent() + "</TRACK_DETAILS>");
    addIndent(-2);
  }
  /**
   * Method generateCompleteDocument.  This is the main entry point to generate a complete document.
   * It is possible to generate sub components.  By calling one of the other. public methods.<BR>
   * @see XMLPublishResults#generateRacerPersonDocument(RacerPerson)
   * @see XMLPublishResults#generateResultsByDenDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByNameDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByPackDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByPlacementDocument(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be printed
   */
  public void generateCompleteDocument( RacerContainer racerContainer )
  {
    println(XML_PROLOG);
    println("<RACE_RESULTS>");
    println(dateTag());
    printTrackDetails();
    printResultsByRacerPlacement(racerContainer);
    printResultsByRacerPack(racerContainer);
    printResultsByRacerDen(racerContainer);
    printResultsByRacerName(racerContainer);
    println("</RACE_RESULTS>");
  }
  /**
   * Method generateRacerPersonDocument.  This method is designed to Generate a 
   * Document for a single person and all his details.
   * @see XMLPublishResults#generateCompleteDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByDenDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByNameDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByPackDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByPlacementDocument(RacerContainer)
   * @param rp - RacerPerson, the Person to generate the document.
   */
  public void generateRacerPersonDocument( RacerPerson rp )
  {
    println(XML_PROLOG);
    println("<RACE_RESULTS>");
    println(dateTag());
    printTrackDetails();
    printRacerPersonXMLComplete(rp);
    println("</RACE_RESULTS>");
  }
  /**
   * Method generateResultsByDenDocument.  This will generate a Document, listing by the Den.
   * @see XMLPublishResults#generateCompleteDocument(RacerContainer)
   * @see XMLPublishResults#generateRacerPersonDocument(RacerPerson)
   * @see XMLPublishResults#generateResultsByNameDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByPackDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByPlacementDocument(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be printed
   */
  public void generateResultsByDenDocument( RacerContainer racerContainer )
  {
    println(XML_PROLOG);
    println("<RACE_RESULTS>");
    println(dateTag());
    printTrackDetails();
    printResultsByRacerDen(racerContainer);
    println("</RACE_RESULTS>");
  }
  /**
   * Method generateResultsByNameDocument.  This will generate a document, listing by the name of the racers.
   * @see XMLPublishResults#generateCompleteDocument(RacerContainer)
   * @see XMLPublishResults#generateRacerPersonDocument(RacerPerson)
   * @see XMLPublishResults#generateResultsByDenDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByPackDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByPlacementDocument(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be printed
   */
  public void generateResultsByNameDocument( RacerContainer racerContainer )
  {
    println(XML_PROLOG);
    println("<RACE_RESULTS>");
    println(dateTag());
    printTrackDetails();
    printResultsByRacerName(racerContainer);
    println("</RACE_RESULTS>");
  }
  /**
   * Method generateResultsByPackDocument.  This will generate a document, l;isting by the Pack of the racers.
   * @see XMLPublishResults#generateCompleteDocument(RacerContainer)
   * @see XMLPublishResults#generateRacerPersonDocument(RacerPerson)
   * @see XMLPublishResults#generateResultsByDenDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByNameDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByPlacementDocument(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be printed
   */
  public void generateResultsByPackDocument( RacerContainer racerContainer )
  {
    println(XML_PROLOG);
    println("<RACE_RESULTS>");
    println(dateTag());
    printTrackDetails();
    printResultsByRacerPack(racerContainer);
    println("</RACE_RESULTS>");
  }
  /**
   * Method generateResultsByPlacementDocument.  This will generate a document, listing by the placement of the racers.
   * @see XMLPublishResults#generateCompleteDocument(RacerContainer)
   * @see XMLPublishResults#generateRacerPersonDocument(RacerPerson)
   * @see XMLPublishResults#generateResultsByDenDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByNameDocument(RacerContainer)
   * @see XMLPublishResults#generateResultsByPackDocument(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be printed
   */
  public void generateResultsByPlacementDocument( RacerContainer racerContainer )
  {
    println(XML_PROLOG);
    println("<RACE_RESULTS>");
    println(dateTag());
    printTrackDetails();
    printResultsByRacerPlacement(racerContainer);
    println("</RACE_RESULTS>");
  }
  /**
   * Method getOrdinalPlacementText.  Converts the int formal param to a Ordinal Text
   * @param value - int, the number ot convert
   * @return String - result of conversion
   */
  private String getOrdinalPlacementText( int value )
  {
    String retVal = "" + value;
    char ch = retVal.charAt(retVal.length() - 1);
    switch (ch)
    {
      case '1' :
        retVal += "st";
        break;
      case '2' :
        retVal += "nd";
        break;
      case '3' :
        retVal += "rd";
        break;
      default :
        retVal += "th";
        break;
    }
    retVal += " Place";
    return retVal;
  }
  /**
   * Method getOrdinalText.  Converts the int formal param to a Ordinal Text
   * @param value - Integer, the number ot convert
   * @return String - result of conversion
   */
  private String getOrdinalPlacementText( Integer value )
  {
    int x = -100;
    if (value != null)
    {
      x = value.intValue();
    }
    return getOrdinalPlacementText(x);
  }
  /**
   * Method indent.  Indents the document the specified number set by the variable <B>spaces</B>
   * @return String
   */
  private String indent()
  {
    String retVal = "";
    for (int i = 0; i < spaces; i++)
    {
      retVal += ' ';
    }
    return retVal;
  }
  /**
   * Method println.  Prints a Line to the Outfile if it is not null, Otherwise prints to Stdout
   * @param sz - String
   */
  private void println( String sz )
  {
    if (outFile != null)
    {
      outFile.println(sz);
    }
    else
    {
      System.out.println(sz);
    }
  }
  /**
   * Method printRaceDataXML.  Prints the Race Data to a XML formated file.
   * @see XMLPublishResults#printRaceDataXML(RaceDataContainer)
   * @param rd - RaceData, the Data to be printed
   */
  private void printRaceDataXML( RaceData rd )
  {
    if (rd == null)
    {
      return;
    }
    String dateField = format(rd.getDate());
    addIndent(2);
    println(indent() + "<RACE_DATA>");
    addIndent(2);
    String descriptionText = "Round " + rd.getRound() + ", Heat " + rd.getHeat();
    String placementText = getOrdinalPlacementText(rd.getPlacement());
    println(indent() + "<DESCRIPTION>" + descriptionText + "</DESCRIPTION>");
    println(indent() + "<DB_ENTRY>" + rd.getEntryID() + "</DB_ENTRY>");
    println(indent() + "<ID>" + rd.getId() + "</ID>");
    println(indent() + "<VEHICLE_NUMBER>" + rd.getVehicleNumber() + "</VEHICLE_NUMBER>");
    println(indent() + "<ROUND>" + rd.getRound() + "</ROUND>");
    println(indent() + "<HEAT>" + rd.getHeat() + "</HEAT>");
    println(indent() + "<LANE>" + rd.getLane() + "</LANE>");
    println(indent() + "<TIME>" + rd.getTime() + "</TIME>");
    println(indent() + "<PLACEMENT>" + placementText + "</PLACEMENT>");
    println(indent() + "<POINTS>" + rd.getPoints() + "</POINTS>");
    println(indent() + "<DATE>" + dateField + "</DATE>");
    // Car Performance to be as Attributes
    println(indent() + "<CAR_PERFORMANCE " + rd.getCarPerformance() + "/>");
    addIndent(-2);
    println(indent() + "</RACE_DATA>");
    addIndent(-2);
  }
  /**
   * Method printRaceDataXML.  Prints the Race Data container to a XML formatted file.
   * @see XMLPublishResults#printRaceDataXML(RaceData)
   * @param raceData - RaceDataContainer, the Data to be printed
   */
  private void printRaceDataXML( RaceDataContainer raceData )
  {
    if (raceData == null)
    {
      return;
    }
    println(indent() + "<RACE_DETAILS>");
    addIndent(2);
    println(indent() + "<RACE_DETAILS_COUNT>" + raceData.getSize() + "</RACE_DETAILS_COUNT>");
    addIndent(-2);
    for (int i = 0; i < raceData.getSize(); i++)
    {
      printRaceDataXML(raceData.elementAt(i));
    }
    println(indent() + "</RACE_DETAILS>");
  }
  /**
   * Method printRacerPersonXMLComplete.  Prints the RacerPerson to the XML Formatted file.
   * @see XMLPublishResults#printRacerPersonXMLDen(RacerPerson)
   * @see XMLPublishResults#printRacerPersonXMLPack(RacerPerson)
   * @see XMLPublishResults#printRacerPersonXMLPlacement(RacerPerson)
   * @param rp - RacerPerson, the Data to be printed
   */
  private void printRacerPersonXMLComplete( RacerPerson rp )
  {
    if (rp == null)
    {
      return;
    }
    String dateField = format(rp.getDateRegistered());
    String placementText = getOrdinalPlacementText(rp.getPlacement());
    String descriptionText = rp.getLastName() + ", " + rp.getFirstName();
    addIndent(2);
    println(indent() + "<RACER_PERSON>");
    addIndent(2);
    println(indent() + "<DESCRIPTION>" + descriptionText + "</DESCRIPTION>");
    println(indent() + "<DB_ID>" + rp.getId() + "</DB_ID>");
    println(indent() + "<DISTRICT>" + rp.getDistrict() + "</DISTRICT>");
    println(indent() + "<PACK>" + rp.getPack() + "</PACK>");
    println(indent() + "<DEN>" + rp.getDen() + "</DEN>");
    println(indent() + "<LAST_NAME>" + rp.getLastName() + "</LAST_NAME>");
    println(indent() + "<FIRST_NAME>" + rp.getFirstName() + "</FIRST_NAME>");
    //println(indent() + "<ADDRESS>" + rp.getAddress() + "</ADDRESS>");
    //println(indent() + "<CITY>" + rp.getCity() + "</CITY>");
    //println(indent() + "<STATE>" + rp.getState() + "</STATE>");
    //println(indent() + "<POSTAL>" + rp.getPostal() + "</POSTAL>");
    //println(indent() + "<PHONE_NUMBER>" + rp.getPhone() + "</PHONE_NUMBER>");
    println(indent() + "<ADDRESS>" + "xxx yyyyyyy ZZ" + "</ADDRESS>");
    println(indent() + "<CITY>" + "zzzzzzzzzzzz" + "</CITY>");
    println(indent() + "<STATE>" + "NN" + "</STATE>");
    println(indent() + "<POSTAL>" + "12345" + "</POSTAL>");
    println(indent() + "<PHONE_NUMBER>" + "111-222-3333" + "</PHONE_NUMBER>");
    println(indent() + "<MIN_TIME>" + rp.getMinTime() + "</MIN_TIME>");
    println(indent() + "<MAX_TIME>" + rp.getMaxTime() + "</MAX_TIME>");
    println(indent() + "<AVG_TIME>" + rp.getAvgTime() + "</AVG_TIME>");
    println(indent() + "<STD_DEV>" + rp.getStdDev() + "</STD_DEV>");
    println(indent() + "<PLACEMENT>" + placementText + "</PLACEMENT>");
    println(indent() + "<POINTS>" + rp.getPoints() + "</POINTS>");
    println(indent() + "<DATE_REGISTERED>" + dateField + "</DATE_REGISTERED>");
    println(indent() + "<VEHICLE_NUMBER>" + rp.getVehicleNumber() + "</VEHICLE_NUMBER>");
    // Car Performance to be as Attributes
    println(indent() + "<CAR_PERFORMANCE " + rp.getCarPerformance() + "/>");

    printRaceDataXML(rp.getRaceDataContainer());

    addIndent(-2);
    println(indent() + "</RACER_PERSON>");
    addIndent(-2);
  }
  /**
   * Method printRacerPersonXMLDen.  Prints the RacerPerson to the XML formatted file by the Den
   * @see XMLPublishResults#printRacerPersonXMLComplete(RacerPerson)
   * @see XMLPublishResults#printRacerPersonXMLPack(RacerPerson)
   * @see XMLPublishResults#printRacerPersonXMLPlacement(RacerPerson)
   * @param rp - RacerPerson, the Data to be printed
   */
  private void printRacerPersonXMLDen( RacerPerson rp )
  {
    if (rp == null)
    {
      return;
    }
    String placementText = getOrdinalPlacementText(rp.getPlacement());
    String descriptionText = rp.getLastName() + ", " + rp.getFirstName() + ", " + placementText;

    println(indent() + "<DEN_RACER>");
    addIndent(2);
    println(indent() + "<DESCRIPTION>" + descriptionText + "</DESCRIPTION>");
    println(indent() + "<PACK>" + rp.getPack() + "</PACK>");
    println(indent() + "<DEN>" + rp.getDen() + "</DEN>");
    println(indent() + "<LAST_NAME>" + rp.getLastName() + "</LAST_NAME>");
    println(indent() + "<FIRST_NAME>" + rp.getFirstName() + "</FIRST_NAME>");
    println(indent() + "<VEHICLE_NUMBER>" + rp.getVehicleNumber() + "</VEHICLE_NUMBER>");
    println(indent() + "<MIN_TIME>" + rp.getMinTime() + "</MIN_TIME>");
    println(indent() + "<MAX_TIME>" + rp.getMaxTime() + "</MAX_TIME>");
    println(indent() + "<AVG_TIME>" + rp.getAvgTime() + "</AVG_TIME>");
    println(indent() + "<STD_DEV>" + rp.getStdDev() + "</STD_DEV>");
    println(indent() + "<POINTS>" + rp.getPoints() + "</POINTS>");
    println(indent() + "<PLACEMENT>" + placementText + "</PLACEMENT>");
    addIndent(-2);
    println(indent() + "</DEN_RACER>");
  }
  /**
   * Method printRacerPersonXMLPack.  Prints the RacerPerson to the XML formatted file by the Pack.
   * @see XMLPublishResults#printRacerPersonXMLDen(RacerPerson)
   * @see XMLPublishResults#printRacerPersonXMLComplete(RacerPerson)
   * @see XMLPublishResults#printRacerPersonXMLPlacement(RacerPerson)
   * @param rp - RacerPerson, the Data to be printed
   */
  private void printRacerPersonXMLPack( RacerPerson rp )
  {
    if (rp == null)
    {
      return;
    }
    String placementText = getOrdinalPlacementText(rp.getPlacement());
    String descriptionText = rp.getDen() + ", " + rp.getLastName() + ", " + rp.getFirstName();

    println(indent() + "<PACK_RACER>");
    addIndent(2);
    println(indent() + "<DESCRIPTION>" + descriptionText + "</DESCRIPTION>");
    println(indent() + "<PACK>" + rp.getPack() + "</PACK>");
    println(indent() + "<DEN>" + rp.getDen() + "</DEN>");
    println(indent() + "<LAST_NAME>" + rp.getLastName() + "</LAST_NAME>");
    println(indent() + "<FIRST_NAME>" + rp.getFirstName() + "</FIRST_NAME>");
    println(indent() + "<VEHICLE_NUMBER>" + rp.getVehicleNumber() + "</VEHICLE_NUMBER>");
    println(indent() + "<MIN_TIME>" + rp.getMinTime() + "</MIN_TIME>");
    println(indent() + "<MAX_TIME>" + rp.getMaxTime() + "</MAX_TIME>");
    println(indent() + "<AVG_TIME>" + rp.getAvgTime() + "</AVG_TIME>");
    println(indent() + "<STD_DEV>" + rp.getStdDev() + "</STD_DEV>");
    println(indent() + "<POINTS>" + rp.getPoints() + "</POINTS>");
    println(indent() + "<PLACEMENT>" + placementText + "</PLACEMENT>");
    addIndent(-2);
    println(indent() + "</PACK_RACER>");
  }
  /**
   * Method printRacerPersonXMLPlacement.  Prints the RacerPerson to the XML formatted file by the Placement.
   * @see XMLPublishResults#printRacerPersonXMLDen(RacerPerson)
   * @see XMLPublishResults#printRacerPersonXMLComplete(RacerPerson)
   * @see XMLPublishResults#printRacerPersonXMLPack(RacerPerson)
   * @param rp - RacerPerson, the Data to be printed
   */
  private void printRacerPersonXMLPlacement( RacerPerson rp )
  {
    if (rp == null)
    {
      return;
    }
    String nameText = rp.getLastName() + ", " + rp.getFirstName();
    String placementText = getOrdinalPlacementText(rp.getPlacement());
    String descriptionText = placementText + ", " + nameText;
    addIndent(2);

    println(indent() + "<RACER_POSITION>");
    addIndent(2);
    println(indent() + "<DESCRIPTION>" + descriptionText + "</DESCRIPTION>");
    println(indent() + "<PACK>" + rp.getPack() + "</PACK>");
    println(indent() + "<DEN>" + rp.getDen() + "</DEN>");
    println(indent() + "<LAST_NAME>" + rp.getLastName() + "</LAST_NAME>");
    println(indent() + "<FIRST_NAME>" + rp.getFirstName() + "</FIRST_NAME>");
    println(indent() + "<VEHICLE_NUMBER>" + rp.getVehicleNumber() + "</VEHICLE_NUMBER>");
    println(indent() + "<MIN_TIME>" + rp.getMinTime() + "</MIN_TIME>");
    println(indent() + "<MAX_TIME>" + rp.getMaxTime() + "</MAX_TIME>");
    println(indent() + "<AVG_TIME>" + rp.getAvgTime() + "</AVG_TIME>");
    println(indent() + "<STD_DEV>" + rp.getStdDev() + "</STD_DEV>");
    println(indent() + "<POINTS>" + rp.getPoints() + "</POINTS>");
    println(indent() + "<PLACEMENT>" + placementText + "</PLACEMENT>");

    addIndent(-2);
    println(indent() + "</RACER_POSITION>");
    addIndent(-2);

  }
  /**
   * Method printResultsByRacerDen.  Prints the Result of the RacerPerson Container to a XML formatted file.
   * @see XMLPublishResults#printResultsByRacerName(RacerContainer)
   * @see XMLPublishResults#printResultsByRacerPack(RacerContainer)
   * @see XMLPublishResults#printResultsByRacerPlacement(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be printed
   */
  private void printResultsByRacerDen( RacerContainer racerContainer )
  {
    if (racerContainer == null)
    {
      return;
    }
    sortByDen(racerContainer);
    String den = racerContainer.elementAt(0).getDen();
    addIndent(2);
    println(indent() + "<ORDER_BY_DEN>");
    addIndent(2);
    println(indent() + "<DEN_NAME>");
    addIndent(2);
    println(indent() + "<DESCRIPTION>" + den + "</DESCRIPTION>");
    for (int i = 0; i < racerContainer.getSize(); i++)
    {
      String tempDen = racerContainer.elementAt(i).getDen();
      if (!den.equals(tempDen))
      {
        addIndent(-2);
        println(indent() + "</DEN_NAME>");
        den = tempDen;
        println(indent() + "<DEN_NAME>");
        addIndent(2);
        println(indent() + "<DESCRIPTION>" + den + "</DESCRIPTION>");
      }
      printRacerPersonXMLDen(racerContainer.elementAt(i));
    }
    addIndent(-2);
    println(indent() + "</DEN_NAME>");
    addIndent(-2);
    println(indent() + "</ORDER_BY_DEN>");
    addIndent(-2);
  }
  /**
   * Method printResultsByRacerName.  Prints the Result of the RacerPerson Container to a XML formatted file.
   * @see XMLPublishResults#printResultsByRacerDen(RacerContainer)
   * @see XMLPublishResults#printResultsByRacerPack(RacerContainer)
   * @see XMLPublishResults#printResultsByRacerPlacement(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be printed
   */
  private void printResultsByRacerName( RacerContainer racerContainer )
  {
    if (racerContainer == null)
    {
      return;
    }
    // Sort the List By LastName, Then First Name
    sortByName(racerContainer);
    addIndent(2);
    println(indent() + "<ORDER_BY_NAME>");
    addIndent(2);
    println(indent() + "<RACER_LIST_COUNT>" + racerContainer.getSize() + "</RACER_LIST_COUNT>");
    addIndent(-2);
    for (int i = 0; i < racerContainer.getSize(); i++)
    {
      printRacerPersonXMLComplete(racerContainer.elementAt(i));
    }
    println(indent() + "</ORDER_BY_NAME>");
    addIndent(-2);
  }
  /**
   * Method printResultsByRacerPack.  Prints the Result of the RacerPerson Container to a XML formatted file.
   * @see XMLPublishResults#printResultsByRacerDen(RacerContainer)
   * @see XMLPublishResults#printResultsByRacerName(RacerContainer)
   * @see XMLPublishResults#printResultsByRacerPlacement(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be printed
   */
  private void printResultsByRacerPack( RacerContainer racerContainer )
  {
    if (racerContainer == null)
    {
      return;
    }
    sortByPack(racerContainer);
    String pack = racerContainer.elementAt(0).getPack();
    addIndent(2);
    println(indent() + "<ORDER_BY_PACK>");
    addIndent(2);
    println(indent() + "<PACK_NAME>");
    addIndent(2);
    println(indent() + "<DESCRIPTION>" + pack + "</DESCRIPTION>");
    for (int i = 0; i < racerContainer.getSize(); i++)
    {
      String tempDen = racerContainer.elementAt(i).getPack();
      if (!pack.equals(tempDen))
      {
        addIndent(-2);
        println(indent() + "</PACK_NAME>");
        pack = tempDen;
        println(indent() + "<PACK_NAME>");
        addIndent(2);
        println(indent() + "<DESCRIPTION>" + pack + "</DESCRIPTION>");
      }
      printRacerPersonXMLPack(racerContainer.elementAt(i));
    }
    addIndent(-2);
    println(indent() + "</PACK_NAME>");
    addIndent(-2);
    println(indent() + "</ORDER_BY_PACK>");
    addIndent(-2);
  }
  /**
   * Method printResultsByRacerPlacement.  Prints the Result of the RacerPerson Container to a XML formatted file.
   * @see XMLPublishResults#printResultsByRacerDen(RacerContainer)
   * @see XMLPublishResults#printResultsByRacerName(RacerContainer)
   * @see XMLPublishResults#printResultsByRacerPack(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be printed
   */
  private void printResultsByRacerPlacement( RacerContainer racerContainer )
  {
    if (racerContainer == null)
    {
      return;
    }
    sortByPlacement(racerContainer);
    addIndent(2);
    println(indent() + "<ORDER_BY_PLACEMENT>");
    for (int i = 0; i < racerContainer.getSize(); i++)
    {
      printRacerPersonXMLPlacement(racerContainer.elementAt(i));
    }
    println(indent() + "</ORDER_BY_PLACEMENT>");
    addIndent(-2);
  }
  /**
   * Method sortByDen.  Sorts the Container by the Den 
   * @see XMLPublishResults#sortByName(RacerContainer)
   * @see XMLPublishResults#sortByPack(RacerContainer)
   * @see XMLPublishResults#sortByPlacement(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be sorted
   */
  private void sortByDen( RacerContainer racerContainer )
  {
    XMLPublishComparator comparator = new XMLPublishComparator(WebPublishRacerSortField.DEN, true);
    Collections.sort(racerContainer.getRacers(), comparator);
  }
  /**
   * Method sortByName.  Sorts the Container by the Last Name follwed by First Name
   * @see XMLPublishResults#sortByDen(RacerContainer)
   * @see XMLPublishResults#sortByPack(RacerContainer)
   * @see XMLPublishResults#sortByPlacement(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be sorted
   */
  private void sortByName( RacerContainer racerContainer )
  {
    XMLPublishComparator comparator = new XMLPublishComparator(WebPublishRacerSortField.LNAME_FNAME, true);
    Collections.sort(racerContainer.getRacers(), comparator);
  }
  /**
   * Method sortByPack.  Sorts the Container by the PACK
   * @see XMLPublishResults#sortByDen(RacerContainer)
   * @see XMLPublishResults#sortByName(RacerContainer)
   * @see XMLPublishResults#sortByPlacement(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be sorted
   */
  private void sortByPack( RacerContainer racerContainer )
  {
    XMLPublishComparator comparator = new XMLPublishComparator(WebPublishRacerSortField.PACK, true);
    Collections.sort(racerContainer.getRacers(), comparator);
  }
  /**
   * Method sortByPlacement.  Sorts the Container by the PLACEMENT
   * @see XMLPublishResults#sortByDen(RacerContainer)
   * @see XMLPublishResults#sortByName(RacerContainer)
   * @see XMLPublishResults#sortByPack(RacerContainer)
   * @param racerContainer - RacerContainer, the Data to be sorted
   */
  private void sortByPlacement( RacerContainer racerContainer )
  {
    XMLPublishComparator comparator = new XMLPublishComparator(WebPublishRacerSortField.PLACEMENT, true);
    Collections.sort(racerContainer.getRacers(), comparator);
    // Now we will ensure the Placment indicators are set.
    int containerSize = racerContainer.getSize();
    for (int i = 0; i < containerSize; i++)
    {
      RacerPerson rp = racerContainer.elementAt(i);
      rp.setPlacement(i + 1); // Make the Placement positive based
    }
  }
}
