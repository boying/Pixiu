package common;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Created by boying on 2017/9/29.
 */
public class ErrorGsonUtils {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private static Gson gson;
    static{
        JsonSerializer<LocalDateTime> serializer = new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
            }
        };

        JsonDeserializer<LocalDateTime> deserializer = new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT,
                                             JsonDeserializationContext context) throws JsonParseException {
                return json == null ? null : LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
            }
        };

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
                .registerTypeAdapter(LocalDateTime.class, serializer)
                .registerTypeAdapter(LocalDateTime.class, deserializer)
                .create();
    }

    public static Gson getGson(){
        return gson;
    }

    /**
     * 错误的方法!!!
     * @param json
     * @param <T>
     * @return
     */
    @Deprecated
    public static <T> List<T> parseList(String json){
        return gson.fromJson(json, new TypeToken<List<T>>(){}.getType());
    }

    /**
     * 错误的方法!!!
     * TODO Why
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    @Deprecated
    public static <T> List<T> parseList(String json, Class<T> clazz){
        return gson.fromJson(json, new TypeToken<List<T>>(){}.getType());
    }

    public static <T> List<T> parseList(String json, TypeToken<List<T>> typeToken){
        return gson.fromJson(json, typeToken.getType());
    }

    public static <T> Map<String, T> parseMap(String json, Class<T> valueClazz){
        return null;
    }
}
