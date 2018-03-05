package nord.is.addvent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ólafur Georg Gylfason on 21-Feb-18.
 */

public class AddventFragment extends Fragment {
    private static final String TAG = "AddventFragment";

    private RecyclerView mEventRecyclerView;
    private List<EventItem> mItems = new ArrayList<>();

    public static AddventFragment newInstance() {
        return new AddventFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addvent, container, false);

        mEventRecyclerView =(RecyclerView) v.findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();

        return v;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mEventRecyclerView.setAdapter(new EventAdapter(mItems));
        }
    }

    private class EventHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;

        public EventHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView;
        }

        public void bindEventItem(EventItem item) {
            mTitleTextView.setText(item.toString());
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {
        private List<EventItem> mEventItems;

        public EventAdapter(List<EventItem> eventItems) {
            mEventItems = eventItems;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            TextView textView = new TextView(getActivity());
            return new EventHolder(textView);
        }

        @Override
        public void onBindViewHolder(EventHolder eventHolder, int position) {
            EventItem eventItem = mEventItems.get(position);
            eventHolder.bindEventItem(eventItem);
        }

        @Override
        public int getItemCount() {
            return mEventItems.size();
        }
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<EventItem>> {
        @Override
        protected List<EventItem> doInBackground(Void... params) {
            return new EventFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<EventItem> items) {
            mItems = items;
            setupAdapter();
        }
    }
}
