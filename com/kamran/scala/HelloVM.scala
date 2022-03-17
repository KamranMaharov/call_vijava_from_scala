package com.kamran.scala

import com.vmware.vim25.mo._;
import java.net._;
import com.vmware.vim25._;

object Runner {
	def main(args: Array[String]) : Unit = {
		println("inside Runner.main")
		
		val si = new ServiceInstance(new URL("https://127.0.0.1/sdk"), "admin", "passwd", true);
		si.getSessionManager().setLocale("zh-CN"); // set locale for the content of all API result.
		val rootFolder = si.getRootFolder();
		val name = rootFolder.getName();
		println("vc root folder:" + name);

		val mes = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
		
		if (mes == null || mes.length == 0) {
			return;
		}
		println("found " + mes.length + " virtual machines")
		
		for (idx <- 0 to mes.length - 1) {
			val vm = mes(idx).asInstanceOf[VirtualMachine];
			val vminfo = vm.getConfig();
			val vmc = vm.getCapability();
			
			println(vm.getName());
			if (vm.getName().equals("127.0.0.1")) {
			
				println("Name: " + vm.getName());
				println("IP: " + vm.getGuest().getIpAddress());
				println("FQDN: " + vm.getGuest().getHostName());
				println("MOID: " + vm.getMOR());
				println("InstanceUUID: " + vm.getSummary().getConfig().getInstanceUuid());
				println("GuestOS: " + vminfo.getGuestFullName());
				println("Multiple snapshot supported: " + vmc.isMultipleSnapshotsSupported());
			
			}
		}
		si.getServerConnection().logout();
			
	}
		
}
