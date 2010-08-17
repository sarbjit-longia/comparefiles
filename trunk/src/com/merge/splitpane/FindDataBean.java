package com.merge.splitpane;

public class FindDataBean {
	boolean findInBoth, findInLeft, findInRight, matchWholeWord, caseInSensitive, searchUp;
	boolean isRegex;
	String searchString = null;
	public FindDataBean(FindDialog diag) {
		findInBoth = diag.isFindinBoth();
		findInLeft = diag.isFindInLeft();
		findInRight = diag.isFindInRight();
		isRegex = diag.isRegex();
		searchString = diag.getSearchString();
		matchWholeWord = diag.isMatchWholeWord();
		caseInSensitive = diag.isCaseInsensitive();
		searchUp = diag.isSearchUp();
	}
	public boolean isFindInBoth() {
		return findInBoth;
	}
	public boolean isFindInLeft() {
		return findInLeft;
	}
	public boolean isFindInRight() {
		return findInRight;
	}
	public boolean isMatchWholeWord() {
		return matchWholeWord;
	}
	public boolean isCaseInSensitive() {
		return caseInSensitive;
	}
	public boolean isSearchUp() {
		return searchUp;
	}
	public boolean isRegex() {
		return isRegex;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setFindInBoth(boolean findInBoth) {
		this.findInBoth = findInBoth;
	}
	public void setFindInLeft(boolean findInLeft) {
		this.findInLeft = findInLeft;
	}
	public void setFindInRight(boolean findInRight) {
		this.findInRight = findInRight;
	}
	public void setMatchWholeWord(boolean matchWholeWord) {
		this.matchWholeWord = matchWholeWord;
	}
	public void setCaseInSensitive(boolean caseInSensitive) {
		this.caseInSensitive = caseInSensitive;
	}
	public void setSearchUp(boolean searchUp) {
		this.searchUp = searchUp;
	}
	public void setRegex(boolean isRegex) {
		this.isRegex = isRegex;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
