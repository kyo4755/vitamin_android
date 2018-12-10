package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordCloudFragment extends Fragment {

    String history="",answer,isEnd="",bool="0";
    TextView num1,num2,num3,num4,question,end_message;
    ImageView next_button;
    LinearLayout l1,l2,l3,l4;
    Context context;
    LottieAnimationView loading_animation,loading_animation2,loading_animation3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        context = container.getContext();
        View view = inflater.inflate(R.layout.activity_exam, null);

        question=view.findViewById(R.id.question);
        next_button=view.findViewById(R.id.next_button);
        end_message=view.findViewById(R.id.end_message);
        num1=view.findViewById(R.id.exam_num1);
        num2=view.findViewById(R.id.exam_num2);
        num3=view.findViewById(R.id.exam_num3);
        num4=view.findViewById(R.id.exam_num4);
        l1=view.findViewById(R.id.num1_layout);
        l2=view.findViewById(R.id.num2_layout);
        l3=view.findViewById(R.id.num3_layout);
        l4=view.findViewById(R.id.num4_layout);
        loading_animation = view.findViewById(R.id.quiz_check_animation);
        loading_animation2 = view.findViewById(R.id.quiz_check_animation2);
        loading_animation3 = view.findViewById(R.id.quiz_check_animation3);

        getExam();

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEnd.equals("0")&&answer.equals("1")) {
                    loading_animation.setAnimation("success.json");
                    loading_animation.setVisibility(View.VISIBLE);
                    loading_animation.playAnimation();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading_animation.setVisibility(View.GONE);
                            loading_animation2.cancelAnimation();
                        }
                    }, 1000);
                    getExam();
                }else if(isEnd.equals("0")){
                    loading_animation2.setAnimation("error_cross.json");
                    loading_animation2.setVisibility(View.VISIBLE);
                    loading_animation2.playAnimation();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading_animation2.setVisibility(View.GONE);
                            loading_animation2.cancelAnimation();
                        }
                    }, 1000);
                }
            }
        });l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEnd.equals("0")&&answer.equals("2")) {
                    loading_animation.setAnimation("success.json");
                    loading_animation.setVisibility(View.VISIBLE);
                    loading_animation.playAnimation();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading_animation.setVisibility(View.GONE);
                            loading_animation2.cancelAnimation();
                        }
                    }, 1000);
                    getExam();
                }else if(isEnd.equals("0")){
                    loading_animation2.setAnimation("error_cross.json");
                    loading_animation2.setVisibility(View.VISIBLE);
                    loading_animation2.playAnimation();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading_animation2.setVisibility(View.GONE);
                            loading_animation2.cancelAnimation();
                        }
                    }, 1000);
                }
            }
        });l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEnd.equals("0")&&answer.equals("3")) {
                    loading_animation.setAnimation("success.json");
                    loading_animation.setVisibility(View.VISIBLE);
                    loading_animation.playAnimation();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading_animation.setVisibility(View.GONE);
                            loading_animation2.cancelAnimation();
                        }
                    }, 1000);
                    getExam();
                }else if(isEnd.equals("0")){
                    loading_animation2.setAnimation("error_cross.json");
                    loading_animation2.setVisibility(View.VISIBLE);
                    loading_animation2.playAnimation();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading_animation2.setVisibility(View.GONE);
                            loading_animation2.cancelAnimation();
                        }
                    }, 1000);
                }
            }
        });l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEnd.equals("0")&&answer.equals("4")) {
                    loading_animation.setAnimation("success.json");
                    loading_animation.setVisibility(View.VISIBLE);
                    loading_animation.playAnimation();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading_animation.setVisibility(View.GONE);
                            loading_animation2.cancelAnimation();
                        }
                    }, 1000);
                    getExam();
                }else if(isEnd.equals("0")){
                    loading_animation2.setAnimation("error_cross.json");
                    loading_animation2.setVisibility(View.VISIBLE);
                    loading_animation2.playAnimation();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading_animation2.setVisibility(View.GONE);
                            loading_animation2.cancelAnimation();
                        }
                    }, 1000);
                }
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getExam();
            }
        });

        return view;
    }

    private void getExam(){

        AQuery aQuery = new AQuery(context);
        String friends_list_url = Config.Server_URL + "study/getQuiz";

        Map<String, Object> params = new LinkedHashMap<>();

        params.put("index", history);

        aQuery.ajax(friends_list_url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();
                    if (result_code.equals("0000")) {
                        isEnd=jsonObject.get("isEnd").toString();
                        if(isEnd.equals("1")){
                            end_message.setText("모든 문제가 끝났습니다.");
                            next_button.setVisibility(View.GONE);
                            loading_animation3.setAnimation("fireworks.json");
                            loading_animation3.setVisibility(View.VISIBLE);
                            loading_animation3.playAnimation();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loading_animation3.setVisibility(View.GONE);
                                }
                            }, 5000);
                            bool="1";
                        }else {
                            history = history + "||" + jsonObject.get("index").toString();
                            question.setText(jsonObject.get("question").toString());
                            num1.setText(jsonObject.get("num1").toString());
                            num2.setText(jsonObject.get("num2").toString());
                            num3.setText(jsonObject.get("num3").toString());
                            num4.setText(jsonObject.get("num4").toString());
                            answer = jsonObject.get("answer").toString();
                        }
                    }
                    else {
                        Toast.makeText(context, "result 0000 아님", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
