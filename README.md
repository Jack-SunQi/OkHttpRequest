# OkHttpRequest
a network request library based on okhttp

# Introduction
- This library can be used in android for network request, which includes post, get, downnload, upload, websocket. unlike the original okhttp request, OkhttpRequest transfered the callback result on MainThread, the latest version of okhttp has been integrated as well.
- The request won`t start until you call setCallback, and a IRequest will be retrurn(Except WebSocket), which can be used to cancel the current operation.
- There are two kinds of callback for post and get, StringCallback, GsonCallback,.StringCallback returns a string result on success, GsonCallback returns a entity on success, this entity can be any type.
- You can add specific params according to your needs, not all of them.

# Download
step1:Add it in your root build.gradle at the end of repositories:
step2:Add the dependency

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
	implementation 'com.github.Jack-SunQi:OkHttpRequest:1.0'
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
    <version>1.0.1</version>
</dependency>
```
# Usage

## Post

```
Map<String, String> header = new HashMap<>();

//拿到body的构建器
FormBody.Builder builder = new FormBody.Builder();
//添加参数
builder.add("name", "chs")
		.add("password", "123");
FormBody body = builder.build();

OkHttpManager
	.post()
	.header(header)
	.url("url")
	.params(body)
	.tag("tag")
	.setCallback(new StringCallback() {
		@Override
		public void onSuccess(int code, String result) {

		}

		@Override
		public void onError(Exception e) {

		}

		@Override
		public void onCancel() {

		}
	});
```
## Get
```
OkHttpManager
	.get()
	.url("url")
	.tag("tag")
	.setCallback(new GsonCallback<TestBean>() {
		@Override
		public void onError(Exception e) {

		}

		@Override
		public void onCancel() {

		}

		@Override
		public void onSuccess(int code, TestBean response) {

		}
	});
```

## Upload 
```
File uploadFile = new File("your file path");

Map<String, String> header = new HashMap<>();

Map<String, String> params = new HashMap<>();

IRequest request = OkHttpManager
		.upload()
		.file(uploadFile)
		.url("url")
		.header(header)
		.params(params)
		.setCallback(new UploadCallback() {
			@Override
			public void onProgress(long curr, long total, boolean isDone) {

			}

			@Override
			public void onSuccess() {

			}

			@Override
			public void onError(Exception e) {

			}

			@Override
			public void onCancel() {

			}
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
	.header(header).url("url")
	.tag("tag")
	.setCallback(new DownloadCallback() {
		@Override
		public void onSuccess(File file) {

		}

		@Override
		public void onFailure(Call call, Exception e) {

		}

		@Override
		public void onProgress(long curr, long total) {

		}

		@Override
		public void onCancel() {

		}
	});
```

## WebSocket
```
OkHttpManager
	.webSocket()
	.url("url")
	.listener(new MTWebSocketListener() {
		@Override
		public void onMTOpen(WebSocket webSocket, Response response) {

		}

		@Override
		public void onMTMessage(WebSocket webSocket, String text) {

		}

		@Override
		public void onMTMessage(WebSocket webSocket, ByteString bytes) {

		}

		@Override
		public void onMTClosing(WebSocket webSocket, int code, String reason) {

		}

		@Override
		public void onMTClosed(WebSocket webSocket, int code, String reason) {

		}

		@Override
		public void onMTFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {

		}
	});
```
