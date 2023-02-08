package com.example.ciro.services;

import com.example.ciro.model.BaseCompanyObject;
import com.example.ciro.model.CompareResponse;

import java.util.Map;

public interface SimilarityService {

    <T extends BaseCompanyObject> CompareResponse getHighestSimilarityObject(Map<Integer, T> idToObjectMap, T object);

}
