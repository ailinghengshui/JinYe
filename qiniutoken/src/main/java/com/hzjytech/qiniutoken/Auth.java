package com.hzjytech.qiniutoken;

import android.text.TextUtils;

import com.hzjytech.qiniutoken.utils.Json;
import com.hzjytech.qiniutoken.utils.StringMap;
import com.hzjytech.qiniutoken.utils.StringUtils;
import com.hzjytech.qiniutoken.utils.UrlSafeBase64;

import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class define upload stratege
 * Created by Hades on 2016/2/24.
 */
public class Auth {

    private static final String[] policyFields=new String[]{
            "callbackUrl",
            "callbackBody",
            "callbackHost",
            "callbackBodyType",
            "callbackFetchKey",

            "returnUrl",
            "returnBody",

            "endUser",
            "saveKey",
            "insertOnly",

            "detectMime",
            "mimeLimit",
            "fsizeLimit",
            "fsizeMin",

            "persistentOps",
            "persistentNotifyUrl",
            "persistentPipeline",
    };

    private static  final String[] deprecatedPolicyFields=new String[]{
            "asyncOps"
    };
    private final String accessKey;
    private final SecretKeySpec secretKey;

    private Auth(String accessKey,SecretKeySpec secretKeySpec){
        this.accessKey=accessKey;
        this.secretKey=secretKeySpec;
    }

    public static Auth create(){
        return create("2VQYwL7KbVJwSfBnILVDSpaGbE7F4qB_7zV6lltd","50tn_nGNbv3cilGf-6nGGSgtSXk9yDlKUGoHGB9w");
    }

    public static Auth create(String accessKey,String secretKey){
        if(TextUtils.isEmpty(accessKey)||TextUtils.isEmpty(secretKey)){
            throw new IllegalArgumentException("empty key");
        }
        byte[] sk= StringUtils.utf8Bytes(secretKey);

        SecretKeySpec secretKeySpec=new SecretKeySpec(sk,"HmacSHA1");
        return new Auth(accessKey,secretKeySpec);
    }

    /**
     * scope=bucket
     *
     * @param bucket 上传空间名
     * @return 上传凭证
     */
    public String uploadToken(String bucket){
        return uploadToken(bucket,null,3600,null,true);
    }

    /**
     * scope=bucket:key
     * 上传指定key的文件，通过此方法获取上传凭证
     * 同名文件覆盖
     * @param bucket
     * @param key
     * @return 上传凭证
     */
    public String uploadToken(String bucket,String key){
        return uploadToken(bucket,key,3600,null,true);
    }

    /**
     *
     * @param bucket 空间名
     * @param key
     * @param expires  有效时长，单位s
     * @param policy 上传策略的其他参数，new StringMap().put("endUser","userid").putNotEmpty("returnBody","");
     *               scope通过bucket：可以间接设置，deadline通过expires间接设置
     * @return 上传凭证
     */
    public String uploadToken(String bucket,String key,long expires,StringMap policy){
        return uploadToken(bucket,key,expires,policy,true);
    }

    /**
     *
     * @param bucket 空间名
     * @param key  key，可为null
     * @param expires 有效时长，单位s。默认3600s
     * @param policy 上传策略其他参数。。。
     * @param strict 是否去除非限定的策略字段，默认true
     * @return 上传凭证
     */
    public String uploadToken(String bucket,String key,long expires,StringMap policy,boolean strict ){
        long deadline=System.currentTimeMillis()/1000+expires;
        return uploadTokenWithDeadline(bucket, key, deadline, policy, strict);
    }

    /**
     *
     * @param bucket
     * @param key
     * @param deadline
     * @param policy
     * @param strict
     * @return
     */
    public String uploadTokenWithDeadline(String bucket, String key, long deadline, StringMap policy, boolean strict) {
        String scope=bucket;
        if(key!=null){
            scope=bucket+":"+key;
        }
        StringMap x=new StringMap();
        copyPolicy(x, policy, strict);
        x.put("scope", scope);
        x.put("deadline", deadline);

        String s= Json.encode(x);
        return signWithData(StringUtils.utf8Bytes(s));
    }

    private String signWithData(byte[] data) {
        String s= UrlSafeBase64.encodeToString(data);
        return sign(StringUtils.utf8Bytes(s))+":"+s;
    }

    public String sign(byte[] data) {
        Mac mac=createMac();
        String encodedSign=UrlSafeBase64.encodeToString(mac.doFinal(data));
        return this.accessKey+":"+encodedSign;
    }

    public String sign(String data){
        return sign(StringUtils.utf8Bytes(data));
    }

    private Mac createMac() {
        Mac mac;
        try {
            mac=Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
        return mac;

    }

    private void copyPolicy(final StringMap policy, final StringMap originPolicy,final boolean strict) {
        if(originPolicy==null){
            return;
        }
        originPolicy.forEach(new StringMap.Consumer() {
            @Override
            public void accept(String key, Object value) {
                if(StringUtils.inStringArray(key,deprecatedPolicyFields)){
                    throw new IllegalArgumentException(key + " is deprecated!");
                }

                if(!strict||StringUtils.inStringArray(key,policyFields)){
                    policy.put(key,value);
                }
            }
        });


    }
    /**
     * 下载签名
     *
     * @param baseUrl 待签名文件url，如 http://img.domain.com/u/3.jpg 、
     *                http://img.domain.com/u/3.jpg?imageView2/1/w/120
     * @return
     */
    public String privateDownloadUrl(String baseUrl) {
        return privateDownloadUrl(baseUrl, 3600);
    }

    /**
     * 下载签名
     *
     * @param baseUrl 待签名文件url，如 http://img.domain.com/u/3.jpg 、
     *                http://img.domain.com/u/3.jpg?imageView2/1/w/120
     * @param expires 有效时长，单位秒。默认3600s
     * @return
     */
    public String privateDownloadUrl(String baseUrl, long expires) {
        long deadline = System.currentTimeMillis() / 1000 + expires;
        return privateDownloadUrlWithDeadline(baseUrl, deadline);
    }

    String privateDownloadUrlWithDeadline(String baseUrl, long deadline) {
        StringBuilder b = new StringBuilder();
        b.append(baseUrl);
        int pos = baseUrl.indexOf("?");
        if (pos > 0) {
            b.append("&e=");
        } else {
            b.append("?e=");
        }
        b.append(deadline);
        String token = sign(StringUtils.utf8Bytes(b.toString()));
        b.append("&token=");
        b.append(token);
        return b.toString();
    }

}
