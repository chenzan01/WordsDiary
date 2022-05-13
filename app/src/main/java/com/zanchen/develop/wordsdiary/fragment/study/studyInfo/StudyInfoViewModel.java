package com.zanchen.develop.wordsdiary.fragment.study.studyInfo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StudyInfoViewModel extends ViewModel {

    private static final String TAG = "StudyInfoViewModel";

    private MutableLiveData<String> currentBook;

    public LiveData<String> getCurrentBookName() {
        if(currentBook == null){
            currentBook = new MutableLiveData<>();
        }
        new Thread(() -> {
            try{
                //http客户端
                OkHttpClient client = new OkHttpClient();
                //http请求
                Request request = new Request.Builder()
                        .url("http://192.168.1.34:8080/cet4/queryWordsTotalAmount")
                        .post(RequestBody.create(null,""))
                        .build();
                //执行发送
                Response response = client.newCall(request).execute();
                Log.d(TAG,"请求成功！responseBody：" + Objects.requireNonNull(response.body()).string());

            }catch (Exception e){
                e.printStackTrace();
                Log.d(TAG,"网络连接失败！错误信息：" + e);
            }
        }).start();

        currentBook.setValue("Library");
        return currentBook;
    }
}