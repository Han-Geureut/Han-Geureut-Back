package hangeureut.global.config;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String root() {
        return """
            <!DOCTYPE html>
            <html lang="ko">
            <head>
                <meta charset="UTF-8">
                <title>HanGeureut API</title>
                <style>
                    body { font-family: monospace; background: #1e1e1e; color: #d4d4d4; padding: 40px; }
                    h1 { color: #4ec9b0; }
                    h2 { color: #9cdcfe; margin-top: 30px; }
                    .endpoint { margin: 8px 0; padding: 8px 12px; background: #2d2d2d; border-radius: 4px; display: flex; align-items: center; gap: 12px; }
                    .method { font-weight: bold; min-width: 60px; }
                    .get { color: #4ec9b0; }
                    .post { color: #dcdcaa; }
                    .delete { color: #f44747; }
                    .put { color: #569cd6; }
                    a { color: #d4d4d4; text-decoration: none; }
                    a:hover { color: #4ec9b0; text-decoration: underline; }
                    .status { color: #6a9955; margin-bottom: 20px; }
                    .badge { background: #4ec9b0; color: #1e1e1e; padding: 2px 8px; border-radius: 10px; font-size: 12px; }
                </style>
            </head>
            <body>
                <h1>🍜 HanGeureut Backend API</h1>
                <p class="status">✅ Server is running | <span class="badge">Spring Boot 3.2</span></p>

                <h2>👤 User</h2>
                <div class="endpoint"><span class="method post">POST</span> /login</div>
                <div class="endpoint"><span class="method post">POST</span> /api/user/signup</div>
                <div class="endpoint"><span class="method get">GET</span> <a href="/api/user/profile">/api/user/profile</a></div>
                <div class="endpoint"><span class="method put">PUT</span> /api/user/profile</div>
                <div class="endpoint"><span class="method post">POST</span> /api/user/follow/{userId}</div>

                <h2>📚 Album</h2>
                <div class="endpoint"><span class="method get">GET</span> <a href="/api/album">/api/album</a></div>
                <div class="endpoint"><span class="method post">POST</span> /api/album</div>
                <div class="endpoint"><span class="method get">GET</span> /api/album/{albumId}</div>
                <div class="endpoint"><span class="method delete">DELETE</span> /api/album/{albumId}</div>
                <div class="endpoint"><span class="method post">POST</span> /api/album/like/{albumId}</div>

                <h2>📸 Photo</h2>
                <div class="endpoint"><span class="method get">GET</span> /api/photo/{photoId}</div>
                <div class="endpoint"><span class="method post">POST</span> /api/photo</div>

                <h2>⭐ Review</h2>
                <div class="endpoint"><span class="method get">GET</span> <a href="/api/reviews">/api/reviews</a></div>
                <div class="endpoint"><span class="method post">POST</span> /api/reviews</div>
                <div class="endpoint"><span class="method get">GET</span> /api/reviews/places/{placeId}</div>
                <div class="endpoint"><span class="method delete">DELETE</span> /api/reviews/{reviewId}</div>

                <h2>🔍 Search</h2>
                <div class="endpoint"><span class="method get">GET</span> <a href="/search/keyword?keyword=강남">/search/keyword</a></div>
                <div class="endpoint"><span class="method get">GET</span> /search/placeDetail/{id}</div>

                <h2>🔑 OAuth2</h2>
                <div class="endpoint"><span class="method get">GET</span> <a href="/oauth2/authorization/kakao">/oauth2/authorization/kakao</a></div>
                <div class="endpoint"><span class="method get">GET</span> <a href="/oauth2/authorization/google">/oauth2/authorization/google</a></div>
            </body>
            </html>
        """;
    }
}