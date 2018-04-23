package edu.whut.zhangzhen.bangdai;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Acer on 2018/3/23.
 */

public class NeedInformationAdapter extends RecyclerView.Adapter<NeedInformationAdapter.ViewHolder> implements View.OnClickListener{
    private List<NeedInformation> mNeedInformationList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txv_information;
        TextView txv_location;

        public ViewHolder(View v){
            super(v);
            txv_information=(TextView)v.findViewById(R.id.txv_infomation);
            txv_location=(TextView)v.findViewById(R.id.txv_location);
        }
    }

    public NeedInformationAdapter(List<NeedInformation> list){
        mNeedInformationList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.need_information_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NeedInformation needinfo=mNeedInformationList.get(position);
        holder.txv_information.setText(needinfo.getInformation());
        holder.txv_location.setText(needinfo.getLocation());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mNeedInformationList.size();
    }

    private OnItemClickListener mOnItemClickListener=null;

    public static interface OnItemClickListener{
        void OnItemClick(View view,int position);
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.OnItemClick(v,(int)v.getTag());
        }
    }

    public void setmOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }
}
