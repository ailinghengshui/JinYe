package com.hzjytech.operation.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/8/22.
 */

public class CellLinearLayout extends LinearLayout {

    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.tv_right)
    EditText mETRight;
    @BindView(R.id.view_line)
    View mViewLine;
    private String mLeftText;
    private String mRightHint;
    private boolean mHasDivider;

    public CellLinearLayout(Context context) {
        this(context, null);
    }

    public CellLinearLayout(
            Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellLinearLayout(
            Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cell_linearlayout,this, false);
        addView(view);
        ButterKnife.bind(this, view);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CellLinearLayout);
        mLeftText = ta.getString(R.styleable.CellLinearLayout_leftText);
        mRightHint = ta.getString(R.styleable.CellLinearLayout_rightHintText);
        mHasDivider = ta.getBoolean(R.styleable.CellLinearLayout_hasDivider, true);
        ta.recycle();
        initView();
    }

    private void initView() {
       mTvLeft.setText(mLeftText);
        mETRight.setHint(mRightHint);
        mViewLine.setVisibility(mHasDivider?VISIBLE:GONE);
    }

    /**
     * 获取右边的edittext内容
     * @return
     */
    private String getRightText(){
        String s = mETRight.getText()
                .toString();
        return s;
    }
    public boolean isEmpty(){
        if(getRightText()==null||getRightText().equals("")){
            return true;
        }
        return false;
    }
    public Float getRightValue(){
        if(isEmpty()){
            return null;
        }
        return Float.valueOf(getRightText());
    }
}
