package com.example.crudapp.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudapp.R
import com.example.crudapp.dashboard.adapter.UsersDashboardAdapter
import com.example.crudapp.databinding.FragmentDashboardBinding
import com.example.crudapp.model.User
import com.google.android.material.snackbar.Snackbar


class DashboardFragment : Fragment() {


    private val viewModel: DashboardContract.ViewModel by lazy {
        ViewModelProvider(this, DashboardViewModelFactory.getInstance())[DashboardViewModel::class.java]
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUsersList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        binding.rvUsersList.adapter = UsersDashboardAdapter()

        viewModel.dashboardEvent.observe(viewLifecycleOwner, Observer (::renderViewEvent) )
        viewModel.dashboardState.observe(viewLifecycleOwner, Observer (::renderViewState) )

        viewModel.invokeAction(DashboardContract.Action.FetchUsers)

        binding.fabButtonAdd.setOnClickListener{
            findNavController().navigate(R.id.action_dashboardFragment_to_addUserFragment)
        }
    }

    private fun renderViewState(state: DashboardContract.State) {
        when(state){
            is DashboardContract.State.Error -> {
                showMessageSnackBar(state.res.message)
                binding.rvUsersList.isVisible = false
                binding.progressBar.isVisible = false
            }

            is DashboardContract.State.Loading -> {
                binding.rvUsersList.isVisible = false
                binding.progressBar.isVisible = true
            }
            is DashboardContract.State.Success -> {
                binding.rvUsersList.isVisible = true
                binding.progressBar.isVisible = false
                (binding.rvUsersList.adapter as UsersDashboardAdapter).bindItems(state.users)
            }
            is DashboardContract.State.SuccessMessage -> showMessageSnackBar(state.successMessage)
        }


}
    private fun showMessageSnackBar(message: String?) {
        binding.rvUsersList.isVisible = false
        binding.progressBar.isVisible = false
        val snackBar = Snackbar
            .make(
                binding.root,
                message ?: "API ERROR.. SORRY",
                Snackbar.LENGTH_SHORT
            )
        snackBar.show()
    }
    private fun renderViewEvent(event: DashboardContract.Event) {
        when(event)
        {
            DashboardContract.Event.DummyEvent -> showMessageSnackBar("state.res.message")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}