package scratch.frontend.examples.hybrid.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomLongs.someLongBetween;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;

public class PubliclyCachedHandlerInterceptorTest {

    private String path;
    private List<String> cachedPaths;
    private PubliclyCachedHandlerInterceptor interceptor;
    private Long maxAge;
    private TimeUnit maxAgeUnit;

    @Before
    public void setUp() {
        path = someString();
        cachedPaths = asList(someString(), path, someString());
        maxAge = someLongBetween(0L, 100L);
        maxAgeUnit = someEnum(TimeUnit.class);
        interceptor = new PubliclyCachedHandlerInterceptor(cachedPaths, maxAge, maxAgeUnit);
    }

    @Test
    public void Will_cache_listed_resources() throws Exception {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        // Given
        given(request.getServletPath()).willReturn(path);

        // When
        interceptor.postHandle(request, response, someThing(), mock(ModelAndView.class));

        // Then
        then(response).should().setHeader("Cache-Control", format("max-age=%d", maxAgeUnit.toSeconds(maxAge)));
        then(response).should().setHeader("Expires", "");
        then(response).should().setHeader("Pragma", "");
    }

    @Test
    public void Will_not_cache_unlisted_resources() throws Exception {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        // Given
        given(request.getServletPath()).willReturn(someString());

        // When
        interceptor.postHandle(request, response, someThing(), mock(ModelAndView.class));

        // Then
        verifyZeroInteractions(response);
    }
}