# MapDirectionApiWithRetrofit
Google map direction api easy implementation

![Screenshoots](https://github.com/cyclicsoft/MapDirectionApiWithRetrofit/blob/master/screenshots.png)

# 1. Dependencies to add
```java
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.google.maps.android:android-maps-utils:2.2.2'
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
```

# 2. Insert your api key at strings.xml

```java
        <string name="api_key">YOUR_KEY_HERE</string>
```

3. Add metadata to Manifest
```java
         <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="YOUR_KEY_HERE" />
```



