package com.example.stackexchange.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchange.R
import com.example.stackexchange.databinding.DialogSearchOptionsBinding
import com.example.stackexchange.databinding.RecyclerItemTagBinding
import com.example.stackexchange.domain.models.Tag
import kotlinx.android.synthetic.main.tag_square.view.*

class TagsAdapter(
        private val context: Context,
        private val selectedTagList: MutableList<Tag>
) : ListAdapter<Tag, RecyclerView.ViewHolder>(diffUtil){
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Tag>(){
            override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean = oldItem == newItem
        }
    }

    private val inflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: RecyclerItemTagBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.recycler_item_tag,
                parent,false
        )

        return TagViewHolder(binding, selectedTagList)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TagViewHolder)
            holder.bind(getItem(position))
    }
}

class TagViewHolder(
        private val binding: RecyclerItemTagBinding,
        private val selectedTagList: MutableList<Tag>
) : RecyclerView.ViewHolder(binding.root){
    fun bind(tag: Tag){
        binding.apply {
            tagText.text = tag.name
            setViewState(tagText, tag)
            tagText.setOnClickListener {
                changeViewState(it, tag)
            }
            executePendingBindings()
        }
    }

    private fun setViewState(view: View, tag: Tag){
        val selectedTag = selectedTagList.firstOrNull { clickedTag ->
            clickedTag.name == tag.name
        }
        view.isActivated = selectedTag != null
    }
    private fun changeViewState(view: View, tag: Tag){
        val selectedTag = selectedTagList.firstOrNull { clickedTag ->
            clickedTag.name == tag.name
        }

        selectedTag?.let {foundTag ->
            selectedTagList.remove(foundTag)
        } ?: selectedTagList.add(tag)

        view.isActivated = selectedTag == null
    }
}