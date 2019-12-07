/*
 * @author:		Owner
 * date:		Dec 22, 2003
 * Package:		com.boyscouts.util.track
 * File Name:		TrackSegments.java
 */
package com.boyscouts.util.track;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.Vector;

/**
 * author:      hgrein<BR>
 * date:        Jun 7, 2004<BR>
 * Package:     com.boyscouts.util.track<BR>
 * File Name:   TrackSegments.java<BR>
 * Type Name:   TrackSegments<BR>
 * Description: Maintains details of the Track - This class is Package Scope only.  
 * The functionality is similar in nature to a Vector. The difference in behavior is the 
 * method names.
 */

class TrackSegments implements Serializable
{

  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 4120848858805975351L;
  /**
   * Field <code>segments</code> : Vector
   * 
   * @uml.property name="segments"
   * @uml.associationEnd multiplicity="(0 -1)" elementType="java.awt.Dimension"
   */
  private Vector segments = new Vector();

  /**
   * Method clear.  Clears all data
   * 
   */
  public void clear()
  {
    segments.clear();
  }
  /**
   * Method addSegment.  Add Data via a Dimention Object
   * @param dim - Dimension, the Length and Height Segment
   */
  public void addSegment( Dimension dim )
  {
    segments.add(dim);
  }
  /**
   * Method getSegment.  Returns a Length and Height Segment via a Dimention Object
   * @param index - int, the Index of the segement desired
   * @return Dimension
   */
  public Dimension getSegment( int index )
  {
    Dimension dim = new Dimension(0, 0);

    if (0 <= index && index < segments.size())
    {
      dim = (Dimension) segments.elementAt(index);
    }
    return dim;
  }
  /**
   * Method numberOfSegements.  Returns the Number of Segments stored in the List of Segments
   * @return int, the Total Number OF Segments
   */
  public int numberOfSegements()
  {
    return segments.size();
  }
  /**
   * Method getLengthSegment.  Returns a Length Segment specified by the index.
   * @param index - int, the index desired
   * @return int, the Length segment
   */
  public int getLengthSegment( int index )
  {
    Dimension dim = getSegment(index);
    int length = dim.width;
    return length;
  }
  /**
   * Method getHeightSegment.  Returns the Height Segment specified by the index
   * @param index - int, the index desired
   * @return int, The Height Segment
   */
  public int getHeightSegment( int index )
  {
    Dimension dim = getSegment(index);
    int height = dim.height;
    return height;
  }

}
