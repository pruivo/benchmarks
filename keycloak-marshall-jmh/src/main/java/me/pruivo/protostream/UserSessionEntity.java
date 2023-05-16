/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.pruivo.protostream;

import org.infinispan.protostream.annotations.ProtoEnumValue;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoTypeId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
@ProtoTypeId(ProtostreamIds.USER_SESSION_ENTITY_ID)
public class UserSessionEntity extends SessionEntity {

    // Metadata attribute, which contains the lastSessionRefresh available on remoteCache. Used in decide whether we need to write to remoteCache (DC) or not
    public static final String LAST_SESSION_REFRESH_REMOTE = "lsrr";

    private String id;

    private String user;

    private String brokerSessionId;
    private String brokerUserId;

    private String loginUsername;

    private String ipAddress;

    private String authMethod;

    private boolean rememberMe;

    private int started;

    private int lastSessionRefresh;

    private State state;

    @ProtoField(2)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private Map<String, String> notes = new ConcurrentHashMap<>();

    private AuthenticatedClientSessionStore authenticatedClientSessions = new AuthenticatedClientSessionStore();

    @ProtoField(3)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @ProtoField(4)
    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    @ProtoField(5)
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @ProtoField(6)
    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    @ProtoField(value = 7, defaultValue = "false")
    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @ProtoField(value = 8, defaultValue = "0")
    public int getStarted() {
        return started;
    }

    public void setStarted(int started) {
        this.started = started;
    }

    @ProtoField(value = 9, defaultValue = "0")
    public int getLastSessionRefresh() {
        return lastSessionRefresh;
    }

    public void setLastSessionRefresh(int lastSessionRefresh) {
        this.lastSessionRefresh = lastSessionRefresh;
    }

    public Map<String, String> getNotes() {
        return notes;
    }

    public void setNotes(Map<String, String> notes) {
        this.notes = notes;
    }

    @ProtoField(10)
    public AuthenticatedClientSessionStore getAuthenticatedClientSessions() {
        return authenticatedClientSessions;
    }

    public void setAuthenticatedClientSessions(AuthenticatedClientSessionStore authenticatedClientSessions) {
        this.authenticatedClientSessions = authenticatedClientSessions;
    }

    @ProtoField(11)
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @ProtoField(12)
    public String getBrokerSessionId() {
        return brokerSessionId;
    }

    public void setBrokerSessionId(String brokerSessionId) {
        this.brokerSessionId = brokerSessionId;
    }

    @ProtoField(13)
    public String getBrokerUserId() {
        return brokerUserId;
    }

    public void setBrokerUserId(String brokerUserId) {
        this.brokerUserId = brokerUserId;
    }

    @ProtoField(value = 14, collectionImplementation = ArrayList.class)
    List<MapOfStringEntry> notesToList() {
        return notes == null ? null : notes.entrySet().stream()
                .map(MapOfStringEntry::new)
                .collect(Collectors.toList());
    }

    void notesToList(List<MapOfStringEntry> entries) {
        if (entries == null) {
            return;
        }
        notes = new ConcurrentHashMap<>();
        entries.forEach(entry -> entry.addToMap(notes));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSessionEntity)) return false;

        UserSessionEntity that = (UserSessionEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("UserSessionEntity [id=%s, realm=%s, lastSessionRefresh=%d, clients=%s]", getId(), getRealmId(), getLastSessionRefresh(),
                new TreeSet<>(this.authenticatedClientSessions.keySet()));
    }

    @ProtoTypeId(ProtostreamIds.USER_SESSION_STATE_ID)
    public enum State {
        @ProtoEnumValue(1)
        LOGGED_IN(),
        @ProtoEnumValue(2)
        LOGGING_OUT(),
        @ProtoEnumValue(3)
        LOGGED_OUT(),
        @ProtoEnumValue(4)
        LOGGED_OUT_UNCONFIRMED();

        State() {
        }
    }
}
