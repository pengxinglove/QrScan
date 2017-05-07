package com.hp.qrscan;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.zxing.qrcode.encoder.QRCode;
import com.netease.scan.IScanModuleCallBack;
import com.netease.scan.QrScan;
import com.netease.scan.ui.CaptureActivity;

public class MainActivity extends AppCompatActivity {
    private CaptureActivity mCaptureContext;
    private Button scanBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn =(Button)findViewById(R.id.scanBtn);
        setListeners();
    }

    private void setListeners() {
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QrScan.getInstance().launchScan(MainActivity.this, new IScanModuleCallBack() {
                    @Override
                    public void OnReceiveDecodeResult(Context context, String result) {
                        mCaptureContext = (CaptureActivity)context;
                        final AlertDialog dialog1 = new AlertDialog.Builder(mCaptureContext)
                                .setMessage(result)
                                .setCancelable(false)
                                .setNegativeButton("取消", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        QrScan.getInstance().restartScan(mCaptureContext);
                                    }
                                })
                                .setPositiveButton("关闭", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        QrScan.getInstance().finishScan(mCaptureContext);
                                    }
                                })
                                .create();
                        dialog1.show();
                    }
                });
            }
        });
    }
}
