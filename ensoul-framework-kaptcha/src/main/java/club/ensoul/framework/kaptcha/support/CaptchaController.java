package club.ensoul.framework.kaptcha.support;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * 图片验证码（支持算术形式）
 *
 * @author chpen
 */
@Controller
@Api(value = "验证码")
@RequestMapping("/captcha")
public class CaptchaController {

    private final Producer captchaProducer;

    private final Producer captchaProducerMath;

    @Autowired
    public CaptchaController(Producer captchaProducer, Producer captchaProducerMath) {
        this.captchaProducer = captchaProducer;
        this.captchaProducerMath = captchaProducerMath;
    }

    /**
     * 验证码生成
     */
    @GetMapping(value = "/captchaImage")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) {

        try (ServletOutputStream out = response.getOutputStream()) {

            HttpSession session = request.getSession();
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            String type = request.getParameter("type");
            String code = null;
            BufferedImage bi = null;
            if ("math".equals(type))//验证码为算数 8*9 类型
            {
                String capText = captchaProducerMath.createText();
                String capStr = capText.substring(0, capText.lastIndexOf("@"));
                code = capText.substring(capText.lastIndexOf("@") + 1);
                bi = captchaProducerMath.createImage(capStr);
            } else if ("char".equals(type))//验证码为 abcd类型
            {
                String capStr = code = captchaProducer.createText();
                bi = captchaProducer.createImage(capStr);
            }

            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
            ImageIO.write(Objects.requireNonNull(bi), "jpg", out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}