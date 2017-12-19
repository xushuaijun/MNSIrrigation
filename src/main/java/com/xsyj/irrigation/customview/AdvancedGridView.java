package com.xsyj.irrigation.customview;




import android.content.Context;  
import android.util.AttributeSet;  
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;  
import android.widget.BaseAdapter;  
import android.widget.TableLayout;  
import android.widget.TableRow;

import com.xsyj.irrigation.R;

/** 
 * AdvancedGridView 
 * @author RobinTang 
 * @time 2012-10-15 
 */  
public class AdvancedGridView extends TableLayout {  
  
//  private static final String tag = "AdvancedGridView";  
      
    private int rowNum = 0; // row number  行数
    private int colNum = 0; // col number  列数
      
    private BaseAdapter adapter = null;   
      
    private Context context = null;  
      
    public AdvancedGridView(Context context) {  
        super(context);  
        initThis(context, null);  
    }  
  
    public AdvancedGridView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        initThis(context, attrs);  
    }  
  
    private void initThis(Context context, AttributeSet attrs) { 
    	Log.e("initThis", "initThis");
        this.context =  context;  
    	Log.e("context", context.toString());
        if (this.getTag() != null) {  
            String atb = (String) this.getTag();  
            int ix = atb.indexOf(',');  
            if (ix > 0) {  
                rowNum = Integer.parseInt(atb.substring(0, ix));  
                colNum = Integer.parseInt(atb.substring(ix+1, atb.length()));  
            }  
        }  
        if (rowNum <= 0)  
            rowNum = 3;  
        if (colNum <= 0)  
            colNum = 3;  
  
    	Log.e("this.isInEditMode()", this.isInEditMode()+"");
        if(this.isInEditMode()){  
        	Log.e("isineditmode", "isineditmode");
            this.removeAllViews();  
            for(int y=0; y<rowNum; ++y){  
                TableRow row = new TableRow(context);  
                row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f));  
                for(int x=0; x<colNum; ++x){  
                View v=LayoutInflater.from(context).inflate(
        					R.layout.main_menu_item, null);
                //    View button = new Button(context);  
                    row.addView(v, new TableRow.LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));  
                }  
                this.addView(row);  
            }  
        }
        
    }  
  
    public BaseAdapter getAdapter() {  
        return adapter;  
    }  
  
    public void setAdapter(BaseAdapter adapter) {  
        if(adapter != null){  
            if(adapter.getCount() < this.rowNum*this.colNum){  
                throw new IllegalArgumentException("The view count of adapter is less than this gridview's items");  
            }  
            this.removeAllViews();  
            for(int y=0; y<rowNum; ++y){  
                TableRow row = new TableRow(context);  
                row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));  
                for(int x=0; x<colNum; ++x){  
                    View view = adapter.getView(y*colNum+x, null, row);  
                    row.addView(view, new TableRow.LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));  
                }  
                this.addView(row);  
            }  
        }  
        this.adapter = adapter;  
    }  
  
    public int getRowNum() {  
        return rowNum;  
    }  
  
    public void setRowNum(int rowNum) {  
        this.rowNum = rowNum;  
    }  
  
    public int getColNum() {  
        return colNum;  
    }  
  
    public void setColNum(int colNum) {  
        this.colNum = colNum;  
    }  
}  