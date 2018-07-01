package com.fr.util.base;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {

    public static String uploadFile(MultipartFile file, String path, String filefinalName) throws IOException {
        String name = file.getOriginalFilename();// 文件上传的真实名称
        String suffixName = name.substring(name.lastIndexOf("."));// 获取后缀名
        // String hash = Integer.toHexString(new Random().nextInt());// 自定义随机数(字母+数字)作为文件名
        String hash = BillNumberBuilder.createImgNum(filefinalName);
        String fileName = hash + suffixName;
        File tempFile = new File(path, fileName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        if (tempFile.exists()) {
            tempFile.delete();
        }
//		try {
//			// 压缩并保存图片
//			// 1图片尺寸不变，修改图片文件类型
//			// Thumbnails.of(file.getInputStream()).scale(1f).outputFormat("jpg").toFile(tempFile);
//			// 2.图片尺寸不变，压缩图片文件大小
//			// Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).outputFormat("jpg").toFile(tempFile);
//		//	Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(tempFile);
//			// 3.压缩至指定图片尺寸（例如：横400高300），不保持图片比例
//			// Thumbnails.of(file.getInputStream()).forceSize(400, 300).toFile(tempFile);
//		} catch (IOException e) {
//			try {
//				// 失败了再用springmvc自带的方式
//				// tempFile.createNewFile();
//				file.transferTo(tempFile);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
        // file.transferTo(tempFile);
        return tempFile.getName();
    }
}
