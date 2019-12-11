package com.boyscouts.util.schedule.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

/**
 * author:      Hans-Jurgen Greiner<BR>
 * Package:     com.boyscouts.util.schedule.table<BR>
 * File Name:   PrintPreview.java<BR>
 * Type Name:   PrintPreview<BR>
 * Description: A JFrame containing all the pages to preview before printing.
 */

public class PrintPreview extends JFrame
{
  /** Field <code>serialVersionUID</code> : long */
  private static final long serialVersionUID = 3546929069284144946L;
  /** Field <code>widthPage</code> : int */
  protected int widthPage;
  /** Field <code>heightPage</code> : int */
  protected int heightPage;
  /** Field <code>pageOrientation</code> : int */
  protected int pageOrientation;
  /** Field <code>printableTarget</code> : Printable */
  protected Printable printableTarget;

  /**
   * Field <code>cmboScale</code> : JComboBox
   * 
   * @uml.property name="cmboScale"
   * @uml.associationEnd multiplicity="(1 1)"
   */
  protected JComboBox cmboScale;

  /**
   * Field <code>previewContainer</code> : PreviewContainer
   * 
   * @uml.property name="previewContainer"
   * @uml.associationEnd multiplicity="(1 1)" inverse="this$0:com.boyscouts.util.schedule.table.PrintPreview$PreviewContainer"
   */
  protected PreviewContainer previewContainer;

  public PrintPreview( Printable target )
  {
    this(target, "Print Preview", PageFormat.PORTRAIT);
  }

  /**
   * Constructor for PrintPreview. 
   * @param target - Printable, the Target for the Printable
   * @param title - String, The Title of the Preview
   * @param orientation - int, the Orientation of the Page
   */
  public PrintPreview( Printable target, String title, int orientation )
  {
    super(title);
    final int STARTING_SCALE = 75;
    setSize(600, 400);
    getContentPane().setLayout(new BorderLayout());
    printableTarget = target;
    pageOrientation = orientation;

    JToolBar tb = new JToolBar();
    ImageIcon ii = new ImageIcon(PrintPreview.class.getResource("/resources/images/printer.gif"));
    JButton bt = new JButton("Print", ii);
    ActionListener lst = new ActionListener() {
      /**
       * Overridden Method:  
       * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
       * @param e
       */
      public void actionPerformed( ActionEvent e )
      {
        try
        {
          PrinterJob prnJob = PrinterJob.getPrinterJob();
          PageFormat pageFormat = prnJob.defaultPage();
          pageFormat.setOrientation(pageOrientation);
          prnJob.setPrintable(printableTarget, pageFormat);

          if (!prnJob.printDialog())
          {
            return;
          }
          setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          prnJob.print();
          setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          dispose();
        }
        catch (PrinterException ex)
        {
          ex.printStackTrace();
          System.err.println("Printing error: " + ex.toString());
        }
      }
    };
    bt.addActionListener(lst);
    bt.setAlignmentY(0.5f);
    bt.setMargin(new Insets(4, 6, 4, 6));
    tb.add(bt);

    bt = new JButton("Close");
    lst = new ActionListener() {
      /**
       * Overridden Method:  
       * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
       * @param e
       */
      public void actionPerformed( ActionEvent e )
      {
        dispose();
      }
    };
    bt.addActionListener(lst);
    bt.setAlignmentY(0.5f);
    bt.setMargin(new Insets(2, 6, 2, 6));
    tb.add(bt);

    String[] scales = {"10 %", "25 %", "50 %", "75 %", "100 %", "150 %", "200 %", "300 %"};
    cmboScale = new JComboBox(scales);
    cmboScale.setSelectedIndex(3);
    lst = new ActionListener() {
      /**
       * Overridden Method:  
       * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
       * @param e
       */
      public void actionPerformed( ActionEvent e )
      {
        Thread runner = new Thread() {
          /**
           * Overridden Method:  
           * @see java.lang.Runnable#run()
           * 
           */
          public void run()
          {
            String str = cmboScale.getSelectedItem().toString();
            if (str.endsWith("%"))
            {
              str = str.substring(0, str.length() - 1);
            }
            str = str.trim();
            int scale = 0;
            try
            {
              scale = Integer.parseInt(str);
            }
            catch (NumberFormatException ex)
            {
              return;
            }
            int w = (int) (widthPage * scale / 100);
            int h = (int) (heightPage * scale / 100);

            Component[] comps = previewContainer.getComponents();
            for (int k = 0; k < comps.length; k++)
            {
              if (!(comps[k] instanceof PagePreview))
              {
                continue;
              }
              PagePreview pp = (PagePreview) comps[k];
              pp.setScaledSize(w, h);
            }
            previewContainer.doLayout();
            previewContainer.getParent().getParent().validate();
          }
        };
        runner.start();
      }
    };
    cmboScale.addActionListener(lst);
    cmboScale.setMaximumSize(cmboScale.getPreferredSize());
    cmboScale.setEditable(true);
    tb.addSeparator();
    tb.add(cmboScale);
    getContentPane().add(tb, BorderLayout.NORTH);

    previewContainer = new PreviewContainer();

    PrinterJob prnJob = PrinterJob.getPrinterJob();
    PageFormat pageFormat = prnJob.defaultPage();
    pageFormat.setOrientation(pageOrientation);

    if (pageFormat.getHeight() == 0 || pageFormat.getWidth() == 0)
    {
      System.err.println("Unable to determine default page size");
      return;
    }
    widthPage = (int) (pageFormat.getWidth());
    heightPage = (int) (pageFormat.getHeight());
    int scale = STARTING_SCALE;
    int w = (int) (widthPage * scale / 100);
    int h = (int) (heightPage * scale / 100);

    int pageIndex = 0;
    try
    {
      boolean done = false;
      while (!done)
      {

        BufferedImage img = new BufferedImage(widthPage, heightPage, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, widthPage, heightPage);
        if (target.print(g, pageFormat, pageIndex) != Printable.PAGE_EXISTS)
        {
          done = true;
          continue;
        }

        PagePreview pp = new PagePreview(w, h, img);
        previewContainer.add(pp);
        pageIndex++;
      }
    }
    catch (PrinterException e)
    {
      e.printStackTrace();
      System.err.println("Printing error: " + e.toString());
    }

    JScrollPane ps = new JScrollPane(previewContainer);
    getContentPane().add(ps, BorderLayout.CENTER);

    centerOnScreen();

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setVisible(true);
  }
  /**
   * Method centerOnScreen.  Centers the Application on the screen, based on the Screen Dimensions
   * and the Dimesions of the Application
   */
  private void centerOnScreen()
  {
    Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();

    Rectangle screenRect = new Rectangle(0, 0, screenDim.width, screenDim.height);

    // Make sure we don't place the demo off the screen.
    int centerWidth = screenRect.width < this.getSize().width ? screenRect.x : screenRect.x + screenRect.width / 2 - this.getSize().width / 2;
    int centerHeight = screenRect.height < this.getSize().height ? screenRect.y : screenRect.y + screenRect.height / 2 - this.getSize().height / 2;

    this.setLocation(centerWidth, centerHeight);
  }

  /**
   * author:      Hans-Jurgen Greiner<BR>
   * Package:     com.boyscouts.util.schedule.table<BR>
   * File Name:   PrintPreview.java<BR>
   * Type Name:   PreviewContainer<BR>
   * Description: The Panel, rendering a sequence of several pages to preview
   */
  class PreviewContainer extends JPanel
  {
    /** Field <code>serialVersionUID</code> : long */
    private static final long serialVersionUID = 4120853256936370739L;
    /** Field <code>H_GAP</code> : int */
    protected int H_GAP = 16;
    /** Field <code>V_GAP</code> : int */
    protected int V_GAP = 10;

    /**
     * Overridden Method:  
     * @see java.awt.Component#getPreferredSize()
     * @return Dimension
     */
    public Dimension getPreferredSize()
    {
      int n = getComponentCount();
      if (n == 0)
      {
        return new Dimension(H_GAP, V_GAP);
      }
      Component comp = getComponent(0);
      Dimension dc = comp.getPreferredSize();
      int w = dc.width;
      int h = dc.height;

      Dimension dp = getParent().getSize();
      int nCol = Math.max((dp.width - H_GAP) / (w + H_GAP), 1);
      int nRow = n / nCol;
      if (nRow * nCol < n)
      {
        nRow++;
      }

      int ww = nCol * (w + H_GAP) + H_GAP;
      int hh = nRow * (h + V_GAP) + V_GAP;
      Insets ins = getInsets();
      return new Dimension(ww + ins.left + ins.right, hh + ins.top + ins.bottom);
    }

    /**
     * Overridden Method:  
     * @see java.awt.Component#getMaximumSize()
     * @return Dimension
     */
    public Dimension getMaximumSize()
    {
      return getPreferredSize();
    }

    /**
     * Overridden Method:  
     * @see java.awt.Component#getMinimumSize()
     * @return Dimension
     */
    public Dimension getMinimumSize()
    {
      return getPreferredSize();
    }

    /**
     * Overridden Method:  
     * @see java.awt.Component#doLayout()
     * 
     */
    public void doLayout()
    {
      Insets ins = getInsets();
      int x = ins.left + H_GAP;
      int y = ins.top + V_GAP;

      int n = getComponentCount();
      if (n == 0)
      {
        return;
      }
      Component comp = getComponent(0);
      Dimension dc = comp.getPreferredSize();
      int w = dc.width;
      int h = dc.height;

      Dimension dp = getParent().getSize();
      int nCol = Math.max((dp.width - H_GAP) / (w + H_GAP), 1);
      int nRow = n / nCol;
      if (nRow * nCol < n)
      {
        nRow++;
      }

      int index = 0;
      for (int k = 0; k < nRow; k++)
      {
        for (int m = 0; m < nCol; m++)
        {
          if (index >= n)
          {
            return;
          }
          comp = getComponent(index++);
          comp.setBounds(x, y, w, h);
          x += w + H_GAP;
        }
        y += h + V_GAP;
        x = ins.left + H_GAP;
      }
    }
  }

  /**
   * author:      Hans-Jurgen Greiner<BR>
   * Package:     com.boyscouts.util.schedule.table<BR>
   * File Name:   PrintPreview.java<BR>
   * Type Name:   PagePreview<BR>
   * Description: The Panel rendering the Page Preview
   */
  class PagePreview extends JPanel
  {
    /** Field <code>serialVersionUID</code> : long */
    private static final long serialVersionUID = 3258413941162980914L;
    /** Field <code>previewWidth</code> : int */
    protected int previewWidth;
    /** Field <code>previewHeight</code> : int */
    protected int previewHeight;
    /** Field <code>imageSource</code> : Image */
    protected Image imageSource;
    /** Field <code>previewImage</code> : Image */
    protected Image previewImage;

    /**
     * Constructor for PagePreview. 
     * @param w - int, the Width
     * @param h - int, the Height
     * @param source - Image, The Source
     */
    public PagePreview( int w, int h, Image source )
    {
      previewWidth = w;
      previewHeight = h;
      imageSource = source;
      previewImage = imageSource.getScaledInstance(previewWidth, previewHeight, Image.SCALE_SMOOTH);
      previewImage.flush();
      setBackground(Color.white);
      setBorder(new MatteBorder(1, 1, 2, 2, Color.black));
    }

    /**
     * Method setScaledSize.  
     * @param w - int, the Width
     * @param h - int, the Height
     */
    public void setScaledSize( int w, int h )
    {
      previewWidth = w;
      previewHeight = h;
      previewImage = imageSource.getScaledInstance(previewWidth, previewHeight, Image.SCALE_SMOOTH);
      repaint();
    }

    /**
     * Overridden Method:  
     * @see java.awt.Component#getPreferredSize()
     * @return Dimension
     */
    public Dimension getPreferredSize()
    {
      Insets ins = getInsets();
      return new Dimension(previewWidth + ins.left + ins.right, previewHeight + ins.top + ins.bottom);
    }

    /**
     * Overridden Method:  
     * @see java.awt.Component#getMaximumSize()
     * @return Dimension
     */
    public Dimension getMaximumSize()
    {
      return getPreferredSize();
    }

    /**
     * Overridden Method:  
     * @see java.awt.Component#getMinimumSize()
     * @return Dimension
     */
    public Dimension getMinimumSize()
    {
      return getPreferredSize();
    }

    /**
     * Overridden Method:  
     * @see java.awt.Component#paint(java.awt.Graphics)
     * @param g - Graphics
     */
    public void paint( Graphics g )
    {
      g.setColor(getBackground());
      g.fillRect(0, 0, getWidth(), getHeight());
      g.drawImage(previewImage, 0, 0, this);
      paintBorder(g);
    }
  }
}
