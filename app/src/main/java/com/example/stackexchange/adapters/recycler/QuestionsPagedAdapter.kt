package com.example.stackexchange.adapters.recycler

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.*
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
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
import timber.log.Timber

class QuestionsPagedAdapter(
    private val navCallback: QuestionsAdapterNavCallback,
    private val context: Context
) : PagingDataAdapter<SearchQuestion, RecyclerView.ViewHolder>(diffUtil){
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<SearchQuestion>(){
            override fun areItemsTheSame(oldItem: SearchQuestion, newItem: SearchQuestion) = oldItem.questionId == newItem.questionId
            override fun areContentsTheSame(oldItem: SearchQuestion, newItem: SearchQuestion) = newItem == oldItem
        }
    }

    private val inflater by lazy {
        LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<RecyclerItemQuestionBinding>(
            inflater,
            R.layout.recycler_item_question,
            parent,
            false
        )

        return QuestionViewHolder(binding, navCallback, inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is QuestionViewHolder)
            getItem(position)?.let { holder.bind(it) }
    }
}

class QuestionViewHolder(
        private val binding: RecyclerItemQuestionBinding,
        private val navCallback: QuestionsAdapterNavCallback,
        private val layoutInflater: LayoutInflater
) : RecyclerView.ViewHolder(binding.root){
    private val USER_INFO_ID = 0

    private val displayMetrics by lazy {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R)
            (binding.root.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        else
            binding.root.context.display
    }

    fun bind(question: SearchQuestion){
        binding.apply {
            title = question.title
            viewCount = question.viewCount.toString()
            answerCount = question.answerCount.toString()
            voteCount = question.score.toString()
            isAnswered = question.isAnswered

            layout.setOnClickListener {
                navCallback.navigate(it.findNavController(), question.link, question.title)
            }

            layout.setOnCreateContextMenuListener { menu, v, menuInfo ->
                menu.add(Menu.NONE, USER_INFO_ID, Menu.NONE, "User info").setOnMenuItemClickListener {
                    navCallback.navigateToUser(itemView.findNavController(), question.owner.userId)

                    true
                }
            }
            tagsViewGroup.removeAllViews()
            var currentLL = LinearLayout(root.context)
            tagsViewGroup.addView(currentLL)

            var currentWidth = 0

            for(tag in question.tags)
            {
                val tagTextView = createTagTextView().apply {
                    text = tag
                    setOnClickListener {
                        navCallback.navigateToTagSearch(it.findNavController(), listOf(tag))
                    }
                    measure(0,0)
                }
                currentWidth += tagTextView.measuredWidth

                if(currentWidth >= displayMetrics!!.width - tagsViewGroup.marginLeft * 2)
                {
                    currentLL = LinearLayout(root.context)
                    tagsViewGroup.addView(currentLL)
                    currentWidth = 0
                }

                currentLL.addView(tagTextView)
            }


            executePendingBindings()
        }
    }

    private fun createTagTextView() = TextView(binding.root.context).apply {
        layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                context.resources.getDimension(R.dimen.tag_rect_height).toInt()
        ).apply {
            setMargins(0, 0, 10, 5)
            background = ContextCompat.getDrawable(context, R.drawable.tag_drawable)
        }
        isSingleLine = true
        isClickable = true
        isFocusable = true
        ellipsize = TextUtils.TruncateAt.END
        gravity = Gravity.CENTER
    }
}



