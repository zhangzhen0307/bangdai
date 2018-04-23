package edu.whut.zhangzhen.bangdai;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Acer on 2018/3/15.
 */

public class MainFragment extends Fragment {
    public MainFragment(){
    }

    public static MainFragment NewInstance(String type)
    {
        MainFragment mainFragment=new MainFragment();
        Bundle args=new Bundle();
        args.putString("type",type);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        switch(getArguments().getString("type"))
        {
            case "show":return inflater.inflate(R.layout.show_fragment,container,false);
            case "add":return inflater.inflate(R.layout.add_fragment,container,false);
            case "processing":return inflater.inflate(R.layout.processing_fragment,container,false);
            default:return inflater.inflate(R.layout.show_fragment,container,false);
        }
    }
}
