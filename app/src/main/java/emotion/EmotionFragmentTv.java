package emotion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zpf.animmenu.R;


/**
 * Created by zejian
 * Time  16/1/7 上午10:26
 * Email shinezejian@163.com
 * Description:
 */
public class EmotionFragmentTv extends EmotionBaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.emotion_fragment1, null);
        TextView tv = rootView.findViewById(R.id.tv);
        tv.setText(args.getString("Interge"));
        return rootView;
    }
}
