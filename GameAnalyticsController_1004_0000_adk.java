// 代码生成时间: 2025-10-04 00:00:29
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;
import play.mvc.BodyParser;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import models.GameData;
import services.GameDataService;
import javax.inject.Inject;
import java.util.List;
import static play.mvc.Results.ok;

// 使用PlayFramework框架实现游戏数据分析的控制器
public class GameAnalyticsController extends Controller {

    private final GameDataService gameDataService;

    // 构造函数注入GameDataService
    @Inject
    public GameAnalyticsController(GameDataService gameDataService) {
        this.gameDataService = gameDataService;
    }

    // 提供游戏数据的分析结果
    // 返回JSON格式的数据
    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> analyzeGame() {
        try {
            // 异步获取游戏数据
            return CompletableFuture.supplyAsync(() -> {
                List<GameData> data = gameDataService.getAllGameData();
                return ok(Json.toJson(data));
            }).toCompletionStage();
        } catch (Exception e) {
            // 错误处理，返回内部服务器错误
            return CompletableFuture.completedFuture(internalServerError("Error analyzing game data: " + e.getMessage()));
        }
    }
}
