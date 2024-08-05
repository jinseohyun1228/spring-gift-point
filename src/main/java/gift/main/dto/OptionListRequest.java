package gift.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Validated
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OptionListRequest(@Size(min = 1)
                                @Valid
                                List<OptionRequest> options) {

    public OptionListRequest {

        //스트림 API 에서 특정 조선을 만족하는 지 검사할 수 있음
        // allMatch() : 모든 요소들이 참일때만 참
        // anyMatch() : 하나의 요소라도 참이면 참
        // noneMatch() : 모든 요소가 거짓일 때 참

        Set<OptionRequest> optionSet  = new HashSet<>();
        boolean hasDuplicates = options.stream().anyMatch(optionRequest -> !optionSet.add(optionRequest));
        if (hasDuplicates) {
            throw new CustomException(ErrorCode.DUPLICATE_OPTION);
        }
    }

    public OptionListRequest(ProductAllRequest productAllRequest) {
        this(productAllRequest.options());
    }
}
