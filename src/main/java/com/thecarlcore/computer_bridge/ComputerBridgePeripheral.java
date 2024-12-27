package com.thecarlcore.computer_bridge;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import net.minecraft.resources.ResourceLocation;

public class ComputerBridgePeripheral implements GenericPeripheral {
    @Override
    public String id() {
        return new ResourceLocation(ComputerBridge.MODID, "computer_bridge").toString();
    }

    @LuaFunction
    public String recv(ComputerBridgeEntity bridge) {
        return "Input text";
    }

    @LuaFunction
    public void send(ComputerBridgeEntity bridge, String toSend) {
        System.out.println("Input: " + toSend);
    }
}
