package com.onrender.navkolodozvillya.media;

import java.io.InputStream;

public interface BinaryStorageClient {

    String upload(InputStream inputStream);
    String download(String key);
}
