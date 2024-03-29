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

import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoTypeId;
import org.keycloak.models.sessions.infinispan.changes.SessionEntityWrapper;

import java.io.Serializable;

/**
 * Represents an entity containing data about a session, i.e. an object that is stored in infinispan cache and can be
 * potentially shared across DCs. Due to conflict management in {@code RemoteCacheInvoker} and
 * {@code InfinispanChangelogBasedTransaction} that use Infinispan's {@code replace()} method, overriding {@link #hashCode()}
 * and {@link #equals(Object)} is <b>mandatory</b> in descendants.
 *
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public abstract class SessionEntity implements Serializable {

    private String realmId;

    /**
     * Returns realmId ID.
     * @return
     */
    @ProtoField(1)
    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public SessionEntity() {
    }

    protected SessionEntity(String realmId) {
        this.realmId = realmId;
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

}
