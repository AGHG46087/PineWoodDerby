<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- Iterate through the XML -->
  <xsl:template match="/">
    <xsl:element name="tree">
      <xsl:element name="entity">
        <xsl:element name="description">Race Results - '<xsl:value-of select="/RACE_RESULTS/RACE_DATE"/>'</xsl:element>
        <xsl:element name="definition">Web Page presents a web page display of the competitors and their associated statistics.</xsl:element>
        <xsl:element name="imageBase">images/globe.gif</xsl:element>
        <xsl:element name="imageOpen">images/globe_selected.gif</xsl:element>
        <xsl:apply-templates select="/RACE_RESULTS/TRACK_DETAILS"/>
        <xsl:element name="contents">
          <xsl:apply-templates select="/RACE_RESULTS/ORDER_BY_PLACEMENT"/>
          <xsl:apply-templates select="/RACE_RESULTS/ORDER_BY_PACK"/>
          <xsl:apply-templates select="/RACE_RESULTS/ORDER_BY_DEN"/>
          <xsl:apply-templates select="/RACE_RESULTS/ORDER_BY_NAME"/>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="TRACK_DETAILS">
    <xsl:element name="Track-Notes">The Fields values viewed are known and calculated.  If any values are less than or equal to zero,
    then some details were not properly set during the race setup. We use the Newtonion formula for the relationship between distance,
    velocity, and acceleration: Xfinal = Xinitial + XDOTinitial * time + 0.5 XDBLDOT * time^2. From these Values, the individual Races
    and the Average Race Times are used to calculate the cars performance.</xsl:element>
    <xsl:element name="Gravity-Acceleration">32.174 - Earth's surface gravitational acceleration - Physical Properties</xsl:element>
    <xsl:element name="Track-Length-Feet"><xsl:value-of select="TRACK_LENGTH_FEET"/> - Physical Properties</xsl:element>
    <xsl:element name="Track-Length-Inches"><xsl:value-of select="TRACK_LENGTH_INCHES"/> - Physical Properties</xsl:element>
    <xsl:element name="Ideal-Total-Time"><xsl:value-of select="IDEAL_TOTAL_TIME"/> seconds as calculated based on the known Physical Properties</xsl:element>
    <xsl:element name="Ideal-Finish-Speed"><xsl:value-of select="IDEAL_FINISH_SPEED"/> mph as calculated based on the known Physical Properties</xsl:element>
    <xsl:element name="Ideal-Average-Speed"><xsl:value-of select="IDEAL_AVERAGE_SPEED"/> mph as calculated based on the known Physical Properties</xsl:element>
  </xsl:template>
  <xsl:template match="ORDER_BY_PLACEMENT">
    <xsl:element name="entity">
      <xsl:element name="description">Order By Placement</xsl:element>
      <xsl:element name="definition">The Order of display under this category, shows the order of the competitors in their associated finishing order.</xsl:element>
      <xsl:element name="imageBase">images/book.gif</xsl:element>
      <xsl:element name="imageOpen">images/bookOpen.gif</xsl:element>
      <xsl:element name="contents">
        <xsl:apply-templates select="RACER_POSITION"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="RACER_POSITION">
    <xsl:element name="entity">
      <xsl:element name="description"><xsl:value-of select="DESCRIPTION"/></xsl:element>
      <xsl:element name="imageBase">images/paper.gif</xsl:element>
      <xsl:element name="imageOpen">images/paper.gif</xsl:element>
      <xsl:element name="definition">Vehicle '<xsl:value-of select="VEHICLE_NUMBER"/>' finished in '<xsl:value-of select="PLACEMENT"/>'.</xsl:element>
      <xsl:element name="Competitor-Pack"><xsl:value-of select="PACK"/></xsl:element>
      <xsl:element name="Competitor-Den"><xsl:value-of select="DEN"/></xsl:element>
      <xsl:element name="Last-Name"><xsl:value-of select="LAST_NAME"/></xsl:element>
      <xsl:element name="First-Name"><xsl:value-of select="FIRST_NAME"/></xsl:element>
      <xsl:element name="Vehicle-Name"><xsl:value-of select="VEHICLE_NUMBER"/></xsl:element>
      <xsl:element name="Points"><xsl:value-of select="POINTS"/></xsl:element>
      <xsl:element name="Placement"><xsl:value-of select="PLACEMENT"/></xsl:element>
      <xsl:element name="Fastest-Time"><xsl:value-of select="MIN_TIME"/></xsl:element>
      <xsl:element name="Slowest-Time"><xsl:value-of select="MAX_TIME"/></xsl:element>
      <xsl:element name="Average-Time"><xsl:value-of select="AVG_TIME"/></xsl:element>
      <xsl:element name="Standard-Deviation"><xsl:value-of select="STD_DEV"/> deviation from the Average Time of this vehicles heats during competition.</xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="ORDER_BY_PACK">
    <xsl:element name="entity">
      <xsl:element name="description">Order By Pack</xsl:element>
      <xsl:element name="definition">The Order of display under this category, shows the order of the competitors in their associated pack.</xsl:element>
      <xsl:element name="imageBase">images/book.gif</xsl:element>
      <xsl:element name="imageOpen">images/bookOpen.gif</xsl:element>
      <xsl:element name="contents">
        <xsl:apply-templates select="PACK_NAME"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="PACK_NAME">
    <xsl:element name="entity">
      <xsl:element name="description"><xsl:value-of select="DESCRIPTION"/></xsl:element>
      <xsl:element name="definition">Competitors of Pack '<xsl:value-of select="DESCRIPTION"/>' are listed by DEN and Name.</xsl:element>
      <xsl:element name="imageBase">images/book.gif</xsl:element>
      <xsl:element name="imageOpen">images/bookOpen.gif</xsl:element>
      <xsl:element name="contents">
        <xsl:apply-templates select="PACK_RACER"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="PACK_RACER">
    <xsl:element name="entity">
      <xsl:element name="description"><xsl:value-of select="DESCRIPTION"/></xsl:element>
      <xsl:element name="imageBase">images/paper.gif</xsl:element>
      <xsl:element name="imageOpen">images/paper.gif</xsl:element>
      <xsl:element name="definition"><xsl:value-of select="FIRST_NAME"/>&nbsp;<xsl:value-of select="LAST_NAME"/> of Pack <xsl:value-of select="PACK"/> had finished in <xsl:value-of select="PLACEMENT"/>.</xsl:element>
      <xsl:element name="Competitor-Pack"><xsl:value-of select="PACK"/></xsl:element>
      <xsl:element name="Competitor-Den"><xsl:value-of select="DEN"/></xsl:element>
      <xsl:element name="Last-Name"><xsl:value-of select="LAST_NAME"/></xsl:element>
      <xsl:element name="First-Name"><xsl:value-of select="FIRST_NAME"/></xsl:element>
      <xsl:element name="Points"><xsl:value-of select="POINTS"/></xsl:element>
      <xsl:element name="Placement"><xsl:value-of select="PLACEMENT"/></xsl:element>
      <xsl:element name="Vehicle-Name"><xsl:value-of select="VEHICLE_NUMBER"/></xsl:element>
      <xsl:element name="Fastest-Time"><xsl:value-of select="MIN_TIME"/></xsl:element>
      <xsl:element name="Slowest-Time"><xsl:value-of select="MAX_TIME"/></xsl:element>
      <xsl:element name="Average-Time"><xsl:value-of select="AVG_TIME"/></xsl:element>
      <xsl:element name="Standard-Deviation"><xsl:value-of select="STD_DEV"/> deviation from the Average Time of this vehicles heats during competition.</xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="ORDER_BY_DEN">
    <xsl:element name="entity">
      <xsl:element name="description">Order By Den</xsl:element>
      <xsl:element name="definition">The Order of display under this category, shows the order of the competitors in their associated Den.</xsl:element>
      <xsl:element name="imageBase">images/book.gif</xsl:element>
      <xsl:element name="imageOpen">images/bookOpen.gif</xsl:element>
      <xsl:element name="contents">
        <xsl:apply-templates select="DEN_NAME"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="DEN_NAME">
    <xsl:element name="entity">
      <xsl:element name="description"><xsl:value-of select="DESCRIPTION"/></xsl:element>
      <xsl:element name="definition">Competitors of Den '<xsl:value-of select="DESCRIPTION"/>' are listed by Name and Placement.</xsl:element>
      <xsl:element name="imageBase">images/book.gif</xsl:element>
      <xsl:element name="imageOpen">images/bookOpen.gif</xsl:element>
      <xsl:element name="contents">
        <xsl:apply-templates select="DEN_RACER"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="DEN_RACER">
    <xsl:element name="entity">
      <xsl:element name="description"><xsl:value-of select="DESCRIPTION"/></xsl:element>
      <xsl:element name="imageBase">images/paper.gif</xsl:element>
      <xsl:element name="imageOpen">images/paper.gif</xsl:element>
      <xsl:element name="definition"><xsl:value-of select="FIRST_NAME"/>&nbsp;<xsl:value-of select="LAST_NAME"/> of Den <xsl:value-of select="DEN"/> had finished in <xsl:value-of select="PLACEMENT"/>.</xsl:element>
      <xsl:element name="Competitor-Pack"><xsl:value-of select="PACK"/></xsl:element>
      <xsl:element name="Competitor-Den"><xsl:value-of select="DEN"/></xsl:element>
      <xsl:element name="Last-Name"><xsl:value-of select="LAST_NAME"/></xsl:element>
      <xsl:element name="First-Name"><xsl:value-of select="FIRST_NAME"/></xsl:element>
      <xsl:element name="Points"><xsl:value-of select="POINTS"/></xsl:element>
      <xsl:element name="Placement"><xsl:value-of select="PLACEMENT"/></xsl:element>
      <xsl:element name="Vehicle-Name"><xsl:value-of select="VEHICLE_NUMBER"/></xsl:element>
      <xsl:element name="Fastest-Time"><xsl:value-of select="MIN_TIME"/></xsl:element>
      <xsl:element name="Slowest-Time"><xsl:value-of select="MAX_TIME"/></xsl:element>
      <xsl:element name="Average-Time"><xsl:value-of select="AVG_TIME"/></xsl:element>
      <xsl:element name="Standard-Deviation"><xsl:value-of select="STD_DEV"/> deviation from the Average Time of this vehicles heats during competition.</xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="ORDER_BY_NAME">
    <xsl:element name="entity">
      <xsl:element name="description">Order By Name</xsl:element>
      <xsl:element name="definition">The Order of display under this category, shows the order of the competitors in their associated Names.</xsl:element>
      <xsl:element name="imageBase">images/book.gif</xsl:element>
      <xsl:element name="imageOpen">images/bookOpen.gif</xsl:element>
      <xsl:element name="contents">
        <xsl:apply-templates select="RACER_PERSON"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="RACER_PERSON">
    <xsl:element name="entity">
      <xsl:element name="description"><xsl:value-of select="DESCRIPTION"/></xsl:element>
      <xsl:element name="imageBase">images/book.gif</xsl:element>
      <xsl:element name="imageOpen">images/bookOpen.gif</xsl:element>
      <xsl:element name="definition"><xsl:value-of select="FIRST_NAME"/>&nbsp;<xsl:value-of select="LAST_NAME"/> of Den <xsl:value-of select="DEN"/> had finished in <xsl:value-of select="PLACEMENT"/>.</xsl:element>
      <xsl:element name="Database-ID"><xsl:value-of select="DB_ID"/></xsl:element>
      <xsl:element name="Competitor-District"><xsl:value-of select="DISTRICT"/></xsl:element>
      <xsl:element name="Competitor-Pack"><xsl:value-of select="PACK"/></xsl:element>
      <xsl:element name="Competitor-Den"><xsl:value-of select="DEN"/></xsl:element>
      <xsl:element name="Last-Name"><xsl:value-of select="LAST_NAME"/></xsl:element>
      <xsl:element name="First-Name"><xsl:value-of select="FIRST_NAME"/></xsl:element>
<!--
      <xsl:element name="Address"><xsl:value-of select="ADDRESS"/></xsl:element>
      <xsl:element name="City"><xsl:value-of select="CITY"/></xsl:element>
      <xsl:element name="State"><xsl:value-of select="STATE"/></xsl:element>
      <xsl:element name="Postal"><xsl:value-of select="POSTAL"/></xsl:element>
      <xsl:element name="Phone-Number"><xsl:value-of select="PHONE_NUMBER"/></xsl:element>
-->      
      <xsl:element name="Date-Registered"><xsl:value-of select="DATE_REGISTERED"/></xsl:element>
      <xsl:element name="Placement"><xsl:value-of select="PLACEMENT"/></xsl:element>
      <xsl:element name="Points"><xsl:value-of select="POINTS"/></xsl:element>
      <xsl:element name="Vehicle-Name"><xsl:value-of select="VEHICLE_NUMBER"/></xsl:element>
      <xsl:element name="Fastest-Time"><xsl:value-of select="MIN_TIME"/></xsl:element>
      <xsl:element name="Slowest-Time"><xsl:value-of select="MAX_TIME"/></xsl:element>
      <xsl:element name="Average-Time"><xsl:value-of select="AVG_TIME"/></xsl:element>
      <xsl:element name="Standard-Deviation"><xsl:value-of select="STD_DEV"/> deviation from the Average Time of this vehicles heats during competition.</xsl:element>
      <xsl:element name="contents">
        <xsl:apply-templates select="CAR_PERFORMANCE"/>
        <xsl:apply-templates select="RACE_DETAILS"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="RACE_DETAILS">
    <xsl:element name="entity">
    <xsl:element name="description">Race Details (<xsl:value-of select="RACE_DETAILS_COUNT"/>)</xsl:element>
    <xsl:element name="imageBase">images/book.gif</xsl:element>
    <xsl:element name="imageOpen">images/bookOpen.gif</xsl:element>
    <xsl:element name="definition">Contains the list of <xsl:value-of select="RACE_DETAILS_COUNT"/> itemized race details ran by this competitor.</xsl:element>
    <xsl:element name="contents">
      <xsl:apply-templates select="RACE_DATA"/>
    </xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="RACE_DATA">
    <xsl:element name="entity">
      <xsl:element name="description"><xsl:value-of select="DESCRIPTION"/></xsl:element>
      <xsl:element name="imageBase">images/book.gif</xsl:element>
      <xsl:element name="imageOpen">images/bookOpen.gif</xsl:element>
      <xsl:element name="definition">The vehicle <xsl:value-of select="VEHICLE_NUMBER"/> arrived in <xsl:value-of select="PLACEMENT"/> of the round '<xsl:value-of select="ROUND"/>' and Heat '<xsl:value-of select="HEAT"/>'.</xsl:element>
      <xsl:element name="Database-EntryID"><xsl:value-of select="DB_ENTRY"/> (Database-ID of the this heat).</xsl:element>
      <xsl:element name="Database-ID"><xsl:value-of select="ID"/> (Database-ID of the this competitor).</xsl:element>
      <xsl:element name="Round"><xsl:value-of select="ROUND"/></xsl:element>
      <xsl:element name="Heat"><xsl:value-of select="HEAT"/></xsl:element>
      <xsl:element name="Lane"><xsl:value-of select="LANE"/></xsl:element>
      <xsl:element name="Time"><xsl:value-of select="TIME"/></xsl:element>
      <xsl:element name="Points"><xsl:value-of select="POINTS"/></xsl:element>
      <xsl:element name="Placement"><xsl:value-of select="PLACEMENT"/> (for this Heat).</xsl:element>
      <xsl:element name="Points"><xsl:value-of select="POINTS"/></xsl:element>
      <xsl:element name="Race-Date"><xsl:value-of select="DATE"/></xsl:element>
      <xsl:element name="Vehicle-Name"><xsl:value-of select="VEHICLE_NUMBER"/></xsl:element>
      <xsl:element name="contents">
        <xsl:apply-templates select="CAR_PERFORMANCE"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  <xsl:template match="CAR_PERFORMANCE">
    <xsl:element name="entity">
      <xsl:element name="description">Car Performance</xsl:element>
      <xsl:element name="imageBase">images/paper.gif</xsl:element>
      <xsl:element name="imageOpen">images/paper.gif</xsl:element>
      <xsl:element name="definition">Vehicle Car Performance for this Heat/Race.  Note: the Vehicle Completed Race Time is base Time used for the calculations and the Delta Times are the difference from a "Perfect Vehicle"</xsl:element>
      <xsl:element name="Completed-Race-Time"><xsl:value-of select="@vehicleCompletedRaceTime"/></xsl:element>
      <xsl:element name="Real-Average-Speed"><xsl:value-of select="@realAverageSpeed"/> mph</xsl:element>
      <xsl:element name="Real-Efficiency"><xsl:value-of select="@realEfficiency"/>%</xsl:element>
      <xsl:element name="Delta-Efficiency"><xsl:value-of select="@deltaEfficiency"/>%</xsl:element>
      <xsl:element name="Delta-Time"><xsl:value-of select="@deltaTime"/></xsl:element>
      <xsl:element name="Delta-Average-Speed"><xsl:value-of select="@deltaAverageSpeed"/> mph</xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>