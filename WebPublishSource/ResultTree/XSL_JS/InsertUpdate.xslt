<xsl:stylesheet version="1.1" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:dt="urn:schemas-microsoft-com:datatypes">
  <xsl:param name="action"/>
  <xsl:param name="selectedEntity"/>
  <xsl:template match="entity">
    <TABLE BORDER="0" CELLSPACING="0" CELLPADDING="1" WIDTH="100%">
      <xsl:for-each select="*">
        <xsl:if test="name() != 'contents'">
          <xsl:choose>
            <!-- Exclusions for the Images -->
            <xsl:when test="name() = 'imageBase'"></xsl:when>
            <xsl:when test="name() = 'imageOpen'"></xsl:when>
            <!-- Everything Else -->
            <xsl:otherwise>
              <TR>
                <TD CLASS="dataLabel" ONMOUSEOVER="swapClass(this, 'dataLabelOver')" ONMOUSEOUT="swapClass(this, 'dataLabel')" STYLE="border-right: 1px solid black; border-bottom: 1px solid black;">
                  <xsl:value-of select="name()"/>
                </TD>
                <TD CLASS="dataInput" ONMOUSEOVER="swapClass(this, 'dataInputOver')" ONMOUSEOUT="swapClass(this, 'dataInput')" STYLE="border-right: 1px solid black; border-bottom: 1px solid black;" WIDTH="100%">
                  <xsl:if test="$action = 'update'">
                    <xsl:value-of select="."/>
                  </xsl:if>
                  <!--  This template may allow for an insert - Although,
                  I must admit, I am not sure How this is going to work.
                  <xsl:if test="$action = 'insert'">
                  </xsl:if>
                  -->
                </TD>
              </TR>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:if>
      </xsl:for-each>
    </TABLE>
  </xsl:template>
</xsl:stylesheet>