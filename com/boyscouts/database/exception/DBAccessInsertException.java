/*
 * @author:			Owner
 * Package:			com.boyscouts.database.exception
 * File Name:		DBAccessInsertException.java
 */
package com.boyscouts.database.exception;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.database.exception<BR>
 * File Name:   DBAccessInsertException.java<BR>
 * Type Name:   DBAccessInsertException<BR>
 * Description: Insert Exception, The exception defines an error with inserting data into the 
 * Database.  The exception descends from the DBAccessException
 */
public class DBAccessInsertException extends DBAccessException
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3257852060555884345L;
  /**
   * Constructor for DBAccessInsertException. 
   * @param message - String, the message to send.
   */
  public DBAccessInsertException( String message )
  {
    super(message);
  }
  /**
   * Constructor for DBAccessInsertException. 
   * @param cause - Throwable, the Exceptionas the cause
   */
  public DBAccessInsertException( Throwable cause )
  {
    super(cause);
  }
  /**
   * Constructor for DBAccessInsertException. 
   * @param message - String, the message to send.
   * @param cause - Throwable, the Exceptionas the cause
   */
  public DBAccessInsertException( String message, Throwable cause )
  {
    super(message, cause);
  }
}
