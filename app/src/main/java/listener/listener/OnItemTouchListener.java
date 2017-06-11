package listener.listener;


/**
 * Created by zpf on 2016/12/2.
 */

public interface OnItemTouchListener {

    boolean onItemMove(int fromPosition, int toPosition);

    void onSwipeDismiss(int position);
}
