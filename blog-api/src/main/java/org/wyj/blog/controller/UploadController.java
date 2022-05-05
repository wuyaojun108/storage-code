package org.wyj.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.wyj.blog.utils.MultipartFileResource;
import org.wyj.blog.vo.Result;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${imageServerUrl}")
    private String imageServerUrl;

    @PostMapping
    public Result upload(MultipartFile image
            , HttpServletRequest request) throws IOException {
        if (image == null) {return Result.fail(-999, "上传文件不能为空");}
        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("content-type", request.getHeader("content-type"));
        // 构建请求体
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("image", new MultipartFileResource(image));
        // 发送上传请求
        HttpEntity<MultiValueMap<String, Object>> httpEntity
                = new HttpEntity<>(requestBody, requestHeaders);
        ResponseEntity<String> responseEntity
                = restTemplate.postForEntity(imageServerUrl, httpEntity, String.class);
        return Result.success(responseEntity.getBody());
    }

}
