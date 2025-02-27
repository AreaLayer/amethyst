package com.vitorpamplona.amethyst

import com.vitorpamplona.amethyst.model.Account
import com.vitorpamplona.amethyst.service.Constants
import com.vitorpamplona.amethyst.service.NostrAccountDataSource
import com.vitorpamplona.amethyst.service.NostrChannelDataSource
import com.vitorpamplona.amethyst.service.NostrChatroomListDataSource
import com.vitorpamplona.amethyst.service.NostrGlobalDataSource
import com.vitorpamplona.amethyst.service.NostrHomeDataSource
import com.vitorpamplona.amethyst.service.NostrNotificationDataSource
import com.vitorpamplona.amethyst.service.NostrSingleEventDataSource
import com.vitorpamplona.amethyst.service.NostrSingleUserDataSource
import com.vitorpamplona.amethyst.service.NostrThreadDataSource
import com.vitorpamplona.amethyst.service.NostrUserProfileDataSource
import com.vitorpamplona.amethyst.service.NostrUserProfileFollowersDataSource
import com.vitorpamplona.amethyst.service.NostrUserProfileFollowsDataSource
import com.vitorpamplona.amethyst.service.relays.Client

object ServiceManager {
  private var account: Account? = null

  fun start(account: Account) {
    this.account = account
    start()
  }

  fun start() {
    val myAccount = account

    if (myAccount != null) {
      Client.connect(myAccount.activeRelays() ?: Constants.defaultRelays)

      // start services
      NostrAccountDataSource.account = myAccount
      NostrHomeDataSource.account = myAccount
      NostrNotificationDataSource.account = myAccount
      NostrChatroomListDataSource.account = myAccount

      NostrAccountDataSource.start()
      NostrGlobalDataSource.start()
      NostrHomeDataSource.start()
      NostrNotificationDataSource.start()
      NostrSingleEventDataSource.start()
      NostrSingleUserDataSource.start()
      NostrThreadDataSource.start()
      NostrChatroomListDataSource.start()
    } else {
      // if not logged in yet, start a basic service wit default relays
      Client.connect()
    }
  }

  fun pause() {
    NostrAccountDataSource.stop()
    NostrHomeDataSource.stop()
    NostrChannelDataSource.stop()
    NostrChatroomListDataSource.stop()
    NostrUserProfileDataSource.stop()
    NostrUserProfileFollowersDataSource.stop()
    NostrUserProfileFollowsDataSource.stop()

    NostrGlobalDataSource.stop()
    NostrNotificationDataSource.stop()
    NostrSingleEventDataSource.stop()
    NostrSingleUserDataSource.stop()
    NostrThreadDataSource.stop()

    Client.disconnect()
  }

}