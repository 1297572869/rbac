package com.cgeel.controller;

import com.cgeel.common.FastDFS;
import com.cgeel.common.utils.image.ImageUtils;
import com.cgeel.model.UploadFile;
import com.cgeel.service.UploadFileService;
import com.cgeel.utils.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FileUploadController {

	@Autowired
	private UploadFileService uploadFileService;

	// 缩略图默认裁剪尺寸
	private final int DEAFAULT_WIDTH = 200;
	private final int DEAFAULT_HEIGHT = 200;
	// 缩略图后缀
	private final String SUFFIX = "_TN";

	@Autowired
	@Qualifier(value = "mediaDomain")
	private String mediaDomain;


	@RequestMapping(value = "/upload_file.do", method = RequestMethod.POST)
	@ResponseBody
	public RestResult uploadFile(MultipartFile uploadFile, String dir) {
		try {
			String newFileName = null;
			if(uploadFile.getContentType().indexOf("image") != -1){
				newFileName = FastDFS.upload(uploadFile.getOriginalFilename(), ImageUtils.pngToJpg(uploadFile), null);
			}
			if(uploadFile.getContentType().indexOf("video") != -1){
				newFileName = FastDFS.upload(uploadFile.getOriginalFilename(),uploadFile.getBytes(), null);
			}

			if(dir==null||dir.equals("")){
				dir="icon";
			}

			UploadFile uploadFile1 = uploadFileService.addUploadFile(newFileName, dir, uploadFile.getOriginalFilename());
			Map<String, Object> map = new HashMap<>();
			map.put("path", mediaDomain + uploadFile1.getPath());
			map.put("fileName", uploadFile.getOriginalFilename());
			map.put("uploadFileId", uploadFile1.getId());
			return RestResult.SUCCESS().put("uploadFile", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RestResult.ERROR_500();
	}


	@RequestMapping(value = "/upload_fileBanner.do", method = RequestMethod.POST)
	@ResponseBody
	public RestResult upload_fileBanner(MultipartFile uploadFile, String dir) {
		try {

			byte[] fileByte = compressionImage(uploadFile.getBytes());
			String originalFileName = uploadFile.getOriginalFilename();
			if(fileByte==null){

				return RestResult.ERROR_500();
			}

			String newFileName = null;
			if(uploadFile.getContentType().indexOf("image") != -1){
				newFileName = FastDFS.upload(originalFileName, ImageUtils.pngToJpg(uploadFile), null);
			}
			if(uploadFile.getContentType().indexOf("video") != -1){
				newFileName = FastDFS.upload(originalFileName,uploadFile.getBytes(), null);
			}
			if(uploadFile.getContentType().indexOf("zip") != -1){
				newFileName = FastDFS.upload(originalFileName,uploadFile.getBytes(), null);
			}
			if(dir==null||dir.equals("")){
				dir="icon";
			}
			UploadFile uploadFile1 = uploadFileService.addUploadFile(newFileName, dir, originalFileName);
			Map<String, Object> map = new HashMap<>();
			map.put("path", mediaDomain + uploadFile1.getPath());
			map.put("fileName", uploadFile.getOriginalFilename());
			map.put("uploadFileId", uploadFile1.getId());
			return RestResult.SUCCESS().put("uploadFile", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RestResult.ERROR_500();
	}



	/**
	 * description:  图片压缩
	 * @param source
	 * @return 返回压缩后的字节流
	 *
	 * @author don
	 * @date 2015年12月7日 下午2:45:28
	 */
	public byte[] compressionImage(byte[] source) {
		// 碎片图片，最大不超过500K，1280*720
		if (source.length > ImageUtils.MAX_IMAGE_SIZE_PROJECT) {
			return ImageUtils.resize(source, 1920, 1920);
		}
		return source;
	}

	public byte[] compressionImageRecommend(byte[] source) {
		// 碎片图片，最大不超过500K，1280*720
		if (source.length > ImageUtils.MAX_IMAGE_SIZE_PROJECT) {
			return ImageUtils.resize(source, ImageUtils.RECOMMEND_WIDTH, ImageUtils.RECOMMEND_HEIGHT);
		}
		return source;
	}





}
