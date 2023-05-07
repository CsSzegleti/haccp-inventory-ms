package hu.soft4d.resource.utils;

import io.quarkus.oidc.TenantResolver;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;

import static hu.soft4d.resource.utils.TenantNameResolver.extractTenantFrom;

@ApplicationScoped
public class CustomOidcTenantResolver implements TenantResolver {
    @Override
    public String resolve(RoutingContext context) {
        return extractTenantFrom(context);
    }
}
