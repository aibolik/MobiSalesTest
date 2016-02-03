package kz.aibol.mobisalestest;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import kz.aibol.mobisalestest.data.DataContract;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ItemsAdapter mItemsAdapter;
    private static final int ITEMS_LOADER = 100;

    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem okItem = new SwipeMenuItem(getApplicationContext());
            okItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
            okItem.setWidth(dp2px(90));
            okItem.setTitle("Ok");
            okItem.setTitleSize(18);
            okItem.setTitleColor(Color.WHITE);

            SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00, 0x00)));
            deleteItem.setWidth(dp2px(90));
            deleteItem.setTitle("Delete");
            deleteItem.setTitleSize(18);
            deleteItem.setTitleColor(Color.WHITE);

            menu.addMenuItem(okItem);
            menu.addMenuItem(deleteItem);
        }
    };

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mItemsAdapter = new ItemsAdapter(this, null, 0);

        SwipeMenuListView itemsList = (SwipeMenuListView) findViewById(R.id.listview_items);
        itemsList.setAdapter(mItemsAdapter);
        itemsList.setMenuCreator(creator);
        itemsList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        itemsList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(ListActivity.this, "Okay", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        break;
                }
                return false;
            }
        });

        getLoaderManager().initLoader(ITEMS_LOADER, null, this);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri itemsUri = DataContract.ItemsEntry.CONTENT_URI;

        String[] projection = {
                DataContract.ItemsEntry.TABLE_NAME + "." + DataContract.ItemsEntry._ID,
                DataContract.ItemsEntry.COLUMN_NAME1,
                DataContract.PricesEntry.COLUMN_PRICE
        };

        return new CursorLoader(this,
                itemsUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mItemsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mItemsAdapter.swapCursor(null);
    }
}
