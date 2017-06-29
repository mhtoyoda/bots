package com.fiveware.core.writers;

import com.fiveware.core.Cell;
import com.fiveware.core.RectangularTextContainer;
import com.fiveware.core.Table;
import com.fiveware.core.TextChunk;
import com.fiveware.core.json.TableSerializer;
import com.fiveware.core.json.TextChunkSerializer;
import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.List;


public class JSONWriter implements Writer {

    class TableSerializerExclusionStrategy implements ExclusionStrategy {

        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes fa) {
            return !fa.hasModifier(Modifier.PUBLIC);
        }
    }


    final Gson gson;

    public JSONWriter() {
        gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new TableSerializerExclusionStrategy())
                .registerTypeAdapter(Table.class, new TableSerializer())
                .registerTypeAdapter(RectangularTextContainer.class, new TextChunkSerializer())
                .registerTypeAdapter(Cell.class, new TextChunkSerializer())
                .registerTypeAdapter(TextChunk.class, new TextChunkSerializer())
                .create();
    }

    @Override
    public void write(Appendable out, Table table) throws IOException {

        out.append(gson.toJson(table, Table.class));
    }

    public void write(Appendable out, List<Table> tables) throws IOException {

        JsonArray array = new JsonArray();
        for (Table table : tables) {
            array.add(gson.toJsonTree(table, Table.class));
        }
        out.append(gson.toJson(array));

    }
}
