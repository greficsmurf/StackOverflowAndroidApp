package com.example.stackexchange.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchange.R
import com.example.stackexchange.databinding.RecyclerItemQuestionBinding
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.interfaces.QuestionsAdapterNavCallback
import com.example.stackexchange.ui.home.HomeFragmentDirections

class QuestionsPagedAdapter(
    private val navCallback: QuestionsAdapterNavCallback
) : PagingDataAdapter<SearchQuestion, RecyclerView.ViewHolder>(diffUtil){
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<SearchQuestion>(){
            override fun areItemsTheSame(oldItem: SearchQuestion, newItem: SearchQuestion) = oldItem.questionId == newItem.questionId

            override fun areContentsTheSame(oldItem: SearchQuestion, newItem: SearchQuestion) = newItem == oldItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<RecyclerItemQuestionBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recycler_item_question,
            parent,
            false
        )

        return QuestionViewHolder(binding, navCallback)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is QuestionViewHolder)
            getItem(position)?.let { holder.bind(it) }
    }
}

class QuestionViewHolder(
        private val binding: RecyclerItemQuestionBinding,
        private val navCallback: QuestionsAdapterNavCallback
) : RecyclerView.ViewHolder(binding.root){
    fun bind(question: SearchQuestion){
        binding.apply {
            title = question.title
            viewCount = question.viewCount.toString()
            answerCount = question.answerCount.toString()
            voteCount = question.score.toString()
            isAnswered = question.isAnswered

            layout.setOnClickListener {
                navCallback.navigate(it.findNavController(), question.link, question.title)
//                it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToQuestionFragment(question.link))
            }

            executePendingBindings()
        }
    }
}



