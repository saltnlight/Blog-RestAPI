package com.flora.week9taskblog.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRemoveObjResponse {
    private Long id;
    private Long user_id;
    private Long field_id;
}
