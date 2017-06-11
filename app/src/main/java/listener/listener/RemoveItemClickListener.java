package listener.listener;

import android.view.View;

/**
 * Created by zpf on 2017/2/22.
 */

public interface RemoveItemClickListener {

    /**
     * 整个Item点击方法
     * */
    void onItemClick(View view, int position);

    /**
     * 删除按钮点击事件
     */
    void onDeleteClick(int position);
}
