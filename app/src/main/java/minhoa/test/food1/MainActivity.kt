package minhoa.test.food1

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerlayout : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var btn_navbar : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        init()
        setNavigationViewListener()
        displayLogo()
    }

    override fun onResume() {
        super.onResume()
        displayLogo()
    }

    private fun displayLogo() {
        var vvLogo : VideoView = findViewById(R.id.logo_video)
        var uri : Uri = Uri.parse("android.resource://minhoa.test.food1/" + R.raw.logo)
        vvLogo.setVideoURI(uri)
        vvLogo.requestFocus()
        vvLogo.setZOrderOnTop(true)
        vvLogo.start()
    }

    private fun initToolbar() {
        var toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {
        drawerlayout = findViewById(R.id.activity_navigation_drawer_drawerLayout)
        btn_navbar = findViewById(R.id.app_bar_nav_btn_show_drawer)
        btn_navbar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                findViewById<VideoView>(R.id.logo_video).setZOrderOnTop(false)
                drawerlayout.openDrawer(GravityCompat.START)
            }
        })
        toggle = ActionBarDrawerToggle(this, drawerlayout,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
    }


    fun start(view: View) {
        MediaPlayer.create(this, R.raw.btn_click).start()
        val intent = Intent(this, FoodTrackingActivity::class.java)
        startActivity(intent)
    }

    fun showAbout() {
        var intent = Intent(this, About::class.java)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.getItemId()) {
            R.id.about -> showAbout()
        }
        drawerlayout.closeDrawer(GravityCompat.START);
        return true
    }

    private fun setNavigationViewListener(){
        var navigationView : NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }
}