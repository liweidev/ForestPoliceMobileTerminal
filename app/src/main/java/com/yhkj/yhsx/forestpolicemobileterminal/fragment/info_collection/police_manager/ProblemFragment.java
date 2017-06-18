package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.police_manager;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ImageShowActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.SignatureActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.widget.DateInputEditText;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--治安管理--问题与建议
 */

public class ProblemFragment extends BaseFragment implements View.OnClickListener {
    /**
     * 检查内容及存在的问题
     */
    @BindView(R.id.etSmir_ContentAndProblems)
    EditText etSmirContentAndProblems;
    /**
     * 整改意见
     */
    @BindView(R.id.etSmir_rectificationOpinions)
    EditText etSmirRectificationOpinions;
    /**
     * 业主或者负责人意见
     */
    @BindView(R.id.etSmir_ownerOrHead)
    EditText etSmirOwnerOrHead;

    @BindView(R.id.tvSmir_signature)
    TextView tvSmirSignature;
    /**
     * 签字
     */
    @BindView(R.id.ivSmir_signature)
    ImageView ivSmirSignature;
    /**
     * 签字时间
     */
    @BindView(R.id.etSmir_signature_time)
    DateInputEditText etSmirSignatureTime;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.smir_form2;
    }

    @Override
    protected void initView() {
        ivSmirSignature.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.ivSmir_signature://签字
                if (v.getTag().equals(getString(R.string.add))) {
                    it = new Intent(getActivity(), SignatureActivity.class);
                    getActivity().startActivityForResult(it, 100);
                } else {
                    it = new Intent(getActivity(), ImageShowActivity.class);
                    it.putExtra("filepath", v.getTag().toString());
                    getActivity().startActivityForResult(it, 4);
                }
                break;
        }
    }
}
