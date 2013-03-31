import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Cookie;

import play.api.mvc.Session$;
import play.libs.F;
import play.test.TestBrowser;
import play.test.WithApplication;

/**
 * @author Martial SOMDA
 *
 */
@Ignore
public class GlobalTest extends WithApplication {

    @Test
    public void test_path_not_found() {
        running(testServer(9001), HTMLUNIT, new F.Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser testBrowser) throws Throwable {
                testBrowser.goTo("http://localhost:9001/this/path/not/found");
                Cookie sessionCookie = testBrowser.getCookie(Session$.MODULE$.COOKIE_NAME());
                assertThat(sessionCookie).isNotNull();
                assertThat(sessionCookie.getValue()).contains("status%3A404");
            }
        });
    }

}
