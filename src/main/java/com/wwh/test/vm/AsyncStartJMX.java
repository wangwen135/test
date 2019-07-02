package com.wwh.test.vm;

import java.io.IOException;
import java.util.Properties;

import com.sun.tools.attach.VirtualMachine;

public class AsyncStartJMX {

    public static void main(String[] args) throws Exception, IOException {

        // attach to target VM
        VirtualMachine vm = VirtualMachine.attach("9172");

        // start management agent
        Properties props = new Properties();
        props.put("com.sun.management.jmxremote.port", "5000");
        vm.startManagementAgent(props);

        // detach
        vm.detach();
    }
}
