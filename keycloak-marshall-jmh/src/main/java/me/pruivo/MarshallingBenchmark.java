package me.pruivo;

import me.pruivo.state.AuthenticationSessionEntityState;
import me.pruivo.state.MarshallerState;
import me.pruivo.state.UserSessionState;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 10)
@Measurement(iterations = 6, time = 10)
@Fork(1)
@State(Scope.Benchmark)
public class MarshallingBenchmark {

    @Benchmark
    public Object testUserSessionsMarshall(MarshallerState mState, UserSessionState uState) throws IOException, InterruptedException {
        return mState.marshaller().objectToByteBuffer(uState.getEntity());
    }

    @Benchmark
    public Object testUserSessionUnmarshall(MarshallerState mState, UserSessionState uState) throws IOException, ClassNotFoundException {
        return mState.marshaller().objectFromByteBuffer(uState.getEntityBytes());
    }

    @Benchmark
    public Object testAuthSessionsMarshall(MarshallerState mState, AuthenticationSessionEntityState aState) throws IOException, InterruptedException {
        return mState.marshaller().objectToByteBuffer(aState.getEntity());
    }

    @Benchmark
    public Object testAuthSessionUnmarshall(MarshallerState mState, AuthenticationSessionEntityState aState) throws IOException, ClassNotFoundException {
        return mState.marshaller().objectFromByteBuffer(aState.getEntityBytes());
    }

}
