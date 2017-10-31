package com.waspy.sayekti.waspy.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waspy.sayekti.waspy.R;
import com.waspy.sayekti.waspy.db.Doo;

import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link ContentLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentLogFragment extends Fragment {
    private static final String TITLE = "title";
    private RealmResults<Doo> doos;
    private String mTitle;
    private TextView txtLog;


    public static ContentLogFragment newInstance(String title) {
        ContentLogFragment fragment = new ContentLogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public void setDoos(RealmResults<Doo> doos) {
        this.doos = doos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        txtLog = view.findViewById(R.id.txtLog);
        txtLog.setTextColor(Color.parseColor("#0B0719"));

        for (int i = 0; i < doos.size(); i++) {
            if (doos.get(i).getTitle().toLowerCase().equalsIgnoreCase(mTitle.toLowerCase())) {
                txtLog.append(Html.fromHtml(DateFormat.format("dd-MMM-yyyy, HH:mm:ss", doos.get(i).getTime()) + " : <b>" + doos.get(i).getText() + "</b><br><br>"));
            }
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
