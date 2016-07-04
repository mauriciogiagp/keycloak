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

import org.junit.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.common.util.Time;
import org.keycloak.models.Constants;
import org.keycloak.testsuite.util.UserBuilder;

import javax.ws.rs.core.Response;

import static org.keycloak.testsuite.auth.page.AuthRealm.ADMIN;
import static org.keycloak.testsuite.auth.page.AuthRealm.MASTER;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class AddUsers {

    @Test
    public void run() {
        Keycloak kc = Keycloak.getInstance("http://localhost:8080/auth", MASTER, ADMIN, ADMIN, Constants.ADMIN_CLI_CLIENT_ID);
        UsersResource user = kc.realm("master").users();

        long s = System.currentTimeMillis();
        long p = s;

        for (int i = 1; i <= 1000000; i++) {
            Response response = user.create(UserBuilder.create().username("user-" + Time.currentTimeMillis()).build());
            if (response.getStatus() >= 400) {
                System.err.println(response.getStatus());
            }
            response.close();

            if (i % 100 == 0) {
                long c = System.currentTimeMillis();
                System.out.println(i + " in " + (c - p) + " ms");
                p = c;
            }
        }

        System.out.println("Done in " + (System.currentTimeMillis() - s) + " ms");
    }

}
