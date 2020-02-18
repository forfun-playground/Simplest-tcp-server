package forfun.sandbox.uwns.bothub.network;

import forfun.sandbox.uwns.bothub.bot.BotFactory;
import forfun.sandbox.uwns.bothub.network.handler.ChannelOnActiveHandler;
import forfun.sandbox.uwns.bothub.network.handler.ChannelOnExceptionHandler;
import forfun.sandbox.uwns.bothub.network.handler.ChannelOnInactiveHandler;
import forfun.sandbox.uwns.bothub.network.handler.FlatEnterWorldResponseHandler;
import forfun.sandbox.uwns.bothub.network.handler.FlatSnapshotResponseHandler;
import forfun.sandbox.uwns.bothub.network.handler.FlatTargetPositionRequestHandler;
import forfun.sandbox.uwns.bothub.network.handler.JsonEnterWorldResponseHandler;
import forfun.sandbox.uwns.bothub.network.handler.JsonSnapshotResponseHandler;
import forfun.sandbox.uwns.bothub.network.handler.JsonTargetPositionRequestHandler;
import forfun.sandbox.uwns.bothub.network.handler.ProtoEnterWorldResponseHandler;
import forfun.sandbox.uwns.bothub.network.handler.ProtoSnapshotResponseHandler;
import forfun.sandbox.uwns.bothub.network.handler.ProtoTargetPositionRequestHandler;
import forfun.sandbox.uwns.shared.codec.FlatbufferDecoder;
import forfun.sandbox.uwns.shared.codec.FlatbufferEncoder;
import forfun.sandbox.uwns.shared.codec.JsonDecoder;
import forfun.sandbox.uwns.shared.codec.JsonEncoder;
import forfun.sandbox.uwns.shared.pack.Packtype;
import forfun.sandbox.uwns.shared.pack.proto.Message;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NetworkInitializer extends ChannelInitializer<SocketChannel> {

    private final Packtype packtype;
    private final BotFactory botFactory;

    public NetworkInitializer(
            Packtype packtype,
            BotFactory botFactory
    ) {
        super();
        this.packtype = packtype;
        this.botFactory = botFactory;
    }

    @Override
    protected void initChannel(SocketChannel sch) throws Exception {
        ChannelPipeline pipeline = sch.pipeline();
        pipeline.addLast("FrameEncoder", new LengthFieldPrepender(2, true));
        pipeline.addLast("FrameDecoder", new LengthFieldBasedFrameDecoder(0x80000, 0, 2, -2, 2));

        switch (packtype) {
            case Protobuf:
                log.debug("Initialize Protobuf pack");
                pipeline.addLast("ProtobufEncoder", new ProtobufEncoder());
                pipeline.addLast("ProtobufDecoder", new ProtobufDecoder(Message.getDefaultInstance()));
                pipeline.addLast("EnterWorldResponseHandler", new ProtoEnterWorldResponseHandler());
                pipeline.addLast("SnapshotResponseHandler", new ProtoSnapshotResponseHandler());
                pipeline.addLast("TargetPositionRequestHandler", new ProtoTargetPositionRequestHandler());
                break;
            case Flatbuffer:
                log.debug("Initialize Flatbuffer pack");
                pipeline.addLast("FlatbufferEncoder", new FlatbufferEncoder());
                pipeline.addLast("FlatbufferDecoder", new FlatbufferDecoder());
                pipeline.addLast("EnterWorldResponseHandler", new FlatEnterWorldResponseHandler());
                pipeline.addLast("SnapshotResponseHandler", new FlatSnapshotResponseHandler());
                pipeline.addLast("TargetPositionRequestHandler", new FlatTargetPositionRequestHandler());
                break;
            case Json:
                log.debug("Initialize Json pack");
                pipeline.addLast("JsonEncoder", new JsonEncoder());
                pipeline.addLast("JsonDecoder", new JsonDecoder());
                pipeline.addLast("EnterWorldResponseHandler", new JsonEnterWorldResponseHandler());
                pipeline.addLast("SnapshotResponseHandler", new JsonSnapshotResponseHandler());
                pipeline.addLast("TargetPositionRequestHandler", new JsonTargetPositionRequestHandler());
                break;                
            default:
                log.error("Protocol " + packtype.name() + " not implemented");
                break;
        }

        pipeline.addLast("ChannelActiveHandler", new ChannelOnActiveHandler(packtype, botFactory));
        pipeline.addLast("ChannelInactiveHandler", new ChannelOnInactiveHandler());
        pipeline.addLast("ExceptionHandler", new ChannelOnExceptionHandler());

    }

}
