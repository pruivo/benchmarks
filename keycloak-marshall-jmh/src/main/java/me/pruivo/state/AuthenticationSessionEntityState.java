package me.pruivo.state;

import org.keycloak.models.sessions.infinispan.entities.AuthenticationSessionEntity;
import org.keycloak.sessions.AuthenticationSessionModel;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.IOException;

@State(Scope.Benchmark)
public class AuthenticationSessionEntityState {

    @Param({"-1", "1", "8", "64"})
    int items;
    AuthenticationSessionEntity entity;
    byte[] entityBytes;

    @Setup
    public void setup(MarshallerState state, RandomDataState generator) throws IOException, InterruptedException {
        entity = new AuthenticationSessionEntity();
        entity.setClientUUID(generator.randomString());
        entity.setAuthUserId(generator.randomString());
        entity.setTimestamp(generator.randomInt());
        entity.setRedirectUri(generator.randomString());
        entity.setAction(generator.randomString());
        entity.setProtocol(generator.randomString());
        if (items >= 0) {
            entity.setClientScopes(generator.randomSet(items));
            entity.setExecutionStatus(generator.randomMapOfT(items, AuthenticationSessionModel.ExecutionStatus.SUCCESS));
            entity.setClientNotes(generator.randomMapOfString(items));
            entity.setAuthNotes(generator.randomMapOfString(items));
            entity.setRequiredActions(generator.randomSet(items));
            entity.setUserSessionNotes(generator.randomMapOfString(items));
        }

        entityBytes = state.marshaller().objectToByteBuffer(entity);
        System.out.printf("Items: %d, byte array length: %d%n", items, entityBytes.length);
    }

    public AuthenticationSessionEntity getEntity() {
        return entity;
    }

    public byte[] getEntityBytes() {
        return entityBytes;
    }
}
