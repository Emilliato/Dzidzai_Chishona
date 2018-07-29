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

public class Vowels extends AppCompatActivity {
	
	ListView listView;
	TextView desc ;
	WordAdapter1 adapter;
	MediaPlayer mMediaPlayer;
	private AudioManager mAudioManager;
	AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
		@Override
		public void onAudioFocusChange(int focusChange) {
			if (focusChange== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
				//PAUSE PLAYBACK
				mMediaPlayer.pause();
				mMediaPlayer.seekTo(0);
			}else if (focusChange==AudioManager.AUDIOFOCUS_GAIN){
				//Resume playback
				mMediaPlayer.start();
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
		desc.setText("The building blocks of our language");
		setWords();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		adapter = new WordAdapter1(getApplicationContext(),words,R.color.category_proverbs);
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
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
					mMediaPlayer = MediaPlayer.create(getApplicationContext(), word.getmAudioResourceId());
					//Start the  audio  file
					mMediaPlayer.start();
					//set up an on complete  listener when the audio has finished playing
					mMediaPlayer.setOnCompletionListener(mCompletionListener);
				}
			}
		});
		
	}
	private void setWords(){
		words.add(new Word("  Vowels ","====================",R.raw.phrase_where_from));
		words.add(new Word("","",R.raw.phrase_where_from));
		words.add(new Word("a ",getString(R.string.place_holder),R.raw.phrase_morning));
		words.add(new Word("e",getString(R.string.place_holder),R.raw.phrase_afternoon));
		words.add(new Word("i ",getString(R.string.place_holder),R.raw.phrase_evening));
		words.add(new Word("o",getString(R.string.place_holder),R.raw.phrase_feeling));
		words.add(new Word("u",getString(R.string.place_holder),R.raw.phrase_your_name));
		words.add(new Word("Combinations ","====================",R.raw.phrase_where_from));
		words.add(new Word("","",R.raw.phrase_where_from));
		words.add(new Word(getString(R.string.vowel_ba),getString(R.string.place_holder),R.raw.phrase_doing));
		words.add(new Word(getString(R.string.vowel_bh),getString(R.string.place_holder),R.raw.phrase_doing));
		words.add(new Word(getString(R.string.vowel_bv),getString(R.string.place_holder),R.raw.phrase_doing));
		words.add(new Word(getString(R.string.vowel_bw),getString(R.string.place_holder),R.raw.phrase_doing));
		words.add(new Word(getString(R.string.vowel_ch),getString(R.string.place_holder),R.raw.phrase_doing));
		words.add(new Word(getString(R.string.vowel_da),getString(R.string.place_holder),R.raw.phrase_doing));
		words.add(new Word(getString(R.string.vowel_dh),getString(R.string.place_holder),R.raw.phrase_doing));
		words.add(new Word(getString(R.string.vowel_dy),getString(R.string.place_holder),R.raw.phrase_doing));
		words.add(new Word(getString(R.string.vowel_dz),getString(R.string.place_holder),R.raw.phrase_doing));
		words.add(new Word(getString(R.string.fa),getString(R.string.place_holder),R.raw.phrase_doing));
		words.add(new Word(getString(R.string.vowel_ga),getString(R.string.place_holder),R.raw.phrase_doing));
		words.add(new Word(getString(R.string.vowel_gw),getString(R.string.place_holder),R.raw.phrase_doing));
		
	}
	@Override
	protected void onStop() {
		super.onStop();
		releaseMediaPlayer();
	}
	//Method to release media player resources after finishing up playing
	private void releaseMediaPlayer(){
		if (mMediaPlayer !=null){
			mMediaPlayer.release();
			mMediaPlayer =null;
			//abandon audio manager
			mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
		}
	}
	
	
	
}
