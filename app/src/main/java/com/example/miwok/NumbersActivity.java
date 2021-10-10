package com.example.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

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


    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        //Create and set up the{@link AudioManager} to request AudioFocus
        mAudioManager =(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one", "ek",R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "do",R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "teen",R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "chaar",R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "paanch",R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "cheigh",R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "saat",R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "aath",R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "naun",R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "dus",R.drawable.number_ten, R.raw.number_ten));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) findViewById(R.id.listn);

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
                mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());

                //start audio file
                mMediaPlayer.start();

                //Set up a listener on the media player, so that we can stop and release the
                //media player once the sound has finished
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        //when the activity is stopeed, release the media play resorces bcoz we won't be playing anymore sound
        releaseMediaPlayer();
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
