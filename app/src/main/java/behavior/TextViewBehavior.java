package behavior;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zpf on 2016/12/1.
 */

public class TextViewBehavior extends CoordinatorLayout.Behavior<TextView> {

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
