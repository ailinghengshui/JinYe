package com.hzjytech.operation.module.task;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.task.DraggableImgGridAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.widgets.TitleBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by hehongcan on 2017/6/12.
 */
public class CommentActivity extends BaseActivity implements DraggableImgGridAdapter
        .OnItemAddListener, DraggableImgGridAdapter.OnItemClickListener, DraggableImgGridAdapter
        .OnItemDeleteListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.recycler_photos)
    RecyclerView mRecyclerPhotos;
    @BindView(R.id.tv_photo_tip)
    TextView mTvPhotoTip;
    @BindView(R.id.marked_category_layout)
    LinearLayout mMarkedCategoryLayout;
    @BindView(R.id.content_layout)
    RelativeLayout mContentLayout;
    @BindView(R.id.tv_img_count)
    TextView mTvImgCount;
    @BindView(R.id.add_img_layout)
    RelativeLayout mAddImgLayout;
    @BindView(R.id.tv_text_count)
    TextView mTvTextCount;
    @BindView(R.id.hide_keyboard_layout)
    LinearLayout mHideKeyboardLayout;
    @BindView(R.id.key_board_help_layout)
    LinearLayout mKeyBoardHelpLayout;
    @BindView(R.id.bottom_layout)
    LinearLayout mBottomLayout;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    private DisplayMetrics dm;
    private int imageSize;
    private InputMethodManager imm;
    private GridLayoutManager layoutManager;
    private MerchantFeedInterface merchantFeedInterface;
    private int photoSizeLimit;
    private List<LocalMedia> selectList;
    private static final String TAG = "CommentActivity";
    private DraggableImgGridAdapter mGridAdapter;
    private boolean keyBoardVisible;
    /**
     * 监听输入框文字数量变化
     */
    private TextWatcher mTextChangeWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String s1 = s.toString();
            mTvTextCount.setText(String.valueOf(s1.length()));
        }
    };
    private ArrayList<String> mImages;

    @Override
    protected int getResId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initView() {
        initTitleBar();
        selectList=new ArrayList<>();
        //photoSizeLimit = merchantFeedInterface.getPhotoSizeLimit();
        photoSizeLimit = 9;
        // merchantFeedPostBody = new PublishMerchantFeedPostBody();
        dm = getResources().getDisplayMetrics();
        imageSize = (int) ((dm.widthPixels - 62 * dm.density) / 3);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        //不需要拖动
       /* dragDropManager = new RecyclerViewDragDropManager();
        dragDropManager.setDraggingItemShadowDrawable((NinePatchDrawable) ContextCompat.getDrawable(
                this,
                R.drawable.sp_dragged_shadow));
        dragDropManager.setInitiateOnMove(false);
        dragDropManager.setInitiateOnLongPress(merchantFeedInterface.getInitiateOnLongPress());
        dragDropManager.setLongPressTimeout(500);*/
        mGridAdapter = new DraggableImgGridAdapter(this, null, imageSize, photoSizeLimit);
        //gridAdapter.setCanDrag(merchantFeedInterface.isCanDrag());
        mGridAdapter.setOnItemAddListener(this);
        mGridAdapter.setOnItemClickListener(this);
        mGridAdapter.setOnItemDeleteListener(this);
        //adapter = dragDropManager.createWrappedAdapter(gridAdapter);
        mRecyclerPhotos.setLayoutManager(layoutManager);
        mRecyclerPhotos.setAdapter(mGridAdapter);
        // recyclerView.setItemAnimator(new RefactoredDefaultItemAnimator());
        //dragDropManager.attachRecyclerView(recyclerView);
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        decorView.getWindowVisibleDisplayFrame(rect);
                        int displayHeight = rect.bottom - rect.top;
                        int height = decorView.getHeight();
                        keyBoardVisible = (double) displayHeight / height < 0.8;
                        if (keyBoardVisible) {
                            mKeyBoardHelpLayout.setVisibility(View.VISIBLE);
                        } else {
                            mKeyBoardHelpLayout.setVisibility(View.GONE);
                        }
                    }
                });
        mEtComment.addTextChangedListener(mTextChangeWatcher);
    }

    /**
     * 标题栏相关操作
     */
    private void initTitleBar() {
        mTitleBar.setLeftText(R.string.cancel);
        mTitleBar.setLeftTextColor(Color.WHITE);
        mTitleBar.setTitle(R.string.add_comment);
        mTitleBar.addAction(new TitleBar.TextAction(getResources().getString(R.string.finish)) {
            @Override
            public void performAction(View view) {
                overAndCommit();
                finish();
            }
        });
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 完成并提交评论
     */
    private void overAndCommit() {
        mImages = new ArrayList<>();
        for (LocalMedia localMedia : selectList) {
            mImages.add(localMedia.getPath());
        }
        Intent data=new Intent();
        data.putExtra("str",mEtComment.getText().toString());
        data.putStringArrayListExtra("url",mImages);
        setResult(RESULT_OK,data);
    }

    @OnClick({R.id.add_img_layout, R.id.hide_keyboard_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_img_layout:
                addImage();
                break;
            case R.id.hide_keyboard_layout:
                hideBottom();
                break;
        }
    }
    @OnTouch({R.id.recycler_photos, R.id.scroll_view})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (imm != null && keyBoardVisible && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        return false;
    }
    /**
     * 隐藏输入键盘
     */
    private void hideBottom() {
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 添加图片
     */
    private void addImage() {
        if (selectList!=null&&selectList.size() == photoSizeLimit) {
            showTip(R.string.choose_photo_limit_out);
            return;
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
    public void onItemAdd() {
          addImage();
    }

    @Override
    public void onItemClick(int adapterPosition, LocalMedia item) {
        mImages = new ArrayList<>();
        mImages.clear();
        for (LocalMedia localMedia : selectList) {
            mImages.add(localMedia.getPath());
        }
        Intent intent = new Intent(this, PicsPageViewActivity.class);
        intent.putStringArrayListExtra("images", mImages);
        intent.putExtra("position",adapterPosition);
        startActivity(intent);
    }

    @Override
    public void onItemDelete(int position) {
        selectList.remove(position);
        mGridAdapter.setList((ArrayList<LocalMedia>) selectList);
        addNewButtonAndRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.scroll_view)
    public void onClick() {
        mEtComment.requestFocus();
        if (imm != null  && getCurrentFocus() != null) {
            imm.toggleSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    0,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public interface MerchantFeedInterface {

        void onGoBack();

        int getPhotoSizeLimit();

        //boolean isCanDrag();

        // boolean getInitiateOnLongPress();

        // void publishMerchantFeedSucceed(PublishMerchantFeedResponse response);

    }

    public void setMerchantFeedInterface(MerchantFeedInterface merchantFeedInterface) {
        this.merchantFeedInterface = merchantFeedInterface;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    mGridAdapter.setList((ArrayList<LocalMedia>) selectList);
                    addNewButtonAndRefresh();
                    Log.e(TAG, "onActivityResult: " + selectList.size());
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void addNewButtonAndRefresh() {
        int size = mGridAdapter.getItemCount();
        int rows = 1;
        if (size > 6) {
            rows = 3;
        } else if (size > 3) {
            rows = 2;
        }
        mRecyclerPhotos.getLayoutParams().height = Math.round(imageSize * rows + rows * 10 * dm
                .density);
        mTvImgCount.setText(String.valueOf(selectList.size()));
        mTvImgCount.setVisibility(size > 1 ? View.VISIBLE : View.GONE);
        mGridAdapter.notifyDataSetChanged();
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
