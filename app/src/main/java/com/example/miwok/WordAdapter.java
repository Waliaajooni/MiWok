package com.example.miwok;

import android.content.Context;
//import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter {

    private int mColorResourceId;

    public WordAdapter(Context context, ArrayList<Word> word, int colorResourceId) {
        super(context, 0, word);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Word currentWord = (Word) getItem(position);
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());

        //find the imageView in the list_item.xml file
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        if(currentWord.hasImage()){
            //Set the imageView to the image resource specified in the current Word
            imageView.setImageResource(currentWord.getImageResourceId());

            //make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            //to remove the space otherwise we could just use INVISIBLE
            imageView.setVisibility(View.GONE);
        }


        return listItemView;
    }
}
