package com.progressoft.utils.utils;

import com.progressoft.utils.jaxb.LabelType;
import com.progressoft.utils.jaxb.LabelsType;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import static java.util.Arrays.stream;

public class XmlLabelReplacer {

    public static void replace(String sourceRootDir, String destinationRootDir) {
        findAllLabelFiles(sourceRootDir)
                .forEach(file -> {
                    String xmlSubPath = file.getAbsolutePath().replace(sourceRootDir, "");
                    String destinationXmlFilePath = destinationRootDir + xmlSubPath;
                    if (!Files.exists(Paths.get(destinationXmlFilePath))) {
                        copySourceIfNotExistInDestination(file, destinationXmlFilePath);
                        return;
                    }
                    replaceLabelsFromSourceToDestination(file, destinationXmlFilePath);
                    writeToDestination(destinationXmlFilePath, replaceLabelsFromSourceToDestination(file, destinationXmlFilePath));
                });
    }

    private static LabelsType replaceLabelsFromSourceToDestination(File file, String destinationXmlFilePath) {
        LabelsType sourceLabelsType = new JaxbParseLabelXml()
                .parse(file);
        LabelsType destinationLabelsType = new JaxbParseLabelXml()
                .parse(new File(destinationXmlFilePath));

        sourceLabelsType.getLabel().forEach(sourceLabelType ->
                destinationLabelsType.getLabel()
                        .stream()
                        .filter(destinationLabelType -> isLabelExistInDestination(sourceLabelType, destinationLabelType))
                        .findFirst()
                        .ifPresent(destLabelType -> {
                            destLabelType.getLabelDetail().set(0, sourceLabelType.getLabelDetail().get(0));
                            destLabelType.getLabelDetail().set(1, sourceLabelType.getLabelDetail().get(1));
                        }));
        return destinationLabelsType;
    }

    private static boolean isLabelExistInDestination(LabelType sourceLabelType, LabelType destinationLabelType) {
        String labelName = resolveLabelName(destinationLabelType.getName());
        return sourceLabelType.getName().startsWith(labelName) || sourceLabelType.getName().equals(labelName);
    }

    private static String resolveLabelName(String name) {
        String[] strings = name.split("_");
        if (strings.length > 1)
            name = stream(strings, 0, strings.length - 1).reduce("", String::concat);
        return name;
    }

    private static void copySourceIfNotExistInDestination(File file, String destinationXmlFilePath) {
        if (!Files.exists(Paths.get(destinationXmlFilePath))) {
            try {
                FileUtils.copyFile(file, new File(destinationXmlFilePath));
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private static void writeToDestination(String destinationXmlFilePath, LabelsType destinationLabelsType) {
        try {
            new JaxbWriteLabelXml().write(destinationLabelsType, new FileOutputStream(destinationXmlFilePath));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @NotNull
    private static Collection<File> findAllLabelFiles(String sourceRootDir) {
        return FileUtils
                .listFiles(new File(sourceRootDir), new String[]{"xml"}, true);
    }
}
