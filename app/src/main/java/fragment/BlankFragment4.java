package fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zpf.animmenu.ActivitySpark;
import com.example.zpf.animmenu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment4 extends Fragment {

    private View root;
    private Context context;

    public BlankFragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        root = inflater.inflate(R.layout.fragment_blank_fragment4, container, false);

        initView();

        return root;
    }

    private void initView(){

        initBtnToSpark();
    }

    private void initBtnToSpark(){

        Button btn = (Button) root.findViewById(R.id.btn_to_spark);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, ActivitySpark.class));
            }
        });
    }

}
