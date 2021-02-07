package com.katilijiwo.movieplot.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.katilijiwo.movieplot.ui.moviedetail.comment.CommentFragment
import com.katilijiwo.movieplot.ui.moviedetail.information.InformationFragment

class MovieDetailStateAdapter(fragment: Fragment, private val movieID: Int): FragmentStateAdapter(fragment)  {

    override fun getItemCount(): Int = TAB_COUNT
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                val fragment = InformationFragment()
                fragment.arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieID)
                }
                fragment
            }
            1 -> {
                val fragment = CommentFragment()
                fragment.arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieID)
                }
                fragment
            }
            else -> throw Exception()
        }
    }

    companion object {
        const val TAB_COUNT = 2
        const val MOVIE_ID = "movie_id"
    }

}