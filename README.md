# SingleActionAspect
To avoid multiple rapid clicks on Android's Views with a single annotation "```@SingleActionFamily("familyName")```".
The annotation will only allow the method to be called once every 3 seconds for each family.


```java
button.setOnClickListener(new View.OnClickListener() {
    @SingleActionFamily("networkCall")
    public void onClick(View v) {
        // make network call.
    }
}

button2.setOnClickListener(new View.OnClickListener() {
    @SingleActionFamily("navigateForward")
    public void onClick(View v) {
        // navigate forward.
    }
});

AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @SingleActionFamily("navigateForward")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // navigate forward.
            }
        };
```

This project uses [AspectJ][AspectJ] to weave code. That will change the client code's line numbers and make it hard to debug. 


# License

    Copyright 2015 Feng Dai

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



[AspectJ]:https://github.com/eclipse/org.aspectj
