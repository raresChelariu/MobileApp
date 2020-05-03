package com.example.mobileapp.MainPages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.R;
import com.github.barteksc.pdfviewer.PDFView;

public class ViewActivity extends AppCompatActivity {

    PDFView myPDFViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pdf_list);

        myPDFViewer=(PDFView)findViewById(R.id.pdfViewer);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String getItem = extras.getString("pdfFileName");

        Log.wtf("getItem: ", getItem);
        myPDFViewer.fromAsset(getItem).load();

    }
}
