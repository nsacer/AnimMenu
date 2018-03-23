package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zpf.animmenu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.StockAnalysisIndexAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment3 extends Fragment {


    private View root;

    public LiveFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_live_fragment3, container, false);

        initView();

        return root;
    }

    private void initView(){

        initRecyclerView();
    }

    private void initRecyclerView() {

        RecyclerView rvHor = (RecyclerView) root.findViewById(R.id.rvVpHor);
        rvHor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvHor.setHasFixedSize(true);

        String[] sContents = new String[]{"资金净流入", "资金活跃度", "短线电波", "散户资金净流入", "大盘趋势", "资金成交量"};
        List<String> contents = new ArrayList<>(Arrays.asList(sContents));
        StockAnalysisIndexAdapter adapterStockIndex = new StockAnalysisIndexAdapter(contents);
        adapterStockIndex.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });

        rvHor.setAdapter(adapterStockIndex);
    }

}
