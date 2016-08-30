package com.kareem.moviesapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by kareem on 8/17/2016.
 */

public class Converter {
    //for converting img from/to byte array
    public byte[] convertBitMap(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,outputStream);
        return outputStream.toByteArray();
    }
    public Bitmap convertArray(byte[] array){
        return BitmapFactory.decodeByteArray(array,0,array.length);
    }
}
