package com.example.alexmao.projetfinal.BDDExterne;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is intended for stocking pictures and sending them to Firebase
 */
@JsonIgnoreProperties({"mChunks", "mBitmap"})
public class Picture {
    private static final String TAG = "Picture";
    //the picture written in bytes
    private List<byte[]> mChunks;
    //The picture
    private Bitmap mBitmap;
    //the picture written in string
    private List<String> mStringChunks;

    public Picture(Bitmap bitmap) {
       this.mBitmap = bitmap;
        mChunks = new ArrayList<>();
        mStringChunks = new ArrayList<>();
        fillChunks(bitmap);
        fillStringChunks();
    }

    //empty, for firebase
    public Picture() {
    }

    public void update(Picture picture) {
        if (picture != null) {
            mChunks = picture.mChunks;
            mBitmap = picture.mBitmap;
            mStringChunks = picture.mStringChunks;
        }
    }

    //for firebase, do not change
    public List<String> getStringChunks() {
        return mStringChunks;
    }

    //for firebase, do not change
    public void setStringChunks(List<String> mStringChunks) {
        this.mStringChunks = mStringChunks;
    }


    public Bitmap getmBitmap() {
        if (mBitmap == null) {
            //if the bitmap is empty we create them from the string we found n firebase
            String rawPicture = uniteStringChunks();
            byte[] decodedBytes = Base64.decode(rawPicture, 0);
            mBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);;
        }

        return mBitmap;
    }

    //translate Byte to byte
    private byte[] fromBytesTobytes(Byte[] mBytes) {
        byte[] chunk = new byte[mBytes.length];
        for (int j = 0; j < mBytes.length; j++) {
            chunk[j] = mBytes[j];
        }

        return chunk;
    }

    //fill the byte array
    private void fillChunks(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        List<Byte> listTmp = new ArrayList<>();
        int size = 1000000;
        for (int i = 0; i < size && i < byteArray.length; i += size) {
            for (int j = 0; j < size && i + j < byteArray.length; j++) {
                listTmp.add(byteArray[i + j]);
            }
            Byte[] tmp = new Byte[listTmp.size()];
            listTmp.toArray(tmp);
            byte[] chunk = fromBytesTobytes(tmp);
            mChunks.add(chunk);
            listTmp.clear();
        }
    }

    //fill the String array
    private void fillStringChunks() {
        for (byte[] bytes: mChunks) {
            mStringChunks.add(Base64.encodeToString(bytes, Base64.DEFAULT));
        }
    }


    private String uniteStringChunks() {
        String bitmap = "";
        for (String chunk : mStringChunks) {
            bitmap += chunk;
        }
        return bitmap;
    }
}
