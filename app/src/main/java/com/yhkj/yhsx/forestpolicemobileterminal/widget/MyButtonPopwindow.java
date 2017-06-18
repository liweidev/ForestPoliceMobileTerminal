package com.yhkj.yhsx.forestpolicemobileterminal.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

/**
 * 
 */
public class MyButtonPopwindow extends PopupWindow implements View.OnClickListener{
    private Button album;
    private Button take_photo;
    private LayoutInflater layoutInflater;
    private int layoutId;
    private Context context;
    private View view;
    private MyPopWindowListener popWindowChooseOnlyListener;

    public MyButtonPopwindow(Context context,int layoutId,MyPopWindowListener popWindowChooseOnlyListener){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        this.popWindowChooseOnlyListener = popWindowChooseOnlyListener;
        init();
    }

    private void init() {
        view = layoutInflater.inflate(layoutId,null);//加载布局文件
//        album = (Button) view.findViewById(R.id.left);
//        take_photo = (Button) view.findViewById(R.id.right);
        setContentView(view);

        DisplayMetrics dm=new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        setWidth(dm.widthPixels * 1 / 4);

        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        album.setOnClickListener(this);
//        take_photo.setOnClickListener(this);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setOutsideTouchable(true);
        
    }

    @Override
    public void onClick(View v) {//注释掉，是因为想在该对象外面注册按钮监听器
//        switch (v.getId()){
//            case R.id.left:
//                popWindowChooseOnlyListener.firstItem();
//                dismiss();
//                break;
//            case R.id.right:
//                popWindowChooseOnlyListener.secondItem();
//                dismiss();
//                break;
//
//        }
    }
    
	public View getPopWindowView() {
		// TODO Auto-generated method stub
		return view;
	}

}
