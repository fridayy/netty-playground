package ninja.harmless.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * Handles inbound Channel events or associated state changes of active or inactive connections, data reads
 * user events and error events.
 * <p>
 * A {@link Sharable} ChannelHandler can safely shared by multiple {@link java.nio.channels.Channels}.
 *
 * @author bnjm@harmless.ninja - 9/13/17.
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * Callback for each incoming message
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("Received: " + byteBuf.toString(UTF_8));
        // Write the incoming message directly back to the sender without flushing the outbound message.
        ctx.write(byteBuf);
    }

    /**
     * Callback for the last inbound event in the current batch
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * Called if an exception is thrown during the read operation
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
