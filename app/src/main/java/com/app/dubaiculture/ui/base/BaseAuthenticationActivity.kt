package com.app.dubaiculture.ui.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.navigation.fragment.NavHostFragment
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.app.dubaiculture.databinding.ActivityGenericBinding
import com.app.dubaiculture.ui.postLogin.attractions.AttractionActivity
import com.app.dubaiculture.ui.postLogin.events.EventActivity
import com.app.dubaiculture.ui.postLogin.explore.ExploreActivity
import com.app.dubaiculture.ui.postLogin.more.MoreActivity
import com.app.dubaiculture.ui.preLogin.PreLoginActivity
import com.app.dubaiculture.utils.killSessionAndStartNewActivity
import com.app.dubaiculture.utils.startNewActivityWithPre
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseAuthenticationActivity : BaseActivity() {

    protected lateinit var binding: ActivityGenericBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        checkLoginStatus()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        baseOnCreate(savedInstanceState)
    }

    private fun checkLoginStatus() {

        //IF User has logged In Proceed with Activity Other Wise Navigate User to Login Screen
        //We will get the User Session From DataStore to check If its LoggedIn Or not
        if (!applicationEntry.auth.isLoggedIn) {
            killSessionAndStartNewActivity(PreLoginActivity::class.java)
        }


    }

    protected abstract fun baseOnCreate(savedInstanceState: Bundle?)

    protected fun BottomInit(bottomNav: BottomNavigationView, itemId: Int) {
        bottomNav.apply {
            selectedItemId = itemId
            setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.exploreFragment -> startNewActivityWithPre(ExploreActivity::class.java)
                        R.id.eventsFragment -> startNewActivityWithPre(EventActivity::class.java)
                        R.id.attractionsFragment -> startNewActivityWithPre(AttractionActivity::class.java)
                        R.id.moreFragment -> startNewActivityWithPre(MoreActivity::class.java)
                    }

                    return false

                }
            })
        }

    }

    protected fun setupViews(navigation: Int,bottomNav: BottomNavigationView) {
//        val fragmentContainer = findViewById<View>(R.id.nav_host_fragment)
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val graph = navController.navInflater.inflate(navigation)
        navHostFragment.navController.graph = graph
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.threeSixtyFragment -> {
                    bottomNav.visibility = View.GONE
                }
                R.id.registrationSuccessFragment2 -> {
                    bottomNav.visibility = View.GONE
                }
                R.id.ARFragment -> {
                    bottomNav.visibility = View.GONE
                }
                R.id.siteMapFragment -> {
                    bottomNav.visibility = View.GONE
                }
                R.id.ibeconFragment -> {
                    bottomNav.visibility = View.GONE
                }
                R.id.attractionGalleryFragment -> {
                    bottomNav.visibility = View.GONE
                }
                else -> {
                    bottomNav.visibility = View.VISIBLE

                }
            }


        }


    }


    fun favouriteClick(
            checkbox: CheckBox,
            isFav: Boolean,
            nav: Int,
            itemId: String,
            baseViewModel: BaseViewModel,
            type: Int = 2,
    ) {
        checkBox = checkbox
        if (applicationEntry.auth.isGuest) {
            navigate(nav)
        } else {
            if (!isFav) {
                applicationEntry.auth.user?.let {
                    baseViewModel.addToFavourites(
                            AddToFavouriteRequest(
                                    userId = applicationEntry.auth.user?.userId,
                                    itemId = itemId,
                                    type = type
                            )
                    )
                }
            } else {
                applicationEntry.auth.user?.let {
                    baseViewModel.addToFavourites(
                            AddToFavouriteRequest(
                                    userId = applicationEntry.auth.user?.userId,
                                    itemId = itemId,
                                    type = type
                            )
                    )
                }
            }

        }
    }
}