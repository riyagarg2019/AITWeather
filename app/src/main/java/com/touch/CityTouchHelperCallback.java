package com.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by riyagarg on 5/2/18.
 */

public class CityTouchHelperCallback extends
        ItemTouchHelper.Callback {

    private CityTouchHelperAdapter todoTouchHelperAdapter; //sending to interface

    public CityTouchHelperCallback(CityTouchHelperAdapter todoTouchHelperAdapter) { //constructer setting field
        this.todoTouchHelperAdapter = todoTouchHelperAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
