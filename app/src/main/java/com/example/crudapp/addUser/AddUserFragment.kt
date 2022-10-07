package com.example.crudapp.addUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.crudapp.databinding.FragmentAddUserBinding
import com.google.android.material.snackbar.Snackbar


class AddUserFragment : Fragment() {


    private val viewModel: AddUserContract.ViewModel by lazy {
        ViewModelProvider(this, AddUserViewModelFactory.getInstance())[AddUserViewModel::class.java]
    }

    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddUser.setOnClickListener{
            onClickSaveUser()
        }
        viewModel.addUserState.observe(viewLifecycleOwner, Observer (::renderViewState) )
    }

    private fun onClickSaveUser() {
        val name = binding.etName.text.toString()
        val birthdate = binding.etBirthdate.text.toString()

        viewModel.invokeAction(AddUserContract.Action.PutUser(name,birthdate))
    }

    private fun renderViewState(state: AddUserContract.State) {
        when(state){
            is AddUserContract.State.Error -> {
                showMessageSnackBar(state.errorMessage)
            }

            is AddUserContract.State.Loading -> {
                binding.progressBar.isVisible = true
            }
            is AddUserContract.State.SuccessMessage -> {
                binding.progressBar.isVisible = false
                showMessageSnackBar(state.successMessage)
                activity?.onBackPressed()
            }
        }


}
    private fun showMessageSnackBar(message: String?) {
        val snackBar = Snackbar
            .make(
                binding.root,
                message ?: "API ERROR.. SORRY",
                Snackbar.LENGTH_SHORT
            )
        snackBar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}