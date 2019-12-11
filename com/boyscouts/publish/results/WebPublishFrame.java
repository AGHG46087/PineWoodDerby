/*
 * @author:		Owner
 * Package:		com.boyscouts.publish.results
 * File Name:		WebPublishFrame.java
 */
package com.boyscouts.publish.results;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;

import com.boyscouts.app.RaceARama;
import com.boyscouts.database.RaceARamaDB;
import com.boyscouts.domain.Log;
import com.boyscouts.domain.PhoneNumber;
import com.boyscouts.domain.RaceDataContainer;
import com.boyscouts.domain.RacerContainer;
import com.boyscouts.domain.RacerPerson;
import com.hgutil.ParseData;
import com.hgutil.swing.GridBagHelper;

/**
 * author:      hgrein<BR>
 * Package:     com.boyscouts.publish.results<BR>
 * File Name:   WebPublishFrame.java<BR>
 * Type Name:   WebPublishFrame<BR>
 * Description: Module Designed as a Wizrd to help facilitate where the 
 *              Web Publishing will be published too.
 */

public class WebPublishFrame extends JFrame
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3616453379992139568L;
  /** Field <code>FILE_SOURCE</code> : String */
  private static final String FILE_SOURCE = "WebPublishSource";
  /** Field <code>CHANGE_DIR</code> : String */
  private static final String CHANGE_DIR = "CHANGE_DIR";
  /** Field <code>CANCEL</code> : String */
  private static final String CANCEL = "CANCEL";
  /** Field <code>CONTINUE</code> : String */
  private static final String CONTINUE = "CONTINUE";
  /** Field <code>BACK</code> : String */
  private static final String BACK = "BACK";
  /** Field <code>RENAME</code> : String */
  private static final String RENAME = "RENAME";
  /** Field <code>STEP_ONE</code> : String */
  private static final String STEP_ONE = "STEP_ONE";
  /** Field <code>STEP_TWO</code> : String */
  private static final String STEP_TWO = "STEP_TWO";
  /** Field <code>STEP_THREE</code> : String */
  private static final String STEP_THREE = "STEP_THREE";
  /** Field <code>RESULT_XML</code> : String */
  private static final String RESULT_XML = "RaceResults.xml";

  /** Field <code>topLevelDirName</code> : String */
  private String topLevelDirName = "C:" + File.separator + "Temp";
  /** Field <code>PUBLISH_DIR</code> : String */
  private String PUBLISH_DIR = "DERBY_RACE_RESULTS_" + ParseData.format(new java.util.Date(), "yyyyMMdd_hhmmss");

  /**
   * Field <code>actionTrigger</code> : ActionTrigger
   * 
   * @uml.property name="actionTrigger"
   * @uml.associationEnd multiplicity="(1 1)" inverse="this$0:com.boyscouts.publish.results.WebPublishFrame$ActionTrigger"
   */
  private ActionTrigger actionTrigger = new ActionTrigger();

  /**
   * Field <code>directoryLbl</code> : JLabel
   * 
   * @uml.property name="directoryLbl"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JLabel directoryLbl = null;

  /**
   * Field <code>publishedLbl</code> : JLabel
   * 
   * @uml.property name="publishedLbl"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JLabel publishedLbl = null;

  /** Field <code>myLayout</code> : CardLayout */
  private CardLayout myLayout = null;

  /**
   * Field <code>cardPanel</code> : JPanel
   * 
   * @uml.property name="cardPanel"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JPanel cardPanel = null;

  /** Field <code>pageCount</code> : int */
  private int pageCount = 1;

  /**
   * Field <code>fromLabel</code> : JLabel
   * 
   * @uml.property name="fromLabel"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JLabel fromLabel = null;

  /**
   * Field <code>destLabel</code> : JLabel
   * 
   * @uml.property name="destLabel"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JLabel destLabel = null;

  /**
   * Field <code>mainApp</code> : RaceARama
   * 
   * @uml.property name="mainApp"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RaceARama mainApp = null;

  /** Field <code>resourceBundle</code> : ResourceBundle */
  private ResourceBundle resourceBundle = null;

  /**
   * Field <code>racerContainer</code> : RacerContainer
   * 
   * @uml.property name="racerContainer"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RacerContainer racerContainer = null;

  /**
   * author:      hgrein<BR>
   * Package:     com.boyscouts.publish.results<BR>
   * File Name:   WebPublishFrame.java<BR>
   * Type Name:   ActionTrigger<BR>
   * Description: Button Listener
   */
  private class ActionTrigger implements ActionListener
  {
    /**
     * Overridden Method:  
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param evt - ActionEvent
     */
    public void actionPerformed( ActionEvent evt )
    {
      String cmd = evt.getActionCommand();
      WebPublishFrame.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      if (cmd.equals(CANCEL))
      {
        deletePublishedResults();
        cleanup();
      }
      else if (cmd.equals(CHANGE_DIR))
      {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(getString("WebPublishFrame.directory.chooser.text"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(new File(topLevelDirName));
        int returnVal = chooser.showSaveDialog(WebPublishFrame.this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
          try
          {
            topLevelDirName = chooser.getSelectedFile().getCanonicalPath();
          }
          catch (IOException exc)
          {
          }
        }
        directoryLbl.setText(topLevelDirName);
      }
      else if (cmd.equals(RENAME))
      {
        String currDirectory = publishedLbl.getText();
        String tempResult = JOptionPane.showInputDialog(WebPublishFrame.this, "Rename directory[" + currDirectory + "]", PUBLISH_DIR);
        if (tempResult != null && tempResult.trim().length() > 0)
        {
          PUBLISH_DIR = tempResult.trim();
        }
        String destName = topLevelDirName + File.separator + PUBLISH_DIR;
        if (!currDirectory.equals(destName))
        {
          File source = new File(currDirectory);
          File dest = new File(destName);
          source.renameTo(dest);
          publishedLbl.setText(destName);
        }
      }
      else if (cmd.equals(CONTINUE))
      {
        if (pageCount == 3) // The Last Page
        {
          cleanup();
        }
        else if (pageCount == 2) // The Second Page
        {
          createPublishedResults();
        }
        pageCount++; // Either 1st or last page
        myLayout.next(cardPanel);
      }
      else if (cmd.equals(BACK))
      {
        if (pageCount == 3)
        {
          deletePublishedResults();
        }
        pageCount--;
        myLayout.previous(cardPanel);
      }
      WebPublishFrame.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

    }
  }
  /**
   * Constructor for WebPublishFrame. 
   * @param mainApp - RaceARama application
   * @param racerContainer - RaceContainer, the RacerContainer of all registered racers
   */
  public WebPublishFrame( RaceARama mainApp, RacerContainer racerContainer, String raceName )
  {
    this.mainApp = mainApp;
    this.racerContainer = racerContainer;
    // Append the Race Name to the Publish Directory
    PUBLISH_DIR += "_" + raceName;

    initialize();
    if (mainApp == null)
    {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    else
    {
      mainApp.setEnabled(false);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    // Add a Window Listener to enforce the Disposal of the Created Published Directory
    // If the User Clicked on the Little "X" on the Top of the Window.
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing( WindowEvent evt )
      {
        deletePublishedResults();
        cleanup();
      }
    });
  }
  /**
   * Method centerOnScreen.  Method will center the Dialog Box on the Screen
   */
  private void centerOnScreen()
  {
    // First Set the Location to be centered on the Screen
    Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();

    Rectangle screenRect = new Rectangle(0, 0, screenDim.width, screenDim.height);

    // Make sure we don't place the demo off the screen.
    int centerWidth = screenRect.width < this.getSize().width ? screenRect.x : screenRect.x + screenRect.width / 2 - this.getSize().width / 2;
    int centerHeight = screenRect.height < this.getSize().height ? screenRect.y : screenRect.y + screenRect.height / 2 - this.getSize().height / 2;

    this.setLocation(centerWidth, centerHeight);
  }
  /**
   * Method cleanup.  Method will perform any cleanup if neccesary and dsipose of the Frame.
   */
  private void cleanup()
  {
    if (mainApp != null)
    {
      mainApp.setEnabled(true);
    }
    this.setVisible(false);
    this.dispose();
  }
  /**
   * Method copyFile.  This method will recursively copy all Files under the Relative directiry
   * @param sourceFile - File, the Source File
   * @param publishedDir - File, the destination directory
   */
  private void copyFile( File sourceFile, File publishedDir )
  {
    String fileName = sourceFile.getName();
    if (sourceFile.isDirectory())
    {
      publishedDir = new File(publishedDir, fileName);
      if (!publishedDir.exists())
      {
        publishedDir.mkdirs();
      }
      File[] sourceFiles = sourceFile.listFiles();
      for (int i = 0; i < sourceFiles.length; i++)
      {
        copyFile(sourceFiles[i], publishedDir);
      }
    }
    else
    { // We are not a directory - it is a file, so we copy it.
      try
      {
        FileInputStream fis = new FileInputStream(sourceFile);
        publishedDir = new File(publishedDir, fileName);
        FileOutputStream fos = new FileOutputStream(publishedDir, true);

        // Set the Labels and update the Display
        fromLabel.setText(sourceFile.getPath());
        destLabel.setText(publishedDir.getPath());
        this.update(this.getGraphics()); // Refresh the display

        int oneChar, count = 0;
        while ((oneChar = fis.read()) != -1)
        {
          fos.write(oneChar);
          count++;
        }
        fis.close();
        fos.close();
        debug("Copy File from [" + sourceFile.getPath() + "] to [" + publishedDir.getPath() + "], bytes=[" + count + "]");
        Thread.sleep(100);
      }
      catch (InterruptedException exc)
      {
      }
      catch (FileNotFoundException exc)
      {
        Log.logError("File Not Found Exception: " + exc.getMessage());
      }
      catch (IOException exc)
      {
        Log.logError("IO Exception: " + exc.getMessage());
      }
    }
  }
  /**
   * Method createPublishedResults.  Copies all the Files that were saved under the Web Publish
   */
  private void createPublishedResults()
  {

    File publishedDir = new File(topLevelDirName + File.separator + PUBLISH_DIR);
    if (!publishedDir.exists())
    {
      publishedDir.mkdirs();
    }
    try
    {
      File sourceDir = new File(new File("."), FILE_SOURCE);
      File[] sourceFiles = sourceDir.listFiles();
      for (int i = 0; i < sourceFiles.length; i++)
      {
        copyFile(sourceFiles[i], publishedDir);
      }
      processXML();
    }
    catch (Exception exc)
    {
    }
  }
  /**
   * Method createStep1CardPanel.  Creates the Card panel 1
   * @return JPanel - The Panel
   */
  private JPanel createStep1CardPanel()
  {
    JPanel retPanel = new JPanel(new BorderLayout());
    retPanel.setBorder(BorderFactory.createTitledBorder(getString("WebPublishFrame.step1.border.title.text")));

    Dimension DIR_FIELD_LENGTH = new Dimension(180, 35);
    directoryLbl = new JLabel(topLevelDirName);
    directoryLbl.setBorder(BorderFactory.createTitledBorder("Directory"));
    directoryLbl.setPreferredSize(DIR_FIELD_LENGTH);
    JButton changeBtn = new JButton("Change");
    changeBtn.setActionCommand(CHANGE_DIR);
    changeBtn.addActionListener(actionTrigger);

    GridBagHelper dirPanel = new GridBagHelper();
    dirPanel.addFilledComponent(directoryLbl, 1, 1, 2, 1, GridBagConstraints.HORIZONTAL);
    dirPanel.addComponent(changeBtn, 1, 3, 1, 1);

    JButton nextBtn = new JButton("Continue");
    nextBtn.setActionCommand(CONTINUE);
    nextBtn.addActionListener(actionTrigger);
    JButton cancelBtn = new JButton("Cancel");
    cancelBtn.setActionCommand(CANCEL);
    cancelBtn.addActionListener(actionTrigger);
    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.add(nextBtn);
    btnPanel.add(cancelBtn);

    JLabel msg = new JLabel(getString("WebPublishFrame.step1.description.text"));
    retPanel.add(msg, BorderLayout.NORTH);
    retPanel.add(dirPanel, BorderLayout.CENTER);
    retPanel.add(btnPanel, BorderLayout.SOUTH);
    return retPanel;
  }
  /**
   * Method createStep2CardPanel.  Creates the Card panel 2
   * @return JPanel - The Panel
   */
  private JPanel createStep2CardPanel()
  {
    JPanel retPanel = new JPanel(new BorderLayout());
    retPanel.setBorder(BorderFactory.createTitledBorder(getString("WebPublishFrame.step2.border.title.text")));

    Dimension DIR_FIELD_LENGTH = new Dimension(180, 35);
    fromLabel = new JLabel("");
    fromLabel.setBorder(BorderFactory.createTitledBorder("Source"));
    fromLabel.setPreferredSize(DIR_FIELD_LENGTH);
    destLabel = new JLabel("");
    destLabel.setBorder(BorderFactory.createTitledBorder("Destination"));
    destLabel.setPreferredSize(DIR_FIELD_LENGTH);

    GridBagHelper labelPanel = new GridBagHelper();
    labelPanel.addFilledComponent(fromLabel, 1, 1, 2, 1, GridBagConstraints.HORIZONTAL);
    labelPanel.addFilledComponent(destLabel, 2, 1, 2, 1, GridBagConstraints.HORIZONTAL);

    JButton backBtn = new JButton("<< Back");
    backBtn.setActionCommand(BACK);
    backBtn.addActionListener(actionTrigger);
    JButton nextBtn = new JButton("Continue");
    nextBtn.setActionCommand(CONTINUE);
    nextBtn.addActionListener(actionTrigger);
    JButton cancelBtn = new JButton("Cancel");
    cancelBtn.setActionCommand(CANCEL);
    cancelBtn.addActionListener(actionTrigger);
    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.add(backBtn);
    btnPanel.add(nextBtn);
    btnPanel.add(cancelBtn);
    JLabel msg = new JLabel(getString("WebPublishFrame.step2.description.text"));
    retPanel.add(msg, BorderLayout.NORTH);
    retPanel.add(labelPanel, BorderLayout.CENTER);
    retPanel.add(btnPanel, BorderLayout.SOUTH);
    return retPanel;
  }
  /**
   * Method createStep3CardPanel.  Creates the Card panel 3
   * @return JPanel - The Card Panel
   */
  private JPanel createStep3CardPanel()
  {
    JPanel retPanel = new JPanel(new BorderLayout());
    retPanel.setBorder(BorderFactory.createTitledBorder(getString("WebPublishFrame.step3.border.title.text")));

    Dimension DIR_FIELD_LENGTH = new Dimension(180, 35);
    String publishedDir = topLevelDirName + File.separator + PUBLISH_DIR;
    publishedLbl = new JLabel(publishedDir);
    publishedLbl.setBorder(BorderFactory.createTitledBorder("Published Directory"));
    publishedLbl.setPreferredSize(DIR_FIELD_LENGTH);
    JButton changeBtn = new JButton("Rename");
    changeBtn.setActionCommand(RENAME);
    changeBtn.addActionListener(actionTrigger);

    GridBagHelper dirPanel = new GridBagHelper();
    dirPanel.addFilledComponent(publishedLbl, 1, 1, 2, 1, GridBagConstraints.HORIZONTAL);
    dirPanel.addComponent(changeBtn, 1, 3, 1, 1);

    JButton backBtn = new JButton("<< Back");
    backBtn.setActionCommand(BACK);
    backBtn.addActionListener(actionTrigger);
    JButton nextBtn = new JButton("Continue");
    nextBtn.setActionCommand(CONTINUE);
    nextBtn.addActionListener(actionTrigger);
    JButton cancelBtn = new JButton("Cancel");
    cancelBtn.setActionCommand(CANCEL);
    cancelBtn.addActionListener(actionTrigger);
    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.add(backBtn);
    btnPanel.add(nextBtn);
    btnPanel.add(cancelBtn);
    JLabel msg = new JLabel(getString("WebPublishFrame.step3.description.text"));
    retPanel.add(msg, BorderLayout.NORTH);
    retPanel.add(dirPanel, BorderLayout.CENTER);
    retPanel.add(btnPanel, BorderLayout.SOUTH);
    return retPanel;
  }
  /**
   * Method debug.  If the DEBUG FLAG is Set to TRUE, then we will log to the Standard Out.
   * @param s - String, he Message to Display
   */
  public void debug( String s )
  {
    s = "WebPublishFrame: " + s;
    Log.debug(s);
  }
  /**
   * Method deletePublishedResults.  Removes all the Files that were saved under the Web Publish
   */
  private void deletePublishedResults()
  {
    File publishedDir = new File(topLevelDirName + File.separator + PUBLISH_DIR);
    if (publishedDir.exists())
    {
      File[] subFiles = publishedDir.listFiles();
      for (int i = 0; i < subFiles.length; i++)
      {
        deleteSavedFile(subFiles[i]);
      }
      publishedDir.delete();
    }
  }
  /**
   * Method deleteSavedFile.  Recursive method, used to iterate and remove all files in the Direcotry Struture
   * @param file - File
   */
  private void deleteSavedFile( File file )
  {
    if (file != null)
    {
      if (file.isDirectory())
      {
        File[] subFiles = file.listFiles();
        for (int i = 0; i < subFiles.length; i++)
        {
          deleteSavedFile(subFiles[i]);
        }
      }
      debug("Deleting... " + file.getPath());
      file.delete();
    }
  }
  /**
   * Method getString.  Returns a String specified in the Key
   * @param key - String, the Key in the lookup
   * @return String, the Result
   */
  public String getString( String key )
  {
    String value = null;
    if (mainApp != null)
    {
      value = mainApp.getString(key);
      // Our Main app provides some stuff, garauntee we have it will be made to null
      if (value.equals("'" + key + "'"))
      {
        value = null;
      }
    }
    if (value == null)
    {
      if (resourceBundle == null)
      {
        resourceBundle = ResourceBundle.getBundle("resources.boy_scouts_race_prog");
      }
      try
      {
        value = resourceBundle.getString(key);
      }
      catch (MissingResourceException e)
      {
        String message = "java.util.MissingResourceException: " + "Couldn't find value for: " + key;
        debug(message);
      }
    }
    return value;
  }

  /**
   * Method initialize.  Method initializes the entire Frame and sets the card panel
   */
  private void initialize()
  {
    this.setTitle(getString("WebPublishFrame.frame.title.text"));
    this.setSize(610, 400);
    this.centerOnScreen();

    myLayout = new CardLayout();
    cardPanel = new JPanel(myLayout);
    cardPanel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));

    JPanel p = createStep1CardPanel();
    cardPanel.add(p, STEP_ONE);
    p = createStep2CardPanel();
    cardPanel.add(p, STEP_TWO);
    p = createStep3CardPanel();
    cardPanel.add(p, STEP_THREE);

    Container cont = this.getContentPane();
    cont.setLayout(new BorderLayout());
    cont.add(cardPanel, BorderLayout.CENTER);
  }
  /**
   * Method processIndividualXML.  This method is used to Generate the Small Individual XML Documents
   * for all indivual Comnpetitor.  After all the Documents are completred it will remove the 
   * XML Temp Directory
   */
  private void processIndividualXML()
  {
    String xmlDirectory = topLevelDirName + File.separator + PUBLISH_DIR + File.separator + "XML_DIR";
    File xmlDir = new File(xmlDirectory);
    if (!xmlDir.exists())
    {
      xmlDir.mkdirs();
    }
    // Generate the Individual XML documents
    for (int i = 0; i < racerContainer.getSize(); i++)
    {
      RacerPerson rp = racerContainer.elementAt(i);
      String moduleName = "" + rp.getLastName() + "_" + rp.getFirstName();
      processIndividualXML(rp, xmlDir, moduleName);
    }
    // Cleanup the Extra XML files - Delete Them all, by deleting the Directory
    deleteSavedFile(xmlDir);
  }
  /**
   * Method processIndividualXML.  This method is used to Generate the Single Individual XML Documents
   * for a indivual Comnpetitor Then Perform a XSLT Transform, After the XML file has been generated
   * @param rp - RacerPerson, The Racer to be published
   * @param xmlDir - File, Where to place the INtermediate XML File
   * @param moduleName - The XML/HTML Module Names
   */
  private void processIndividualXML( RacerPerson rp, File xmlDir, String moduleName )
  {
    PrintStream outXML = null;
    String xmlFileName = moduleName + ".xml";
    File xmlFile = new File(xmlDir, xmlFileName);
    try
    {
      outXML = new PrintStream(new BufferedOutputStream(new FileOutputStream(xmlFile)), true);
      XMLPublishResults results = new XMLPublishResults(outXML);
      results.generateRacerPersonDocument(rp);
    }
    catch (FileNotFoundException exc)
    {
      Log.logError("FileNotFoundException : ", exc);
    }
    finally
    {
      if (outXML != null)
      {
        outXML.flush();
        outXML.close();
        outXML = null;
      }
    }

    String xsltModule = topLevelDirName + File.separator + PUBLISH_DIR;
    xsltModule += File.separator + getString("WebPublishFrame.xslt.transform.individual.source");
    String outputFile = topLevelDirName + File.separator + PUBLISH_DIR;
    outputFile += File.separator + getString("WebPublishFrame.xslt.transform.individual.output_dir");
    outputFile += File.separator + moduleName + ".html";
    transformXML(xmlFile.getPath(), xsltModule, outputFile);
  }
  /**
   * Method processXML.  Method begins processing on the Exporting the XML to a file
   */
  private void processXML()
  {
    PrintStream outXML = null;
    // Generate the Main XML Document
    try
    {
      String outFileName = topLevelDirName + File.separator + PUBLISH_DIR + File.separator + RESULT_XML;
      File outFile = new File(outFileName);
      outXML = new PrintStream(new BufferedOutputStream(new FileOutputStream(outFile)), true);
      XMLPublishResults results = new XMLPublishResults(outXML);
      results.generateCompleteDocument(racerContainer);
    }
    catch (FileNotFoundException exc)
    {
      Log.logError("FileNotFoundException: " + exc.getMessage());
    }
    finally
    {
      if (outXML != null)
      {
        outXML.flush();
        outXML.close();
        outXML = null;
      }
    }
    processXMLTransforms(); // This is on the Master Document
    processIndividualXML(); // This is for Small Individual XML snippets for Competitors

  }
  /**
   * Method processXMLTransforms.  Method, will process the XML and any Transforms that are required
   *  as well as set the ouput of the transofrms
   */
  private void processXMLTransforms()
  {
    // Now We will Read in all the Source Files to Process output transofrms on the XML
    int count = 0;
    Vector xsltList = new Vector();
    Vector outFileList = new Vector();
    String key = null;
    String xsltModule = null;
    String outFileModule = null;

    do
    {
      key = "WebPublishFrame.xslt.transform.source_" + count;
      xsltModule = getString(key);
      if (xsltModule != null && !xsltModule.trim().equals(""))
      {
        xsltList.addElement(xsltModule);
      }
      key = "WebPublishFrame.xslt.transform.output_" + count;
      outFileModule = getString(key);
      if (outFileModule != null && !outFileModule.trim().equals(""))
      {
        outFileList.addElement(outFileModule);
      }
      count++;
    }
    while (xsltModule != null);

    if (xsltList.size() != outFileList.size())
    {
      Log.logError("XML Processing List does not match XSL Modules = [" + xsltList.size() + "], Output Modules = [" + outFileList.size() + "]");
      return;
    }
    String xmlSourceFile = topLevelDirName + File.separator + PUBLISH_DIR + File.separator + RESULT_XML;

    for (int i = 0; i < xsltList.size(); i++)
    {
      xsltModule = topLevelDirName + File.separator + PUBLISH_DIR + File.separator + xsltList.elementAt(i);
      outFileModule = topLevelDirName + File.separator + PUBLISH_DIR + File.separator + outFileList.elementAt(i);
      if (!(outFileModule.toLowerCase().endsWith(".xml") || outFileModule.toLowerCase().endsWith(".html")))
      {
        int start = xmlSourceFile.lastIndexOf(File.separator) + 1;
        int end = xmlSourceFile.toLowerCase().lastIndexOf(".");
        String temp = xmlSourceFile.substring(start, end);
        outFileModule += File.separator + temp + ".html";
      }
      transformXML(xmlSourceFile, xsltModule, outFileModule);
    }
  }
  /**
   * Method transformXML.  Method Will Transform the XML and Save the output to the Specified StreamResult
   * @param xmlSource - SAXSource, The XML Source File
   * @param xlstStreamSource - StreamSource, The XSL Input Stream
   * @param outStreamResult - StreamResult, The Output Stream
   */
  private void transformXML( SAXSource xmlSource, StreamSource xlstStreamSource, StreamResult outStreamResult )
  {
    try
    {
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer(xlstStreamSource);
      transformer.transform((Source) xmlSource, outStreamResult);
    }
    catch (TransformerConfigurationException exc)
    {
    }
    catch (TransformerException exc)
    {
    }
  }
  /**
   * Method transformXML.  Method Will Transform the XML and Save the output to the Specified StreamResult
   * @param xmlFileName - String, The XML Source File
   * @param xslFileName - String, The XSL Input Stream
   * @param outFileName - String, The Output Stream
   */
  private void transformXML( String xmlFileName, String xslFileName, String outFileName )
  {
    BufferedReader bufferedReader = null;
    try
    {
      // Update the Display labels
      fromLabel.setText("XML : " + xmlFileName);
      destLabel.setText(outFileName);
      this.update(this.getGraphics()); // Refresh the display
      // Input XML
      File inFile = new File(xmlFileName);
      bufferedReader = new BufferedReader(new FileReader(inFile));
      SAXSource saxSource = new SAXSource(new InputSource(bufferedReader));
      //  XSLT
      StreamSource xlstStreamSource = new StreamSource(new File(xslFileName));
      // Output File
      File resultXMLFile = new File(outFileName);
      if (resultXMLFile.exists())
      {
        resultXMLFile.delete();
      }
      StreamResult streamResult = new StreamResult(resultXMLFile);

      transformXML(saxSource, xlstStreamSource, streamResult);
    }
    catch (FileNotFoundException exc)
    {
      Log.logError("FileNotFound Exception: " + exc.getMessage());
    }
    finally
    {
      if (bufferedReader != null)
      {
        try
        {
          bufferedReader.close();
        }
        catch (IOException e)
        { // Swallow Exception
        }
        bufferedReader = null;
      }
    }
  }
  /**
   * Method main.  Test Method to test the Section of the Code.  
   * It adds Dummy data and sends it to the Frame
   * @param args - String[] - Arry of input values
   */
  public static void main( String[] args )
  {
    RaceDataContainer raceDataContain = RaceARamaDB.getAllRacerData();
    RacerPerson rp = new RacerPerson();
    rp.setId(new Integer(420));
    rp.setDistrict("Phoenix");
    rp.setPack("Bear");
    rp.setDen("cubs");
    rp.setLastName("Dude");
    rp.setFirstName("Iama_Kool");
    rp.setAddress("12345 W. Foobar.");
    rp.setCity("Phoenix");
    rp.setState("az");
    rp.setPostal("12345");
    PhoneNumber ph = new PhoneNumber("602", "123-4567");
    rp.setPhone(ph);
    rp.setVehicleNumber("1");
    RacerContainer racerCont = new RacerContainer(rp);
    racerCont.addRaceData(raceDataContain);
    new WebPublishFrame(null, racerCont, "Test").setVisible(true);
  }
}
