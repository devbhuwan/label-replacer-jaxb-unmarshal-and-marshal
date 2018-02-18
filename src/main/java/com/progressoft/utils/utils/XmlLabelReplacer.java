package com.progressoft.utils.utils;

import com.progressoft.utils.jaxb.LabelType;
import com.progressoft.utils.jaxb.LabelsType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Slf4j
public class XmlLabelReplacer {

    public static void replace(final String srcRootDir, final String destRootDir) {
        findAllLabelFiles(srcRootDir)
                .forEach(file -> {
                    String xmlSubPath = file.getAbsolutePath().replace(srcRootDir, "");
                    String destXmlPath = destRootDir + xmlSubPath;
                    if (!Files.exists(Paths.get(destXmlPath))) {
                        copySourceIfNotExistInDestination(file, destXmlPath);
                        return;
                    }
                    replaceLabelsFromSourceToDestination(file, destXmlPath);
                    writeToDestination(destXmlPath, replaceLabelsFromSourceToDestination(file, destXmlPath));
                });
    }

    private static LabelsType replaceLabelsFromSourceToDestination(File file, String destXmlPath) {
        LabelsType sourceLabelsType = new JaxbParseLabelXml()
                .parse(file);
        LabelsType destLabelTypes = new JaxbParseLabelXml()
                .parse(new File(destXmlPath));

        sourceLabelsType.getLabel().forEach(sourceLabelType -> {
            log.info("labelKeyName = [{}]", sourceLabelType.getName());
            List<LabelType> matchedLabelType = destLabelTypes.getLabel()
                    .stream()
                    .filter(destinationLabelType -> isLabelExistInDestination(sourceLabelType, destinationLabelType)).collect(Collectors.toList());
            if (matchedLabelType.size() > 0) {
                for (LabelType destLabelType : matchedLabelType) {
                    if (sourceLabelType.getLabelDetail().size() > 1) {
                        destLabelType.getLabelDetail().set(0, sourceLabelType.getLabelDetail().get(0));
                        if (destLabelType.getLabelDetail().size() > 1) {
                            destLabelType.getLabelDetail().set(1, sourceLabelType.getLabelDetail().get(1));
                        } else
                            destLabelType.getLabelDetail().add(sourceLabelType.getLabelDetail().get(1));
                    }
                }
            } else {
                destLabelTypes.getLabel().add(sourceLabelType);
            }
        });
        return destLabelTypes;
    }

    private static boolean isLabelExistInDestination(LabelType sourceLabelType, LabelType destinationLabelType) {
        return resolveLabelName(sourceLabelType.getName()).equals(resolveLabelName(destinationLabelType.getName()));
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
