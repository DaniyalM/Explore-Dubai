package com.app.dubaiculture.ui.postLogin.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.data.repository.explore.local.models.*
import com.app.dubaiculture.databinding.FragmentExploreBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.explore.adapters.ExploreRecyclerAsyncAdapter
import com.app.dubaiculture.ui.postLogin.explore.viewmodel.ExploreViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>() {
    @Inject
    lateinit var glide: RequestManager

    private val exploreViewModel: ExploreViewModel by viewModels()
    private lateinit var explore: ExploreRecyclerAsyncAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentExploreBinding.inflate(inflater, container, false)


    fun getRecyclerView() = binding.rvExplore

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        explore = ExploreRecyclerAsyncAdapter(activity)
        subscribeToObservable()
        setUpRecyclerView()
        createTestItems()
    }

    private fun setUpRecyclerView() {

//        explore.items = createTestItems()
        binding.rvExplore.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(activity)
            adapter = explore
            explore.provideGlideInstance(glide)

        }

    }

    private fun subscribeToObservable() {
        exploreViewModel.getExploreToScreen()
        exploreViewModel.exploreList.observe(viewLifecycleOwner) {
           it.let { explore.items=it }
        }

    }


    private fun createAttractionItems(): List<Attraction> = mutableListOf<Attraction>().apply {

        repeat((1..70).count()) {
            add(
                Attraction(
                    it - 1,
                    "dubai culture ${it}",
                    "https://cdn-sharing.adobecc.com/id/urn:aaid:sc:US:a8b582cb-91d1-4561-b05f-cfe1c0e7b414;version=0?component_id=a46d108d-0cd1-4619-86d9-53e449a87c1e&api_key=CometServer1&access_token=1611608278_urn%3Aaaid%3Asc%3AUS%3Aa8b582cb-91d1-4561-b05f-cfe1c0e7b414%3Bpublic_827967f49e41aad27f7dd2bb859c4045dc9c846e"
                )
            )
        }
    }
    private fun createUpComingItems(): List<UpComingEvents> = mutableListOf<UpComingEvents>().apply {

            repeat((1..70).count()) {
                add(
                    UpComingEvents(
                        it - 1,
                        "dubai culture ${it}",
                        "https://cdn-sharing.adobecc.com/id/urn:aaid:sc:US:a8b582cb-91d1-4561-b05f-cfe1c0e7b414;version=0?component_id=a46d108d-0cd1-4619-86d9-53e449a87c1e&api_key=CometServer1&access_token=1611608278_urn%3Aaaid%3Asc%3AUS%3Aa8b582cb-91d1-4561-b05f-cfe1c0e7b414%3Bpublic_827967f49e41aad27f7dd2bb859c4045dc9c846e",
                        "",
                        "",
                        "",
                        "",
                        "",
                        false,
                        ""
                    )
                )
            }
        }
    private fun createMustSeeItems(): List<MustSee> = mutableListOf<MustSee>().apply {

        repeat((1..70).count()) {
            add(

                MustSee(
                    it - 1,
                    "dubai culture ${it}",
                    "https://cdn-sharing.adobecc.com/id/urn:aaid:sc:US:a8b582cb-91d1-4561-b05f-cfe1c0e7b414;version=0?component_id=a46d108d-0cd1-4619-86d9-53e449a87c1e&api_key=CometServer1&access_token=1611608278_urn%3Aaaid%3Asc%3AUS%3Aa8b582cb-91d1-4561-b05f-cfe1c0e7b414%3Bpublic_827967f49e41aad27f7dd2bb859c4045dc9c846e",
                    false,
                    "",

                    )
            )
        }
    }

    private fun createPopularServiceItems(): List<PopularServices> = mutableListOf<PopularServices>().apply {

        repeat((1..70).count()) {
            add(

                PopularServices(
                    it - 1,
                    "dubai culture ${it}",
                    "https://cdn-sharing.adobecc.com/id/urn:aaid:sc:US:a8b582cb-91d1-4561-b05f-cfe1c0e7b414;version=0?component_id=a46d108d-0cd1-4619-86d9-53e449a87c1e&api_key=CometServer1&access_token=1611608278_urn%3Aaaid%3Asc%3AUS%3Aa8b582cb-91d1-4561-b05f-cfe1c0e7b414%3Bpublic_827967f49e41aad27f7dd2bb859c4045dc9c846e",
                )
            )
        }
    }

    private fun createLatestNewsItems(): List<LatestNews> = mutableListOf<LatestNews>().apply {

        repeat((1..70).count()) {
            add(

                LatestNews(
                    it - 1,
                    "dubai culture ${it}",
                    "",
                    "https://cdn-sharing.adobecc.com/id/urn:aaid:sc:US:a8b582cb-91d1-4561-b05f-cfe1c0e7b414;version=0?component_id=a46d108d-0cd1-4619-86d9-53e449a87c1e&api_key=CometServer1&access_token=1611608278_urn%3Aaaid%3Asc%3AUS%3Aa8b582cb-91d1-4561-b05f-cfe1c0e7b414%3Bpublic_827967f49e41aad27f7dd2bb859c4045dc9c846e",
                    ""
                )
            )
        }
    }
    private fun createTestItems(): List<TestItem> = mutableListOf<TestItem>().apply {
        repeat((1..3).count()) {

            add(
                TestItem(
                    it - 1,
                    "description $it",
                    it,
                    "title $it",
                    it,
                    it,
                    createAttractionItems(),
                    createUpComingItems(),
                    createMustSeeItems(),
                    createPopularServiceItems(),
                    createLatestNewsItems()
                )
            )
        }
    }

}