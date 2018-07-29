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
 * Created by LEoe on 7/20/2018.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {
	private List<Category> categoryList;
	private Context context;
	
	public CategoryAdapter(@NonNull Context context, @NonNull List<Category> categoryList) {
		super(context, 0,categoryList);
		this.categoryList = categoryList;
		this.context = context;
	}
	
	
	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		Category category = getItem(position);
		View mView = convertView;
		if (mView == null){
			mView = LayoutInflater.from(getContext()).inflate(R.layout.category_list_item,parent,false);
		}
		TextView name =   mView.findViewById(R.id.tv_category);
		ImageView mImage =  mView.findViewById(R.id.c_image);
		
		name.setText(category.getCategoryId());
		mImage.setImageResource(category.getmImageRecourceId());
		
		
		//Pick te color that the resource id maps to
		int color = ContextCompat.getColor(getContext(),category.getmColorResourceId());
		//set the background color  of the text container view
		name.setBackgroundColor(color);
		return mView;
	}
}
