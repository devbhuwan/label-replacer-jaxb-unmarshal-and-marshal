#!/usr/bin/env bash

sourceRootDir=/home/developerbhuwan/progressoft/replacer/labels
destinationRootDir=/home/developerbhuwan/progressoft/govpay-5.0-SNAPSHOT/WEB-INF/dictionary/product/labels

echo "Your [sourceRootDir=${sourceRootDir}]"
echo "Your [destinationRootDir=${destinationRootDir}]"

java -jar labels-replacer-1.0.jar ${sourceRootDir} ${destinationRootDir}