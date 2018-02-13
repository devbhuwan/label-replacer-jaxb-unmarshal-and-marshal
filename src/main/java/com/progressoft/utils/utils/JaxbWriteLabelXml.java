package com.progressoft.utils.utils;

import com.progressoft.utils.jaxb.LabelsType;
import com.progressoft.utils.jaxb.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;

class JaxbWriteLabelXml implements WriteLabelXml {

    @Override
    public void write(LabelsType labelsType, OutputStream outputStream) {
        try {
            Marshaller marshaller = JAXBContext.newInstance(ObjectFactory.class)
                    .createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller
                    .marshal(new ObjectFactory().createLabels(labelsType), outputStream);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}
    