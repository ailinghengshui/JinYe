package com.hzjytech.operation.scan.camera;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by Hades on 2016/3/3.
 */
public final class CameraManager {
    private static final String TAG = CameraManager.class.getSimpleName();

    private static final int MIN_FRAME_WIDTH = 480;
    private static final int MIN_FRAME_HEIGHR = 480;
    private static final int MAX_FRAME_WIDTH = 1024;
    private static final int MAX_FRAME_HEIGHT = 1024;

    private static CameraManager cameraManager;
    private static Camera camera;

    private Rect framingRect;
    private Rect framingRectInPreview;


    static final int SDK_INT;

    static {
        int sdkInt;
        sdkInt = Build.VERSION.SDK_INT;
        SDK_INT = sdkInt;
    }

    private final Context context;
    private static CameraConfigurationManager configManager;
    private final boolean useOneShotPreviewCallback;


    private final PreviewCallback previewCallback;
    private final AutoFocusCallback autoFocusCallback;
    private boolean initialized;
    private boolean previewing;

    public static void init(Context context) {
        if (cameraManager == null) {
            cameraManager = new CameraManager(context);
        }
    }

    public static CameraManager get() {
        return cameraManager;
    }

    public static Camera getCamera() {
        return camera;
    }

    private CameraManager(Context context) {
        this.context = context;
        this.configManager = new CameraConfigurationManager(context);

        useOneShotPreviewCallback = Build.VERSION.SDK_INT > 3;

        previewCallback = new PreviewCallback(configManager, useOneShotPreviewCallback);
        autoFocusCallback = new AutoFocusCallback();
    }

    public void openDrive(SurfaceHolder holder) throws IOException {
        if (camera == null) {
            camera = Camera.open();
            if (camera == null) {
                throw new IOException();
            }
            camera.setPreviewDisplay(holder);

            if (!initialized) {
                initialized = true;
                configManager.initFromCameraParameters(camera);
            }

            configManager.setDesiredCameraParameters(camera);

            FlashlightManager.enableFlashlight();
        }
    }

    public void closeDriver() {
        if (camera != null) {
            FlashlightManager.disableFlashlight();
            camera.release();
            camera = null;
        }
    }

    public void startPreview() {
        if (camera != null && !previewing) {
            camera.startPreview();
            previewing = true;
        }
    }

    public void stopPreview() {
        if (camera != null && previewing) {
            if (!useOneShotPreviewCallback) {
                camera.setPreviewCallback(null);
            }
            camera.stopPreview();
            previewCallback.setHandler(null, 0);
            autoFocusCallback.setHandler(null, 0);
            previewing = false;
        }
    }

    public void requestPreviewFrame(Handler handler, int message) {
        if (camera != null && previewing) {
            previewCallback.setHandler(handler, message);
            if (useOneShotPreviewCallback) {
                camera.setOneShotPreviewCallback(previewCallback);
            } else {
                camera.setPreviewCallback(previewCallback);
            }
        }
    }

    public void requestAutoFocus(Handler handler, int message) {
        if (camera != null && previewing) {
            autoFocusCallback.setHandler(handler, message);
            camera.autoFocus(autoFocusCallback);
        }
    }

    public Rect getFramingRect() {
        Point screenResolution = configManager.getScreenResolution();
        if (framingRect == null) {
            if (camera == null) {
                return null;
            }
           /* int width = screenResolution.x * 7 / 8;
            if (width < MIN_FRAME_WIDTH) {
                width = MIN_FRAME_WIDTH;
            } else if (width > MAX_FRAME_WIDTH) {
                width = MAX_FRAME_WIDTH;
            }

            int height = width;
            if (height < MIN_FRAME_HEIGHR) {
                height = MIN_FRAME_HEIGHR;
            } else if (height > MAX_FRAME_HEIGHT) {
                height = MAX_FRAME_HEIGHT;
            }*/
            DisplayMetrics
                    metrics = context.getResources().getDisplayMetrics();

            int width
                    = (int)
                    (metrics.widthPixels * 0.6);

            int height
                    = (int)
                    (width * 0.9);
            int leftOffset = (screenResolution.x - width) / 2;
            int topOffset = (screenResolution.y - height)/3;
            framingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
            Log.d(TAG, "Calculated framing rect: " + framingRect);
        }
        return framingRect;
    }

    public Rect getFramingRectInPreview() {
        if (framingRectInPreview == null) {
            Rect rect = new Rect(getFramingRect());
            Point cameraResolution = configManager.getCameraResolution();
            Point screenResolution = configManager.getScreenResolution();

            rect.left = rect.left * cameraResolution.y / screenResolution.x;
            rect.right = rect.right * cameraResolution.y / screenResolution.x;
            rect.top = rect.top * cameraResolution.x / screenResolution.y;
            rect.bottom = rect.bottom * cameraResolution.x / screenResolution.y;
            framingRectInPreview = rect;
        }
        return framingRectInPreview;
    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height) {
        Rect rect = getFramingRectInPreview();
        int previewFormat = configManager.getPreviewFormat();
        String previewFormatString = configManager.getPreviewFormatString();
        switch (previewFormat) {
            case PixelFormat.YCbCr_420_SP:
            case PixelFormat.YCbCr_422_SP:
                return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top,
                        rect.width(), rect.height());
            default:
                if ("yuv420sp".equals(previewFormatString)) {
                    return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top,
                            rect.width(), rect.height());
                }
        }

        throw new IllegalArgumentException("Unsupported picture format: "+previewFormat+'/'+previewFormatString);
    }

    public Context getContext(){
        return context;
    }
}
