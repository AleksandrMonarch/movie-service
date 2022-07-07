package com.msocial.movie_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DataResponse<T> extends BaseResponse {

    private T content;

    public DataResponse(Boolean success, HttpStatus status, T content) {
        super(success, status);
        this.content = content;
    }
}

