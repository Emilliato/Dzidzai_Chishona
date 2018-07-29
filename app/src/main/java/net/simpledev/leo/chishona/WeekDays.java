package net.simpledev.leo.chishona;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class WeekDays extends AppCompatActivity {
	TextView desc;
	ListView listView;
	WordAdapter adapter;
	MediaPlayer mediaPlayer;
	private AudioManager mAudioManager;
	
	AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
		@Override
		public void onAudioFocusChange(int focusChange) {
			if (focusChange== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
				//PAUSE PLAYBACK
				mediaPlayer.pause();
				mediaPlayer.seekTo(0);
			}else if (focusChange==AudioManager.AUDIOFOCUS_GAIN){
				//Resume playback
				mediaPlayer.start();
			}else if (focusChange==AudioManager.AUDIOFOCUS_LOSS){
				releaseMediaPlayer();
			}
		}
	};
	
	MediaPlayer.OnCompletionListener mCompletionListener  =   new MediaPlayer.OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mediaPlayer) {
			releaseMediaPlayer();
		}
	};
	
	final ArrayList<Word> words = new ArrayList<>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_list);
		
		listView = findViewById(R.id.wordsList);
		desc = findViewById(R.id.description);
		desc.setText(R.string.desc_weekdays);
		setWords();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		adapter = new WordAdapter(getApplicationContext(),words,R.color.category_numbers);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(onItemClickListener);
	}
	
	private void setWords(){
		words.add(new Word("muvhuro","monday",R.drawable.weekday_monday,R.raw.weekday_monday));
		words.add(new Word("chipiri","tuesday",R.drawable.weekday_tuesday,R.raw.weekday_tuesday));
		words.add(new Word("chitatu","wednesday",R.drawable.weekday_wednesday,R.raw.weekday_wednesday));
		words.add(new Word("china","thursday",R.drawable.weekday_thursday,R.raw.weekday_thursday));
		words.add(new Word("chishanu","friday",R.drawable.weekday_friday,R.raw.weekday_friday));
		words.add(new Word("mugovera","saturday",R.drawable.weekday_saturday,R.raw.weekday_saturday));
		words.add(new Word("svondo","sunday",R.drawable.weekday_sunday,R.raw.weekday_sunday));
	}
	@Override
	protected void onStop() {
		super.onStop();
		releaseMediaPlayer();
	}
	//Method to release media player resources after finishing up playing
	private void releaseMediaPlayer(){
		if (mediaPlayer!=null){
			mediaPlayer.release();
			mediaPlayer=null;
			mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
		}
	}
	private AdapterView.OnItemClickListener onItemClickListener=  new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
			Word word =words.get(i);
			//Request audio focus
			int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
					//Use the music stream
					AudioManager.STREAM_MUSIC,
					//Request permanent focus
					AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
			if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
				//release the media player   so that we can play a different  audio  file
				releaseMediaPlayer();
				mediaPlayer = MediaPlayer.create(getApplicationContext(), word.getmAudioResourceId());
				//Start the  audio  file
				mediaPlayer.start();
				//set up an on complete  listener when the audio has finished playing
				mediaPlayer.setOnCompletionListener(mCompletionListener);
			}
		}
	};
}
