package com.example.thatissuccecful;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateRemoteModel;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

public class MainActivity extends AppCompatActivity {

    //public Spinner fLang;

    public Spinner sp1;
    public Spinner sp2;

    public int fromLangCode = 11;
    public int toLangCode = 44;


    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         sp1 = (Spinner) findViewById(R.id.spinner);
         sp2 = (Spinner) findViewById(R.id.toSpinner);





        //fLang = findViewById(R.id.fromSpinner);
//
        ArrayAdapter<String> myAdapterFirst = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.fromLang));
        myAdapterFirst.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(myAdapterFirst);

        ArrayAdapter<String> myAdapterSecond = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.toLang));
        myAdapterSecond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(myAdapterSecond);


        final Button butt = findViewById(R.id.buttonID);

        dbHelper = new DBHelper(this);



        butt.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {
                    String firstVal = sp1.getSelectedItem().toString();
                    String secondVal = sp2.getSelectedItem().toString();

                    //String lang = "Chinese";
                    //String langCode = "58";

//                   SQLiteDatabase database = dbHelper.getWritableDatabase();
//
//                   ContentValues contentValues = new ContentValues();



                   //contentValues.put(DBHelper.KEY_LANG, lang);
                   //contentValues.put(DBHelper.KEY_CODE, langCode);

                   //contentValues.put(DBHelper.KEY_FROM_LANG, firstVal);
                   //contentValues.put(DBHelper.KEY_TO_LANG, secondVal);

                    //UPDATE QUERY
                   //database.update(DBHelper.TABLE_CONTACTS, contentValues,"_id = 1",null);

                   //DELETE QUERY
                   //database.delete(DBHelper.DATABASE_NAME, null, null);

                    //INSERT QUERY
                   //database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

                    //Select SQLite QUERY
//                   Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_CONTACTS ,null );
//
//                   if(cursor.moveToFirst()){
//                       int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
//                       int fromIndex = cursor.getColumnIndex(DBHelper.KEY_LANG);
//                       int toIndex = cursor.getColumnIndex(DBHelper.KEY_CODE);
//                       do{
//                            Log.d("ThisTag", String.valueOf(cursor.getInt(idIndex)+" "+cursor.getString(fromIndex)+" "+cursor.getString(toIndex)));


//                            Log.d("From", String.valueOf(fromLangCode));
//                            Log.d("To", String.valueOf(toLangCode));
                            //USING LANGUAGE
                           FirebaseTranslatorOptions options =
                                   new FirebaseTranslatorOptions.Builder()
                                           .setSourceLanguage(fromLangCode)
                                           .setTargetLanguage(toLangCode)
                                           .build();
                           final FirebaseTranslator englishChineseTranslator =
                                   FirebaseNaturalLanguage.getInstance().getTranslator(options);

                           //DOWNLOADING
                           FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                        .requireWifi()
                        .build();
                englishChineseTranslator.downloadModelIfNeeded(conditions)
                        .addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void v) {
                                        // Model downloaded successfully. Okay to start translating.
                                        // (Set a flag, unhide the translation UI, etc.)
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Model couldn’t be downloaded or other internal error.
                                        // ...
                                    }
                                });
//                       }while(cursor.moveToNext());
//                   }
//                   else {
//                       Log.d("ThisTag", "0 rows");
//                   }
               }
        });
//
        // Create an English-German translator:
//        FirebaseTranslatorOptions options =
//                new FirebaseTranslatorOptions.Builder()
//                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
//                        .setTargetLanguage(FirebaseTranslateLanguage.ZH)
//                        .build();
//        final FirebaseTranslator englishChineseTranslator =
//                FirebaseNaturalLanguage.getInstance().getTranslator(options);

//        //Language paceg downloading
//
//        //TextView justText = findViewById(R.id.tetxtID);
//
//         final EditText et = (EditText)findViewById(R.id.editTextID);
//
       //final Button butt = findViewById(R.id.buttonID);

//        butt.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //Log.d("spinnerValue",sp1.getSelectedItem().toString());
////                FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
////                        .requireWifi()
////                        .build();
////                englishChineseTranslator.downloadModelIfNeeded(conditions)
////                        .addOnSuccessListener(
////                                new OnSuccessListener<Void>() {
////                                    @Override
////                                    public void onSuccess(Void v) {
////                                        // Model downloaded successfully. Okay to start translating.
////                                        // (Set a flag, unhide the translation UI, etc.)
////                                    }
////                                })
////                        .addOnFailureListener(
////                                new OnFailureListener() {
////                                    @Override
////                                    public void onFailure(@NonNull Exception e) {
////                                        // Model couldn’t be downloaded or other internal error.
////                                        // ...
////                                    }
////                                });
//            }
//        });
    }
}
