package com.zanchen.develop.wordsdiary.fragment.study.studyInfo;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zanchen.develop.wordsdiary.R;
import com.zanchen.develop.wordsdiary.databinding.FragmentStudyInfoBinding;
import com.zanchen.develop.wordsdiary.fragment.study.StudyFragmentDirections;

public class StudyInfoFragment extends Fragment {

    private static final String TAG = "StudyInfoFragment";

    private FragmentStudyInfoBinding binding;

    private StudyInfoViewModel mViewModel;

    private LinearLayout mCurrentBook;
    private LinearLayout mNeedStudy;
    private LinearLayout mNeedReview;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //初始化binding
        binding = FragmentStudyInfoBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        mCurrentBook = binding.layoutStudyInfoFragmentBook;
        mCurrentBook.setOnClickListener(onClickCurrentBook);
        mNeedStudy = binding.layoutStudyInfoFragmentStudy;
        mNeedStudy.setOnClickListener(onClickNeedStudy);
        mNeedReview = binding.layoutStudyInfoFragmentReview;
        mNeedReview.setOnClickListener(onClickNeedReview);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"s = " + StudyInfoFragmentArgs.fromBundle(getArguments()).getTestString());
    }

    public View.OnClickListener onClickCurrentBook = view -> {
        Log.d(TAG,"onClickCurrentBook onclick");
    };

    public View.OnClickListener onClickNeedStudy = view -> {
        Log.d(TAG,"onClickNeedStudy onclick");
    };

    public View.OnClickListener onClickNeedReview = view -> {
        Log.d(TAG,"onClickNeedReview onclick");
    };





}