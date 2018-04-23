package edu.whut.zhangzhen.bangdai;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mdrawerlayout;
    private ViewPager vp;
    private TabLayout tabLayout;
    private Button btn_login;
    private TextView txv_username;
    private int requestcode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mdrawerlayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        vp=(ViewPager)findViewById(R.id.main_vp);
        tabLayout=(TabLayout)findViewById(R.id.main_tab_layout);
        List<String> list=new ArrayList<>();
        list.add("show");
        list.add("add");
        list.add("processing");
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(new MainFragmentAdapter(getSupportFragmentManager(),list));
        tabLayout.setupWithViewPager(vp);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu);
        tabLayout.getTabAt(0).setText("show");
        tabLayout.getTabAt(1).setIcon(R.drawable.head);
        tabLayout.getTabAt(1).setText("add");
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setTitle(" ");
        }
        View headview=((NavigationView)findViewById(R.id.nav_view)).inflateHeaderView(R.layout.nav_header);
        btn_login=(Button)headview.findViewById(R.id.btn_login);
        txv_username=(TextView)headview.findViewById(R.id.txv_username);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CustomApplication)getApplication()).getUserid().equals("")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, requestcode);
                }
                else
                {
                    btn_login.setText("登录");
                    txv_username.setText("");
                    ((CustomApplication)getApplication()).setUsername("");
                    ((CustomApplication)getApplication()).setUserid("");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mdrawerlayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mdrawerlayout.isDrawerOpen(findViewById(R.id.nav_view)))
        {
            mdrawerlayout.closeDrawers();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2){
            if(requestCode==requestcode){
                if(!(data.getStringExtra("username").equals("error"))){
                    btn_login.setText("退出登录");
                    txv_username.setText(data.getStringExtra("username"));
                }
            }
        }
    }
}
