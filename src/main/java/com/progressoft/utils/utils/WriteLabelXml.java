package com.progressoft.utils.utils;

import com.progressoft.utils.jaxb.LabelsType;

import java.io.OutputStream;

interface WriteLabelXml {
    void write(LabelsType labelsType, OutputStream outputStream);
}
