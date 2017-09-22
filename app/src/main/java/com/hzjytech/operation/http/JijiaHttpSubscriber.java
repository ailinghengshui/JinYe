package com.hzjytech.operation.http;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.google.gson.JsonSyntaxException;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.module.common.LoginActivity;

import java.io.EOFException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

import rx.Subscriber;

/**
 * Created by werther on 16/7/26.
 * 婚礼纪Http模块专用的Rx Subscriber
 * 对加载条,错误信息和返回空数据作了基本的处理
 */
public class JijiaHttpSubscriber<T> extends Subscriber<T> {
    public static final int REFRESH=1;
    public static final int LOADMORE=2;
    public static final String TAG = JijiaHttpSubscriber.class.getSimpleName();
    private Context mContext;
    private Dialog progressDialog;
    private View progressBar;
    private SubscriberOnNextListener onNextListener;
    private SubscriberOnErrorListener onErrorListener;
    private SubscriberOnCompletedListener onCompletedListener;
   // private JijiaEmptyView emptyView;
    private ListView listView;
    private View contentView;
    private boolean dataNullable = false;
    private String errorMsg;
    private PtrClassicFrameLayout ptcf;
    private ArrayList<Integer> skipInt;
    //private PullToRefreshBase refreshView;

    private JijiaHttpSubscriber() {

    }
    @Override
    public void onStart() {
        super.onStart();
       if (progressBar != null) {

            progressBar.setVisibility(View.VISIBLE);
        }
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    @Override
    public void onCompleted() {
       if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        if(ptcf!=null){
            if(ptcf.isRefreshing()){
                ptcf.refreshComplete();
            }else if(ptcf.isLoadingMore()){
                ptcf.loadMoreComplete(true);
            }
        }
        if (onCompletedListener != null) {
            onCompletedListener.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        //showEmptyView();

        // 错误信息处理和显示
        String msg = null;
        if (e instanceof SocketTimeoutException) {
            msg = "网络中断，请检查您的网络状态";
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            msg = "网络中断，请检查您的网络状态";
        } else if (e instanceof JsonSyntaxException) {
            if (TextUtils.isEmpty(errorMsg)) {
                //msg = "数据解析错误";
                msg = "";
            }
        } else if(e instanceof EOFException){
            //IO读取异常，先忽略
            msg="";
        }else{
            if (TextUtils.isEmpty(errorMsg)) {
                msg = e.getMessage();
                //msg="";
        }}

        if (TextUtils.isEmpty(msg) && !TextUtils.isEmpty(errorMsg)) {
            //msg = errorMsg;
            msg="";
        }
        if(msg.equals("token过期，请重新登录!")){
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            ((BaseActivity)mContext).finish();
        }
        //MyToastUtil.show(mContext.getApplicationContext(),msg);
        //ToastUtil.showShort(mContext,msg);
        BaseActivity activity = (BaseActivity) mContext;
        activity.showTip(msg);
        e.printStackTrace();
        if (onErrorListener != null) {
            onErrorListener.onError(e);
        }

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        if(ptcf!=null){
            if(ptcf.isRefreshing()){
                ptcf.refreshComplete();
            }else if(ptcf.isLoadingMore())
            {ptcf.loadMoreComplete(true);}

        }
    }

    @Override
    public void onNext(T t) {
        if (isDataEmpty(t)) {
            Log.d("pagination tool", "on empty data");
            /**
             * 根据返回控制判断是否无更多数据
             */

            //showEmptyView();

        } else if (onNextListener != null) {
            Log.d("pagination tool", "on data");
            if (contentView != null) {
                contentView.setVisibility(View.VISIBLE);
            }
            /*if (emptyView != null) {
                emptyView.hideEmptyView();
            }
*/

        }
        onNextListener.onNext(t);
    }

    public void setOnErrorListener(SubscriberOnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    /**
     * 判断返回数据是否是空的
     *
     * @param data
     * @return
     */
    private boolean isDataEmpty(T data) {
        if (data == null) {
            return true;
        }
        if (data instanceof HttpResult) {
            HttpResult hljHttpData = (HttpResult) data;
            if (hljHttpData.getResults() == null) {
                return true;
            }
            if (hljHttpData.getResults() instanceof Collection) {
                Collection collection = (Collection) hljHttpData.getResults();
                if (collection.isEmpty()) {
                    return true;
                }
            }
        } else if (data instanceof JijiaHttpResultZip) {
            JijiaHttpResultZip hljHttpResultZip = (JijiaHttpResultZip) data;

            return hljHttpResultZip.isDataEmpty();
        } else if (data instanceof Collection) {
            Collection collection = (Collection) data;
            if (collection.isEmpty()) {
                return true;
            }
        }

        return false;
    }

/*    *//**
     * 如果有设置list view或者content view则显示空页面
     *//*
    private void showEmptyView() {
        if (emptyView == null) {
            return;
        }
        if (listView != null) {
            emptyView.showEmptyView();
            listView.setEmptyView(emptyView);
        }
        if (contentView != null) {
            contentView.setVisibility(View.GONE);
            emptyView.showEmptyView();
        }
    }*/

    public static <T> Builder<T> buildSubscriber(Context context) {
        return new Builder<>(context);
    }

    public static class Builder<T> {
        private Context mContext;
        private Dialog progressDialog;
        private SubscriberOnNextListener onNextListener;
        private SubscriberOnErrorListener onErrorListener;
        private SubscriberOnCompletedListener onCompletedListener;
       // private HljEmptyView emptyView;
        private ListView listView;
       // private PullToRefreshBase refreshView;
        private View contentView;
        private boolean dataNullable;
        private String errorMsg;
        private PtrClassicFrameLayout ptcf;
        private ArrayList<Integer> skipInt;
        private View progressBar;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        /**
         * 设置是否可以允许空数据,即空数据的时候不进入空数据emptyview处理
         *
         * @param dataNullable
         * @return
         */
        public Builder setDataNullable(boolean dataNullable) {
            this.dataNullable = dataNullable;
            return this;
        }
        public Builder setProgress(View progressBar){
            this.progressBar=progressBar;
            return this;
        }
        public Builder setProgressDialog(Dialog dialog) {
            this.progressDialog = dialog;
            return this;
        }

      /*  public Builder setEmptyView(HljEmptyView e) {
            this.emptyView = e;
            return this;
        }*/

        public Builder setListView(ListView l) {
            this.listView = l;
            return this;
        }

        public Builder setContentView(View c) {
            this.contentView = c;
            return this;
        }

        public Builder setOnNextListener(SubscriberOnNextListener n) {
            this.onNextListener = n;
            return this;
        }

        public Builder setOnErrorListener(SubscriberOnErrorListener e) {
            this.onErrorListener = e;
            return this;
        }

        public Builder setOnCompletedListener(SubscriberOnCompletedListener c) {
            this.onCompletedListener = c;
            return this;
        }

       /* public Builder setPullToRefreshBase(PullToRefreshBase refreshView) {
            this.refreshView = refreshView;
            return this;
        }*/
        public Builder setPtcf(PtrClassicFrameLayout ptcf){
            this.ptcf=ptcf;
            return this;
        }
        public JijiaHttpSubscriber build() {
            JijiaHttpSubscriber subscriber = new JijiaHttpSubscriber();
            subscriber.mContext = this.mContext;
            subscriber.progressBar=this.progressBar;
            subscriber.progressDialog = this.progressDialog;
            subscriber.skipInt=this.skipInt;
            subscriber.contentView = this.contentView;
            //subscriber.emptyView = this.emptyView;
            subscriber.onErrorListener = this.onErrorListener;
            subscriber.onNextListener = this.onNextListener;
            subscriber.onCompletedListener = this.onCompletedListener;
            subscriber.listView = this.listView;
            subscriber.dataNullable = this.dataNullable;
            subscriber.errorMsg = this.errorMsg;
            //subscriber.refreshView = this.refreshView;
            subscriber.ptcf=this.ptcf;
            return subscriber;
        }
    }


}
