package com.example.sugarmanagedemo.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.example.sugarmanagedemo.R;

public class RoundProgressBar extends View {
    private Paint paint;
    private int roundColor;
    private int roundProgressColor;
    private int textColor;
    private float textSize;
    private float roundWidth;
    private int max;
    private int progress;
    private boolean textIsDisplayable;
    private int style;
    
    public static final int STROKE = 0;
    public static final int FILL = 1;
    
    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        paint = new Paint();

        
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);
        

        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.rgb(245, 252, 248));
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.rgb(62, 187, 102));
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.BLACK);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
        
        mTypedArray.recycle();
    }
    

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        /**
         * 鐢绘渶澶栧眰鐨勫ぇ鍦嗙幆
         */
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int centre = centerX>centerY?centerY:centerX;
        int radius = (int) (centre - roundWidth/2); //鍦嗙幆鐨勫崐寰�
        paint.setColor(roundColor); //璁剧疆鍦嗙幆鐨勯鑹�
        paint.setStyle(Paint.Style.STROKE); //璁剧疆绌哄績
        paint.setStrokeWidth(roundWidth); //璁剧疆鍦嗙幆鐨勫搴�
        paint.setAntiAlias(true);  //娑堥櫎閿娇 
        canvas.drawCircle(centerX, centerY, radius, paint); //鐢诲嚭鍦嗙幆
        
//        Log.e("log", centre + "");
        
        /**
         * 鐢昏繘搴︾櫨鍒嗘瘮
         */
        paint.setStrokeWidth(0); 
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD); //璁剧疆瀛椾綋
        int percent = (int)(((float)progress / (float)max) * 100);  //涓棿鐨勮繘搴︾櫨鍒嗘瘮锛屽厛杞崲鎴恌loat鍦ㄨ繘琛岄櫎娉曡繍绠楋紝涓嶇劧閮戒负0
        float textWidth = paint.measureText(percent + "%");   //娴嬮噺瀛椾綋瀹藉害锛屾垜浠渶瑕佹牴鎹瓧浣撶殑瀹藉害璁剧疆鍦ㄥ渾鐜腑闂�
        
        if(textIsDisplayable && percent != 0 && style == STROKE){
            canvas.drawText(percent + "%", centerX - textWidth / 2, centerY + textSize/2, paint); //鐢诲嚭杩涘害鐧惧垎姣�
        }
        
        
        /**
         * 鐢诲渾寮� 锛岀敾鍦嗙幆鐨勮繘搴�
         */
        
        //璁剧疆杩涘害鏄疄蹇冭繕鏄┖蹇�
        paint.setStrokeWidth(roundWidth); //璁剧疆鍦嗙幆鐨勫搴�
        paint.setColor(roundProgressColor);  //璁剧疆杩涘害鐨勯鑹�
        RectF oval = new RectF(centerX - radius, centerY - radius, centerX
                + radius, centerY + radius);  //鐢ㄤ簬瀹氫箟鐨勫渾寮х殑褰㈢姸鍜屽ぇ灏忕殑鐣岄檺
        
        switch (style) {
        case STROKE:{
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(oval, 90, 360 * progress / max, false, paint);  //鏍规嵁杩涘害鐢诲渾寮�
            break;
        }
        case FILL:{
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            if(progress !=0){
                canvas.drawArc(oval, 90, 360 * progress / max, true, paint);  //鏍规嵁杩涘害鐢诲渾寮�
            }
            break;
        }
        }
        
    }
    
    
    public synchronized int getMax() {
        return max;
    }

    /**
     * 璁剧疆杩涘害鐨勬渶澶у��
     * @param max
     */
    public synchronized void setMax(int max) {
        if(max < 0){
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 鑾峰彇杩涘害.闇�瑕佸悓姝�
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 璁剧疆杩涘害锛屾涓虹嚎绋嬪畨鍏ㄦ帶浠讹紝鐢变簬鑰冭檻澶氱嚎鐨勯棶棰橈紝闇�瑕佸悓姝�
     * 鍒锋柊鐣岄潰璋冪敤postInvalidate()鑳藉湪闈濽I绾跨▼鍒锋柊
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if(progress < 0){
            throw new IllegalArgumentException("progress not less than 0");
        }
        if(progress > max){
            progress = max;
        }
        if(progress <= max){
            this.progress = progress;
            postInvalidate();
        }
        
    }
    
    
    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }



}
