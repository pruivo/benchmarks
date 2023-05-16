package me.pruivo.state;

import me.pruivo.protostream.AuthenticatedClientSessionStore;
import me.pruivo.protostream.UserSessionEntity;
import org.infinispan.protostream.ProtobufUtil;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.IOException;
import java.util.UUID;

@State(Scope.Benchmark)
public class ProtoStreamUserSessionState {

    @Param({"-1", "1", "8", "64"})
    int items;
    UserSessionEntity entity;
    byte[] entityBytes;

    @Setup
    public void setup(ProtoStreamState state, RandomDataState generator) throws IOException, InterruptedException {
        entity = new UserSessionEntity();
        entity.setAuthMethod(generator.randomString());
        entity.setBrokerSessionId(generator.randomString());
        entity.setBrokerUserId(generator.randomString());
        entity.setId(generator.randomString());
        entity.setIpAddress(generator.randomString());
        entity.setLoginUsername(generator.randomString());
        entity.setRealmId(generator.randomString());
        entity.setUser(generator.randomString());
        entity.setLastSessionRefresh(generator.randomInt());
        entity.setStarted(generator.randomInt());
        entity.setRememberMe(generator.randomBoolean());
        entity.setState(UserSessionEntity.State.LOGGED_IN);

        if (items >= 0) {
            entity.setNotes(generator.randomMapOfString(items));
        }
        AuthenticatedClientSessionStore authSessions = new AuthenticatedClientSessionStore();
        if (items >= 0) {
            for (int i = 0; i < items; ++i) {
                authSessions.put(generator.randomString(), UUID.randomUUID());
            }
        }
        entity.setAuthenticatedClientSessions(authSessions);
        entityBytes = ProtobufUtil.toWrappedByteArray(state.getCtx(), entity);
        System.out.printf("Items: %d, byte array length: %d%n", items, entityBytes.length);
    }


    public UserSessionEntity getEntity() {
        return entity;
    }

    public byte[] getEntityBytes() {
        return entityBytes;
    }

}
