package com.mindsnacks.penmanship.test.elements;

import com.mindsnacks.penmanship.AndroidXMLConstants;
import com.mindsnacks.penmanship.AndroidXMLNode;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by tonyc on 1/20/14.
 */
public class LinkTest extends ElementTest {
  private void testLink(String href) {
    testLink(href, null, null, null);
  }

  private void testLink(String href, String customNamespace, String customScheme, String targetURI) {
    String linkMarkdown = String.format("[Test Link](%s)", href);
    AndroidXMLNode rootNode = convertMarkdownToAndroidNodes(linkMarkdown, customScheme, customNamespace);

    URI uri;
    try {
      if (targetURI != null) {
        uri = new URI(targetURI);
      } else {
        uri = new URI(href);
      }
    } catch (URISyntaxException e) {
      throw new RuntimeException("Error generating URI.", e);
    }

    AndroidXMLNode linkNode = rootNode.getChildren().get(0);

    Assert.assertEquals(AndroidXMLConstants.TEXT_VIEW, linkNode.getName());

    String targetText = String.format("&lt;a href=&quot;%s&quot;&gt;Test Link&lt;/a&gt;", uri.toString());
    assertEquals(targetText, linkNode.getAttributes().get(AndroidXMLConstants.TEXT));
  }

  @Test
  public void customSchemeLinksShouldWork() {
    testLink("penmanship://link");
  }

  @Test
  public void customSchemeLinksWithNamespacesShouldWork() {
    testLink("penmanship://link/with/path?query=true", "custom_namespace", "penmanship", "penmanship://link/with/custom_namespace_path?query=true");
  }

  @Test
  public void linksWithPathsShouldWork() {
    testLink("http://developer.android.com/develop/index.html");
  }
}
