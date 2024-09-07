package com.aphisiit.keycloak;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class CustomRestResourceProviderFactory implements RealmResourceProviderFactory {

    public static final String ID = "custom-rest-api";

    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new RealmResourceProvider() {
            @Override
            public Object getResource() {
                return new CustomRestResource();
            }

            @Override
            public void close() {
            }
        };
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
    }

    @Override
    public void postInit(org.keycloak.models.KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return ID;
    }
}
