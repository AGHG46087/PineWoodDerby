/*
 * @author:			Owner
 * Package:			com.boyscouts.database
 * File Name:		DBAccess.java
 */
package com.boyscouts.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;

import com.boyscouts.database.exception.DBAccessException;
import com.boyscouts.domain.Log;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.database<BR>
 * File Name:   DBAccess.java<BR>
 * Type Name:   DBAccess<BR>
 * Description: Facilates Communication with the Database, Package Scope.  
 * This class provides a layer of Abstraction to actaully open, execute the queriies and close the database.
 * It is generic in that various operations can be used. It also provides the idea of Synchronization, 
 * since the database is a Microsoft Access Database, there can only be one connection at any single time.  
 * This will facilitate the issue, and ensure we have only one connection.  In addition, it also
 * proves some utility method to process the Result Set.
 */

class DBAccess
{
  /** Field <code>singleton</code> : DBAccess */
  private static DBAccess singleton = new DBAccess();

  /**
   * Field <code>DBOpen</code> : boolean
   * 
   * @uml.property name="dBOpen"
   */
  private boolean DBOpen = false;

  /** Field <code>con</code> : Connection */
  private Connection con = null;
  /** Field <code>dsnName</code> : String */
  private static final String dsnName = "RACE_A_RAMA";

  /**
   * Method getInstance.  Singleton getInstance Since we can have only one instance, we need a singleton.
   * @return DBAccess
   */
  public static DBAccess getInstance()
  {
    return singleton;
  }

  /**
   * Method close.  Closes the Database
   */
  public void close()
  {
    try
    {
      if (con != null)
      {
        try
        {
          con.close();
        }
        catch (Exception exc)
        {
        }
      }
    }
    finally
    {
      con = null;
      DBOpen = false;
    }
  }
  /**
   * Method getNextRow.  Populates a Vector for a Row in the Table
   * @param rs - ResultSet
   * @param rsmd - ResultSetMetaData
   * @return Vector, this is a vector of Objects ( Single Row )
   * @throws SQLException - On Processing Failure
   */
  private synchronized Vector getNextRow( ResultSet rs, ResultSetMetaData rsmd ) throws SQLException
  {
    // This method returns a vector of of the columns
    // within a row of our record set.
    Vector currentRow = new Vector();
    // Determine the column count
    for (int i = 1; i <= rsmd.getColumnCount(); i++)
    {
      // Swith on the type of data
      switch (rsmd.getColumnType(i))
      {
        case Types.VARCHAR :
          // Strings
          currentRow.addElement(rs.getString(i));
          break;
        case Types.INTEGER :
          // Integer
          currentRow.addElement(new Integer(rs.getInt(i)));
          break;
        case Types.DOUBLE :
          // Double
          currentRow.addElement(new Double(rs.getDouble(i)));
          break;
        case Types.DATE :
          // Date
          currentRow.addElement(rs.getDate(i));
          break;
        default :
          // All Other Data objects, but Notate the object type
          String temp = rsmd.getColumnTypeName(i);
          if (temp.equals("DATETIME"))
          {
            currentRow.addElement(rs.getDate(i));
          }
          else
          {
            currentRow.addElement(rs.getObject(i));
            Log.logError("Unknown Data Type: " + rsmd.getColumnTypeName(i));
          }
          break;
      }
    }
    // Return the Vector
    return (currentRow);
  }
  /**
   * Method insertOrUpdateDB.  Perform necessary Operation to Insert or Update.
   * If the Transaction was not succesful, it will Rollback the Transaction
   * @param insertQuery - String, The Query.
   * @throws DBAccessException - On Update Failure
   * @return int
   */
  public int insertOrUpdateDB( String insertQuery ) throws DBAccessException
  {
    int result = 0;
    if (insertQuery == null)
    {
      return result;
    }
    if (!isDBOpen())
    {
      openJDBCConnection();
    }
    Statement stmt = null;
    ResultSet resultSet = null;
    try
    {
      con.setAutoCommit(false);
      // Create a statemnt from the connection
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
      // Execute the query
      result = stmt.executeUpdate(insertQuery);
      // Commit the update
      con.commit();
      con.setAutoCommit(true);
    }
    catch (SQLException exc)
    {
      String msg = "insertOrUpdateDB DB FAILED:" + exc.getMessage();
      String allMsg = "";
      // Loop through the SQL Exceptions
      while (exc != null)
      {
        allMsg += "State  : " + exc.getSQLState() + "\n";
        allMsg += "Message: " + exc.getMessage() + "\n";
        allMsg += "Error  : " + exc.getErrorCode() + "\n";
        exc = exc.getNextException();
      }
      try
      {
        con.rollback();
      }
      catch (SQLException exc2)
      {
        msg += "\nRollBack Failed:" + exc.getMessage();
        Log.logError(msg);
      }
      Log.logError(msg);
      Log.logError(allMsg);
      throw new DBAccessException(msg, exc);
    }
    finally
    {
      if (resultSet != null)
      {
        try
        {
          resultSet.close();
        }
        catch (Exception exc)
        {
        }
      }
      resultSet = null;
      if (stmt != null)
      {
        try
        {
          stmt.close();
        }
        catch (Exception exc)
        {
        }
      }
      stmt = null;
      // Close the Database
      this.close();
    }

    return result;
  }

  /**
   * @return Returns the dBOpen : boolean.
   * 
   * @uml.property name="dBOpen"
   */
  public boolean isDBOpen()
  {
    return DBOpen;
  }

  /**
   * Method loadDataIntoVectors.  Loads the Data in the Result Set into a Vector for the Column Headers,
   * and A Vector of Vectors for the Row Data, and Populated the dbResults.
   * @param resultSet - ResultSet of the Query.  
   * @throws DBAccessException - On Processing Failure
   */
  private synchronized DBResults loadDataIntoVectors( ResultSet resultSet ) throws DBAccessException
  {
    DBResults dbResults = new DBResults();
    // This method Loads the recordset in to a vector
    // of header columns, and a vecto of vectors for cell data
    // If we are empty 
    try
    {
      // Start our load up of metadata
      ResultSetMetaData rsmd = resultSet.getMetaData();
      Vector columnHeaders = new Vector();
      Vector rowData = new Vector();

      // Get the Column Headers into a Vector
      for (int i = 1; i <= rsmd.getColumnCount(); i++)
      {
        columnHeaders.addElement(rsmd.getColumnName(i));
      }
      dbResults.setColumnHeaders(columnHeaders);
      // If the Cell, Row Data is empty, then populate a dummy Vector and Return
      if (!resultSet.next())
      { // Log status and return
        Log.debug("Record Set: EMPTY from DB Query");
        rowData.add(loadDummyCellData(columnHeaders));
        dbResults.setRowData(rowData);
        return dbResults;
      }
      // We have live results, get them populated and return
      do
      {
        // Start loading our vector of vectors
        // Get the Next Row Vector
        rowData.addElement(getNextRow(resultSet, rsmd));
      }
      while (resultSet.next()); // Done?
      dbResults.setRowData(rowData);

    }
    catch (SQLException exc)
    { // Oh No!
      System.out.println("Loading Records Failed, SQL Exception:" + exc.getMessage());
      throw new DBAccessException("Loading Records Failed, SQL Exception:", exc);
    }
    return dbResults;
  }
  /**
   * Method loadDummyCellData.  A Method to load the Row Data with Dummy Data, when a Result Set fails.
   * @param columnHeaders - The Column Headers
   * @return Vector - rowData The Dummy Data
   */
  private Vector loadDummyCellData( Vector columnHeaders )
  {
    Vector dummyVector = new Vector();
    for (int i = 0; i < columnHeaders.size(); i++)
    {
      dummyVector.add("");
    }
    return dummyVector;
  }

  /**
   * Method openJDBCConnection.  Opens a JDBC ODBC Bridge Connection
   * @throws DBAccessException - On Open Failure
   */
  public void openJDBCConnection() throws DBAccessException
  {
    // This method is the start calling method
    // to open the data base, query, and start the
    // record processing
    if (DBOpen)
    { // If we are already open - Do nothing and return
      return; // DB is open - do not open another
    }
    // Get our Class name - in case there is a change
    String classname = "sun.jdbc.odbc.JdbcOdbcDriver";
    // Here is a Bridge Name
    String jdbcBridge = "jdbc:odbc:" + dsnName;

    try
    {
      // Load the Driver
      Class.forName(classname);
      con = DriverManager.getConnection(jdbcBridge);
    }
    catch (SQLException e)
    { // Oh no!
      Log.logError("Open DB FAILED:", e);
      throw new DBAccessException("Open DB FAILED, SQL Exception:", e);
    }
    catch (ClassNotFoundException e)
    { // Oh no!
      Log.logError("Open DB FAILED:", e);
      throw new DBAccessException("Open DB FAILED, Class Not Found:", e);
    }
    DBOpen = true;
  }
  /**
   * Method queryDB.  Generic Query Support for the DbAccess,  This provides a Read Only Approach to the DB
   * @param queryString - String, the Query
   * @throws DBAccessException - On Query Failure
   * @return DBResults - Results
   */
  public DBResults queryDB( String queryString ) throws DBAccessException
  {
    DBResults dbResults = null;

    if (queryString == null)
    {
      return dbResults;
    }
    if (!isDBOpen())
    {
      openJDBCConnection();
    }
    Statement stmt = null;
    ResultSet resultSet = null;
    try
    {
      // Create a statemnt from the connection
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      // Execute the query
      resultSet = stmt.executeQuery(queryString);
      // Load the Result Table into ArrayObjects
      dbResults = loadDataIntoVectors(resultSet);
    }
    catch (SQLException exc)
    {
      String msg = "Query DB FAILED:" + exc.getMessage();
      Log.logError(msg);
      throw new DBAccessException(msg, exc);
    }
    finally
    {
      if (resultSet != null)
      {
        try
        {
          resultSet.close();
        }
        catch (Exception exc)
        {
        }
      }
      resultSet = null;
      if (stmt != null)
      {
        try
        {
          stmt.close();
        }
        catch (Exception exc)
        {
        }
      }
      stmt = null;
      // Close the Database
      this.close();
    }

    return dbResults;

  }

}
