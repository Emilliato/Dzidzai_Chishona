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

public class Proverbs extends AppCompatActivity {
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
		desc.setText(R.string.dec_proverbs);
		setWords();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		adapter = new WordAdapter1(getApplicationContext(),words,R.color.category_vowels);
		
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
		words.add(new Word("Aive madziva ava mazambuko ","What were pools  have become fords.",R.raw.proverb_one));
		words.add(new Word("Aguta haaoneki ","One who has had Ms fill  does not bid farewell",R.raw.proverb_two));
		words.add(new Word("Afirwa  haaringwi kumeso ","One who is bereaved / is not looked in the face",R.raw.proverb_three));
		words.add(new Word("Akuruma nzeve ndewako. ","Those that advise you are on your side.",R.raw.proverb_four));
		words.add(new Word("Akweva sanzu akweva namashizha aro ","One who has pulled a branch along has pulled along its leaves",R.raw.proverb_five));
		words.add(new Word("Apunyaira haashayi misodzi ","One who has become emotionally upset does not lack tears",R.raw.proverb_six));
		words.add(new Word("Ashamba haanokorerwi ","One who has washed does not have stiff porridge broken off for him, is not helped to food",R.raw.proverb_seven));
		words.add(new Word("Ateya mariva murutsva haatyi kusviba magaro ","One who has set traps in burnt grass no longer fears his apron getting dirty",R.raw.proverb_eight));
		words.add(new Word("Atswinya arwa ","One who has pinched / has fought",R.raw.proverb_nine));
		words.add(new Word("Avengwa anhuhwa  ","one who is hated stinks",R.raw.proverb_ten));
		
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
