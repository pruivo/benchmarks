package me.pruivo.protostream;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(
        includeClasses = {
                MapOfStringEntry.class,
                UserSessionEntity.class,
                AuthenticatedClientSessionStore.class
        },
        //knows how to marshall UUID
        dependsOn = {
                org.infinispan.protostream.types.java.CommonTypes.class,
                org.infinispan.protostream.types.java.CommonContainerTypes.class
        },
        schemaFileName = "bench-schema.proto",
        schemaPackageName = "benchmark")
public interface BenchSchema extends GeneratedSchema {
    BenchSchema INSTANCE = new BenchSchemaImpl();
}
