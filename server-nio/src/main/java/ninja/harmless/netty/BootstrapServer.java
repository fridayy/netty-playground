package ninja.harmless.netty;

/**
 * @author bnjm@harmless.ninja - 9/13/17.
 */
public class BootstrapServer {
    public static void main(String[] args) throws InterruptedException {
        final int port = 1337;
        EchoServer server = new EchoServer(port);
        server.start();
    }
}
