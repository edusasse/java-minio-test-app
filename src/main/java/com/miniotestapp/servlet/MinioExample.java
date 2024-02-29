package com.miniotestapp.servlet;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioExample {
    private static final Logger logger = LoggerFactory.getLogger(MinioExample.class);

    private static final String PRIVATE_BUCKET_NAME = "private-sample-bucket";
    private static final String FILE_NAME_PRIVATE = "private_sample.txt";

    public static void main(String[] args) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("minio.myapp.com", 80, false)
                    .credentials("test", "testpass")
                    .build();

            // Check if bucket exists, create if not
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(PRIVATE_BUCKET_NAME).build());
            if (!found) {
                logger.info("Creating bucket [{}]", PRIVATE_BUCKET_NAME);
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(PRIVATE_BUCKET_NAME).build());
            }

            // Create private file
            final File privateFile = new File("private_file.txt");
            privateFile.createNewFile();
            FileWriter writer = new FileWriter(privateFile);
            writer.write("My privates file content!\n");
            writer.close();

            // Upload the file
            minioClient.uploadObject(
                    io.minio.UploadObjectArgs.builder()
                            .bucket(PRIVATE_BUCKET_NAME)
                            .object(FILE_NAME_PRIVATE)
                            .filename(privateFile.getAbsolutePath())
                            .build());

            // Generate presigned URL
            String url = minioClient.getPresignedObjectUrl(
                    io.minio.GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(PRIVATE_BUCKET_NAME)
                            .object(FILE_NAME_PRIVATE)
                            .build());

            logger.info("Presigned URL for {}: {}", FILE_NAME_PRIVATE, url);

        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            logger.error("Error occurred: ", e);
        }
    }
}