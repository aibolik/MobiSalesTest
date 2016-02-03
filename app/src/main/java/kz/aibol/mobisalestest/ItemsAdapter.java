package kz.aibol.mobisalestest;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by aibol on 2/3/16.
 */
public class ItemsAdapter extends CursorAdapter {


    public ItemsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.itemTitle.setText(cursor.getString(1));
        viewHolder.itemPrice.setText(cursor.getString(2));
    }

    public static class ViewHolder {
        public final TextView itemTitle;
        public final TextView itemPrice;

        public ViewHolder(View view) {
            itemTitle = (TextView) view.findViewById(R.id.textview_title);
            itemPrice = (TextView) view.findViewById(R.id.textview_price);
        }
    }
}
