import com.saxonica.config.ProfessionalTransformerFactory;
import net.sf.saxon.*;
import net.sf.saxon.dom.NodeOverNodeInfo;
import net.sf.saxon.om.Axis;
import net.sf.saxon.om.DocumentInfo;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.pattern.NodeKindTest;
import net.sf.saxon.sxpath.XPathExpression;
import net.sf.saxon.tinytree.TinyBuilder;
import net.sf.saxon.xpath.XPathEvaluator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLFilterImpl;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.util.Properties;


/**
 * Some examples to show how the JAXP API for Transformations
 * could be used.
 *
 * @author <a href="mailto:scott_boag@lotus.com">Scott Boag</a>
 */
public class TraxExamples {

    /**
     * Method main
     * @param argv command line arguments.
     * There is a single argument, the name of the test to be run. The default is "all",
     * which runs all tests.
     */
    public static void main(String argv[]) {

        // MHK note. The SAX interface says that SystemIds must be absolute
        // URLs. This example assumes that relative URLs are OK, and that
        // they are interpreted relative to the current directory. I have
        // modified the Aelfred SAXDriver to make this work, as a number of
        // users had complained that Xalan allowed this and Saxon didn't.

        String test = "all";
        if (argv.length > 0) {
            test = argv[0];
        }

        String foo_xml = "xml/foo.xml";
        String foo_xsl = "xsl/foo.xsl";
        String baz_xml = "xml/baz.xml";
        String baz_xsl = "xsl/baz.xsl";
        String foo2_xsl = "xsl/foo2.xsl";
        String foo3_xsl = "xsl/foo3.xsl";
        String text_xsl = "xsl/text.xsl";
        String cities_xml = "xml/cities.xml";
        String embedded_xml = "xml/embedded.xml";
        String multidoc_xsl = "xsl/multidoc.xsl";
        String identity_xsl = "xsl/identity.xsl";

        System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");



        if (test.equals("all") || test.equals("ParseOnly")) {
            System.out.println("\n\n==== ParseOnly ====");

            try {
                exampleParseOnly(foo_xml);
            } catch (Exception ex) {
                handleException(ex);
            }
        }



        if (test.equals("all") || test.equals("Simple1")) {
            System.out.println("\n\n==== Simple1 ====");

            try {
                exampleSimple1(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("Simple2")) {
            System.out.println("\n\n==== Simple2 ====");

            try {
                exampleSimple2(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("FromStream")) {
            System.out.println("\n\n==== FromStream ====");

            try {
                exampleFromStream(foo_xml, baz_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("FromReader")) {
            System.out.println("\n\n==== FromReader ====");

            try {
                exampleFromReader(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("UseTemplatesObj")) {
            System.out.println("\n\n==== UseTemplatesObj ====");

            try {
                exampleUseTemplatesObj(foo_xml, baz_xml,
                                       foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("UseTemplatesHandler")) {
            System.out.println("\n\n==== UseTemplatesHandler ====");

            try {
                (new TraxExamples()).exampleUseTemplatesHandler(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("SAXResult")) {
            System.out.println("\n\n==== SAXResult ====");

            try {
                exampleSAXResult(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("ContentHandlerToContentHandler")) {
            System.out
            .println("\n\n==== ContentHandlerToContentHandler ====");

            try {
                exampleContentHandlerToContentHandler(foo_xml,
                                                      foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("XMLReader")) {
            System.out.println("\n\n==== XMLReader ====");

            try {
                exampleXMLReader(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("XMLFilter")) {
            System.out.println("\n\n==== XMLFilter ====");

            try {
                exampleXMLFilter(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("XMLFilterChain")) {
            System.out.println("\n\n==== XMLFilterChain ====");

            try {
                exampleXMLFilterChain(foo_xml, foo_xsl,
                                      foo2_xsl, foo3_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("SaxonToSaxon")) {
            System.out.println("\n\n==== SaxonToSaxon ====");

            try {
                exampleSaxonToSaxon(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("SaxonToSaxonNonRoot")) {
            System.out.println("\n\n==== SaxonToSaxonNonRoot ====");

            try {
                exampleSaxonToSaxonNonRoot(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("DOMsubtree")) {
            System.out.println("\n\n==== DOMsubtree ====");

            try {
                exampleDOMsubtree(foo_xml);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("DOMtoDOM")) {
            System.out.println("\n\n==== DOMtoDOM ====");

            try {
                exampleDOMtoDOM(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("DOMtoDOMAlienDoc")) {
            System.out.println("\n\n==== DOMtoDOMAlienDoc ====");

            try {
                exampleDOMtoDOMAlienDoc(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("DOMtoDOMExisting")) {
            System.out.println("\n\n==== DOMtoDOMExisting ====");

            try {
                exampleDOMtoDOMExisting(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("DOMImport")) {
            System.out.println("\n\n==== DOMImport ====");

            try {
                exampleDOMImport(foo_xml);
            } catch (Exception ex) {
                handleException(ex);
            }
        }


        if (test.equals("all") || test.equals("NodeInfo")) {
            System.out.println("\n\n==== NodeInfo ====");

            try {
                exampleNodeInfo(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("SAXtoDOMNewIdentity")) {
            System.out.println("\n\n==== SAXtoDOMNewIdentity ====");

            try {
                exampleSAXtoDOMNewIdentity(foo_xml);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("Param")) {
            System.out.println("\n\n==== Param ====");

            try {
                exampleParam(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("TransformerReuse")) {
            System.out.println("\n\n==== TransformerReuse ====");

            try {
                exampleTransformerReuse(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("OutputProperties")) {
            System.out.println("\n\n==== OutputProperties ====");

            try {
                exampleOutputProperties(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("UseAssociated")) {
            System.out.println("\n\n==== UseAssociated ====");

            try {
                exampleUseAssociated(foo_xml);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("UseEmbedded")) {
            System.out.println("\n\n==== UseEmbedded ====");

            try {
                exampleUseAssociated(embedded_xml);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("ContentHandlertoDOM")) {
            System.out.println("\n\n==== ContentHandlertoDOM ====");

            try {
                exampleContentHandlertoDOM(foo_xml, foo_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("AsSerializer")) {
            System.out.println("\n\n==== AsSerializer ====");

            try {
                exampleAsSerializer(foo_xml);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("serializerWithDOE")) {
            System.out.println("\n\n==== serializerWithDOE ====");

            try {
                serializerWithDOE();
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("NewDOMSerializer")) {
            System.out.println("\n\n==== NewDOMSerializer ====");

            try {
                exampleNewDOMSerializer();
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("UsingURIResolver")) {
            System.out.println("\n\n==== UsingURIResolver ====");

            try {
                exampleUsingURIResolver(foo_xml, text_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("OutputURIResolver")) {
            System.out.println("\n\n==== OutputURIResolver ====");

            try {
                exampleOutputURIResolver(cities_xml, multidoc_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        if (test.equals("all") || test.equals("Extra")) {
            System.out.println("\n\n==== Extra ====");

            try {
                exampleExtra(foo_xml, identity_xsl);
            } catch (Exception ex) {
                handleException(ex);
            }
        }

        System.out.println("\n==== done! ====");
    }

    /**
     * Show the simplest possible transformation from system id
     * to output stream.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleSimple1(String sourceID, String xslID)
            throws TransformerException {

        // Create a transform factory instance.
        TransformerFactory tfactory = TransformerFactory.newInstance();

        // Create a transformer for the stylesheet.
        Transformer transformer =
            tfactory.newTransformer(new StreamSource(xslID));

        // Transform the source XML to System.out.
        transformer.transform(new StreamSource(sourceID),
                              new StreamResult(System.out));
    }

    /**
     * Example that shows XML parsing only (no transformation)
     * @param sourceID file name of the source file
     */

    public static void exampleParseOnly(String sourceID) throws Exception {
        //System.setProperty("javax.xml.parsers.SAXParserFactory",
        //        "net.sf.saxon.aelfred.SAXParserFactoryImpl");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(sourceID, new org.xml.sax.helpers.DefaultHandler());
        System.out.println("\nParsing complete\n");
    }

    /**
     * Show the simplest possible transformation from File
     * to a File.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleSimple2(String sourceID, String xslID)
            throws TransformerException {

        // Create a transform factory instance.
        TransformerFactory tfactory = TransformerFactory.newInstance();

        // Create a transformer for the stylesheet.
        Transformer transformer =
            tfactory.newTransformer(new StreamSource(xslID));

        // Transform the source XML to System.out.
        transformer.transform(new StreamSource(sourceID),
                              new StreamResult(new File("Simple2.out")));

        System.out.println("\nOutput written to exampleSimple2.out\n");
    }

    /**
     * Show simple transformation from input stream to output stream.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleFromStream(String sourceID, String xslID)
            throws TransformerException,
                   FileNotFoundException {

        // Create a transform factory instance.
        TransformerFactory tfactory  = TransformerFactory.newInstance();
        InputStream        xslIS     =
            new BufferedInputStream(new FileInputStream(xslID));
        StreamSource       xslSource = new StreamSource(xslIS);

        // The following line would be necessary if the stylesheet contained
        // an xsl:include or xsl:import with a relative URL
        // xslSource.setSystemId(xslID);

        // Create a transformer for the stylesheet.
        Transformer  transformer = tfactory.newTransformer(xslSource);
        InputStream  xmlIS       =
            new BufferedInputStream(new FileInputStream(sourceID));
        StreamSource xmlSource   = new StreamSource(xmlIS);

        // The following line would be necessary if the source document contained
        // a call on the document() function using a relative URL
        // xmlSource.setSystemId(sourceID);

        // Transform the source XML to System.out.
        transformer.transform(xmlSource, new StreamResult(System.out));
    }

    /**
     * Show simple transformation from reader to output stream.  In general
     * this use case is discouraged, since the XML encoding can not be
     * processed.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleFromReader(String sourceID, String xslID)
            throws TransformerException, FileNotFoundException {

        // Create a transform factory instance.
        TransformerFactory tfactory = TransformerFactory.newInstance();

        // Note that in this case the XML encoding can not be processed!
        Reader       xslReader = new BufferedReader(new FileReader(xslID));
        StreamSource xslSource = new StreamSource(xslReader);

        // Note that if we don't do this, relative URLs can not be resolved correctly!
        xslSource.setSystemId(xslID);

        // Create a transformer for the stylesheet.
        Transformer transformer = tfactory.newTransformer(xslSource);

        // Note that in this case the XML encoding can not be processed!
        Reader       xmlReader = new BufferedReader(new FileReader(sourceID));
        StreamSource xmlSource = new StreamSource(xmlReader);

        // The following line would be needed if the source document contained
        // a relative URL
        // xmlSource.setSystemId(sourceID);

        // Transform the source XML to System.out.
        transformer.transform(xmlSource, new StreamResult(System.out));
    }

    /**
     * Perform a transformation using a compiled stylesheet (a Templates object),
     * using the compiled stylesheet to transform two source files
     * @param sourceID1 file name of the first source file
     * @param sourceID2 file name of the second source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleUseTemplatesObj(
            String sourceID1, String sourceID2, String xslID)
                throws TransformerException {

        TransformerFactory tfactory = TransformerFactory.newInstance();

        // Create a templates object, which is the processed,
        // thread-safe representation of the stylesheet.
        Templates templates = tfactory.newTemplates(new StreamSource(xslID));

        // Illustrate the fact that you can make multiple transformers
        // from the same template.
        Transformer transformer1 = templates.newTransformer();
        Transformer transformer2 = templates.newTransformer();

        System.out.println("\n\n----- transform of " + sourceID1 + " -----");
        transformer1.transform(new StreamSource(sourceID1),
                               new StreamResult(System.out));
        System.out.println("\n\n----- transform of " + sourceID2 + " -----");
        transformer2.transform(new StreamSource(sourceID2),
                               new StreamResult(System.out));
    }

    /**
     * Perform a transformation using a compiled stylesheet (a Templates object)
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public void exampleUseTemplatesHandler(String sourceID, String xslID)
                throws Exception {

        TransformerFactory tfactory = TransformerFactory.newInstance();

        // Does this factory support SAX features?
        if (tfactory.getFeature(SAXSource.FEATURE)) {

            // If so, we can safely cast.
            SAXTransformerFactory stfactory =
                ((SAXTransformerFactory) tfactory);

            // Create a Templates ContentHandler to handle parsing of the
            // stylesheet.
            javax.xml.transform.sax.TemplatesHandler templatesHandler =
                               stfactory.newTemplatesHandler();

            // Create an XMLReader and set its features.
            XMLReader reader = makeXMLReader();
        	reader.setFeature("http://xml.org/sax/features/namespaces", true);
        	reader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);

            // Create a XMLFilter that modifies the stylesheet
            XMLFilter filter = new ModifyStylesheetFilter();
            filter.setParent(reader);

            filter.setContentHandler(templatesHandler);

            // Parse the stylesheet.
            filter.parse(new InputSource(xslID));

            // Get the Templates object (generated during the parsing of the stylesheet)
            // from the TemplatesHandler.
            Templates templates = templatesHandler.getTemplates();

            // do the transformation
            templates.newTransformer().transform(
                    new StreamSource(sourceID), new StreamResult(System.out));

        } else {
            System.out.println("Factory doesn't implement SAXTransformerFactory");
        }

    }

    private class ModifyStylesheetFilter extends XMLFilterImpl {
        public void startDocument () throws SAXException {
            System.err.println("ModifyStylesheetFilter#startDocument");
            super.startDocument();
        }
        public void startElement (String namespaceURI, String localName,
                          String qName, Attributes atts) throws SAXException {
            String lname = (qName.startsWith("xsl:") ? localName : ("XX" + localName));
            super.startElement(namespaceURI, lname, qName, atts);
        }
        public void endElement (String namespaceURI, String localName,
                          String qName) throws SAXException {
            String lname = (qName.startsWith("xsl:") ? localName : ("XX" + localName));
            super.endElement(namespaceURI, lname, qName);
        }
    }

    /**
     * Send output to a user-specified ContentHandler.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */

    public static void exampleSAXResult(
            String sourceID, String xslID)
                throws TransformerException, IOException {

        TransformerFactory tfactory = TransformerFactory.newInstance();

        // Does this factory support SAX features?
        if (tfactory.getFeature(SAXResult.FEATURE)) {

            // Get a transformer in the normal way:
            Transformer transformer =
                tfactory.newTransformer(new StreamSource(xslID));

            // Get the source as a StreamSource
            Reader       xmlReader = new BufferedReader(new FileReader(sourceID));
            StreamSource xmlSource = new StreamSource(xmlReader);

            // Set the result handling to be a serialization to System.out.
            Result result = new SAXResult(new ExampleContentHandler());

            // Do the transformation
            transformer.transform(xmlSource, result);

        } else {
            System.out.println(
                "Can't do exampleSAXResult because tfactory is not a SAXTransformerFactory");
        }
    }

    /**
     * Show the Transformer using SAX events in and SAX events out.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleContentHandlerToContentHandler(
            String sourceID, String xslID)
                throws TransformerException, SAXException,
                       ParserConfigurationException,
                       IOException {

        TransformerFactory tfactory = TransformerFactory.newInstance();

        // Does this factory support SAX features?
        if (tfactory.getFeature(SAXSource.FEATURE)) {

            // If so, we can safely cast.
            SAXTransformerFactory stfactory =
                ((SAXTransformerFactory) tfactory);

            // A TransformerHandler is a ContentHandler that will listen for
            // SAX events, and transform them to the result.
            TransformerHandler handler =
                stfactory.newTransformerHandler(new StreamSource(xslID));

            // Set the result handling to be a serialization to System.out.
            Result result = new SAXResult(new ExampleContentHandler());

            handler.setResult(result);

            // Create a reader, and set it's content handler to be the TransformerHandler.
            XMLReader reader = makeXMLReader();

            reader.setContentHandler(handler);

            // It's a good idea for the parser to send lexical events.
            // The TransformerHandler is also a LexicalHandler.
            reader.setProperty(
                "http://xml.org/sax/properties/lexical-handler", handler);

            // Parse the source XML, and send the parse events to the TransformerHandler.
            reader.parse(sourceID);
        } else {
            System.out.println(
                "Can't do exampleContentHandlerToContentHandler because tfactory is not a SAXTransformerFactory");
        }
    }

    /**
     * Show the Transformer as a SAX2 XMLReader.  An XMLFilter obtained
     * from newXMLFilter should act as a transforming XMLReader if setParent is not
     * called.  Internally, an XMLReader is created as the parent for the XMLFilter.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleXMLReader(String sourceID, String xslID)
            throws TransformerException,
                   SAXException, IOException {

        TransformerFactory tfactory = TransformerFactory.newInstance();

        if (tfactory.getFeature(SAXSource.FEATURE)) {
            XMLReader reader =
                ((SAXTransformerFactory) tfactory)
                    .newXMLFilter(new StreamSource(new File(xslID)));

            reader.setContentHandler(new ExampleContentHandler());
            reader.parse(new InputSource(new File(sourceID).toURL().toString()));
        } else {
            System.out.println("tfactory does not support SAX features!");
        }
    }

    /**
     * Show the Transformer as a simple XMLFilter.  This is pretty similar
     * to exampleXMLReader, except that here the parent XMLReader is created
     * by the caller, instead of automatically within the XMLFilter.  This
     * gives the caller more direct control over the parent reader.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleXMLFilter(String sourceID, String xslID)
            throws TransformerException,
                   SAXException, IOException, ParserConfigurationException {

        TransformerFactory tfactory = TransformerFactory.newInstance();
        XMLReader reader   = makeXMLReader();


        // The transformer will use a SAX parser as it's reader.

        try {
            reader.setFeature(
                "http://xml.org/sax/features/namespace-prefixes", true);
        } catch (SAXException se) {
            System.err.println("SAX Parser doesn't report namespace prefixes!");
            throw se;
        }

        XMLFilter filter =
            ((SAXTransformerFactory) tfactory)
                .newXMLFilter(new StreamSource(new File(xslID)));

        filter.setParent(reader);
        filter.setContentHandler(new ExampleContentHandler());

        // Now, when you call transformer.parse, it will set itself as
        // the content handler for the parser object (it's "parent"), and
        // will then call the parse method on the parser.
        filter.parse(new InputSource(new File(sourceID).toURL().toString()));
    }

    /**
     * This example shows how to chain events from one Transformer
     * to another transformer, using the Transformer as a
     * SAX2 XMLFilter/XMLReader.
     * @param sourceID file name of the source file
     * @param xslID_1 file name of the first stylesheet file
     * @param xslID_2 file name of the second stylesheet file
     * @param xslID_3 file name of the third stylesheet file
     */
    public static void exampleXMLFilterChain(
            String sourceID, String xslID_1, String xslID_2, String xslID_3)
                throws TransformerException, SAXException,
                       IOException, ParserConfigurationException {

        TransformerFactory tfactory     = TransformerFactory.newInstance();

        // If one success, assume all will succeed.
        if (tfactory.getFeature(SAXSource.FEATURE)) {
            SAXTransformerFactory stf    = (SAXTransformerFactory) tfactory;
            XMLReader reader   = makeXMLReader();

            XMLFilter filter1 = stf.newXMLFilter(new StreamSource(new File(xslID_1)));
            XMLFilter filter2 = stf.newXMLFilter(new StreamSource(new File(xslID_2)));
            XMLFilter filter3 = stf.newXMLFilter(new StreamSource(new File(xslID_3)));

            if (null != filter1)    // If one success, assume all were success.
            {

                // transformer1 will use a SAX parser as it's reader.
                filter1.setParent(reader);

                // transformer2 will use transformer1 as it's reader.
                filter2.setParent(filter1);

                // transformer3 will use transformer2 as it's reader.
                filter3.setParent(filter2);
                filter3.setContentHandler(new ExampleContentHandler());

                // filter3.setContentHandler(new org.xml.sax.helpers.DefaultHandler());
                // Now, when you call transformer3 to parse, it will set
                // itself as the ContentHandler for transform2, and
                // call transform2.parse, which will set itself as the
                // content handler for transform1, and call transform1.parse,
                // which will set itself as the content listener for the
                // SAX parser, and call parser.parse(new InputSource(foo_xml)).
                filter3.parse(new InputSource(new File(sourceID).toURL().toString()));
            } else {
                System.out
                    .println("Can't do exampleXMLFilter because "
                             + "tfactory doesn't support newXMLFilter()");
            }
        } else {
            System.out.println("Can't do exampleXMLFilter because "
                               + "tfactory is not a SAXTransformerFactory");
        }
    }

    /**
     * Show how to transform a Saxon tree into another Saxon tree.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     * @return the root node of the result tree
     */
    public static NodeInfo exampleSaxonToSaxon(String sourceID, String xslID)
            throws TransformerException {

        TransformerFactory tfactory = TransformerFactory.newInstance();
        Configuration config = ((TransformerFactoryImpl)tfactory).getConfiguration();
        NodeInfo doc = config.buildDocument(new StreamSource(new File(xslID)));
        Templates templates = tfactory.newTemplates(doc);
        System.err.println("Stylesheet built OK");

        Transformer transformer = templates.newTransformer();
        doc = config.buildDocument(new StreamSource(new File(sourceID)));
        System.err.println("Source document built OK");

        TinyBuilder builder = new TinyBuilder();
        transformer.transform(doc, builder);
        System.err.println("Transformation done OK");

        // Serialize the output so we can see the transformation actually worked
        Transformer serializer = tfactory.newTransformer();
        serializer.transform(builder.getCurrentRoot(), new StreamResult(System.out));

        return builder.getCurrentRoot();

    }

    /**
     * Show how to transform a Saxon tree into another Saxon tree.
     * This uses Saxon interfaces to create the tree. In this example, the start node
     * for the transformation is an element, not the root node. The non-root start
     * node is found using the JAXP XPath interface.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     * @return the root node of the result tree
     */
    public static NodeInfo exampleSaxonToSaxonNonRoot(String sourceID, String xslID)
            throws TransformerException, IOException {

        TransformerFactory tfactory = TransformerFactory.newInstance();
        Configuration config = ((TransformerFactoryImpl)tfactory).getConfiguration();
        NodeInfo sdoc = config.buildDocument(new StreamSource(new File(xslID).toURL().toString()));
        Templates templates = tfactory.newTemplates(sdoc);
        System.err.println("Stylesheet built OK");

        NodeInfo doc = config.buildDocument(new StreamSource(new File(sourceID).toURL().toString()));
        System.err.println("Source document built OK");
        net.sf.saxon.sxpath.XPathEvaluator xpath = new net.sf.saxon.sxpath.XPathEvaluator(config);
        XPathExpression xpe = xpath.createExpression("/*/*[1]");
        NodeInfo start = (NodeInfo)xpe.evaluateSingle(doc);

        Transformer transformer = templates.newTransformer();

        TinyBuilder builder = new TinyBuilder();
        transformer.transform(start, builder);
        System.err.println("Transformation done OK");

        // Serialize the output so we can see the transformation actually worked
        Transformer serializer = tfactory.newTransformer();
        serializer.transform(builder.getCurrentRoot(), new StreamResult(System.out));

        return builder.getCurrentRoot();
    }


    /**
     * Show how to extract a subtree of a DOM by using the identity
     * transformer starting at a non-root elememt of the supplied DOM
     * @param sourceID file name of the source file
     */
    public static void exampleDOMsubtree(String sourceID)
            throws TransformerException,
                   SAXException, IOException, ParserConfigurationException {

        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer transformer = tfactory.newTransformer();

        if (tfactory.getFeature(DOMSource.FEATURE)) {

            DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
                System.err.println("Using DocumentBuilderFactory " + dfactory.getClass());

            dfactory.setNamespaceAware(true);

            DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
                System.err.println("Using DocumentBuilder " + docBuilder.getClass());


            Document doc = docBuilder.parse(
                          new InputSource(new File(sourceID).toURL().toString()));
            Node bar = doc.getDocumentElement().getFirstChild();
            while (bar.getNodeType() != Node.ELEMENT_NODE) {
                bar = bar.getNextSibling();
            }

            System.err.println("Source document built OK");

            DOMSource ds = new DOMSource(bar);
            ds.setSystemId(new File(sourceID).toURL().toString());
            transformer.transform(ds, new StreamResult(System.out));
            System.err.println("Transformation done OK");
        } else {
            throw new org.xml.sax.SAXNotSupportedException(
                "DOM node processing not supported!");
        }
    }


    /**
     * Show how to transform a DOM tree into another DOM tree.
     * This uses the javax.xml.parsers to parse an XML file into a
     * DOM, and create an output DOM. In this example, Saxon uses a
     * third-party DOM as both input and output.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleDOMtoDOM(String sourceID, String xslID)
            throws TransformerException,
                   SAXException, IOException, ParserConfigurationException {

        String factory = getDocumentBuilderFactory();

        if (factory==null) {
            System.err.println("No third-party DOM Builder found");
            return;
        }

        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", factory);

        TransformerFactory tfactory = TransformerFactory.newInstance();

        if (tfactory.getFeature(DOMSource.FEATURE)) {
            Templates templates;

            {
                DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
                System.err.println("Using DocumentBuilderFactory " + dfactory.getClass());

                dfactory.setNamespaceAware(true);

                DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
                System.err.println("Using DocumentBuilder " + docBuilder.getClass());

                Node doc =
                    docBuilder.parse(new InputSource(new File(xslID).toURL().toString()));
                System.err.println("Stylesheet document built OK");
                DOMSource dsource = new DOMSource(doc);

                // If we don't do this, the transformer won't know how to
                // resolve relative URLs in the stylesheet.
                dsource.setSystemId(new File(xslID).toURL().toString());

                templates = tfactory.newTemplates(dsource);
            }

            Transformer transformer = templates.newTransformer();
            DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dfactory.newDocumentBuilder();

            Document doc = docBuilder.parse(
                          new InputSource(new File(sourceID).toURL().toString()));
            Node bar = doc.getDocumentElement().getFirstChild();
            while (bar.getNodeType() != Node.ELEMENT_NODE) {
                bar = bar.getNextSibling();
            }

            System.err.println("Source document built OK");

            DOMSource ds = new DOMSource(bar);
            ds.setSystemId(new File(sourceID).toURL().toString());

            // create a skeleton output document, to which
            // the transformation results will be added

            Document out = docBuilder.newDocument();
            Element extra = out.createElement("extra");
            out.appendChild(extra);


            transformer.transform(ds, new DOMResult(extra));
            System.err.println("Transformation done OK");

            // Serialize the output so we can see the transformation actually worked
            Transformer serializer = tfactory.newTransformer();

            serializer.transform(new DOMSource(out),
                                 new StreamResult(System.out));

        } else {
            throw new org.xml.sax.SAXNotSupportedException(
                "DOM node processing not supported!");
        }
    }

    /**
     * Show how to transform a DOM tree into another DOM tree.
     * This uses the javax.xml.parsers to parse an XML file into a
     * DOM, and create an output DOM. In this example, Saxon uses a
     * third-party DOM as both input and output, and the output is attached
     * directly under the document node.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleDOMtoDOMAlienDoc(String sourceID, String xslID)
            throws TransformerException,
                   SAXException, IOException, ParserConfigurationException {

        String factory = getDocumentBuilderFactory();

        if (factory==null) {
            System.err.println("No third-party DOM Builder found");
            return;
        }

        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", factory);

        TransformerFactory tfactory = TransformerFactory.newInstance();

        if (tfactory.getFeature(DOMSource.FEATURE)) {
            Templates templates;

            {
                DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
                System.err.println("Using DocumentBuilderFactory " + dfactory.getClass());

                dfactory.setNamespaceAware(true);

                DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
                    System.err.println("Using DocumentBuilder " + docBuilder.getClass());

                Node doc =
                    docBuilder.parse(new InputSource(new File(xslID).toURL().toString()));
                System.err.println("Stylesheet document built OK");
                DOMSource dsource = new DOMSource(doc);

                // If we don't do this, the transformer won't know how to
                // resolve relative URLs in the stylesheet.
                dsource.setSystemId(new File(xslID).toURL().toString());

                templates = tfactory.newTemplates(dsource);
            }

            Transformer transformer = templates.newTransformer();
            DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dfactory.newDocumentBuilder();

            Document doc = docBuilder.parse(
                          new InputSource(new File(sourceID).toURL().toString()));
            Node bar = doc.getDocumentElement().getFirstChild();
            while (bar.getNodeType() != Node.ELEMENT_NODE) {
                bar = bar.getNextSibling();
            }

            System.err.println("Source document built OK");

            DOMSource ds = new DOMSource(bar);
            ds.setSystemId(new File(sourceID).toURL().toString());

            // create a skeleton output document, to which
            // the transformation results will be added

            Document out = docBuilder.newDocument();

            transformer.transform(ds, new DOMResult(out));
            System.err.println("Transformation done OK");

            // Serialize the output so we can see the transformation actually worked
            Transformer serializer = tfactory.newTransformer();

            serializer.transform(new DOMSource(out),
                                 new StreamResult(System.out));

        } else {
            throw new org.xml.sax.SAXNotSupportedException(
                "DOM node processing not supported!");
        }
    }

    /**
     * Show how to insert nodes into specific position in an existing DOM tree.
     * This uses the DOMResult.setNextSibling() method introduced in JDK 1.5.
     * This test uses the javax.xml.parsers to parse an XML file into a
     * DOM, and create an output DOM. In this example, Saxon uses a
     * third-party DOM as both input and output, and the output is attached
     * directly under the document node.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleDOMtoDOMExisting(String sourceID, String xslID)
            throws TransformerException,
                   SAXException, IOException, ParserConfigurationException {

        String factory = getDocumentBuilderFactory();

        if (factory==null) {
            System.err.println("No third-party DOM Builder found");
            return;
        }

        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", factory);

        TransformerFactory tfactory = TransformerFactory.newInstance();

        if (tfactory.getFeature(DOMResult.FEATURE)) {
            Templates templates = tfactory.newTemplates(new StreamSource(new File(xslID)));
            Transformer transformer = templates.newTransformer();
            StreamSource input = new StreamSource(new File(sourceID));

            DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dfactory.newDocumentBuilder();

            // create a skeleton output document, to which
            // the transformation results will be added

            Document doc = docBuilder.newDocument();
            Element root = doc.createElementNS("", "doc");
            doc.appendChild(root);
            Element childOne = doc.createElementNS("", "childOne");
            root.appendChild(childOne);
            Element childTwo = doc.createElementNS("", "childTwo");
            root.appendChild(childTwo);

            transformer.transform(input, new DOMResult(root, childTwo));
            System.err.println("Transformation done OK");

            // Serialize the output so we can see the transformation actually worked
            Transformer serializer = tfactory.newTransformer();

            serializer.transform(new DOMSource(doc),
                                 new StreamResult(System.out));

        } else {
            throw new org.xml.sax.SAXNotSupportedException(
                "DOM node processing not supported!");
        }
    }

    /**
     * Get a DocumentBuilderFactory for a third-party DOM
     * @return the name of the chosen DocumentBuilderFactory class
     */

    private static String getDocumentBuilderFactory() {
        // Try the crimson parser
        try {
            Class.forName("org.apache.crimson.jaxp.DocumentBuilderFactoryImpl");
            return "org.apache.crimson.jaxp.DocumentBuilderFactoryImpl";
        }
        catch (Exception e) {
            //
        }

        // Try the Xerces parser

        try {
            Class.forName("org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
            return "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl";
        }
        catch (Exception e) {
            //
        }

        try {
            Class.forName("com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
            return "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl";
        }
        catch (Exception e) {
            //
        }
        return null;

    }

    /**
     * Identity transformation from a SAX Source to a new DOM result.
     * This leaves the XSLT processor to create the result DOM.
     * @param sourceID file name of the source file
     */
    public static void exampleSAXtoDOMNewIdentity(String sourceID)
            throws TransformerException {

        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer transformer = tfactory.newTransformer();
        SAXSource source = new SAXSource(new InputSource(sourceID));
        DOMResult result = new DOMResult();
        transformer.transform(source, result);

        System.err.println("Transformation done OK");

        // Serialize the output so we can see the transformation actually worked
        Transformer serializer = tfactory.newTransformer();

        serializer.transform(new DOMSource(result.getNode()),
                             new StreamResult(System.out));

        int k = result.getNode().getChildNodes().getLength();
        System.err.println("Result root has " + k + " children");

    }

    /**
     * Show how to transform directly from a Saxon NodeInfo object.
     * This example is peculiar to Saxon: it is not pure JAXP.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleNodeInfo(String sourceID, String xslID)
            throws TransformerException {

        TransformerFactory tfactory = TransformerFactory.newInstance();
        Templates templates = tfactory.newTemplates(new StreamSource(xslID));
        Transformer transformer = templates.newTransformer();
        XPathEvaluator xpath = new XPathEvaluator(((TransformerFactoryImpl)tfactory).getConfiguration());
        DocumentInfo doc = (DocumentInfo)xpath.setSource(new SAXSource(new InputSource(sourceID)));
        transformer.transform(doc, new StreamResult(System.out));
    }


    /**
     * This shows how to set a parameter for use by the templates. Use
     * two transformers to show that different parameters may be set
     * on different transformers.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleParam(String sourceID, String xslID)
            throws TransformerException {

        TransformerFactory tfactory     = TransformerFactory.newInstance();
        Templates          templates    =
            tfactory.newTemplates(new StreamSource(new File(xslID)));
        Transformer        transformer1 = templates.newTransformer();
        Transformer        transformer2 = templates.newTransformer();

        transformer1.setParameter("a-param", "hello to you!");
        transformer1.transform(new StreamSource(new File(sourceID)),
                               new StreamResult(System.out));
        System.out.println("\n========= (and again with a different parameter value) ===");
        transformer1.setParameter("a-param", "goodbye to you!");
        transformer1.transform(new StreamSource(new File(sourceID)),
                               new StreamResult(System.out));
        System.out.println("\n========= (and again with a no parameter value) ===");
        transformer2.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer2.transform(new StreamSource(new File(sourceID)),
                               new StreamResult(System.out));
    }

    /**
     * Show the that a transformer can be reused, and show resetting
     * a parameter on the transformer.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleTransformerReuse(String sourceID, String xslID)
            throws TransformerException {

        // Create a transform factory instance.
        TransformerFactory tfactory = TransformerFactory.newInstance();

        // Create a transformer for the stylesheet.
        Transformer transformer =
            tfactory.newTransformer(new StreamSource(new File(xslID)));

        transformer.setParameter("a-param", "hello to you!");

        // Transform the source XML to System.out.
        transformer.transform(new StreamSource(new File(sourceID)),
                              new StreamResult(System.out));
        System.out.println("\n=========\n");
        transformer.setParameter("a-param", "hello to me!");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Transform the source XML to System.out.
        transformer.transform(new StreamSource(new File(sourceID)),
                              new StreamResult(System.out));
    }

    /**
     * Show how to override output properties.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleOutputProperties(String sourceID, String xslID)
            throws TransformerException {

        TransformerFactory tfactory  = TransformerFactory.newInstance();
        Templates          templates =
            tfactory.newTemplates(new StreamSource(new File(xslID)));
        Properties         oprops    = templates.getOutputProperties();
        oprops.setProperty(OutputKeys.INDENT, "yes");

        Transformer transformer = templates.newTransformer();

        transformer.setOutputProperties(oprops);
        transformer.transform(new StreamSource(new File(sourceID)),
                              new StreamResult(System.out));
    }

    /**
     * Show how to get stylesheets that are associated with a given
     * xml document via the xml-stylesheet PI (see http://www.w3.org/TR/xml-stylesheet/).
     * @param sourceID file name of the source file
     */
    public static void exampleUseAssociated(String sourceID)
            throws TransformerException {

        TransformerFactory tfactory = TransformerFactory.newInstance();

        // The DOM tfactory will have it's own way, based on DOM2,
        // of getting associated stylesheets.
        if (tfactory instanceof SAXTransformerFactory) {
            SAXTransformerFactory stf     =
                ((SAXTransformerFactory) tfactory);
            Source                sources =
                stf.getAssociatedStylesheet(new StreamSource(sourceID), null,
                                            null, null);

            if (null != sources) {
                Transformer transformer = tfactory.newTransformer(sources);

                transformer.transform(new StreamSource(sourceID),
                                      new StreamResult(System.out));
            } else {
                System.out.println("Can't find the associated stylesheet!");
            }
        }
    }

    /**
     * Show the Transformer using SAX events in and DOM nodes out.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */
    public static void exampleContentHandlertoDOM(
            String sourceID, String xslID)
                throws TransformerException, SAXException,
                       IOException, ParserConfigurationException {

        TransformerFactory tfactory = TransformerFactory.newInstance();

        // Make sure the transformer factory we obtained supports both
        // DOM and SAX.
        if (tfactory.getFeature(SAXSource.FEATURE)
                && tfactory.getFeature(DOMSource.FEATURE)) {

            // We can now safely cast to a SAXTransformerFactory.
            SAXTransformerFactory sfactory = (SAXTransformerFactory) tfactory;

            // Create an Document node as the root for the output.
            DocumentBuilderFactory dfactory   =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder        docBuilder = dfactory.newDocumentBuilder();
            org.w3c.dom.Document   outNode    = docBuilder.newDocument();

            // Create a ContentHandler that can liston to SAX events
            // and transform the output to DOM nodes.
            TransformerHandler handler =
                sfactory.newTransformerHandler(new StreamSource(xslID));

            handler.setResult(new DOMResult(outNode));

            // Create a reader and set it's ContentHandler to be the
            // transformer.
            XMLReader reader   = makeXMLReader();

            reader.setContentHandler(handler);
            reader.setProperty(
                "http://xml.org/sax/properties/lexical-handler", handler);

            // Send the SAX events from the parser to the transformer,
            // and thus to the DOM tree.
            reader.parse(sourceID);

            // Serialize the node for diagnosis.
            exampleSerializeNode(outNode);
        } else {
            System.out.println(
                "Can't do exampleContentHandlerToDOM because tfactory is not a SAXTransformerFactory");
        }
    }

    /**
     * Show a transformation using a user-written URI Resolver.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */

    public static void exampleUsingURIResolver(String sourceID, String xslID)
            throws TransformerException {

        // Create a transform factory instance.
        TransformerFactory tfactory = TransformerFactory.newInstance();

        // Create a transformer for the stylesheet.
        Transformer transformer =
            tfactory.newTransformer(new StreamSource(xslID));

        // Set the URIResolver
        transformer.setURIResolver(new UserURIResolver(transformer));

        // Transform the source XML to System.out.
        transformer.transform(new StreamSource(sourceID),
                              new StreamResult(System.out));

    }

    /**
    * A sample URIResolver. This handles a URI ending with ".txt". It loads the
    * text file identified by the URI, assuming it is in ISO-8859-1 encoding,
    * into a tree containing a single text node. It returns this
    * result tree to the transformer, exploiting the fact that a Saxon NodeInfo
    * can be used as a Source. If the URI doesn't end with ".txt", it hands over
    * to the standard URI resolver by returning null.
    */

    public static class UserURIResolver implements URIResolver {

        Transformer transformer;

        /**
         * Create a URIResolver
         * @param t the JAXP Transformer
         */
        public UserURIResolver(Transformer t) {
            transformer = t;
        }

        /**
        * Resolve a URI
        * @param base The base URI that should be used. May be null if uri is absolute.
        * @param href The relative or absolute URI. May be an empty string. May contain
        * a fragment identifier starting with "#", which must be the value of an ID attribute
        * in the referenced XML document.
        * @return a Source object representing an XML document
        */

        public Source resolve(String href, String base)
        throws TransformerException {
            if (href.endsWith(".txt")) {
                try {
                    URL url = new URL(new URL(base), href);
                    java.io.InputStream in = url.openConnection().getInputStream();
                    java.io.InputStreamReader reader =
                        new java.io.InputStreamReader(in, "iso-8859-1");

                    // this could be vastly more efficient, but it doesn't matter here

                    StringBuffer sb = new StringBuffer();
                    while (true) {
                        int c = reader.read();
                        if (c<0) break;
                        sb.append((char)c);
                    }
                    net.sf.saxon.value.TextFragmentValue tree =
                        new net.sf.saxon.value.TextFragmentValue(
                            sb.toString(), url.toString());
                    tree.setConfiguration(((Controller)transformer).getConfiguration());
                    return tree;
                } catch (Exception err) {
                    throw new TransformerException(err);
                }
            } else {
                return null;
            }
        }

    } // end of inner class UserURIResolver

    /**
     * Show a transformation using a user-written output URI Resolver.
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */

    public static void exampleOutputURIResolver(String sourceID, String xslID)
            throws TransformerException {

        // Create a transform factory instance.
        TransformerFactory tfactory = TransformerFactory.newInstance();

        // Set the OutputURIResolver
        tfactory.setAttribute("http://saxon.sf.net/feature/outputURIResolver",
                                new UserOutputResolver());
        tfactory.setAttribute("http://saxon.sf.net/feature/allow-external-functions",
                                Boolean.TRUE);     // this is the default
        tfactory.setAttribute("http://saxon.sf.net/feature/timing", Boolean.TRUE);

        // Create a transformer for the stylesheet.
        Transformer transformer =
            tfactory.newTransformer(new StreamSource(xslID));


        // Transform the source XML to System.out.
        transformer.transform(new StreamSource(sourceID),
                              new StreamResult(System.out));

    }

    /**
     * A sample OutputURIResolver. This handles a URI ending with ".out", and causes the
     * specified output to be written to standard output, preceded by "** Start ** and terminated
     * by ** End **. It also handles a URI ending with ".sax", in this case it creates a SAXResult
     * and sends the output there.
    */

    public static class UserOutputResolver implements OutputURIResolver {

        /**
        * Resolve a URI
        * @param base The base URI that should be used. May be null if uri is absolute.
        * @param href The relative or absolute URI. May be an empty string. May contain
        * a fragment identifier starting with "#", which must be the value of an ID attribute
        * in the referenced XML document.
        * @return a Source object representing an XML document
        */

        public Result resolve(String href, String base)
        throws TransformerException {
            if (href.endsWith(".out")) {
                System.out.println("** Start " + href + " **");
                StreamResult res = new StreamResult(System.out);
                res.setSystemId(href);
                return res;
            } else if (href.endsWith(".sax")) {
                // Output to a SAX ContentHandler. Normally we would expect this to be supplied
                // or initialized by the calling application, but this is only a demonstration
                System.out.println("** Start " + href + " **");
                SAXResult res = new SAXResult(new ExampleContentHandler());
                res.setSystemId(href);
                return res;
            } else {
                return null;
            }
        }

        public void close(Result result) throws TransformerException {
            System.out.println("** End " + result.getSystemId() + " **");
        }

    } // end of inner class UserOutputResolver

    /**
     * Serialize a node to System.out.
     * @param node the node to be serialized
     */
    public static void exampleSerializeNode(Node node)
            throws TransformerException {

        TransformerFactory tfactory = TransformerFactory.newInstance();

        // This creates a transformer that does a simple identity transform,
        // and thus can be used for all intents and purposes as a serializer.
        Transformer serializer = tfactory.newTransformer();

        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        serializer.transform(new DOMSource(node),
                             new StreamResult(System.out));
    }

    /**
     * A fuller example showing how the TrAX interface can be used
     * to serialize a DOM tree.
     * @param sourceID file name of the source file
     */
    public static void exampleAsSerializer(String sourceID)
            throws TransformerException,
                   SAXException, IOException, ParserConfigurationException {

        DocumentBuilderFactory dfactory   =
            DocumentBuilderFactory.newInstance();
        DocumentBuilder        docBuilder = dfactory.newDocumentBuilder();
        Node                   doc        =
            docBuilder.parse(new InputSource(sourceID));
        TransformerFactory     tfactory   = TransformerFactory.newInstance();

        // This creates a transformer that does a simple identity transform,
        // and thus can be used for all intents and purposes as a serializer.
        Transformer serializer = tfactory.newTransformer();
        Properties  oprops     = new Properties();

        oprops.setProperty("method", "html");
        if (tfactory instanceof ProfessionalTransformerFactory) {
            oprops.setProperty("{http://saxon.sf.net/}indent-spaces", "2");
        }
        serializer.setOutputProperties(oprops);
        serializer.transform(new DOMSource(doc),
                             new StreamResult(System.out));
    }

    /**
     * An example showing how to create a serializer that can disable output escaping
     */

    public static void serializerWithDOE()
            throws TransformerException,
                   SAXException, IOException, ParserConfigurationException {

        TransformerFactoryImpl tf = new TransformerFactoryImpl();

        // Following is needed to ensure Saxon recognizes the JAXP-defined processing instructions
        tf.setAttribute(FeatureKeys.USE_PI_DISABLE_OUTPUT_ESCAPING, Boolean.TRUE);
        TransformerHandler t = tf.newTransformerHandler();

        // Set some serialization options
        t.getTransformer().setOutputProperty("method", "xml");
        t.getTransformer().setOutputProperty("version", "1.1");
        t.getTransformer().setOutputProperty("encoding", "iso-8859-1");
        t.getTransformer().setOutputProperty("indent", "yes");
        t.setResult(new StreamResult(System.out));

        // Write events to the serializer
        t.startDocument();
        t.startElement("", "out", "out", new AttributesImpl());
        t.characters(new char[]{'a','&','b'}, 0, 3);
        t.processingInstruction(Result.PI_DISABLE_OUTPUT_ESCAPING, "");
        t.characters(new char[]{'<','M','/', '>'}, 0, 4);
        t.processingInstruction(Result.PI_ENABLE_OUTPUT_ESCAPING, "");
        t.characters(new char[]{'y','&','z'}, 0, 3);
        t.endElement("", "out", "out");
        t.endDocument();
    }


    /**
     * An example showing how to serialize a program-constructed DOM tree.
     */

    public static void exampleNewDOMSerializer()
            throws TransformerException, ParserConfigurationException {

        DocumentBuilderFactory dfactory   =
            DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
        org.w3c.dom.Document doc    = docBuilder.newDocument();
        org.w3c.dom.Element docElement = doc.createElementNS("nsx.uri", "x");
        doc.appendChild(docElement);

        TransformerFactory     tfactory   = TransformerFactory.newInstance();

        // This creates a transformer that does a simple identity transform,
        // and thus can be used for all intents and purposes as a serializer.
        Transformer serializer = tfactory.newTransformer();
        Properties  oprops     = new Properties();

        oprops.setProperty("method", "xml");
        oprops.setProperty("indent", "yes");
        serializer.setOutputProperties(oprops);
        serializer.transform(new DOMSource(doc),
                             new StreamResult(System.out));
    }

    /**
     * Test to demonstrate that a SAXON tree can be imported into a third-party DOM implementation
     * @param sourceID file name of the source file
     */

    public static void exampleDOMImport(String sourceID)
            throws TransformerException,
                   SAXException, ParserConfigurationException {

        String factory = getDocumentBuilderFactory();

        if (factory==null) {
            System.err.println("No third-party DOM Builder found");
            return;
        }

        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", factory);

        TransformerFactory tfactory = TransformerFactory.newInstance();

        if (tfactory.getFeature(DOMSource.FEATURE)) {
            DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
            System.err.println("Using DocumentBuilderFactory " + dfactory.getClass());

            dfactory.setNamespaceAware(true);

            DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
            System.err.println("Using DocumentBuilder " + docBuilder.getClass());

            Document doc = docBuilder.newDocument();

            XPathEvaluator xpath = new XPathEvaluator(((TransformerFactoryImpl)tfactory).getConfiguration());
            DocumentInfo saxonDoc = (DocumentInfo)xpath.setSource(new SAXSource(new InputSource(sourceID)));
            NodeInfo top = (NodeInfo)saxonDoc.iterateAxis(Axis.CHILD, NodeKindTest.ELEMENT).next();

            Node imported = doc.importNode(NodeOverNodeInfo.wrap(top), true);

            // Serialize the output so we can see the import actually worked
            Transformer serializer = tfactory.newTransformer();

            serializer.transform(new DOMSource(imported),
                                 new StreamResult(System.out));


        } else {
            throw new org.xml.sax.SAXNotSupportedException(
                "DOM node processing not supported!");
        }
    }

    /**
     * Placeholder to add and test your own examples
     * @param sourceID file name of the source file
     * @param xslID file name of the stylesheet file
     */

    public static void exampleExtra(String sourceID, String xslID)
            throws TransformerException {

        // Create a transform factory instance.
        TransformerFactory tfactory = TransformerFactory.newInstance();

         // Create a transformer for the stylesheet.
        Transformer transformer =
            tfactory.newTransformer(new StreamSource(xslID));

        // Transform the source XML to System.out.
        transformer.transform(new StreamSource(sourceID),
                              new StreamResult(System.out));

    }



    private static void handleException(Exception ex) {

        System.out.println("EXCEPTION: " + ex);
        ex.printStackTrace();

        if (ex instanceof TransformerConfigurationException) {
            System.out.println();
            System.out.println("Test failed");

            Throwable ex1 =
                ((TransformerConfigurationException) ex).getException();

            if (ex1!=null) {    // added by MHK
                ex1.printStackTrace();

                if (ex1 instanceof SAXException) {
                    Exception ex2 = ((SAXException) ex1).getException();

                    System.out.println("Internal sub-exception: ");
                    ex2.printStackTrace();
                }
            }
        }
    }

    /**
     * Make an XMLReader (a SAX Parser)
     * @return the constructed XMLReader
    */

    private static XMLReader makeXMLReader() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newSAXParser().getXMLReader();
    }
}
