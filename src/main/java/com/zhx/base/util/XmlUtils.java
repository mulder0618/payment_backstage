package com.zhx.base.util;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtils {

    public static String xmlChangeString(String fileName){
        try {
            SAXReader saxReader = new SAXReader();
            Document tempDocument = saxReader.read(XmlUtils.class.getClassLoader().getResourceAsStream(fileName));
            return tempDocument.asXML();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Document strChangeXML(String str) throws IOException, DocumentException {
        String xmlStr = str;
        Document document = DocumentHelper.parseText(xmlStr);
        return document;
    }


    /**
     * 通过xml标签获取值
     * @param tagName
     * @param xml
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static String findTagVal(String tagName,String xml) throws IOException, DocumentException {
        int tagIndex = xml.indexOf(tagName);
        if(tagIndex==-1){
            return null;
        }
        else {
            String startTag = "<"+tagName+">";
            String endTag = "</"+tagName+">";
            int tagLength = tagName.length()+2;
            String result = xml.substring(xml.lastIndexOf(startTag)+tagLength,xml.indexOf(endTag));
            return result;
        }
    }
}

