package me.pruivo.protostream;

import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

/**
 * TODO! document this
 *
 * @author Pedro Ruivo
 */
@AutoProtoSchemaBuilder(includeClasses = {
      Address.class,
      User.class,
      IracMetadata.class,
      IracEntryVersion.class,
      IracEntryVersion.MapEntry.class,
      TopologyIracVersion.class
})
public interface BenchmarkSerializationContextInitializer extends SerializationContextInitializer {

   SerializationContextInitializer INSTANCE = new me.pruivo.protostream.BenchmarkSerializationContextInitializerImpl();

}
