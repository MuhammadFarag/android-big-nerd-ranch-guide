package com.mfarag.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by muhammadfarag on 9/22/15.
 */
public class CrimeFragment extends Fragment {
    public static final String CRIME_DATE_FORMAT = "EEEE, MMMM d, yyyy";
    private static final String ARG_CRIME_ID = "crime_id";
    public static final String EXTRA_CRIME_ID = "com.mfarag.criminalintent.crime_id";
    private Crime mCrime;
    private CrimeLab mCrimeLab;
    private EditText mTitleField;
    private CheckBox mSolvedCheckBox;
    private Button mDateButton;

    public static CrimeFragment newInstance(UUID uuid) {
        CrimeFragment crimeFragment = new CrimeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CRIME_ID, uuid);
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrimeLab = CrimeLab.create();
        mCrime = mCrimeLab.getCrime(crimeId);
    }

    private void crimeDetailsChanged(){
        getActivity().setResult(Activity.RESULT_OK, new Intent().putExtra(EXTRA_CRIME_ID,mCrime.getId()));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View crimeFragmentView = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField = (EditText) crimeFragmentView.findViewById(R.id.crime_title);
        mSolvedCheckBox = (CheckBox) crimeFragmentView.findViewById(R.id.crime_solved);
        mDateButton = (Button) crimeFragmentView.findViewById(R.id.crime_date);

        mTitleField.setText(mCrime.getTitle());

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
                crimeDetailsChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // intentionally left blank
            }
        });

        mDateButton.setText(DateFormat.format(CRIME_DATE_FORMAT, mCrime.getDate()));
        mDateButton.setEnabled(false);

        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
                crimeDetailsChanged();
            }
        });

        return crimeFragmentView;
    }
}
