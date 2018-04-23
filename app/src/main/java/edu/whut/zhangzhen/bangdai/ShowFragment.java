package edu.whut.zhangzhen.bangdai;

import android.content.Intent;
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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 2018/3/23.
 */

public class ShowFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private List<NeedInformation> needInformationList=new ArrayList<>();
    public ShowFragment(){
    }

    public static ShowFragment NewInstance(){
        ShowFragment showFragment=new ShowFragment();
        return  showFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.show_fragment,container,false);
        initNeedInformation();
        /*initNeedInformation();
        NeedInformationAdapter adapter=new NeedInformationAdapter(needInformationList);
        recyclerView.setAdapter(adapter);*/
        return view;
    }

    public void initNeedInformation(){


        new Thread(){
            @Override
            public void run() {
                String namespace="http://tempuri.org/";
                String methodname="SelectForNeedInfo";
                String endpoint=(String) getResources().getText(R.string.endpoint);
                String soapaction=namespace+methodname;
                SoapObject soapObject=new SoapObject(namespace,methodname);
                soapObject.addProperty("sqlstr","select * from tb_need_info");
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
                    SoapObject object=(SoapObject)envelope.getResponse();
                    for(int i=0;i<object.getPropertyCount();i++){
                        Log.e("cnm",String.valueOf(object.getPropertyCount()));
                        int sender=object.getProperty(i).toString().indexOf("sender=");
                        int title=object.getProperty(i).toString().indexOf("title");
                        int information=object.getProperty(i).toString().indexOf("information");
                        int location=object.getProperty(i).toString().indexOf("location");
                        int date=object.getProperty(i).toString().indexOf("date");
                        int deadline=object.getProperty(i).toString().indexOf("deadline");
                        needInformationList.add(new NeedInformation("","",object.getProperty(i).toString().substring(9,sender),object.getProperty(i).toString().substring(sender+7,title),object.getProperty(i).toString().substring(title+6,information),object.getProperty(i).toString().substring(information+12,location),object.getProperty(i).toString().substring(location+9,date),object.getProperty(i).toString().substring(date+5,deadline),object.getProperty(i).toString().substring(deadline+9)));
                    }
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
                handler.sendMessage(msg);
            }
        }.start();
    }

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                recyclerView=(RecyclerView) view.findViewById(R.id.rv_show);
                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new RecycleViewItemDecoration(30));
                NeedInformationAdapter adapter=new NeedInformationAdapter(needInformationList);
                recyclerView.setAdapter(adapter);
                Log.e("233",String.valueOf(needInformationList.size()));
                adapter.setmOnItemClickListener(new NeedInformationAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int position) {
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("detail",needInformationList.get(position));
                        Intent intent=new Intent(getActivity(),DetailActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }
    };
}
