/*
 * @author:		Owner
 * date:		Dec 27, 2003
 * Package:		com.boyscouts.util.track
 * File Name:		TrackDiagramTest.java
 */
package com.boyscouts.util.track.test;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.boyscouts.util.track.TrackUtils;

/**
 * author:      Owner
 * date:        Dec 27, 2003
 * Package:     com.boyscouts.util.track
 * File Name:   TrackDiagramTest.java
 * Type Name:   TrackDiagramTest
 * Description: Tests the Track Diagram
 */
class TrackDiagramTest extends JFrame
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3256728364034569780L;
  public TrackDiagramTest()
  {
    this.setSize(600, 400);
    Container cont = this.getContentPane();
    JPanel p = TrackUtils.getInstance().getTrackGraph();
    p.setSize(this.getSize());
    cont.add(p);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  public static void main( String[] args )
  {
    new TrackDiagramTest().setVisible(true);
  }
}
