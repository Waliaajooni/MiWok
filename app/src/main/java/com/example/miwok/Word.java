package com.example.miwok;

public class Word {

    //default translation for the word
    private String mdefaultTranslation;

    //miwok translation
    private String mMiwokTranslation;

    //image
    private static final int NO_IMAGE_PROVIDED=-1;
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    //audio
    private int mAudioResourceId;

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId){
        mdefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId){
        mdefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    //get resource id
    public int getImageResourceId() {
        return mImageResourceId;
    }

    //Get the default translation of the word
    public String getDefaultTranslation() {
        return mdefaultTranslation;
    }

    //get miwok translation of the word
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceId(){
        return mAudioResourceId;
    }
}
