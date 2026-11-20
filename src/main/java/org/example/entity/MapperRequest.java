package org.example.entity;

import lombok.Data;
import java.util.Map;

@Data
public class MapperRequest {
    private Map<String, Object> data;
}