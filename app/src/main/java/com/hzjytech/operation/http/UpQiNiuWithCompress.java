package com.hzjytech.operation.http;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.qiniutoken.Auth;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by hehongcan on 2017/7/5.
 */
public class UpQiNiuWithCompress {
    private Context context;
    private List<String> urls;
    private ArrayList upimg_key_list=new ArrayList<>();

    public UpQiNiuWithCompress(Context context) {
        this.context=context;
    }
    public void upLoadPic( ArrayList<String>urls){
        this.urls=urls;
        compressPic(urls);
    }
    public void  compressPic(ArrayList<String>mUrls) {
        final int[] index = {1};
        // mProgressDlg.show();
        Flowable.just(mUrls)
                .flatMap(new Function<ArrayList<String>, Publisher<File>>() {
                    @Override
                    public Publisher<File> apply(ArrayList<String> strings) throws Exception {
                        List<File> files = new ArrayList<>();
                        for (String url : strings) {
                            File file = new File(url);
                            files.add(file);
                        }
                        return Flowable.fromIterable(files);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<File, File>() {
                    @Override
                    public File apply(@NonNull File file) throws Exception {
                        return Luban.with(context)
                                .load(file)
                                .get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(@NonNull File file) throws Exception {
                        ((BaseActivity)context).showTip("正在上传第" + (index[0]++) + "张图片");
                        upFile(file.getAbsolutePath());

                    }
                });
    }

    private void upFile(String path) {
        // String path = file.getPath();
        String token = Auth.create()
                .uploadToken(NetConstants.QINIU_BUCKET);
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(path, null, token, new UpCompletionHandler() {
            @Override
            public void complete(
                    String key, ResponseInfo info, JSONObject res) {
                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
                Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                try {
                    // 七牛返回的文件名
                    String upimg = res.getString("key");
                    String s = Auth.create()
                            .privateDownloadUrl("http://coffees.qiniu.jijiakafei.com/" + upimg);
                    upimg_key_list.add(s);//将七牛返回图片的文件名添加到list集合中
                    //list集合中图片上传完成后，发送handler消息回主线程进行其他操作
                    if (upimg_key_list.size() == urls.size()) {
                        RxBus.getDefault().send(upimg_key_list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }

}
