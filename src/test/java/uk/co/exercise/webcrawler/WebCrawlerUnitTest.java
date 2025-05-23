package uk.co.exercise.webcrawler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WebCrawlerUnitTest {

    private ByteArrayOutputStream outputContent;

    @BeforeEach
    void setUp() {
        outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void webCrawler_failsToStartUp_ifGivenInvalidStartingUrl() {
        // given
        var crawler = new WebCrawler("fake-url", 30000, false);

        // when
        crawler.crawl();

        // then
        var output = outputContent.toString();
        assertThat(output).contains("Starting URL is invalid. Cannot start crawling.");
        assertThat(output).contains("Cannot start the web crawler.");
    }
}
