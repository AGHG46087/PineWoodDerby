/*
 * Created on Dec 8, 2003 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.boyscouts.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.util.GregorianCalendar;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import DaqDigIOAccess.DaqDigIOAccess;

import com.boyscouts.database.RaceARamaDB;
import com.boyscouts.domain.FieldLengths;
import com.boyscouts.domain.Log;
import com.boyscouts.domain.RaceDataContainer;
import com.boyscouts.domain.RacerContainer;
import com.boyscouts.domain.WinnerRaceContainer;
import com.boyscouts.publish.results.ExportTopWinnersTask;
import com.boyscouts.publish.results.WebPublishFrame;
import com.hgmenu.HGMenuItem;
import com.hgmenu.HGMenuListItem;
import com.hgutil.ParseData;
import com.hgutil.datarenderer.HGTableColorModel;
import com.hgutil.swing.themes.ObsidianTheme;

/**
 * RaceARama.java Type Name: RaceARama Description: Main Application Frame for
 * the Race - A - Rama application
 */

public class RaceARama extends JFrame implements FieldLengths
{

  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3258412837339476537L;

  /**
   * Field <code>resourceBundle</code>: ResourceBundle
   * 
   * @uml.property name="resourceBundle"
   */
  private ResourceBundle resourceBundle = null;

  /** Field <code>SIM_REPORT</code> : boolean */
  public static boolean SIM_REPORT = ParseData.parseBool(System.getProperties().getProperty("SIM_REPORT"), false);

  /**
   * Field <code>tabPane</code>: JTabbedPane
   * 
   * @uml.property name="tabPane"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JTabbedPane tabPane = null;

  /**
   * Field <code>splashLabel</code>: JLabel
   * 
   * @uml.property name="splashLabel"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JLabel splashLabel = null;

  /**
   * Field <code>splashScreen</code>: JWindow
   * 
   * @uml.property name="splashScreen"
   * @uml.associationEnd multiplicity="(0 1)"
   */
  private JWindow splashScreen = null;

  /**
   * Field <code>racerViewFrame</code>: RacersViewFrame
   * 
   * @uml.property name="racerViewFrame"
   * @uml.associationEnd multiplicity="(0 1)" inverse="mainApp:com.boyscouts.app.RacersViewFrame"
   */
  private RacersViewFrame racerViewFrame = null;

  /**
   * Field <code>racersContainer</code>: RacerContainer
   * 
   * @uml.property name="racersContainer"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RacerContainer racersContainer = null;

  /**
   * Field <code>currentRaceData</code>: RaceDataContainer
   * 
   * @uml.property name="currentRaceData"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private RaceDataContainer currentRaceData = null;

  /**
   * Field <code>plafMenuGroup</code>: ButtonGroup
   * 
   * @uml.property name="plafMenuGroup"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private ButtonGroup plafMenuGroup = new ButtonGroup();

  /** Field <code>METAL</code>: String */
  private static final String METAL = UIManager.getCrossPlatformLookAndFeelClassName();
  /** Field <code>MOTIF</code>: String */
  private static final String MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
  /** Field <code>WINDOWS</code>: String */
  private static final String WINDOWS = UIManager.getSystemLookAndFeelClassName();
  //"com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
  /** Field <code>STARTUP_METAL_THEME</code>: DefaultMetalTheme */
  protected static final DefaultMetalTheme STARTUP_METAL_THEME = new ObsidianTheme();
  /** Field <code>DEFAULT_METAL_THEME</code>: DefaultMetalTheme */
  protected static final DefaultMetalTheme DEFAULT_METAL_THEME = new DefaultMetalTheme();
  /** Field <code>currentLookAndFeel</code>: String, the current Look and Feel */
  private static String currentLookAndFeel = WINDOWS;
  /** Field <code>raceCount</code>: int */
  private static int raceCount = 0;

  /**
   * Field <code>raceName</code>: String
   * 
   * @uml.property name="raceName"
   */
  private String raceName = null; // "RACE_" + raceCount++;

  /**
   * Field <code>actionTrigger</code>: ActionTrigger
   * 
   * @uml.property name="actionTrigger"
   * @uml.associationEnd multiplicity="(1 1)" inverse="this$0:com.boyscouts.app.RaceARama$ActionTrigger"
   */
  private ActionTrigger actionTrigger = new ActionTrigger();

  /**
   * Field <code>heatNumber</code>: int
   * 
   * @uml.property name="heatNumber"
   */
  private int heatNumber = 0;

  /**
   * Field <code>raceTopRacersItem</code> : JMenuItem
   * 
   * @uml.property name="raceTopRacersItem"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  private JMenuItem raceTopRacersItem = null;

  /**
   * author:      hgrein<BR>
   * Package:     com.boyscouts.app<BR>
   * File Name:   RaceARama.java<BR>
   * Type Name:   ActionTrigger<BR>
   * Description: Provides the mouse click events for the outer class
   */
  private class ActionTrigger implements ActionListener
  {
    /**
     * Overridden Method:
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param evt - ActionEvent, the event being fired.
     */
    public void actionPerformed( ActionEvent evt )
    {
      String cmd = evt.getActionCommand();
      Log.debug("Command recieved: " + cmd);
      if (cmd.equals(getString("RaceARama.MenuBar.ToolsMenu.webpublish_label")))
      { // Publish the results of the Race to set of Web Pages.
        publishRaceResults();
      }
      else if (cmd.equals(getString("RaceARama.MenuBar.ToolsMenu.racer_view_label")))
      { // We are going to show the racersViewFrame - so they can watch as well
        if (RaceARama.this.racerViewFrame == null)
        {
          RaceARama.this.racerViewFrame = new RacersViewFrame(RaceARama.this);
        }
        if (!RaceARama.this.racerViewFrame.isShowing())
        { // Pack it down and Show it baby
          // RaceARama.this.racerViewFrame.pack();
          RaceARama.this.racerViewFrame.setVisible(true);
        }
      }
      else if (cmd.equals(getString("RaceARama.MenuBar.FileMenu.new_race_label")))
      { // Setup new Race Data
        setupNewRaceData(false); // Not a final Race
        //renameCurrentRaceName();
      }
      else if (cmd.equals(getString("RaceARama.MenuBar.FileMenu.rename_race_label")))
      { // Rename the Race Title
        renameCurrentRaceName();
      }
      else if (cmd.equals(getString("RaceARama.MenuBar.FileMenu.race_top_racers")))
      { // Race the Top Racers.
        raceTheTopRacersOfAllRacers();
      }
      else if (cmd.equals(getString("RaceARama.MenuBar.FileMenu.top_count_label")))
      { // Change the Count of the Top Racers.
        changeCurrentTopCount();
      }
      else if (cmd.equals(getString("RaceARama.MenuBar.FileMenu.exit_race_label")))
      { // Cleanup and Exit the Application
        cleanupOnExit();
      }
      else if (cmd.equals(getString("RaceARama.MenuBar.FileMenu.export_top_racers")))
      { // Execute the method A-Synchronously
        exportTopWinners(true);
      }
      else
      {
        JOptionPane.showMessageDialog(RaceARama.this, "ActionCmd: [" + cmd + "] option not supported at this time", "information",
                                      JOptionPane.INFORMATION_MESSAGE);
      }
    }

  }

  /**
   * author:      hgrein<BR>
   * Package:     com.boyscouts.app<BR>
   * File Name:   RaceARama.java<BR>
   * Type Name:   ChangeLookAndFeelAction<BR>
   * Description: Inner Class to change the Look And Feel
   */

  private class ChangeLookAndFeelAction extends AbstractAction
  {

    /** Field <code>serialVersionUID</code> : long */
    private static final long serialVersionUID = 3833749884515071540L;

    /**
     * 
     * @uml.associationEnd multiplicity="(0 1)"
     */
    RaceARama mainApp;

    String plaf;
    /**
     * Method ChangeLookAndFeelAction. Class Contructor
     * @param mainApp - RaceARama
     * @param plaf
     */
    protected ChangeLookAndFeelAction( RaceARama mainApp, String plaf )
    {
      super("ChangeL&F");
      this.mainApp = mainApp;
      this.plaf = plaf;
    }

    /**
     * Method listens for a change Look and Feel action
     * @see ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed( ActionEvent e )
    {
      mainApp.setLookAndFeel(plaf);
    }
  }

  /**
   * author:      hgrein<BR>
   * Package:     com.boyscouts.app<BR>
   * File Name:   RaceARama.java<BR>
   * Type Name:   TabListener<BR>
   * Description: A Tab Pane Listener to repaint the Display and take any 
   * other actions required if the Tab Pane selection should change.
   */
  private class TabListener implements ChangeListener
  {
    private int lastIndex = -1;
    /**
     * Listener for the TabPane on which Tab is selected. If a Tab is selected
     * different from what is already selected then it will repaint the the Tab
     * Display.
     * @see ChangeListener#stateChanged(ChangeEvent)
     */
    public void stateChanged( ChangeEvent e )
    {
      // If the source is not the JTabbedPane, then return
      if (!(e.getSource() instanceof JTabbedPane))
      {
        return;
      }
      JTabbedPane theTabPane = (JTabbedPane) e.getSource();
      // Get the selected index.
      int index = theTabPane.getSelectedIndex();
      // If we are the same index, then simply return, as we are already
      // processing for the this tab
      if (index == this.lastIndex)
      { // Return - As this is not changing
        return;
      }
      this.lastIndex = index;
      // Record our new index if it is at least 0 and change the refresh the
      // Display
      java.awt.Component comp = RaceARama.this.tabPane.getSelectedComponent();
    }
  }
  /**
   * Method getRaceCount(). returns the RaceCount
   * @return Returns the raceCount.
   */
  public static int getRaceCount()
  {
    return raceCount;
  }
  /**
   * Method loadSimulationData. This method is only called if the simulation
   * flag is true
   */
  private static RacerContainer loadSimulationData()
  {
    if (!SIM_REPORT)
    {
      return new RacerContainer();
    }
    JOptionPane.showMessageDialog(null, "Simulation Data will loaded from the database,\nas the application is being run in simultion mode.",
                                  "SIMULATION MODE", JOptionPane.INFORMATION_MESSAGE);
    com.boyscouts.domain.RaceDataContainer raceDataContain = RaceARamaDB.getAllRacerData();
    com.boyscouts.domain.RacerPerson rp = new com.boyscouts.domain.RacerPerson();
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
    com.boyscouts.domain.PhoneNumber ph = new com.boyscouts.domain.PhoneNumber("602", "123-4567");
    rp.setPhone(ph);
    rp.setVehicleNumber("1");
    rp.addRaceData(raceDataContain);
    RacerContainer racerCont = new RacerContainer(rp);

    rp = new com.boyscouts.domain.RacerPerson();
    rp.setId(new Integer(421));
    rp.setDistrict("Phoenix");
    rp.setPack("134");
    rp.setDen("tiger");
    rp.setLastName("Dudette");
    rp.setFirstName("Iama_Dudette");
    rp.setAddress("12345 W. Foobar.");
    rp.setCity("Phoenix");
    rp.setState("az");
    rp.setPostal("12345");
    ph = new com.boyscouts.domain.PhoneNumber("602", "123-4567");
    rp.setPhone(ph);
    rp.setVehicleNumber("2");
    rp.addRaceData(raceDataContain);
    racerCont.addRacerPerson(rp);
    // Assign the simultion data to the container
    return racerCont;
  }
  /**
   * Constructor for RaceARama. Initilaizes the Window.
   */
  public RaceARama()
  {
    setLookAndFeel(METAL);
    showSplashScreen();
    // Load the preliminary Data
    setupNewRaceData(false); // not a final race
    this.setJMenuBar(createMenuBar());
    createAndPopulateTabPane();
    Container cont = this.getContentPane();
    cont.setLayout(new BorderLayout());
    cont.add(tabPane);
    this.setSize(610, 400);
    centerOnScreen(this);
    delay(4000);
    hideSplashScreen();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.addWindowListener(new WindowAdapter() {
      /**
       * Overridden Method:  
       * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
       * @param evt
       */
      public void windowClosing( WindowEvent evt )
      {
        cleanupOnExit();
      }
    });
  }
  /**
   * Method centerOnScreen. Centers the Application on the screen, based on the
   * Screen Dimensions and the Dimesions of the Application
   * @param comp - JFrame, the Frame to be centered.
   */
  private void centerOnScreen( JFrame comp )
  {
    Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle screenRect = new Rectangle(0, 0, screenDim.width, screenDim.height);

    // Make sure we don't place the demo off the screen.
    int centerWidth = screenRect.width < comp.getSize().width ? screenRect.x : screenRect.x + screenRect.width / 2 - comp.getSize().width / 2;
    int centerHeight = screenRect.height < comp.getSize().height ? screenRect.y : screenRect.y + screenRect.height / 2 - comp.getSize().height / 2;
    comp.setLocation(centerWidth, centerHeight);
  }
  /**
   * Method centerOnScreen. Centers the Window on the Screeen
   * @param comp - JWindow, the Window to be centered on the screen
   */
  private void centerOnScreen( JWindow comp )
  {
    Dimension sDim = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) (sDim.getWidth() - comp.getPreferredSize().width) / 2;
    int height = (int) (sDim.getHeight() - comp.getPreferredSize().height) / 2;
    comp.setLocation(width, height);
  }
  /**
   * Method changeCurrentTopCount.  Changes the Top Racer Count to a value desired by the user
   */
  private void changeCurrentTopCount()
  {
    String topRaceCount = "" + WinnerRaceContainer.getTopRaceSize();
    String tempResult = JOptionPane.showInputDialog(this, "Change Top Racer Count [" + topRaceCount + "]", topRaceCount);
    WinnerRaceContainer.setTopRaceSize(tempResult);
  }

  /**
   * Method cleanupOnExit.  This method will perform any cleanup required. and Terminate the program.
   * It is synchronized, so that any Outstanding threads.  Are blocked while this is performing 
   * its termination.
   */
  private synchronized void cleanupOnExit()
  {
    DaqDigIOAccess.getInstance().destroy();
    WinnerRaceContainer.getInstance().purgeSavedData();
    System.exit(0);
  }
  /**
   * Method createAndPopulateTabPane. Populates the instance variable Tab Pane
   */
  private void createAndPopulateTabPane()
  {
    tabPane = new JTabbedPane();
    tabPane.addChangeListener(new TabListener());
    Vector moduleTitles = getModulePanelTitles();
    Vector modulePanels = getModulePanelList();
    for (int i = 0; i < modulePanels.size(); i++)
    {
      String title = "" + moduleTitles.get(i);
      JPanel panel = (JPanel) modulePanels.get(i);
      tabPane.add(title, panel);
    }
  }
  /**
   * Creates an icon from an image contained in the "images" directory.
   * @return ImageIcon
   */
  public ImageIcon createImageIcon( String filename )
  {
    String path = "/resources/images/" + filename;
    Log.debug("createImageIcon() - ATTEMPTING load image " + path);
    ImageIcon ii = new ImageIcon(getClass().getResource(path));
    Log.debug("createImageIcon() - Image is " + ii);
    return ii;
  }
  /**
   * Method createMenuBar. Creates the Menu Bar for the Application
   * @return JMenuBar
   */
  private JMenuBar createMenuBar()
  {
    JMenuBar menuBar = null;
    // Create four individual check button menu items for L&F, and add
    // to the button group
    HGMenuItem motifRadio = new HGMenuItem(HGMenuListItem.JRADIOBTNMNUITEM, getString("RaceARama.MenuBar.PlafMenu.motif_label"),
                                           getString("RaceARama.MenuBar.PlafMenu.motif_actionCommand"), null, KeyEvent.VK_M, InputEvent.CTRL_MASK,
                                           plafMenuGroup, false, new ChangeLookAndFeelAction(this, MOTIF));
    HGMenuItem metalRadio = new HGMenuItem(HGMenuListItem.JRADIOBTNMNUITEM, getString("RaceARama.MenuBar.PlafMenu.metal_label"),
                                           getString("RaceARama.MenuBar.PlafMenu.metal_actionCommand"), null, KeyEvent.VK_J, InputEvent.CTRL_MASK,
                                           plafMenuGroup, true, new ChangeLookAndFeelAction(this, METAL));
    HGMenuItem nativeRadio = new HGMenuItem(HGMenuListItem.JRADIOBTNMNUITEM, getString("RaceARama.MenuBar.PlafMenu.windows_label"),
                                            getString("RaceARama.MenuBar.PlafMenu.windows_actionCommand"), null, KeyEvent.VK_W, InputEvent.CTRL_MASK,
                                            plafMenuGroup, false, new ChangeLookAndFeelAction(this, WINDOWS));

    JMenu plafMenu = HGMenuItem.makeMenu("Look & Feel", 'L', new Object[]{metalRadio, motifRadio, nativeRadio}, null);
    String webPublishItem = getString("RaceARama.MenuBar.ToolsMenu.webpublish_label");
    String racerViewItem = getString("RaceARama.MenuBar.ToolsMenu.racer_view_label");
    JMenu toolsMenu = HGMenuItem.makeMenu("Tools", 'T', new Object[]{racerViewItem, webPublishItem}, actionTrigger);
    String newRaceItem = getString("RaceARama.MenuBar.FileMenu.new_race_label");
    String renameRaceItem = getString("RaceARama.MenuBar.FileMenu.rename_race_label");
    String topCountItem = getString("RaceARama.MenuBar.FileMenu.top_count_label");
    String exitRaceItem = getString("RaceARama.MenuBar.FileMenu.exit_race_label");

    WinnerRaceContainer.setTopRaceSize(getString("RaceARama.MenuBar.FileMenu.top_count_value"));
    String raceTopRacersString = getString("RaceARama.MenuBar.FileMenu.race_top_racers");
    String exportTopRacers = getString("RaceARama.MenuBar.FileMenu.export_top_racers");
lRacers()
    raceTopRacersItem = HGMenuItem.makeMenuItem(raceTopRacersString, raceTopRacersString, null);

    JMenu fileMenu = HGMenuItem.makeMenu("File", 'F', new Object[]{newRaceItem, renameRaceItem, null, topCountItem, raceTopRacersItem, exportTopRacers, null,
                                                                   exitRaceItem}, actionTrigger);
    menuBar = new JMenuBar();
    menuBar.add(fileMenu);
    menuBar.add(plafMenu);
    menuBar.add(toolsMenu);
    return menuBar;
  }
  /**
   * Create the spash screen while the rest of the demo loads
   */
  private void createSplashScreen()
  {
    Log.debug("createSplashScreen() - Creating Splash Screen...");
    String splashImageName = getString("RaceARama.JFrame.SplashImage");
    //String splashImageName = "ILoveSpeed.jpg";
    splashLabel = new JLabel(createImageIcon(splashImageName));
    splashScreen = new JWindow(this);
    splashScreen.getContentPane().add(splashLabel);
    splashScreen.pack();
    centerOnScreen(splashScreen);
    Log.debug("createSplashScreen() - Creating Splash Screen...Done");
    delay(200);
    splashScreen.setVisible(true);
  }
  /**
   * Method delay. Sets a Delay for a Period specified in millis
   * @param millis - long, the time to eait in millis
   */
  private void delay( long millis )
  {
    try
    {
      Thread.sleep(millis);
    }
    catch (InterruptedException exc)
    {
    }
  }
  /**
   * Method exportTopWinners.  
   * @param exportInAThread - boolean, true to execute the export as a Seperate Thread, false to perform the operation synchronous 
   */
  private void exportTopWinners( boolean exportInAThread )
  {
    if (exportInAThread)
    {
      Timer t = new Timer();
      t.schedule(new ExportTopWinnersTask(RaceARama.this), 500);
    }
    else
    {
      new ExportTopWinnersTask(RaceARama.this).exportTopRacersToCSV();
    }
  }
  /**
   * racerViewFrame does not exist. Then we will simply tell the racer container
   * maintain the current winners. If it does exist. Then signal the frame to
   */
  {
    if (racerViewFrame != null)
    {
    }
    {
      WinnerRaceContainer.getInstance().addRacerContainer(getRaceName(), racersContainer);
    }
    raceTopRacersItem.setEnabled(WinnerRaceContainer.getInstance().getRaceKeys().size() > 0);
  }

  /**
   * Method getcurrentRaceData. Returns currentRaceData of the RaceARama
   * @return RaceDataContainer: Returns the currentRaceData.
   * 
   * @uml.property name="currentRaceData"
   */
  public RaceDataContainer getCurrentRaceData()
  {
    return currentRaceData;
  }

  /**
   * Method getheatNumber. Returns heatNumber of the RaceARama
   * @return int: Returns the heatNumber.
   * 
   * @uml.property name="heatNumber"
   */
  public int getHeatNumber()
  {
    return heatNumber;
  }

  /**
   * Method getModulePanelList. Returns a list of Module Panels to load
   * @return Vector
   */
  private Vector getModulePanelList()
  {
    // Get a List of all the available Module to load
    Vector moduleList = new Vector();
    String moduleNames = null;
    int count = 0;
    do
    {
      String key = "RaceARama.module_" + count + ".panel";
      moduleNames = getModuleString(key);
      if (moduleNames != null && !moduleNames.trim().equals(""))
      {
        moduleList.addElement(moduleNames);
      }
      count++;
    }
    while (moduleNames != null);
    Vector loadedModules = loadModules(moduleList);
    return loadedModules;
  }
  /**
   * Method getModulePanelList. Returns a list of Module Panels to load
   * @return Vector
   */
  private Vector getModulePanelTitles()
  {
    // Get a List of all the available Module to load
    Vector moduleList = new Vector();
    String moduleNames = null;
    int count = 0;
    do
    {
      String key = "RaceARama.module_" + count + ".title";
      moduleNames = getModuleString(key);
      if (moduleNames != null && !moduleNames.trim().equals(""))
      {
        moduleList.addElement(moduleNames);
      }
      count++;
    }
    while (moduleNames != null);
    return moduleList;
  }
  /**
   * This method returns a string from the demo's resource bundle.
   * @param key The Key in the resource bundle
   */
  public String getModuleString( String key )
  {
    Log.debug("getModuleString(" + key + ") - retrieving Key...");
    String value = null;
    try
    {
      value = getResourceBundle().getString(key);
    }
    catch (MissingResourceException e)
    {
      Log.logError("java.util.MissingResourceException: " + "Couldn't find value for: " + key);
    }
    Log.debug("getModuleString(" + key + ") - value ==> " + value);
    Log.debug("getModuleString(" + key + ") - retrieving Key...Done");
    return value;
  }

  /**
   * Method getraceName. Returns raceName of the RaceARama
   * @return String: Returns the raceName.
   * 
   * @uml.property name="raceName"
   */
  public String getRaceName()
  {
    return raceName;
  }

  /**
   * Method getRacersContainer. Returns racersContainer of the RaceARama
   * @return RacerContainer: Returns the racersContainer.
   * 
   * @uml.property name="racersContainer"
   */
  public RacerContainer getRacersContainer()
  {
    return racersContainer;
  }

  /**
   * Method getResourceBundle.
   * @return Returns the resourceBundle : ResourceBundle.
   * 
   * @uml.property name="resourceBundle"
   */
  protected ResourceBundle getResourceBundle()
  {
    if (resourceBundle == null)
    {
      resourceBundle = ResourceBundle.getBundle("resources.boy_scouts_race_prog");
    }
    return resourceBundle;
  }

  /**
   * Method getString. Returns a String specified in the Key
   * @param key - String, the Key in the lookup
   * @return String, the Result
   */
  public String getString( String key )
  {
    Log.debug("getString(" + key + ") - retrieving Key...");
    String value = null;
    try
    {
      value = getResourceBundle().getString(key);
    }
    catch (MissingResourceException e)
    {
      Log.logError("java.util.MissingResourceException: " + "Couldn't find value for: " + key);
    }
    if (value == null)
    {
      value = "'" + key + "'";
    }
    Log.debug("getString(" + key + ") - value ==> " + value);
    Log.debug("getString(" + key + ") - retrieving Key...Done");
    return value;
  }
  /**
   * Method hideSplashScreen. Hides the Splash Screen and Sets the Variables
   * Eligible for Garbage Collection.
   */
  private void hideSplashScreen()
  {
    Log.debug("hideSplash() - Hiding Splash Screen...");
    splashScreen.setVisible(false);
    splashScreen = null;
    splashLabel = null;
    Log.debug("hideSplash() - Hiding Splash Screen...Done");
  }
  /**
   * Method incrementHeatNumber. Sets heatNumber of the RaceARama
   */
  public void incrementHeatNumber()
  {
    this.heatNumber += 1;
  }
  /**
   * Method loadModules. Loads all the Modules Specified in the List of formal
   * Parameter And Returns a Vecotr of the Loaded Modules In Memory
   * @param moduleList - Vector, The List of Modules to load
   * @return Vector, the List of Modules loaded in memory
   */
  private Vector loadModules( Vector moduleList )
  {
    Vector moduleVector = new Vector();
    Log.debug("loadModules() - Loading modules...");
    for (int i = 0; i < moduleList.size(); i++)
    {
      String moduleName = (String) moduleList.elementAt(i);
      Object obj = loadTheModule(moduleName);
      if (obj != null)
      {
        moduleVector.add(obj);
      }
    }
    Log.debug("loadModules() - Loading modules...Done");
    return moduleVector;
  }
  /**
   * Loads a Module from a classname. Pay Attentiton to this Method. Note that
   * it uses Reflection to Load the Classes. It Has the Class Loader load the
   * entire class into memory. Then It looks for a Constructor. Where the
   * Constructor has a parameter, of the RaceARama Application. Once it finds
   * the Constructor it will create a new instance of the module, using the
   * instance of RaceARama. then it will add it to the List of Available
   * Modules.
   * @param classname String - The Class to load,
   * @return Object, the Module to be loaded
   */
  private Object loadTheModule( String classname )
  {
    Log.debug("loadTheModule(" + classname + ") - Loading the module...");
    Object module = null;
    final String MAINPACKAGE = this.getClass().getPackage().getName();
    try
    {
      String packageName = "";
      if (!classname.startsWith(MAINPACKAGE))
      {
        packageName += MAINPACKAGE + "."; // Add A Terminating Period
      }
      packageName += classname;
      Class demoClass = Class.forName(packageName);
      Constructor demoConstructor = demoClass.getConstructor(new Class[]{RaceARama.class});
      module = demoConstructor.newInstance(new Object[]{this});
    }
    catch (Exception e)
    {
      Log.logError("Error occurred loading demo: " + classname);
    }
    Log.debug("loadTheModule(" + classname + ") - Loading the module...Done");
    return module;
  }
  /**
   * Method publishRaceResults. Sends a Message to the WebPublish Frame to
   * publish all results
   */
  public void publishRaceResults()
  {
    // Load the Web Publish Frame and Display it
    RacerContainer racerCont = getRacersContainer();
    if (SIM_REPORT)
    {
      racerCont = loadSimulationData();
    }
    else
    {
      Timer timer = new Timer();
    }
    WebPublishFrame webPublish = new WebPublishFrame(RaceARama.this, racerCont, this.raceName);
    webPublish.setVisible(true);
  }
  /**
   * Method raceTheTopRacersOfAllRacers.  This method is used to initiate a setup of the Top Racers.
   * Before this done a Confirmation of the process will be promoted to the user.
   */
  private void raceTheTopRacersOfAllRacers()
  {
    int topRacerCount = WinnerRaceContainer.getTopRaceSize();
    Vector keys = WinnerRaceContainer.getInstance().getRaceKeys();
    String keyList = "";
    for (int i = 0; i < keys.size(); i++)
    {
      keyList += "<FONT face=arial color=#FFFF22>" + keys.elementAt(i) + "</FONT><br>";
    }
    String temp = "<html>Are you sure you want to begin a " + "<FONT face=arial color=#FFFF22>Race of the Top Racers</FONT><br>"
                  + "This is Permanent, The Top <FONT face=arial color=#FFFF22>" + topRacerCount + "</FONT> racers of <FONT face=arial color=#FFFF22>"
                  + raceCount + "</FONT> Race(s)<br> will be added to the Registration Panel and "
                  + "the list will be cleared.<br>  You may ADD or REMOVE Racers from the "
                  + "Registration panel.<br> If this option has been chosen in error, " + "click \"Cancel\".<br> The List of Races to be processed are: <br>"
                  + keyList + "<br></html>";

    int n = JOptionPane.showConfirmDialog(this, temp, "Registered top Racers of All Races", JOptionPane.OK_CANCEL_OPTION);
    // If the User Selected YES.  Meaning that the Table is going to process a new set of racers.
    // then We can continue the process.
    if (n == JOptionPane.YES_OPTION)
    {
      temp = "<html><FONT face=arial color=#FFFF22>Would you like to export the Top Racers to an CSV ( Comma Seperated Vector)<BR> for later in use in programs such as Excel.</FONT></html>";
      n = JOptionPane.showConfirmDialog(this, temp, "Export Top Racers", JOptionPane.YES_NO_OPTION);
      if (n == JOptionPane.YES_OPTION)
      { // Execute the method Synchronously
        exportTopWinners(false);
      }
      // Set up a new Race, and inidication that this is the final Race.
      setupNewRaceData(true);
      RegistrationPanel regPanel = getRegistrationPanel();
      regPanel.setupRaceOfTopRacers();

      raceTopRacersItem.setEnabled(false);
    }
  }
  /**
   * Method getRegistrationPanel.  Returns the RegistrationPanel from the Tab Pane.
   * @return RegistrationPanel
   */
  private RegistrationPanel getRegistrationPanel()
  {
    Component[] tabComps = tabPane.getComponents();
    Component comp = null;
    for (int i = 0; i < tabComps.length; i++)
    {
      if (tabComps[i] instanceof RegistrationPanel)
      {
        comp = tabComps[i];
        tabPane.setSelectedIndex(i);
      }
    }
    return (RegistrationPanel) comp;
  }
  /**
   * Method renameCurrentRaceName.  Renames the Current Race to the New Name Desired by the User
   */
  private void renameCurrentRaceName()
  {
    String currRaceName = this.getRaceName();
    String tempResult = JOptionPane.showInputDialog(this, "Rename Race [" + currRaceName + "]", currRaceName);
    setRaceName(tempResult);
    if (racersContainer != null)
    {
      racersContainer.setRaceName(this.getRaceName());
    }
  }
  /**
   * @param laf The Loof and Feel constant we want to display
   */
  private void setLookAndFeel( String laf )
  {
    Log.debug("setLookAndFeel() - Setting Look and Feel to..." + laf);
    if (!currentLookAndFeel.equals(laf))
    {
      if (METAL.equals(laf))
      {
        MetalLookAndFeel.setCurrentTheme(STARTUP_METAL_THEME);
        HGTableColorModel.getInstance().setTheme(STARTUP_METAL_THEME);
      }
      else
      {
        MetalLookAndFeel.setCurrentTheme(DEFAULT_METAL_THEME);
        HGTableColorModel.getInstance().setTheme(DEFAULT_METAL_THEME);
      }
      currentLookAndFeel = laf;
    }
    Log.debug("setLookAndFeel() - Setting Look and Feel...Done");
  }

  /**
   * Method setRaceName.  Sets the Race Name
   * @param newRaceName - String, the New Race Name
   * 
   * @uml.property name="raceName"
   */
  private void setRaceName( String newRaceName )
  {
    if (newRaceName != null && newRaceName.trim().length() > 0)
    {
      raceName = newRaceName.trim();
      this.setTitle(getString("RaceARama.JFrame.title") + " - " + raceName);
    }
  }

  /**
   * Method setupNewRaceData. Provides the Capability to setup a new Race First
   * by querying the database, based on data that it knows. Followed by
   * creating a data vector of details.  If the race is a new Race, and we have more than 1
   * races that have been counted.  Then allow for a rename of the race.  
   * NOTE:  You can utilize this feature to retrieve a starting point in case of power failure.
   * @param raceIsFinal -boolean, true if the race is the final top racers of al racers, otherwise use false.
   */
  private void setupNewRaceData( boolean raceIsFinal )
  {
    setRaceName("RACE_" + raceCount++);

    if (raceIsFinal)
    {
      setRaceName("FINAL_RACE");
      racersContainer = new RacerContainer();
    }
    else
    {
      if (raceCount > 1)
      {
        renameCurrentRaceName();
      }
      // Load Preliminary Data
      racersContainer = RaceARamaDB.getAllRacers(new GregorianCalendar(), this.getRaceName());
    }
    racersContainer.setRaceName(this.getRaceName());
    // Create a Vector for the Race Schedule
    // Create a Empty RaceDataContainer
    currentRaceData = new RaceDataContainer();
    this.heatNumber = 0;
    // If we have the Racers View Up, then we know what to do.
    if (this.racerViewFrame != null)
    {
    }
    if (tabPane != null)
    {
      tabPane.setSelectedIndex(0);
      getRegistrationPanel();
    }

  }
  /**
   * Method showSplashScreen. Shows the Splash Screen in a backround Thread Task
   */
  private void showSplashScreen()
  {
    /**
     * @author:     hgrein<BR>
     * Package:     com.boyscouts.app<BR>
     * File Name:   RaceARama.java<BR>
     * Type Name:   ShowSplashScreenTask<BR>
     * Description: A Timer Task to Display the Splash Screen
     */
    class ShowSplashScreenTask extends TimerTask
    {
      /**
       * Overridden Method:
       * @see java.lang.Runnable#run()
       */
      public void run()
      {
        createSplashScreen();
        this.cancel();
      }
    }
    Timer timer = new Timer();
    timer.schedule(new ShowSplashScreenTask(), 100);
  }

  /**
   */
  {
    try
    {
      UIManager.setLookAndFeel(currentLookAndFeel);
      if (racerViewFrame != null)
      {
      }
    }
    catch (Exception ex)
    {
      System.out.println("Failed loading L&F: " + currentLookAndFeel);
      System.out.println(ex);
    }
  }

  /**
   * Method main.
   * @param args
   */
  public static void main( String[] args )
  {
    new RaceARama().setVisible(true);
  }
}
