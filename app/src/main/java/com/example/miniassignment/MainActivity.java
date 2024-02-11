package com.example.miniassignment;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.*;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Integer> nums;
    ImageView imageView;
    ActivityResultLauncher<Intent> resultLauncher;
    private static final int PERMISSION_REQUEST = 0;
    private static final int REQUEST_LOAD_IMAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if((Build.VERSION.SDK_INT > Build.VERSION_CODES.M) && checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
//        }

        nums = buildNums(50);
        imageView = (ImageView)findViewById(R.id.imageview);
        TextView tb1 = (TextView)findViewById(R.id.tb1);
        System.out.println("nums set");
        Button b1 = (Button) findViewById(R.id.b1);
        registerResult();

//        b1.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                // Launch the photo picker and let the user choose only images.
//                resultLauncher.launch(new PickVisualMediaRequest.Builder()
//                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
//                        .build());
//                updateNum(tb1);
//            }
//        });

        b1.setOnClickListener(view -> buttonActions());


    }

    public void buttonActions(){
        pickImage();
    }

    public void pickImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    //updates the number in the text field to a random one from the numbers list
    public void updateNum(TextView tv){
        int index = (int)(Math.random()*nums.size());
        tv.setText(nums.get(index).toString());
        //tv.setText("1");
    }

    //Gives an arraylist of integers based on size
    //Integers in the list anywhere from 0 to 4x the size of the number specified
    //No repeat integers allowed
    public ArrayList<Integer> buildNums(int i){
        ArrayList<Integer> newnums = new ArrayList<>();
        while(newnums.size()<i){
            System.out.println("newnums size: "+newnums.size());
            int newnum = (int) (Math.random()*i*4);
            if (newnums.contains(newnum)) {
                System.out.println("Skipped "+newnum);
                continue;
            }
            System.out.println(newnum);
            newnums.add(newnum);
        }
        System.out.println("built newnums");
        return newnums;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    // Registers a photo picker activity launcher in single-select mode.
//    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
//            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
//                // Callback is invoked after the user selects a media item or closes the
//                // photo picker.
//                if (uri != null) {
//                    Log.d("PhotoPicker", "Selected URI: " + uri);
//                } else {
//                    Log.d("PhotoPicker", "No media selected");
//                }
//            });

    private void registerResult(){
        resultLauncher
                = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Uri imageUri = result.getData().getData();
                        imageView.setImageURI(imageUri);
                    }
                }
        );
    }


}