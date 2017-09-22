package com.hzjytech.operation.module.scan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.scan.PollingAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.PollingInfo;
import com.hzjytech.operation.entity.PollingUp;

import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.NetConstants;
import com.hzjytech.operation.http.RxBus;
import com.hzjytech.operation.http.SubscriberOnErrorListener;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.UpQiNiuWithCompress;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.inter.OnAddPicClickListener;
import com.hzjytech.operation.inter.OnImageDelClickListener;
import com.hzjytech.operation.inter.OnPollingClickListener;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.qiniutoken.Auth;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import top.zibin.luban.Luban;

/**
 * Created by hehongcan on 2017/6/7.
 */
public class PollingActivity extends BaseActivity implements OnAddPicClickListener, OnImageDelClickListener {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_polling)
    RecyclerView mRvPolling;
    private PollingAdapter mPollingAdapter;
    private List<LocalMedia> selectList;
    private List<PollingInfo>strList=new ArrayList<>();
    private int position;
    private List<PollingInfo> list;
    int machineId=-1;
    private Subscription mSubscribe;

    /**
     * 提交巡检结果
     */
    private void commitAnser() {
        if(mSubscribe!=null){
            mSubscribe.unsubscribe();
        }
        ArrayList<PollingUp> finishContent = new ArrayList<>();
        ArrayList<PollingUp> unfinishContent = new ArrayList<>();
        for (PollingInfo pollingInfo : strList) {
            if(pollingInfo.isCheck()){
                finishContent.add(new PollingUp(pollingInfo.getNo(),pollingInfo.getUrlStrs()));
            }else{
                unfinishContent.add(new PollingUp(pollingInfo.getNo(),pollingInfo.getUrlStrs()));
            }
        }
        JSONArray array = new JSONArray();
        for (PollingUp finish : finishContent) {
            JSONObject object = new JSONObject();
            try {
                object.put("inspectId",finish.getInspectId());
                JSONArray urlArray = new JSONArray();
                for (String s: finish.getUrl()) {
                   urlArray.put(s);
                }
                object.put("url",urlArray);
                array.put(object);
            } catch (JSONException e) {


            } }
            JSONArray array2 = new JSONArray();
        for (PollingUp finish2 : unfinishContent) {
            JSONObject object2 = new JSONObject();
            try {
                object2.put("inspectId", finish2.getInspectId());
                JSONArray urlArray2 = new JSONArray();
                for (String s : finish2.getUrl()) {
                    urlArray2.put(s);
                }
                object2.put("url", urlArray2);
                array2.put(object2);
            } catch (JSONException e) {


            }

        }

        Observable<Boolean> observable = MachinesApi.upPollingAnser(UserUtils.getUserInfo()
                .getToken(), machineId, array.toString(), array2.toString());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {
                        showTip(R.string.polling_success);
                        mProgressDlg.hide();
                        finish();
                    }
                }).setOnErrorListener(new SubscriberOnErrorListener() {
                    @Override
                    public void onError(Throwable e) {
                        mProgressDlg.hide();
                    }
                })
                .build();
        observable.subscribe(subscriber);
    }

    private int mImageSize;

    @Override
    protected int getResId() {
        return R.layout.activity_polling;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyler();
        initListener();
        initData();
    }

    /**
     * 联网初始化title，content
     */
    private void initData() {
        Intent intent = getIntent();
         machineId = intent.getIntExtra("machineId", -1);
        Observable<List<PollingInfo>> observable = MachinesApi.getPollingContent(UserUtils
                .getUserInfo()
                .getToken());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<List<PollingInfo>>() {
                    @Override
                    public void onNext(List<PollingInfo> list) {
                        PollingActivity.this.list=list;
                        mPollingAdapter.setData(list);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);


    }

    private void initListener() {
        /**
         * 提交巡检结果，先上传照片，再提交
         */
        mPollingAdapter.setOnPollingClickListener(new OnPollingClickListener() {
            @Override
            public void onClick() {
               upLoadPic();
            }
        });
        mPollingAdapter.setOnAddPicClickListener(this);
        mPollingAdapter.setOnImageDeletClickListener(this);
    }

    /**
     * 初始化recyclerview
     */
    private void initRecyler() {
        mRvPolling.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        // TODO: 2017/6/7 此处模拟后台数据 ，后面setData改进
        mPollingAdapter = new PollingAdapter(this, null);
        mRvPolling.setAdapter(mPollingAdapter);
    }

    private void initTitle() {
      mTitleBar.setLeftImageResource(R.drawable.icon_left);
      mTitleBar.setTitle(R.string.polling);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 点击添加图片
     * @param position
     */
    @Override
    public void addClick(int position) {
        this.position=position;
        if(list.get(position).getUrls()==null){
            selectList=new ArrayList<>();
        }else {
            selectList=list.get(position).getUrls();
        }

        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()
                // 、视频.ofVideo()
                .maxSelectNum(9)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or
                // PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                //.compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban
                // .FIRST_GEAR、Luban.CUSTOM_GEAR
                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                .isCamera(true)// 是否显示拍照按钮 true or false
                .compress(false)// 是否压缩 true or false
                // .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig
                // .SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .glideOverride(200, 200)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .compressMaxKB(500)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
                .compressWH(200, 200) // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    if(list.get(position).getUrls()==null){
                        list.get(position).setUrls((ArrayList<LocalMedia>) PictureSelector.obtainMultipleResult(data));
                    }else{
                        list.get(position).getUrls().clear();
                        list.get(position).getUrls().addAll(PictureSelector.obtainMultipleResult(data));
                    }

                    mPollingAdapter.setData(list);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 删除照片
     * @param listPosition
     * @param imagPosition
     */

    @Override
    public void deletClcik(int listPosition, int imagPosition) {
        list.get(position).getUrls().remove(imagPosition);
        mPollingAdapter.setData(list);
    }
    public void upLoadPic() {
        strList.clear();
        mProgressDlg.show();
        for (int i=0;i<list.size();i++) {
            ArrayList<String> strings = new ArrayList<>();
            PollingInfo info = list.get(i);
            //用于接收上传后的照片
            strList.add(new PollingInfo(info.getTitle(),info.getContent(),info.getNo(),info.getUrls(),strings,info.isCheck()));
        }
        // mProgressDlg.show();
        //图片总量
        mImageSize = 0;
        for (PollingInfo pollingInfo : list) {
            ArrayList<LocalMedia> urls = pollingInfo.getUrls();
            if(urls!=null&&urls.size()>0){
                mImageSize +=urls.size();
            }
        }
        if(mImageSize ==0){
            commitAnser();
            return;
        }
        for (int i=0;i<list.size();i++) {
            PollingInfo pollingInfo = list.get(i);
            ArrayList<LocalMedia> urls = pollingInfo.getUrls();
            if(urls!=null&&urls.size()>0){
               compressPic(i,urls);
            }
        }

    }

    /**
     * 压缩图片
     * @param i
     * @param urls
     */
    private void compressPic(final int i, ArrayList<LocalMedia> urls) {
        ArrayList<String> imagList = new ArrayList<>();
        for (LocalMedia url : urls) {
            imagList.add(url.getPath());
        }
        UpQiNiuWithCompress upQiNiuWithCompress = new UpQiNiuWithCompress(this);
        upQiNiuWithCompress.upLoadPic(imagList);
        mSubscribe = RxBus.getDefault()
                .toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof ArrayList) {
                            ArrayList<String> urlStrs = strList.get(i)
                                    .getUrlStrs();
                            ArrayList<String> mUrls = (ArrayList<String>) event;
                            urlStrs.addAll(mUrls);
                            commitAnser();
                        }
                    }
                });
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
