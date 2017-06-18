package com.yhkj.yhsx.forestpolicemobileterminal.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 *父Fragment，所有自定义Fragment都需要继承
 */
public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;

    private View layout;

    protected final int PAGESIZE = 10;

    protected static final String ENTITY = "ENTITY";
    protected static final String TYPE = "TYPE";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (layout == null) {
            layout = inflater.inflate(layoutResID(), null);
        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        ButterKnife.bind(this, layout);
        initView();
        initData();
        return layout;
    }

    protected abstract int layoutResID();

    /**
     * 初始化视图，可以为空代码
     */
    protected abstract void initView();

    /**
     * 初始化数据，可以为空代码
     */
    protected abstract void initData();


    protected void click(View view) {

    }

}
