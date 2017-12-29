# Java SDK (unofficial) for Seletel Cloud Storage API [![Build Status](https://travis-ci.org/pelenthium/selectel-storage-client.svg?branch=master)](https://travis-ci.org/pelenthium/selectel-storage-client)

Latest Seletel Cloud Storage API docs : https://kb.selectel.ru/22058988.html

# 1. Prerequisites
 - [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
 - [gradle 3.*](https://gradle.org/)

# 2. Latest release
 
Latest version: [ ![Download](https://api.bintray.com/packages/pelenthium/maven/selectel-storage-client/images/download.svg) ](https://bintray.com/pelenthium/maven/selectel-storage-client/_latestVersion)

To add a dependency using Maven <details><summary>How to setup Maven to use bintray repository</summary>

You need to create ~/.m2/settings.xml with this code

```xml
 <?xml version="1.0" encoding="UTF-8" ?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd'
          xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
   
    <profiles>
        <profile>
            <repositories>
                <repository>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                    <id>bintray-pelenthium-maven</id>
                    <name>bintray</name>
                    <url>https://dl.bintray.com/pelenthium/maven</url>
                </repository>
            </repositories>
            <pluginRepositories>
                <pluginRepository>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                    <id>bintray-pelenthium-maven</id>
                    <name>bintray-plugins</name>
                    <url>https://dl.bintray.com/pelenthium/maven</url>
                </pluginRepository>
            </pluginRepositories>
            <id>bintray</id>
        </profile>
    </profiles>
    <activeProfiles>
        <activeProfile>bintray</activeProfile>
    </activeProfiles>
</settings>
 ```
</details>

```xml
        <dependency>
          <groupId>com.github.pelenthium</groupId>
          <artifactId>selectel-storage-client</artifactId>
          <version>LATEST_VERSION</version>
          <type>pom</type>
        </dependency>
```
To add a dependency using Gradle:
    
```groovy
    repositories {
        mavenUrl 'https://dl.bintray.com/pelenthium/maven'
    }
    compile 'com.github.pelenthium:selectel-storage-client:LATEST_VERSION'
```

# 3. Prepare for using

First of all you need to sign up in [selectel.ru](https://selectel.ru/services/cloud/storage/?ref_code=3267936d99)

# 4. Logging

See (slf4j)[http://www.slf4j.org/manual.html#swapping]

# 5. Initialization and Authorization

Authorization is initialization step

```java
    SelectelClient client = SelectelClientBuilder.create()
                    .authorize(username, password)
                    .build();
 ```
This code initializes and authorize with your credential



# 6. API Requests


