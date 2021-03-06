package pablomurciaodriozola.gorlizwebcam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val delay = 800L

    private var handler = Handler()
    private var isLoading = false
    private var runnable = Runnable({})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        isLoading = false
        handler.postDelayed(object : Runnable {
            override fun run() {
                loadImage()
                runnable = this
                handler.postDelayed(this, delay)
            }
        }, delay)

        super.onStart()
    }

    private fun loadImage(){
        if (!isLoading){
            isLoading = true
            Picasso.with(this).load(getString(R.string.imageUrl))
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .noPlaceholder()
                    .into(ivMain, object : Callback {
                        override fun onSuccess() {
                            isLoading = false
                        }

                        override fun onError() {
                            isLoading = false
                        }
                    })
        }
    }

    override fun onPause() {
        handler.removeCallbacks(runnable)
        isLoading = false;
        super.onPause()
    }
}
