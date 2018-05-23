package fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zpf.animmenu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment1 extends Fragment {

    private View root;
    private SwipeRefreshLayout swipeRefresh;

    /**
     * handler接受消息并进行处理
     * @param 0 ： 下拉刷新结束
     * */
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 0:

                    swipeRefresh.setRefreshing(false);
                    break;

            }
        }
    };

    public LiveFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_live_fragment1, container, false);

        initView();

        return root;
    }

    private void initView(){

        initSwipeRefresh();
    }

    private void initSwipeRefresh(){

        swipeRefresh = root.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                handler.sendEmptyMessageDelayed(0, 3000);
            }
        });
    }
}
