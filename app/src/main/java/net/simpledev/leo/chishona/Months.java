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

public class Months extends AppCompatActivity {
	
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
		desc.setText("Months are part of what defines shona culture");
		setWords();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		adapter = new WordAdapter(getApplicationContext(),words,R.color.category_family);
		
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
		words.add(new Word("Ndira","January",R.drawable.number_one,R.raw.month_january));
		words.add(new Word("Kukadzi","February",R.drawable.number_two,R.raw.month_february));
		words.add(new Word("Kurume","March",R.drawable.number_three,R.raw.month_march));
		words.add(new Word("Kubvumbi","April",R.drawable.number_four,R.raw.month_april));
		words.add(new Word("Chivabvu","May",R.drawable.number_five,R.raw.month_may));
		words.add(new Word("Chikumi","June",R.drawable.number_six,R.raw.month_june));
		words.add(new Word("Chikunnguru","July",R.drawable.number_seven,R.raw.month_july));
		words.add(new Word("Nyamavhuvhu","Augast",R.drawable.number_eight,R.raw.month_augast));
		words.add(new Word("Gunyana","September",R.drawable.number_nine,R.raw.month_september));
		words.add(new Word("Gumi","October",R.drawable.number_ten,R.raw.month_october));
		words.add(new Word("Mbudzi","November",R.drawable.number_ten,R.raw.month_november));
		words.add(new Word("Zvita","December",R.drawable.number_ten,R.raw.month_december));
		
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
