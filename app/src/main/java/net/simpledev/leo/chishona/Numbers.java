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

public class Numbers extends AppCompatActivity {
	
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
		desc.setText(R.string.s_count);
		setWords();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		adapter = new WordAdapter(getApplicationContext(),words,R.color.category_numbers);
		
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
		words.add(new Word("motsi","one",R.drawable.number_one,R.raw.number_one));
		words.add(new Word("piri","two",R.drawable.number_two,R.raw.number_two));
		words.add(new Word("tatu","three",R.drawable.number_three,R.raw.number_three));
		words.add(new Word("ina","four",R.drawable.number_four,R.raw.number_four));
		words.add(new Word("shanu","five",R.drawable.number_five,R.raw.number_five));
		words.add(new Word("nhanhatu","six",R.drawable.number_six,R.raw.number_six));
		words.add(new Word("nomwe","seven",R.drawable.number_seven,R.raw.number_seven));
		words.add(new Word("sere","eight",R.drawable.number_eight,R.raw.number_eight));
		words.add(new Word("pfumbamwe","nine",R.drawable.number_nine,R.raw.number_nine));
		words.add(new Word("gumi","ten",R.drawable.number_ten,R.raw.number_ten));
		
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
