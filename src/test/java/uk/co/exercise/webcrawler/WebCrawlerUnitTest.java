package uk.co.exercise.webcrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class WebCrawlerUnitTest {

    private final String VALID_URL = "https://example.com/";
    private final String URL_ON_PAGE = "https://example.com/help";
    private final Integer TIMEOUT_30_SECONDS_IN_MILLISECONDS = 30 * 1000;

    @Mock
    private final Connection connection = mock(Connection.class);

    private MockedStatic<Jsoup> jsoupMockedStatic;
    private ByteArrayOutputStream outputContent;

    @BeforeEach
    void setUp() throws IOException {
        var html = "<html><body><a href='" + URL_ON_PAGE + "'>Help</a></body></html>";
        var document = Jsoup.parse(html, VALID_URL);

        jsoupMockedStatic = mockStatic(Jsoup.class);

        when(Jsoup.connect(URL_ON_PAGE)).thenReturn(connection);
        when(Jsoup.connect(VALID_URL)).thenReturn(connection);

        when(connection.timeout(anyInt())).thenReturn(connection);
        when(connection.get()).thenReturn(document);

        outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
    }

    @AfterEach
    void tearDown() {
        jsoupMockedStatic.close();
        System.setOut(System.out);
    }

    @Test
    void webCrawler_stopsWhenNoMoreDistinctUrls() {
        // given
        var crawler = new WebCrawler(VALID_URL, TIMEOUT_30_SECONDS_IN_MILLISECONDS, false);

        // when
        crawler.crawl();

        // then
        var output = outputContent.toString();
        assertThat(output).contains("Crawling finished! Visited 2 distinct URLs:", VALID_URL, URL_ON_PAGE);
    }

    @Test
    void webCrawler_failsToStartUp_ifGivenInvalidStartingUrl() {
        // given
        var crawler = new WebCrawler("fake-url", TIMEOUT_30_SECONDS_IN_MILLISECONDS, false);

        // when
        crawler.crawl();

        // then
        var output = outputContent.toString();
        assertThat(output).contains("Starting URL is invalid. Cannot start crawling.", "Cannot start the web crawler.");
    }
}
