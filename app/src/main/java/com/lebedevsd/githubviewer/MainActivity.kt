package com.lebedevsd.githubviewer

import com.lebedevsd.githubviewer.databinding.MainActivityBinding
import com.lebedevsd.githubviewer.base.ui.BaseActivity

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {
    override val viewModelClass: Class<MainViewModel> = MainViewModel::class.java
    override val layoutId: Int = R.layout.main_activity
}
