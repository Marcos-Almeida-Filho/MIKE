/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AWS;

/**
 *
 * @author Marcos Filho
 */
public class UploadObject {
    
//    public static void main(String[] args) throws IOException {
//        Regions clientRegion = Regions.DEFAULT_REGION;
//        String bucketName = "*** Bucket name ***";
//        String stringObjKeyName = "*** String object key name ***";
//        String fileObjKeyName = "*** File object key name ***";
//        String fileName = "*** Path to file to upload ***";
//
//        try {
//            //This code expects that you have AWS credentials set up per:
//            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
//            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                    .withRegion(clientRegion)
//                    .build();
//
//            // Upload a text string as a new object.
//            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");
//
//            // Upload a file as a new object with ContentType and title specified.
//            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType("plain/text");
//            metadata.addUserMetadata("x-amz-meta-title", "someTitle");
//            request.setMetadata(metadata);
//            s3Client.putObject(request);
//        } catch (AmazonServiceException e) {
//            // The call was transmitted successfully, but Amazon S3 couldn't process 
//            // it, so it returned an error response.
//            e.printStackTrace();
//        } catch (SdkClientException e) {
//            // Amazon S3 couldn't be contacted for a response, or the client
//            // couldn't parse the response from Amazon S3.
//            e.printStackTrace();
//        }
//    }
}
