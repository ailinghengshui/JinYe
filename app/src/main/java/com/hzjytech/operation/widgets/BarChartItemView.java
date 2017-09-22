package com.hzjytech.operation.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hehongcan on 2017/5/17.
 */
public class BarChartItemView extends View {
    
        private static final int STYLE_HORIZONTAL = 0;
        private static final int STYLE_ROUND = 1;
        private static final int STYLE_SECTOR=2;
        /**进度背景画笔**/
//	private Paint mBgpaint;
        /**进度画笔**/
//	private Paint mPrgpaint;
        /**进度文字画笔**/
//	private Paint mTextpaint;
        /**
         * 圆形进度条边框宽度
         **/
        private int strokeWidth=20;
        /**
         * 进度条中心X坐标
         **/
        private int centerX;
        /**
         * 进度条中心Y坐标
         **/
        private int centerY;
        /**
         * 进度提示文字大小
         **/
        private int percenttextsize = 18;
        /**
         * 进度提示文字颜色
         **/
        private int percenttextcolor = 0xff009ACD;
        /**
         * 进度条背景颜色
         **/
        private int progressBarBgColor = 0x00ACEE;
        /**
         * 进度颜色
         **/
        private int progressColor = 0xff00C5CD;
        /**
         * 扇形扫描进度的颜色
         */
        private int sectorColor=0xaaffffff;
        /**
         * 扇形扫描背景
         */
        private int unSweepColor = 0xaa5e5e5e;
        /**
         * 进度条样式（水平/圆形）
         **/
        private int orientation = STYLE_HORIZONTAL;
        /**
         * 圆形进度条半径
         **/
        private int radius = 30;
        /**
         * 进度最大值
         **/
        private int max = 100;
        /**
         * 进度值
         **/
        private int progress = 0;
        /**
         * 水平进度条是否是空心
         **/
        private boolean isHorizonStroke;
        /**
         * 水平进度圆角值
         **/
        private int rectRound=5;
        /**进度文字是否显示百分号**/
        private boolean showPercentSign;
        private Paint mPaint;

        public BarChartItemView(Context context) {
        this(context, null);
    }

        public BarChartItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

        public BarChartItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }



        @Override
        protected void onDraw(Canvas canvas) {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        radius = centerX - strokeWidth / 2;
        drawHoriRectProgressBar(canvas,mPaint);
    }

    /**
     * 绘制水平矩形进度条
     *
     * @param canvas
     */
    private void drawHoriRectProgressBar(Canvas canvas, Paint piant) {
        // 初始化画笔属性
        piant.setColor(progressBarBgColor);
        piant.setStyle(Paint.Style.FILL);
        // 画水平进度
        canvas.drawRoundRect(new RectF(centerX - getWidth() / 2, centerY - getHeight() / 2,
                    ((progress * 100 / max) * getWidth()) / 100, centerY + getHeight() / 2),rectRound, rectRound, piant);
        float[] radii={12f,12f,0f,0f,0f,0f,0f,0f};
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, 50, 50), radii, Path.Direction.CW);
        canvas.drawPath(path,piant);
        // 画进度文字
        piant.setStyle(Paint.Style.FILL);
        piant.setColor(percenttextcolor);
        piant.setTextSize(percenttextsize);
        String percent = (int) (progress * 100 / max) + "%";
        Rect rect = new Rect();
        piant.getTextBounds(percent, 0, percent.length(), rect);
        float textWidth = rect.width();
        float textHeight = rect.height();
        if (textWidth >= getWidth()) {
            textWidth = getWidth();
        }
        Paint.FontMetrics metrics = piant.getFontMetrics();
        float baseline = (getMeasuredHeight()-metrics.bottom+metrics.top)/2-metrics.top;
        canvas.drawText(percent, centerX - textWidth / 2, baseline, piant);

    }
   public void setMax(float max){
       //this.max=max;
   }
    public void setData(){}


}

