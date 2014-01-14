package com.mindsnacks.markdroid.gradle
import com.mindsnacks.markdroid.Markdroid
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskInputs
import org.gradle.api.tasks.TaskOutputs
/**
 * Created by Tony Cosentini Date: 12/5/13 Time: 4:04 PM
 */
public class ConvertMarkdownDirectoryToAndroidResourcesDirectoryTask extends DefaultTask {
  @TaskAction
  def convert() {
    Markdroid markdroid = new Markdroid();

    TaskInputs inputs = getInputs();
    if (!inputs.hasInputs) {
      throw new RuntimeException("You must set inputs for Markdroid.");
    }

    TaskOutputs outputs = getOutputs();
    if (!outputs.hasOutput) {
      throw new RuntimeException("You must set output directory for Markdroid.");
    }

    def outputDirectory = outputs.getFiles().getSingleFile();
    FileUtils.deleteDirectory(outputDirectory);

    def markdownFileExtensions = ["md", "markdown", "text"];
    def imageFileExtensions = ["jpg", "png", "gif", "bmp", "webp"];

    for (File file : getInputs().getFiles().getFiles()) {
      def extension = FilenameUtils.getExtension(file.name).toLowerCase();
      if (markdownFileExtensions.contains(extension)) {
        markdroid.convertMarkdownFile(file, outputDirectory);
      } else if (imageFileExtensions.contains(extension)) {
        markdroid.convertImageFile(file, outputDirectory);
      } else {
        throw new RuntimeException("Unknown file type: " + file.name);
      }
    }
  }
}
