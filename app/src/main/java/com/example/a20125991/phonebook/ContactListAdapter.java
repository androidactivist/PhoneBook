package com.example.a20125991.phonebook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.a20125991.phonebook.data.DataSource;
import com.example.a20125991.phonebook.data.Model;
import com.example.a20125991.phonebook.data.PhoneBookContract;

import java.util.List;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    Context mContext;
    private Cursor mCursor;
    private List<Model> mList;
    private long id;
    private int adapterPosition;

    //Constructor
    public ContactListAdapter(Context c, List<Model> list) {
        this.mContext = c;
        this.mList = list;

    }


    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ContactViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        Model model = mList.get(position);


        // Update the view holder with the information needed to display
        String name = model.getmFname();

        // Retrieve the id from the cursor and
        long id = model.getId();

        // Display the guest name
        holder.nameTextView.setText(name);

        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }


    }

    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Will display the guest name
        TextView nameTextView;
        // Will display the party size number
        ImageView delete, edit;


        public ContactViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.del);

            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            id = (long) itemView.getTag();
            adapterPosition = getAdapterPosition();
            switch (v.getId()) {
                case R.id.del:
                    showDialog();
                    /*boolean didItWork = true;
                    DataSource db = new DataSource(mContext);
                    db.open();
                    try {
                        //remove from DB
                        db.removeContact(id);
                    } catch (Exception e) {

                        didItWork = false;
                        String error = e.toString();
                        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        if (didItWork) {
                            //refresh  the list after deleting from phonebook
                            refreshList(getAdapterPosition());
                        }
                    }
                    db.close();
*/
                    break;
                case R.id.edit:
                    Intent intent = new Intent(mContext, EditContactActivity.class);
                    intent.putExtra("ID", id);
                    mContext.startActivity(intent);
                    break;
                default:
                    break;
            }

        }
    }


    public void refreshList(int id) {
        mList.remove(id);
        notifyItemRemoved(id);
        notifyItemRangeChanged(id, mList.size());
    }

    private void showDialog() {

        new MaterialDialog.Builder(mContext)
                .title(R.string.dialog_title)
                .content(R.string.content)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)

                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO
                        boolean didItWork = true;
                        DataSource db = new DataSource(mContext);
                        db.open();
                        try {
                            //remove from DB
                            db.removeContact(id);
                        } catch (Exception e) {

                            didItWork = false;
                            String error = e.toString();
                            Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } finally {
                            if (didItWork) {
                                //refresh  the list after deleting from phonebook
                                refreshList(adapterPosition);
                            }
                        }
                        db.close();

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO
                        dialog.dismiss();
                    }
                }).show();
    }


}
