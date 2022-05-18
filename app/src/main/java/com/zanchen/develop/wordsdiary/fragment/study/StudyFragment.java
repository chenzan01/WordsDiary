package com.zanchen.develop.wordsdiary.fragment.study;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.alibaba.fastjson.JSONObject;
import com.zanchen.develop.wordsdiary.database.entity.Dictionary;
import com.zanchen.develop.wordsdiary.databinding.FragmentStudyBinding;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    //词典
    public Dictionary dictionary = new Dictionary();


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
        //跳转到StudyInfoFragment
        StudyFragmentDirections.ActionNavigationStudyToStudyInfoFragment action;
        action = StudyFragmentDirections.actionNavigationStudyToStudyInfoFragment();
        action.setTestString(s);
        Navigation.findNavController(view).navigate(action);
        Log.d(TAG,"dictionary: " + dictionary.getWordname());
    };

    public SearchView.OnQueryTextListener onQueryText = new SearchView.OnQueryTextListener() {
        //单击搜索按钮时激发该方法
        @Override
        public boolean onQueryTextSubmit(String wordname) {
            //跳转到DictionaryFragment
            StudyFragmentDirections.ActionNavigationStudyToDictionaryFragment action;
            action = StudyFragmentDirections.actionNavigationStudyToDictionaryFragment();
            action.setWordname(wordname);
            Navigation.findNavController(binding.getRoot()).navigate(action);

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try{
//                        JSONObject json = new JSONObject();
//                        json.put("wordname",wordname);
//                        //http客户端
//                        OkHttpClient client = new OkHttpClient();
//                        //http请求
//                        Request request = new Request.Builder()
//                                .url("http://192.168.1.34:8080/dictionary/queryWords")
//                                .post(RequestBody.create(MediaType.parse("application/json"),json.toString()))
//                                .build();
//                        //执行发送
//                        Response response = client.newCall(request).execute();
//                        Log.d(TAG,"发送成功！");
//                        String result = null;
//                        if(response.body() != null){
//                            result = Objects.requireNonNull(response.body()).string();
//                        }
//                        Log.d(TAG,"result: " + result);
//                        Message message = Message.obtain();
//                        message.obj = result;
//                        handler.sendMessage(message);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                        Log.d(TAG,"网络连接失败！");
//                        Toast toast = Toast.makeText(mContext,"网络连接失败！",Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//                }
//            }).start();
            return false;
        }
        //用户输入时激发该方法
        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    private final Handler handler = new Handler(msg -> {
        String s = msg.obj.toString();
        JSONObject jsonObject = JSONObject.parseObject(s);
        Log.d(TAG,"UI: " + jsonObject);
        dictionary.setWid((int) Objects.requireNonNull(jsonObject.get("wid")));
        dictionary.setWordname((String) Objects.requireNonNull(jsonObject.get("wordname")));
        dictionary.setWordpronounce_UK((String) Objects.requireNonNull(jsonObject.get("wordpronounce_UK")));
        dictionary.setWordpronounce_US((String) Objects.requireNonNull(jsonObject.get("wordpronounce_US")));
        dictionary.setWordmean_CN((String) Objects.requireNonNull(jsonObject.get("wordmean_CN")));
        return false;
    });


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}