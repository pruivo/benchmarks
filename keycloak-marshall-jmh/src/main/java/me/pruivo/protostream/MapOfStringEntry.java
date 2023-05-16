package me.pruivo.protostream;

import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoTypeId;

import java.util.Map;

@ProtoTypeId(ProtostreamIds.MAP_OF_STRING_ENTRY_ID)
public class MapOfStringEntry {

    @ProtoField(1)
    final String key;
    @ProtoField(2)
    final String value;

    public MapOfStringEntry(Map.Entry<String, String> entry) {
        this(entry.getKey(), entry.getValue());
    }

    @ProtoFactory
    MapOfStringEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void addToMap(Map<String, String> map) {
        map.put(key, value);
    }
}
