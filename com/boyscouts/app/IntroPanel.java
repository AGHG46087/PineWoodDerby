/*
 * @author: Owner 
 * Package: com.boyscouts.app 
 * File Name: IntroPanel.java
 */
package com.boyscouts.app;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import com.boyscouts.domain.Log;

/**
 * author: Owner 
 * Package: com.boyscouts.app 
 * File Name: IntroPanel.java 
 * Type Name: IntroPanel 
 * Description: The Intro Panel is a panel that will display a Screen using HTML. 
 * NOTE: If the Image needs to change make a change in the HTML Page as specified 
 * in the properties file.
 */

public class IntroPanel extends JPanel
{

  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3689634696456058672L;

  /**
   * Field <code>html</code>: JEditorPane
   * 
   * @uml.property name="html"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JEditorPane html = null;

  /**
   * Field <code>mainApp</code>: RaceARama
   * 
   * @uml.property name="mainApp"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RaceARama mainApp = null;

  /**
   * Constructor for IntroPanel.
   */
  public IntroPanel( RaceARama mainApp )
  {
    this.mainApp = mainApp;
    this.setLayout(new BorderLayout());
    String urlPath = getString("IntroPanel.url.path.name");
    setPath(urlPath);
  }
  /**
   * Method debug.
   * @param s
   */
  protected void debug( String s )
  {
    s = "IntroPanel: " + s;
    Log.debug(s);
  }
  /**
   * Method getString. Method returns a String from a Specified Key
   * @param key
   * @return String
   */
  protected String getString( String key )
  {
    debug("getString(" + key + ") - retrieving Key...");
    String value = null;
    if (mainApp != null)
    {
      value = mainApp.getString(key);
    }
    if (value == null)
    {
      value = "Could not find resource: " + key + "  ";
    }
    debug("getString(" + key + ") - value ==> " + value);
    debug("getString(" + key + ") - retrieving Key...Done");
    return value;
  }
  /**
   * Method setPath. Sets the Path of the HTML File URL
   * @param path
   */
  public void setPath( String path )
  {
    try
    {
      URL url = null;
      try
      {
        url = getClass().getResource(path);
      }
      catch (Exception e)
      {
        Log.logError("Failed to open " + path);
        url = null;
      }
      if (url != null)
      {
        html = new JEditorPane();
        html.setPage(url);
        html.setEditable(false);
        html.addHyperlinkListener(createHyperLinkListener());
        JScrollPane scroller = new JScrollPane();
        JViewport vp = scroller.getViewport();
        vp.add(html);
        this.add(scroller, BorderLayout.CENTER);
        vp.setSize(mainApp.getSize());
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      }
    }
    catch (MalformedURLException e)
    {
      System.out.println("Malformed URL: " + e);
    }
    catch (IOException e)
    {
      System.out.println("IOException: " + e);
    }
  }
  /**
   * Method createHyperLinkListener. Adds a Listener for any Hyperlinks on the
   * HTML Page
   * @return HyperlinkListener
   */
  public HyperlinkListener createHyperLinkListener()
  {
    return new HyperlinkListener() {
      {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
        {
          if (e instanceof HTMLFrameHyperlinkEvent)
          {
            ((HTMLDocument) html.getDocument()).processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent) e);
          }
          else
          {
            try
            {
              html.setPage(e.getURL());
            }
            catch (IOException ioe)
            {
              System.out.println("IOE: " + ioe);
            }
          }
        }
      }
    };
  }
}
