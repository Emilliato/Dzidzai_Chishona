package net.simpledev.leo.chishona;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Categories extends AppCompatActivity  {
	
	ListView categoryList ;
	final ArrayList<Category> categories = new ArrayList<>();
	//private  String[] categories = {,"Family Members","Week Days", "Phrases"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		
		
		setCategory();
		categoryList = findViewById(R.id.category_list);
		CategoryAdapter arrayAdapter= new CategoryAdapter(getApplicationContext(),categories);
		categoryList.setAdapter(arrayAdapter);
		setClicks();
		
	}
	
	private void setCategory(){
		categories.add(new Category("Vowels and Combinations",R.drawable.category_vowels,R.color.category_vowels));
		categories.add(new Category("Numbers",R.drawable.category_numbers,R.color.category_numbers));
		categories.add(new Category("Family Members",R.drawable.number_one,R.color.category_family));
		categories.add(new Category("Phrases",R.drawable.category_phrases,R.color.category_phrases));
		categories.add(new Category("Proverbs",R.drawable.category_provebs,R.color.category_vowels));
		categories.add(new Category("Week Days",R.drawable.category_weekdays,R.color.category_numbers));
		categories.add(new Category("Months",R.drawable.number_one,R.color.category_family));
		categories.add(new Category("Animals",R.drawable.category_animals,R.color.category_phrases));
		categories.add(new Category("Trees",R.drawable.category_animals,R.color.category_vowels));
	}
	
	
	 private void setClicks(){
		/*
		* Handle all clicks on th categories
		*/
		 categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 @Override
			 public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				 int num = i;
				 switch (num){
					 case 0:
					 {   // Launch the numbers activity
						 Intent intent= new Intent(getApplicationContext(),Vowels.class);
						 startActivity(intent);
						 break;
					 }
					 case 1:
					 {   // Launch the numbers activity
						 Intent intent= new Intent(getApplicationContext(),Numbers.class);
						 startActivity(intent);
						 break;
					 }
					 case 2:
					 {   // Launch the family members activity
						 Intent intent= new Intent(getApplicationContext(),FamilyMembers.class);
						 startActivity(intent);
						 break;
						
					 }
					 case 3:
					 {   // Launch the weekdays activity
						 Intent intent= new Intent(getApplicationContext(),Phrases.class);
						 startActivity(intent);
						 break;
						
					 }
					 case 4:
					 {// Launch the phrases activity
						 Intent intent= new Intent(getApplicationContext(),Proverbs.class);
						 startActivity(intent);
						 break;
						
					 }
					 case 5:
					 {// Launch the phrases activity
						 Intent intent= new Intent(getApplicationContext(),WeekDays.class);
						 startActivity(intent);
						 break;
						
					 }
					 case 6:
					 {// Launch the phrases activity
						 Intent intent= new Intent(getApplicationContext(),Months.class);
						 startActivity(intent);
						 break;
						
					 }
					 case 7:
					 {// Launch the phrases activity
						 Intent intent= new Intent(getApplicationContext(),Animals.class);
						 startActivity(intent);
						 break;
						
					 }
					 case 8:
					 {// Launch the phrases activity
						 Intent intent= new Intent(getApplicationContext(),Trees.class);
						 startActivity(intent);
						 break;
						
					 }
					 default:{
						 showToast("Invalid selection");
					 }
				 }
			 }
		 });
	 }
	 private void showToast(String text){
		 Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	 }
	
}
