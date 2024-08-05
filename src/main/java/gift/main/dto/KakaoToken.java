package gift.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoToken(
        String accessToken,                             //사용자 액세스 토큰 값
        String tokenType,                               //토큰타입, bearer로 고정
        String refreshToken,                            //사용자 리프레시 토큰 값
        int expiresIn,                                  //엑세스 토큰 시간 (초)
        int refreshTokenExpiresIn,                      //리프레시 토큰 시간
        LocalDateTime accessTokenExpirationDate         //직접 계산한 만료시간
) {
    public KakaoToken(TempToken token, LocalDateTime createDateTime) {
        this(token.accessToken(), token.tokenType(), token.refreshToken(), token.expiresIn(), token.refreshTokenExpiresIn(), createDateTime.plusSeconds(token.expiresIn()));
    }
}
