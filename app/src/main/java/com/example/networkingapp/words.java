package com.example.networkingapp;

public class words {
    private final double mText1;
    private final String mText2;
    private  String mText3;
    private  long mTime;
    private String murl;

    words(double text1,String text2,String text3,String url) {
        mText1=text1;
        mText2=text2;
        mText3=text3;
        murl=url;
    }
    words(double text1,String text2,long time,String url) {
        mText1=text1;
        mText2=text2;
        mTime=time;
        murl=url;
    }
    public double getMtext1(){
        return mText1;
    }
    public String getMtext2(){
        return mText2;
    }
    public String getMtext3(){
        return mText3;
    }
    public long getMtime(){
        return mTime;
    }
    public String getMurl(){
        return murl;
    }
}
