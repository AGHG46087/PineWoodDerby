<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" />
  <!-- Iterate through the XML -->
  <xsl:template match="/">
  
  <html>
  <head><title>Race Results <xsl:value-of select="/RACE_RESULTS/RACE_DATE"/></title>
  <STYLE>H1: {COLOR: blue FONT-FAMILY: Arial; } BODY {COLOR: blue; FONT-FAMILY: Arial; FONT-SIZE: 8pt;} TR.clsOdd { background-Color: beige; } TR.clsEven { background-color: #cccccc; }</STYLE>
  </head>
    <body text="white" bgColor="black">
      <CENTER><H1>Race Results <xsl:value-of select="/RACE_RESULTS/RACE_DATE"/><BR></BR>Competitor List</H1></CENTER>
      <CENTER><h3><xsl:value-of select="/RACE_RESULTS/ORDER_BY_NAME/RACER_LIST_COUNT"/> Racers</h3></CENTER>
      <center><xsl:apply-templates select="/RACE_RESULTS/ORDER_BY_NAME"/></center>
    </body>
  </html>
  </xsl:template>
  <xsl:template match="ORDER_BY_NAME">
   <xsl:variable name="lname"><xsl:value-of select="RACER_PERSON/LAST_NAME"/></xsl:variable>
   <xsl:variable name="fname"><xsl:value-of select="RACER_PERSON/FIRST_NAME"/></xsl:variable>
   <xsl:variable name="URL"><xsl:value-of select="concat($lname,'_',$fname,'.html')"/></xsl:variable>
   <table border="1">
     <tr bgcolor="#FFAAAA">
       <th align="center">Last Name</th>
       <th align="center">First Name</th>
       <th align="center">Placement</th>
       <th align="center">Points</th>
       <th align="center">Result Details</th>
     </tr>
     <xsl:for-each select="RACER_PERSON">
      <tr>
        <xsl:choose>
          <xsl:when test="position() mod 2 = 1">
            <xsl:attribute name="class">clsOdd</xsl:attribute>
          </xsl:when>
          <xsl:otherwise>
            <xsl:attribute name="class">clsEven</xsl:attribute>
          </xsl:otherwise>
        </xsl:choose>
        <td><xsl:value-of select="LAST_NAME"/></td>
        <td><xsl:value-of select="FIRST_NAME"/></td>
        <td><xsl:value-of select="PLACEMENT"/></td>
        <td><xsl:value-of select="POINTS"/></td>
        <td>
          <xsl:call-template name="linkParameter">
            <xsl:with-param name="lname" select="LAST_NAME"/>
            <xsl:with-param name="fname" select="FIRST_NAME"/>
          </xsl:call-template>
        </td>
      </tr>
     </xsl:for-each>
   </table>
  </xsl:template>
  <xsl:template match="RACERS_LIST_ITEM">
    <tr>

    </tr>
  </xsl:template>
<!-- A Template to Attach a Link to a RuleElement Name Except we take the value as a Parameter-->
  <xsl:template name="linkParameter">
    <xsl:param name="lname"/>
    <xsl:param name="fname"/>
    <xsl:variable name="URL"><xsl:value-of select="concat($lname,'_',$fname,'.html')"/></xsl:variable>
    <B><A href="{concat('HTML/', $URL)}"><xsl:value-of select="$URL"/></A></B>
  </xsl:template>
</xsl:stylesheet>