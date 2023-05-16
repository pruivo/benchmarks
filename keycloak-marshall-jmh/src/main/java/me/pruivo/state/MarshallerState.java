package me.pruivo.state;

import org.infinispan.jboss.marshalling.commons.GenericJBossMarshaller;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class MarshallerState {

    private GenericJBossMarshaller marshaller;

    @Setup
    public void setup() {
        marshaller = new GenericJBossMarshaller();
    }

    public GenericJBossMarshaller marshaller() {
        return marshaller;
    }
}
