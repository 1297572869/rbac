package com.cgeel.service;

import com.cgeel.model.UploadFile;

public interface UploadFileService {

    public UploadFile addUploadFile(String fileName, String dir, String oldName);

}
