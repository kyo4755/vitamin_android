package com.vitamin.wecantalk.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.CommunityRoomActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * Created by JongHwa on 2018-04-17.
 */

public class FriendsProfileFragment extends BlurDialogFragment {

    private static final String BUNDLE_KEY_DOWN_SCALE_FACTOR = "bundle_key_down_scale_factor";
    private static final String BUNDLE_KEY_BLUR_RADIUS = "bundle_key_blur_radius";
    private static final String BUNDLE_KEY_DIMMING = "bundle_key_dimming_effect";
    private static final String BUNDLE_KEY_DEBUG = "bundle_key_debug_effect";

    private int mRadius;
    private float mDownScaleFactor;
    private boolean mDimming;
    private boolean mDebug;
    private static FriendsListViewPOJO data;
    String room_number;

    public static FriendsProfileFragment newInstance(int radius, float downScaleFactor, boolean dimming, boolean debug, FriendsListViewPOJO pojo) {
        FriendsProfileFragment fragment = new FriendsProfileFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_KEY_BLUR_RADIUS, radius);
        args.putFloat(BUNDLE_KEY_DOWN_SCALE_FACTOR, downScaleFactor);
        args.putBoolean(BUNDLE_KEY_DIMMING, dimming);
        args.putBoolean(BUNDLE_KEY_DEBUG, debug);
        data=pojo;

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle args = getArguments();
        mRadius = args.getInt(BUNDLE_KEY_BLUR_RADIUS);
        mDownScaleFactor = args.getFloat(BUNDLE_KEY_DOWN_SCALE_FACTOR);
        mDimming = args.getBoolean(BUNDLE_KEY_DIMMING);
        mDebug = args.getBoolean(BUNDLE_KEY_DEBUG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_friends_profile, null);

        Button talk_btn = view.findViewById(R.id.friends_profile_add);

        talk_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AQuery aQuery = new AQuery(view.getContext());
                String chattings_open_url = Config.Server_URL + "chattings/open";

                Map<String, Object> params = new LinkedHashMap<>();

                params.put("myid", GlobalInfo.my_profile.getId());

                ArrayList<String> mem = new ArrayList<>();
                String member="";
                mem.add(GlobalInfo.my_profile.getId());
                mem.add(data.getId());
                Collections.sort(mem);
                for (String s : mem){
                    member += s + ",";
                }
                member=member.substring(0, member.length()-1);
                params.put("member", member);

                final String finalMember = member;
                aQuery.ajax(chattings_open_url, params, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String result, AjaxStatus status) {

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String result_code = jsonObject.get("result").toString();
                            if (result_code.equals("0000")) {
                                room_number = jsonObject.getString("room_num");
                                Intent it = new Intent(getActivity(), CommunityRoomActivity.class);
                                it.putExtra("room_num",room_number);
                                it.putExtra("member", finalMember);
                                startActivity(it);
                                Toast.makeText(view.getContext(), "정상.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(view.getContext(), "오류.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("chat_open Error", e.toString());
                            Toast.makeText(view.getContext(), "서버와 통신오류.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });

        builder.setView(view);

        return builder.create();
    }

    @Override
    protected boolean isDebugEnable() {
        return mDebug;
    }

    @Override
    protected boolean isDimmingEnable() {
        return mDimming;
    }

    @Override
    protected boolean isActionBarBlurred() {
        return true;
    }

    @Override
    protected float getDownScaleFactor() {
        return mDownScaleFactor;
    }

    @Override
    protected int getBlurRadius() {
        return mRadius;
    }
}
