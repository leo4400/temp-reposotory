/*
* Copyright 2016 名片项目组
*/
package com.genius.card.setting.notify;

import android.os.Bundle;

import com.genius.card.R;
import com.genius.card.activitys.SuperActivity;
import com.genius.card.activitys.TitleMetaData;
import com.genius.widget.StatedFragment;

/**
 * setting中的消息管理的界面
 * <p>Created by Leo on 2016/4/20 18:06 </p>
 */
public class SettingNotificationAlarmActivity extends SuperActivity {
    private SettingNotificationAlarmFragment mFragment;
    @Override
    public TitleMetaData getToolBarTitle() {
        TitleMetaData titleMetaData = new TitleMetaData(getString(R.string.notification_alarm), true);
        return titleMetaData;
    }

    @Override
    public StatedFragment getBodyFragment() {
        if(mFragment == null){
            mFragment = new SettingNotificationAlarmFragment();
        }
        return mFragment;
    }

    @Override
    public void create(Bundle savedInstanceState) {

    }

    @Override
    public boolean hasScrollerView() {
        return true;
    }
}
