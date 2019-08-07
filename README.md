# FileUpload-API

The API automation of the Rescale FileUpload is automated in this framework.

The technologies used include Java, TestNG, Maven and RestAssured for API tests. I have also used  Java Logger to log the console outputs.
This framework is designed with the idea of having different classes for different API. Considering this is only one API call for the uploadFile api and one to list all files for a user, I have coded both in the same class.

## How to run the tests:
1. Clone the repository and import in your favorite IDE
2. After importing, right click on testng.xml file -> Run As -> TestNG Suite.

The Testng.xml file maintains the list of files to be tested in a preserved order, the tests are run in that specific order. 

The api endpoint /files/ which is used to list the files for specific user is throwing Gateway Timeout Error hence unable to complete the testing. 

## APIUploadFile class:

1. The class APIUploadFile.java, primarily imports the RestAssured and TestNG methods.
2. The class has a method called **'uploadFile'** which is a post method using the endPoint files/contents/. This post method takes the request header including the content-type and the API key along with the path to the file to be uploaded passed as an argument in multiPart method of RestAssured.  Once the file is uploaded, I parse the response and assert that the "owner" of the response json contains the expected email address and the status code equals to 201.
3. The other method **'verifyFileUpload'** verifies that the uploaded file is listed in the list of files for the specific owner. This hits the endPoint 'files/' and gets the list of files specific to that owner. Once the list is obtained, an assert statement verifies that the file exists or not. This method currently throws **Gateway Timeout Error** and hence I am unable to code this completely and test the expected results. 

Currently the API automation tests the happy path for the file upload. Below is the list of other test cases that I would test for the APIs. 

## Other test cases:

1. Test different types of files and check the response time. Currently the response type for a 13 byte file is close to 3sec which is very slow. Consider uploading huge files and check the response time.
2. Get the id of the file that is uploaded from the response Json and check whether is downloadable. 
3. Get the list of files for the specific owner and check that the file uploaded is listed in the response. 
4. Parse the Json response and test the owner, size and file name and type matches with what is uploaded. 
5. Delete the file using the API and get the lsit of files for the specific user. The file deleted should not be listed
6. Get the list of files for the specific user and test the response properties like count, Previous, next and results. 
7. Check if there is a limit for the total number of files and storage allocated for a user, and try to insert more files above the allocated limit and check the API tests are failing. 
8. Get the content of the file using API and test its break point with a really huge file.
9. Concurrently upload 100s of files and check the performance of the API. 
10. **Stress test** the API using tools like JMeter, Gatlang and identify the limit that the server can handle. Test both in basic and enhanced mode and check the different n response time. The enhanced method shouldbe faster than the basic
11. Test the **availability** of the server by taking one of the nodes down and check whether the ELB spins up automatically making sure that the application is available 24/7.
12. Test the **security** of the API using different API token and check for the failures. 
13. Verify the security certificate for the API and verify that the tests fail for invalid certificates. 
14. Check the port in which the API run, it should run on the secured port 443 and not on 8080 which is insecure.
15. Test the rules for the password generation tool and make sure that is tough for hackers to get in


