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
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.zanchen.develop.wordsdiary.util.translateUtil.TranslateUtil;

public class StudyFragment extends Fragment {

    private static final String TAG = "StudyFragment";
    //定义binding
    private FragmentStudyBinding binding;
    //定义控件
    private LinearLayout dailyWordsLayout;
    private TextView dailyWords;
    private TextView dailyWordsTranslate;
    private Button dailyWordsButton;
    private SearchView searchView;
    //定义上下文
    private Context mContext;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //初始化binding
        binding = FragmentStudyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //获取fragment上下文
        mContext = getContext();
        //绑定UI组件
        dailyWordsLayout = binding.layoutStudyFragmentDailyWords;
        dailyWords = binding.textStudyFragmentDailyWords;
        dailyWordsTranslate = binding.textStudyFragmentDailyWordsTranslate;
        dailyWordsButton = binding.btnStudyFragment;
        searchView = binding.searchViewDictionary;
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("你想查询的单词");
        //按钮响应事件
        dailyWordsLayout.setOnClickListener(dailyWordsLayoutOnClick);
        dailyWordsButton.setOnClickListener(dailyWordsButtonOnclick);
        searchView.setOnQueryTextListener(onQueryText);
        //初始化数据
        initData();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initData(){
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

    }

    public View.OnClickListener dailyWordsLayoutOnClick = view -> {
        if(dailyWordsTranslate.getVisibility() == View.VISIBLE){
            dailyWordsTranslate.setVisibility(View.GONE);
        }else {
            dailyWordsTranslate.setVisibility(View.VISIBLE);
        }
    };


    public View.OnClickListener dailyWordsButtonOnclick = view -> {
        @SuppressLint("ShowToast")
        String s = "test";
        StudyFragmentDirections.ActionNavigationStudyToStudyInfoFragment action;
        action = StudyFragmentDirections.actionNavigationStudyToStudyInfoFragment();
        action.setTestString(s);
        Navigation.findNavController(view).navigate(action);
    };

    public SearchView.OnQueryTextListener onQueryText = new SearchView.OnQueryTextListener() {
        //单击搜索按钮时激发该方法
        @Override
        public boolean onQueryTextSubmit(String query) {
            Toast toast = Toast.makeText(mContext,"功能正在开发中！",Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        //用户输入时激发该方法
        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}