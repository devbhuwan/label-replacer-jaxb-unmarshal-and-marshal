package com.progressoft.utils.utils;

import com.progressoft.utils.jaxb.LabelsType;
import com.progressoft.utils.jaxb.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import java.io.File;

class JaxbParseLabelXml implements ParseLabelXml {

    public LabelsType parse(File file) {
        try {
            Object unMarshalObject = JAXBContext.newInstance(ObjectFactory.class)
                    .createUnmarshaller()
                    .unmarshal(file);
            return ((JAXBElement<LabelsType>) unMarshalObject).getValue();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
