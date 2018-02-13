package com.progressoft.utils.utils;

import com.progressoft.utils.jaxb.LabelsType;

import java.io.File;

interface ParseLabelXml {
    LabelsType parse(File file);
}
