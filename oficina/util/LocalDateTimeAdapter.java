// LocalDateTimeAdapter.java
package com.mycompany.oficina.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Adapter para serializar/desserializar LocalDateTime no formato ISO-8601 (yyyy-MM-dd'T'HH:mm:ss).
 */
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
  private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  @Override
  public JsonElement serialize(LocalDateTime src, Type type, JsonSerializationContext ctx) {
    return new JsonPrimitive(src.format(FMT));
  }
  @Override
  public LocalDateTime deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
    return LocalDateTime.parse(el.getAsString(), FMT);
  }
}
