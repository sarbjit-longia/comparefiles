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


import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Tushar Joshi <tusharvjoshi@gmail.com>
 */
public class RightTreeModel  implements TreeModel {

    private final RightItem rootItem;

    public RightTreeModel(Item item) {
        this.rootItem = new RightItem( item);
    }

    public Object getRoot() {
        return rootItem;
    }

    public Object getChild(Object parent, int index) {
        return ((RightItem)parent).getChild(index);
    }

    public int getChildCount(Object parent) {
        return ((Item)parent).getChildCount();
        
    }

    public boolean isLeaf(Object node) {
        return ((RightItem)node).isLeaf();
    }

    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    public int getIndexOfChild(Object parent, Object child) {
        return 0;
    }

    public void addTreeModelListener(TreeModelListener l) {

    }

    public void removeTreeModelListener(TreeModelListener l) {

    }

}
