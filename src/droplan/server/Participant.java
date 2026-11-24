package droplan.server;

public class Participant {
    private final String name;
    private final ClientHandler handler;

    Participant(String name, ClientHandler handler){
        this.name = name;
        this.handler = handler;
    }

    public String getName(){
        return name;
    }

    public ClientHandler getHandler() {
        return handler;
    }

}
