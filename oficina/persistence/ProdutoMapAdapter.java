package com.mycompany.oficina.persistence;

import com.google.gson.*;
import com.mycompany.oficina.model.Produto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * (De)serializa {@code Map<Produto,Integer>} como lista de objetos {produto, quantidade},
 * ou para compatibilidade com o formato legado, desserializa de um objeto JSON cujas
 * chaves são toString() de Produto e valores são quantidades.
 */
public class ProdutoMapAdapter implements JsonSerializer<Map<Produto,Integer>>, JsonDeserializer<Map<Produto,Integer>> {

    @Override
    public JsonElement serialize(Map<Produto, Integer> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray array = new JsonArray();
        for (Map.Entry<Produto,Integer> e : src.entrySet()) {
            JsonObject obj = new JsonObject();
            obj.add("produto", context.serialize(e.getKey()));
            obj.addProperty("quantidade", e.getValue());
            array.add(obj);
        }
        return array;
    }

    @Override
    public Map<Produto, Integer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Map<Produto,Integer> map = new HashMap<>();
        if (json.isJsonArray()) {
            // Formato atual: array de objetos { produto, quantidade }
            for (JsonElement el : json.getAsJsonArray()) {
                JsonObject obj = el.getAsJsonObject();
                Produto p = context.<Produto>deserialize(obj.get("produto"), Produto.class);
                int q = obj.get("quantidade").getAsInt();
                map.put(p, q);
            }
        } else if (json.isJsonObject()) {
            // Formato legado: objeto com chaves = toString() de Produto e valores = quantidade
            JsonObject legacy = json.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : legacy.entrySet()) {
                String keyStr = entry.getKey();
                try {
                    // extrai o código do produto a partir de "Produto{codigo=X, ...}"
                    int idx = keyStr.indexOf("codigo=");
                    if (idx >= 0) {
                        int start = idx + 7;
                        int end = keyStr.indexOf(',', start);
                        if (end < 0) end = keyStr.indexOf('}', start);
                        String code = keyStr.substring(start, end).trim();
                        Produto p = new Produto(Integer.parseInt(code), "", 0.0);
                        int q = entry.getValue().getAsInt();
                        map.put(p, q);
                    }
                } catch (Exception ignore) {
                    // ignora entradas inesperadas
                }
            }
        }
        return map;
    }
}
