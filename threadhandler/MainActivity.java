package snc_cs.threadhandler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = (TextView) findViewById(R.id.tvCount);

        Thread thread = new Thread(countNumbers);
        thread.start();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mCount = 0;
//    }

    public Handler threadHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle here = msg.getData();
            int x = here.getInt("theCount");
            tvCount.setText( x+"");
        }
    };
    private Runnable countNumbers = new Runnable() {
        private static final int DELAY = 1000;
        private int mCount = 0;
        public void run() {
            try {
                while (true) {
                    mCount++;
                    Thread.sleep(DELAY);
                    Bundle b = new Bundle();
                    b.putInt("theCount", mCount);
                    Message myMsg = new Message();
                    myMsg.setData(b);
                    threadHandler.sendMessage(myMsg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
