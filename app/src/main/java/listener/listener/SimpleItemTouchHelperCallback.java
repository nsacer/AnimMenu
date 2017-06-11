package listener.listener;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by zpf on 2016/12/2.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final float ALPHA = 1.0f;
    private OnItemTouchListener adapter;
    private boolean isLongPressEnable = true;
    private boolean isSwipeEnable = true;

    public SimpleItemTouchHelperCallback(OnItemTouchListener adapter) {

        this.adapter = adapter;
    }

    public SimpleItemTouchHelperCallback(OnItemTouchListener adapter, boolean isLongPressEnable,
                                         boolean isSwipeEnable) {
        this.adapter = adapter;
        this.isLongPressEnable = isLongPressEnable;
        this.isSwipeEnable = isSwipeEnable;

    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {

            int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
            int swipeFlag = 0;
            return makeMovementFlags(dragFlag, swipeFlag);

        } else {

            int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlag, swipeFlag);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        if (viewHolder.getItemViewType() != target.getItemViewType())
            return false;
        adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        adapter.onSwipeDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            float alpha = ALPHA - Math.abs(dX) / viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);

        } else {

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG
                && viewHolder instanceof ItemTouchViewHolder) {

            ((ItemTouchViewHolder) viewHolder).onItemSelected();
        }

        super.onSelectedChanged(viewHolder, actionState);

    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        viewHolder.itemView.setAlpha(ALPHA);
        if (viewHolder instanceof ItemTouchViewHolder) {

            ((ItemTouchViewHolder) viewHolder).onItemClear();
        }
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isLongPressEnable;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeEnable;
    }

}
