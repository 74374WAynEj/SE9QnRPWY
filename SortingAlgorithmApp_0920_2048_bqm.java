// 代码生成时间: 2025-09-20 20:48:20
import play.mvc.Controller;
import play.mvc.Result;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

/**
 * Controller for sorting algorithm application
 */
public class SortingAlgorithmApp extends Controller {

    /**
     * Handles the sort request and returns sorted numbers
     * 
     * @param numbers List of numbers to be sorted
     * @return Sorted list of numbers in JSON format
     */
    public static Result sortNumbers(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            // Handle the error case where the list is null or empty
            return badRequest("Invalid input: numbers list is null or empty.");
        }
        try {
            Collections.sort(numbers);
            return ok(views.html.sort.render(numbers));
        } catch (Exception e) {
            // Handle any unexpected exceptions
            return internalServerError("An error occurred during sorting: " + e.getMessage());
        }
    }

    /**
     * Test route to demonstrate sorting functionality
     */
    public static Result testSort() {
        List<Integer> testNumbers = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        return sortNumbers(testNumbers);
    }
}