package huudan.io.accountservice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class StatisticDTO {
    private Long id;

    @NonNull
    private String message;

    @NonNull
    private Date createdDate;
}
