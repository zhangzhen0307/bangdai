package edu.whut.zhangzhen.bangdai;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Acer on 2018/3/25.
 */

public class RecycleViewItemDecoration extends RecyclerView.ItemDecoration {
    private int decoration;
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom=decoration;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = decoration;
        }
    }

    public RecycleViewItemDecoration(int decoration){
        this.decoration=decoration;
    }
}
