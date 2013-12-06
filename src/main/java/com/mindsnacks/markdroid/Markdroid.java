package com.mindsnacks.markdroid;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;
import com.google.common.io.*;
import com.google.common.base.*;

/** Created by Tony Cosentini Date: 11/26/13 Time: 4:50 PM */
public class Markdroid {
  public void convertMarkdownDirectoryToAndroidResources(File inputMarkdownDirectory, File outputResourceDirectory) {
    System.out.println("Going to convert!");

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
      markdown = Files.toString(markdownFile, Charsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException("Error reading Markdown file.", e);
    }

    PegDownProcessor pegDownProcessor = new PegDownProcessor();
    RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

    AndroidMarkdownVisitor androidMarkdownVisitor = new AndroidMarkdownVisitor();
    androidMarkdownVisitor.visit(rootNode);

    File layoutXMLFile = new File(layoutDirectory, String.format("%s.xml", markdownFile.getParentFile().getName()));
    try {
      Files.write(androidMarkdownVisitor.printer.getString(), layoutXMLFile, Charsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException("Error writing Android XML layout file.", e);
    }
  }
}