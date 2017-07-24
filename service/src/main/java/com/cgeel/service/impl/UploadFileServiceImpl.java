package com.cgeel.service.impl;

import com.cgeel.model.UploadFile;
import com.cgeel.persistence.UploadFileMapper;
import com.cgeel.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private UploadFileMapper uploadFileMapper;

    @Override
    @Transactional
    public UploadFile addUploadFile(String fileName, String dir, String oldName) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setName(oldName);
        uploadFile.setDir(dir);
        uploadFile.setState(10);
        uploadFile.setPath(fileName);
        uploadFile.setCreateTime(System.currentTimeMillis() / 1000);
        uploadFileMapper.insert(uploadFile);
        return uploadFile;
    }

}
