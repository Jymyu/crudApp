package com.example.crudapp.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.databinding.UsersAdapterItemBinding
import com.example.crudapp.model.User

class UsersDashboardAdapter(
) : ListAdapter<User,UsersDashboardAdapter.UserViewHolder>(UserDiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemBinding = UsersAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemBinding)
    }

    fun bindItems(items: List<User>?) {
        submitList(items)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class UserViewHolder(private val adapterItem: UsersAdapterItemBinding) : RecyclerView.ViewHolder(adapterItem.root) {
        private val name = adapterItem.tvNameAdapterItem
        private val birthDate = adapterItem.tvBirthdateAdapterItem
        fun bind(user: User) {
           name.text = user.name
           birthDate.text = user.birthDate
        }

    }

    private class UserDiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.name == newItem.name && oldItem.birthDate == newItem.birthDate
    }
}