package hu.soft4d.resource.utils;

import io.quarkus.arc.Unremovable;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import static hu.soft4d.resource.utils.TenantNameResolver.extractTenantFrom;

@RequestScoped
@Unremovable
@PersistenceUnitExtension
public class CustomHibernateTenantResolver implements TenantResolver {

    private static final String BASE_TENANT = "base";

    @Inject
    RoutingContext context;

    @Override
    public String getDefaultTenantId() {
        return BASE_TENANT;
    }

    @Override
    public String resolveTenantId() {
        String tenant = extractTenantFrom(context);

        if (null == tenant) {
            return getDefaultTenantId();
        }
        else {
            return tenant;
        }
    }
}
