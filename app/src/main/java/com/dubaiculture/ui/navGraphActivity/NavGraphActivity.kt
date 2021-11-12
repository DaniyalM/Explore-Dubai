package om.dubaiculture.ui.navGraphActivity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.dubaiculture.R
import com.dubaiculture.ui.base.BaseActivity
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.hideKeyboard

import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NavGraphActivity : BaseActivity(),
    NavController.OnDestinationChangedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_out,R.anim.fade_in)

        setContentView(R.layout.activity_nav_graph)
        setupGraph()
        getNavControllerFun(R.id.navHostFragment).addOnDestinationChangedListener(this)

    }

    private fun setupGraph() {
        val controller = getNavControllerFun(R.id.navHostFragment)
        val bundle = intent.extras
        val graphResourceId: Int? = bundle?.getInt(Constants.NavBundles.GRAPH_ID)
        if (graphResourceId == null) {
            Timber.e("NavGraphActivity - graphResourceId not found")
            return
        }
        val graph = controller.navInflater.inflate(graphResourceId) //eg R.navigation.nav_menu
        controller.setGraph(graph, bundle)

    }


//    private fun getNavController(): NavController {
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
//        return navHostFragment.navController
//    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        currentFocus?.hideKeyboard()
    }

    override fun onDestroy() {
        getNavControllerFun(R.id.navHostFragment).removeOnDestinationChangedListener(this)
        currentFocus?.hideKeyboard()
        super.onDestroy()
    }
}