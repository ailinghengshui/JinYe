package com.hzjytech.operation.scan.activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.hzjytech.operation.scan.R;
import com.hzjytech.operation.scan.camera.CameraManager;
import com.hzjytech.operation.scan.decoding.CaptureActivityHandler;
import com.hzjytech.operation.scan.decoding.InactivityTimer;
import com.hzjytech.operation.scan.view.ScanTitleBar;
import com.hzjytech.operation.scan.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by Hades on 2016/3/3.
 */
public class CaptureActivity extends FragmentActivity implements SurfaceHolder.Callback {

    private static final float BEEP_VOLUME = 0.10f;
    public static final String SCAN_RESULT_KEY = "SCAN_RESULT";
    private static final int REQUEST_CODE = 700;
    private static final String TAG = CaptureActivity.class.getSimpleName();
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private Camera camera;
    private Camera.Parameters parameters;
    private boolean isOpen = true;
    private CaptureActivityHandler handler;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private boolean playBeep;
    private boolean vibrate;
    private MediaPlayer mediaPlayer;
    private ImageView iv_light;
    private CameraManager cameraManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        ScanTitleBar titleBar = (ScanTitleBar) findViewById(R.id.scan_title_bar);
        iv_light = (ImageView) findViewById(R.id.activity_capture_iv_light);
        titleBar.setImmersive(false);
        titleBar.setTitle("扫一扫");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaptureActivity.this.finish();
            }
        });

        CameraManager.init(getApplication());
        cameraManager = CameraManager.get();
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        initView();
    }

    private void initView() {
        findViewById(R.id.activity_capture_iv_light).setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.activity_capture_iv_light) {
                turnLight();
            }
        }
    };


    private void turnLight() {
        camera = CameraManager.getCamera();
        parameters = camera.getParameters();
        if (isOpen) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            isOpen = false;
            iv_light.setBackgroundResource(R.drawable.icon_light_press);
        } else {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            isOpen = true;
            iv_light.setBackgroundResource(R.drawable.icon_light_on);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_CODE:
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }
    private void showErrorDialog() {
        closeCamera();
        viewfinderView.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("扫描确认出错，请重新扫描二维码！");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartCamera();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                CaptureActivity.this.finish();
            }
        });
        builder.show();
    }

    void restartCamera(){
        Log.d(TAG, "hasSurface " + hasSurface);

        viewfinderView.setVisibility(View.VISIBLE);

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        initCamera(surfaceHolder);

        // 恢复活动监控器
        inactivityTimer=new InactivityTimer(this);
    }

    void closeCamera(){
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.shutdown();

        // 关闭摄像头
        cameraManager.closeDriver();
    }
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        //Toast.makeText(CaptureActivity.this, resultString, Toast.LENGTH_SHORT).show();

        if (resultString.equals("")) {
            Toast.makeText(CaptureActivity.this, "扫描失败", Toast.LENGTH_SHORT).show();
            CaptureActivity.this.finish();
        } else {
             /*  String decodeString = null;
            try {
                decodeString = new String(Base64.decode(resultString, Base64.NO_WRAP), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
             if (!decodeString.startsWith("02")) {
                TitleButtonsDialog descCenterDialog = TitleButtonsDialog.newInstance("二维码有误", "扫一扫");
                closeCamera();
                viewfinderView.setVisibility(View.GONE);
                descCenterDialog.setOnButtonClickListener(new IButtonClick() {
                    @Override
                    public void onButtonClick() {
                        restartCamera();
                    }
                });
                descCenterDialog.show(CaptureActivity.this.getSupportFragmentManager(), "asktag");
            }else{
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(SCAN_RESULT_KEY, resultString);
                resultIntent.putExtras(bundle);
                this.setResult(RESULT_OK, resultIntent);
                CaptureActivity.this.finish();

            }*/
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(SCAN_RESULT_KEY, resultString);
            resultIntent.putExtras(bundle);
            this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();


        }

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDrive(surfaceHolder);
        } catch (IOException e) {
            return;
        } catch (RuntimeException e) {
            return;
        }

        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);
            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);

            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }

        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}
