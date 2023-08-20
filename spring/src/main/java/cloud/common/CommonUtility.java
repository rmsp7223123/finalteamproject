package cloud.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import cloud.member.MemberVO;

@Service
public class CommonUtility {
	// http://192.168.0.87:8080/cloud/imgs/profileImage/ansqudwns12.jpg
	// rootPath/profileImage/ansqudwns12.jpg
	public static String rootPath = "D:\\finalteamproject\\images\\";
//	public static String rootPath = "http://192.168.0.87:8080/cloud/images/";
	

	// 파일 업로드
	public String fileUpload(String category, MultipartFile file, HttpServletRequest req) {

		String path = rootPath + category;

		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		try {
			file.transferTo(new File(path, filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return generateFileURL(req, category, filename);
	}

	private String generateFileURL(HttpServletRequest req, String category, String filename) {
		String baseUrl = req.getContextPath();
		return  "http://" + req.getLocalAddr() + ":"+ req.getLocalPort()  +  baseUrl + "/imgs/" + category + "/" + filename;
	}

	// 파일 삭제 메서드
	public void deleteFile(String filePath) {
		if (filePath != null) {
			String newData = filePath.replaceAll("http://192.168.000.000:8080/cloud/imgs/", "");
			File file = new File(rootPath + newData);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	// 파일 업로드 및 기존 이미지 삭제
	public String uploadAndDeletePreviousImage(String category, MultipartFile file, HttpServletRequest req,
			String previousImagePath) {
		// 기존 이미지 삭제
		deleteFile(previousImagePath);

		// 파일 업로드
		String newImagePath = fileUpload(category, file, req);
		return newImagePath;
	}
}
