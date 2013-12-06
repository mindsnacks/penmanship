package com.mindsnacks.markdroid.gradle

import com.mindsnacks.markdroid.Markdroid
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by Tony Cosentini Date: 12/5/13 Time: 4:04 PM
 */
public class ConvertMarkdownDirectoryToAndroidResourcesDirectoryTask extends DefaultTask {
  def inputMarkdownDirectory
  def outputResourcesDirectory

  File getInputMarkdownDirectory() {
    project.file(inputMarkdownDirectory)
  }

  File getOutputResourcesDirectory() {
    project.file(outputResourcesDirectory)
  }

  @TaskAction
  def convert() {
    System.out.println("Converting!");

    Markdroid markdroid = new Markdroid();

    markdownParentDirectory = getInputMarkdownDirectory()
    outputDirectory = getOutputResourcesDirectory()

    System.out.println("Markdown parent directory: " + markdownParentDirectory.absolutePath);
    System.out.println("Output directory: " + outputDirectory.absolutePath);

    String[] directories = markdownParentDirectory.list(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return new File(dir, name).isDirectory();
      }
    });

    for (String markdownDirectoryString : directories) {
      markdownDirectory = new File(markdownParentDirectory, markdownDirectoryString);
      markdroid.convertMarkdownDirectoryToAndroidResources(markdownDirectory, outputDirectory);
    }
  }
}
