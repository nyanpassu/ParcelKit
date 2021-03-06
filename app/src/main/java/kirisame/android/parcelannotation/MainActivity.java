package kirisame.android.parcelannotation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kirisame.android.toolset.parcel.ParcelField;
import kirisame.android.toolset.parcel.ParcelKit;

public class MainActivity extends Activity {

    public static final String PARCEL_OBJECT = "parcel_object";

    @ParcelField
    String mParcelField;

    @InjectView(R.id.text)
    TextView mTextView;

    @InjectView(R.id.button)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mParcelField = "hello parcel";

                Intent intent = new Intent();
                intent.putExtra(PARCEL_OBJECT, ParcelKit.toParcelable(MainActivity.this));
                intent.setClass(MainActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });

        getParcel();
    }

    private void getParcel() {
        Intent intent = getIntent();
        if (intent != null) {
            ParcelKit.fromParcelable(this,intent.getParcelableExtra(PARCEL_OBJECT));
            if (mParcelField != null) {
                mTextView.setVisibility(View.VISIBLE);
                mTextView.setText(mParcelField);
            } else {
                mTextView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
