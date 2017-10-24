<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="/">
		<ul>
			<xsl:for-each select="/Products/Maker/Printer">
				<li>
					<ul>
						<li><xsl:value-of select="@model"/></li>
						<li><xsl:value-of select="@price"/></li>
						<li><xsl:value-of select="Color"/></li>
						<li><xsl:value-of select="Type"/></li>
			</ul>
			</li>
			</xsl:for-each>
		</ul>
	</xsl:template>
</xsl:stylesheet>
