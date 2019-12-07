/*
 * @author: Owner 
 * date: Dec 27, 2003 
 * Package: examples.awt.graphics.linegraph
 * File Name: TrackGraph.java
 */
package com.boyscouts.util.track;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * author:      hgrein<BR>
 * date:        Jun 7, 2004<BR>
 * Package:     com.boyscouts.util.track<BR>
 * File Name:   TrackGraph.java<BR>
 * Type Name:   TrackGraph<BR>
 * Description: A Panel that displays the Graph of the Track, Notice that this class is Package Scope. 
 * The class will draw the Track as specified by the User.  If there is an error in the Graqph it 
 * will show how the described the Track for the actual height and length as well as the relative 
 * Heights and lengths, rendering along with it the calcualted points.
 */

class TrackGraph extends JPanel
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3258128068106270776L;
  /** Field <code>trackX</code> : int[] */
  int[] trackX = null;
  /** Field <code>trackY</code> : int[] */
  int[] trackY = null;
  int[] relativeY = null;
  /** Field <code>trackLengthInches</code> : double */
  private double trackLengthInches = -1;
  /** Field <code>TITLE</code> : String */
  private static final String TITLE = "Track Configuration";
  /** Field <code>LINES</code> : int */
  private static final int LINES = 1;
  /** Field <code>LINEWIDTH</code> : int */
  private static final int LINEWIDTH = 2;
  /** Field <code>BORDER_LEFT</code> : int */
  private static final int BORDER_LEFT = 30;
  /** Field <code>BORDER_RIGHT</code> : int */
  private static final int BORDER_RIGHT = 20;
  /** Field <code>BORDER_TOP</code> : int */
  private static final int BORDER_TOP = 20;
  /** Field <code>BORDER_BOTTOM</code> : int */
  private static final int BORDER_BOTTOM = 20; // borderbase
  /** Field <code>SHOW_VALS</code> : boolean */
  private static final boolean SHOW_VALS = true;
  /** Field <code>SHOW_POINTS</code> : boolean */
  private static final boolean SHOW_POINTS = true;
  /** Field <code>SHOW_RELATIVE_LINES</code> : boolean */
  private static final boolean SHOW_RELATIVE_LINES = true;
  /** Field <code>FONT_SIZE</code> : int */
  private static final int FONT_SIZE = 11;
  /** Field <code>KEY_BORDER</code> : int */
  private static final int KEY_BORDER = 5;
  /** Field <code>yMin</code> : int */
  private int yMin = 0; // inches
  /** Field <code>yMax</code> : int */
  private int yMax = 96; // inches
  /** Field <code>xMin</code> : int */
  private int xMin = 0; // inches
  /** Field <code>xMax</code> : int */
  private int xMax = 240; // inches
  /** Field <code>keyWidth</code> : int */
  private int keyWidth = 0;
  /** Field <code>xBorder</code> : int */
  private int xBorder = 0;
  /** Field <code>graphTop</code> : int */
  private int graphTop;
  /** Field <code>graphLeft</code> : int */
  private int graphLeft;
  /** Field <code>x2</code> : float */
  private float x2;
  /** Field <code>height</code> : float */
  private float height;
  /** Field <code>yFreq</code> : int */
  private int yFreq;
  /** Field <code>yCounter</code> : int */
  private int yCounter;
  /** Field <code>yFactor</code> : float */
  private float yFactor;
  /** Field <code>xFreq</code> : int */
  private int xFreq;
  /** Field <code>xCounter</code> : int */
  private int xCounter;
  /** Field <code>xFactor</code> : float */
  private float xFactor;
  /** Field <code>width</code> : float */
  private float width;
  /** Field <code>numberOfVals</code> : int */
  private int numberOfVals;

  /**
   * Field <code>segments</code> : TrackSegments
   * 
   * @uml.property name="segments"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private TrackSegments segments = null;

  /** Field <code>fontsize</code> : int */
  private int fontsize;
  /** Field <code>x</code> : float */
  private float x;
  /** Field <code>fontcol</code> : java.awt.Color */
  private java.awt.Color fontcol;
  /** Field <code>fm</code> : java.awt.FontMetrics */
  private java.awt.FontMetrics fm;
  /** Field <code>gFont</code> : java.awt.Font */
  private java.awt.Font gFont;
  /**
   * Constructor for TrackGraph. 
   * @param segments - TrackSegments, the segments of the Track
   */
  public TrackGraph( TrackSegments segments )
  {
    this.segments = segments;
    initialize();
  }
  /**
   * Method calcGraphPoints.  Calculates the Points on the graph of the 
   * Track Configuration.  The points are realative the the specified position by the end user.
   */
  private synchronized void calcGraphPoints()
  {
    /** Field <code>SEGMENTS</code>: int */
    final int SEGMENTS = segments.numberOfSegements(); // The Number of Track
    // Segments
    /** Field <code>SEGMENTS_PLUS_ONE</code>: int */
    final int SEGMENTS_PLUS_ONE = SEGMENTS + 1; // The Number of Height
    // Measurements
    double totalTrackLength = 0;
    trackX = new int[SEGMENTS_PLUS_ONE];
    trackY = new int[SEGMENTS_PLUS_ONE];
    relativeY = new int[SEGMENTS_PLUS_ONE];
    for (int i = 2, j = 0; i < SEGMENTS; i++, j++)
    {
      int xVal = segments.getLengthSegment(j);
      totalTrackLength += xVal;
      trackX[i] = xVal;
      if (xVal <= 0)
      {
        trackX[i] = (int) totalTrackLength;
      }
      else
      {
        trackX[i] = trackX[i - 1] + xVal;
      }
    }
    for (int i = 0; i < SEGMENTS; i++)
    {
      int yVal = segments.getHeightSegment(i - 1);
      int y1 = segments.getHeightSegment(i);
      trackY[i] = yVal;
      relativeY[i] = y1;
    }
    this.trackLengthInches = totalTrackLength;
    this.xMax = (int) this.trackLengthInches + 2;
    this.yMax = (int) segments.getHeightSegment(0);
    this.numberOfVals = segments.numberOfSegements();
  }
  /**
   * Method drawPoint.  Draws a Single Point on the Graph.
   * @param graphics1 - TGraphics, the Graphics object of where to draw the point
   * @param int2 - int, the X coordinate
   * @param int3 - int, the Y Corrdinate
   * @param int4 - int, The Type, circle or rectangle
   */
  public void drawPoint( java.awt.Graphics graphics1, int int2, int int3, int int4 )
  {
    switch (int4)
    {
      case 1 :
        graphics1.fillRect(int2 - 2, int3 - 2, 4, 4);
        return;
      case 2 :
        graphics1.fillOval(int2 - 3, int3 - 3, 6, 6);
        return;
      default :
        graphics1.fillRect(int2 - 3, int3 - 3, 6, 6);
        return;
    }
  }
  /**
   * Method getFrequency.  Calculates the Frequency of the Tick Marks.
   * @param inValue - int, the Value where the range is sitting
   * @return int, the Frequency
   */
  public int getFrequency( int inValue )
  {
    int freq = 1;
    if (inValue > 10 && inValue < 21)
    {
      freq = 5;
    }
    if (inValue > 20 && inValue < 101)
    {
      freq = 10;
    }
    if (inValue > 100 && inValue < 201)
    {
      freq = 50;
    }
    if (inValue > 200 && inValue < 1001)
    {
      freq = 100;
    }
    if (inValue > 1000 && inValue < 2001)
    {
      freq = 500;
    }
    if (inValue > 2000 && inValue < 10001)
    {
      freq = 1000;
    }
    if (inValue > 10000 && inValue < 20001)
    {
      freq = 5000;
    }
    if (inValue > 20000 && inValue < 100001)
    {
      freq = 10000;
    }
    if (inValue > 100000 && inValue < 1000001)
    {
      freq = 100000;
    }
    if (inValue > 1000000 && inValue < 10000001)
    {
      freq = 1000000;
    }
    if (inValue > 10000000 && inValue < 100000001)
    {
      freq = 10000000;
    }
    if (inValue > 100000000)
    {
      freq = 100000000;
    }
    return freq;
  }
  /**
   * Method getOffset.  Calculates the Offset of the String Width, to determine where the center point is.
   * @param string1 - String, The string value
   * @return int, the Offset of the String value
   */
  public int getOffset( java.lang.String string1 )
  {
    return java.lang.Math.round((float) (fm.stringWidth(string1) / 2));
  }
  /**
   * Method initialize.  Initializes the Panel Display to set some intermdiate values 
   * for the actual paint of this object
   */
  public void initialize()
  {
    calcGraphPoints();
    ((java.awt.Component) this).setBackground(new java.awt.Color(0, 0, 0));
    xBorder = java.lang.Math.round((float) ((double) java.lang.String.valueOf(yMax).length() / 1.5) * (float) fontsize);
    graphLeft = BORDER_LEFT + xBorder;
    graphTop = BORDER_TOP;
    if (TITLE != null)
    {
      graphTop += fontsize + 3;
    }
    x2 = (float) graphLeft;
    fontsize = FONT_SIZE;
    height = (float) (((java.awt.Component) this).getSize().height - (graphTop + BORDER_BOTTOM + fontsize));
    if (keyWidth > 0)
    {
      width = (float) ((java.awt.Component) this).getSize().width - (x2 + (float) (KEY_BORDER * 2) + (float) keyWidth);
    }
    else
    {
      width = (float) (((java.awt.Component) this).getSize().width - BORDER_RIGHT) - x2;
    }
    yCounter = yMin;
    yFactor = height / (float) (yMax - yMin);
    xFactor = width / (float) (xMax - xMin);
    yFreq = getFrequency(yMax - yMin);
    xFreq = getFrequency(xMax - xMin);
    xCounter = xMin;
    fontcol = Color.RED;
    gFont = new java.awt.Font("Arial", 0, fontsize);
    //fm = java.awt.Toolkit.getDefaultToolkit().getFontMetrics(gFont);
    //    lm = fm.getLineMetrics("ABCxyz", g2);
  }
  /**
   * Method Paint().  The Method Paints the Panel in the manner described filling in the Border, the 
   * Axis planes and the Lines, It may also depending on the Flags set paint the Points and Values
   * Overridden Method:  
   * @see java.awt.Component#paint(java.awt.Graphics)
   * @param graphics1 - Graphics, The Graphics Object.
   */
  public void paint( java.awt.Graphics graphics1 )
  {
    initialize();
    fm = graphics1.getFontMetrics(gFont);
    graphics1.setFont(gFont);
    graphics1.setColor(fontcol);
    if (TITLE != null)
    {
      graphics1.drawString(TITLE, graphLeft + (int) (width / 2.0F - (float) getOffset(TITLE)), BORDER_TOP + fontsize);
    }
    // Paint the Border
    graphics1.setColor(Color.BLUE);
    graphics1.drawRect(0, 0, ((java.awt.Component) this).getSize().width - 1, ((java.awt.Component) this).getSize().height - 1);
    // Paint the Key - If any?
    if (keyWidth > 0)
    {
      graphics1.setColor(Color.white);
      graphics1.fillRect(BORDER_LEFT + xBorder + (int) width + KEY_BORDER, KEY_BORDER, keyWidth, LINES * fontsize + KEY_BORDER * 2);
      graphics1.setColor(Color.black);
      graphics1.fillRect(BORDER_LEFT + xBorder + (int) width + KEY_BORDER, KEY_BORDER, keyWidth, LINES * fontsize + KEY_BORDER * 2);
      for (int j = 1; j <= LINES; j++)
      {
        graphics1.setColor(fontcol);
        graphics1.drawString("KEY_" + j, BORDER_LEFT + xBorder + (int) width + KEY_BORDER + 8, KEY_BORDER * 2 + j * fontsize);
        graphics1.setColor(Color.GREEN);
        graphics1.fillRect(BORDER_LEFT + xBorder + (int) width + KEY_BORDER + 2, KEY_BORDER * 2 + (j - 1) * fontsize + 5, 4, 4);
      }
    }
    graphics1.setColor(Color.black);
    graphics1.drawLine(graphLeft, graphTop, graphLeft, graphTop + (int) height);
    graphics1.drawLine(graphLeft - 3, graphTop + (int) height, graphLeft + (int) width, graphTop + (int) height);
    // Paint the Y Axis
    while (yCounter <= yMax)
    {
      String temp = "" + yCounter;
      int x1 = graphLeft - 6 - fm.stringWidth(temp);
      int y1 = Math.round(height * (1.0F - (float) (yCounter - yMin) / (float) (yMax - yMin))) + (graphTop + 5);
      int x2 = 0;
      int y2 = 0;
      graphics1.setColor(fontcol);
      graphics1.drawString(temp, x1, y1);
      graphics1.setColor(Color.black);
      final boolean grid = false;
      if (grid)
      {
        x1 = graphLeft - 3;
        y1 = Math.round(height * (1.0F - (float) (yCounter - yMin) / (float) (yMax - yMin))) + graphTop;
        x2 = (int) ((float) graphLeft + width);
        y2 = Math.round(height * (1.0F - (float) (yCounter - yMin) / (float) (yMax - yMin))) + graphTop;
        graphics1.drawLine(x1, y1, x2, y2);
      }
      else
      {
        x1 = graphLeft - 3;
        y1 = Math.round(height * (1.0F - (float) (yCounter - yMin) / (float) (yMax - yMin))) + graphTop;
        x2 = graphLeft;
        y2 = Math.round(height * (1.0F - (float) (yCounter - yMin) / (float) (yMax - yMin))) + graphTop;
        graphics1.drawLine(x1, y1, x2, y2);
      }
      yCounter += yFreq;
    }
    // Paint the X-Axis
    while (xCounter <= xMax)
    {
      String temp = "" + xCounter;
      int offset = getOffset(temp);
      int x1 = (int) (width - (float) Math.round(width * (1.0F - (float) (xCounter - xMin) / (float) (xMax - xMin))) + (float) (int) x) - offset;
      int y1 = graphTop + (int) height + fontsize + 6;
      int x2 = 0;
      int y2 = 0;
      graphics1.setColor(fontcol);
      graphics1.drawString(temp, x1, y1);
      graphics1.setColor(Color.black);
      x1 = (int) (width - (float) Math.round(width * (1.0F - (float) (xCounter - xMin) / (float) (xMax - xMin)))) + (int) x;
      y1 = graphTop + (int) height;
      x2 = (int) (width - (float) Math.round(width * (1.0F - (float) (xCounter - xMin) / (float) (xMax - xMin)))) + (int) x;
      y2 = graphTop + (int) height + 3;
      graphics1.drawLine(x1, y1, x2, y2);
      xCounter += xFreq;
    }
    // Draw Relative Lines
    if (SHOW_RELATIVE_LINES)
    {
      graphics1.setColor(new Color(0, 0, 112));
      for (int i = 0; i < numberOfVals; i++)
      {
        int v1y = relativeY[i];
        int v1x = trackX[i];
        int v2y = trackY[i];
        int v2x = trackX[i + 1];
        int lineCount = 1;
        int x1 = (int) ((float) Math.round((v1x - (float) xMin) * xFactor) + x) + lineCount;
        int y1 = (int) ((float) graphTop + height) - Math.round((v1y - (float) yMin) * yFactor);
        int x2 = (int) ((float) Math.round((v2x - (float) xMin) * xFactor) + x) + lineCount;
        int y2 = (int) ((float) graphTop + height) - Math.round((v2y - (float) yMin) * yFactor);
        graphics1.drawLine(x1, y1, x1, y2);
        graphics1.drawLine(x1, y1, x2, y1);
      }
    }
    // Paint the Graph Lines
    for (int j = 1; j <= LINES; j++)
    {
      x = (float) (BORDER_LEFT + xBorder);
      graphics1.setColor(Color.DARK_GRAY);
      for (int i = 0; i < numberOfVals; i++)
      {
        int v1y = trackY[i];
        int v1x = trackX[i];
        int v2y = trackY[i + 1];
        int v2x = trackX[i + 1];
        int lineCount = 0;
        while (lineCount < LINEWIDTH)
        {
          int x1 = (int) ((float) Math.round((v1x - (float) xMin) * xFactor) + x) + lineCount;
          int y1 = (int) ((float) graphTop + height) - Math.round((v1y - (float) yMin) * yFactor);
          int x2 = (int) ((float) Math.round((v2x - (float) xMin) * xFactor) + x) + lineCount;
          int y2 = (int) ((float) graphTop + height) - Math.round((v2y - (float) yMin) * yFactor);
          graphics1.drawLine(x1, y1, x2, y2);
          x1 = (int) ((float) Math.round((v1x - (float) xMin) * xFactor) + x);
          y1 = (int) ((float) graphTop + height) - Math.round((v1y - (float) yMin) * yFactor) + lineCount;
          x2 = (int) ((float) java.lang.Math.round((v2x - (float) xMin) * xFactor) + x);
          y2 = (int) ((float) graphTop + height) - Math.round((v2y - (float) yMin) * yFactor) + lineCount;
          graphics1.drawLine(x1, y1, x2, y2);
          lineCount++;
          if (SHOW_VALS)
          {
            String temp = "" + v2x + "," + v2y;
            x1 = (int) ((float) Math.round((v2x - (float) xMin) * xFactor) + x) - getOffset(java.lang.String.valueOf(v2y)) / 2;
            y1 = (int) ((float) graphTop + height - 10.0F) - Math.round((v2y - (float) yMin) * yFactor);
            graphics1.setColor(Color.blue);
            graphics1.drawString(temp, x1, y1);
          }
          if (SHOW_POINTS)
          {
            x1 = (int) ((float) Math.round((v2x - (float) xMin) * xFactor) + x);
            y1 = (int) ((float) graphTop + height) - Math.round((v2y - (float) yMin) * yFactor);
            drawPoint(graphics1, x1, y1, j);
          }
          graphics1.setColor(Color.DARK_GRAY);
        }
      }
    }
  }
}
