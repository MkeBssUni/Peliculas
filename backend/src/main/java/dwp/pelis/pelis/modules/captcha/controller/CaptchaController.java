package dwp.pelis.pelis.modules.captcha.controller;

import dwp.pelis.pelis.modules.captcha.model.CaptchaResponseDto;
import dwp.pelis.pelis.modules.captcha.service.CaptchaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinema/captcha")
@CrossOrigin(
        origins = {"*"},
        methods = {RequestMethod.POST, RequestMethod.OPTIONS} //INDICA LOS METODOS QUE SE VAN A PERMITIR
)
@AllArgsConstructor
public class CaptchaController {
    private CaptchaService captchaService;

    @PostMapping("/verify")
    public CaptchaResponseDto verifyCaptcha(@RequestParam("solution") String solution){
        return captchaService.verifyCaptcha(solution);
    }
}
