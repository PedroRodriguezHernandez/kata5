package software;

import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        commands.put("counter",new WordCounterLetterFileTxtCommand());
        Spark.port(8080);
        Spark.get("/counter/*",(request, response)
                -> new CommandExecutor(request,response)
                .execute("counter"));
    }
    static Map<String, Command> commands = new HashMap<>();

    private record CommandExecutor(Request request, Response response) {

        public String execute(String name) {
            Command command = commands.get(name);
            Command.Output output = command.execute(input());
            response.status(output.responseCode());
            return output.result();
        }

        private Command.Input input() {
            return parameter -> request.splat()[0];
        }
    }
}
