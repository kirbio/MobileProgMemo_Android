package th.ac.chula.kirbio.mobileprogproject;

import android.view.View;

import java.util.UUID;

public interface OnItemClickListener {
    void onItemClick(View view, int type, int position, UUID memoId);
}
