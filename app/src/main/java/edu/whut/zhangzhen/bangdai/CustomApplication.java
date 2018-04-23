package edu.whut.zhangzhen.bangdai;

import android.app.Application;

/**
 * Created by Acer on 2018/3/31.
 */

public class CustomApplication extends Application {
    private String userid="";
    private String username="";
    public String getUserid(){return userid;}
    public void setUserid(String userid){this.userid=userid;}
    public String getUsername(){return username;}
    public void setUsername(String username){this.username=username;}
}
