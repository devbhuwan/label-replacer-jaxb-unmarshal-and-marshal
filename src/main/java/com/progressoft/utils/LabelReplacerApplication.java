package com.progressoft.utils;

import com.progressoft.utils.utils.XmlLabelReplacer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

@SpringBootApplication
public class LabelReplacerApplication {

    public static void main(String[] args) {
        Assert.isTrue(args.length == 2, "[sourceRootDir and destinationRootDir is required in parameter] \n[eg. java -jar zzz.jar /home/src/dictionary /home/dest/dictionary]");
        SpringApplication.run(LabelReplacerApplication.class, args);
        XmlLabelReplacer.replace(args[0], args[1]);
    }

}
