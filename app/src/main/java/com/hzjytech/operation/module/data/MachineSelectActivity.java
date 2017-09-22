package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.SelectMachineAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.Groups;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import com.hzjytech.operation.entity.Groups.SubGroupsBean.SubMachinesBean;

/**
 * 层级树选择咖啡机界面
 * Created by hehongcan on 2017/5/17.
 */
public class MachineSelectActivity extends BaseActivity {
    public static final int SELECTED_MACHIES = 101;
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.tv_home_search)
    TextView tvHomeSearch;
    @BindView(R.id.rl_edit)
    RelativeLayout rl_edit;
    @BindView(R.id.rl_text)
    RelativeLayout rl_text;
    @BindView(R.id.rv_select_machine)
    RecyclerView rv_select_machine;
    @BindView(R.id.et_search)
    EditText searchView;
    @BindView(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @BindView(R.id.btn_sure)
    Button btnSure;
    private SelectMachineAdapter adapter;
    private List<Groups> groups=new ArrayList<>();
    private List<Groups> searchGroups = new ArrayList<>();
    private  List<SubMachinesBean> machines=new ArrayList();
    private int name;
    private boolean isSelectAll=false;
    private TextView textView;

    @Override
    protected int getResId() {
        return R.layout.data_machine_select;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initData();
        initSearchView();
    }

    private void initTitle() {
        Intent intent = getIntent();
        name = intent.getIntExtra("name", -1);
        titleBar.setTitle(R.string.select_machine);
        titleBar.setTitleBold(true);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setLeftText("取消");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        TitleBar.TextAction textAction = new TitleBar.TextAction("全选") {
            @Override
            public void performAction(View view) {
                isSelectAll=!isSelectAll;
                adapter.setAllSelected();
                if(textView!=null){
                    textView.setText(isSelectAll?"取消全选":"全选");
                }
            }
        };
        if(name!=Constants.error_count){
            textView = (TextView) titleBar.addAction(textAction);
        }

    }

    /**
     * 搜索框的一些初始化
     */
    private void initSearchView() {
        rl_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_text.setVisibility(View.INVISIBLE);
                rl_edit.setVisibility(View.VISIBLE);
                searchView.setFocusable(true);
                searchView.setFocusableInTouchMode(true);
                searchView.requestFocus();
                showSoftKeyboard();
            }
        });
        searchView.addTextChangedListener(mWatcher);
    }

    /**
     * 关键字搜索
     * 搜索结果以机器为最小单位
     * @param text
     */
    private void searchText(final String text) {
        if(text.equals("")){
            adapter.setData(this,groups);
            textView.setVisibility(View.VISIBLE);
            return;
        }
        if(textView!=null){
            textView.setVisibility(View.INVISIBLE);
        }

        machines.clear();
        Observable.from(groups).flatMap(new Func1<Groups, Observable<SubMachinesBean>>() {
            @Override
            public Observable<SubMachinesBean> call(Groups groups) {
                ArrayList<SubMachinesBean> sList = new ArrayList<>();
                for (Groups.SubGroupsBean subGroupsBean : groups.getSubGroups()) {
                    sList.addAll(subGroupsBean.getSubMachines());
                }
                List<SubMachinesBean> subMachines = groups.getSubMachines();
                sList.addAll(subMachines);
                return Observable.from(sList);
            }
        }).filter(new Func1<SubMachinesBean, Boolean>() {
            @Override
            public Boolean call(SubMachinesBean subMachinesBean) {
                String id = subMachinesBean.getMachineId() + "";
                String name = subMachinesBean.getMachineName();
                if(id.toLowerCase().contains(text.toLowerCase())||name.toLowerCase().contains(text.toLowerCase())){
                    return true;
                }
              return false;
            }
        }).subscribe(new Subscriber<SubMachinesBean>() {
            @Override
            public void onCompleted() {
                Collections.sort(machines, new Comparator<SubMachinesBean>() {
                    @Override
                    public int compare(
                            SubMachinesBean o1, SubMachinesBean o2) {
                        return o1.getMachineId()-o2.getMachineId();
                    }
                });
              adapter.setData(machines);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SubMachinesBean subMachinesBean) {
               machines.add(subMachinesBean);
            }
        });
    }
    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_select_machine.setLayoutManager(manager);
        adapter = new SelectMachineAdapter(this, null);
        rv_select_machine.setAdapter(adapter);
        adapter.setOnSureClickableListener(new SelectMachineAdapter.OnSureClickableListener() {
            @Override
            public void clickable(boolean b) {
                if (b) {
                      btnSure.setEnabled(true);
                }else{
                    btnSure.setEnabled(false);
                }
            }
        });
    }

    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Observable<List<Groups>> observable = DataApi.getSelectMachies(userInfo.getToken());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<List<Groups>>() {
            @Override
            public void onNext(List<Groups> groups) {
                resovleResult(groups);
            }
        }).setProgressDialog(mProgressDlg).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    /**
     * 剔除空的咖啡机组
     * @param groups
     */
    private void resovleResult(List<Groups> groups) {
        Observable.from(groups).filter(new Func1<Groups, Boolean>() {
            @Override
            public Boolean call(Groups groups) {
                if(groups.getSubMachines().size()>0){
                    return true;
                }else if(groups.getSubGroups().size()>0){
                    for (Groups.SubGroupsBean bean : groups.getSubGroups()) {
                        if(bean.getSubMachines().size()>0){
                            return true;
                        }
                    }
                    return false;
                }else{
                    return false;
                }
            }
        }).map(new Func1<Groups, Groups>() {
            @Override
            public Groups call(Groups groups) {
                    if(groups.getSubGroups().size()>0){
                        List<Groups.SubGroupsBean> groups1 = groups.getSubGroups();
                        Iterator<Groups.SubGroupsBean> iterator = groups1.iterator();
                        while (iterator.hasNext()){
                            Groups.SubGroupsBean bean = iterator.next();
                            if(bean.getSubMachines().size()<=0){
                                iterator.remove();
                            }
                        }
                    }

                return groups;
            }
        }).subscribe(new Observer<Groups>() {
            @Override
            public void onCompleted() {
                adapter.setData(MachineSelectActivity.this, MachineSelectActivity.this.groups);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Groups groups) {
                MachineSelectActivity.this.groups.add(groups);
            }
        });
    }

    private TextWatcher mWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
            if (temp.length() > 0) {
                ivSearchClear.setVisibility(View.VISIBLE);
                ivSearchClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchView.setText("");
                        ivSearchClear.setVisibility(View.GONE);
                    }
                });
            }

            if (temp.length() == 0) {
                ivSearchClear.setVisibility(View.GONE);
            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            searchText(s.toString());
            if (temp.length() > 0) {
                ivSearchClear.setVisibility(View.VISIBLE);
                ivSearchClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchView.setText("");
                        ivSearchClear.setVisibility(View.GONE);
                    }
                });
            }

            if (temp.length() == 0) {
                ivSearchClear.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sure)
    public void onClick() {
        List<GroupInfo.SubMachinesBean> list =  adapter.getSelectedMachine();
        if(name== Constants.error_count&&list.size()!=1){
            showTip("只能选择一台咖啡机");
            return;
        }
        if(name==Constants.task&&list.size()!=1){
            showTip("只能选择一台咖啡机");
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) list);
        intent.putExtras(bundle);
        setResult(SELECTED_MACHIES, intent);
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
