package com.boyscouts.domain;

import java.awt.Dimension;

/**
 * author:      hgrein<BR>
 * date:        Jun 2, 2004<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   FieldLengths.java<BR>
 * Type Name:   FieldLengths<BR>
 * Description: Interface describes the Field Lengths to use in the Helper Class, GridBagHelper.
 */
public interface FieldLengths
{
  /** Field <code>SHORTER_FIELD</code> : Dimension 40,20 */
  public Dimension SHORTER_FIELD = new Dimension(40, 20);
  /** Field <code>SHORT_FIELD</code> : Dimension 80, 20 */
  public Dimension SHORT_FIELD = new Dimension(80, 20);
  /** Field <code>MEDIUM_FIELD</code> : Dimension 120, 20 */
  public Dimension MEDIUM_FIELD = new Dimension(120, 20);
  /** Field <code>LONG_FIELD</code> : Dimension 160, 20 */
  public Dimension LONG_FIELD = new Dimension(160, 20);
  /** Field <code>LONGER_FIELD</code> : Dimension 180, 20 */
  public Dimension LONGER_FIELD = new Dimension(180, 20);
  /** Field <code>HUGE_FIELD</code> : Dimension 240, 80 */
  public Dimension HUGE_FIELD = new Dimension(240, 80);
}