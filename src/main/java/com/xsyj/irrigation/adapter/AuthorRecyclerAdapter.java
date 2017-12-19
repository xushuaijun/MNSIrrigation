package com.xsyj.irrigation.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xsyj.irrigation.MainActivity;
import com.xsyj.irrigation.R;
import com.xsyj.irrigation.biz.CreateTempTurnTaskBiz;
import com.xsyj.irrigation.entity.IrrigationTurnInfo;
import com.xsyj.irrigation.interfaces.NetCallBack;
import com.xsyj.irrigation.utils.AlertDialogUtil;
import com.xsyj.irrigation.utils.LogUtil;
import com.xsyj.irrigation.utils.TimeUtil;
import com.xsyj.irrigation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Clock on 2016/7/26.
 */
public class AuthorRecyclerAdapter extends RecyclerView.Adapter<AuthorRecyclerAdapter.AuthorViewHolder> {

    private List<IrrigationTurnInfo> mAuthorInfoList=new ArrayList<>();
    /**
     * item view 的类型是否是小类型的
     */
    private boolean mIsSmall = false;
    private Context mContext;

    public AuthorRecyclerAdapter(List<IrrigationTurnInfo> authorInfoList) {
        LogUtil.e("IrrigationWatcher","authorInfoList"+authorInfoList.size());
        this.mAuthorInfoList.clear();
        this.mAuthorInfoList.addAll(authorInfoList);
    }

    public void setSmallType(boolean isSmall) {
        this.mIsSmall = isSmall;
    }

    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = null;

        childView = inflater.inflate(R.layout.turn_item, parent, false);


        AuthorViewHolder viewHolder = new AuthorViewHolder(childView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AuthorViewHolder holder, int position) {
        LogUtil.e("IrrigationWatcher", "onBindViewHolder");
        holder.contentView.setEnabled(false);

        final IrrigationTurnInfo authorInfo = mAuthorInfoList.get(position);
        holder.turn_name.setText(authorInfo.getTurnname());
        holder.cdz_fk.setText(authorInfo.getPileName());

        if(authorInfo.getTurnState()==0){
            holder.state_now.setText("休眠中…");
            holder.btn_switch.setText("开启");
        }else{
            holder.state_now.setText("灌溉中…");
            holder.btn_switch.setText("停止");
        }



        if(authorInfo.getStartType()==0){
            holder.lg_fs.setText("自动");
        }else{
            holder.lg_fs.setText("手动");
        }

        if(authorInfo.getStartType()==1){
            holder.next_time.setText("--");
        }else{
            if(authorInfo.getNextExeTime()==null || authorInfo.getNextExeTime()==0){
                holder.next_time.setText("无下次执行任务");
            }else{
                holder.next_time.setText(TimeUtil.datetimestring(new Date(authorInfo.getNextExeTime())));
            }
        }






        holder.btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {




                AlertDialogUtil.showDialog((MainActivity) mContext, "提示", "您确定开启轮灌组吗？", new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case AlertDialogUtil.SETOK: // 确定
                                String dowhat = "0";
                                if ("开启".equals(((TextView) v).getText().toString().trim())) {
                                    dowhat = "1";
                                } else {
                                    dowhat = "0";
                                }

                                CreateTempTurnTaskBiz createTempTurnTaskBiz = new CreateTempTurnTaskBiz();

                                createTempTurnTaskBiz.control(v.getContext(), String.valueOf(authorInfo.getId()), dowhat, new NetCallBack<Integer>() {
                                    @Override
                                    public void getNetSuccess(int statu, String url, Integer integer) {
                                          if(integer.intValue()==1){
                                              ToastUtil.toast(mContext,"命令发送成功");
                                          }else{
                                              ToastUtil.toast(mContext,"命令发送失败");
                                          }
                                    }

                                    @Override
                                    public void getNetFauiled(int statu, String url) {

                                    }

                                    @Override
                                    public void getNetCanceled(int statu, String url) {

                                    }

                                    @Override
                                    public void getNetError(int statu, String url) {

                                    }

                                    @Override
                                    public void getNetFinished(int statu, String url) {

                                    }
                                });
                                break;
                            case AlertDialogUtil.SETCANCEL: // 取消


                                break;

                        }
                    }
                });




            }
        });


    }

    /**
     * 移动Item
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        //做数据的交换
        if (fromPosition < toPosition) {
            for (int index = fromPosition; index < toPosition; index++) {
                Collections.swap(mAuthorInfoList, index, index + 1);
            }
        } else {
            for (int index = fromPosition; index > toPosition; index--) {
                Collections.swap(mAuthorInfoList, index, index - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 滑动Item
     *
     * @param position
     */
    public void removeItem(int position) {
        mAuthorInfoList.remove(position);//删除数据
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        if (mAuthorInfoList == null) {
            return 0;
        }
        return mAuthorInfoList.size();
    }



    static class AuthorViewHolder extends RecyclerView.ViewHolder {

        LinearLayout contentView;
        TextView turn_name;//轮灌组名称
        TextView cdz_fk;//出地桩-阀口
        TextView state_now;//当前状态
        TextView lg_fs;//轮灌方式
        TextView next_time;//下次执行时间

        Button btn_switch;//开关
        public AuthorViewHolder(View itemView) {
            super(itemView);

            contentView = (LinearLayout) itemView.findViewById(R.id.contentView);
            turn_name = (TextView) itemView.findViewById(R.id.tv_turn_name);
            cdz_fk = (TextView) itemView.findViewById(R.id.tv_cdz_fk);
            state_now = (TextView) itemView.findViewById(R.id.tv_state_now);
            lg_fs = (TextView) itemView.findViewById(R.id.tv_lg_fs);
            next_time = (TextView) itemView.findViewById(R.id.tv_next_time);
            next_time = (TextView) itemView.findViewById(R.id.tv_next_time);
            btn_switch=(Button)itemView.findViewById(R.id.btn_switch);


        }
    }


}
