package com.example.zhaoxuanli.nodatatipdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zhaoxuanli.nodatatipdemo.widget.NoDataTipsWidget;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    protected static final int TASK_COMPLETED = 1; //网络请求成功
    protected static final int TASK_FAILED = -1;  //服务器连接失败
    protected static final int TASK_NONETWORK = -2;//无网络

    private NoDataTipsWidget noDataTipsView;

    /**
     * 因为是一个简单的Demo，空数据提示是肯定要显示的，ListView我们也用不到，就不初始化了
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noDataTipsView = (NoDataTipsWidget)findViewById(R.id.nodata_tipsview);
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(-1);// 测试是请求失败的情况
            }
        }, 2000);// 延迟1秒,然后加载
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            ArrayList list = new ArrayList(); //List是空的，所以应该显示空数据提示
            switch (msg.what){
                case TASK_COMPLETED:  //请求成功后处理
                    break;
                case TASK_FAILED:  //请求失败后处理
                    break;
                case TASK_NONETWORK:  //无网络情况处理
                    break;
            }
            //所有状况处理完毕后，调用noDataTipsView.doTipsView（）方法 adapter 或List都可以
            noDataTipsView.doTipsView(msg, list);

        };
    };


}
