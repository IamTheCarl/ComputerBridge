package com.thecarlcore.computer_bridge;

import li.cil.oc2r.api.bus.device.object.Callback;
import li.cil.oc2r.api.bus.device.object.DocumentedDevice;
import li.cil.oc2r.api.bus.device.object.NamedDevice;
import li.cil.oc2r.common.blockentity.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class ComputerBridgeEntity extends ModBlockEntity implements NamedDevice, DocumentedDevice {
    private static final String RECV = "recv";
    private static final String SEND = "send";

    public ComputerBridgeEntity(BlockPos position, BlockState state) {
        super(ComputerBridge.COMPUTER_BRIDGE_ENTITY.get(), position, state);
    }

    @Override
    public void getDeviceDocumentation(DeviceVisitor visitor) {
        visitor.visitCallback(RECV)
                .description("Receive the next message from the remote system")
                .returnValueDescription("The message received, or None if no message came in.");

        visitor.visitCallback(SEND)
                .description("Send a message from the remote system")
                .parameterDescription("message", "The message to send to the remote system");
    }

    @Override
    public Collection<String> getDeviceTypeNames() {
        return Collections.singletonList("computer_bridge");
    }

    @Callback(name = SEND)
    public void send(String message) {
        System.out.println("SEND");
    }

    @Callback(name = RECV)
    public String recv() {
        return null;
    }
}
