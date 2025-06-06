package cc.mrbird.febs.cos.controller;

import cc.mrbird.febs.common.utils.FileUtil;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.FaceRecognition;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 人脸识别
 */
@RestController
@RequestMapping("/cos/face")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FaceRecognitionController {

    private final FaceRecognition faceRecognition;

    private final IUserInfoService ownerInfoService;


    /**
     * 人脸注册
     *
     * @param file  图片
     * @param name 名称
     * @return
     */
    @PostMapping("/registered")
    public R registered(@RequestParam("avatar") MultipartFile file, @RequestParam("name") String name, @RequestParam("ownerId") Integer ownerId) throws IOException {
        BASE64Encoder base64Encoder =new BASE64Encoder();
        String base64EncoderImg = base64Encoder.encode(file.getBytes());
        String result = faceRecognition.registered(base64EncoderImg, name);
        if ("success".equals(result)) {
            String localPath="G:/Project/停车场管理系统/db";
            String fileName=file.getOriginalFilename();
            String newFileName = FileUtil.upload(file, localPath, fileName);
            ownerInfoService.update(Wrappers.<UserInfo>lambdaUpdate().set(UserInfo::getUserImages, newFileName).eq(UserInfo::getId, ownerId));
        }
        return R.ok(result);
    }

    /**
     * 人脸搜索
     *
     * @param file 图片
     * @return
     */
    @PostMapping("/verification")
    public R verification(@RequestParam("file") String file) throws IOException {
//        BASE64Encoder base64Encoder =new BASE64Encoder();
//        String base64EncoderImg = base64Encoder.encode(file.getBytes());

        String result = faceRecognition.verification(file);
        if ("error".equals(result)) {
            return R.ok("人脸识别未通过！");
        } else {
            return R.ok("成功");
        }
    }

    /**
     * test
     * @param text
     * @return
     */
    @PostMapping("/sendFile")
    public R test(String text) throws IOException {
        BASE64Encoder base64Encoder =new BASE64Encoder();
        String fileList = JSONUtil.parseObj(text).get("fileName").toString();
        String path = JSONUtil.parseObj(text).get("path").toString();
        // 识别人脸信息
        for (String s : fileList.split(",")) {
            System.out.println("=====>"+s);
            if (".jpg".equals(s.substring(s.length() - 4))) {
                System.out.println(path + s);
                File file = new File("D:/saber/new.jpg");
                FileInputStream inputStream = new FileInputStream(file);
                MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                        ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                String result = faceRecognition.verification(base64Encoder.encode(multipartFile.getBytes()));
                if (!"error".equals(result)) {
                    System.out.println("识别成功=====>   "+result);
                    return R.ok(result);
                }
            }
        }
        return R.ok(true);
    }

    /**
     * 人脸检测
     *
     * @param img 图片Base64码
     */
    @RequestMapping("/faceDetection")
    public R faceDetection(String img) {
        return R.ok(faceRecognition.faceDetection(img));
    }
}
