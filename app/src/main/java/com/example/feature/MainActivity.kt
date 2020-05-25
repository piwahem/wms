package com.example.feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.R
import com.example.base.BaseActivity
import com.example.feature.core.network.ApiInterface
import com.example.feature.login.data.LoginLocalDataSource
import com.example.feature.login.data.LoginMapper
import com.example.feature.login.data.LoginRemoteDataSource
import com.example.feature.login.data.LoginRepository
import com.example.feature.login.domain.SignOut
import com.example.feature.login.presentation.LoginActivity
import com.example.feature.material.presentation.MaterialReceiptWithPackageFragment
import com.example.feature.material.presentation.SlideMenuItemId
import com.example.feature.material.presentation.TestFragment
import com.example.feature.material.presentation.search.MaterialReceiptListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var toolBar: Toolbar
    private var mDrawerLayout: DrawerLayout? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null

    private lateinit var navigateViewModel: NavigateViewModel

    private lateinit var viewModel: MainViewModel
    private lateinit var factory: MainViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()
        setUpSlideMenu()
        setUpNavigation(savedInstanceState)
        //TODO Improve with dagger 2
        val pref = PreferenceManager.getDefaultSharedPreferences(MainApplication.appContext)
        val mapper = LoginMapper()
        val api = ApiInterface.getClient().create(
            ApiInterface::class.java)
        val repository = LoginRepository(LoginLocalDataSource(pref, mapper), LoginRemoteDataSource(api, mapper))
        factory = MainViewModelFactory(SignOut(repository))
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.viewState.observe(this, Observer {
            if (it.isSignOutSuccess) {
                LoginActivity.start(this)
                finish()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navigateViewModel.actionGuideFromComputer.value = item.itemId
        return true
    }

    //    "05152020WN001"
    private fun navigateToPage(page: String, receiptNoNumber: String? = null) {
        val fragment = when (page) {
            SlideMenuItemId.QR_MATERIAL_RECEIPT_WITH_PACKAGE -> MaterialReceiptWithPackageFragment.newInstance(receiptNoNumber)
            SlideMenuItemId.QR_MATERIAL_RECEIPT_WITH_PACKAGE_LIST -> MaterialReceiptListFragment()
            else -> TestFragment.newInstance(page)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, fragment, page)
            .commitAllowingStateLoss()
    }

    private fun toggleMenu() {
        if (mDrawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            mDrawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            mDrawerLayout?.openDrawer(GravityCompat.START)
        }
    }

    private fun closeMenu(){
        if (mDrawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            mDrawerLayout?.closeDrawer(GravityCompat.START)
        }
    }

    private fun setUpToolbar() {
        toolBar = toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolBar.navigationIcon = getDrawable(R.drawable.ic_hamburger)
        toolBar.setNavigationOnClickListener {
            toggleMenu()
        }
    }

    private fun setUpSlideMenu() {
        mDrawerLayout = drawer_layout
        mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0);
        mDrawerToggle?.isDrawerIndicatorEnabled = false
        mDrawerToggle?.toolbarNavigationClickListener = View.OnClickListener {
            mDrawerLayout?.openDrawer(GravityCompat.START)
        }

        mDrawerLayout?.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
//                sv.scrollTo(0,0)
            }
        })
    }

    private fun setUpNavigation(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            navigateToPage(SlideMenuItemId.HOME)
        }
        navigateViewModel = ViewModelProvider(this).get(NavigateViewModel::class.java)
        navigateViewModel.selected.observe(this, Observer {
            if (it.id == SlideMenuItemId.SIGN_OUT) {
                viewModel.signOut()
            } else {
                navigateToPage(it.id)
            }
            closeMenu()
        })
        navigateViewModel.materialSelected.observe(this, Observer {
            navigateToPage(SlideMenuItemId.QR_MATERIAL_RECEIPT_WITH_PACKAGE, it.receiptNo)
        })
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
