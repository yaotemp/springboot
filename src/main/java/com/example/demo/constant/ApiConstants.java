package com.example.demo.constant;

import org.springframework.http.MediaType;

public final class ApiConstants {
    
    // 基础路径
    public static final String API_BASE_PATH = "/api";
    public static final String USERS_BASE_PATH = API_BASE_PATH + "/users";
    
    // 路径常量
    public static final class UserApi {
        public static final String CREATE = "";  // 直接使用基础路径
        public static final String GET_ALL = "/all";
        public static final String FIND_BY_ID = "/find";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String TEST = "/test";
        
        // 媒体类型常量
        public static final String CONSUMES = MediaType.APPLICATION_JSON_VALUE;
        public static final String PRODUCES = MediaType.APPLICATION_JSON_VALUE;
    }
    
    // 私有构造函数，防止实例化
    private ApiConstants() {
        throw new AssertionError("Constants class should not be instantiated");
    }
} 