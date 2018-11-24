package org.roningrum.footballsubmission3.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.roningrum.footballsubmission3.R
import org.roningrum.footballsubmission3.R.id.favorites
import org.roningrum.footballsubmission3.R.id.prevs
import org.roningrum.footballsubmission3.favorite.FavoriteFragment
import org.roningrum.footballsubmission3.nextmatch.NextFragment
import org.roningrum.footballsubmission3.prevmatch.PrevFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener { item->
         when(item.itemId){
             prevs ->{
                 loadPrevFragment(savedInstanceState)
             }
             R.id.nexts ->{
             loadNextFragment(savedInstanceState)
         }
             favorites->{
                 loadFavoriteFragement(savedInstanceState)
             }
         }
            true
        }
        bottom_navigation.selectedItemId = R.id.prevs
    }

    private fun loadNextFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    NextFragment(),
                    NextFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoriteFragement(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    FavoriteFragment(),
                    FavoriteFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadPrevFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    PrevFragment(),
                    PrevFragment::class.java.simpleName)
                .commit()
        }
    }
}
