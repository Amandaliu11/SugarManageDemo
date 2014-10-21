package com.example.sugarmanagedemo.chart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sugarmanagedemo.R;

public class RealTimeSugarChart extends AbstractChart{
    private List<RealTimeXYPoint> mList;
    private List<Long> xValues;
    private List<Date> xDateValues;
    private List<Double> yValues;
    
    private Long minXValue;
    private Long maxXValue;
    private double minYValue = 0.0D;
    private double maxYValue;
    
    private GraphicalView mView;
    private XYMultipleSeriesRenderer mRenderer;
    private XYMultipleSeriesDataset mDataset;
    
    public RealTimeSugarChart(){
        mList = new ArrayList<RealTimeXYPoint>();
        xValues = new ArrayList<Long>();
        xDateValues = new ArrayList<Date>();
        yValues = new ArrayList<Double>();
        
        initXYLimit();
    }
    
    public RealTimeSugarChart(List<RealTimeXYPoint> historyList){
        mList = historyList;
        xValues = new ArrayList<Long>();
        xDateValues = new ArrayList<Date>();
        yValues = new ArrayList<Double>();
        for(int i = 0; i<mList.size();++i){
            xDateValues.add(mList.get(i).getXValue());
            xValues.add(RealTimeXYPoint.date2Long(mList.get(i).getXValue()));
            yValues.add(mList.get(i).getYValue());
        }
        
        
        
        initXYLimit();
    }
    
    
    public XYMultipleSeriesRenderer getRenderer(){
        return this.mRenderer;
    }
    
    
    public XYMultipleSeriesDataset getDataset(){
        return this.mDataset;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDesc() {
        return null;
    }
    
    private void initXYLimit(){
        if(null == xValues || xValues.size() == 0){
            minXValue = 0L;
            maxXValue = 0L;
        }else{
            minXValue = xValues.get(0);
            maxXValue = xValues.get(0);
            
            for(Long i:xValues){
                if(minXValue > i){
                    minXValue = i;
                }
                
                if(maxXValue < i){
                    maxXValue = i;
                }
            }
            
        }
        
        
        if(null == yValues || yValues.size() == 0){
            minYValue = 0.0D;
            maxYValue = 0.0D;
        }else{
            maxYValue = yValues.get(0);
            
            for(double i:yValues){                
                if(maxYValue < i){
                    maxYValue = i;
                }
            }
            
            maxYValue = maxYValue < 12?12:maxYValue;
        }
        
    }
    
    
    private void updateXYLimit(Long x, double y){
        if(minXValue > x){
            minXValue = x;
        }
        
        if(maxXValue < x){
            maxXValue = x;
        }
        
        if(maxYValue < y){
            maxYValue = y;
        }
        
        maxXValue += 1;
    }
    
    
    private void updateXYLimit(RealTimeXYPoint point){
        updateXYLimit(RealTimeXYPoint.date2Long(point.getXValue()),point.getYValue());
    }
    
    public void update(Context context,RealTimeXYPoint point){
        if(null == mView){
            mView = execute(context);
        }
        
        mList.add(point);   
        xDateValues.add(point.getXValue());
        xValues.add(RealTimeXYPoint.date2Long(point.getXValue()));
        yValues.add(point.getYValue());
        
        updateXYLimit(point);
        
        TimeSeries series = (TimeSeries)mDataset.getSeriesAt(0);
        series.add(point.getXValue(), point.getYValue());
        
        mRenderer.setXAxisMax(maxXValue*1000);
        mRenderer.setYAxisMin(minYValue);
        mRenderer.setYAxisMax(maxYValue);
        mRenderer.setPanLimits(new double[]{minXValue*1000,maxXValue*1000,minYValue,maxYValue});
        mRenderer.setZoomLimits(new double[]{minXValue*1000,maxXValue*1000,minYValue,maxYValue});
        mView.repaint();
    }
    
   

    @Override
    public GraphicalView execute(final Context context) {
        if(null == mList || mList.size()==0){
            return null;
        }       

        String[] titles = new String[] {""};
        int[] colors = new int[] {Color.GREEN};
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE};
        
        mRenderer = buildRenderer(colors, styles);
        int length = mRenderer.getSeriesRendererCount();
        
        Log.d("abc","length: "+length);
        
        for (int i = 0; i < length; i++) {
          ((XYSeriesRenderer) mRenderer.getSeriesRendererAt(i)).setFillPoints(true);
          ((XYSeriesRenderer) mRenderer.getSeriesRendererAt(i)).setLineWidth(10F);
          ((XYSeriesRenderer) mRenderer.getSeriesRendererAt(i)).setPointStrokeWidth(15f);
          ((XYSeriesRenderer) mRenderer.getSeriesRendererAt(i)).setAnnotationsTextSize(24);
        }  
        
        mRenderer.setXAxisMax(maxXValue*1000);
        mRenderer.setYAxisMin(minYValue);
        mRenderer.setYAxisMax(maxYValue);
        
        mRenderer.setShowGridX(true);
        mRenderer.setShowGridY(false);
        mRenderer.setXLabelsAlign(Align.CENTER);
        mRenderer.setYLabelsAlign(Align.RIGHT);
        mRenderer.setZoomButtonsVisible(false);        
        mRenderer.setPanEnabled(true);
        mRenderer.setPanLimits(new double[]{minXValue*1000,maxXValue*1000,minYValue,maxYValue});        
        mRenderer.setZoomEnabled(true);       
        mRenderer.setZoomLimits(new double[]{minXValue*1000,maxXValue*1000,minYValue,maxYValue});
        mRenderer.setExternalZoomEnabled(true);
        mRenderer.setAxisTitleTextSize(24);// 璁剧疆鍧愭爣杞存爣棰樻枃鏈ぇ灏�
        mRenderer.setAxesColor(Color.BLACK);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0, Color.BLACK);        
        mRenderer.setLabelsTextSize(25); // 璁剧疆杞存爣绛炬枃鏈ぇ灏�
        mRenderer.setXLabelsAngle(30);
        mRenderer.setTextTypeface("sans_serif", Typeface.BOLD);
        mRenderer.setShowLegend(false);
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(0, 255, 255, 255));        
        mRenderer.setMarginsColor(Color.argb(0, 255, 255, 255));
        mRenderer.setClickEnabled(true);     
                
        
        mRenderer.setMargins(new int[]{10,10,10,10});
        
        
        mDataset = buildDateDataset(titles, getBuildXDataset(xDateValues), getBuildYDataset(yValues));
        
        mView = ChartFactory.getTimeChartView(context, mDataset, mRenderer, "HH:mm");        
        registerDoubleClickListener(mView,new OnDoubleClickListener(){
            @Override
            public void OnSingleClick(View v) {
              SeriesSelection seriesSelection = mView.getCurrentSeriesAndPoint();  
              if(null == seriesSelection){
                  return;
              }else{
//                  Toast.makeText(context, 
//                          "seriesSelection.getXValue();" + seriesSelection.getXValue()+"\n"
//                          +"seriesSelection.getValue();" + seriesSelection.getValue()+"\n"
//                          +"seriesSelection.getPointIndex();" + seriesSelection.getPointIndex()+"\n"
//                          +"seriesSelection.getSeriesIndex();" + seriesSelection.getSeriesIndex(), Toast.LENGTH_SHORT).show();
                  
                  Toast.makeText(context, context.getString(R.string.current_suger)+": "+seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
              }
            }

            @Override
            public void OnDoubleClick(View v) {
                mView.zoomReset();
                mRenderer.setPanEnabled(true);
                mRenderer.setXAxisMax(maxXValue*1000);
                mRenderer.setYAxisMin(minYValue);
                mRenderer.setYAxisMax(maxYValue);
            }
            
        });
        
        
        
        
        return mView;
    }
    
    private List<Date[]> getBuildXDataset(List<Date> date){
        List<Date[]> listValue = new ArrayList<Date[]>();
        Date[] tmp = new Date[date.size()];
        for(int i = 0; i<date.size();++i){
            tmp[i] = date.get(i);
        }
        listValue.add(tmp);
        return listValue;
    }
    

    private List<double[]> getBuildYDataset(List<Double> data){
        List<double[]> listValue = new ArrayList<double[]>();
        double[] tmp = new double[data.size()];
        for(int i = 0; i<data.size();++i){
            tmp[i] = (double) data.get(i);
        }
        listValue.add(tmp);
        return listValue;
    }
    
    
    
    
    private boolean waitDouble = true;
    private void registerDoubleClickListener(View view, final OnDoubleClickListener listener){
        if(listener==null){
            return;
        }
        
        view.setOnClickListener(new View.OnClickListener() {
            private static final int DOUBLE_CLICK_TIME = 350;   //鍙屽嚮闂撮殧鏃堕棿350姣            
            
            private Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    listener.OnSingleClick((View)msg.obj);
                }
            };
            
            //绛夊緟鍙屽嚮
            public void onClick(final View v) {
                if(waitDouble){
                    waitDouble = false; //涓庢墽琛屽弻鍑讳簨浠�
                    new Thread(){
                        public void run() {
                            try {
                                Thread.sleep(DOUBLE_CLICK_TIME);
                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }//绛夊緟鍙屽嚮鏃堕棿锛屽惁鍒欐墽琛屽崟鍑讳簨浠�
                             
                             if(!waitDouble){
                                 //濡傛灉杩囦簡绛夊緟浜嬩欢杩樻槸棰勬墽琛屽弻鍑荤姸鎬侊紝鍒欒涓哄崟鍑�
                                 waitDouble = true;
                                 Message msg = handler.obtainMessage();
                                 msg.obj = v;
                                 handler.sendMessage(msg);
                             }
                         }
                      }.start();
                 }else{
                     waitDouble = true;
                     listener.OnDoubleClick(v); //鎵ц鍙屽嚮
             }
          }
        });
    }
    
    private interface OnDoubleClickListener {
        public void OnSingleClick(View v);
        public void OnDoubleClick(View v);
    }
    
   
}
