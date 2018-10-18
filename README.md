# OkHttpRequest
a network request library based on okhttp

# Introduction
- This library can be used in android for network request, which includes post, get, downnload, upload, websocket. unlike the original okhttp request, OkhttpRequest transfered the callback result on MainThread, the latest version of okhttp has been integrated as well.
- The request won`t start until you call setCallback, and a IRequest will be retrurn(Except WebSocket), which can be used to cancel the current operation.
- There are two kinds of callback for post and get, StringCallback, GsonCallback,.StringCallback returns a string result on success, GsonCallback returns a entity on success, this entity can be any type.
- You can add specific params according to your needs, not all of them.

# Download
- step1:Add it in your root build.gradle at the end of repositories:
- step2:Add the dependency

## Gradle:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
```
dependencies {
	implementation 'com.github.Jack-SunQi:OkHttpRequest:1.0.2'
}
```
## Maven:
```
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
```
<dependency>
    <groupId>com.github.Jack-SunQi</groupId>
    <artifactId>OkHttpRequest</artifactId>
    <version>1.0.2</version>
</dependency>
```
# Usage

## Post

```
Map<String, String> header = new HashMap<>();

OkHttpManager
	.post()
	.header(header)
	.url("url")
	.params("key", "value")
	.params("key1", "value1")
	.tag("tag")
	.setCallback(new StringCallback() {
		...
	});
```
## Get
```
OkHttpManager
	.get()
	.url("url")
	.tag("tag")
	.setCallback(new GsonCallback<TestBean>() {
		...
	});
```

## Upload 
```
File uploadFile = new File("your file path");

Map<String, String> header = new HashMap<>();

IRequest request = OkHttpManager
		.upload()
		.file(uploadFile)
		.url("url")
		.header(header)
		.params("key", "value")
		.params("key1", "value1")
		.setCallback(new UploadCallback() {
			...
		});

//        request.cancel(); 
```
## Download
```
Map<String, String> header = new HashMap<>();

OkHttpManager
	.download()
	.fileName("FileName")
	.savePath("savePath")
	.header(header)
	.url("url")
	.tag("tag")
	.setCallback(new DownloadCallback() {
		...
	});
```

## WebSocket
```
OkHttpManager
	.webSocket()
	.url("url")
	.listener(new MTWebSocketListener() {
		...
	});
```
