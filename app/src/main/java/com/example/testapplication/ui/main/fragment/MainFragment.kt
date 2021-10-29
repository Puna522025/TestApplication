package com.example.testapplication.ui.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testapplication.R
import com.example.testapplication.ui.main.JsonData
import com.example.testapplication.ui.main.dto.City
import com.example.testapplication.ui.main.viewmodel.MainViewModel
import com.example.testapplication.ui.main.injection.CustomViewModelFactory
import com.example.testapplication.ui.main.viewmodel.StateData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var customViewModelFactory: CustomViewModelFactory

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this, customViewModelFactory).get(MainViewModel::class.java)
        viewModel.getCityDetailsObservable().observe(viewLifecycleOwner, performAction())

        viewModel.parseCityDetails(JsonData.paris)
    }

    private fun performAction(): Observer<StateData<City>?> {
        return Observer { t ->
            if (t != null) {
                when (t.getStatus()) {
                    StateData.DataStatus.SUCCESS -> {
                        Log.i("##City Name", "" + t.getData()?.cityDetails?.name)
                        Log.i("##City Rank", "" + t.getData()?.cityDetails?.rank)
                        return@Observer
                    }
                    StateData.DataStatus.ERROR -> if (t.getError() != null) {
                        Toast.makeText(
                            requireContext(),
                            "Error ${t.getError()!!.message}",
                            Toast.LENGTH_LONG
                        ).show()
                        return@Observer
                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "Error",
                            Toast.LENGTH_LONG
                        ).show()
                        return@Observer
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}