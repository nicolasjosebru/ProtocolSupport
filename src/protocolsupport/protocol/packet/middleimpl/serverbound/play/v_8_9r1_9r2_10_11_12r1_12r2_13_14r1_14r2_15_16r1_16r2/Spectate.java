package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSpectate;
import protocolsupport.protocol.serializer.UUIDSerializer;

public class Spectate extends MiddleSpectate {

	public Spectate(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		entityUUID = UUIDSerializer.readUUID2L(clientdata);
	}

}
