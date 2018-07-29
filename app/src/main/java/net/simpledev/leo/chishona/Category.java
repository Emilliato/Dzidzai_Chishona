package net.simpledev.leo.chishona;

/**
 * Created by LEoe on 7/20/2018.
 */

public class Category {
	private String categoryId;
	private int mImageRecourceId;
	private int mColorResourceId;
	
	public Category(String categoryId, int mImageRecourceId, int mColorResourceId) {
		this.categoryId = categoryId;
		this.mImageRecourceId = mImageRecourceId;
		this.mColorResourceId = mColorResourceId;
	}
	
	public String getCategoryId() {
		return categoryId;
	}
	
	public int getmImageRecourceId() {
		return mImageRecourceId;
	}
	
	public int getmColorResourceId() {
		return mColorResourceId;
	}
}
