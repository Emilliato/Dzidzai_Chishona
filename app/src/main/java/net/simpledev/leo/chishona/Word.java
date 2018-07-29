package net.simpledev.leo.chishona;

/*
 * Created by Leo on 7/18/2018.
 */

public class Word {
	private String shonaTranslation;
	private String  defaultTranslation;
	public static final int NO_IMAGE_PROVIDED =-1;
	private  int mResourceId = NO_IMAGE_PROVIDED;
	private int mAudioResourceId;
	
	
	
	public Word(String shonaTranslation, String defaultTranslation,int mAudioResourceId) {
		this.shonaTranslation = shonaTranslation;
		this.defaultTranslation = defaultTranslation;
		this.mAudioResourceId = mAudioResourceId;
	}
	
	public Word(String shonaTranslation, String defaultTranslation, int mResourceId,int mAudioResourceId) {
		this.shonaTranslation = shonaTranslation;
		this.defaultTranslation = defaultTranslation;
		this.mResourceId = mResourceId;
		this.mAudioResourceId = mAudioResourceId;
	}
	
	public String getShonaTranslation() {
		return shonaTranslation;
	}
	
	public String getDefaultTranslation() {
		return defaultTranslation;
	}
	public int getmResourceId() {return mResourceId;}
	
	public int getmAudioResourceId() {
		return mAudioResourceId;
	}
	
	public boolean hasImage(){
		return mResourceId!=NO_IMAGE_PROVIDED;
	}
	
}
