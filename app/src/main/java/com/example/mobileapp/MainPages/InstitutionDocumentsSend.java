package com.example.mobileapp.MainPages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobileapp.R;

public class InstitutionDocumentsSend extends Fragment {

    ListView pdfListView;

    Button preview_doc;
    String[] description = {"description1", "description 2", "description 3"};
    String[] title = {"Title1", "Title2", "Title3"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_documents_send, container, false);

        pdfListView = (ListView) view.findViewById(R.id.PDFLstDocumentsSend);



        class MyAdapter extends ArrayAdapter<String> {
            Context context;
            String[] rTitle;
            String[] rDescription;

            MyAdapter(Context c, String title[], String[] description) {
                super(c, R.layout.fragment_documents_send_custom_list, R.id.textView1, title);
                this.context = c;
                this.rTitle = title;
                this.rDescription = description;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = super.getView(position, convertView, parent);
                TextView myDescription = view.findViewById(R.id.textView2);
                TextView myTitle = (TextView) view.findViewById(R.id.textView1);

                myTitle.setText(rTitle[position]);
                myDescription.setText(rDescription[position]);

                return view;
            }
        }
        MyAdapter adapter = new MyAdapter(getContext(), title, description);
        pdfListView.setAdapter(adapter);
        pdfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) pdfListView.getItemAtPosition(position);
                Intent start = new Intent(getActivity(), ViewActivity.class);
                start.putExtra("pdfFileName", title[position]);
                Log.wtf("title:", title[position]);
                Log.wtf("position:", " "+position);
                startActivity(start);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        return view;
    }
}
