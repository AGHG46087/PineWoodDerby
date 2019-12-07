<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- Iterate through the XML -->
  <xsl:template match="/">
  <html>
  <head><title>Race Results for <xsl:value-of select="/RACE_RESULTS/RACER_PERSON/DESCRIPTION"/></title>
  <STYLE>H1: {COLOR: blue FONT-FAMILY: Arial; } BODY {COLOR: blue; FONT-FAMILY: Arial; FONT-SIZE: 8pt;} TR.clsOdd { background-Color: beige; } TR.clsEven { background-color: #cccccc; } TR.clsData { color: red; text-align: center} TD.clsInfo { color: white; }</STYLE>
  </head>
    <body text="white" bgColor="black">
      <CENTER><H1><xsl:value-of select="/RACE_RESULTS/RACER_PERSON/DESCRIPTION"/><BR></BR>Race Results</H1></CENTER>
      <CENTER><h3>Date: <xsl:value-of select="/RACE_RESULTS/RACE_DATE"/></h3></CENTER>
      <CENTER>
        <table border="1" width="75%">
          <xsl:apply-templates select="/RACE_RESULTS/RACER_PERSON"/>
          <xsl:apply-templates select="/RACE_RESULTS/TRACK_DETAILS"/>
        </table>
        <br></br><br></br>
        <table width="75%">
          <tr>
            <th>Definitions</th>
          </tr>
          <tr>
            <td class="clsInfo">* <A href="misc/CarScience.doc"><b>Car Performance</b></A> - According to the laws of physics, the minimum time to race down a track is affected ONLY by the shape of the track.  Simply speaking, even perfect cars race faster down steeper tracks.  Oddly enough, things such as: weight, size, wheelbase, shape, balance, and so on do NOT affect the best possible race time.  The Car Performance on a single race is measured based on the time the race was completed for that heat.  The Overall Car Performance is based on the cars Average Time.<BR></BR><BR></BR>
            </td>
          </tr>
          <tr>
            <td class="clsInfo">* <b><FONT color="blue">Real Efficiency</FONT></b> - Comparing your Car's time to that of the Perfect Time will always be between 0 and 100%.  Just like grades in school, the higher the number the better. The Perfect Car's efficiency is always 100%.<br></br><br></br>
            </td>
          </tr>
          <tr>
            <td class="clsInfo">* <b><FONT color="blue">Delta Efficiency</FONT></b> - The Difference in "Real Efficiency" of Your car from that of the Perfect Car's Efficiency. Will always be between 0 and 100%.  Unlike "Real Effeciency", The Lower the number the better the Delta.<br></br><br></br>
            </td>
          </tr>
          <tr>
            <td class="clsInfo">* <b><FONT color="blue">Delta Time</FONT></b> - The Difference in time of Your car from that of the Perfect Car's Time.<br></br><br></br>
            </td>
          </tr>
          <tr>
            <td class="clsInfo">* <b><FONT color="blue">Delta Average Speed</FONT></b> - The Difference in Average Speed of Your car from that of the Perfect Car's Average Speed.<br></br><br></br>
            </td>
          </tr>
          <tr>
            <td class="clsInfo">* <b><FONT color="blue">Average Time</FONT></b> - The Sum of all your Times divided by the Number of Races your had competed.<br></br><br></br>
            </td>
          </tr>
          <tr>
            <td class="clsInfo">* <b><FONT color="blue">Standard Deviation</FONT></b> - of the Population Time to complete all the heats.  Standard deviation tells how tightly a set of values is clustered around the average of those same values. It's a measure of dispersal, or variation, in a group of numbers.<BR></BR>Higher standard deviation is often interpreted as higher volatility. In comparison, lower standard deviation would likely be an indicator of stability. The most consistent values will usually be the set of numbers with the lowest standard deviation.<br></br><br></br>
            </td>
          </tr>
        </table>
      </CENTER>
    </body>
  </html>
  </xsl:template>
  <xsl:template match="TRACK_DETAILS">
    <tr>
      <td colspan="2">
        <table  border="1" width="100%">
          <tr class="clsOdd">
            <th colspan="5">TRACK DETAILS</th>
          </tr>
          <tr>
            <th>Track Length<br></br>Feet</th>
            <th>Track Length<br></br>Inches</th>
            <th>Perfect Time</th>
            <th>Perfect Finish<br></br>Speed(mph)</th>
            <th>Perfect Average<br></br>Speed(mph)</th>
          </tr>
          <tr class="clsData">
            <td><xsl:value-of select="TRACK_LENGTH_FEET"/></td>
            <td><xsl:value-of select="TRACK_LENGTH_INCHES"/></td>
            <td><xsl:value-of select="IDEAL_TOTAL_TIME"/></td>
            <td><xsl:value-of select="IDEAL_FINISH_SPEED"/></td>
            <td><xsl:value-of select="IDEAL_AVERAGE_SPEED"/></td>
          </tr>
        </table>
      </td>
    </tr>
  </xsl:template>
  <xsl:template match="RACER_PERSON">
     <tr class="clsOdd">
       <th align="center">Racer Info</th>
       <th align="center">Race Statistics</th>
     </tr>
     <tr> 
       <td valign="top"> <!-- Racer Information -->
        <table border="0" width="100%">
          <tr valign="top">
            <td>Last Name</td>
            <td class="clsInfo" ><xsl:value-of select="LAST_NAME"/></td>
          </tr>
          <tr valign="top">
            <td>First Name</td>
            <td class="clsInfo" ><xsl:value-of select="FIRST_NAME"/></td>
          </tr>
<!-- Childs address, city, state, zip, phone is being removed for protection -->
<!--
          <tr valign="top">
            <td>Address</td>
            <td class="clsInfo" ><xsl:value-of select="ADDRESS"/></td>
          </tr>
          <tr valign="top">
            <td>City</td>
            <td class="clsInfo" ><xsl:value-of select="CITY"/></td>
          </tr>
          <tr valign="top">
            <td>State</td>
            <td class="clsInfo" ><xsl:value-of select="STATE"/></td>
          </tr>
          <tr valign="top">
            <td>Postal</td>
            <td class="clsInfo" ><xsl:value-of select="POSTAL"/></td>
          </tr>
          <tr valign="top">
            <td>Phone</td>
            <td class="clsInfo" ><xsl:value-of select="PHONE_NUMBER"/></td>
          </tr>
-->
          <tr valign="top">
            <td>District</td>
            <td class="clsInfo" ><xsl:value-of select="DISTRICT"/></td>
          </tr>
          <tr valign="top">
            <td>Pack</td>
            <td class="clsInfo" ><xsl:value-of select="PACK"/></td>
          </tr>
          <tr valign="top">
            <td>Den</td>
            <td class="clsInfo" ><xsl:value-of select="DEN"/></td>
          </tr>
          <tr valign="top">
            <td>DB-ID</td>
            <td class="clsInfo" ><xsl:value-of select="DB_ID"/></td>
          </tr>
        </table>
       </td>
       <td valign="top"> <!-- Racer Summary -->
        <table border="0" width="100%">
          <tr>
            <td>Vehicle Name</td>
            <td class="clsInfo"><xsl:value-of select="VEHICLE_NUMBER"/></td>
          </tr>
          <tr>
            <td>Fastest Time</td>
            <td class="clsInfo"><xsl:value-of select="MIN_TIME"/> seconds</td>
          </tr>
          <tr>
            <td>Slowest Time</td>
            <td class="clsInfo"><xsl:value-of select="MAX_TIME"/> seconds</td>
          </tr>
          <tr>
            <td>Average Time</td>
            <td class="clsInfo"><xsl:value-of select="AVG_TIME"/> seconds</td>
          </tr>
          <tr>
            <td>Standard Deviation</td>
            <td class="clsInfo"><xsl:value-of select="STD_DEV"/> from the Avg.</td>
          </tr>
          <tr>
            <td>Total Points</td>
            <td class="clsInfo"><xsl:value-of select="POINTS"/> of all Heats</td>
          </tr>
          <tr>
            <td vAlign="bottom">Placement Overall</td>
            <td class="clsInfo" vAlign="bottom"><xsl:apply-templates select="PLACEMENT" mode="OVERALL"/></td>
          </tr>
          <tr>
            <td colspan="2">
               <table border="1" width="100%">
                 <tr class="clsEven">
                   <th colspan="6">Overall Car Performance</th>
                 </tr>
                 <tr>
                   <th>Race Time</th>
                   <th>Real Avg<br></br>Speed mph</th>
                   <th>Real<BR></BR>Efficiency %</th>
                   <th>Delta<br></br>Efficiency %</th>
                   <th>Delta Time</th>
                   <th>Delta Avg<br></br> Speed mph</th>
                 </tr>
                 <tr class="clsData">
                   <xsl:apply-templates select="CAR_PERFORMANCE" mode="OVERALL"/>
                 </tr>
               </table>
            </td>
          </tr>
        </table>
       </td>
     </tr>
     <tr>
       <td colspan="2">
         <table border="1" width="100%">
           <tr class="clsOdd">
             <th colspan="7">RACE DETAILS</th>
           </tr>
           <tr>
             <th>Round</th>
             <th>Heat</th>
             <th>Lane</th>
             <th>Time</th>
             <th>Points</th>
             <th>Placement</th>
             <th>
               <table border="1" width="100%">
                 <tr class="clsEven">
                   <th colspan="6">Car Performance</th>
                 </tr>
                 <tr>
                   <th>Race Time</th>
                   <th>Real Avg Speed mph</th>
                   <th>Real Efficiency %</th>
                   <th>Delta Efficiency %</th>
                   <th>Delta Time</th>
                   <th>Delta Avg Speed mph</th>
                 </tr>
               </table>
             </th>
           </tr>
           <xsl:apply-templates select="RACE_DETAILS/RACE_DATA"/>
         </table>
       </td>
     </tr>
  </xsl:template>
  <xsl:template match="CAR_PERFORMANCE" mode="OVERALL">
    <td><xsl:value-of select="@vehicleCompletedRaceTime"/></td>
    <td><xsl:value-of select="@realAverageSpeed"/></td>
    <td><xsl:value-of select="@realEfficiency"/>%</td>
    <td><xsl:value-of select="@deltaEfficiency"/>%</td>
    <td><xsl:value-of select="@deltaTime"/></td>
    <td><xsl:value-of select="@deltaAverageSpeed"/></td>
  </xsl:template>
  <xsl:template match="RACE_DATA">
    <tr class="clsData">
      <td><xsl:value-of select="ROUND"/></td>
      <td><xsl:value-of select="HEAT"/></td>
      <td><xsl:value-of select="LANE"/></td>
      <td><xsl:value-of select="TIME"/></td>
      <td><xsl:value-of select="POINTS"/></td>
      <td valign="bottom"><xsl:apply-templates select="PLACEMENT" mode="RACE_DATA"/></td>
      <xsl:apply-templates select="CAR_PERFORMANCE" mode="RACE_DATA"/>
    </tr>
  </xsl:template>
  <xsl:template match="CAR_PERFORMANCE" mode="RACE_DATA">
    <td>
      <table width="100%">
        <tr class="clsData">
          <td><xsl:value-of select="@vehicleCompletedRaceTime"/></td>
          <td><xsl:value-of select="@realAverageSpeed"/></td>
          <td><xsl:value-of select="@realEfficiency"/>%</td>
          <td><xsl:value-of select="@deltaEfficiency"/>%</td>
          <td><xsl:value-of select="@deltaTime"/></td>
          <td><xsl:value-of select="@deltaAverageSpeed"/></td>
        </tr>
      </table>
    </td>
  </xsl:template>
  <xsl:template match="PLACEMENT" mode="RACE_DATA">
    <xsl:param name="place"><xsl:value-of select="text()"/></xsl:param>
      <xsl:choose>
        <xsl:when test="starts-with($place,'1st')">
          <IMG alt="" hspace="0" src="images/FirstPlace_Boom.gif" align="left"></IMG>
        </xsl:when>
        <xsl:when test="starts-with($place,'2nd')">
          <IMG alt="" hspace="0" src="images/SecondPlace_Boom.gif" align="left"></IMG>
        </xsl:when>
        <xsl:when test="starts-with($place,'3rd')">
          <IMG alt="" hspace="0" src="images/ThirdPlace_Boom.gif" align="left"></IMG>
        </xsl:when>
        <xsl:when test="starts-with($place,'4th')">
          <IMG alt="" hspace="0" src="images/FourthPlace_Boom.gif" align="left"></IMG>
        </xsl:when>
        <xsl:when test="starts-with($place,'5th')">
          <IMG alt="" hspace="0" src="images/FifthPlace_Boom.gif" align="left"></IMG>
        </xsl:when>
        <xsl:otherwise>
          <IMG alt="" hspace="0" src="images/OutOfRange.gif" align="left"></IMG>
        </xsl:otherwise>
      </xsl:choose>
    <xsl:value-of select="$place"/>
  </xsl:template>
  <xsl:template match="PLACEMENT" mode="OVERALL">
    <xsl:param name="place"><xsl:value-of select="text()"/></xsl:param>
      <xsl:choose>
        <xsl:when test="starts-with($place,'1st')">
          <IMG alt="" hspace="0" src="images/FirstPlace_Flag.gif" align="left"></IMG>
        </xsl:when>
        <xsl:when test="starts-with($place,'2nd')">
          <IMG alt="" hspace="0" src="images/SecondPlace_Flag.gif" align="left"></IMG>
        </xsl:when>
        <xsl:when test="starts-with($place,'3rd')">
          <IMG alt="" hspace="0" src="images/ThirdPlace_Flag.gif" align="left"></IMG>
        </xsl:when>
        <xsl:when test="starts-with($place,'4th')">
          <IMG alt="" hspace="0" src="images/FourthPlace_Flag.gif" align="left"></IMG>
        </xsl:when>
        <xsl:when test="starts-with($place,'5th')">
          <IMG alt="" hspace="0" src="images/FifthPlace_Flag.gif" align="left"></IMG>
        </xsl:when>
        <xsl:otherwise>
          <IMG alt="" hspace="0" src="images/OutOfRange.gif" align="left"></IMG>
        </xsl:otherwise>
      </xsl:choose>
    <xsl:value-of select="$place"/>
  </xsl:template>
</xsl:stylesheet>