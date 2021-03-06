package com.flora.week9taskblog.Payload.Response;

import com.flora.week9taskblog.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String status;
    private Page<Comment> comments;
}
