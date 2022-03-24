package com

import scalaz._

import com.vmware.vim25._
import com.vmware.vim25.mo._
import java.net._

/*
commands to trigger this script:
cs launch scalac:2.12.10 --  -d out -cp lib/vijava55b20130927.jar:lib/scalaz-core_2.12-7.0.9.jar com/listExtensions.scala
cs launch scala:2.12.10 -- -cp out:lib/scalaz-core_2.12-7.0.9.jar:lib/vijava55b20130927.jar:lib/dom4j-1.6.1.jar  com.runner

use scalaz.Validation in order to not use null keyword in code
*/

trait ClientInfo {
	def getClientInfo() : ValidationNel[Throwable, String]

	def getEventList() : ValidationNel[Throwable, String]

	def getExtendedProductInfo() : ValidationNel[Throwable, String]

	def getFaultList(): ValidationNel[Throwable, String]

	def getHealthInfo(): ValidationNel[Throwable, String]

	def getManagedEntityList(): ValidationNel[Throwable, String]

	def getOvfConsumerInfo() : ValidationNel[Throwable, String]

	def getPrivilegeList(): ValidationNel[Throwable, String]

	def getResourceList() : ValidationNel[Throwable, String]

	def getServerList(): ValidationNel[Throwable, String]

	def solutionManagerInfo() : ValidationNel[Throwable, String]

	def taskList() : ValidationNel[Throwable, String]
}

trait CompanyInfo {
	def getCompanyInfo() : String

	def getDescription() : String

	def getKey() : String
	
	def heartbeatTime() : String

	def shownInSolutionManager() : String

	def subjectName() : String

	def getType() : String

	def getVersion() : String
}

trait ExtensionProvider extends ClientInfo with CompanyInfo {
}

class ExtensionInfo(vcExtension: Extension) extends ExtensionProvider with ClientInfo with CompanyInfo {
	def getClientInfo() : ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "Clients["
			for (extClient <- vcExtension.client) {
				str += ("extensionClient.company = " + extClient.company)
				str += (", extensionClient.description.label = " + extClient.description.label)
				str += (", extensionClient.description.summary = " + extClient.description.summary)
				str += ",   "
			}
			str += "]"
			str
		}.toValidationNel[Throwable, String]
	}

	def getCompanyInfo() : String = {
		return vcExtension.company
	}

	def getDescription() : String = {
		return ".label = " + vcExtension.description.label + 
				", .summary = " + vcExtension.description.summary
	}

	def getEventList() : ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "Events["
			for (event <- vcExtension.eventList) {
				str += ("eventId = " + event.eventID + ", eventTypeSchema = " + event.eventTypeSchema)
			}
			str += "]"
			str
		}.toValidationNel[Throwable, String]
	}

	def getExtendedProductInfo() : ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "ExtendedProduct["
			str += "company url = " +  vcExtension.extendedProductInfo.companyUrl
			str += ", management url = " + vcExtension.extendedProductInfo.managementUrl
			str += ", product url = " + vcExtension.extendedProductInfo.productUrl
			str += ", self = " +  vcExtension.extendedProductInfo.self.asInstanceOf[VirtualMachine]
			str += "]"
			str
		}.toValidationNel[Throwable, String]
	}

	def getFaultList() : ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "FaultList["
			for (fault <- vcExtension.faultList) {
				str += "fault id = " + fault.faultID + ", "
			}
			str += "]"
			str
		}.toValidationNel[Throwable, String]
	}

	def getHealthInfo() : ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "Healthinfo["
			str += "healthinfo url = " + (if (vcExtension.healthInfo != null) vcExtension.healthInfo.url else "NULL")
			str += "]"
			str
		}.toValidationNel[Throwable, String]
	}

	def getKey() : String = {
		"Key: " + vcExtension.getKey()
	}

	def heartbeatTime() : String = {
		"Registration Time: " + vcExtension.getLastHeartbeatTime().getTime()
	}

	def getManagedEntityList() : ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "ManagedEntityList["
			for (managedEntity <- vcExtension.managedEntityInfo) {
				str += "managedEntity.description = " + managedEntity.description
				str += "managedEntity.smallIconUrl = " + managedEntity.smallIconUrl
				str += "managedEntity.type = " + managedEntity.getType()
			}
			str += "]"
			str
		}.toValidationNel[Throwable, String]
	}

	def getOvfConsumerInfo() : ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "OVFConsumerInfo["
			str += "callback url = " + vcExtension.ovfConsumerInfo.callbackUrl
			for (sType <- vcExtension.ovfConsumerInfo.sectionType) {
				str += "sectionType = " + sType
			}
			str += "]"
			str
		}.toValidationNel[Throwable, String]
	}

	def getPrivilegeList() : ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "PrivilegeList["
			for (privilege <- vcExtension.privilegeList) {
				str += "privilege group name = " + privilege.privGroupName
				str += "privilege ID = " + privilege.privID
			}
			str += "]"
			str
		}.toValidationNel[Throwable, String]
	}

	def getResourceList() : ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "**** Start resource list ****"
			for (resource <- vcExtension.resourceList) {
				str += "resource locale = " + resource.locale
				str += ", resource module = " + resource.module
			}
			str += "**** End resource list ****"
			str
		}.toValidationNel[Throwable, String]
	}

	def getServerList() : ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "**** Start Extension Servers ****"
			for (extServer <- vcExtension.server) {
				str += "Extension Server URL " + extServer.url + ", "
				}
			str += "**** End Extension Servers ****"
			str
		}.toValidationNel[Throwable, String]
	}

	def shownInSolutionManager() : String = {
		"shown in manager = " + vcExtension.shownInSolutionManager
	}

	def solutionManagerInfo(): ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "solutionManagerIcon = " + vcExtension.solutionManagerInfo.smallIconUrl
			str += ", solutionManagerTab = " + vcExtension.solutionManagerInfo.tab
			str	
		}.toValidationNel[Throwable, String]
	}

	def subjectName() : String = {
		return "subjectName = " + vcExtension.subjectName
	}

	def taskList() : ValidationNel[Throwable, String] = {
		Validation.fromTryCatch {
			var str = "**** Start task list ****"
			for (taskId <- vcExtension.taskList) {
				str += "taskId " + taskId.taskID
			}
			str += "**** End task list ****"
			str
		}.toValidationNel[Throwable, String]
	}

	def getType() : String = {
		return "Type: " + vcExtension.getType()
	}

	def getVersion() : String = {
		return "Version: " + vcExtension.getVersion()
	}
}


object runner {

	def printData(label: String, data: ValidationNel[Throwable, String]) = {
		data.fold(
			fail = ex => {
				println("no " + label + " found")
				for (e <- ex) {
					println("    reason " + e)
				}
			},
			succ = _ => {
			})

		for (x <- data) {
			println(label + ": " + x)
		}
	}

	def printData(label: String, data: String) = {
		println(label + " " + data)
	}

	def main(args: Array[String]) : Unit = {
		val si = new ServiceInstance(new URL("https://127.0.0.1/sdk"), "admin", "1234", true);
		val em = si.getExtensionManager();
		var i = 0

		for (extension <- em.getExtensionList()) {

			println("\n ---- Plugin # " + (i + 1) + " ---- ");
			val ei = new ExtensionInfo(extension)

			this.printData("clientInfo", ei.getClientInfo())
			this.printData("companyInfo", ei.getCompanyInfo())
			this.printData("description", ei.getDescription())
			this.printData("eventList", ei.getEventList())
			this.printData("extendedProductInfo", ei.getExtendedProductInfo())
			this.printData("faultList", ei.getFaultList())
			this.printData("healthInfo", ei.getHealthInfo())
			this.printData("key", ei.getKey())
			this.printData("registrationTime", ei.heartbeatTime())
			this.printData("managedEntityList", ei.getManagedEntityList())
			this.printData("ovfConsumerInfo", ei.getOvfConsumerInfo())
			this.printData("privilegeList", ei.getPrivilegeList())
			this.printData("resourceList", ei.getResourceList())
			this.printData("serverList", ei.getServerList())
			this.printData("shownInSolutionManager", ei.shownInSolutionManager())
			this.printData("solutionManagerInfo", ei.solutionManagerInfo())
			this.printData("subjectName", ei.subjectName())
			this.printData("taskList", ei.taskList())
			this.printData("type", ei.getType())
			this.printData("version", ei.getVersion())

			i += 1

		}

		si.getServerConnection.logout()
		println("total " + i + " extensions")
	}
}

