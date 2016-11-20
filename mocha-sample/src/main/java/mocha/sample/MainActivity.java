/*
 * Copyright 2015 Feng Dai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mocha.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import com.tbse.aop.internal.SingleActionFamily;

public class MainActivity extends Activity
        implements MainActivityListener {

    private int mClickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView clickCountTextView = (TextView) findViewById(R.id.click_counts);
        showClickCount(clickCountTextView);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleActionFamily("add1")
            public void onClick(View v) {
                mClickCount++;
                showClickCount(clickCountTextView);
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleActionFamily("add1")
            public void onClick(View v) {
                mClickCount++;
                showClickCount(clickCountTextView);
            }
        });
        ListView listView = (ListView) findViewById(R.id.list_id);
        listView.setAdapter(new MyListAdapter(this, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)), this));
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @SingleActionFamily("add1")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mClickCount++;
                showClickCount(clickCountTextView);
            }
        };
        listView.setOnItemClickListener(onItemClickListener);


        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleActionFamily("add10")
            public void onClick(View v) {
                mClickCount += 10;
                showClickCount(clickCountTextView);
            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleActionFamily("add10")
            public void onClick(View v) {
                mClickCount += 10;
                showClickCount(clickCountTextView);
            }
        });
    }

    @Override
    public void showClickCount(TextView clickCountTextView) {
        clickCountTextView.setText(getString(R.string.msg_formatter, mClickCount));
    }
}
