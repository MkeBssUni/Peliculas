package dwp.pelis.pelis.modules.captcha.service;

import dwp.pelis.pelis.modules.captcha.model.CaptchaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class CaptchaService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${captcha.site.key}")
    private String siteKey;
    @Value("${captcha.api.key}")
    private String captchaKey;
    public CaptchaResponseDto verifyCaptcha(String solution) {
        String url= "https://api.friendlycaptcha.com/api/v1/siteverify";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //Crear el cuerpo de la solicitud

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("solution", solution);
        requestBody.put("secret", captchaKey);
        requestBody.put("sitekey",siteKey);

        HttpEntity<Map<String,String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<CaptchaResponseDto> responseEntity = restTemplate.postForEntity(
                url, requestEntity, CaptchaResponseDto.class);
        return responseEntity.getBody();
    }
}
