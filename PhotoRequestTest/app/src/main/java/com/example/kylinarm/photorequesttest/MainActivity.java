package com.example.kylinarm.photorequesttest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.btn_chose)
    TextView btnChose;
    @InjectView(R.id.btn_up)
    TextView btnUp;

    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_chose,R.id.btn_up})
    public void click(View v){
        switch (v.getId()){
            case R.id.btn_chose:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0x1111);
                break;
            case R.id.btn_up:
                upload();
                break;
        }
    }

    /**
     *  上传图片
     */
    private void upload(){
        ByteArrayOutputStream out = null;
        try {
            InputStream in = getContentResolver().openInputStream(uri);
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) != -1) {
                out.write(b, 0, b.length);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = out.toByteArray();

        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("uploadFile\"; filename=\"test.jpg\""
                ,RequestBody.create(MediaType.parse("multipart/form-data"), bytes));

        APIClinet.getInstance(PhotoService.class)
                .upload(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {

                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0x1111) {
            uri = data.getData();
            tvAddress.setText(uri.toString());
        }
    }
}
