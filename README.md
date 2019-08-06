# mvvm
build

  //Life cycle
    implementation "android.arch.lifecycle:extensions:1.1.1"
    implementation "android.arch.lifecycle:runtime:1.1.1"
    annotationProcessor "android.arch.lifecycle:compiler:1.1.1"
    implementation 'com.android.support:recyclerview-v7:28.0.0'

    //Retrofit dependencies
    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    implementation 'com.google.code.gson:gson:2.8.2'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.8.0'
    implementation 'com.squareup.okhttp3:okhttp:3.9.1'
    
    
    apply plugin: 'kotlin-kapt'



<uses-permission android:name="android.permission.INTERNET"/>
