// 代码生成时间: 2025-09-21 00:34:48
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import com.fasterxml.jackson.databind.JsonNode;

public class MemoryAnalyzer extends Controller {

    /**
     * GET method to get the memory usage statistics.
     *
     * @return A JSON object containing the memory usage statistics.
     */
    public Result getMemoryUsage() {
        try {
            // Initialize the MemoryMXBean
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

            // Retrieve memory usage statistics
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

            // Create a JSON object to hold the memory usage data
            JsonNode memoryUsageJson = Json.newObject();
            memoryUsageJson.put("heapMemoryUsage", convertMemoryUsageToJson(heapMemoryUsage));
            memoryUsageJson.put("nonHeapMemoryUsage", convertMemoryUsageToJson(nonHeapMemoryUsage));

            // Return the JSON object as the response
            return ok(memoryUsageJson);
        } catch (Exception e) {
            // Handle any exceptions that occur
            return internalServerError(Json.newObject().put("error", e.getMessage()));
        }
    }

    /**
     * Converts MemoryUsage object to a JSON object.
     *
     * @param memoryUsage The MemoryUsage object to convert.
     * @return A JSON object representing the MemoryUsage.
     */
    private JsonNode convertMemoryUsageToJson(MemoryUsage memoryUsage) {
        JsonNode memoryUsageJson = Json.newObject();
        memoryUsageJson.put("init", memoryUsage.getInit());
        memoryUsageJson.put("used", memoryUsage.getUsed());
        memoryUsageJson.put("committed", memoryUsage.getCommitted());
        memoryUsageJson.put("max", memoryUsage.getMax());
        return memoryUsageJson;
    }
}