/*
 * The MIT License
 * 
 * Copyright (c) <year> <copyright holders>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.merge.folder;

import java.beans.PropertyChangeListener;

/**
 *
 * @author Tushar Joshi <tusharvjoshi@gmail.com>
 */
public interface Item {

    public static final int STATE_UNCHECKED = 0;
    public static final int STATE_SAME = 1;
    public static final int STATE_OLD = 2;
    public static final int STATE_NEW = 3;
    public static final int STATE_ORPHAN = 4;
    public static final int STATE_NOTAVAILABLE = 5;
    public static final int STATE_NEWOLD = 6;

    public String getStateDescription(int state);

    public int getLeftState();

    public void setLeftState(int state);

    public int getRightState();

    public void setRightState(int state);
    public String getAbsolutePath();
    public void setAbsolutePath(String path);
    public String getName();

    public void setName(String name);

    public String getLeftPath();

    public void setLeftPath(String path);

    public String getRightPath();

    public void setRightPath(String path);

    public boolean isLeaf();

    public void setLeaf(boolean value);

    public int getChildCount();

    public void clearChildren();

    public void addChild(Item childItem);

    public Item getChild(int index);

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);
}
