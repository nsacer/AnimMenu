package fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zpf.animmenu.AlphaAnalyzeActivity;
import com.example.zpf.animmenu.ChartActivity;
import com.example.zpf.animmenu.CircleTextViewAnim;
import com.example.zpf.animmenu.ConstraintEnterActivity;
import com.example.zpf.animmenu.CoordinatorActivity;
import com.example.zpf.animmenu.CowboyActivity;
import com.example.zpf.animmenu.CustomLoadingActivity;
import com.example.zpf.animmenu.DanmuKuActivity;
import com.example.zpf.animmenu.DateSelectActivity;
import com.example.zpf.animmenu.EmojiActivity;
import com.example.zpf.animmenu.F10Activity;
import com.example.zpf.animmenu.GlideActivity;
import com.example.zpf.animmenu.ImageSwitchViewPagerActivity;
import com.example.zpf.animmenu.PasswordActivity;
import com.example.zpf.animmenu.PermissionActivity;
import com.example.zpf.animmenu.PhotoSetActivity;
import com.example.zpf.animmenu.R;
import com.example.zpf.animmenu.RecyclerViewEnterActivity;
import com.example.zpf.animmenu.TabMainActivity;
import com.example.zpf.animmenu.TabTitleActivity;
import com.example.zpf.animmenu.TableLayoutActivity;
import com.example.zpf.animmenu.TurnTableActivity;
import com.example.zpf.animmenu.VideoActivity;

import customview.CircleImageView;
import top.wefor.circularanim.CircularAnim;
import tyrantgit.explosionfield.ExplosionField;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment5 extends Fragment implements View.OnClickListener {

    private View root;
    private Context context;

    /**
     * 任意View的爆炸效果
     */
    private ExplosionField field;
    private CircleImageView civBang;

    public BlankFragment5() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        root = inflater.inflate(R.layout.fragment_blank_fragment5, container, false);

        initView();

        return root;
    }

    private void initView() {

        initExplosionField();

        initBtnEvent();
    }

    /**
     * bang效果初始化
     */
    private void initExplosionField() {

        field = ExplosionField.attach2Window(getActivity());
    }

    /**
     * Button 按钮
     */
    private void initBtnEvent() {

        //CoordinatorLayout Demo
        Button btnCoordinator = (Button) root.findViewById(R.id.btn_to_coordinator);
        btnCoordinator.setOnClickListener(this);

        //九宫格密码 Demo
        Button btnPassword = (Button) root.findViewById(R.id.btn_password);
        btnPassword.setOnClickListener(this);

        //上传头像 Demo
        Button btnPhoto = (Button) root.findViewById(R.id.btn_photo);
        btnPhoto.setOnClickListener(this);

        //PickerDialog
        Button btnDateSelect = (Button) root.findViewById(R.id.btn_date_select);
        btnDateSelect.setOnClickListener(this);

        //各种Shape
        Button btnShape = (Button) root.findViewById(R.id.btn_shape);
        btnShape.setOnClickListener(this);

        //Band效果
        civBang = (CircleImageView) root.findViewById(R.id.civ_bang);
        addBangListener(civBang);

        Button btnFieldReset = (Button) root.findViewById(R.id.btn_field_reset);
        btnFieldReset.setOnClickListener(this);

        //弹幕
        Button btnBarrage = (Button) root.findViewById(R.id.btn_barrage);
        btnBarrage.setOnClickListener(this);

        //量化分析图表动画
        Button btnAnim = (Button) root.findViewById(R.id.btn_anim);
        btnAnim.setOnClickListener(this);

        Button btnAlphaAnalyze = (Button) root.findViewById(R.id.btn_alpha_analyze);
        btnAlphaAnalyze.setOnClickListener(this);

        //RecyclerView总界面
        Button btnRefresh = (Button) root.findViewById(R.id.btn_recycler_enter);
        btnRefresh.setOnClickListener(this);

        //转圈的文本（唤醒文本）
        Button btnCircleTextAnim = (Button) root.findViewById(R.id.btn_circle_text_anim);
        btnCircleTextAnim.setOnClickListener(this);

        //TableLayout
        Button btnTable = (Button) root.findViewById(R.id.btn_table_layout);
        btnTable.setOnClickListener(this);

        //获取系统权限
        Button btnPermission = (Button) root.findViewById(R.id.btn_request_permission);
        btnPermission.setOnClickListener(this);

        //自定义加载样式
        Button btnLoading = (Button) root.findViewById(R.id.btn_loading_progress);
        btnLoading.setOnClickListener(this);

        //大转盘
        Button btnTurntable = (Button) root.findViewById(R.id.btn_turntable);
        btnTurntable.setOnClickListener(this);

        //图片切换
        Button btnSwitch = (Button) root.findViewById(R.id.btn_iv_switch);
        btnSwitch.setOnClickListener(this);

        //过滤emoji表情
        Button btnEmoji = (Button) root.findViewById(R.id.btn_emoji);
        btnEmoji.setOnClickListener(this);

        //ConstraintLayout
        Button btnConstraint = (Button) root.findViewById(R.id.btn_ConstraintLayout);
        btnConstraint.setOnClickListener(this);

        //Glide
        Button btnGlide = (Button) root.findViewById(R.id.btn_Glide);
        btnGlide.setOnClickListener(this);

        //tabRadioGroup
        Button btnTabRg = (Button) root.findViewById(R.id.btn_tab_rg);
        btnTabRg.setOnClickListener(this);

        //F10
        Button btnF10 = (Button) root.findViewById(R.id.btn_tab_f10);
        btnF10.setOnClickListener(this);

        //视频
        Button btnVideo = (Button) root.findViewById(R.id.btn_video);
        btnVideo.setOnClickListener(this);

        //TabActivity
        Button btnTabAct = (Button) root.findViewById(R.id.btn_tabAct);
        btnTabAct.setOnClickListener(this);

    }

    /**
     * Button按钮的点击事件
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //跳转到Coordinator
            case R.id.btn_to_coordinator:
                openActivity(CoordinatorActivity.class, v);
                break;
            //跳转到九宫格手势密码
            case R.id.btn_password:
                openActivity(PasswordActivity.class, v);
                break;
            //拍照
            case R.id.btn_photo:
                openActivity(PhotoSetActivity.class, v);
                break;
            //Date Select
            case R.id.btn_date_select:
                openActivity(DateSelectActivity.class, v);
                break;
            //宝箱用的各种Shape
            case R.id.btn_shape:
                openActivity(CowboyActivity.class, v);
                break;
            //bang效果
            case R.id.civ_bang:
                field.explode(civBang);
                break;
            //band效果Reset
            case R.id.btn_field_reset:
                resetBangedView(civBang);
                break;
            //弹幕
            case R.id.btn_barrage:
                openActivity(DanmuKuActivity.class, v);
                break;
            //量化分析图表动画
            case R.id.btn_anim:
                openActivity(ChartActivity.class, v);
                break;
            //量化分析界面
            case R.id.btn_alpha_analyze:
                openActivity(AlphaAnalyzeActivity.class, v);
                break;
            //RecyclerView主界面
            case R.id.btn_recycler_enter:
                openActivity(RecyclerViewEnterActivity.class, v);
                break;
            //CircleTextViewActivity
            case R.id.btn_circle_text_anim:
                openActivity(CircleTextViewAnim.class, v);
                break;
            //TableLayout
            case R.id.btn_table_layout:
                openActivity(TableLayoutActivity.class, v);
                break;
            //获取系统权限
            case R.id.btn_request_permission:
                openActivity(PermissionActivity.class, v);
                break;
            //自定义加载样式
            case R.id.btn_loading_progress:
                openActivity(CustomLoadingActivity.class, v);
                break;
            //大转盘
            case R.id.btn_turntable:
                openActivity(TurnTableActivity.class, v);
                break;
            //ImageSwitcher图片切换
            case R.id.btn_iv_switch:
                openActivity(ImageSwitchViewPagerActivity.class, v);
                break;
            //Emoji表情过滤
            case R.id.btn_emoji:
                openActivity(EmojiActivity.class, v);
                break;
            //测试入口
            case R.id.btn_ConstraintLayout:
                openActivity(ConstraintEnterActivity.class, v);
                break;
            //Glide
            case R.id.btn_Glide:
                openActivity(GlideActivity.class, v);
                break;
            //RadioGroup实现的tab
            case R.id.btn_tab_rg:
                openActivity(TabTitleActivity.class, v);
                break;
            //F10
            case R.id.btn_tab_f10:
                openActivity(F10Activity.class, v);
                break;
            //视频
            case R.id.btn_video:
                openActivity(VideoActivity.class, v);
                break;
            //TabActivity
            case R.id.btn_tabAct:
                openActivity(TabMainActivity.class, v);
                break;
            default:
                break;
        }
    }

    /**
     * view 添加 listener
     */
    private void addBangListener(View view) {

        if (!(view instanceof ViewGroup)) {

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    field.explode(v);
                    v.setOnClickListener(null);
                }
            });
        }
    }

    /**
     * Reset Banged View
     */
    private void resetBangedView(View view) {

        if (!(view instanceof ViewGroup)) {

            view.setScaleX(1);
            view.setScaleY(1);
            view.setAlpha(1);

            addBangListener(view);

            field.clear();
        }
    }

    /**
     * 动画跳转页面
     */
    private void openActivity(final Class<?> clazz, View view) {

        CircularAnim.fullActivity(getActivity(), view)
                .colorOrImageRes(R.color.colorAccent)
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {

                        startActivity(new Intent(getActivity(), clazz));
                    }
                });
    }

}
