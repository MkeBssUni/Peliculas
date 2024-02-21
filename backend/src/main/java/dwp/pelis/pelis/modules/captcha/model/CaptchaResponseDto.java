package dwp.pelis.pelis.modules.captcha.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CaptchaResponseDto {
    private boolean success;
    private List<String> erros;
}
