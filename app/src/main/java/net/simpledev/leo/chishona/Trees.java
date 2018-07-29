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

public class Trees extends AppCompatActivity {
	
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
		
		adapter = new WordAdapter(getApplicationContext(),words,R.color.category_vowels);
		
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
		words.add(new Word("Muuyu","Boabab",R.drawable.number_one,R.raw.tree_boabab));
		words.add(new Word("Musasa","Accacia",R.drawable.number_two,R.raw.tree_acacia));
		words.add(new Word("Mupani","Mopane",R.drawable.number_three,R.raw.tree_mopane));
		words.add(new Word("Mukuyu","Fig Tree",R.drawable.number_four,R.raw.tree_fig_tree));
		words.add(new Word("Mumvee","Sausage Tree",R.drawable.number_five,R.raw.tree_sausage_tree));
		words.add(new Word("Muchakata ","Mbola Palm Tree",R.drawable.number_six,R.raw.tree_palm));
		words.add(new Word("Mudimu","Lemon Tree",R.drawable.number_seven,R.raw.tree_lemon));
		words.add(new Word("Mutohwe","Azanza Gakiana",R.drawable.number_eight,R.raw.tree_azanza));
		
		
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
