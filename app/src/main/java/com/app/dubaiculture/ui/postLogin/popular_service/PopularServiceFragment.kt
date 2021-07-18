package com.app.dubaiculture.ui.postLogin.popular_service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.databinding.FragmentPopularServiceBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.viewmodels.PopularServiceViewModel
import com.app.dubaiculture.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularServiceFragment : BaseFragment<FragmentPopularServiceBinding>() {
    private val popularServiceViewModel: PopularServiceViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPopularServiceBinding.inflate(inflater, container, false)

    fun subscribeToObservable(){
        popularServiceViewModel.eServices.observe(viewLifecycleOwner){
            when(it){
                is Result.Success ->{}
                is Result.Failure->handleApiError(it,popularServiceViewModel)
            }
        }
    }


}