package cfh.com.shopkaro;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.microsoft.azure.storage.StorageCredentials;
import com.microsoft.azure.storage.StorageCredentialsSharedAccessSignature;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

public class SellNewActivity extends BaseMenuActivitiy {

    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<Photo> photoTable;

    private String photoID;
    ProgressBar bar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Activity", "Sell");

        getLayoutInflater().inflate(R.layout.activity_sell_new, subActivityLayout);

        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://shopkaromobile.azure-mobile.net/",
                    "qUxsFpdMoybqGfDkywFQPZQaikyvGT63",
                    this);

            // Get the Mobile Service Table instance to use

            photoTable = mClient.getTable(Photo.class);

            // Offline Sync
            //mToDoTable = mClient.getSyncTable("ToDoItem", ToDoItem.class);

        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e){
            createAndShowDialog(e, "Error");
        }

        int fragmentId = getIntent().getExtras().getInt("fragment");
        Fragment fragment;
        if(fragmentId == R.id.nav_sell_service){
            fragment = new SellServiceFragment();
        }
        else {
            fragment = new SellProductFragment();
        }
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.sell_content_frame, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    // Create a File object for storing the photo
    public File createImageFile() throws IOException {
        Log.e("Method", "createImageFile");
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        Log.e("takePicture", imageFileName);
        return image;
    }

    // Run an Intent to start up the Android camera
    static final int REQUEST_TAKE_PHOTO = 1;
    public Uri mPhotoFileUri = null;
    public File mPhotoFile = null;

    public void takePicture(View view) {
        Log.e("Method", "takePicture");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                mPhotoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //
            }
            // Continue only if the File was successfully created
            if (mPhotoFile != null) {
                Log.e("takePicture", "mPhotoFile not null");
                mPhotoFileUri = Uri.fromFile(mPhotoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoFileUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_TAKE_PHOTO) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Button uploadPictureButton = (Button) findViewById(R.id.buttonUpload);
                uploadPictureButton.setEnabled(true);
            }
        }
    }

    /**
     * Add an item to the Mobile Service Table
     *
     * @param item
     *            The item to Add
     */
    public Photo addItemInTable(Photo item) throws ExecutionException, InterruptedException {
        Log.e("Method", "addItemInTable");
    Photo entity = photoTable.insert(item).get();
    return entity;
}

    /**
     * Add a new item
     *
     * @param view
     *            The view that originated the call
     */
    public String uploadPhoto(View view) {
        Log.e("Method", "uploadPhoto");
        if (mClient == null) {
            return null;
        }

        bar = (ProgressBar) findViewById(R.id.progressBar);

        // Create a new item
        final Photo item = new Photo();

        item.setText("testimage1");
       // item.setText(mTextNewToDo.getText().toString());
       // item.setComplete(false);
        item.setContainerName("cfhimages");

        // Use a unigue GUID to avoid collisions.
        UUID uuid = UUID.randomUUID();
        photoID = uuid.toString();
        item.setResourceName(photoID);

        // Send the item to be inserted. When blob properties are set this
        // generates an SAS in the response.
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){

            @Override
            protected void onPreExecute(){
                bar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final Photo entity = addItemInTable(item);


                    // If we have a returned SAS, then upload the blob.
                    if (entity.getSasQueryString() != null) {
                        Log.e("Photo sasquerystring", entity.getSasQueryString());
                        // Get the URI generated that contains the SAS
                        // and extract the storage credentials.
                        StorageCredentials cred =
                                new StorageCredentialsSharedAccessSignature(entity.getSasQueryString());
                        URI imageUri = new URI(entity.getImageUri());

                        // Upload the new image as a BLOB from a stream.
                        CloudBlockBlob blobFromSASCredential =
                                new CloudBlockBlob(imageUri, cred);


                        Log.e("Photo filepath", mPhotoFileUri.getPath());
                        blobFromSASCredential.uploadFromFile(mPhotoFileUri.getPath());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!entity.isComplete()){
                               // mAdapter.add(entity);
                            }
                        }
                    });
                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                bar.setVisibility(View.GONE);
                Button addServiceButton = (Button) findViewById(R.id.add_product_button);
                addServiceButton.setEnabled(true);
            }
        };

        runAsyncTask(task);
        return photoID;

       // mTextNewToDo.setText("");
    }

    /**
     * Run an ASync task on the corresponding executor
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }


    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message
     *            The dialog message
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

}
