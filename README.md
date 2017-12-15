# Java API implementation for Seletel Cloud Storage [![Build Status](https://travis-ci.org/pelenthium/selectel-storage-client.svg?branch=master)](https://travis-ci.org/pelenthium/selectel-storage-client)

Latest API docs : https://kb.selectel.ru/22058988.html

How to use :
 - create SelectelClient by SelectelClientBuild
 - call to client.execute(command)
 ```java
    SelectelClient client = SelectelClientBuilder.create()
                    .authorize(username, secret)
                    .bucket(bucket)
                    .build();
    UploadResponse response = client.execute(new Upload(new File(file), "/test/test1.txt"));
 ```
 
 