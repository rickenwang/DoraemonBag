package com.doraemon.ui.sample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doraemon.ui.TransferProgressIndicatorView;

public class MainActivity extends AppCompatActivity {

    int progress = 0;
    TransferProgressIndicatorView inProgress;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(getMainLooper());

        inProgress = findViewById(R.id.in_progress);
        TransferProgressIndicatorView uploadPause = findViewById(R.id.upload_pause);
        TransferProgressIndicatorView downloadPause = findViewById(R.id.download_pause);
        TransferProgressIndicatorView retry = findViewById(R.id.retry);

        inProgress.refreshState(TransferProgressIndicatorView.IN_PROGRESS);
        refreshProgress();

        uploadPause.refreshState(TransferProgressIndicatorView.PAUSE);
        uploadPause.setTransferType(TransferProgressIndicatorView.TRANSFER_TYPE_UPLOAD);
        uploadPause.refreshProgress(40);

        downloadPause.refreshState(TransferProgressIndicatorView.PAUSE);
        downloadPause.setTransferType(TransferProgressIndicatorView.TRANSFER_TYPE_DOWNLOAD);
        downloadPause.refreshProgress(40);

        retry.refreshState(TransferProgressIndicatorView.RETRY);

    }

    void refreshProgress() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                inProgress.refreshProgress(progress++);
                if (progress > 100) {
                    progress = 0;
                }
                refreshProgress();
            }
        }, 100);
    }

}
