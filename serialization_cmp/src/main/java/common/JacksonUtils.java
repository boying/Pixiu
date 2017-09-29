package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by boying on 2017/9/29.
 */
public class JacksonUtils {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(TIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(TIME_FORMATTER));

        mapper.registerModule(javaTimeModule)
                .setDateFormat(new SimpleDateFormat(DATE_TIME_PATTERN))
                .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
