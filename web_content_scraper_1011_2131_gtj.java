// 代码生成时间: 2025-10-11 21:31:46
// Web Content Scraper using Java and PlayFramework

import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import play.libs.Json;
import play.mvc.BodyParser;
import play.Logger;

public class WebContentScraper extends Controller {

    // Dependency injection of WSClient
    private final WSClient ws;

    public WebContentScraper(WSClient ws) {
        this.ws = ws;
    }

    // Route to start the scraping process
    public Result scrapeContent(String url) {
        try {
            WSRequest request = ws.url(url);

            // Send a GET request and handle the response
            return request.get().thenApplyAsync(response -> {
                if (response.getStatus() == 200) {
                    return ok(response.getBody()).setContentType("text/html");
                } else {
                    return status(response.getStatus(), "Failed to retrieve content.");
                }
            }).exceptionally(e -> {
                Logger.error("Error during web content scraping: " + e.getMessage());
                return internalServerError("Error during web content scraping.");
            });
        } catch (Exception e) {
            Logger.error("Error occurred while scraping content: " + e.getMessage());
            return internalServerError("Error occurred while scraping content.");
        }
    }

    // Route to handle JSON requests for scraping content
    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> scrapeContentJson() {
        Http.RequestBody jsonBody = request().body();
        JsonNode json = jsonBody.asJson();
        String url = json.get("url").asText();

        try {
            WSRequest request = ws.url(url);

            // Send a GET request and handle the response
            return request.get().thenApplyAsync(response -> {
                if (response.getStatus() == 200) {
                    return ok(response.getBody()).setContentType("application/json");
                } else {
                    return status(response.getStatus(), "Failed to retrieve content.");
                }
            });
        } catch (Exception e) {
            Logger.error("Error occurred while scraping content: " + e.getMessage());
            return CompletableFuture.completedFuture(internalServerError("Error occurred while scraping content."));
        }
    }
}
