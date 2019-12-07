/*
 * @author:		Owner
 * date:		Dec 11, 2003
 * Package:		com.boyscouts.database
 * File Name:		DBTask.java
 */
package com.boyscouts.database;

import java.util.TimerTask;

import javax.swing.JOptionPane;

import com.boyscouts.domain.Log;

/**
 * author:      hgrein<BR>
 * date:        Jun 3, 2004<BR>
 * Package:     com.boyscouts.database<BR>
 * File Name:   DBTask.java<BR>
 * Type Name:   DBTask<BR>
 * Description: Base Timer Task, Ensure the Logging Goes Where it needs to Go.  
 * The class decends from TimerTask providing the means of processing data in a
 * A-Synchronous fashion.  It also provides a means of logging debug information
 * and error logging on failures.
 */
public abstract class DBTask extends TimerTask
{
  /**
   * Constructor for DBTask. 
   */
  public DBTask()
  {
    super();
  }
  /**
   * Method debug.  Logs a Message to the Debug.
   * @param title - String, the Message Title
   * @param msg - String, the Message to be displayed.
   */
  public void debug( String title, String msg )
  {
    Log.debug("DBTask::" + title + ", " + msg);
  }
  /**
   * Method log.  Logs a Message to a JOptionPane.
   * @param title - String, the Message Title
   * @param msg - String, the Message to be displayed.
   */
  public void log( String title, String msg )
  {
    JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
  }
  /**
   * Method log.  Sends a Request to log an error out to the Logger And makes a request to 
   * send a Message to Popup Message.
   * @param title - String, the Title of the Message.
   * @param exc - Throwable, the 
   */
  public void log( String title, Throwable exc )
  {
    Log.logError(title, exc);
    this.log(title, exc.getMessage());
  }
}