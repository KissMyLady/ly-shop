package top.mylady.upload.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.mylady.upload.service.UploadService;


@RestController
@RequestMapping("/upload")
public class UploadCtrl {

    @Autowired
    private UploadService uploadService;

    /**
     * 调用FastDFS, 上传图片类型, 返回string字符串url
     * Postman测试: Body => form-data file:binary
     */
    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){
        //调用上传模块
        String url = uploadService.uploadImage(file);

        if (url == null){
            return new ResponseEntity<>("null, upload error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }

    @GetMapping("/ok")
    public String test(){
        return "测试成功, 您看到这个返回消息是因为正确的运行了";
    }
}
