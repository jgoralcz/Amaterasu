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

package wtf.cmyk.amaterasu.api

import wtf.cmyk.amaterasu.Amaterasu
import wtf.cmyk.amaterasu.api.AmaterasuAPI.BotInfo
import wvlet.airframe.http.Endpoint

object AmaterasuAPI {
  case class JVMInfo(vmName: String, memUsed: Double)
  case class OSInfo(name: String, arch: String)
  case class BotInfo(version: String, uptime: String, jvm: JVMInfo, os: OSInfo)
}

@Endpoint(path = "/api")
class AmaterasuAPI {

  @Endpoint(path = "/info")
  def getInfo: BotInfo = Amaterasu.getInfo

}
