package com.mindsnacks.markdroid;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

/** Created by Tony Cosentini Date: 11/26/13 Time: 4:50 PM */
public class Markdroid {
  public void convertImageFile(File inputImageFile, File outputResourceDirectory) {
    File drawableDirectory = new File(outputResourceDirectory, "drawable");
    drawableDirectory.mkdirs();

    System.out.println(String.format("Copying drawable: %s to %s", inputImageFile.getName(), drawableDirectory.getAbsolutePath()));

    try {
      FileUtils.copyFileToDirectory(inputImageFile, drawableDirectory);
    } catch (IOException e) {
      throw new RuntimeException("Error copying drawable.", e);
    }
  }

  public void convertMarkdownFile(File inputMarkdownFile, File outputResourceDirectory) {
    File layoutDirectory = new File(outputResourceDirectory, "layout");
    layoutDirectory.mkdirs();

    System.out.println(String.format("Converting Markdown: %s to %s", inputMarkdownFile.getName(), layoutDirectory.getAbsolutePath()));

    String markdown;

    try {
      markdown = Files.toString(inputMarkdownFile, Charsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException("Error reading Markdown file.", e);
    }

    String xml = xmlForMarkdown(markdown);

    String fileNameWithOutExt = FilenameUtils.removeExtension(inputMarkdownFile.getName());
    File layoutXMLFile = new File(layoutDirectory, String.format("%s.xml", fileNameWithOutExt));

    try {
      Files.write(xml, layoutXMLFile, Charsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException("Error writing Android XML layout file.", e);
    }
  }

  public String xmlForMarkdown(String markdown) {
    PegDownProcessor pegDownProcessor = new PegDownProcessor();
    RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

    AndroidMarkdownVisitor androidMarkdownVisitor = new AndroidMarkdownVisitor();
    androidMarkdownVisitor.visit(rootNode);

    return androidMarkdownVisitor.render();
  }
}