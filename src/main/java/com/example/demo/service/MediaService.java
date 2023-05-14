package com.example.demo.service;

import com.example.demo.model.FileMetadata;

import java.io.File;

public interface MediaService {


    FileMetadata uploadFile(File file, String userName);
}
