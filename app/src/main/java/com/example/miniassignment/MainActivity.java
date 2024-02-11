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
    private ArrayList<String> birds;
    ImageView imageView;
    TextView tb1;
    TextView tb2;
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
        birds=new ArrayList<>();
        buildBirds();
        imageView = (ImageView)findViewById(R.id.imageview);
        tb1 = (TextView)findViewById(R.id.tb1);
        tb2 = (TextView)findViewById(R.id.tb2);
        System.out.println("nums set");
        Button b1 = (Button) findViewById(R.id.b1);
        registerResult();
        b1.setOnClickListener(view -> buttonActions());
    }

    public void buttonActions(){
        pickImage();
        int i = updateNum();
        String s = updateBird();
        tb1.setText(String.format("%d",i));
        tb2.setText(s);
    }

    public void pickImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    //updates the number in the text field to a random one from the numbers list
    public int updateNum(){
//        int index = (int)(Math.random()*nums.size());
        int index = (int)(Math.random()*50);
        int num = nums.get(index);
        return num;
    }

    //returns a random bird from the list of birds
    public String updateBird(){
        int index = (int)(Math.random()*birds.size());
        return birds.get(index);
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

    //creates a list of birds so text can be added below image
    private void buildBirds(){
        String[] b = new String[]{"Flamingo",
                "Crow",
                "Hen",
                "Vulture",
                "Eagle",
                "Peacock",
                "Pigeon",
                "Emu",
                "Ostrich",
                "Dove",
                "Stork"};
        birds.addAll(Arrays.asList(b));
    }

}