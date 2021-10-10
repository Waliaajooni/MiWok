package com.example.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    //handles audio focus while playing a sound file
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //The AUDIO_FOCUS_LOSS_TRANSIENT case means that we have lost audioFocus
                //short amount of time . The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK means our app is allowed to comtinue playing but at lower volume
                //both cases the same way becoz aour app is playing short sound file

                //pause playback and reset player to the start of the file
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                //The AUDIOFOCUS_GAIN case means we have regained focus and we can resume playback
                mMediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //The AUDIO"_FOCUS_LOSS case means we've lost the audio focus and
                //So, stop playback and cleanup resources
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onStop(){
        super.onStop();
        //when the activity is stopeed, release the media play resorces bcoz we won't be playing anymore sound
        releaseMediaPlayer();
    }

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        //Create and set up the{@link AudioManager} to request AudioFocus
        mAudioManager =(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("white", "safed", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("black", "kaala", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("dusty yellow", "dhool bhra peela", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "sarason ka peela", R.drawable.color_mustard_yellow, R.raw.color_yellow));
        words.add(new Word("red", "laal", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("green", "hara", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "bhoora", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "dhoosar", R.drawable.color_gray, R.raw.color_gray));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) findViewById(R.id.listc);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                //release the media player if it currently exists because we are about tp play different audio file
                releaseMediaPlayer();

                //create and set up the {@link MediaPlayer} for the audio resource associated
                //with the current word
                mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());

                //start audio file
                mMediaPlayer.start();

                //Set up a listener on the media player, so that we can stop and release the
                //media player once the sound has finished
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });
    }

    //clean up the media player by releasing its resources
    private void releaseMediaPlayer(){
        //if the media player is not null, then it may be currently playing
        if(mMediaPlayer!=null){
            //Regardless of its current state of the media player , release its resources
            //because it no longer exists
            mMediaPlayer.release();

            //set the media player back to null
            //setting media player to null is an easy way to tell that media player is not configured to play any audio
            mMediaPlayer=null;

            //regardless of whether or not we were granted audio focus, abandon it.
            //This also unregisters the AudioFocusChangeListener so we don't get anymore callbacks
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}

