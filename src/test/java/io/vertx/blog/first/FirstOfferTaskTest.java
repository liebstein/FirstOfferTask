package io.vertx.blog.first;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FirstOfferTaskTest {

    private Vertx vertx;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(FirstOfferTask.class.getName(),
                context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testHealthCheck(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient().getNow(8080, "localhost", "/healthcheck",
                response -> {
                    response.handler(body -> {
                        context.assertTrue(body.toString().contains("alive"));
                        async.complete();
                    });
                });
    }

    @Test
    public void testHello(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient().getNow(8080, "localhost", "/hello?name=Assaf",
                response -> {
                    response.handler(body -> {
                        context.assertTrue(body.toString().contains("Assaf"));
                        async.complete();
                    });
                });
    }
}
