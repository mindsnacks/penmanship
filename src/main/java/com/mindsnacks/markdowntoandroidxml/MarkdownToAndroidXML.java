package com.mindsnacks.markdowntoandroidxml;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

/** Created by Tony Cosentini Date: 11/26/13 Time: 4:50 PM */
public class MarkdownToAndroidXML {
  public static void main(String[] args) {
    MarkdownToAndroidXML markdownToAndroidXML = new MarkdownToAndroidXML();

    File inputDirectory = new File("/Users/tonyc/Desktop/MindSnacks-Android/MindSnacks-Android/reference/1");
    File outputResourceDirectory = new File("/Users/tonyc/Desktop/res");

    markdownToAndroidXML.convertMarkdownDirectoryToAndroidResources(inputDirectory, outputResourceDirectory);
  }

  public void convertMarkdownDirectoryToAndroidResources(File inputMarkdownDirectory, File outputResourceDirectory) {
    if (!inputMarkdownDirectory.exists() || !inputMarkdownDirectory.isDirectory()) {
      throw new RuntimeException("Input directory does not exist or is not a directory.");
    }

    File markdownFile = new File(inputMarkdownDirectory, "index.md");
    if (!markdownFile.exists() || markdownFile.isDirectory()) {
      throw new RuntimeException("index.md file does not exist or is a directory.");
    }

    outputResourceDirectory.mkdirs();

    File layoutDirectory = new File(outputResourceDirectory, "layout");
    layoutDirectory.mkdirs();

    String markdown;

    try {
      markdown = FileUtils.readFileToString(markdownFile);
    } catch (IOException e) {
      throw new RuntimeException("Error reading Markdown file.", e);
    }

    PegDownProcessor pegDownProcessor = new PegDownProcessor();
    RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

    AndroidMarkdownVisitor androidMarkdownVisitor = new AndroidMarkdownVisitor();
    androidMarkdownVisitor.visit(rootNode);

    File layoutXMLFile = new File(layoutDirectory, String.format("%s.xml", markdownFile.getParentFile().getName()));
    try {
      FileUtils.write(layoutXMLFile, androidMarkdownVisitor.printer.getString());
    } catch (IOException e) {
      throw new RuntimeException("Error writing Android XML layout file.", e);
    }
  }
}