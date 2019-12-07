/*
 * Package:		com.boyscouts.publish.results
 * File Name:	ExportTopWinnersTask.java
 */
package com.boyscouts.publish.results;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.boyscouts.domain.RacerContainer;
import com.boyscouts.domain.RacerPerson;
import com.boyscouts.domain.WinnerRaceContainer;
import com.hgutil.GenericFileFilter;

/**
 * author:      hgrein<BR>
 * date:        May 31, 2004<BR>
 * Package:     com.boyscouts.publish.results<BR>
 * File Name:   ExportTopWinnersTask.java<BR>
 * Type Name:   ExportTopWinnersTask<BR>
 * Description: A Task to print all the Winners in a Comma Seperated Vector
 */

public class ExportTopWinnersTask extends TimerTask
{

  /**
   * 
   * @uml.property name="frame"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  JFrame frame = null;

  public ExportTopWinnersTask( JFrame frame )
  {
    this.frame = frame;
  }
  /**
   * Method exportTopRacersToCSV.  Exports the top racers to CSV
   */
  public void exportTopRacersToCSV()
  {
    WinnerRaceContainer winnerCont = WinnerRaceContainer.getInstance();
    int topRacerCount = WinnerRaceContainer.getTopRaceSize();
    String fileName = getFileName();
    if (topRacerCount < 1 || fileName == null)
    {
      return;
    }
    File tempFile = new File(fileName);
    PrintWriter pw = null;
    try
    {
      pw = new PrintWriter(new FileOutputStream(tempFile), true);
      Vector keys = winnerCont.getRaceKeys();
      RacerContainer rc = null;
      for (int i = 0; i < keys.size(); i++)
      { // Get the Key of the container and the Container for that key.
        String keyItem = "" + keys.elementAt(i);
        rc = winnerCont.getRacerContainer(keyItem);
        if (rc != null && rc.getSize() > 0)
        { // Ok, we have 1 or more racers, let her rip baby!
          int containerSize = rc.getSize();
          if (i == 0)
          {
            Vector headers = RacerPerson.generateHeaderData();
            for (int j = 0; j < headers.size(); j++)
            {
              String temp = "#" + headers.elementAt(j) + ",";
              pw.print(temp);
            }
            pw.println();
          }
          // OK if we are set to Add, loop through all the elements and add them to the container
          for (int j = 0; j < containerSize; j++)
          {
            RacerPerson rp = rc.elementAt(j);
            if (rp != null)
            { // We are ready, Do we want to Print the Racer Person
              Vector data = rp.getDataAsVector();
              for (int k = 0; k < data.size(); k++)
              {
                String temp = "" + data.elementAt(k) + ",";
                pw.print(temp);
              }
              pw.println();
            }
          }
        }
      }
    }
    catch (IOException exc)
    {
    }
    finally
    {
      if (pw != null)
      {
        try
        {
          pw.close();
        }
        catch (Exception exc)
        {
        }
      }
    }

  }

  /**
   * Method getFileName.  
   * @throws HeadlessException
   */
  private String getFileName()
  {
    String fileName = null;
    JFileChooser chooser = new JFileChooser();
    String topLevelDirName = "C:" + File.separator + "Temp" + File.separator + "RaceARama" + File.separator; //C:\Temp\RaceARama
    chooser.setDialogTitle("Select the Directory to Save CSV of Winners");
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setCurrentDirectory(new File(topLevelDirName));
    GenericFileFilter gff = new GenericFileFilter();
    gff.addExtension("csv");
    chooser.setFileFilter(gff);
    int returnVal = chooser.showSaveDialog(frame);
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      try
      {
        fileName = chooser.getSelectedFile().getCanonicalPath();
        if ((fileName != null) && !fileName.toLowerCase().endsWith(".csv"))
        {
          fileName += ".csv";
        }
      }
      catch (IOException exc)
      {
      }
    }

    return fileName;
  }
  /**
   * Overridden Method:  
   * @see java.lang.Runnable#run()
   * 
   */
  public void run()
  {
    this.cancel();
    exportTopRacersToCSV();
  }

}
