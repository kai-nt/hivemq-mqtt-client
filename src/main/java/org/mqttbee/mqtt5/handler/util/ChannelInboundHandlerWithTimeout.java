package org.mqttbee.mqtt5.handler.util;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import org.mqttbee.annotations.NotNull;
import org.mqttbee.api.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import org.mqttbee.mqtt5.handler.disconnect.Mqtt5DisconnectUtil;

import java.util.concurrent.TimeUnit;

/**
 * ChannelInboundHandler with timeout handling. Subclasses must not be {@link io.netty.channel.ChannelHandler.Sharable}.
 *
 * @author Silvio Giebl
 */
public abstract class ChannelInboundHandlerWithTimeout extends ChannelInboundHandlerAdapter
        implements Runnable, GenericFutureListener<Future<? super Void>> {

    private ChannelHandlerContext ctx;
    private ScheduledFuture<?> timeoutFuture;

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Schedules a timeout if the given future succeeded. Otherwise the channel is closed.
     *
     * @param future the future of the operation that triggers a timeout.
     */
    @Override
    public void operationComplete(final Future future) {
        if (future.isSuccess()) {
            scheduleTimeout();
        } else {
            Mqtt5DisconnectUtil.close(ctx.channel(), future.cause());
        }
    }

    /**
     * Invoked when a timeout happens. Sends a DISCONNECT message if the channel is still active and always closes the
     * channel.
     */
    @Override
    public void run() {
        if (!Thread.currentThread().isInterrupted()) {
            if (ctx.channel().isActive()) {
                Mqtt5DisconnectUtil.disconnect(ctx.channel(), getTimeoutReasonCode(), getTimeoutReasonString());
            } else {
                Mqtt5DisconnectUtil.close(ctx.channel(), getTimeoutReasonString());
            }
        }
    }

    /**
     * Schedules a timeout.
     */
    protected void scheduleTimeout() {
        timeoutFuture = ctx.executor().schedule(this, getTimeout(ctx), TimeUnit.SECONDS);
    }

    /**
     * Cancels a scheduled timeout.
     */
    protected void cancelTimeout() {
        if (timeoutFuture != null) {
            timeoutFuture.cancel(true);
            timeoutFuture = null;
        }
    }

    /**
     * Returns the timeout interval in seconds.
     *
     * @param ctx the channel handler context.
     * @return the timeout interval in seconds.
     */
    protected abstract long getTimeout(@NotNull ChannelHandlerContext ctx);

    /**
     * @return the Reason Code that will be used in the DISCONNECT message if a timeout happens and the channel is still
     * active.
     */
    @NotNull
    protected abstract Mqtt5DisconnectReasonCode getTimeoutReasonCode();

    /**
     * @return the Reason String that will be used to notify the API and may also be sent with a DISCONNECT message.
     */
    @NotNull
    protected abstract String getTimeoutReasonString();

}