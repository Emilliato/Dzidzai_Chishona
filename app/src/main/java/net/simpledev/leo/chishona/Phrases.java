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

public class Phrases extends AppCompatActivity {
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
		desc.setText(getString(R.string.desc_phrases));
		setWords();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		adapter = new WordAdapter(getApplicationContext(),words,R.color.category_phrases);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(onItemClickListener);
		
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
	
	private void setWords(){
		words.add(new Word("mamuka sei","good morning",R.raw.phrase_morning));
		words.add(new Word("maswera sei","good afternoon",R.raw.phrase_afternoon));
		words.add(new Word("manheru","good evening",R.raw.phrase_evening));
		words.add(new Word("uri kunzwa sei","how are you feeling",R.raw.phrase_feeling));
		words.add(new Word("zita rako unonzi ani","what is your name",R.raw.phrase_your_name));
		words.add(new Word("unobva kupi","where do you come from",R.raw.phrase_where_from));
		words.add(new Word("uri kuitei","what are you doing",R.raw.phrase_doing));
		words.add(new Word("uri kuenda kupi","where are you going",R.raw.phrase_going));
		words.add(new Word("huya pano","come here",R.raw.phrase_come_here));
		
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
