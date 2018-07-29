package net.simpledev.leo.chishona;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LEoe on 7/23/2018.
 */

public class WordAdapter1 extends ArrayAdapter<Word> {
	
	private List<Word> wordList;
	private Context context;
	private int mColorResourceId;
	
	public WordAdapter1(Context context, List<Word> wordList, int mColorResourceId) {
		super(context,0,wordList);
		this.wordList = wordList;
		this.context = context;
		this.mColorResourceId = mColorResourceId;
	}
	
	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		// Get cureent word first
		Word curentWord = getItem(position);
		
		View listItemView = convertView;
		if (listItemView==null){
			listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item2,parent,false);
		}
		//Find the text view in the list item with id tv_shona
		TextView shonaTranslation = listItemView.findViewById(R.id.tv_shona1);
		
		//Set the value to be shona wo at current position
		shonaTranslation.setText(curentWord.getShonaTranslation());
		
		//Find the text iew in the list item with id tv_shona
		TextView defaultTranslation = listItemView.findViewById(R.id.tv_default1);
		
		//Set the value to be english word at current position
		defaultTranslation.setText(curentWord.getDefaultTranslation());
		
		//Sst the color for the  list item
		View textContainer = listItemView.findViewById(R.id.text_container1);
		//Pick te color that the resource id maps to
		int color = ContextCompat.getColor(getContext(),mColorResourceId);
		//set the background color  of the text container view
		textContainer.setBackgroundColor(color);
		
		
		return listItemView;
	}
}
