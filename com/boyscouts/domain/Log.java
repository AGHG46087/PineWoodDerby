/*
 * @author:		Owner
 * date:		Dec 21, 2003
 * Package:		com.boyscouts.domain
 * File Name:		Log.java
 */
package com.boyscouts.domain;

import com.hgutil.ParseData;

/**
 * author:      hgrein<BR>
 * date:        Jun 2, 2004<BR>
 * Package:     com.boyscouts.domain<BR>
 * File Name:   Log.java<BR>
 * Type Name:   Log<BR>
 * Description: Class is used for all the Logging Aspects.
 */
public class Log
{
  /** Field <code>DEBUG</code>: boolean */
  private static boolean DEBUG = ParseData.parseBool(System.getProperties().getProperty("HGDEBUG"), false);
  /**
   * Method logError.  If the DEBUG FLAG is Set to TRUE, then we will log to the Standard Out.
   * @param s - String, he Message to Display
   */
  public static void logError( String s )
  {
    if (DEBUG)
    {
      System.err.println("RaceARama::" + s);
    }
  }
  /**
   * Method logError.  If the DEBUG FLAG is Set to TRUE, then we will log to the Standard Out.
   * @param s - String, he Message to Display
   * @param t - Throwable, he Message to Display
   */
  public static void logError( String s, Throwable t )
  {
    if (s != null && t != null)
    {
      s += " : " + t.getMessage();
    }
    logError(s);
    logError(t);
  }
  /**
   * Method logError.  If the DEBUG FLAG is Set to TRUE, then we will log to the Standard Out.
   * @param t - Throwable, he Message to Display
   */
  public static void logError( Throwable t )
  {
    if (DEBUG)
    {
      t.printStackTrace();
    }
  }
  /**
   * Method debug.  If the DEBUG FLAG is Set to TRUE, then we will log to the Standard Out.
   * @param s - String, he Message to Display
   */
  public static void debug( String s )
  {
    if (DEBUG)
    {
      System.out.println("RaceARama::" + s);
    }
  }

}