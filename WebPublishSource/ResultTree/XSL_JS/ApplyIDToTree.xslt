<xsl:stylesheet version="1.1" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:dt="urn:schemas-microsoft-com:datatypes">
  <xsl:template match="/tree">
    <xsl:element name="tree">
      <xsl:apply-templates select="entity"/>
    </xsl:element>
  </xsl:template>
  <xsl:template match="entity">
    <xsl:element name="entity">
      <xsl:attribute name="id">
        <xsl:value-of select="generate-id(.)"/>
      </xsl:attribute>
      <xsl:for-each select="*">
        <xsl:if test="name() = 'contents'">
          <xsl:element name="contents">
            <xsl:apply-templates select="entity"/>
          </xsl:element>
        </xsl:if>
        <xsl:if test="name() != 'contents'">
          <xsl:element name="{name()}">
            <xsl:value-of select="."/>
          </xsl:element>
        </xsl:if>
      </xsl:for-each>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>