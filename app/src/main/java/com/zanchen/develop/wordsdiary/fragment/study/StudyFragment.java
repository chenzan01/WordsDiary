package com.zanchen.develop.wordsdiary.fragment.study;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.zanchen.develop.wordsdiary.databinding.FragmentStudyBinding;
import com.zanchen.develop.wordsdiary.fragment.study.studyInfo.StudyInfoFragment;

public class StudyFragment extends Fragment {

    private static final String TAG = "StudyFragment";
    //定义binding
    private FragmentStudyBinding binding;
    //定义控件
    private TextView dailyWords;
    private TextView dailyWordsTranslate;
    private Button dailyWordsButton;
    private Context mContext;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //初始化binding
        binding = FragmentStudyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //获取fragment上下文
        mContext = getContext();
        //绑定UI组件
        dailyWords = binding.textStudyFragmentDailyWords;
        dailyWordsTranslate = binding.textStudyFragmentDailyWordsTranslate;
        dailyWordsButton = binding.btnStudyFragment;
        //初始化View
        initView();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(){
        if(dailyWords == null && dailyWordsTranslate == null && dailyWordsButton == null){
            Log.d(TAG,"init Error!");
            return;
        }
        StudyViewModel studyViewModel = new ViewModelProvider(this).get(StudyViewModel.class);
        //获取ViewModel中的每日单词
        final MutableLiveData<String> words = (MutableLiveData<String>)studyViewModel.getDailyWords();
        words.observe(getViewLifecycleOwner(), s -> dailyWords.setText(s));
        //获取ViewModel中的每日单词翻译
        final MutableLiveData<String> translate = (MutableLiveData<String>)studyViewModel.getDailyWordsTranslate();
        translate.observe(getViewLifecycleOwner(), s -> dailyWordsTranslate.setText(s));
        //按钮响应事件
        dailyWordsButton.setOnClickListener(onclick);

    }

    public View.OnClickListener onclick = view -> {
        @SuppressLint("ShowToast")
        String s = "test";
        StudyFragmentDirections.ActionNavigationStudyToStudyInfoFragment action;
        action = StudyFragmentDirections.actionNavigationStudyToStudyInfoFragment();
        action.setTestString(s);Navigation.findNavController(view).navigate(action);

    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}