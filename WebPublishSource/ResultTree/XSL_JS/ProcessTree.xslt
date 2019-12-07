<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dt="urn:schemas-microsoft-com:datatypes">

<xsl:template match="tree">
  <xsl:apply-templates select="entity"/>
  <DIV id="divError" STYLE="font-family: Verdana; font-size: 11px;font-weight: bold;padding-top: 8px;padding:5px;"></DIV>
</xsl:template>

<xsl:template match="entity">
  <div ondragstart="return false">
  <xsl:attribute name="onclick">selectedEntity = '<xsl:value-of select="@id"/>';clickOnEntity(this);insertUpdateDisplay('update');</xsl:attribute>
  <xsl:attribute name="image"><xsl:value-of select="imageBase"/></xsl:attribute>
  <xsl:attribute name="imageOpen"><xsl:value-of select="imageOpen"/></xsl:attribute>
  <xsl:attribute name="open">false</xsl:attribute>
  <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
  <xsl:attribute name="open">false</xsl:attribute>
  <xsl:attribute name="STYLE">
    <xsl:if test="count(ancestor::node()) > 2">
      display: none;
    </xsl:if>
    padding-left: 20px;
    cursor: hand;
  </xsl:attribute>
    <table border="0" cellspacing="0" cellpadding="2">
      <tr>
        <td valign="top" align="left">
          <img border="0">
            <xsl:attribute name="id"><xsl:value-of select="@id"/>Image</xsl:attribute>
            <xsl:attribute name="SRC">
              <xsl:value-of select="imageBase"/>
            </xsl:attribute>
          </img>
        </td>
        <td valign="middle" align="left" nowrap="true" onmouseover="this.style.color = 'red'; this.style.fontWeight = 'bold';" onmouseout="this.style.color = 'black'; this.style.fontWeight = 'normal';">
          <span ONFOCUS="document.body.onselectstart = null" ONBLUR="document.body.onselectstart = returnFalse;" ONSELECTSTART="return false">
            <xsl:attribute name="STYLE">
              padding-left: 2px;
              font-family: Verdana;
              font-size: 11px;
              font-color: black;
            </xsl:attribute>
		    <xsl:attribute name="ID"><xsl:value-of select="@id"/>name</xsl:attribute>
		    <xsl:value-of select="description"/>
		  </span>
		</td>
      </tr>
    </table>
  <xsl:apply-templates select="contents/entity"/>
  </div>
</xsl:template>

</xsl:stylesheet>