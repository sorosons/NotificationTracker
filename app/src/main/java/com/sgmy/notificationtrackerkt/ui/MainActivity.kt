package   com.sgmy.notificationtrackerkt.ui;


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.ui.fragment.AppListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction()
            .replace(R.id.frlayout, AppListFragment())
            .addToBackStack("applistfragment")
            .commit()

       // LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, IntentFilter("Msg"))

    }




    private val onNotice: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {

            println("ALOOOO222222")
            // String pack = intent.getStringExtra("package");
            val title = intent.getStringExtra("title")
            val text = intent.getStringExtra("text")

            //int id = intent.getIntExtra("icon",0);
            val remotePackageContext: Context? = null
            try {
//                remotePackageContext = getApplicationContext().createPackageContext(pack, 0);
//                Drawable icon = remotePackageContext.getResources().getDrawable(id);
//                if(icon !=null) {
//                    ((ImageView) findViewById(R.id.imageView)).setBackground(icon);
//                }
                val byteArray = intent.getByteArrayExtra("icon")
                var bmp: Bitmap? = null
                if (byteArray != null) {
                    bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                }
               // val model = ModelClass()
                //model.name = "$title $text"
                //model.image = bmp
                println("HeYYX:"+"$title $text");


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


