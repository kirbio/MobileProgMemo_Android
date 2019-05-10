package th.ac.chula.kirbio.mobileprogproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import th.ac.chula.kirbio.mobileprogproject.db.MemoItem;

public class MemoAdapter  extends
        RecyclerView.Adapter<MemoAdapter.ViewHolder> {

        private OnItemClickListener mOnItemClickListener;


        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        public class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView nameTextView;
            public Button editButton;
            public Button viewButton;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                nameTextView = itemView.findViewById(R.id.contact_name);
                editButton = itemView.findViewById(R.id.btnEdit_menu);
                viewButton = itemView.findViewById((R.id.btnView_menu));
            }
        }

    // Store a member variable for the contacts
    private List<MemoItem> memoItemList;

    // Pass in the contact array into the constructor
    public MemoAdapter(List<MemoItem> memos,OnItemClickListener listener) {
        memoItemList = memos;
        mOnItemClickListener = listener;
    }

    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_memo, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MemoAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        MemoItem memo = memoItemList.get(position);

        final UUID memoId = memo.memoId;

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(memo.name);

        Button btnView = viewHolder.viewButton;
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                mOnItemClickListener.onItemClick(v,0,position,memoId);
            }
        });

        Button btnEdit = viewHolder.editButton;
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                mOnItemClickListener.onItemClick(v,1,position,memoId);
            }
        });
        //button.setText(memo.isOnline() ? "Message" : "Offline");
        //button.setEnabled(memo.isOnline());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return memoItemList.size();
    }



    public void RefreshItemList(List<MemoItem> memos){
        memoItemList = memos;
    }

}
