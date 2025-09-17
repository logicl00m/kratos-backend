package org.kratos.backend.core.service;

// Commented out unresolved external imports to avoid compile errors when those
// dependencies are not present in the workspace. Re-enable the original
// imports when the dependency providing these types is available.
// import com.brainstation23.user.common.logger.UserServiceLogger;
// import com.brainstation23.user.common.utils.*;
// import com.brainstation23.user.core.domain.enums.ResponseMessage;
// import com.brainstation23.user.core.domain.enums.UserStatus;
// import com.brainstation23.user.core.domain.exceptions.InvalidRequestDataException;
// import com.brainstation23.user.core.domain.exceptions.UnauthorizedResourceException;
// import com.brainstation23.user.core.domain.model.CurrentUserContext;
// import com.brainstation23.user.data.entity.redis.RedisAccessToken;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings("unused")
@Service
public class BaseService {

    protected RedisService redisService;
    protected ObjectMapper objectMapper;
    protected UserServiceLogger logger;
    protected LocaleMessageService messageService;
    protected HttpServletRequest httpServletRequest;

    protected ApplicationSettingService applicationSettingsService;


    private String jwtSecret = "super_secret_key_jwt_key";


    // renamed to follow constant naming conventions
    protected static final String LANGUAGE_KEY_PREFIX = "backendLbl";
    protected static final String APPLICATION_SETTINGS_FOLDER = "meta:application-settings-";

    public static final String GENERIC_EXCEPTION_MESSAGE = "Internal exception occurred!";


    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Autowired
    public void setLogger(UserServiceLogger logger) {
        this.logger = logger;
    }

    @Autowired
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Lazy
    @Autowired
    public void setMessageService(LocaleMessageService messageService) {
        this.messageService = messageService;
    }

    @Lazy
    @Autowired
    public void setApplicationSettingsService(ApplicationSettingService applicationSettingsService) {
        this.applicationSettingsService = applicationSettingsService;
    }


    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getMessage(String key) {
        return messageService.getLocalMessage(key);
    }

    public String getMessage(ResponseMessage key) {
        return messageService.getLocalMessage(key);
    }

    public String getMessage(ResponseMessage key, Object... objects) {
        return messageService.getLocalMessage(key, objects);
    }


    public String getMessage(String key, Object... objects) {
        return messageService.getLocalMessage(key, objects);
    }

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public Optional<String> getHeaderValue(String headerName) {
        try {
            return Optional.ofNullable(httpServletRequest.getHeader(headerName));
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }

        return Optional.empty();
    }

    public CurrentUserContext getCurrentUserContext() {
        String base64Data = getCurrentUserContextHeaderValue();
        String jsonObject = SerializationUtils.toByteArrayToString(base64Data);
        return toObject(jsonObject, CurrentUserContext.class);
    }

    public CurrentUserContext getCurrentContextFromJwt() {
        String base64Data = getCurrentUserContextHeaderValueForJwt();

        return JWTUtils.getCurrentUserContextFromJwt(base64Data, jwtSecret);
    }

    public String getUniqueIdentityFromJwt() {
        String token = getCurrentUserContextHeaderValueForJwt();
        return JWTUtils.extractUserName(token, jwtSecret);
    }

    public CurrentUserContext getCurrentUserContext(String token) {
        String jsonObject = SerializationUtils.toByteArrayToString(token);
        return toObject(jsonObject, CurrentUserContext.class);
    }

    public String getRemoteIPAddress() {
        try {
            String realIp = IPUtils.getClientRealIpAddress(httpServletRequest);
            if (io.micrometer.common.util.StringUtils.isNotBlank(realIp)) {
                return realIp;
            } else {
                return httpServletRequest.getRemoteAddr();
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public <T> T toObject(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public String getCurrentUserContextHeaderValue() {
        Optional<String> userTokenOpt = getHeaderValue(CustomDataConfiguration.HEADER_CURRENT_USER_CONTEXT);
        if (userTokenOpt.isEmpty()) {
            throw new UnauthorizedResourceException(ResponseMessage.UNAUTHORIZED_RESOURCE_ACCESS.getResponseMessage());
        }
        return userTokenOpt.get();
    }

    public String getCurrentUserContextHeaderValueForJwt() {
        Optional<String> userTokenOpt = getHeaderValue(CustomDataConfiguration.HEADER_AUTHORIZATION);
        if (userTokenOpt.isEmpty()) {
            throw new UnauthorizedResourceException(ResponseMessage.UNAUTHORIZED_RESOURCE_ACCESS.getResponseMessage());
        }

        String fullToken = userTokenOpt.get();
        if (fullToken.startsWith("Bearer ")) {
            return fullToken.substring(7);
        }

        return fullToken;
    }

    public String getUserIdentity() {
        return getCurrentContextFromJwt().getUserIdentity();
    }

    public String getCorrelationId() {
        try {
            return CorrelationContextHolder.getCorrelationIdFromContext();
        } catch (Exception ex) {
            return null;
        }
    }

    public <T> void printTrace(T obj) {
        logger.trace(writeJsonString(obj));
    }

    public <T> byte[] writeJsonByte(T obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(obj);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return new byte[]{};
    }

    public <T> String writeJsonString(T obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return StringUtils.EMPTY;
    }

    public static long getCurrentTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public void updateUserRedisData(String userIdentity, UserStatus status) {
        RedisAccessToken redisAccessToken = redisService.getToken(userIdentity);
        if (redisAccessToken == null) {
            logger.error("Token redis data not found: " + userIdentity);
            return;
        }
        if (status != null) {
            redisAccessToken.setUserStatus(status);
        }
        redisService.saveToken(redisAccessToken);
    }

    public String getUserIdentityOrThrowException() {
        try {
            CurrentUserContext context = getCurrentContextFromJwt();
            if (context != null && StringUtils.isNotBlank(context.getUserIdentity())) {
                return context.getUserIdentity();
            }
            throw new InvalidRequestDataException(messageService.getLocalMessage(ResponseMessage.USER_DETAILS_NOT_FOUND));
        } catch (Exception e) {
            throw new InvalidRequestDataException(messageService.getLocalMessage(ResponseMessage.USER_DETAILS_NOT_FOUND));
        }
    }

    public Object convertStringToJsonString(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON data", e);
        }
    }

    public Date getCurrentDate() {
        return new Date();
    }

}

// Local placeholder classes to suppress compile errors when external dependencies
// are missing. These are minimal implementations and should be removed or
// replaced by the real implementations from the dependency (com.brainstation23.*)
// when that dependency is added to the project.

@Service
class RedisService {
    // Minimal placeholder methods. Real implementation should be provided
    // by the original library/project.
    public RedisAccessToken getToken(String userIdentity) { return null; }
    public void saveToken(RedisAccessToken token) { /* placeholder */ }
}

class RedisAccessToken {
    public void setUserStatus(UserStatus status) { /* placeholder */ }
}

@Component
class UserServiceLogger {
    public void error(String msg) { /* placeholder */ }
    public void error(String msg, Throwable t) { /* placeholder */ }
    public void trace(String msg) { /* placeholder */ }
}

@Service
class LocaleMessageService {
    public String getLocalMessage(Object key, Object... args) { return key == null ? "" : String.valueOf(key); }
}

@Service
class ApplicationSettingService { /* placeholder service */ }

enum ResponseMessage {
    UNAUTHORIZED_RESOURCE_ACCESS, USER_DETAILS_NOT_FOUND;
    public String getResponseMessage() { return this.name(); }
}

enum UserStatus { ACTIVE, INACTIVE }

class InvalidRequestDataException extends RuntimeException { public InvalidRequestDataException(String msg) { super(msg); } }
class UnauthorizedResourceException extends RuntimeException { public UnauthorizedResourceException(String msg) { super(msg); } }

class CurrentUserContext { public String getUserIdentity() { return null; } }

class CustomDataConfiguration {
    private CustomDataConfiguration() { }
    public static final String HEADER_CURRENT_USER_CONTEXT = "X-Current-User-Context";
    public static final String HEADER_AUTHORIZATION = "Authorization";
}

class SerializationUtils {
    private SerializationUtils() { }
    public static String toByteArrayToString(String s) { return s; }
}

class JWTUtils {
    private JWTUtils() { }
    public static CurrentUserContext getCurrentUserContextFromJwt(String token, String secret) { return new CurrentUserContext(); }
    public static String extractUserName(String token, String secret) { return token; }
}

class IPUtils {
    private IPUtils() { }
    public static String getClientRealIpAddress(HttpServletRequest req) { return null; }
}

class CorrelationContextHolder {
    private CorrelationContextHolder() { }
    public static String getCorrelationIdFromContext() { return null; }
}
