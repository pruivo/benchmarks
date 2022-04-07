package me.pruivo;

import static me.pruivo.protostream.BenchmarkSerializationContextInitializer.INSTANCE;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.infinispan.protostream.ProtobufUtil;
import org.infinispan.protostream.SerializationContext;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import me.pruivo.protostream.Address;
import me.pruivo.protostream.User;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 10)
@Measurement(iterations = 6, time = 10)
@Fork(1)
@State(Scope.Benchmark)
public class ProtostreamBenchmark {

   private final SerializationContext ctx;
   private final Address address;
   private final User user;

   public ProtostreamBenchmark() {
      ctx = ProtobufUtil.newSerializationContext();
      INSTANCE.registerSchema(ctx);
      INSTANCE.registerMarshallers(ctx);
      address = new Address("That street", "1234-567", 1024, true);
      Set<Integer> accounts = new HashSet<>();
      IntStream.range(10, 20).forEach(accounts::add);
      List<Address> addresses = new ArrayList<>(4);
      addresses.add(new Address("This street", "1234-678", 256, false));
      addresses.add(new Address("The other street", "1234-789", 1, true));
      addresses.add(new Address("Yet another street", "1111-222", 56, true));
      addresses.add(new Address("Out of street", "3333-111", 1, true));
      user = new User(10, "John", "Doe", accounts, addresses, 18, User.Gender.MALE, null, Instant.now(), Instant.now().plusMillis(TimeUnit.DAYS.toMillis(30)));
   }


   @Benchmark
   public void testMarshallAddress(Blackhole blackhole) throws IOException {
      blackhole.consume(ProtobufUtil.toWrappedByteArray(ctx, address));
   }

   @Benchmark
   public void testMarshallUser(Blackhole blackhole) throws IOException {
      blackhole.consume(ProtobufUtil.toWrappedByteArray(ctx, user));
   }

}
