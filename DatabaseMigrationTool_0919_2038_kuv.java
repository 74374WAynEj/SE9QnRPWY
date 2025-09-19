// 代码生成时间: 2025-09-19 20:38:45
package com.example.tools;

import play.db.Database;
import play.db.evolutions.Evolutions;
import play.mvc.Controller;
import play.mvc.Result;
import play.Logger;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseMigrationTool extends Controller {

    private Database db;

    /**
     * Constructor that injects the database.
     * @param db The Play Framework database connection.
     */
    @Inject
    public DatabaseMigrationTool(Database db) {
        this.db = db;
    }

    /**
     * Method to handle the database migration.
     * @return Result with a status code indicating success or error.
     */
    public Result migrate() {
        try {
            // Start a transaction
            Connection connection = db.getConnection();
            try {
                connection.setAutoCommit(false);

                // Apply evolutions
                Evolutions.applyEvolutions(db, "演化文件路径"); // Replace with the actual evolutions file path

                // Commit the transaction
                connection.commit();
                return ok("Database migration successful.");
            } catch (Exception e) {
                // Rollback the transaction on error
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    Logger.error("Error during database migration rollback.", ex);
                }
                Logger.error("Error during database migration.", e);
                return internalServerError("Database migration failed: " + e.getMessage());
            } finally {
                db.closeConnection(connection);
            }
        } catch (Exception e) {
            Logger.error("Error getting database connection.", e);
            return internalServerError("Error getting database connection.");
        }
    }
}
