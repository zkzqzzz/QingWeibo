/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chuang.myweibo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.chuang.myweibo.home.activity.MainActivity;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * 该类定义了微博授权时所需要的参数。
 *
 * @author SINA
 * @since 2013-10-07
 */
public class AccessTokenKeeper {
    private static final String PREFERENCES_WEIBO_USER_NAME="com_chuang_myweibo";
    private static final String KEY_WEIBO_USER_NAME="weibo_username";/////////


    private static final String PREFERENCES_NAME = "com_weibo_sdk_android";

    private static final String KEY_UID = "uid";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";

    /**
     * 保存 Token 对象到 SharedPreferences。
     *
     * @param context 应用程序上下文环境
     * @param token   Token 对象
     */
    public static void writeAccessToken(Context context, Oauth2AccessToken token) {
        if (null == context || null == token) {
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();

        editor.putString(KEY_UID, token.getUid());
        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
        editor.putString(KEY_REFRESH_TOKEN, token.getRefreshToken());
        editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
        editor.commit();
        Log.d("writeAccessToken", "AccessToken已写入SP");
    }

    /**
     * 从 SharedPreferences 读取 Token 信息。
     *
     * @param context 应用程序上下文环境
     * @return 返回 Token 对象
     */
    public static Oauth2AccessToken readAccessToken(Context context) {
        if (null == context) {
            return null;
        }

        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        token.setUid(pref.getString(KEY_UID, ""));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
        token.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, ""));
        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));

//        Log.d("readAccessToken", "AccessToken已从SP获取");
        return token;
    }

    public static void writeUserName(Context context,String username){
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_WEIBO_USER_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(KEY_WEIBO_USER_NAME,username);/////////////
        editor.commit();
    }

    public static void readUserName(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_WEIBO_USER_NAME, Context.MODE_APPEND);
        MainActivity.weiboUserName=pref.getString(KEY_WEIBO_USER_NAME,"");
//        Log.d("12","成功获取微博name  "+MainActivity.weiboUserName);
    }
    /**
     * 清空 SharedPreferences 中 Token信息。
     *
     * @param context 应用程序上下文环境
     */
    public static void clear(Context context) {
        if (null == context) {
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
