package com.zanchen.develop.wordsdiary.fragment.dictionary;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.zanchen.develop.wordsdiary.R;
import com.zanchen.develop.wordsdiary.database.entity.Dictionary;
import com.zanchen.develop.wordsdiary.databinding.FragmentDictionaryBinding;
import com.zanchen.develop.wordsdiary.databinding.FragmentStudyBinding;
import com.zanchen.develop.wordsdiary.databinding.FragmentStudyInfoBinding;
import com.zanchen.develop.wordsdiary.fragment.study.studyInfo.StudyInfoFragmentArgs;

public class DictionaryFragment extends Fragment {

    private static final String TAG = "DictionaryFragment";

    private DictionaryViewModel mViewModel;

    //定义binding
    private FragmentDictionaryBinding binding;

    //定义控件
    private TextView mTextWordName;
    private TextView mTextWordPronounceUK;
    private TextView mTextWordPronounceUS;
    private TextView mTextWordMeanCN;

    //定义上下文
    private Context mContext;

    public static DictionaryFragment newInstance() {
        return new DictionaryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDictionaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mTextWordName = binding.textDictionaryFragmentWordname;
        mTextWordPronounceUK = binding.textDictionaryFragmentWordpronounceUK;
        mTextWordPronounceUS = binding.textDictionaryFragmentWordpronounceUS;
        mTextWordMeanCN = binding.textDictionaryFragmentWordmeanCN;

        initData(DictionaryFragmentArgs.fromBundle(getArguments()).getWordname());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initData(String wordName){
        mViewModel = new ViewModelProvider(this).get(DictionaryViewModel.class);
        mViewModel.initViewModel(wordName);

        final MutableLiveData<String> mWordName = (MutableLiveData<String>)mViewModel.getWordName();
        mWordName.observe(getViewLifecycleOwner(),s -> mTextWordName.setText(s));

        final MutableLiveData<String> mWordPronounceUK = (MutableLiveData<String>)mViewModel.getWordPronounceUK();
        mWordPronounceUK.observe(getViewLifecycleOwner(),s -> mTextWordPronounceUK.setText(s));

        final MutableLiveData<String> mWordPronounceUS = (MutableLiveData<String>)mViewModel.getWordPronounceUS();
        mWordPronounceUS.observe(getViewLifecycleOwner(),s -> mTextWordPronounceUK.setText(s));

        final MutableLiveData<String> mWordMeanCN = (MutableLiveData<String>)mViewModel.getWordMeanCN();
        mWordMeanCN.observe(getViewLifecycleOwner(),s -> mTextWordMeanCN.setText(s));

    }


}