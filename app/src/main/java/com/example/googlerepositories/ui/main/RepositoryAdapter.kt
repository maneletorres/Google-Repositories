package com.example.googlerepositories.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.googlerepositories.R
import com.example.googlerepositories.data.Repository
import com.example.googlerepositories.databinding.ItemRepositoryBinding
import com.example.googlerepositories.util.extensions.getString
import com.example.googlerepositories.util.extensions.inflate
import com.example.googlerepositories.util.extensions.loadImage
import com.example.googlerepositories.util.extensions.validate
import kotlin.properties.Delegates

typealias Listener = (Repository) -> Unit

class RepositoryAdapter(
    repositories: List<Repository> = listOf(),
    private val listener: Listener
) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var repositories: List<Repository> by Delegates.observable(repositories) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder(parent.inflate(R.layout.item_repository))

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val currentRepository = repositories[position]
        holder.apply {
            bind(currentRepository)
            itemView.setOnLongClickListener {
                listener(currentRepository)
                true
            }
        }
    }

    override fun getItemCount(): Int = repositories.size

    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRepositoryBinding.bind(itemView)

        fun bind(repository: Repository) {
            with(binding) {
                repository.apply {
                    if (fork) repositoryCardView.validate(itemView)
                    loginImageView.loadImage(owner.avatarUrl)
                    nameTextView.text = name
                    descriptionTextView.text =
                        description ?: itemView.getString(R.string.no_description)
                }
            }
        }
    }
}