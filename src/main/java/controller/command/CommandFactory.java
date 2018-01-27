package controller.command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private Map<String, Command> commandMap;

    CommandFactory() {
        commandMap = new HashMap<>();
    }

    private static class Holder{
        static final CommandFactory INSTANCE = new CommandFactory();
    }

    public static CommandFactory getInstance(){
        return Holder.INSTANCE;
    }

    public Command getCommand(HttpServletRequest request){
        String servletPath =  request.getServletPath();
        Command command = commandMap.get(servletPath);
        if (command == null){
            command = OpenMainPageCommand.getInstance();
        }
        return command;
    }
}
