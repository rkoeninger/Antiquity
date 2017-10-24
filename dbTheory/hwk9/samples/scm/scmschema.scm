<?xml version="1.0" encoding="UTF-8"?>
<scm:schema xmlns:scm="http://ns.saxonica.com/schema-component-model"
            generatedAt="2009-05-28T10:16:16.739Z">
   <scm:simpleType id="C0" name="explicitTimezoneType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#NCName"
                   variety="atomic"
                   primitiveType="#string">
      <scm:enumeration value="optional"/>
      <scm:enumeration value="prohibited"/>
      <scm:enumeration value="required"/>
   </scm:simpleType>
   <scm:simpleType id="C1" base="#NCName" variety="atomic" primitiveType="#string">
      <scm:enumeration value="preserve"/>
      <scm:enumeration value="default"/>
   </scm:simpleType>
   <scm:simpleType id="C2" name="builtInTypeReferenceType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#string"
                   variety="atomic"
                   primitiveType="#string">
      <scm:pattern value="#[a-zA-Z0-9]+"/>
   </scm:simpleType>
   <scm:simpleType id="C3" name="namespaceListType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#anySimpleType"
                   variety="list"
                   itemType="C4"/>
   <scm:simpleType id="C5" name="finalType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#anySimpleType"
                   variety="list"
                   itemType="C6"/>
   <scm:complexType id="C7" name="xpathContainerType"
                    targetNamespace="http://ns.saxonica.com/schema-component-model"
                    base="#anyType"
                    derivationMethod="restriction"
                    abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C8"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:simpleType id="C9" name="whitespaceType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#NCName"
                   variety="atomic"
                   primitiveType="#string">
      <scm:enumeration value="collapse"/>
      <scm:enumeration value="replace"/>
      <scm:enumeration value="preserve"/>
   </scm:simpleType>
   <scm:simpleType id="C6" name="derivationMethodType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#NCName"
                   variety="atomic"
                   primitiveType="#string">
      <scm:enumeration value="union"/>
      <scm:enumeration value="list"/>
      <scm:enumeration value="substitution"/>
      <scm:enumeration value="restriction"/>
      <scm:enumeration value="extension"/>
   </scm:simpleType>
   <scm:simpleType id="C10" base="#anySimpleType" variety="union" memberTypes="#language C11"/>
   <scm:simpleType id="C12" name="complexVarietyType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#NCName"
                   variety="atomic"
                   primitiveType="#string">
      <scm:enumeration value="element-only"/>
      <scm:enumeration value="mixed"/>
      <scm:enumeration value="simple"/>
      <scm:enumeration value="empty"/>
   </scm:simpleType>
   <scm:simpleType id="C13" name="typeReferenceType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#anySimpleType"
                   variety="union"
                   memberTypes="C2 #IDREF"/>
   <scm:simpleType id="C14" name="pseudoNamespaceType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#string"
                   variety="atomic"
                   primitiveType="#string">
      <scm:enumeration value="##targetNamespace"/>
      <scm:enumeration value="##other"/>
      <scm:enumeration value="##local"/>
      <scm:enumeration value="##defaultNamespace"/>
      <scm:enumeration value="##absent"/>
      <scm:enumeration value="##any"/>
   </scm:simpleType>
   <scm:simpleType id="C15" name="notQNameListType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#anySimpleType"
                   variety="list"
                   itemType="C16"/>
   <scm:simpleType id="C11" base="#string" variety="atomic" primitiveType="#string">
      <scm:enumeration value=""/>
   </scm:simpleType>
   <scm:complexType id="C17" name="identityConstraintType"
                    targetNamespace="http://ns.saxonica.com/schema-component-model"
                    base="#anyType"
                    derivationMethod="restriction"
                    abstract="false"
                    variety="element-only">
      <scm:attributeUse required="true" ref="C18"/>
      <scm:attributeUse required="true" ref="C19"/>
      <scm:attributeUse required="false" ref="C20"/>
      <scm:attributeUse required="false" ref="C21"/>
      <scm:modelGroupParticle minOccurs="1" maxOccurs="1">
         <scm:sequence>
            <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C22"/>
            <scm:elementParticle minOccurs="1" maxOccurs="unbounded" ref="C23"/>
         </scm:sequence>
      </scm:modelGroupParticle>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0">
            <scm:edge term="C22" to="1"/>
         </scm:state>
         <scm:state nr="1">
            <scm:edge term="C23" to="2"/>
         </scm:state>
         <scm:state nr="2" final="true">
            <scm:edge term="C23" to="3"/>
         </scm:state>
         <scm:state nr="3" final="true">
            <scm:edge term="C23" to="3"/>
         </scm:state>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:simpleType id="C24" name="xpathExpressionType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#string"
                   variety="atomic"
                   primitiveType="#string">
      <scm:pattern value=".+"/>
   </scm:simpleType>
   <scm:complexType id="C25" name="abstractParticleType"
                    targetNamespace="http://ns.saxonica.com/schema-component-model"
                    base="#anyType"
                    derivationMethod="restriction"
                    abstract="true"
                    variety="empty">
      <scm:attributeUse required="true" ref="C26"/>
      <scm:attributeUse required="true" ref="C27"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:simpleType id="C4" name="namespaceType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#anySimpleType"
                   variety="union"
                   memberTypes="C14 C28"/>
   <scm:simpleType id="C16" name="notQNameType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#anySimpleType"
                   variety="union"
                   memberTypes="C29 C30 #NCName"/>
   <scm:complexType id="C31" name="typedValueType"
                    targetNamespace="http://ns.saxonica.com/schema-component-model"
                    base="#anyType"
                    derivationMethod="restriction"
                    abstract="false"
                    variety="element-only">
      <scm:elementParticle minOccurs="0" maxOccurs="unbounded" ref="C32"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C32" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true">
            <scm:edge term="C32" to="1"/>
         </scm:state>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:simpleType id="C33" name="processContentsType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#NCName"
                   variety="atomic"
                   primitiveType="#string">
      <scm:enumeration value="skip"/>
      <scm:enumeration value="lax"/>
      <scm:enumeration value="strict"/>
   </scm:simpleType>
   <scm:simpleType id="C34" name="unboundedType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#NCName"
                   variety="atomic"
                   primitiveType="#string">
      <scm:enumeration value="unbounded"/>
   </scm:simpleType>
   <scm:simpleType id="C29" name="pseudoQNameType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#string"
                   variety="atomic"
                   primitiveType="#string">
      <scm:enumeration value="##sibling"/>
      <scm:enumeration value="##defined"/>
   </scm:simpleType>
   <scm:simpleType id="C35" name="xsdVersionType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#string"
                   variety="atomic"
                   primitiveType="#string">
      <scm:enumeration value="1.1"/>
      <scm:enumeration value="1.0"/>
   </scm:simpleType>
   <scm:simpleType id="C28" name="uriType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#token"
                   variety="atomic"
                   primitiveType="#string">
      <scm:pattern value="[^\s\r\n\t]*"/>
   </scm:simpleType>
   <scm:simpleType id="C30" name="clarkNameType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#string"
                   variety="atomic"
                   primitiveType="#string">
      <scm:pattern value="\{[^{}]*\}\i\c*"/>
   </scm:simpleType>
   <scm:simpleType id="C36" name="maxOccursType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#anySimpleType"
                   variety="union"
                   memberTypes="#nonNegativeInteger C34"/>
   <scm:simpleType id="C37" name="openContentModeType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#NCName"
                   variety="atomic"
                   primitiveType="#string">
      <scm:enumeration value="interleave"/>
      <scm:enumeration value="suffix"/>
   </scm:simpleType>
   <scm:simpleType id="C38" name="blockType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#anySimpleType"
                   variety="list"
                   itemType="C6"/>
   <scm:simpleType id="C39" name="typeReferenceListType"
                   targetNamespace="http://ns.saxonica.com/schema-component-model"
                   base="#anySimpleType"
                   variety="list"
                   itemType="C13"/>
   <scm:element id="C40" name="totalDigits"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C41"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C43" name="enumeration"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C44"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C45" name="attributeWildcard"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C46"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C47" name="simpleType"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C48"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C49" name="maxInclusive"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C50"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C51" name="minLength"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C52"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C53" name="maxLength"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C54"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C55" name="substitutionGroupAffiliation"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C56"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C57" name="assertion"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C58"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C59" name="openContent"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C60"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C61" name="modelGroupDefinition"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C62"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C63" name="attributeGroup"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C64"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C42" name="abstractFacet"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="#anyType"
                global="true"
                nillable="false"
                abstract="true"/>
   <scm:element id="C65" name="modelGroupParticle"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C66"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C67"/>
   </scm:element>
   <scm:element id="C68" name="element"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C69"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C70" name="elementParticle"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C71"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C67"/>
   </scm:element>
   <scm:element id="C72" name="pattern"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C73"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C74" name="explicitTimezone"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C75"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C76" name="notation"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C77"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C78" name="minInclusive"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C79"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C80" name="schema"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C81"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C82" name="length"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C83"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C84" name="abstractModelGroup"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C85"
                global="true"
                nillable="false"
                abstract="true"/>
   <scm:element id="C23" name="field"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C7"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C86" name="wildcard"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C87"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C67" name="abstractParticle"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C25"
                global="true"
                nillable="false"
                abstract="true"/>
   <scm:element id="C88" name="assert"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C89"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C90" name="state"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C91"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C92" name="unique"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C17"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C93" name="attributeUse"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C94"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C95" name="sequence"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C85"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C84"/>
   </scm:element>
   <scm:element id="C96" name="choice"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C85"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C84"/>
   </scm:element>
   <scm:element id="C97" name="key"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C17"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C98" name="all"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C85"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C84"/>
   </scm:element>
   <scm:element id="C99" name="attribute"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C100"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C101" name="maxExclusive"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C102"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C103" name="minExclusive"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C104"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C105" name="edge"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C106"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C22" name="selector"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C7"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C107" name="keyref"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C17"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C108" name="fixed"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C31"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C109" name="complexType"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C110"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C111" name="preprocess"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C112"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C113" name="alternativeType"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C114"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:element id="C115" name="elementWildcard"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C116"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C67"/>
   </scm:element>
   <scm:element id="C117" name="fractionDigits"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C118"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C119" name="whiteSpace"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C120"
                global="true"
                nillable="false"
                abstract="false">
      <scm:substitutionGroupAffiliation ref="C42"/>
   </scm:element>
   <scm:element id="C121" name="finiteStateMachine"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C122"
                global="true"
                nillable="false"
                abstract="false"/>
   <scm:attribute id="C123" name="lang" targetNamespace="http://www.w3.org/XML/1998/namespace"
                  type="C10"
                  global="true"/>
   <scm:attribute id="C124" name="space" targetNamespace="http://www.w3.org/XML/1998/namespace"
                  type="C1"
                  global="true"/>
   <scm:attribute id="C125" name="id" targetNamespace="http://www.w3.org/XML/1998/namespace"
                  type="#ID"
                  global="true"/>
   <scm:attribute id="C126" name="base" targetNamespace="http://www.w3.org/XML/1998/namespace"
                  type="#anyURI"
                  global="true"/>
   <scm:attributeGroup id="C127" name="specialAttrs"
                       targetNamespace="http://www.w3.org/XML/1998/namespace">
      <scm:attributeUse required="false" ref="C126"/>
      <scm:attributeUse required="false" ref="C123"/>
      <scm:attributeUse required="false" ref="C124"/>
      <scm:attributeUse required="false" ref="C125"/>
   </scm:attributeGroup>
   <scm:attribute id="C18" name="id" type="#ID" global="false" containingComplexType="C17"/>
   <scm:complexType id="C75" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C128"/>
      <scm:attributeUse required="false" ref="C129" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:complexType id="C85" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:modelGroupParticle minOccurs="0" maxOccurs="unbounded">
         <scm:sequence>
            <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C67"/>
         </scm:sequence>
      </scm:modelGroupParticle>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C70" to="1"/>
            <scm:edge term="C65" to="1"/>
            <scm:edge term="C67" to="1"/>
            <scm:edge term="C115" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true">
            <scm:edge term="C70" to="1"/>
            <scm:edge term="C65" to="1"/>
            <scm:edge term="C67" to="1"/>
            <scm:edge term="C115" to="1"/>
         </scm:state>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C20" name="targetNamespace" type="C28" global="false"
                  containingComplexType="C17"/>
   <scm:complexType id="C52" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C130"/>
      <scm:attributeUse required="false" ref="C131" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:complexType id="C56" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C132"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:complexType id="C71" base="C25" derivationMethod="extension" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C133"/>
      <scm:attributeUse required="true" ref="C26"/>
      <scm:attributeUse required="true" ref="C27"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:complexType id="C87" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C134"/>
      <scm:attributeUse required="true" ref="C135"/>
      <scm:attributeUse required="false" ref="C136"/>
      <scm:attributeUse required="true" ref="C137"/>
      <scm:attributeUse required="false" ref="C138"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C135" name="id" type="#ID" global="false" containingComplexType="C87"/>
   <scm:complexType id="C94" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="false" ref="C139"/>
      <scm:attributeUse required="true" ref="C140"/>
      <scm:attributeUse required="true" ref="C141"/>
      <scm:elementParticle minOccurs="0" maxOccurs="1" ref="C108"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C108" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:complexType id="C112" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="false" ref="C142" default="false"/>
      <scm:elementParticle minOccurs="1" maxOccurs="2" ref="C57"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0">
            <scm:edge term="C57" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true" minOccurs="1" maxOccurs="2">
            <scm:edge term="C57" to="1"/>
         </scm:state>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C128" name="value" type="C0" global="false" containingComplexType="C75"/>
   <scm:attribute id="C137" name="processContents" type="C33" global="false"
                  containingComplexType="C87"/>
   <scm:complexType id="C100" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="false" ref="C143"/>
      <scm:attributeUse required="false" ref="C144"/>
      <scm:attributeUse required="true" ref="C145"/>
      <scm:attributeUse required="true" ref="C146"/>
      <scm:attributeUse required="true" ref="C147"/>
      <scm:attributeUse required="false" ref="C148"/>
      <scm:attributeUse required="true" ref="C149"/>
      <scm:elementParticle minOccurs="0" maxOccurs="1" ref="C108"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C108" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C147" name="name" type="#NCName" global="false"
                  containingComplexType="C100"/>
   <scm:attribute id="C149" name="type" type="C13" global="false" containingComplexType="C100"/>
   <scm:attribute id="C143" name="containingComplexType" type="#IDREF" global="false"
                  containingComplexType="C100"/>
   <scm:attribute id="C130" name="value" type="#nonNegativeInteger" global="false"
                  containingComplexType="C52"/>
   <scm:complexType id="C104" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C150"/>
      <scm:attributeUse required="false" ref="C151" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C151" name="fixed" type="#boolean" global="false"
                  containingComplexType="C104"/>
   <scm:attribute id="C140" name="ref" type="#IDREF" global="false" containingComplexType="C94"/>
   <scm:complexType id="C110" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="true" ref="C152"/>
      <scm:attributeUse required="true" ref="C153"/>
      <scm:attributeUse required="true" ref="C154"/>
      <scm:attributeUse required="false" ref="C155"/>
      <scm:attributeUse required="false" ref="C156"/>
      <scm:attributeUse required="true" ref="C157"/>
      <scm:attributeUse required="false" ref="C158"/>
      <scm:attributeUse required="false" ref="C159"/>
      <scm:attributeUse required="false" ref="C160"/>
      <scm:attributeUse required="true" ref="C161"/>
      <scm:modelGroupParticle minOccurs="1" maxOccurs="1">
         <scm:sequence>
            <scm:elementParticle minOccurs="0" maxOccurs="1" ref="C59"/>
            <scm:elementParticle minOccurs="0" maxOccurs="unbounded" ref="C93"/>
            <scm:elementParticle minOccurs="0" maxOccurs="1" ref="C45"/>
            <scm:elementParticle minOccurs="0" maxOccurs="1" ref="C67"/>
            <scm:elementParticle minOccurs="0" maxOccurs="1" ref="C121"/>
            <scm:elementParticle minOccurs="0" maxOccurs="unbounded" ref="C57"/>
         </scm:sequence>
      </scm:modelGroupParticle>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C45" to="6"/>
            <scm:edge term="C57" to="5"/>
            <scm:edge term="C59" to="4"/>
            <scm:edge term="C65" to="2"/>
            <scm:edge term="C70" to="2"/>
            <scm:edge term="C67" to="2"/>
            <scm:edge term="C93" to="3"/>
            <scm:edge term="C115" to="2"/>
            <scm:edge term="C121" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true">
            <scm:edge term="C57" to="5"/>
         </scm:state>
         <scm:state nr="2" final="true">
            <scm:edge term="C121" to="1"/>
            <scm:edge term="C57" to="5"/>
         </scm:state>
         <scm:state nr="3" final="true">
            <scm:edge term="C121" to="1"/>
            <scm:edge term="C70" to="2"/>
            <scm:edge term="C93" to="3"/>
            <scm:edge term="C65" to="2"/>
            <scm:edge term="C67" to="2"/>
            <scm:edge term="C115" to="2"/>
            <scm:edge term="C57" to="5"/>
            <scm:edge term="C45" to="6"/>
         </scm:state>
         <scm:state nr="4" final="true">
            <scm:edge term="C121" to="1"/>
            <scm:edge term="C70" to="2"/>
            <scm:edge term="C93" to="3"/>
            <scm:edge term="C65" to="2"/>
            <scm:edge term="C67" to="2"/>
            <scm:edge term="C115" to="2"/>
            <scm:edge term="C57" to="5"/>
            <scm:edge term="C45" to="6"/>
         </scm:state>
         <scm:state nr="5" final="true">
            <scm:edge term="C57" to="5"/>
         </scm:state>
         <scm:state nr="6" final="true">
            <scm:edge term="C121" to="1"/>
            <scm:edge term="C70" to="2"/>
            <scm:edge term="C65" to="2"/>
            <scm:edge term="C67" to="2"/>
            <scm:edge term="C115" to="2"/>
            <scm:edge term="C57" to="5"/>
         </scm:state>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C152" name="abstract" type="#boolean" global="false"
                  containingComplexType="C110"/>
   <scm:attribute id="C157" name="id" type="#ID" global="false" containingComplexType="C110"/>
   <scm:complexType id="C64" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="true" ref="C162"/>
      <scm:attributeUse required="false" ref="C163"/>
      <scm:attributeUse required="false" ref="C164"/>
      <scm:modelGroupParticle minOccurs="1" maxOccurs="1">
         <scm:sequence>
            <scm:elementParticle minOccurs="0" maxOccurs="unbounded" ref="C93"/>
            <scm:elementParticle minOccurs="0" maxOccurs="1" ref="C45"/>
         </scm:sequence>
      </scm:modelGroupParticle>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C93" to="1"/>
            <scm:edge term="C45" to="2"/>
         </scm:state>
         <scm:state nr="1" final="true">
            <scm:edge term="C93" to="1"/>
            <scm:edge term="C45" to="2"/>
         </scm:state>
         <scm:state nr="2" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:complexType id="C106" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C165"/>
      <scm:attributeUse required="true" ref="C166"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C165" name="term" type="#IDREF" global="false" containingComplexType="C106"/>
   <scm:attribute id="C161" name="variety" type="C12" global="false" containingComplexType="C110"/>
   <scm:attribute id="C146" name="id" type="#ID" global="false" containingComplexType="C100"/>
   <scm:complexType id="C54" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C167"/>
      <scm:attributeUse required="false" ref="C168" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C136" name="namespaces" type="C3" global="false"
                  containingComplexType="C87"/>
   <scm:attribute id="C131" name="fixed" type="#boolean" global="false"
                  containingComplexType="C52"/>
   <scm:attribute id="C148" name="targetNamespace" type="C28" global="false"
                  containingComplexType="C100"/>
   <scm:attribute id="C141" name="required" type="#boolean" global="false"
                  containingComplexType="C94"/>
   <scm:attribute id="C168" name="fixed" type="#boolean" global="false"
                  containingComplexType="C54"/>
   <scm:attribute id="C150" name="value" type="#anySimpleType" global="false"
                  containingComplexType="C104"/>
   <scm:attribute id="C160" name="targetNamespace" type="C28" global="false"
                  containingComplexType="C110"/>
   <scm:attribute id="C145" name="global" type="#boolean" global="false"
                  containingComplexType="C100"/>
   <scm:attribute id="C134" name="constraint" type="#NCName" global="false"
                  containingComplexType="C87"/>
   <scm:complexType id="C118" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C169"/>
      <scm:attributeUse required="false" ref="C170" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C170" name="fixed" type="#boolean" global="false"
                  containingComplexType="C118"/>
   <scm:attribute id="C129" name="fixed" type="#boolean" global="false"
                  containingComplexType="C75"/>
   <scm:complexType id="C102" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C171"/>
      <scm:attributeUse required="false" ref="C172" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C171" name="value" type="#anySimpleType" global="false"
                  containingComplexType="C102"/>
   <scm:attribute id="C156" name="final" type="C5" global="false" containingComplexType="C110"/>
   <scm:attribute id="C19" name="name" type="#NCName" global="false" containingComplexType="C17"/>
   <scm:attribute id="C164" name="targetNamespace" type="C28" global="false"
                  containingComplexType="C64"/>
   <scm:complexType id="C66" base="C25" derivationMethod="extension" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="false" ref="C173"/>
      <scm:attributeUse required="true" ref="C26"/>
      <scm:attributeUse required="true" ref="C27"/>
      <scm:elementParticle minOccurs="0" maxOccurs="unbounded" ref="C84"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C98" to="1"/>
            <scm:edge term="C84" to="1"/>
            <scm:edge term="C96" to="1"/>
            <scm:edge term="C95" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true">
            <scm:edge term="C98" to="1"/>
            <scm:edge term="C84" to="1"/>
            <scm:edge term="C96" to="1"/>
            <scm:edge term="C95" to="1"/>
         </scm:state>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C173" name="ref" type="#IDREF" global="false" containingComplexType="C66"/>
   <scm:complexType id="C62" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="true" ref="C174"/>
      <scm:attributeUse required="false" ref="C175"/>
      <scm:attributeUse required="false" ref="C176"/>
      <scm:elementParticle minOccurs="0" maxOccurs="unbounded" ref="C67"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C70" to="1"/>
            <scm:edge term="C65" to="1"/>
            <scm:edge term="C67" to="1"/>
            <scm:edge term="C115" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true">
            <scm:edge term="C70" to="1"/>
            <scm:edge term="C65" to="1"/>
            <scm:edge term="C67" to="1"/>
            <scm:edge term="C115" to="1"/>
         </scm:state>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C176" name="targetNamespace" type="C28" global="false"
                  containingComplexType="C62"/>
   <scm:attribute id="C174" name="id" type="#ID" global="false" containingComplexType="C62"/>
   <scm:attribute id="C166" name="to" type="#integer" global="false" containingComplexType="C106"/>
   <scm:complexType id="C120" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C177"/>
      <scm:attributeUse required="false" ref="C178" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C177" name="value" type="C9" global="false" containingComplexType="C120"/>
   <scm:complexType id="C81" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="false" ref="C179"/>
      <scm:attributeUse required="false" ref="C180"/>
      <scm:modelGroupParticle minOccurs="0" maxOccurs="unbounded">
         <scm:choice>
            <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C68"/>
            <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C99"/>
            <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C109"/>
            <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C47"/>
            <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C63"/>
            <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C61"/>
            <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C76"/>
            <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C86"/>
         </scm:choice>
      </scm:modelGroupParticle>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C86" to="1"/>
            <scm:edge term="C109" to="1"/>
            <scm:edge term="C68" to="1"/>
            <scm:edge term="C76" to="1"/>
            <scm:edge term="C63" to="1"/>
            <scm:edge term="C99" to="1"/>
            <scm:edge term="C61" to="1"/>
            <scm:edge term="C47" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true">
            <scm:edge term="C86" to="1"/>
            <scm:edge term="C109" to="1"/>
            <scm:edge term="C68" to="1"/>
            <scm:edge term="C76" to="1"/>
            <scm:edge term="C63" to="1"/>
            <scm:edge term="C99" to="1"/>
            <scm:edge term="C61" to="1"/>
            <scm:edge term="C47" to="1"/>
         </scm:state>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C180" name="xsdVersion" type="C35" global="false"
                  containingComplexType="C81"/>
   <scm:attribute id="C179" name="generatedAt" type="#dateTime" global="false"
                  containingComplexType="C81"/>
   <scm:complexType id="C116" base="C25" derivationMethod="extension" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C181"/>
      <scm:attributeUse required="true" ref="C26"/>
      <scm:attributeUse required="true" ref="C27"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C181" name="ref" type="#IDREF" global="false" containingComplexType="C116"/>
   <scm:complexType id="C50" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C182"/>
      <scm:attributeUse required="false" ref="C183" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C167" name="value" type="#nonNegativeInteger" global="false"
                  containingComplexType="C54"/>
   <scm:attribute id="C183" name="fixed" type="#boolean" global="false"
                  containingComplexType="C50"/>
   <scm:attribute id="C153" name="base" type="C13" global="false" containingComplexType="C110"/>
   <scm:attribute id="C158" name="name" type="#NCName" global="false"
                  containingComplexType="C110"/>
   <scm:attribute id="C132" name="ref" type="#IDREF" global="false" containingComplexType="C56"/>
   <scm:complexType id="C79" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C184"/>
      <scm:attributeUse required="false" ref="C185" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C184" name="value" type="#anySimpleType" global="false"
                  containingComplexType="C79"/>
   <scm:attribute id="C185" name="fixed" type="#boolean" global="false"
                  containingComplexType="C79"/>
   <scm:attribute id="C26" name="maxOccurs" type="C36" global="false" containingComplexType="C25"/>
   <scm:attribute id="C172" name="fixed" type="#boolean" global="false"
                  containingComplexType="C102"/>
   <scm:complexType id="C83" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C186"/>
      <scm:attributeUse required="false" ref="C187" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C187" name="fixed" type="#boolean" global="false"
                  containingComplexType="C83"/>
   <scm:attribute id="C159" name="simpleType" type="C13" global="false"
                  containingComplexType="C110"/>
   <scm:attribute id="C154" name="derivationMethod" type="C6" global="false"
                  containingComplexType="C110"/>
   <scm:element id="C32" name="item"
                targetNamespace="http://ns.saxonica.com/schema-component-model"
                type="C188"
                global="false"
                containingComplexType="C31"
                nillable="false"
                abstract="false"/>
   <scm:complexType id="C188" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C189"/>
      <scm:attributeUse required="true" ref="C190"/>
      <scm:attributeUse required="false" ref="C191"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C190" name="value" type="#string" global="false"
                  containingComplexType="C188"/>
   <scm:attribute id="C191" name="namespace" type="C28" global="false"
                  containingComplexType="C188"/>
   <scm:complexType id="C41" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C192"/>
      <scm:attributeUse required="false" ref="C193" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C193" name="fixed" type="#boolean" global="false"
                  containingComplexType="C41"/>
   <scm:attribute id="C175" name="name" type="#NCName" global="false" containingComplexType="C62"/>
   <scm:attribute id="C178" name="fixed" type="#boolean" global="false"
                  containingComplexType="C120"/>
   <scm:attribute id="C133" name="ref" type="#IDREF" global="false" containingComplexType="C71"/>
   <scm:complexType id="C89" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="false" ref="C194" default="false"/>
      <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C57"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0">
            <scm:edge term="C57" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C194" name="fixed" type="#boolean" global="false"
                  containingComplexType="C89"/>
   <scm:attribute id="C162" name="id" type="#ID" global="false" containingComplexType="C64"/>
   <scm:complexType id="C122" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="true" ref="C195"/>
      <scm:elementParticle minOccurs="1" maxOccurs="unbounded" ref="C90"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0">
            <scm:edge term="C90" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true">
            <scm:edge term="C90" to="2"/>
         </scm:state>
         <scm:state nr="2" final="true">
            <scm:edge term="C90" to="2"/>
         </scm:state>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:complexType id="C73" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C196"/>
      <scm:attributeUse required="false" ref="C197" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C197" name="fixed" type="#boolean" global="false"
                  containingComplexType="C73"/>
   <scm:attribute id="C196" name="value" type="#string" global="false"
                  containingComplexType="C73"/>
   <scm:attribute id="C163" name="name" type="#NCName" global="false" containingComplexType="C64"/>
   <scm:attribute id="C139" name="default" type="#string" global="false"
                  containingComplexType="C94"/>
   <scm:complexType id="C114" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C198"/>
      <scm:attributeUse required="true" ref="C199"/>
      <scm:attributeUse required="true" ref="C200"/>
      <scm:attributeUse required="false" ref="C126"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C199" name="test" type="C24" global="false" containingComplexType="C114"/>
   <scm:attribute id="C198" name="type" type="C13" global="false" containingComplexType="C114"/>
   <scm:attribute id="C200" name="defaultNamespace" type="C28" global="false"
                  containingComplexType="C114"/>
   <scm:attribute id="C144" name="default" type="#string" global="false"
                  containingComplexType="C100"/>
   <scm:attribute id="C138" name="notQName" type="C15" global="false" containingComplexType="C87"/>
   <scm:attribute id="C27" name="minOccurs" type="#nonNegativeInteger" global="false"
                  containingComplexType="C25"/>
   <scm:attribute id="C192" name="value" type="#positiveInteger" global="false"
                  containingComplexType="C41"/>
   <scm:attribute id="C182" name="value" type="#anySimpleType" global="false"
                  containingComplexType="C50"/>
   <scm:attribute id="C186" name="value" type="#nonNegativeInteger" global="false"
                  containingComplexType="C83"/>
   <scm:attribute id="C142" name="fixed" type="#boolean" global="false"
                  containingComplexType="C112"/>
   <scm:complexType id="C44" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C201"/>
      <scm:attributeUse required="false" ref="C202" default="false"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C201" name="value" type="#anySimpleType" global="false"
                  containingComplexType="C44"/>
   <scm:complexType id="C58" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C203"/>
      <scm:attributeUse required="true" ref="C204"/>
      <scm:attributeUse required="false" ref="C126"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C203" name="test" type="C24" global="false" containingComplexType="C58"/>
   <scm:attribute id="C204" name="defaultNamespace" type="C28" global="false"
                  containingComplexType="C58"/>
   <scm:attribute id="C195" name="initialState" type="#integer" global="false"
                  containingComplexType="C122"/>
   <scm:attribute id="C189" name="type" type="C2" global="false" containingComplexType="C188"/>
   <scm:complexType id="C69" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="true" ref="C205"/>
      <scm:attributeUse required="false" ref="C206"/>
      <scm:attributeUse required="false" ref="C207"/>
      <scm:attributeUse required="false" ref="C208"/>
      <scm:attributeUse required="false" ref="C209"/>
      <scm:attributeUse required="true" ref="C210"/>
      <scm:attributeUse required="true" ref="C211"/>
      <scm:attributeUse required="true" ref="C212"/>
      <scm:attributeUse required="true" ref="C213"/>
      <scm:attributeUse required="false" ref="C214"/>
      <scm:attributeUse required="true" ref="C215"/>
      <scm:modelGroupParticle minOccurs="1" maxOccurs="1">
         <scm:sequence>
            <scm:elementParticle minOccurs="0" maxOccurs="unbounded" ref="C55"/>
            <scm:elementParticle minOccurs="0" maxOccurs="unbounded" ref="C113"/>
            <scm:modelGroupParticle minOccurs="0" maxOccurs="unbounded">
               <scm:choice>
                  <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C92"/>
                  <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C97"/>
                  <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C107"/>
               </scm:choice>
            </scm:modelGroupParticle>
            <scm:elementParticle minOccurs="0" maxOccurs="1" ref="C108"/>
         </scm:sequence>
      </scm:modelGroupParticle>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C107" to="1"/>
            <scm:edge term="C97" to="1"/>
            <scm:edge term="C108" to="2"/>
            <scm:edge term="C92" to="1"/>
            <scm:edge term="C113" to="3"/>
            <scm:edge term="C55" to="4"/>
         </scm:state>
         <scm:state nr="1" final="true">
            <scm:edge term="C107" to="1"/>
            <scm:edge term="C97" to="1"/>
            <scm:edge term="C108" to="2"/>
            <scm:edge term="C92" to="1"/>
         </scm:state>
         <scm:state nr="2" final="true"/>
         <scm:state nr="3" final="true">
            <scm:edge term="C107" to="1"/>
            <scm:edge term="C97" to="1"/>
            <scm:edge term="C108" to="2"/>
            <scm:edge term="C92" to="1"/>
            <scm:edge term="C113" to="3"/>
         </scm:state>
         <scm:state nr="4" final="true">
            <scm:edge term="C107" to="1"/>
            <scm:edge term="C97" to="1"/>
            <scm:edge term="C108" to="2"/>
            <scm:edge term="C92" to="1"/>
            <scm:edge term="C113" to="3"/>
            <scm:edge term="C55" to="4"/>
         </scm:state>
      </scm:finiteStateMachine>
      <scm:assertion xmlns:xs="http://www.w3.org/2001/XMLSchema" test="not(@default and scm:fixed)"
                     defaultNamespace=""
                     xml:base="file:/c:/MyJava/samples/scm/scmschema.xsd"/>
   </scm:complexType>
   <scm:attribute id="C206" name="block" type="C38" global="false" containingComplexType="C69"/>
   <scm:attribute id="C214" name="targetNamespace" type="C28" global="false"
                  containingComplexType="C69"/>
   <scm:attribute id="C209" name="final" type="C5" global="false" containingComplexType="C69"/>
   <scm:attribute id="C211" name="id" type="#ID" global="false" containingComplexType="C69"/>
   <scm:attribute id="C213" name="nillable" type="#boolean" global="false"
                  containingComplexType="C69"/>
   <scm:attribute id="C210" name="global" type="#boolean" global="false"
                  containingComplexType="C69"/>
   <scm:attribute id="C205" name="abstract" type="#boolean" global="false"
                  containingComplexType="C69"/>
   <scm:attribute id="C212" name="name" type="#NCName" global="false" containingComplexType="C69"/>
   <scm:attribute id="C208" name="default" type="#string" global="false"
                  containingComplexType="C69"/>
   <scm:attribute id="C215" name="type" type="C13" global="false" containingComplexType="C69"/>
   <scm:complexType id="C46" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C216"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C8" name="xpath" type="C24" global="false" containingComplexType="C7"/>
   <scm:attribute id="C216" name="ref" type="#IDREF" global="false" containingComplexType="C46"/>
   <scm:complexType id="C77" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="true" ref="C217"/>
      <scm:attributeUse required="false" ref="C218"/>
      <scm:attributeUse required="false" ref="C219"/>
      <scm:attributeUse required="false" ref="C220"/>
      <scm:attributeUse required="false" ref="C221"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C220" name="systemId" type="C28" global="false" containingComplexType="C77"/>
   <scm:attribute id="C218" name="name" type="#NCName" global="false" containingComplexType="C77"/>
   <scm:attribute id="C219" name="targetNamespace" type="C28" global="false"
                  containingComplexType="C77"/>
   <scm:attribute id="C202" name="namespaceSensitive" type="#boolean" global="false"
                  containingComplexType="C44"/>
   <scm:attribute id="C169" name="value" type="#nonNegativeInteger" global="false"
                  containingComplexType="C118"/>
   <scm:attribute id="C207" name="containingComplexType" type="#IDREF" global="false"
                  containingComplexType="C69"/>
   <scm:complexType id="C48" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="true" ref="C222"/>
      <scm:attributeUse required="false" ref="C223"/>
      <scm:attributeUse required="true" ref="C224"/>
      <scm:attributeUse required="false" ref="C225"/>
      <scm:attributeUse required="false" ref="C226"/>
      <scm:attributeUse required="false" ref="C227"/>
      <scm:attributeUse required="false" ref="C228"/>
      <scm:attributeUse required="false" ref="C229"/>
      <scm:attributeUse required="true" ref="C230"/>
      <scm:modelGroupParticle minOccurs="0" maxOccurs="unbounded">
         <scm:sequence>
            <scm:elementParticle minOccurs="1" maxOccurs="1" ref="C42"/>
         </scm:sequence>
      </scm:modelGroupParticle>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C40" to="1"/>
            <scm:edge term="C43" to="1"/>
            <scm:edge term="C49" to="1"/>
            <scm:edge term="C51" to="1"/>
            <scm:edge term="C53" to="1"/>
            <scm:edge term="C42" to="1"/>
            <scm:edge term="C78" to="1"/>
            <scm:edge term="C72" to="1"/>
            <scm:edge term="C74" to="1"/>
            <scm:edge term="C82" to="1"/>
            <scm:edge term="C88" to="1"/>
            <scm:edge term="C103" to="1"/>
            <scm:edge term="C101" to="1"/>
            <scm:edge term="C111" to="1"/>
            <scm:edge term="C117" to="1"/>
            <scm:edge term="C119" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true">
            <scm:edge term="C40" to="1"/>
            <scm:edge term="C43" to="1"/>
            <scm:edge term="C49" to="1"/>
            <scm:edge term="C51" to="1"/>
            <scm:edge term="C53" to="1"/>
            <scm:edge term="C42" to="1"/>
            <scm:edge term="C78" to="1"/>
            <scm:edge term="C72" to="1"/>
            <scm:edge term="C74" to="1"/>
            <scm:edge term="C82" to="1"/>
            <scm:edge term="C88" to="1"/>
            <scm:edge term="C103" to="1"/>
            <scm:edge term="C101" to="1"/>
            <scm:edge term="C111" to="1"/>
            <scm:edge term="C117" to="1"/>
            <scm:edge term="C119" to="1"/>
         </scm:state>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C222" name="base" type="C13" global="false" containingComplexType="C48"/>
   <scm:attribute id="C225" name="itemType" type="C13" global="false" containingComplexType="C48"/>
   <scm:attribute id="C223" name="final" type="C5" global="false" containingComplexType="C48"/>
   <scm:attribute id="C226" name="memberTypes" type="C39" global="false"
                  containingComplexType="C48"/>
   <scm:attribute id="C229" name="targetNamespace" type="C28" global="false"
                  containingComplexType="C48"/>
   <scm:attribute id="C228" name="primitiveType" type="C2" global="false"
                  containingComplexType="C48"/>
   <scm:attribute id="C227" name="name" type="#NCName" global="false" containingComplexType="C48"/>
   <scm:attribute id="C230" name="variety" type="#NCName" global="false"
                  containingComplexType="C48"/>
   <scm:attribute id="C224" name="id" type="#NCName" global="false" containingComplexType="C48"/>
   <scm:attribute id="C21" name="key" type="#IDREF" global="false" containingComplexType="C17"/>
   <scm:attribute id="C217" name="id" type="#ID" global="false" containingComplexType="C77"/>
   <scm:attribute id="C155" name="block" type="C38" global="false" containingComplexType="C110"/>
   <scm:complexType id="C91" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="element-only">
      <scm:attributeUse required="false" ref="C231"/>
      <scm:attributeUse required="false" ref="C232"/>
      <scm:attributeUse required="false" ref="C233"/>
      <scm:attributeUse required="false" ref="C234"/>
      <scm:attributeUse required="true" ref="C235"/>
      <scm:elementParticle minOccurs="0" maxOccurs="unbounded" ref="C105"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true">
            <scm:edge term="C105" to="1"/>
         </scm:state>
         <scm:state nr="1" final="true">
            <scm:edge term="C105" to="1"/>
         </scm:state>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C233" name="maxOccurs" type="C36" global="false"
                  containingComplexType="C91"/>
   <scm:attribute id="C235" name="nr" type="#integer" global="false" containingComplexType="C91"/>
   <scm:attribute id="C234" name="minOccurs" type="#integer" global="false"
                  containingComplexType="C91"/>
   <scm:attribute id="C232" name="final" type="#boolean" global="false"
                  containingComplexType="C91"/>
   <scm:attribute id="C231" name="afterMax" type="#integer" global="false"
                  containingComplexType="C91"/>
   <scm:attribute id="C221" name="publicId" type="#string" global="false"
                  containingComplexType="C77"/>
   <scm:complexType id="C60" base="#anyType" derivationMethod="restriction" abstract="false"
                    variety="empty">
      <scm:attributeUse required="false" ref="C236"/>
      <scm:attributeUse required="false" ref="C237"/>
      <scm:finiteStateMachine initialState="0">
         <scm:state nr="0" final="true"/>
      </scm:finiteStateMachine>
   </scm:complexType>
   <scm:attribute id="C236" name="mode" type="C37" global="false" containingComplexType="C60"/>
   <scm:attribute id="C237" name="wildcard" type="#IDREF" global="false"
                  containingComplexType="C60"/>
</scm:schema>