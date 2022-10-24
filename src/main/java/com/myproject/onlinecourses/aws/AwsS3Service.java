package com.myproject.onlinecourses.aws;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@PropertySource("classpath:awsservice.properties")
public class AwsS3Service {

    @Autowired
    Environment environment;

    @Value("${amazonProperties.videoBucket.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.videoBucket.secretKey}")
    private String secretKey;

    @Value("${amazonProperties.videoBucket.bucketName}")
    private String bucketVideo;

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String uploadFileTos3bucket(String fileName, File file) {
        String accessKey = environment.getProperty("amazonProperties.videoBucket.accessKey");
        String secretKey = environment.getProperty("amazonProperties.videoBucket.secretKey");
        String bucketName = environment.getProperty("amazonProperties.videoBucket.bucketName");

            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3client = new AmazonS3Client(credentials);
            s3client.setRegion(Region.getRegion(Regions.US_EAST_1));
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
            request.withCannedAcl(CannedAccessControlList.PublicRead);

            s3client.putObject(request);
            return s3client.getUrl(bucketName, fileName).toString();
    }


    public String uploadSingleFile(String fileName, MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            fileUrl = uploadFileTos3bucket(fileName, file);
            file.delete();
        }
        catch (AmazonS3Exception ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fileUrl;
    }

    public boolean deleteObjectInBucket(String bucketName, String filename){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        AmazonS3 s3client = new AmazonS3Client(credentials);
        s3client.setRegion(Region.getRegion(Regions.US_EAST_1));

        try{
            s3client.deleteObject(bucketName, filename);
            return true;
        }
        catch(AmazonS3Exception e){
            return false;
        }
    }

}
