package nextstep.web.endpoint.theme.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ThemeCreateRequest {
    private final String name;
    private final String desc;
    private final Long price;

    public ThemeCreateRequest(String name, String desc, Long price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
