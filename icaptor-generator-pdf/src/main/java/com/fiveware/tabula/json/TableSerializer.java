package com.fiveware.tabula.json;

import com.fiveware.tabula.RectangularTextContainer;
import com.fiveware.tabula.Table;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class TableSerializer implements JsonSerializer<Table> {

    @Override
    public JsonElement serialize(Table table, Type type,
            JsonSerializationContext context) {

        JsonObject object = new JsonObject();
        if( table.getExtractionAlgorithm() == null){
            object.addProperty("extraction_method", "");
        }else{
            object.addProperty("extraction_method", (table.getExtractionAlgorithm()).toString());
        }
        object.addProperty("top", table.getTop());
        object.addProperty("left", table.getLeft());
        object.addProperty("width", table.getWidth());
        object.addProperty("height", table.getHeight());
        
        JsonArray jsonDataArray = new JsonArray();
        for (List<RectangularTextContainer> row: table.getRows()) {
            JsonArray jsonRowArray = new JsonArray();
            for (RectangularTextContainer textChunk: row) {
                jsonRowArray.add(context.serialize(textChunk));
            }
            jsonDataArray.add(jsonRowArray);
        }
        object.add("data", jsonDataArray);
        
        return object;
    }
}
