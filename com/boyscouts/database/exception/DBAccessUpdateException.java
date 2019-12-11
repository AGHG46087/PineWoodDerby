/*
 * @author:			Owner
 * Package:			com.boyscouts.database.exception
 * File Name:		DBAccessUpdateException.java
 */
package com.boyscouts.database.exception;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.database.exception<BR>
 * File Name:   DBAccessUpdateException.java<BR>
 * Type Name:   DBAccessUpdateException<BR>
 * Description: Update Exception. The exception defines an error with updating data into the 
 * Database.  The exception descends from the DBAccessException
 */
public class DBAccessUpdateException extends DBAccessException
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3760566386191512887L;

  /**
   * Constructor for DBAccessUpdateException. 
   * @param message - String, the message to send.
   */
  public DBAccessUpdateException( String message )
  {
    super(message);
  }

  /**
   * Constructor for DBAccessUpdateException. 
   * @param message - String, the message to send.
   * @param cause - Throwable, the Exceptionas the cause
   */
  public DBAccessUpdateException( String message, Throwable cause )
  {
    super(message, cause);
  }

  /**
   * Constructor for DBAccessUpdateException. 
   * @param cause - Throwable, the Exceptionas the cause
   */
  public DBAccessUpdateException( Throwable cause )
  {
    super(cause);
  }

}
