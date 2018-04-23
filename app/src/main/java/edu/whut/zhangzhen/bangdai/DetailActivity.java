package edu.whut.zhangzhen.bangdai;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private Button btn_update;
    private TextView txv_detail_sender;
    private TextView txv_detail_information;
    private TextView txv_detail_location;
    private NeedInformation needInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar=(Toolbar)findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(" ");
        }
        txv_detail_sender=(TextView)findViewById(R.id.txv_detail_sender);
        txv_detail_information=(TextView)findViewById(R.id.txv_detail_information);
        txv_detail_location=(TextView)findViewById(R.id.txv_detail_location);
        btn_update=(Button)findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProcessingInfo();
            }
        });
        Bundle bundle=getIntent().getExtras();
        needInformation=(NeedInformation)bundle.getSerializable("detail");
        txv_detail_sender.setText(needInformation.getSender());
        txv_detail_information.setText(needInformation.getInformation());
        txv_detail_location.setText(needInformation.getLocation());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

    public void updateProcessingInfo(){
        new Thread(){
            @Override
            public void run() {
                Date date=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String datestring=sdf.format(date);
                String num="";
                String namespace="http://tempuri.org/";
                String methodname="UpdateProcessingInfo";
                String endpoint=(String) getResources().getText(R.string.endpoint);
                String soapaction=namespace+methodname;
                SoapObject soapObject=new SoapObject(namespace,methodname);
                soapObject.addProperty("sqlstr","insert into tb_processing_info values('"+((CustomApplication)(getApplication())).getUserid()+"','"+((CustomApplication)(getApplication())).getUsername()+"','"+needInformation.getSenderid()+"','"+needInformation.getSender()+"','"+needInformation.getTitle()+"','"+needInformation.getInformation()+"','"+needInformation.getLocation()+"','"+datestring+"','"+needInformation.getDeadline()+"');delete from tb_need_info where date='"+needInformation.getDate()+"'and senderid='"+needInformation.getSenderid()+"'");
                SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(soapObject);
                HttpTransportSE httpTransportSE=new HttpTransportSE(endpoint);
                try
                {
                    httpTransportSE.call(soapaction,envelope);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                try {
                    num=envelope.getResponse().toString();
                    /*needInformationList.add(new NeedInformation("",object.getProperty(0).toString().substring(0,2),object.getProperty(0).toString().substring(2)));
                    needInformationList.add(new NeedInformation("",object.getProperty(1).toString().substring(0,2),object.getProperty(1).toString().substring(2)));
                    needInformationList.add(new NeedInformation("",object.getProperty(2).toString().substring(0,2),object.getProperty(2).toString().substring(2)));*/
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                        /*needInformationList.add(new NeedInformation("小明","cnm","南1"));
        NeedInformation a=new NeedInformation("小红","rnm","南2");
        needInformationList.add(a);*/
                Message msg=new Message();
                msg.what=1;
                msg.arg1=Integer.parseInt(num);
                handler.sendMessage(msg);
            }
        }.start();
    }

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                if(msg.arg1!=0) {
                    Toast.makeText(getApplicationContext(), "succeed", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    };
}
