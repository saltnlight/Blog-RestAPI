package com.flora.week9taskblog.Payload.Response;

import com.flora.week9taskblog.Model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private String status;
    private List<Post> posts;
}
