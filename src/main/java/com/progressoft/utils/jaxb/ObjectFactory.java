
package com.progressoft.utils.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.progressoft.utils package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Labels_QNAME = new QName("", "Labels");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.progressoft.utils
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LabelsType }
     * 
     */
    public LabelsType createLabelsType() {
        return new LabelsType();
    }

    /**
     * Create an instance of {@link LabelType }
     * 
     */
    public LabelType createLabelType() {
        return new LabelType();
    }

    /**
     * Create an instance of {@link LabelDetailType }
     * 
     */
    public LabelDetailType createLabelDetailType() {
        return new LabelDetailType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LabelsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Labels")
    public JAXBElement<LabelsType> createLabels(LabelsType value) {
        return new JAXBElement<LabelsType>(_Labels_QNAME, LabelsType.class, null, value);
    }

}
