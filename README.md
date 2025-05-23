# Web crawler

## Intro

This console application uses a breadth-first search approach to explore all URLs found on the same domain as the starting URL. 
It prints each distinct URL that was visited, and a list of URLs which failed.

Stack:

- Java 21
- Maven
- Docker
- [jsoup](https://jsoup.org/)

## Setup and usage

In the project root directory, you can either:

1. run `docker compose up --build` - this will crawl the default page, https://monzo.com, with a default timeout of 60 seconds and debug mode* turned off or
2. run with a specific starting URL and timeout like so:
```
docker build -t web-crawler .
docker run --rm web-crawler "https://www.example.com/" 30 false
```

* Debug mode will print reasons for failures with the list of URLs which failed to be added to the queue.

### Limitations

At the moment:

 - Only works for static HTML
 - Only works with HTTP and HTTPS
 - Only parses HTML and XML (no PDFs, for instance)
 - Doesn't work with URLs which redirect
 - Ignores URL fragments **and query parameters**
 - Has difficulties with encoding, such as foreign characters and encoded spaces
 - Minimal test coverage

### Future improvements

 - Use a headless browser, such as Selenium, to cope with dynamic content
 - Persist query parameters, so e.g. `/?page=1` and `/?page=2` would be treated as two distinct URLs
 - Improve test coverage
 - Improve file structure, e.g. put common helper methods in `/helpers` folder