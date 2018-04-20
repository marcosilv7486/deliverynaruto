package com.marcosilv7.narutodelivery.events;

import android.support.v7.widget.RecyclerView;

public interface onMoveAndSwipedListener {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(RecyclerView.ViewHolder viewHolder);
}
