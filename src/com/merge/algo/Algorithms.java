package com.merge.algo;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.MutableTreeNode;

import org.incava.util.diff.Diff;
import org.incava.util.diff.Difference;


import com.merge.splitpane.DataRow;
import com.merge.splitpane.EmptyDataRow;

/**
 * This class acts as the comparator for the data in the tables in the panes
 * @author singhs
 *
 */
class RowComparator implements Comparator<DataRow>{

	/**
	 * Overridden method that performs the comparison.
	 */
	@Override
	public int compare(DataRow o1, DataRow o2) {
		/* In case we are comparing the empty row with the populated row
		 * return that they are equal as we don't want to insert rows in other
		 * table due to empty rows
		 */
		if(o1 instanceof EmptyDataRow || o2 instanceof EmptyDataRow) return 0;
		/* If we have valid data to compare perform comparison else return */
		if((o1 != null) && (o2 != null) && (o1.getData() != null) && (o2.getData()!= null))
			return o1.getData().toString().compareTo(o2.getData().toString());
		return -1;
	}
}

/**
 * This class will be responsible to contain all the algorithms in the system
 * @author singhs
 *
 */

public class Algorithms {

	

	/**
	 * This method has vital importance. It calculates the data to be shown
	 * in UI
	 * @param left
	 * @param right
	 */
	public static void matchArrayData(ArrayList<DataRow> left, ArrayList<DataRow> right){
		DataRow empty = new EmptyDataRow();
		Comparator<DataRow> compare = new RowComparator();
		/* Convert data to the form required by the library */
		DataRow l[] = new DataRow[left.size()];
		left.toArray(l);
		DataRow r[] = new DataRow[right.size()];
		right.toArray(r);
		Diff<DataRow> d = new Diff<DataRow>(l, r, compare);
		/* Perform comparison */
		List<Difference> output = d.diff();
		//TODO: Should be removed
		System.out.println(output);
		int rightAdded = 0;
		int leftAdded = 0;
		for(Difference dif : output){
			/* Case when we need to add rows */
			if(dif.getAddedEnd() == -1){
				for(int i = dif.getDeletedStart() ; i <= dif.getDeletedEnd(); i++){
					/* Its possible that we might be adding at index more than the capacity, 
					 * so ensure capacity */
					right.ensureCapacity(i + leftAdded + 1);
					right.add(i + leftAdded, empty);
					rightAdded++;
				}
			}

			if(dif.getDeletedEnd() == -1){
				for(int i = dif.getAddedStart() ; i <= dif.getAddedEnd(); i++){
					/* Its possible that we might be adding at index more than the capacity, 
					 * so ensure capacity */
					left.ensureCapacity(i + rightAdded + 1);
					left.add(i + rightAdded, empty);
					leftAdded++;
				}
			}
		}
		/* Make both of equal size */
		int max = Math.max(left.size(), right.size());
		for(int i = left.size(); i < max; i++){
			left.add(empty);
		}
		for(int i = right.size(); i < max; i++){
			right.add(empty);
		}
		/* Truncate the empty rows in end */
		for(int  i = right.size() - 1; i > 0 ;i--){
			if((left.get(i) instanceof EmptyDataRow) && (right.get(i)) instanceof EmptyDataRow){
				left.remove(i);
				right.remove(i);
			}
		}
	}

	/**
	 * Method to get the common characters from the data supplied. 
	 * It is used to color the characters that are different in red color.
	 * @param a
	 * @param b
	 * @return
	 */
	public static List<Difference> getCommonCharacters(String a, String b){
		/* In case any is null return , no need to do any processing */
		if( a == null || b == null) return null;
		Character aArr[] = new Character[a.length()];
		Character bArr[] = new Character[b.length()];
		ArrayList<Character> aList = new ArrayList<Character>();
		ArrayList<Character> bList = new ArrayList<Character>();
		/* Create data to the form required by the lower level API */
		for(Character c : a.toCharArray()){
			aList.add(c);
		}
		for(Character c : b.toCharArray()){
			bList.add(c);
		}
		aList.toArray(aArr);
		bList.toArray(bArr);
		Diff<Character> d = new Diff<Character>(aArr, bArr);
		/* Return the difference */
		return d.diff();
	}
}
