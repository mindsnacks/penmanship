# Markdroid

A simple Gradle plug-in that makes it easy to convert Markdown documents into native Android layout XML files.

Useful for apps that require rich content and an easy way to maintain.

## Build Process Integration
Add Markdroid to your buildscript depdencies and apply the plug-in:

```groovy
buildscript {
  repositories {
    mavenCentral()
  }
  
  dependencies {
    classpath 'com.android.tools.build:gradle:0.7+'
    classpath 'com.mindsnacks:markdroid:CURRENT-VERSION'
  }
}

apply plugin: 'markdroid'
```

Then create a task to convert your Markdown files:

```groovy
import com.mindsnacks.markdroid.gradle.ConvertMarkdownDirectoryToAndroidResourcesDirectoryTask
import com.android.build.gradle.tasks.ProcessAndroidResources

task generateMarkdownResources(type: ConvertMarkdownDirectoryToAndroidResourcesDirectoryTask) {
  def inputTree = file('path/to/directory/containing_only_your_markdown/and_images')
  inputs.dir inputTree
  outputs.dir file("markdroid_gen/res")
}

tasks.withType(ProcessAndroidResources) { processResourcesTask ->
  processResourcesTask.dependsOn generateMarkdownResources
}
```

Finally, just add your generated XML directory to your source set:

```groovy
android {
  sourceSets {
    main {
      res.srcDir 'mardroid_gen/res'
    }
  }
}
```

## App Integration

Since there is not one super common way of using the generated XML, it's up to you to inflate the layouts and integrate them into your app. Below are some integration tips for loading and managaing the Markdown content. I'm planning on extracting these into another library that your app can use, but for now it needs to be handled manually.

### Some App Integration Tips

You can get the AAPT generated layout ID for a filename String:

```java
static int getResourceId(final String layoutFilename, final Context context) {
  return context.getResources().getIdentifier(layoutFilename, "layout", context.getPackageName());
}
```

Once the layout is loaded, you can iterate through all of the TextView using this useful method:

```java
public List<TextView> getTextViews(ViewGroup container) {
  List<TextView> textViews = new ArrayList<TextView>();

  for (int i = 0; i < container.getChildCount(); i++) {
    View v = container.getChildAt(i);
    if (v instanceof TextView) {
      textViews.add((TextView) container.getChildAt(i));
    } else if (v instanceof ViewGroup) {
      textViews.addAll(getTextViews((ViewGroup) v));
    }
  }

  return textViews;
}

``` 

This makes it easy to enable rich text (and set any custom fonts, or perform any other configuration you'd like) in each TextView like so:

```java
private void configureTextViews(ViewGroup markdownContainer) {
  for (TextView textView : getTextViews(markdownContainer)) {
    textView.setMovementMethod(LinkMovementMethod.getInstance());
    textView.setText(Html.fromHtml(textView.getText().toString()));
  }
}
```

## Markdown Support
Markdown support is still somewhat limited. The following features work:

1. Text formatting (bold and italics)
2. Headings
3. Blockquotes
4. Unordered and ordered lists
5. Images (only local images, can't be inline with text)
6. Links

