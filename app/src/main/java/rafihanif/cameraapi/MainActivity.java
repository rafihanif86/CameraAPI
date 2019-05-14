package rafihanif.cameraapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ImageView imageHolder;
    private final int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageHolder = (ImageView)findViewById(R.id.captured_photo);
        Button capturedImageButton = (Button)findViewById(R.id.take_picture);
        capturedImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, requestCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageHolder.setImageBitmap(bitmap);
        }
    }

    private void saveImage() {
        Date date = new Date();
        String dateString = date.toString().replaceAll("\\s", "");
        File rootFolder = Environment.getExternalStorageDirectory();
        File imgFolder = new File(rootFolder + "/Pictures/CameraAPI");
        File pdfFile = new File(rootFolder.getAbsolutePath() + "/Pictures/CameraAPI" + "image" + dateString + "jpg");
        if (!imgFolder.exists()) {
            imgFolder.mkdir();
        }
        if (!imgFolder.exists()) {
            try {
                pdfFile.createNewFile();
                Toast.makeText(this, "image saved", Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Someting Wrong:" + toString(), Toast.LENGTH_LONG).show();
            }
            File file = new File(rootFolder.getAbsolutePath() + "/Pictures/CameraAPI", "image" +dateString + "jpg");
            if (file.exists()) file.delete();
            ImageView imageView = (ImageView) findViewById(R.id.captured_photo);
            Bitmap finalBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void savePicture(View view) {
        saveImage();
    }
}
