package me.pruivo.state;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class RandomDataState {

    @Param({"8192"})
    int seed;
    Random random;

    @Setup
    public void setup() {
        random = new Random(seed);
    }

    public String randomString() {
        byte[] data = new byte[256];
        random.nextBytes(data);
        return UUID.nameUUIDFromBytes(data).toString();
    }

    public Set<String> randomSet(int size) {
        return IntStream.range(0, size)
                .mapToObj(__ -> randomString())
                .collect(Collectors.toSet());
    }

    public Map<String, String> randomMapOfString(int size) {
        return IntStream.range(0, size).
                mapToObj(i -> randomString())
                .collect(Collectors.toMap(Function.identity(), Function.identity()));
    }

    public <T> Map<String, T> randomMapOfT(int size, T value) {
        return IntStream.range(0, size).
                mapToObj(i -> randomString())
                .collect(Collectors.toMap(Function.identity(), s -> value));
    }

    public int randomInt() {
        return random.nextInt();
    }

    public boolean randomBoolean() {
        return random.nextBoolean();
    }
}
