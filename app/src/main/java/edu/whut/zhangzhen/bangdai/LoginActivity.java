package edu.whut.zhangzhen.bangdai;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private Button btn_confirm;
    private EditText edt_userid;
    private EditText edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_confirm =(Button)findViewById(R.id.btn_confirm);
        edt_userid=(EditText) findViewById(R.id.edt_userid);
        edt_password=(EditText)findViewById(R.id.edt_password);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        String userid=edt_userid.getText().toString();
                        String password=edt_password.getText().toString();
                        OkHttpClient httpclient =new OkHttpClient();
                        RequestBody body = new FormBody.Builder().add("systemId","").add("xmlmsg","").add("userName",userid).add("password",password).add("type","xs").build();
                        Request request =new Request.Builder().url("http://sso.jwc.whut.edu.cn/Certification/login.do").post(body).build();
                        Response response;
                        for(int i=1;i<50;i++) {
                            try {
                                String username = "";
                                response = httpclient.newCall(request).execute();
                                String responsestring=response.body().string();
                                Document document = Jsoup.parse(responsestring);
                                Element nameelement = document.select("i").first();
                                if (null != nameelement) {
                                    username = nameelement.html();
                                    Log.e("abc",username);
                                    ((CustomApplication)getApplication()).setUserid(userid);
                                    ((CustomApplication)getApplication()).setUsername(username);
                                    /*Intent intent = new Intent(LoginActivity.this, TestActivity.class);
                                    intent.putExtra("response",username);
                                    startActivity(intent);*/
                                    Intent intent=new Intent();
                                    intent.putExtra("username",username);
                                    setResult(2,intent);
                                    finish();
                                    break;
                                }
                                /*Intent intent = new Intent(LoginActivity.this, TestActivity.class);
                                intent.putExtra("response",responsestring);
                                startActivity(intent);
                                break;*/
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Intent intent=new Intent();
                        intent.putExtra("username","error");
                        setResult(2,intent);
                        finish();
                        super.run();
                    }
                }.start();
                /*new Thread(){
                    @Override
                    public void run() {
                        OkHttpClient httpclient_login =new OkHttpClient();
                        Request request =new Request.Builder().url("https://www.baidu.com").get().build();
                        Response response;
                        try {
                            response = httpclient_login.newCall(request).execute();

                        }
                        catch (IOException e)
                        {
                            Toast.makeText(LoginActivity.this,"error",Toast.LENGTH_SHORT).show();
                        }
                        super.run();
                    }
                }.start();*/

            }
        });
    }

}
