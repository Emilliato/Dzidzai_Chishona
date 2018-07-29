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

public class Animals extends AppCompatActivity {
	
	ListView listView;
	TextView desc ;
	WordAdapter adapter;
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
		desc.setText(R.string.desc_animals);
		setWords();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		adapter = new WordAdapter(getApplicationContext(),words,R.color.category_phrases);
		
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
		words.add(new Word("mombe","cow",R.drawable.animal_cow,R.raw.animal_cow));
		words.add(new Word("mbudzi","goat",R.drawable.animal_goat,R.raw.animal_goat));
		words.add(new Word("hwai","sheep",R.drawable.animal_sheep,R.raw.animal_sheep));
		words.add(new Word("imbwa","dog",R.drawable.animal_dog,R.raw.animal_dog));
		words.add(new Word("mbongoro","donkey",R.drawable.animal_donkey,R.raw.animal_donkey));
		words.add(new Word("shumba","lion",R.drawable.animal_lion,R.raw.animal_lion));
		words.add(new Word("mbizi","zebra",R.drawable.animal_zebra,R.raw.animal_zebra));
		words.add(new Word("gudo","baboon",R.drawable.animal_baboon,R.raw.animal_baboon));
		words.add(new Word("tsoko","monkey",R.drawable.animal_monkey,R.raw.animal_monkey));
		words.add(new Word("nyati","buffalo",R.drawable.animal_buffalo,R.raw.animal_buffalo));
	
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		releaseMediaPlayer();
	}
	//Method to release media player resources after finishing up playing
	private void releaseMediaPlayer(){
		if (mMediaPlayer !=null){
			//release resources
			mMediaPlayer.release();
			mMediaPlayer =null;
			//abandon audio manager
			mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
		}
	}
	
	
	
	
}
