
package org.crosswire.jsword.view.swing.book;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import org.crosswire.jsword.book.Bible;
import org.crosswire.jsword.book.BookException;
import org.crosswire.jsword.passage.Passage;
import org.crosswire.jsword.view.style.Style;
import org.crosswire.common.util.Logger;
import org.crosswire.common.util.LogicError;
import org.crosswire.common.util.Reporter;

/**
 * An inner component of Passage pane that can't show the list.
 *
 * <table border='1' cellPadding='3' cellSpacing='0' width="100%">
 * <tr><td bgColor='white'class='TableRowColor'><font size='-7'>
 * Distribution Licence:<br />
 * Project B is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License,
 * version 2 as published by the Free Software Foundation.<br />
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br />
 * The License is available on the internet
 * <a href='http://www.gnu.org/copyleft/gpl.html'>here</a>, by writing to
 * <i>Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston,
 * MA 02111-1307, USA</i>, Or locally at the Licence link below.<br />
 * The copyright to this program is held by it's authors.
 * </font></td></tr></table>
 * @see <a href='http://www.eireneh.com/servlets/Web'>Project B Home</a>
 * @see <{docs.Licence}>
 * @author Joe Walker
 * @version $Id$
 */
public class PassageTabbedPane extends JPanel
{
    /**
     * Simple Constructor
     */
    public PassageTabbedPane()
    {
        jbInit();
    }

    /**
     * Gui creation
     */
    private void jbInit()
    {
        tab_main.setTabPlacement(JTabbedPane.BOTTOM);
        tab_main.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent ev) { newTab(ev); }
        });

        this.setLayout(new BorderLayout());
        this.add(pnl_view, BorderLayout.CENTER);
        center = pnl_view;
    }

    /**
     * Set the version used for lookup
     */
    public void setVersion(Bible version)
    {
        this.version = version;
        pnl_view.setVersion(version);
    }

    /**
     * Set the passage being viewed
     */
    public void setPassage(Passage ref) throws IOException, SAXException, BookException, TransformerException
    {
        this.whole = ref;

        try
        {
            // Tabbed view or not we should clear out the old tabs
            tab_main.removeAll();

            // Do we need a tabbed view
            if (ref.countVerses() > page_size)
            {
                // Calc the verses to display in this tab
                Passage cut = (Passage) whole.clone();
                waiting = cut.trimVerses(page_size);
                String tabname = cut.getName();
                int len = tabname.length();
                if (len > 25)
                {
                    tabname = tabname.substring(0, 9)
                            + " ... "
                            + tabname.substring(len-9, len);
                }

                // Create the tab
                PassageInnerPane pnl_new = new PassageInnerPane();
                pnl_new.setVersion(version);
                pnl_new.setPassage(cut);
                tab_main.add(pnl_new, tabname);
                tab_main.add(pnl_more, "More ...");

                // And show it is needed
                if (center != tab_main)
                {
                    this.remove(center);
                    this.add(tab_main, BorderLayout.CENTER);
                    center = tab_main;
                }
            }
            else
            {
                // Setup the front tab
                pnl_view.setPassage(ref);

                // And show it if needed
                if (center != pnl_view)
                {
                    this.remove(center);
                    this.add(pnl_view, BorderLayout.CENTER);
                    center = pnl_view;
                }
            }
        }
        catch (CloneNotSupportedException ex)
        {
            throw new LogicError(ex);
        }
    }

    /**
     * Tabs changed, generate some stuff
     */
    private void newTab(ChangeEvent ev)
    {
        try
        {
            // This is someone clicking on more isnt it?
            if (tab_main.getSelectedComponent() != pnl_more)
                return;

            // First remove the old more ... tab that the user has just selected
            tab_main.remove(pnl_more);

            // Calculate the new verses to display
            Passage cut = waiting;
            waiting = cut.trimVerses(page_size);
            String tabname = cut.getName();
            int len = tabname.length();
            if (len > 25)
            {
                tabname = tabname.substring(0, 9)
                        + " ... "
                        + tabname.substring(len-9, len);
            }


            // Create a new tab
            PassageInnerPane pnl_new = new PassageInnerPane();
            pnl_new.setVersion(version);
            pnl_new.setPassage(cut);
            tab_main.add(pnl_new, tabname);

            // Do we need a new more tab
            if (waiting != null)
            {
                tab_main.add(pnl_more, "More ...");
            }

            // Select the real new tab in place of any more tabs
            tab_main.setSelectedComponent(pnl_new);
        }
        catch (Exception ex)
        {
            Reporter.informUser(this, ex);
        }
    }

    /**
     * Accessor for the page size
     */
    public void setPageSize(int page_size)
    {
        this.page_size = page_size;
    }

    /**
     * Accessor for the page size
     */
    public int getPageSize()
    {
        return page_size;
    }

    /**
     * Forward the mouse listener to our child components
     */
    public void removeMouseListener(MouseListener li)
    {
        pnl_view.removeMouseListener(li);
        tab_main.removeMouseListener(li);
    }

    /**
     * Forward the mouse listener to our child components
     */
    public void addMouseListener(MouseListener li)
    {
        pnl_view.addMouseListener(li);
        tab_main.addMouseListener(li);
    }

    // Should this be a static?
    private int page_size = 30;

    /** What is being displayed */
    private Passage whole = null;
    private Passage waiting = null;
    private Bible version = null;
    private Style style = new Style("swing");

    /** The log stream */
    protected static Logger log = Logger.getLogger("bible.view");

    private JTabbedPane tab_main = new JTabbedPane();
    private JPanel pnl_more = new JPanel();
    private PassageInnerPane pnl_view = new PassageInnerPane();
    private Component center = null;
    private int pageSize;
}