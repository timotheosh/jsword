/**
 * Distribution License:
 * JSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 as published by
 * the Free Software Foundation. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The License is available on the internet at:
 *       http://www.gnu.org/copyleft/lgpl.html
 * or by writing to:
 *      Free Software Foundation, Inc.
 *      59 Temple Place - Suite 330
 *      Boston, MA 02111-1307, USA
 *
 * Copyright: 2005
 *     The copyright to this program is held by it's authors.
 *
 * ID: $Id$
 */
package org.crosswire.jsword.book.sword;

import java.io.File;

import org.crosswire.common.activate.Lock;
import org.crosswire.jsword.book.BookException;
import org.crosswire.jsword.passage.DefaultKeyList;
import org.crosswire.jsword.passage.Key;

/**
 * An implementation KeyBackend to read Z format files.
 * LATER(joe): implement ZLDBackend
 * 
 * @see gnu.lgpl.License for license details.
 *      The copyright to this program is held by it's authors.
 * @author Joe Walker [joe at eireneh dot com]
 */
public class ZLDBackend extends AbstractBackend
{
    /**
     * Simple ctor
     */
    public ZLDBackend(SwordBookMetaData sbmd, File rootPath)
    {
        super(sbmd, rootPath);
    }

    /* (non-Javadoc)
     * @see org.crosswire.common.activate.Activatable#activate(org.crosswire.common.activate.Lock)
     */
    public final void activate(Lock lock)
    {
    }

    /* (non-Javadoc)
     * @see org.crosswire.common.activate.Activatable#deactivate(org.crosswire.common.activate.Lock)
     */
    public final void deactivate(Lock lock)
    {
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.sword.KeyBackend#readIndex()
     */
    public Key readIndex()
    {
        return new DefaultKeyList(null, getBookMetaData().getName());
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.sword.AbstractBackend#getRawText(org.crosswire.jsword.passage.Key, java.lang.String)
     */
    public String getRawText(Key key) throws BookException
    {
        // LATER(joe): implement this
        throw new BookException(Msg.COMPRESSION_UNSUPPORTED);
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.sword.AbstractBackend#isSupported()
     */
    public boolean isSupported()
    {
        return false;
    }
}