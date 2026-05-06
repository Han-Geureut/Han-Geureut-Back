package hangeureut.global.security.oauth.handler;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import hangeureut.domain.user.repository.UserRepository;
import hangeureut.global.security.jwt.JwtService;
import hangeureut.global.security.oauth.CustomOAuth2User;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger log = LogManager.getLogger(OAuth2LoginSuccessHandler.class);

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            log.info("OAuth2 Login Success :: Login ID = {}", oAuth2User.getLoginId());
            log.info("Current User Role : {}", oAuth2User.getRole());
            loginSuccess(response, oAuth2User);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getLoginId());
        String refreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(oAuth2User.getLoginId(), refreshToken);

        Long userId = userRepository.findByLoginId(oAuth2User.getLoginId())
                .map(user -> user.getId())
                .orElse(null);

        String redirectUrl = "https://hangrt.site/oauth/callback"
                + "?access_token=" + accessToken
                + "&refresh_token=" + refreshToken
                + (userId != null ? "&user_id=" + userId : "");

        response.sendRedirect(redirectUrl);
    }
}