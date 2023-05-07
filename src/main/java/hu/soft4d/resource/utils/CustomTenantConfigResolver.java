package hu.soft4d.resource.utils;

import io.quarkus.oidc.OidcRequestContext;
import io.quarkus.oidc.OidcTenantConfig;
import io.quarkus.oidc.TenantConfigResolver;
import io.quarkus.oidc.common.runtime.OidcCommonConfig;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

import java.util.Optional;

import static hu.soft4d.resource.utils.TenantNameResolver.extractTenantFrom;

@ApplicationScoped
public class CustomTenantConfigResolver implements TenantConfigResolver {

    private static final String CLIENT_ID = "backend-service";

    @ConfigProperty(name = "auth-server-url.format")
    String AUTH_SERVER_FORMAT;


    @Override
    public Uni<OidcTenantConfig> resolve(RoutingContext context, OidcRequestContext<OidcTenantConfig> requestContext) {
        String tenant = extractTenantFrom(context);

        if (null == tenant) {
            return null;
        }

        return Uni.createFrom().item(() -> createTenantConfig(tenant));
    }

    private OidcTenantConfig createTenantConfig(String tenant) {
        final OidcTenantConfig config = new OidcTenantConfig();

        config.setTenantId(tenant);
        config.setAuthServerUrl(String.format(AUTH_SERVER_FORMAT, tenant));
        config.setClientId(CLIENT_ID);
        OidcCommonConfig.Credentials credentials = new OidcCommonConfig.Credentials();
        config.setCredentials(credentials);

        config.tls.verification = Optional.of(OidcCommonConfig.Tls.Verification.NONE);
        return config;
    }
}
