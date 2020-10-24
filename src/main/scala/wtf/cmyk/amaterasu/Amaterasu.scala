/*
 *     Copyright (C) 2020  jaywalkn / cmyk
 *
 *     Amaterasu is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Amaterasu is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Amaterasu.  If not, see <https://www.gnu.org/licenses/>.
 */

package wtf.cmyk.amaterasu

import java.lang.management.{ManagementFactory, RuntimeMXBean}

import org.ekrich.config.{Config, ConfigFactory}
import wtf.cmyk.amaterasu.api.AmaterasuAPI
import wtf.cmyk.amaterasu.api.AmaterasuAPI.{BotInfo, JVMInfo, OSInfo}
import wtf.cmyk.amaterasu.bot.AmaterasuClient
import wvlet.airframe.http.Router
import wvlet.airframe.http.finagle.{Finagle, FinagleServerConfig}

object Amaterasu {
  val config: Config = ConfigFactory.load()
  config.checkValid(ConfigFactory.defaultReference())

  val client: AmaterasuClient = new AmaterasuClient(config.getString("bot.token"), config.getLong("bot.ownerID"))

  private val router: Router = Router.add[AmaterasuAPI]

  private val server: FinagleServerConfig = Finagle.server
    .withPort(config.getInt("api.port"))
    .withRouter(router)

  private val bean: RuntimeMXBean = ManagementFactory.getRuntimeMXBean
  private val runtime: Runtime = Runtime.getRuntime

  private val version: String = config.getString("version")
  private val vmName: String = bean.getSystemProperties.get("java.vm.name")
  private val osName: String = bean.getSystemProperties.get("os.name")
  private val osArch: String = bean.getSystemProperties.get("os.arch")

  def getInfo: BotInfo = {
    val uptime: String = bean.getUptime.toString
    val memUsed: Double = (runtime.totalMemory() - runtime.freeMemory()) / 1000000
    val jvmInfo = JVMInfo(vmName, memUsed)
    val osInfo = OSInfo(osName, osArch)
    BotInfo(version, uptime, jvmInfo, osInfo)
  }

  def main(args: Array[String]): Unit = {
    server.start(_.waitServerTermination)
  }

}
