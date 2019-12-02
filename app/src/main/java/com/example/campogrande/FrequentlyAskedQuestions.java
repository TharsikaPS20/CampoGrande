package com.example.campogrande;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campogrande.Adapter.AdapterExp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FrequentlyAskedQuestions extends AppCompatActivity {

    private ExpandableListView list;
    private ExpandableListAdapter adapterList;
    private List<String> listHeadline;
    private HashMap<String,List<String>> hashMapList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequently_asked_questions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = findViewById(R.id.lvExp);
        initData();
        adapterList = new AdapterExp(this,listHeadline,hashMapList);
        list.setAdapter(adapterList);
    }

    private void initData() {
        listHeadline = new ArrayList<>();
        hashMapList = new HashMap<>();

        listHeadline.add(getResources().getString(R.string.visitor_faq));
        listHeadline.add(getResources().getString(R.string.host_faq));
        listHeadline.add(getResources().getString(R.string.collaboration));


        List<String> visitor = new ArrayList<>();
        visitor.add(getResources().getString(R.string.touch_faq));
        visitor.add(getResources().getString(R.string.pay_faq));
        visitor.add(getResources().getString(R.string.payment_faq));
        visitor.add(getResources().getString(R.string.check_in_faq));
        visitor.add(getResources().getString(R.string.password_reset_faq));
        visitor.add(getResources().getString(R.string.cancelreservation_faq));

        List<String> host = new ArrayList<>();
        host.add(getResources().getString(R.string.becomehost_faq));
        host.add(getResources().getString(R.string.property_faq));
        host.add(getResources().getString(R.string.touchvisitor_faq));

        List<String> collaboration = new ArrayList<>();
        collaboration.add(getResources().getString(R.string.meeting_faq));
        collaboration.add(getResources().getString(R.string.touchcg_faq));


        hashMapList.put(listHeadline.get(0),visitor);
        hashMapList.put(listHeadline.get(1),host);
        hashMapList.put(listHeadline.get(2),collaboration);
    }
}
