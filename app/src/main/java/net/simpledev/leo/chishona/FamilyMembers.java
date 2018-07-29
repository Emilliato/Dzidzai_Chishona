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

public class FamilyMembers extends AppCompatActivity {
	TextView desc ;
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
	
	private final ArrayList<Word> words = new ArrayList<>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_list);
		
		listView = findViewById(R.id.wordsList);
		
		desc = findViewById(R.id.description);
		desc.setText(R.string.desc_family);
		// make use function to initialize our words constructor
		setWords();
		
		adapter = new WordAdapter(getApplicationContext(),words,R.color.category_family);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(onItemClickListener);
	}
	
	//function to initialize our words constructor
	public void setWords(){
		words.add(new Word("amai","mother",R.drawable.family_mother,R.raw.family_mother));
		words.add(new Word("baba","father",R.drawable.family_father,R.raw.family_father));
		words.add(new Word("sekuru","grandfather",R.drawable.family_grandfather,R.raw.family_grandfather));
		words.add(new Word("ambuya","grandmother",R.drawable.family_grandmother,R.raw.family_grandmother));
		words.add(new Word("mukoma","older brother",R.drawable.family_older_brother,R.raw.family_older_brother));
		words.add(new Word("munin'ina","younger brother",R.drawable.family_younger_brother,R.raw.family_younger_brother));
		words.add(new Word("sisi","sister",R.drawable.family_daughter,R.raw.family_sister));
		words.add(new Word("bhudhi","brother",R.drawable.family_son,R.raw.family_brother));
		words.add(new Word("vakoma","older sister",R.drawable.family_older_sister,R.raw.family_older_sister));
		words.add(new Word("vatete","aunt",R.drawable.family_mother,R.raw.family_aunt));
		words.add(new Word("mwanakomana","son",R.drawable.family_son,R.raw.family_son));
		words.add(new Word("mwanasikana","daughter",R.drawable.family_daughter,R.raw.family_daugter));
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
	
}
