package me.pruivo.state;

import me.pruivo.protostream.BenchSchema;
import org.infinispan.protostream.ProtobufUtil;
import org.infinispan.protostream.SerializationContext;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ProtoStreamState {

    private final SerializationContext ctx;

    public ProtoStreamState() {
        ctx = ProtobufUtil.newSerializationContext();
    }

    @Setup
    public void setup() {
        BenchSchema.INSTANCE.registerSchema(ctx);
        BenchSchema.INSTANCE.registerMarshallers(ctx);
    }

    public SerializationContext getCtx() {
        return ctx;
    }
}
