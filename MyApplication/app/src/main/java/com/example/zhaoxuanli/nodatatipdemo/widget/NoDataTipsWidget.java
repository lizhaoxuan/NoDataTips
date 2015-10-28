package com.example.zhaoxuanli.nodatatipdemo.widget;

/**
 * 空数据提示控件
 * Created by zhaoxuan.li on 2015/9/26.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.zhaoxuanli.nodatatipdemo.R;

import java.util.List;

/**
 * Created by zhaoxuan.li on 2015/8/28.
 * 无数据时提示的自定义控件
 */
public class NoDataTipsWidget extends RelativeLayout {
    protected static final int TASK_COMPLETED = 1;//网络请求成功
    protected static final int TASK_FAILED = -1;//服务器连接失败
    protected static final int TASK_NONETWORK = -2;//无网络
    private ImageView tipImage;
    private TextView tipText;
    public TipsOnClickListener tipsOnClickListener;

    public NoDataTipsWidget(Context context) {
        super(context);
        init(context, null);
    }

    public NoDataTipsWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 对控件进行初始化，并取得xml文件里给予的资源
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs){
        if(attrs ==null)
            return;
        LayoutInflater.from(context).inflate(R.layout.nodata_tipsview, this, true);
        tipImage = (ImageView)findViewById(R.id.tipImage);
        tipText = (TextView)findViewById(R.id.tipText);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NoDataTipsWidget);
        CharSequence text = a.getText(R.styleable.NoDataTipsWidget_android_text);
        if(text!=null) tipText.setText(text);
        Drawable drawable = a.getDrawable(R.styleable.NoDataTipsWidget_android_src);
        if(drawable != null) tipImage.setImageDrawable(drawable);
        a.recycle();
        //设置图片的点击事件，通常我们在这里设置点击图片刷新
        tipImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipsOnClickListener != null)
                    tipsOnClickListener.OnClick();
            }
        });
    }


    /**
     * 设置提示图片的监听事件，一个事件接口，需要调用处来实现
     * @param onClickListener
     */
    public void setTipsClickListener(TipsOnClickListener onClickListener){
        tipsOnClickListener = onClickListener;
    }
    /**
     * 设置提示文字
     * @param str
     */
    public void setText(String str){
        tipText.setText(str);
    }

    /**
     * 设置提示图片
     * @param drawable
     */
    public void setImage(Drawable drawable){
        tipImage.setImageDrawable(drawable);
    }

    /**
     * 图片点击事件回调接口
     */
    public interface TipsOnClickListener{
        void OnClick();
    }


    /**
     * 重載方法，當無Adapter可以加入時，使用List判斷
     * @param msg   handler Message
     * @param dto   List 数据集
     */
    public void doTipsView(Message msg , List dto){ doTipsView(msg, isEmpty(dto));}
    /**
     * 重載方法  仅根据List 数据集判断提示样式
     * @param dto   List 数据集
     */
    public void doTipsView(List dto){   doTipsView(null,isEmpty(dto));}
    /**
     * 重載方法 仅根据Adapter设置提示样式
     * @param adapter   Adapter
     */
    public void doTipsView(Adapter adapter){    doTipsView(null,isEmpty(adapter));}
    /**
     * 根据 Message  和 Adapter 设置提示样式
     * @param msg   handler Message
     * @param adapter   Adapter 数据集
     */
    public void doTipsView(Message msg , Adapter adapter){
        doTipsView(msg, isEmpty(adapter));
    }
    /**
     * 重载方法仅用于数据接收，具体的判断逻辑由此方法统一处理
     * @param msg
     * @param noData
     */
    private  void doTipsView(Message msg , boolean  noData){
        if(!noData){   //如果数据集不为空，就隐藏
            this.setVisibility(View.GONE);
            return ;
        }else{
            if(msg==null){
                showTip_noData();
                return;
            }
            switch (msg.what){
                case TASK_COMPLETED:  //网络请求成功
                    showTip_noData();return;
                case TASK_FAILED:   //网络请求失败
                    showTip_noServer();return;
                case TASK_NONETWORK:
                    showTip_noConnect();return;
            }
        }
    }

    private boolean isEmpty(Adapter adapter){
        if(adapter == null)
            return true;
        return adapter.isEmpty();
    }
    private boolean isEmpty(List list){
        if(list == null)
            return true;
        return list.isEmpty();
    }

    /**
     * 设置样式为无数据样式
     */
    public void showTip_noData(){
        tipImage.setImageResource(R.drawable.nodatatip_nodata);
        tipText.setText("无任何数据");
        this.setVisibility(View.VISIBLE);
    }
    /**
     * 设置样式为无连接样式
     */
    public void showTip_noConnect(){
        tipImage.setImageResource(R.drawable.nodatatip_noserver);
        tipText.setText("无网络连接");
        this.setVisibility(View.VISIBLE);
    }
    /**
     * 设置样式为无服务器样式
     */
    public void showTip_noServer(){
        tipImage.setImageResource(R.drawable.nodatatip_noserver);
        tipText.setText("暂时无法连接服务器，请稍后再试");
        this.setVisibility(View.VISIBLE);
    }

}
