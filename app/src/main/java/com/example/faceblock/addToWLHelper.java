package com.example.faceblock;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.AddPersistedFaceResult;
import com.microsoft.projectoxford.face.contract.FaceRectangle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class addToWLHelper {
}

public class AddFaceToPersonActivity extends AppCompatActivity {
    // Background task of adding a face to person.
    class AddFaceTask extends AsyncTask<Void, String, Boolean> {
        List<Integer> mFaceIndices;
        AddFaceTask(List<Integer> faceIndices) {
            mFaceIndices = faceIndices;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Get an instance of face service client to detect faces in image.
            FaceServiceClient faceServiceClient = FaceServiceHelper.getFaceServiceClient();
            try{
                publishProgress("Adding face...");
                UUID personId = UUID.fromString(mPersonId);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                InputStream imageInputStream = new ByteArrayInputStream(stream.toByteArray());

                for (Integer index: mFaceIndices) {
                    FaceRectangle faceRect = mFaceGridViewAdapter.faceRectList.get(index);
                    addLog("Request: Adding face to person " + mPersonId);
                    // Start the request to add face.
                    AddPersistedFaceResult result = faceServiceClient.addPersonFaceInLargePersonGroup(
                            mPersonGroupId,
                            personId,
                            imageInputStream,
                            "User data",
                            faceRect);

                    mFaceGridViewAdapter.faceIdList.set(index, result.persistedFaceId);
                }
                return true;
            } catch (Exception e) {
                publishProgress(e.getMessage());
                addLog(e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPreExecute() {
            setUiBeforeBackgroundTask();
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            setUiDuringBackgroundTask(progress[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            setUiAfterAddingFace(result, mFaceIndices);
        }
    }