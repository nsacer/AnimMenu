package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zpf.animmenu.R;

import customview.PainView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment3 extends Fragment {

    private View root;

    public BlankFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_blank_fragment3, container, false);

        initView();

        return root;
    }

    public void initView(){

        PainView painView = root.findViewById(R.id.paintView);

    }
}
