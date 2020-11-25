package fr.android.progmob_poject;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import fr.android.progmob_poject.jdbc.Controller;
import fr.android.progmob_poject.model.Match;
import fr.android.progmob_poject.sqlite.SQLiteDatabaseHelper;

public class AddFragment extends Fragment implements View.OnClickListener {
    public EditText teamA;
    public EditText teamB;
    public EditText address;
    public EditText coordinates;
    public EditText date;
    public EditText scoreTeamA;
    public EditText scoreTeamB;
    public Button submit;
    public Button location;
    public Button picture;
    public MainActivity mainActivity;
    public ImageView imageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private int id = 0;
    String currentPhotoPath;
    SQLiteDatabaseHelper mSQLiteDatabaseHelper;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        teamA = rootView.findViewById(R.id.editTextTeamA);
        teamB = rootView.findViewById(R.id.editTextTeamB);
        scoreTeamA = rootView.findViewById(R.id.editTextScoreTeamA);
        scoreTeamB = rootView.findViewById(R.id.editTextScoreTeamB);
        address = rootView.findViewById(R.id.editTextAddress);
        coordinates = rootView.findViewById(R.id.editTextCoordinates);
        date = rootView.findViewById(R.id.editTextDate);
        submit = rootView.findViewById(R.id.buttonSubmit);
        location = rootView.findViewById(R.id.buttonLocation);
        picture = rootView.findViewById(R.id.buttonPicture);
        imageView = rootView.findViewById(R.id.imageView);
        submit.setOnClickListener(this);
        location.setOnClickListener(this);
        picture.setOnClickListener(this);
        mainActivity = (MainActivity) getActivity();
        mSQLiteDatabaseHelper = new SQLiteDatabaseHelper(mainActivity);

        return rootView;
    }
/*
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

 */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLocation:
                Toast.makeText(mainActivity, "button location clicked", Toast.LENGTH_LONG).show();


                break;
            case R.id.buttonPicture:
                //Toast.makeText(mainActivity, "button picture clicked", Toast.LENGTH_LONG).show();
                dispatchTakePictureIntent();
                break;
            case R.id.buttonSubmit:

                if (teamA.length() != 0 && teamB.length() != 0 && address.length() != 0 && coordinates.length() != 0 && date.length() != 0 && scoreTeamA.length() != 0 && scoreTeamB.length() != 0 ) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                    LocalDate ld = LocalDate.parse("1970-01-01");
                    try {
                        ld = LocalDate.parse(date.getText(), formatter);
                    } catch (DateTimeParseException e) {
                        System.err.println("Unable to parse the date!");
                        Toast.makeText(mainActivity, "Unable to parse the date. please use format (d/MM/yyyy) ", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    String strTeamA = teamA.getText().toString().replaceAll("\\s", "");
                    String strTeamB = teamB.getText().toString().replaceAll("\\s", "");
                    Match match = new Match(0, strTeamA, strTeamB, address.getText().toString(), coordinates.getText().toString(), ld, Integer.parseInt(scoreTeamA.getText().toString()), Integer.parseInt(scoreTeamB.getText().toString()));
                    AsyncCaller task = new AsyncCaller();
                    mSQLiteDatabaseHelper.addData(match, currentPhotoPath);
                    task.execute(new Match[]{match});
                    break;
                } else {
                    Toast.makeText(mainActivity, "You must put something in the text fields", Toast.LENGTH_LONG).show();
                }
        }

    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mainActivity,
                        "fr.android.progmob_poject.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }


    private class AsyncCaller extends AsyncTask<Match, Void, String> {
        @Override
        protected String doInBackground(Match... matches) {
            Match match = matches[0];
            try {
                id = Controller.addMatch(match);

                return null;
            } catch (Exception e) {
                Toast.makeText(mainActivity, "error", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(mainActivity, "Match Added", Toast.LENGTH_LONG).show();
                mainActivity.bottomNav.setSelectedItemId(R.id.nav_home);
                teamA.getText().clear();
                teamB.getText().clear();
                address.getText().clear();
                coordinates.getText().clear();
                date.getText().clear();
            } else {
                //Toast.makeText(mainActivity, "error", Toast.LENGTH_LONG).show();
                Toast.makeText(mainActivity, result, Toast.LENGTH_LONG).show();
            }

        }

    }
    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            File file = new File(currentPhotoPath);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(mainActivity.getContentResolver(), Uri.fromFile(file));
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (data != null){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mainActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        System.out.println(storageDir.toString());
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }




}
