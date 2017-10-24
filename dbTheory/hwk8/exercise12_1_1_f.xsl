<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="/">
		<ul>
			<xsl:for-each select="/Products/Maker/PC/">
				<xsl:if test="/HardDisk &gt; 10">
					<li>
						<xsl:value-of select="./@model"/>
					</li>
				</xsl:if>
			</xsl:for-each>
		</ul>
	</xsl:template>
</xsl:stylesheet>
