package com.progressoft.utils;

import com.progressoft.utils.utils.XmlLabelReplacer;
import org.junit.Test;

import java.nio.file.FileSystems;

public class ApplicationLabelReplacerTest {

    @Test
    public void replaceFile() {
        String currentPath = FileSystems.getDefault().getPath("target/test-classes").toAbsolutePath().toString();
        String sourceRootDir = currentPath + "/source";
        String destinationRootDir = currentPath + "/destination";
        XmlLabelReplacer.replace(sourceRootDir, destinationRootDir);
    }


}
