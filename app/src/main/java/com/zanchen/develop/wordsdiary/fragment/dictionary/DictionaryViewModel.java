package com.zanchen.develop.wordsdiary.fragment.dictionary;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.zanchen.develop.wordsdiary.database.entity.Dictionary;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DictionaryViewModel extends ViewModel {
    private static final String TAG = "DictionaryViewModel";

    private MutableLiveData<String> mWordName;
    private MutableLiveData<String> mWordPronounceUK;
    private MutableLiveData<String> mWordPronounceUS;
    private MutableLiveData<String> mWordMeanCN;

    public void initViewModel(String wordName){

        mWordName = new MutableLiveData<>();
        mWordPronounceUK = new MutableLiveData<>();
        mWordPronounceUS = new MutableLiveData<>();
        mWordMeanCN = new MutableLiveData<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject json = new JSONObject();
                    json.put("wordname",wordName);
                    //http客户端
                    OkHttpClient client = new OkHttpClient();
                    //http请求
                    Request request = new Request.Builder()
                            .url("http://192.168.1.34:8080/dictionary/queryWords")
                            .post(RequestBody.create(MediaType.parse("application/json"),json.toString()))
                            .build();
                    //执行发送
                    Response response = client.newCall(request).execute();
                    Log.d(TAG,"发送成功！");
                    String result = null;
                    if(response.body() != null){
                        result = Objects.requireNonNull(response.body()).string();
                    }
                    Log.d(TAG,"result: " + result);
                    Message message = Message.obtain();
                    message.obj = result;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d(TAG,"网络连接失败！");
                }
            }
        }).start();
    }

    public LiveData<String> getWordName() {
        return mWordName;
    }

    public LiveData<String> getWordPronounceUK() {
        return mWordPronounceUK;
    }

    public LiveData<String> getWordPronounceUS() {
        return mWordPronounceUS;
    }

    public LiveData<String> getWordMeanCN() {
        return mWordMeanCN;
    }

    private final Handler handler = new Handler(msg -> {
        String s = msg.obj.toString();
        JSONObject jsonObject = JSONObject.parseObject(s);
        Log.d(TAG,"UI: " + jsonObject);
        mWordName.postValue((String) Objects.requireNonNull(jsonObject.get("wordname")));
        mWordPronounceUK.postValue((String) Objects.requireNonNull(jsonObject.get("wordpronounce_UK")));
        mWordPronounceUS.postValue((String) Objects.requireNonNull(jsonObject.get("wordpronounce_US")));
        mWordMeanCN.postValue((String) Objects.requireNonNull(jsonObject.get("wordmean_CN")));
        return false;
    });
}