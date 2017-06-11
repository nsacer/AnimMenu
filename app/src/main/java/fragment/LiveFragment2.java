package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zpf.animmenu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment2 extends Fragment {

    private View root;

    public LiveFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_live_fragment2, container, false);

        initView();

        return root;
    }

    private void initView(){


    }

}
