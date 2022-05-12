package com.zanchen.develop.wordsdiary.fragment.study.bookInfo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zanchen.develop.wordsdiary.R;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookInfoFragment extends Fragment {

    private static final String TAG = "BookInfoFragment";

    private BookInfoViewModel mViewModel;

    public static BookInfoFragment newInstance() {
        return new BookInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    String json = "{\n" +
//                            "    \"username\": \"admin\",\n" +
//                            "    \"password\": \"123456\"\n" +
//                            "}";
//                    //http客户端
//                    OkHttpClient client = new OkHttpClient();
//                    //http请求
//                    Request request = new Request.Builder()
//                            .url("http://192.168.1.34:8080/login/login")
//                            .post(RequestBody.create(MediaType.parse("application/json"),json))
//                            .build();
//                    //执行发送
//                    Response response = client.newCall(request).execute();
//                    Log.d(TAG,"发送成功！" + response);
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                    Log.d(TAG,"网络连接失败！");
//                }
//            }
//        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    //http客户端
//                    OkHttpClient client = new OkHttpClient();
//                    //http请求
//                    Request request = new Request.Builder()
//                            .url("http://192.168.1.34:8080/wordtest/queryAllWords")
//                            .post(RequestBody.create(null,""))
//                            .build();
//                    //执行发送
//                    Response response = client.newCall(request).execute();
//                    Log.d(TAG,"请求成功！responseBody：" + Objects.requireNonNull(response.body()).string());
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                    Log.d(TAG,"网络连接失败！");
//                }
//            }
//        }).start();





        return inflater.inflate(R.layout.fragment_book_info, container, false);
    }


}