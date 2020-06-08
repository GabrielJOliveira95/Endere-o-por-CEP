package com.cursoandroid.oliveiragabriel.helloworld.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.cursoandroid.oliveiragabriel.helloworld.fragment.RuaFragment
import com.cursoandroid.oliveiragabriel.helloworld.fragment.CepFragment
import com.cursoandroid.oliveiragabriel.helloworld.R
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems

class Main2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        supportActionBar?.elevation = 0f

        val smartTabLayout = findViewById<SmartTabLayout>(R.id.viewpagertab)
        val viewPager = findViewById<ViewPager>(R.id.viewpager)

        val adapter = FragmentPagerItemAdapter(
            supportFragmentManager,
            FragmentPagerItems.with(this)
                .add("Por Rua", RuaFragment::class.java)
                .add("Por CEP", CepFragment::class.java)
                .create()
        )

        viewPager.adapter = adapter
        smartTabLayout.setViewPager(viewPager)


    }
}
