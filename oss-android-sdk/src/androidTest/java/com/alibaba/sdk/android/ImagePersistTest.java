package com.alibaba.sdk.android;

import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.HeadObjectRequest;
import com.alibaba.sdk.android.oss.model.HeadObjectResult;
import com.alibaba.sdk.android.oss.model.ImagePersistRequest;
import com.alibaba.sdk.android.oss.model.ImagePersistResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;

/**
 * Created by huaixu on 2018/1/30.
 */

public class ImagePersistTest extends BaseTestCase {
    public static final String JPG_OBJECT_KEY = "JPG_OBJECT_KEY";
    public static final String persist2Obj = "persis2Obj";
    private static final String objectName = "shilan.jpg";

    @Override
    void initTestData() throws Exception {
        OSSTestConfig.initDemoFile(objectName);
    }


    public void testImagePersist() throws Exception {
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(mBucketName, JPG_OBJECT_KEY,
                    OSSTestConfig.FILE_DIR + objectName);
            OSSTestConfig.TestPutCallback putCallback = new OSSTestConfig.TestPutCallback();
            OSSAsyncTask task = oss.asyncPutObject(putObjectRequest, putCallback);
            task.waitUntilFinished();

            ImagePersistRequest request = new ImagePersistRequest(mBucketName, JPG_OBJECT_KEY, mBucketName, persist2Obj, "resize,w_100");
            ImagePersistResult result = oss.imagePersist(request);
            assertEquals(200, result.getStatusCode());

            HeadObjectRequest headRequest = new HeadObjectRequest(mBucketName, persist2Obj);
            HeadObjectResult headResult = oss.headObject(headRequest);

            assertNotNull(headResult.getMetadata().getContentType());
            assertEquals(200, headResult.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
            assertNull(e);
        }

    }
}
