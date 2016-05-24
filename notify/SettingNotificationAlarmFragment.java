/*
* Copyright 2016 名片项目组
*/
package com.genius.card.setting.notify;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genius.card.R;
import com.genius.card.me.ItemData;
import com.genius.card.me.ItemDivider;
import com.genius.card.me.ItemLine;
import com.genius.card.me.ItemSwitch;
import com.genius.card.me.ItemText;
import com.genius.card.setting.SettingAdapter;
import com.genius.card.util.Clog;
import com.genius.card.util.ItemBase;
import com.genius.card.util.OnItemClickListener;
import com.genius.card.util.SettingUtil;
import com.genius.card.util.UserUtil;
import com.genius.widget.StatedFragment;

import java.util.ArrayList;

/**
 * <p>Created by Leo on 2016/4/22 17:14 </p>
 */
public class SettingNotificationAlarmFragment extends StatedFragment implements OnItemClickListener {
    /** 消息免打扰 */
    private static final String TAG_REFUSE_ACCEPT_MESSAGE = "refuse_accept_message";
    /** 短信铃声 */
    private static final String TAG_SMS_RINGTONE = "sms_ringtone";
    /** 来电铃声 */
    private static final String TAG_CALL_RINGTONE = "call_ringtone";
    /** 振动 */
    private static final String TAG_VIBRATION = "vibration";
    /** 所属公司、组织消息推送*/
    private static final String TAG_COMPANY_OR_ORGANIZATION_INFORMATION_PUSH = "company_or_organization_information_push";
    private RecyclerView mRecyclerView;
    private SettingAdapter mAdapter;
    private SettingUtil settingUtil;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_second_activity, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SettingAdapter(false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if(UserUtil.getCurrentUser(getContext()).isLoginState()) {
            String userName = UserUtil.getCurrentUser(getContext()).getUserName();
            settingUtil =SettingUtil.getInstance(getContext(),userName);
        }else {
            settingUtil = SettingUtil.getInstance(getContext(),SettingUtil.default_username);
        }
        mAdapter.setData(initData());
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private ArrayList<ItemData> initData() {
        ArrayList<ItemData> itemList = new ArrayList<>();
        Clog.d("4444"+ settingUtil.isMsgNotDisturb());
        ItemSwitch refuseAcceptMessageItem = new ItemSwitch();
        refuseAcceptMessageItem.setDisplayName(getString(R.string.refuse_accept_message));
        refuseAcceptMessageItem.setTag(TAG_REFUSE_ACCEPT_MESSAGE);
        refuseAcceptMessageItem.setIsChecked(settingUtil.isMsgNotDisturb());
        itemList.add(refuseAcceptMessageItem);
        if (!settingUtil.isMsgNotDisturb()) {
            itemList.add(new ItemLine());

            ItemText smsRingToneItem = new ItemText(getString(R.string.sms_ringtone));
            smsRingToneItem.setTag(TAG_SMS_RINGTONE);
            itemList.add(smsRingToneItem);
            itemList.add(new ItemLine());

            ItemText callRingToneItem = new ItemText(getString(R.string.call_ringtone));
            callRingToneItem.setTag(TAG_CALL_RINGTONE);
            itemList.add(callRingToneItem);
        }
        itemList.add(new ItemDivider());

        ItemSwitch vibrationMessageItem = new ItemSwitch();
        vibrationMessageItem.setDisplayName(getString(R.string.vibration));
        vibrationMessageItem.setIsChecked(settingUtil.getVibrationData());
        vibrationMessageItem.setTag(TAG_VIBRATION);
        itemList.add(vibrationMessageItem);
        itemList.add(new ItemLine());

        ItemSwitch companyOrOrganizationInformationPushItem =new ItemSwitch();
        companyOrOrganizationInformationPushItem.setDisplayName(getString(R.string.company_or_organization_information_push));
        companyOrOrganizationInformationPushItem.setTag(TAG_COMPANY_OR_ORGANIZATION_INFORMATION_PUSH);
        companyOrOrganizationInformationPushItem.setIsChecked(settingUtil.getCompany());
        itemList.add(companyOrOrganizationInformationPushItem);

        return itemList;
    }

    @Override
    public void onItemClick(ItemBase meta) {
        ItemData data=(ItemData)meta;
        switch (data.getTag()){
            case TAG_REFUSE_ACCEPT_MESSAGE:
                final ItemSwitch refuseSwitchData =(ItemSwitch)data;
                settingUtil.setMsgNotDisturb(refuseSwitchData.isChecked());
                settingUtil.saveMsgNotDisturb(getContext());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(initData());
                    }
                }, DEFAULT_DELAY);
                break;
            case TAG_VIBRATION:
                final ItemSwitch vibrationSwitchData =(ItemSwitch)data;
                settingUtil.setVibrationData(vibrationSwitchData.isChecked());
                settingUtil.saveVibration(getContext());
                mAdapter.setData(initData());
                break;
            case TAG_COMPANY_OR_ORGANIZATION_INFORMATION_PUSH:
                final ItemSwitch companySwitchData =(ItemSwitch)data;
                settingUtil.setCompany(companySwitchData.isChecked());
                settingUtil.saveCompany(getContext());
                mAdapter.setData(initData());
                break;
        }
    }
}
