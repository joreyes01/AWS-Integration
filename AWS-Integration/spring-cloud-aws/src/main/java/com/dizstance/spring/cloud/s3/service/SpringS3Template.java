package com.dizstance.spring.cloud.s3.service;

import io.awspring.cloud.s3.S3Exception;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.*;
import java.time.Duration;


@Service
@Slf4j
public class SpringS3Template {
    //TODO: CRUD de S3 (Crear bucket, subir arhivo, actualizar archivo, descargar archivo)

    private final S3Template s3Template;
    private static final String BUCKET_NAME = "dizstance-testing-bucket";
    private static final String DOWNLOAD_ROOT_FILE = "./AWS-download/";
    private static final File DOWNLOAD_FILE = new File(DOWNLOAD_ROOT_FILE);

    public SpringS3Template(S3Template s3Template) {
        this.s3Template = s3Template;
    }

    //TODO : CREATE
    public void createS3Bucket() {
        String bucketName = "dizstance-test-bucket";
        //log.info("name bucket creating : " + bucketName);
        s3Template.createBucket(bucketName);
    }

    //TODO: GET
    // Using S3Template - Download
    //@PostConstruct
    public void downloadS3Object() throws IOException {
        String OBJECT_KEY = "mona.zip";
        S3Resource s3Resource = s3Template.download(BUCKET_NAME, OBJECT_KEY);
        s3Resource.getInputStream().transferTo(new FileOutputStream(DOWNLOAD_ROOT_FILE+OBJECT_KEY));
        log.info("The object was downloaded - name : {} and the content type : {}", s3Resource.getFilename(), s3Resource.contentType());
        System.out.println("URL : "+s3Template.createSignedGetURL(BUCKET_NAME, OBJECT_KEY, Duration.ofMinutes(2)));
    }

    //TODO: GET
    // URL Donwload - S3 Template createSignedGetUrl

    //TODO : Update - Using S3Template

    public void putObject() throws FileNotFoundException {

        InputStream inputStream;

        //From String
        //String messageTest = "This ís @@@a message test for í s3";
        //inputStream= new ByteArrayInputStream(messageTest.getBytes());

        //From File
        File file = new File("./AWS/MonaLisa.zip");
        inputStream = new FileInputStream(file);

        //TODO: How implement ZipInputStream? - Not working 0B on S3
        S3Resource s3Resource = s3Template
                .upload(
                        BUCKET_NAME
                        , "mona.zip"
                        , inputStream
                );
        //S3Resource s3Resource = s3Template.store(BUCKET_NAME, "messageTest.txt", messageTest);


        log.info("The object was loaded - name : {} and the content type : {}", s3Resource.getFilename(), s3Resource.contentType());
    }


    //TODO: Using S3Template - STORE
    // S3Template also allows storing & retrieving Java objects as JSON
    //@PostConstruct
    public void storeJsonToS3() {
        //Default storing contentType JSON
        S3Resource s3Resource = s3Template.store(BUCKET_NAME, "messageTest.txt", "Default");

        log.info("The object was loaded - name : {} and the content type : {}", s3Resource.getFilename(), s3Resource.contentType());
    }


    //@PostConstruct
    public void deleteObject() {
        try {
            s3Template.deleteObject(BUCKET_NAME, "MonaLisa.jpg");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void listBuckets(){

    }
}
