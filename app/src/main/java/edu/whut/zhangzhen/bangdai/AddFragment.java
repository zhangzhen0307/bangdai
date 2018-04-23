package edu.whut.zhangzhen.bangdai;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Acer on 2018/3/27.
 */

public class AddFragment extends Fragment {
    private String userid,username;
    private View view;
    public AddFragment(){
    }

    public static AddFragment NewInstance(){
        AddFragment addFragment=new AddFragment();
        return addFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.add_fragment,container,false);
        Button btn_add=(Button)view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid=((CustomApplication)(getActivity().getApplication())).getUserid();
                username=((CustomApplication)(getActivity().getApplication())).getUsername();
                if(userid.equals("")){
                    Toast.makeText(getActivity(),"error",Toast.LENGTH_LONG).show();
                }
                else
                    insertNeedInformation();
            }
        });
        return view;
    }

    public void insertNeedInformation(){
        new Thread(){
            @Override
            public void run() {
                EditText edt_title=(EditText)view.findViewById(R.id.edt_title);
                EditText edt_information=(EditText)view.findViewById(R.id.edt_info);
                EditText edt_location=(EditText)view.findViewById(R.id.edt_location);
                EditText edt_deadline=(EditText)view.findViewById(R.id.edt_deadline);
                String title=edt_title.getText().toString();
                String information=edt_information.getText().toString();
                String location=edt_location.getText().toString();
                String deadline=edt_deadline.getText().toString();
                Date date=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String datestring=sdf.format(date);
                String num="";
                String namespace="http://tempuri.org/";
                String methodname="InsertNeedInfo";
                String endpoint=(String) getResources().getText(R.string.endpoint);
                String soapaction=namespace+methodname;
                SoapObject soapObject=new SoapObject(namespace,methodname);
                soapObject.addProperty("sqlstr","insert into tb_need_info values('"+userid+"','"+username+"','"+title+"','"+information+"','"+location+"','"+datestring+"','"+deadline+"')");
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
                    Log.e("abc",num);
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
                if(msg.arg1!=0)
                Toast.makeText(getContext(),"succeed",Toast.LENGTH_LONG).show();
            }
        }
    };
}
