package com.dizstance.spring.cloud.s3.service;

import io.awspring.cloud.s3.S3Exception;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.EncodingType;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.config.DownloadFilter;
import software.amazon.awssdk.transfer.s3.model.CompletedDirectoryDownload;
import software.amazon.awssdk.transfer.s3.model.DirectoryDownload;
import software.amazon.awssdk.transfer.s3.model.DownloadDirectoryRequest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AwsS3Client {

    private static final String BUCKET_NAME = "dizstance-test-bucket";
    private static final String OBJECT_KEY = "defaults3clientobject.txt";
    private static final String FILE_PATH = "H:\\Proyectos\\aws-integration-parent\\AWS-Integration\\aws-sdk-native\\AWS\\mona.jpg";
    private final S3Client s3Client;
    private final S3TransferManager s3TransferManager;

    public AwsS3Client(S3Client s3Client, S3TransferManager s3TransferManager) {
        this.s3Client = s3Client;
        this.s3TransferManager = s3TransferManager;
    }

    //TODO: Create - Upload file from disk
    public void putObject() {

        s3Client.putObject(request ->
                request
                        .bucket(BUCKET_NAME)
                        .key(OBJECT_KEY)
                , Path.of(FILE_PATH)
        );
        log.info("The object was loaded!!!! ");
    }

    //TODO : Download - Reading a TXT or JSON
    // Better use S3Template for unmarshall and manipulate JSON to POJO
    public void getObject() throws IOException {
        InputStream resource = s3Client.getObject(builder -> builder
                .bucket(BUCKET_NAME)
                .key("messageTest.txt")
        );

        log.info("The content of this object : {}", new String(resource.readAllBytes()));
    }

    //TODO : Download Object on file
    public void downloadObject() throws IOException {

        String DOWNLOAD_FILE_PATH = "./AWS-Integration/AWS-download/";
        String DOWNLOAD_FILE_NAME = "download.jpg";
        File file = new File(DOWNLOAD_FILE_PATH + DOWNLOAD_FILE_NAME);
        //Validate if path exist and create it
        if (!file.getParentFile().exists()) {
            System.out.println(file.getParentFile().getAbsolutePath());
            Files.createDirectories(Paths.get(file.getParentFile().getPath()));
        }

        InputStream resource = s3Client.getObject(request -> request
                .key("mona.jpg")
                .bucket(BUCKET_NAME)
        );

        resource.transferTo(new FileOutputStream(DOWNLOAD_FILE_PATH+DOWNLOAD_FILE_NAME));
        log.info("The resource was downloaded!!");

    }

    //TODO: Download whit s3TransferManager
    //
    @PostConstruct
    public void downloadDirectory() throws IOException {
        String DOWNLOAD_FILE_PATH = "./AWS-Integration/AWS-download/";
        String DOWNLOAD_FILE_NAME = "download.jpg";
        File file = new File(DOWNLOAD_FILE_PATH + DOWNLOAD_FILE_NAME);

        //Validate if path exist and create it
        if (!file.getParentFile().exists()) {
            System.out.println(file.getParentFile().getAbsolutePath());
            Files.createDirectories(Paths.get(file.getParentFile().getPath()));
        }

        DirectoryDownload directoryDownload = s3TransferManager.downloadDirectory(builder -> builder
                .bucket(BUCKET_NAME)
                .destination(Paths.get(""))
                .listObjectsV2RequestTransformer(request -> request
                                .prefix("carpeta1")
                        )
                //.filter(s3Object -> s3Object.key().contains("default"))
                .build());

        // Wait for the transfer to complete
        CompletedDirectoryDownload completedDirectoryDownload = directoryDownload.completionFuture().join();

        // Print out any failed downloads
        completedDirectoryDownload.failedTransfers().forEach(System.out::println);


    }

    //Get All bucketS
    public void listBuckets() {
        int listOfBucketsCount = s3Client.listBuckets().buckets().size();
        s3Client.listBuckets()
                .buckets()
                .stream()
                .map(Bucket::name)
                .toList()
                .forEach(System.out::println);

        System.out.println(listOfBucketsCount);
    }

    //Delete Object
    public void deleteObject() {
        String KEY_OBJECT_DELETE = "mona.zip";

        try {
            log.info("Trying to delete {} from bucket {}", KEY_OBJECT_DELETE, BUCKET_NAME);
            s3Client.deleteObject(
                    builder -> builder
                            .bucket(BUCKET_NAME)
                            .key(KEY_OBJECT_DELETE)
                            .build()
            );
            log.info("The object {} was deleted!!", KEY_OBJECT_DELETE);
        }catch (S3Exception e) {
            log.error(e.getMessage());
        }finally {
            log.info("The S3 Client is closing!!");
            s3Client.close();
        }
    }

    //Delete Objects
    public void deleteObjects() {
        List<ObjectIdentifier> toDelete = new ArrayList<>();
        toDelete.add(ObjectIdentifier.builder().key("messageTest.txt").build());
        toDelete.add(ObjectIdentifier.builder().key("MonaLisa.jpg").build());
        try {
            log.info("Trying to delete objects");
            s3Client.deleteObjects(builder -> builder
                    .bucket("dizstance-testing-bucket")
                    .delete(deleteBuilder -> deleteBuilder
                            .objects(toDelete)
                            .build())
            );
            log.info("Objects deleted succesfully");
        } catch (S3Exception e) {
            log.error(e.getMessage());
        } finally {
            log.info("The S3 Client is closing!!");
            s3Client.close();
        }

    }
}
