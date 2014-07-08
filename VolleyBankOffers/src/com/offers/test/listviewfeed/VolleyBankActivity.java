package com.offers.test.listviewfeed;



import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.offers.test.listviewfeed.adapter.VolleyBankAdapter;
import com.offers.test.listviewfeed.app.VolleyBankController;
import com.offers.test.listviewfeed.data.VolleyBankFeedItem;
import com.volley.bankoffers.R;

public class VolleyBankActivity extends Activity {
	private static final String TAG = VolleyBankActivity.class.getSimpleName();
	private ListView listView;
	private VolleyBankAdapter volleylistAdapter;
	private List<VolleyBankFeedItem> feedItems;
	private String URL_FEED = "https://www.bespokeoffers.co.uk/mobile-api/v1/offers.json?page_size=10&page=1";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.list);

		feedItems = new ArrayList<VolleyBankFeedItem>();

		volleylistAdapter = new VolleyBankAdapter(this, feedItems);
		listView.setAdapter(volleylistAdapter);

	
		// We first check for cached request
		Cache cache = VolleyBankController.getInstance().getRequestQueue().getCache();
		Entry entry = cache.get(URL_FEED);
		if (entry != null) {
			// fetch the data from cache
			try {
				String data = new String(entry.data, "UTF-8");
				try {
					parseJsonFeed(new JSONObject(data));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		} else {
			// making fresh volley request and getting json
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
					URL_FEED, null, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							VolleyLog.d(TAG, "Response: " + response.toString());
							if (response != null) {
								parseJsonFeed(response);
							}
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							VolleyLog.d(TAG, "Error: " + error.getMessage());
						}
					});

			// Adding request to volley request queue
			VolleyBankController.getInstance().addToRequestQueue(jsonReq);
		}

	}

	/**
	 * Parsing json reponse and passing the data to VolleyBank adapter
	 * */
	private void parseJsonFeed(JSONObject response) {
		try {
			JSONArray feedArray = response.getJSONArray("offers");

			for (int i = 0; i < feedArray.length(); i++) {
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				VolleyBankFeedItem item = new VolleyBankFeedItem();

				item.setTitle(feedObj.getString("title"));

				// Image might be null sometimes
				String image = feedObj.isNull("image") ? null : feedObj
						.getString("image");
				item.setImage(image);
				item.setDecsription(feedObj.getString("description"));
				item.setCount(feedObj.getString("available_count"));
				item.setValiddate(feedObj.getString("end_date"));

				feedItems.add(item);
			}

			// notify data changes to list adapter
			volleylistAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
