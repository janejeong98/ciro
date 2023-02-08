package com.example.ciro.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompareResponse<T> {

    private T closestMatchingObject;
    private int matchScore;

}
