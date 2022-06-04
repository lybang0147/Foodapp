package hcmute.huynhlybang19110330.nhom10foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {
    Animation topAnimation,botAnimation;
    ImageView image,logo;
    Handler handler;
    TextView slogan;
    Database mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global.setId(-1);
        Global.setName("Guest");
        Global.setPhone("");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mydb = new Database(this);
        setContentView(R.layout.activity_main);
        File database=getApplicationContext().getDatabasePath(Database.DBNAME);
        if (database.exists()==false) {
            mydb.getReadableDatabase();
            if (copydatabase(this)) {
                Toast.makeText(this, "Copy database susscess", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Copy data", Toast.LENGTH_SHORT).show();
        }
        mydb.CreateTable();
        //Animation
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        //Hooks
        image=findViewById(R.id.imageViewlogo);
        logo=findViewById(R.id.imageViewlogoname);
        slogan=findViewById(R.id.txtviewslogan);
        image.setAnimation(topAnimation);
        logo.setAnimation(botAnimation);
        slogan.setAnimation(botAnimation);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                startActivity(intent);
                finish();
            }
        },1500);
    }
public boolean copydatabase(Context context)
{
    try{
        InputStream inputStream = context.getAssets().open(mydb.DBNAME);
        String outFileName = Database.DBLOCATION + Database.DBNAME;
        OutputStream outputStream = new FileOutputStream(outFileName);
        byte[] buff = new byte[1024];
        int length=0;
        while((length=inputStream.read(buff))>0)
        {
            outputStream.write(buff,0,length);
        }
        outputStream.flush();
        outputStream.close();
        Log.v("MainActivity","DB copied");
        return true;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
}