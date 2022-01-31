package org.egehan.functions;

import java.time.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import java.sql.*;

/**
 * Azure Functions with Timer trigger.
 */
public class TimerTriggerFunction {
    /**
     * This function will be invoked periodically according to the specified schedule.
     */
    @FunctionName("TimerTrigger-Java")
    public void run(
        @TimerTrigger(name = "timerInfo", schedule = "0 * * * * *") String timerInfo,
        final ExecutionContext context
    ) {
        deleteAllLogs();
        context.getLogger().info("Java Timer trigger function executed at: " + LocalDateTime.now());
    }

    private void deleteAllLogs(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://bulut-final-project-db.mysql.database.azure.com:3306/bulutdb","egehan@bulut-final-project-db","19290a.22dm-QE");

            //here mydb is database name, root is username and password
            Statement stmt=con.createStatement();
            stmt.execute("DELETE FROM form_recognize_request_log");
            System.out.println("Delete Successful");
            con.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        TimerTriggerFunction timerTriggerFunction = new TimerTriggerFunction();
        timerTriggerFunction.deleteAllLogs();
    }
}

