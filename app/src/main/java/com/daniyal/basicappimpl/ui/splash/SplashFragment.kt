package com.daniyal.basicappimpl.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniyal.basicappimpl.data.repository.photo.remote.response.PhotoDTO
import com.daniyal.basicappimpl.databinding.FragmentSplashBinding
import com.daniyal.basicappimpl.ui.base.BaseFragment
import com.daniyal.basicappimpl.ui.callbacks.GroupieInterface
import com.daniyal.basicappimpl.ui.home.adapters.MainViewItem
import com.daniyal.basicappimpl.ui.home.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_splash.*

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(), GroupieInterface<PhotoDTO> {
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSplashBinding.inflate(inflater, container, false)

    private val mainViewModel: MainViewModel by viewModels()
    var photoDTOs: List<PhotoDTO> = listOf(
        PhotoDTO("1", "this is description 1", 21),
        PhotoDTO("2", "this is description 2", 22),
        PhotoDTO("3", "this is description 3", 23),
        PhotoDTO("4", "this is description 4", 24),
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(mainViewModel)
        setupRecyclerView()
        fillRecyclerView(photoDTOs)
    }

    private fun fillRecyclerView(items: List<PhotoDTO>) {
        items.forEach { photoDTO ->
            groupAdapter?.add(MainViewItem(photoDTO, this))
        }
    }


    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(activity)
        mainRv.apply {
            layoutManager = linearLayoutManager
            adapter = groupAdapter
        }

    }

    override fun invokeSingleItemClick(item: PhotoDTO, position: Int) {
        mainViewModel.showToast("${item.desc} - ${position}")
    }

    //
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewLifecycleOwner.lifecycleScope.launch {
//           navigate()
//       }
//
//
//   }
//
//    private suspend fun navigate(){
//        delay(3000)
//        findNavController(this).navigate(R.id.action_splashFragment_to_loginFragment)
//    }


}