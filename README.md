# Gradle exhibitor plugin
[![Build Status](https://travis-ci.org/vadeg/gradle-exhibitor-plugin.svg?branch=master)](https://travis-ci.org/vadeg/gradle-exhibitor-plugin)

## Overview

Gradle plugin which allows to upload data to Zookeeper using Exhibitor Rest API.

## Requirements

* Java 1.8 or above
* Gradle 4.0

# How to use

## Install plugin

Install using plugins section 
```
plugins {
  id "com.github.vadeg.exhibitor" version "1.0.0"
}
```

or using `apply` mechanism
```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.github.vadeg:gradle-exhibitor-plugin:1.0.0"
  }
}

apply plugin: "com.github.vadeg.exhibitor"

```

## Create exhibitor script

Script is a sample script with exhibitor command lines. Each command line contains command, path and value separated by whitespaces.  

#### Script structure
```
<command> <path> <value>
```

- `command` can be `create` or `delete`
- `path` can be any path in zookeeper tree
- `value` is optional and can be any value accepted by zookeeper. Value can be enclosed in `"double quotes".`

#### Example
```
create a/b/c 123
create test-string hello world
create test-string "hello world"
delete a/b
#This is comment line example
```

## Configure plugin

Add `exhibitor` extension to build script
```
exhibitor {
    url =  "http://localhost:8181"
    files = ['sample.script']
}
```
- `url` is an exhibitor http url
- `files` is a list of script file names related to project root.

## Apply script
```
./gradlew updateExhibitor
```

This task will apply commands from script files using Exhibitor API. 

# Development

## How to build
```
./gradlew clean build
```

## How to run tests

```
./gradlew clean test
```

## How to run integration tests

The following command will run unit tests and integration tests

```./gradlew clean integrationTest```


