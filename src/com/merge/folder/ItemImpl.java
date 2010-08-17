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
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tushar Joshi <tusharvjoshi@gmail.com>
 */
public class ItemImpl implements Item {

    private int leftState;
    private int rightState;
    private String name;
    private String leftPath;
    private String rightPath;
    private boolean leaf;
    private String absPath;
    private PropertyChangeSupport propertyChangeSupport =
            new PropertyChangeSupport(this);
    private List<Item> itemList = new ArrayList<Item>();

    public String getAbsolutePath(){
    	return absPath;
    }
    public void setAbsolutePath(String path){
    	absPath = path;
    }
    public int getLeftState() {
        return this.leftState;
    }

    public void setLeftState(int state) {
        this.leftState = state;
    }

    public int getRightState() {
        return this.rightState;
    }

    public void setRightState(int state) {
        this.rightState = state;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeftPath() {
        return this.leftPath;
    }

    public void setLeftPath(String path) {
        this.leftPath = path;
    }

    public String getRightPath() {
        return this.rightPath;
    }

    public void setRightPath(String path) {
        this.rightPath = path;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean value) {
        this.leaf = value;
    }

    public int getChildCount() {
        return itemList.size();
    }

    public void addChild(Item childItem) {
        itemList.add( childItem);
    }

    public Item getChild(int index) {
        return itemList.get(index);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public String getStateDescription(int state) {
        String description = "";

        switch( state ) {
            case Item.STATE_UNCHECKED:
                description = "UNCHECKED";
                break;
            case Item.STATE_NEW:
                description = "NEW";
                break;
            case Item.STATE_OLD:
                description = "OLD";
                break;
            case Item.STATE_SAME:
                description = "SAME";
                break;
            case Item.STATE_ORPHAN:
                description = "ORPHAN";
                break;
            case Item.STATE_NOTAVAILABLE:
                description = "NOT AVAILABLE";
                break;
        }

        return description;
    }

    @Override
    public String toString() {
        return name;
    }

    public String toString2() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Left Path:" + leftPath);
        buffer.append("\n");
        buffer.append("Right Path:" + rightPath);
        buffer.append("\n");
        buffer.append("Name: " + name);
        buffer.append("\n");
        buffer.append("Left State: " +getStateDescription(leftState));
        buffer.append("\n");
        buffer.append("Right State: " +getStateDescription(rightState));
        buffer.append("\n");
        buffer.append("IsLeaf: "+ leaf);
        buffer.append("\n");

        if( !leaf ) {
            int size = getChildCount();
            buffer.append("\n");
            for( int index = 0 ; index < size; index++ ) {
                Item child = getChild(index);
                buffer.append( ((ItemImpl)child).toString2());
            }
        }
        buffer.append("\n");


        return buffer.toString();
    }

    public void clearChildren() {
        itemList.clear();
    }
}
