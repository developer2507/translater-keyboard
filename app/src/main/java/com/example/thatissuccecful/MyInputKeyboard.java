package com.example.thatissuccecful;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

public class MyInputKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView kv;
    private Keyboard keyboard;



    FirebaseTranslatorOptions options =
            new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.RU)
                    .build();
    final FirebaseTranslator englishGermanTranslator =
            FirebaseNaturalLanguage.getInstance().getTranslator(options);

    private final static int NAME_CODE = -32;

    public String df;

    //MainActivity mn;

    DBHelper dbHelper = new DBHelper(this);

    private boolean isCaps = false;

    @Override
    public View onCreateInputView(){
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard_view,null);
        keyboard = new Keyboard(this,R.xml.number_pad);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // do your stuff here
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public void onKey(int i, int[] ints) {
        final InputConnection ic = getCurrentInputConnection();
        playClick(i);

        switch (i)
        {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                isCaps = !isCaps;
                keyboard.setShifted(isCaps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                //HERE
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;

            // That is key for Translating && Include Get and Set inputed TEXT
            case NAME_CODE:
                //check = true;
                final String abc = String.valueOf(ic.getTextBeforeCursor(5000,0));
                df = abc + " ";



                englishGermanTranslator.translate(df)
                    .addOnSuccessListener(
                            new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(@NonNull final String translatedText) {
                                    // Do something in response to button click
                                    ic.deleteSurroundingText(5000,0);
                                    ic.commitText(translatedText,0);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error.
                                    // ...
                                }
                            });

//                String abc = String.valueOf(ic.getTextBeforeCursor(5000,0));
//                df = abc + " Change text";
//                ic.deleteSurroundingText(5000,0);
//                ic.commitText(df,0);
                //Log.d("anyTag", df);
                break;

            default:
                char code =(char) i;
                if (Character.isLetter(code) && isCaps)
                    code = Character.toUpperCase(code);

                ic.commitText(String.valueOf(code), 0);

        }
    }

    private void playClick(int i) {
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch (i)
        {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
