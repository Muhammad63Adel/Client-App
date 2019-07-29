package com.example.clientapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

//import android.support.annotation.DrawableRes;
//import com.blikoon.qrcodescanner.QrCodeActivity;
//import android.support.v7.app.AlertDialog;
//import android.content.DialogInterface;


public class qr extends AppCompatActivity {

    final Activity activity = this;
    String message;
    ImageView image;
    private static final int REQUEST_CODE_SCAN = 101;
    public final static int QRcodeWidth = 500 ;
    private static final String IMAGE_DIRECTORY = "/QRfile";
    Bitmap bitmap ;
    ImageView iv;
    TextView result_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");
        TextView text_message = (TextView) findViewById(R.id.recv_message);
        text_message.setText(message);

        iv = (ImageView) findViewById(R.id.qr_image);

        try {
            bitmap = TextToImageEncode(text_message.getText().toString());
            iv.setImageBitmap(bitmap);
            String path = saveImage(bitmap);  //give read write permission
            Toast.makeText(qr.this, "QRCode saved to -> "+path, Toast.LENGTH_SHORT).show();
        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void check_product(View view) {

        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode, data);
        if (result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "you cancelled the scanning ",Toast.LENGTH_SHORT).show();

            }else{

                String str = result.getContents();

                boolean correct = message.equals(str);
                result_text = findViewById(R.id.result_message);
                image = findViewById(R.id.status_image);
                iv.setVisibility(View.INVISIBLE);
                image.setVisibility(View.VISIBLE);
                if(correct){

                    result_text.setText("right");
                    image.setBackgroundResource(R.drawable.right);

                }else {
                    result_text.setText("wrong");
                    image.setBackgroundResource(R.drawable.wrong);

                }

            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
