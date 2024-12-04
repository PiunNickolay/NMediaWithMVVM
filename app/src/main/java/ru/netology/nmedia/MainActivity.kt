package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this){ post ->
            with(binding) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)
                likeCount!!.text = countFormat(post.likeCount)
                shareCount!!.text = countFormat(post.shareCount)
                viewsCount!!.text = countFormat(post.viewsCount)
            }
        }

            binding.like.setOnClickListener{
                viewModel.like()
        }
            binding.share.setOnClickListener {
                viewModel.share()
            }

    }
    fun countFormat(count: Int): String {
        if (count >= 1_000_000) {
            val millions = count / 1_000_000
            val remainder = (count % 1_000_000) / 100_000
            return if (remainder > 0) {
                "$millions.${remainder}M"
            } else {
                "$millions M"
            }
        } else if (count >= 10_000) {
            val thousands = count / 1_000
            return "$thousands K"
        } else if (count >= 1_100) {
            val thousands = count / 1_000
            val remainder = (count % 1_000) / 100
            return if (remainder > 0) {
                "$thousands.${remainder}K"
            } else {
                "$thousands K"
            }
        } else if (count >= 1_000) {
            return "${count / 1_000}K"
        } else {
            return count.toString()
        }
    }
}