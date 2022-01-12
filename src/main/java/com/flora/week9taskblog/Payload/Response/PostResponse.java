package com.flora.week9taskblog.Payload.Response;

import com.flora.week9taskblog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private String status;
    private Page<Post> posts;
}
