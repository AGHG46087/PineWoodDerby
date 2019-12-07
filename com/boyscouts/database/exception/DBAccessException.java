/*
 * @author:			Owner
 * date:				Dec 9, 2003
 * Package:			com.boyscouts.database.exception
 * File Name:		DBAccessException.java
 */
package com.boyscouts.database.exception;

/**
 * author:      hgrein<BR>
 * date:        Jun 3, 2004<BR>
 * Package:     com.boyscouts.database.exception<BR>
 * File Name:   DBAccessException.java<BR>
 * Type Name:   DBAccessException<BR>
 * Description: Base DataBase Exception. Defines the main exceptions for 
 * Adding, inserting and deleting records to and from the the database.
 */
public class DBAccessException extends Exception
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3257566221992865840L;
  /**
   * Constructor for DBAccessException. 
   * @param message - String, the message to send.
   */
  public DBAccessException( String message )
  {
    super(message);
  }
  /**
   * Constructor for DBAccessException. 
   * @param cause - Throwable, the Exceptionas the cause
   */
  public DBAccessException( Throwable cause )
  {
    super(cause);
  }
  /**
   * Constructor for DBAccessException. 
   * @param message - String, the message to send.
   * @param cause - Throwable, the Exceptionas the cause
   */
  public DBAccessException( String message, Throwable cause )
  {
    super(message, cause);
  }
}
