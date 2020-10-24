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

package wtf.cmyk.amaterasu.bot

import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.{ChunkingFilter, MemberCachePolicy}
import net.dv8tion.jda.api.utils.cache.CacheFlag
import net.dv8tion.jda.api.{JDA, JDABuilder}

class AmaterasuClient(token: String, ownerID: Long) {
  val jda: JDA = JDABuilder.createDefault(token)
    .setActivity(Activity.watching("over this server."))
    .setMemberCachePolicy(MemberCachePolicy.ALL)
    .setChunkingFilter(ChunkingFilter.ALL)
    .disableCache(CacheFlag.CLIENT_STATUS, CacheFlag.VOICE_STATE, CacheFlag.EMOTE, CacheFlag.ACTIVITY)
    .disableIntents(
      GatewayIntent.GUILD_MESSAGE_TYPING,
      GatewayIntent.DIRECT_MESSAGES,
      GatewayIntent.GUILD_VOICE_STATES
    )
    .enableIntents(GatewayIntent.GUILD_MEMBERS)
    .build()

  jda.awaitReady()
}
