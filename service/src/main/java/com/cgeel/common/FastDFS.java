package com.cgeel.common;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ZXW on 2015/3/19.
 */
public class FastDFS implements InitializingBean {

    private String resource;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public static String upload(String fileName, byte[] file, Map<String, String> nameValuePair) throws IOException {
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        try {
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            List<NameValuePair> list = new ArrayList<>();
            list.add(new NameValuePair("fileName", fileName));
            if (nameValuePair != null && nameValuePair.size() > 0) {
                for (String key : nameValuePair.keySet()) {
                    list.add(new NameValuePair(key, nameValuePair.get(key)));
                }
            }
            String ext = null;
            if(fileName != null){
                int index = fileName.lastIndexOf(".");
                if(index > 0){
                    ext = fileName.substring(index + 1);
                }
            }
            try {
                return client.upload_file1(file, ext, list.toArray(new NameValuePair[list.size()]));
            } catch (MyException e) {
                e.printStackTrace();
                throw new IOException("上传失败");
            }
        }finally {
            if(trackerServer != null) {
                trackerServer.close();
            }
        }
    }

    public static String upload(String fileName, String filePath, Map<String, String> nameValuePair) throws IOException {
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        try {
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            List<NameValuePair> list = new ArrayList<>();
            list.add(new NameValuePair("fileName", fileName));
            if (nameValuePair != null && nameValuePair.size() > 0) {
                for (String key : nameValuePair.keySet()) {
                    list.add(new NameValuePair(key, nameValuePair.get(key)));
                }
            }
            String ext = null;
            if(fileName != null){
                int index = fileName.lastIndexOf(".");
                if(index > 0){
                    ext = fileName.substring(index + 1);
                }
            }
            try {
                return client.upload_file1(filePath, ext, list.toArray(new NameValuePair[list.size()]));
            } catch (MyException e) {
                e.printStackTrace();
                throw new IOException("上传失败");
            }
        }finally {
            if(trackerServer != null) {
                trackerServer.close();
            }
        }
    }

    public static byte[] download(String fileId) throws IOException {
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        try {
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            try {
                return client.download_file1(fileId);
            } catch (MyException e) {
                e.printStackTrace();
                throw new IOException("下载失败");
            }
        }finally {
            if(trackerServer != null) {
                trackerServer.close();
            }
        }
    }

    public static int delete(String fileId) throws IOException {
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        try {
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            try {
                return client.delete_file1(fileId);
            } catch (MyException e) {
                e.printStackTrace();
                throw new IOException("删除失败");
            }
        }finally {
            if(trackerServer != null) {
                trackerServer.close();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(resource != null){
            try {
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resolver.getResources(resource);
                Assert.isTrue(resources.length > 0);
                Resource resource = resources[0];
                ClientGlobal.init(resource.getURL().getFile());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MyException e) {
                e.printStackTrace();
            }
        }else{
            throw new IllegalArgumentException("Property \'resource\' is required");
        }
    }
}
