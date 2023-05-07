package hu.soft4d.resource.utils;

import io.vertx.ext.web.RoutingContext;

public class TenantNameResolver {
    public static String extractTenantFrom(RoutingContext context) {
        String[] domainWithPort = context.request().host().split(":");

        String tenant = domainWithPort[0].split("\\.")[0];

        return tenant;
    }
}
