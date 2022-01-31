package org.egehan.functions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;

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

