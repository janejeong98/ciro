package com.example.ciro.services;

import com.example.ciro.model.BaseCompanyObject;
import com.example.ciro.model.Company;
import com.example.ciro.model.CompareResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
public class SimilarityServiceImpl implements SimilarityService {

    @Override
    public <T extends BaseCompanyObject> CompareResponse getHighestSimilarityObject(Map<Integer, T> idToObjectMap, T object) {
        if (idToObjectMap.containsKey(object.getId())) {
            return CompareResponse.builder()
                    .closestMatchingObject(idToObjectMap.get(object.getId()))
                    .build();
        }
        int highestMatchValue = -1;
        T highestMatchObject = null;
        for (T savedObject : idToObjectMap.values()) {
            int currentMatchValue = calculate(savedObject.toString(), object.toString());
            if (currentMatchValue > highestMatchValue) {
                highestMatchObject = savedObject;
                highestMatchValue = currentMatchValue;
            }
        }
        return CompareResponse.builder()
                .closestMatchingObject(highestMatchObject)
                .matchScore(highestMatchValue)
                .build();
    }

    private static int calculate(String x, String y) {
        if (x.isEmpty()) {
            return y.length();
        }

        if (y.isEmpty()) {
            return x.length();
        }

        int substitution = calculate(x.substring(1), y.substring(1))
                + costOfSubstitution(x.charAt(0), y.charAt(0));
        int insertion = calculate(x, y.substring(1)) + 1;
        int deletion = calculate(x.substring(1), y) + 1;

        return min(substitution, insertion, deletion);
    }

    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

}
